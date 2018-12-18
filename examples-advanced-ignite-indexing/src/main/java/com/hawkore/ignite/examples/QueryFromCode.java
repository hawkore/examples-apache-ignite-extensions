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

import static org.hawkore.ignite.lucene.builder.Builder.all;
import static org.hawkore.ignite.lucene.builder.Builder.bitemporal;
import static org.hawkore.ignite.lucene.builder.Builder.bool;
import static org.hawkore.ignite.lucene.builder.Builder.dateMapper;
import static org.hawkore.ignite.lucene.builder.Builder.geoDistance;
import static org.hawkore.ignite.lucene.builder.Builder.index;
import static org.hawkore.ignite.lucene.builder.Builder.match;
import static org.hawkore.ignite.lucene.builder.Builder.must;
import static org.hawkore.ignite.lucene.builder.Builder.not;
import static org.hawkore.ignite.lucene.builder.Builder.search;
import static org.hawkore.ignite.lucene.builder.Builder.snowballAnalyzer;
import static org.hawkore.ignite.lucene.builder.Builder.stringMapper;
import static org.hawkore.ignite.lucene.builder.Builder.textMapper;
import static org.hawkore.ignite.lucene.builder.Builder.uuidMapper;
import static org.hawkore.ignite.lucene.builder.Builder.wildcard;

import java.util.Calendar;

import org.apache.ignite.IgniteCheckedException;
import org.apache.ignite.Ignition;
import org.apache.ignite.internal.IgniteKernal;
import org.hawkore.ignite.lucene.builder.index.Index;

import com.hawkore.ignite.examples.entities.tweets.Tweet;
import com.hawkore.ignite.examples.entities.tweets.TweetKey;
import com.hawkore.ignite.examples.entities.users.User;
import com.hawkore.ignite.examples.entities.users.UserKey;

/**
 * This class provides a some samples to construct lucene filters using lucene
 * builder for <b>TESTING</b> purposes.
 * 
 * <p>
 * 
 * Run from a terminal in 'examples-advanced-ignite-indexing' directory:
 * 
 * <pre>
 * mvn exec:java -Dexec.mainClass="com.hawkore.ignite.examples.QueryFromCode" -Dexec.classpathScope=compile -DnodeName=queryCode
 * </pre>
 * 
 * <b>IMPORTANT</b>: Command line system property <b><code>nodeName</code></b>
 * will be use to create
 * <code>IGNITE_HOME=[system temp dir]/clients/[nodeName]</code>,
 * <code>IGNITE_HOME</code> directory must be unique per node.
 * 
 * <p>
 * 
 * If you want to start a test server node with JVM parameters, set <b>
 * <code>MAVEN_OPTS</code></b> system property before start node.
 *
 * <p>
 * 
 * Sample for linux:
 * 
 * <pre>
 * export MAVEN_OPTS="-Xms128m -Xmx512m -XX:+UseG1GC -XX:+DisableExplicitGC"
 * </pre>
 *
 *
 * @author Manuel Núñez (manuel.nunez@hawkore.com)
 *
 *
 */
public class QueryFromCode {

    private static String testCacheName = "test";

    private static String tweetsCacheName = "tweets";

    private static void testSQLCensus(IgniteKernal ignite) {

        String query = "SELECT name, city, vtFrom, vtTo, ttFrom, ttTo FROM \"test\".census USE INDEX(census_lucene_idx) WHERE lucene = ? and name = ?";

        // If you want to know what is the last info about where John resides,
        // you perform a query with tt_from and tt_to set to now_value:
        SQLQueryRunner.fetch(ignite, query, search().refresh(true).filter(bitemporal("bitemporal").ttFrom("2200/12/31")
            .ttTo("2200/12/31")
            .vtFrom(0)
            .vtTo("2200/12/31")).build(), "John");

        // If you want to know what is the last info about where John resides
        // now, you perform a query with tt_from, tt_to, vt_from, vt_to set to
        // now_value:
        SQLQueryRunner.fetch(ignite, query, search().refresh(true).filter(bitemporal("bitemporal").ttFrom("2200/12/31")
            .ttTo("2200/12/31")
            .vtFrom("2200/12/31")
            .vtTo("2200/12/31")).build(), "John");

        // If the test case needs to know what the system was thinking at
        // '2015/03/01' about where John resides in "2015/03/01".
        SQLQueryRunner.fetch(ignite, query, search().refresh(true).filter(bitemporal("bitemporal").ttFrom("2015/03/01")
            .ttTo("2015/03/01")
            .vtFrom("2015/03/01")
            .vtTo("2015/03/01")).build(), "John");

        // If the test case needs to know what the system was thinking at
        // '2015/07/05' about where John resides:
        SQLQueryRunner.fetch(ignite, query,
            search().refresh(true).filter(bitemporal("bitemporal").ttFrom("2015/07/05").ttTo("2015/07/05")).build(),
            "John");
    }

