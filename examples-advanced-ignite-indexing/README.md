## Samples for Hawkore's Apache Ignite Advanced Indexing

This maven project contains sample source code used on [Hawkore's Apache Ignite Advanced Indexing
](https://docs.hawkore.com/private/apache-ignite-advanced-indexing) documentation.

**NOTE**: You must start Apache Ignite Servers before running this samples. See [README.md](../ignite-server-node-test/README.md) for more info.

### Steps for testing

- 1. **Populate caches (once)**: Open a terminal from `examples-advanced-ignite-extensions` directory, change to `examples-advanced-ignite-indexing` directory and run:
```
mvn exec:java -Dexec.mainClass="com.hawkore.ignite.examples.PopulateProfiles" -Dexec.classpathScope=compile -DnodeName=populator

mvn exec:java -Dexec.mainClass="com.hawkore.ignite.examples.PopulatePois" -Dexec.classpathScope=compile -DnodeName=populator

mvn exec:java -Dexec.mainClass="com.hawkore.ignite.examples.PopulateTweets" -Dexec.classpathScope=compile -DnodeName=populator
```

- 2. **Start SQL query runner**: This is a testing application to run SQL queries from a terminal. Open a terminal from `examples-advanced-ignite-extensions` directory, change to `examples-advanced-ignite-indexing` directory and run:
```
mvn exec:java -Dexec.mainClass="com.hawkore.ignite.examples.SQLQueryRunner" -Dexec.classpathScope=compile -DnodeName=query
```

Run SQL samples located in `examples-advanced-ignite-indexing/sample_sql_scripts` directory within SQL query runner.


Visit [Hawkore's home page](https://www.hawkore.com).