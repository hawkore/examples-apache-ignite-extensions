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

import org.apache.ignite.Ignition;

/**
 * 
 * This class provides a simple way to start an Apache Ignite server node for
 * <b>TESTING</b> purposes.
 * 
 * <p>
 * 
 * Run from a terminal in 'ignite-server-node-test' directory:
 * 
 * <pre>
 * mvn exec:java -Dexec.mainClass="com.hawkore.ignite.examples.IgniteServerNode" -Dexec.classpathScope=compile -DnodeName=node1
 * </pre>
 * 
 * If you want to start a second node, just open another terminal in same directory and
 * change <code>nodeName</code>, example -DnodeName=node2 :
 * 
 * <pre>
 * mvn exec:java -Dexec.mainClass="com.hawkore.ignite.examples.IgniteServerNode" -Dexec.classpathScope=compile -DnodeName=node2
 * </pre>
 * 
 * <b>IMPORTANT</b>: Command line system property <b><code>nodeName</code></b> will be use to
 * create <code>IGNITE_HOME=[system temp dir]/servers/[nodeName]</code>,
 * <code>IGNITE_HOME</code> directory must be unique per node.
 * 
 * <p>
 * 
 * If you want to start a test server node with JVM parameters, set
 * <b><code>MAVEN_OPTS</code></b> system property before start node.
 * 
 * <p>
 * 
 * Sample for linux with JMX remote management enabled at port 5678. You could use
 * <b>jconsole</b> to manage com.hawkore.ignite.indexing MBeans:
 * 
 * <pre>
 * export MAVEN_OPTS="-Xms128m -Xmx512m -XX:+UseG1GC -XX:+DisableExplicitGC -Dcom.sun.management.jmxremote=true -Dcom.sun.management.jmxremote.port=5679 -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false -Dcom.sun.management.jmxremote.rmi.port=5679"
 * </pre>
 * 
 * 
 * @author Manuel Núñez (manuel.nunez@hawkore.com)
 */
public class IgniteServerNode {

    /**
     * @param args
     *            - no args required
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        System.setProperty("env", "test");

        Ignition.start("ignite-server-alone-config.xml");

    }
}