    private static void testLegacyTextQueryUsers(IgniteKernal ignite) {

        // USING Advanced JSON filter
        // search for all the indexed rows:
        SQLQueryRunner.fetchTextQuery(ignite, testCacheName, UserKey.class, User.class,
            search().refresh(true).filter(all()).build());

        // using wildcard with advanced filtering
        SQLQueryRunner.fetchTextQuery(ignite, testCacheName, UserKey.class, User.class,
            search().refresh(true).filter(bool().must(wildcard("food", "tu*")).not(wildcard("name", "*a"))).build());
        SQLQueryRunner.fetchTextQuery(ignite, testCacheName, UserKey.class, User.class,
            search().refresh(true).filter(must(wildcard("food", "tu*")).not(wildcard("name", "*a"))).build());
        SQLQueryRunner.fetchTextQuery(ignite, testCacheName, UserKey.class, User.class,
            search().refresh(true).filter(wildcard("food", "tu*"), not(wildcard("name", "*a"))).build());

        // with pagination
        SQLQueryRunner.fetchTextQuery(ignite, tweetsCacheName, TweetKey.class, Tweet.class,
            search().refresh(true)
                .filter(geoDistance("place", 40.416775, -3.703790, "10km"), match("countryCode", "ES"))
                .sort(geoDistance("place", 40.416775, -3.703790)).limit(120).build());
        SQLQueryRunner.fetchTextQuery(ignite, tweetsCacheName, TweetKey.class, Tweet.class,
            search().refresh(true)
                .filter(geoDistance("place", 40.416775, -3.703790, "10km"), match("countryCode", "ES"))
                .sort(geoDistance("place", 40.416775, -3.703790)).limit(100).offset(20).build());

        // USING Lucene classic query parser syntax
        // see
        // https://lucene.apache.org/core/5_5_4/queryparser/org/apache/lucene/queryparser/classic/package-summary.html

        // Wildcard on field
        SQLQueryRunner.fetchTextQuery(ignite, testCacheName, UserKey.class, User.class, "name:Ja*");
        // Fuzzy on field
        SQLQueryRunner.fetchTextQuery(ignite, testCacheName, UserKey.class, User.class, "food:chip~");
        // RegExp on field
        SQLQueryRunner.fetchTextQuery(ignite, testCacheName, UserKey.class, User.class, "name:/([J][aeiou]).*/");
        // Proximity search
        SQLQueryRunner.fetchTextQuery(ignite, testCacheName, UserKey.class, User.class, "phrase:\"mancha camisa\"~2");
        // Range Search
        SQLQueryRunner.fetchTextQuery(ignite, testCacheName, UserKey.class, User.class, "name:[Alicia TO Joe}");

        // default text search on any field
        SQLQueryRunner.fetchTextQuery(ignite, testCacheName, UserKey.class, User.class, "fema~");

    }

    private static void testSQLUsers(IgniteKernal ignite) {

        String query = "SELECT * FROM \"test\".user USE INDEX(user_lucene_idx) WHERE lucene = ?";

        // USING Advanced JSON filter

        // search for all the indexed rows:
        SQLQueryRunner.fetch(ignite, query, search().refresh(true).filter(all()).build());

        // Wildcard
        SQLQueryRunner.fetch(ignite, query,
            search().refresh(true).filter(bool().must(wildcard("food", "tu*")).not(wildcard("name", "*a"))).build());
        SQLQueryRunner.fetch(ignite, query,
            search().refresh(true).filter(must(wildcard("food", "tu*")).not(wildcard("name", "*a"))).build());
        SQLQueryRunner.fetch(ignite, query,
            search().refresh(true).filter(wildcard("food", "tu*"), not(wildcard("name", "*a"))).build());

        // USING Lucene classic query parser syntax
        // see
        // https://lucene.apache.org/core/5_5_4/queryparser/org/apache/lucene/queryparser/classic/package-summary.html

        // Wildcard on field
        SQLQueryRunner.fetch(ignite, query, "name:Ja*");
        // Fuzzy on field
        SQLQueryRunner.fetch(ignite, query, "food:chip~");
        // RegExp on field
        SQLQueryRunner.fetch(ignite, query, "name:/([J][aeiou]).*/");
        // Proximity search
        SQLQueryRunner.fetch(ignite, query, "phrase:\"mancha camisa\"~2");
        // Range Search
        SQLQueryRunner.fetch(ignite, query, "name:[Alicia TO Joe}");
        // default text search on any field
        SQLQueryRunner.fetch(ignite, query, "fema~");

    }

    private static void welcome() {
        System.out.println();
        System.out.println("********************************************");
        System.out.println("  HK code samples for lucene searches");
        System.out.println();
        System.out.println("     Copyright (c) " + Calendar.getInstance().get(Calendar.YEAR) + " - HAWKORE, S.L.");
        System.out.println("********************************************");
        System.out.println();
        System.out.println();
    }

    /**
     * 
     * @param args
     * @throws IgniteCheckedException
     */
    public static void main(String[] args) throws IgniteCheckedException {

        System.setProperty("env", "test");

        IgniteKernal ignite = (IgniteKernal) Ignition.start("ignite-client-config.xml");

        welcome();
        
        testSQLCensus(ignite);

        testSQLUsers(ignite);

        testLegacyTextQueryUsers(ignite);


        // SAMPLE INDEX GENERATION
        Index index = index("keyspace_name", "table_name")
            .refreshSeconds(10)
            .defaultAnalyzer("english")
            .analyzer("danish", snowballAnalyzer("danish"))
            .mapper("id", uuidMapper())
            .mapper("user", stringMapper().caseSensitive(false))
            .mapper("message", textMapper().analyzer("danish"))
            .mapper("date", dateMapper().pattern("yyyyMMdd"));

        System.out.println("Sample JSON lucene index configuration from code:\n\n" + index.build());
        System.out.println();
        System.out.println("Sample DDL CREATE INDEX statement from code:\n\n " + index.buildDDL());
        System.out.println();
        
        Ignition.stop(true);

        System.exit(0);
    }
}
