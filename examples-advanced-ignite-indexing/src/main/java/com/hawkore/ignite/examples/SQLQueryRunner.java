/*
 * Copyright (C) 2018 HAWKORE S.L. (http://hawkore.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hawkore.ignite.examples;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.cache.Cache.Entry;
import jline.TerminalFactory;
import jline.console.ConsoleReader;
import jline.console.completer.FileNameCompleter;
import org.apache.commons.io.IOUtils;
import org.apache.ignite.Ignition;
import org.apache.ignite.cache.query.FieldsQueryCursor;
import org.apache.ignite.cache.query.SqlFieldsQuery;
import org.apache.ignite.cache.query.TextQuery;
import org.apache.ignite.internal.IgniteKernal;
import org.apache.ignite.internal.util.typedef.internal.S;
import org.apache.ignite.internal.util.typedef.internal.U;

/**
 * This class provides a simple way to run SQL statements to Apache Ignite
 * cluster for <b>TESTING</b> purposes.
 *
 * <p>
 * Run from a terminal in 'examples-advanced-ignite-indexing' directory:
 *
 * <pre>
 *      mvn exec:java -Dexec.mainClass="com.hawkore.ignite.examples.SQLQueryRunner" -Dexec.classpathScope=compile -DnodeName=query
 * </pre>
 *
 * <b>IMPORTANT</b>: Command line system property <b><code>nodeName</code></b>
 * will be use to create
 * <code>IGNITE_HOME=[system temp dir]/clients/[nodeName]</code>,
 * <code>IGNITE_HOME</code> directory must be unique per node.
 *
 * <p>
 * <p>
 * If you want to start a test server node with JVM parameters, set
 * <b><code>MAVEN_OPTS</code></b> system property before start node.
 *
 * <p>
 * <p>
 * Sample for linux:
 * <pre>
 * export MAVEN_OPTS="-Xms128m -Xmx512m -XX:+UseG1GC -XX:+DisableExplicitGC"
 * </pre>
 *
 * @author Manuel Núñez (manuel.nunez@hawkore.com)
 */
public class SQLQueryRunner {

    private static final String INPUT_USER_MESSAGE
        = "Enter SQL statement and press 'enter' twice to run ('q' to exit):";

    public static final Pattern COMMENT_REPLACE_PATTERN = Pattern.compile("--.*$", Pattern.MULTILINE);

