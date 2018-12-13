# Hawkore's extensions for Apache Ignite - samples project

Hi! This is Hawkore's extensions for Apache Ignite - samples project.


- Requirements

	-  Java >= 1.8.0_65 (OpenJDK and Sun have been tested)
	-  Maven >= 3.3.0

- Clone

	-  Clone this project: `git clone http://github.com/hawkore/examples-advanced-ignite-extensions.git`
	-  Change to directory: `cd examples-advanced-ignite-extensions`


**Start ignite server test-node 1**: Open a terminal from `examples-advanced-ignite-extensions` directory, change to `ignite-server-node-test` directory and run:

```
mvn exec:java -Dexec.mainClass="com.hawkore.ignite.examples.IgniteServerNode" -Dexec.classpathScope=compile -DnodeName=node1
```

**Start ignite server test-node 2**: Open a terminal from `examples-advanced-ignite-extensions` directory, change to `ignite-server-node-test` directory and run:

```
mvn exec:java -Dexec.mainClass="com.hawkore.ignite.examples.IgniteServerNode" -Dexec.classpathScope=compile -DnodeName=node2
```

### Examples for Hawkore's Apache Ignite Advanced Indexing

This maven project contains source code for samples used on [Hawkore's Apache Ignite Advanced Indexing
](https://docs.hawkore.com/apache-ignite-advanced-indexing) documentation.

**Populate caches (once)**: Open a terminal from `examples-advanced-ignite-extensions` directory, change to `examples-advanced-ignite-indexing` directory and run:
```
mvn exec:java -Dexec.mainClass="com.hawkore.ignite.examples.PopulateProfiles" -Dexec.classpathScope=compile -DnodeName=populator

mvn exec:java -Dexec.mainClass="com.hawkore.ignite.examples.PopulatePois" -Dexec.classpathScope=compile -DnodeName=populator

mvn exec:java -Dexec.mainClass="com.hawkore.ignite.examples.PopulateTweets" -Dexec.classpathScope=compile -DnodeName=populator
```

**Start SQL query runner**: Open a terminal from `examples-advanced-ignite-extensions` directory, change to `examples-advanced-ignite-indexing` directory and run:
```
mvn exec:java -Dexec.mainClass="com.hawkore.ignite.examples.SQLQueryRunner" -Dexec.classpathScope=compile -DnodeName=query
```


Run SQL samples located in `examples-advanced-ignite-indexing/sample_sql_scripts` directory on terminal running SQL query runner.