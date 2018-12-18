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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteDataStreamer;
import org.apache.ignite.Ignition;
import org.apache.ignite.internal.IgniteKernal;

import com.hawkore.ignite.examples.dataproviders.PoisProvider;
import com.hawkore.ignite.examples.entities.pois.Poi;
import com.hawkore.ignite.examples.entities.pois.PoiKey;

/**
 * 
 * This class provides a simple way to populate pois cache for testing
 * Hawkore's Apache Ignite Advanced Indexing.
 * 
 * By default:
 * <ul>
 * <li>100.000 (id = 0-99.999) pois in Spain (ES) randomly geo located</li>
 * <li>100.000 (id = 100.000-199.999) pois in Portugal (PT) randomly geo
 * located</li>
 * <li>100.000 (id = 199.999-299.999) pois in France (FR) randomly geo located
 * </li>
 * </ul>
 * 
 * <p>
 * 
 * Run from a terminal in 'examples-advanced-ignite-indexing' directory:
 * 
 * <pre>
 *      mvn exec:java -Dexec.mainClass="com.hawkore.ignite.examples.PopulatePois" -Dexec.classpathScope=compile -DnodeName=populator
 * </pre>
 * 
 * <b>IMPORTANT</b>: Command line system property <b><code>nodeName</code></b>
 * will be use to create
 * <code>IGNITE_HOME=[system temp dir]/clients/[nodeName]</code>,
 * <code>IGNITE_HOME</code> directory must be unique per node.
 * 
 * <p>
 * 
 * If you want to start a test server node with JVM parameters, set
 * <b><code>MAVEN_OPTS</code></b> system property before start node.
 *
 * <p>
 * 
 * Sample for linux:
 * <pre>
 * export MAVEN_OPTS="-Xms128m -Xmx512m -XX:+UseG1GC -XX:+DisableExplicitGC"
 * </pre>
 * 
 * @author Manuel Núñez (manuel.nunez@hawkore.com)
 *
 *
 */
public class PopulatePois {

    private static ExecutorService pool;

    // number of pois to generate. Default 10.000
    private static final String POIS_NUMBER_PROP = "pois";
    private static final int DEFAULT_POIS_NUMBER = 100000;
    private static int pois = intProperty(POIS_NUMBER_PROP, DEFAULT_POIS_NUMBER);

    // number of producer threads. Default 4
    private static final String PRODUCER_THREADS_PROP = "producers";
    private static final int DEFAULT_PRODUCER_THREADS = 2;
    private static int threads = intProperty(PRODUCER_THREADS_PROP, DEFAULT_PRODUCER_THREADS);

    // environment to load properties file. Default "test"
    private static final String ENVIRONMENT_PROP = "env";
    private static final String DEFAULT_ENVIRONMENT = "test";
    private static final String ENV = System.getProperty(ENVIRONMENT_PROP, DEFAULT_ENVIRONMENT);

    private static String poisCacheName = "pois";

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private static void populatepois(Ignite ignite, String cacheName, int poisNumber, String countryCode,
        int initKey) throws Exception {

        System.out.format("Populating '%s' cache with %d pois randomly geo located (%d producer threads) in %s...%n",
            poisCacheName, pois, threads, countryCode);

        PoisProvider poisProvider = new PoisProvider(poisNumber, countryCode, initKey);

        List<Future<?>> futs = new ArrayList<>();

        IgniteDataStreamer poisCacheStreamer = ignite.dataStreamer(cacheName);
        poisCacheStreamer.allowOverwrite(false);
        // auto flush 20 seconds
        poisCacheStreamer.autoFlushFrequency(20000);

        long init = System.currentTimeMillis();

        for (int i = 0; i < threads; i++) {
            futs.add(pool.submit(() -> {
                Poi poi;

                Map<PoiKey, Poi> entries = new HashMap<>();

                while ((poi = poisProvider.get()) != null) {
                    // packages of 5000 pois before stream them to cache
                    if (!entries.isEmpty() && entries.size() % 5000 == 0) {
                        poisCacheStreamer.addData(entries);
                        entries.clear();
                    }
                    entries.put(poi.getKey(), poi);
                }
                // send remaining entries
                if (!entries.isEmpty()) {
                    poisCacheStreamer.addData(entries);
                    entries.clear();
                }

            }));
        }
        // wait for finish
        for (Future<?> f : futs) {
            f.get();
        }
        System.out.format("Stream any remaining data and close streamer....\n");
        poisCacheStreamer.close(false);

        long elapsed = System.currentTimeMillis() - init;

        System.out.format("Population finishes in %d ms. Average throwoutput: %s records/second (%s ms per record).%n",
            elapsed, 1000 * pois / elapsed, (1d * elapsed) / (1d * pois));
    }

    /**
     * Read int system property
     * 
     * @param name
     *            system property name
     * @param dflt
     *            default value
     * @return property value
     */
    public static int intProperty(String name, int dflt) {
        String val = System.getProperty(name);
        if (val == null) {
            return dflt;
        }
        try {
            return Integer.parseInt(val);
        } catch (Exception e) {
            return dflt;
        }
    }
    
    private static void welcome() {
        System.out.println();
        System.out.println("********************************************");
        System.out.println("           HK pois populator");
        System.out.println();
        System.out.println("     Copyright (c) " + Calendar.getInstance().get(Calendar.YEAR) + " - HAWKORE, S.L.");
        System.out.println("********************************************");
        System.out.println();
        System.out.println();
    }

    /**
     * 
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        System.setProperty("env", ENV);

        IgniteKernal ignite = (IgniteKernal) Ignition.start("ignite-client-config.xml");

        welcome();
        
        pool = Executors.newFixedThreadPool(threads);

        populatepois(ignite, poisCacheName, pois, "ES", 0);

        populatepois(ignite, poisCacheName, pois, "PT", pois);

        populatepois(ignite, poisCacheName, pois, "FR", 2 * pois);

        //just show total number of created pois
        SQLQueryRunner.fetch(ignite, "select count(*) as pois from \"pois\".poi");

        Ignition.stop(true);

        System.exit(0);
    }

}