    private static String askUserInput(ConsoleReader console, String promptMessage, Object... promptMessageArgs) {

        System.out.println(String.format(promptMessage, promptMessageArgs));

        List<String> lines = new ArrayList<>();
        try {
            String line = null;
            while ((line = console.readLine()) != null && !line.trim().isEmpty()) {
                line = line.replaceAll("\t", " ").trim();
                if (!line.isEmpty() && !line.startsWith("--")) {
                    // contains a comment on line, just remove it
                    int idx = line.indexOf("--");
                    if (idx > -1) {
                        line = line.substring(0, idx);
                    }
                    lines.add(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return String.join(" ", lines);
    }

    /**
     * Simple ascii table print
     *
     * @param columns
     * @param data
     * @param init
     * @param end
     */
    public static void printResultset(List<String> columns, List<List<?>> data, long init, long end) {

        HashMap<Integer, Integer> colSize = new HashMap<>();

        ArrayList<String> flatTableData = new ArrayList<>();

        // add columns names
        for (int col = 0; col < columns.size(); col++) {
            colSize.put(col, columns.get(col).length());
            flatTableData.add(columns.get(col));
        }

        // add rows and compute cell length
        for (List<?> row : data) {
            for (int col = 0; col < columns.size(); col++) {
                Object cell = row.get(col);
                String cellValue = cell == null ? "null" : cell.toString();
                colSize.put(col, Math.max(colSize.get(col), cellValue.length()));
                flatTableData.add(cellValue);
            }
        }

        // row format based on cell length
        String rowFormat = String.join(" | ",
            colSize.values().stream().map(s -> "%" + s + "s").collect(Collectors.toList()));

        // print results
        int rows = flatTableData.size() / columns.size();

        Object[] cellsVoid = new Object[columns.size()];
        Arrays.fill(cellsVoid, "");

        // line separator
        System.out
            .println("+-" + String.format(rowFormat, cellsVoid).replaceAll("\\|", "+").replaceAll(" ", "-") + "-+");

        for (int row = 0; row < rows; row++) {
            Object[] cells = new Object[columns.size()];
            for (int cell = 0; cell < cells.length; cell++) {
                cells[cell] = flatTableData.get(row * cells.length + cell);
            }

            System.out.println("| " + String.format(rowFormat, cells) + " |");

            if (row == 0) {
                // header separator
                System.out.println(
                    "+=" + String.format(rowFormat, cellsVoid).replaceAll("\\|", "+").replaceAll(" ", "=") + "=+");
            } else {
                // line separator
                System.out.println(
                    "+-" + String.format(rowFormat, cellsVoid).replaceAll("\\|", "+").replaceAll(" ", "-") + "-+");
            }
        }

        System.out.format("%n(%s rows fetched in %s ms)%n%n", data.size(), end - init);
    }

    /**
     * run a SQL query and prints results as ascci table
     *
     * @param ignite
     * @param query
     * @param queryParams
     */
    public static List<List<?>> fetch(IgniteKernal ignite, String query, Object... queryParams) {

        if (query != null && !query.isEmpty()) {

            SqlFieldsQuery q = new SqlFieldsQuery(query);

            if (queryParams != null && queryParams.length > 0) {
                q.setArgs(queryParams);
            }

            long init = System.currentTimeMillis();

            FieldsQueryCursor<List<?>> cursor = ignite.context().query().querySqlFields(q, false);
            List<List<?>> data = cursor.getAll();

            long end = System.currentTimeMillis();

            ArrayList<String> columns = new ArrayList<>();

            // add columns names
            for (int col = 0; col < cursor.getColumnsCount(); col++) {
                columns.add(cursor.getFieldName(col));
            }

            printResultset(columns, data, init, end);

            return data;
        }

        return Collections.emptyList();
    }

    /**
     * run an out of the box Apache Ignite's TextQuery and prints results
     *
     * @param ignite
     * @param cacheName
     * @param keyType
     * @param valType
     * @param textQuery
     */
    public static <K, V> void fetchTextQuery(IgniteKernal ignite,
        String cacheName,
        Class<K> keyType,
        Class<V> valType,
        String textQuery) {

        if (textQuery != null && !textQuery.isEmpty()) {

            System.out.format("%n--- Apache Ignite's TextQuery on cache '%s' ('%s', '%s'). Lucene query: '%s' ---%n%n",
                cacheName, keyType.getSimpleName(), valType.getSimpleName(), textQuery);

            //created legacy lucene text query
            TextQuery<K, V> q = new TextQuery<>(valType, textQuery);

            long init = System.currentTimeMillis();

            List<Entry<K, V>> entries = ignite.cache(cacheName).query(q).getAll();

            long end = System.currentTimeMillis();

            for (Entry<K, V> entry : entries) {
                System.out
                    .format("%s, %s%n", S.toString(keyType, entry.getKey()), S.toString(valType, entry.getValue()));
            }

            System.out.format("%n(%s entries fetched in %s ms)%n%n", entries.size(), end - init);

            System.out.println("----------------------------------------------");
        }
    }

    private static void welcome() {
        System.out.println();
        System.out.println("********************************************");
        System.out.println("            HK SQL Query Runner");
        System.out.println();
        System.out.println("     Copyright (c) " + Calendar.getInstance().get(Calendar.YEAR) + " - HAWKORE, S.L.");
        System.out.println("********************************************");
        System.out.println();
        System.out.println();
    }

    public static void runMultiStatement(IgniteKernal ignite, String query) {
        // remove comments
        String[] queries = COMMENT_REPLACE_PATTERN.matcher(query).replaceAll("").split(";");
        for (String q : queries) {
            try {
                fetch(ignite, q.replace("\n", " ").trim());
            } catch (Exception e) {
                throw new RuntimeException("Unable to execute query: " + q, e);
            }
        }
    }

    /**
     * @param args
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static void main(String[] args) throws IOException, ClassNotFoundException {

        String query = null;

        System.setProperty("env", "test");

        IgniteKernal ignite = (IgniteKernal)Ignition.start("ignite-client-config.xml");

        while (!ignite.active()) {
            U.sleepSafe(1000);
        }

        welcome();

        try {
            ConsoleReader console = new ConsoleReader();
            console.addCompleter(new FileNameCompleter());
            console.setPaginationEnabled(true);

            for (query = askUserInput(console, INPUT_USER_MESSAGE); !query.equalsIgnoreCase("q");
                query = askUserInput(console, INPUT_USER_MESSAGE)) {
                runMultiStatement(ignite, query);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                TerminalFactory.get().restore();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        Ignition.stop(true);

        System.exit(0);
    }

}
