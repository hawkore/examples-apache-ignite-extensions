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
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;

import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;
import org.apache.ignite.internal.IgniteKernal;

import com.hawkore.ignite.examples.entities.profiles.Address;
import com.hawkore.ignite.examples.entities.profiles.Profile;

/**
 * This class provides a simple way to populate Profiles for testing Hawkore's
 * Apache Ignite Advanced Indexing.
 * 
 * <p>
 * 
 * Run from a terminal in 'examples-advanced-ignite-indexing' directory:
 * 
 * <pre>
 * mvn exec:java -Dexec.mainClass="com.hawkore.ignite.examples.PopulateProfiles" -Dexec.classpathScope=compile -DnodeName=populator
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
public class PopulateProfiles {

    // environment to load properties file. Default "test"
    private static final String ENVIRONMENT_PROP = "env";
    private static final String DEFAULT_ENVIRONMENT = "test";
    private static final String ENV = System.getProperty(ENVIRONMENT_PROP, DEFAULT_ENVIRONMENT);

    private static String cacheName = "profiles";

    private static void welcome() {
        System.out.println();
        System.out.println("********************************************");
        System.out.println("           HK profiles populator");
        System.out.println();
        System.out.println("     Copyright (c) " + Calendar.getInstance().get(Calendar.YEAR) + " - HAWKORE, S.L.");
        System.out.println("********************************************");
        System.out.println();
        System.out.println();
    }

    public static void main(String[] args) {
        System.setProperty("env", ENV);

        IgniteKernal ignite = (IgniteKernal) Ignition.start("ignite-client-config.xml");

        welcome();

        IgniteCache cache = ignite.cache(cacheName);

        for (int i = 0; i < 100; i++) {

            Profile p = new Profile();

            p.setLogin("user_" + i);
            p.setFirstName("John " + i);
            p.setLastName("Smith " + i);

            p.setCities(new ArrayList<String>(Arrays.asList("London", "Madrid", "OtherCity_"+i)));

            p.setAddress(new Address("Chicago", "S Ellis Ave #" + i, 60601+i));

            HashMap<String, Address> addresses = new HashMap<>();
            addresses.put("Illinois", new Address("Chicago", "S Ellis Ave #" + i, 60601+i));
            addresses.put("Colorado", new Address("Denver", "16th street #" + i, 80012+i));
            addresses.put("OtherState_"+i, new Address("OtherCity_"+i, "Street #" + i, 1000+i));
            p.setAddresses(addresses);

            HashMap<String, String> textAddresses = new HashMap<>();
            textAddresses.put("London", "Camden Road #" + i);
            textAddresses.put("Madrid", "Paseo de la Castellana #" + i);
            textAddresses.put("OtherCity_"+i, "Street #" + i);
            p.setTextAddresses(textAddresses);

            
            ArrayList<Address> addressList = new ArrayList<>();
            addressList.add(new Address("Chicago", "S Ellis Ave #" + i, 60601+i));
            addressList.add(new Address("Denver", "16th street #" + i, 80012+i));
            addressList.add(new Address("OtherCity_"+i, "Street #" + i, 1000+i));
            p.setAddressList(addressList);
            
            cache.put(p.getLogin(), p);
        }

        // just show total number of created profiles
        SQLQueryRunner.fetch(ignite, "select count(*) as profiles from \"profiles\".profile");

        Ignition.stop(true);

        System.exit(0);
    }

}
