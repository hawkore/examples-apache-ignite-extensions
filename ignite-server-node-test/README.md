## Start server nodes for testing Hawkore's Apache Ignite Extensions

**Start ignite server test-node 1**: Open a terminal from `examples-apache-ignite-extensions` directory, change to `ignite-server-node-test` directory and run:

```
mvn compile exec:java -Dexec.mainClass="com.hawkore.ignite.examples.IgniteServerNode" -Dexec.classpathScope=compile -DnodeName=node1
```

**Start ignite server test-node 2**: Open a terminal from `examples-apache-ignite-extensions` directory, change to `ignite-server-node-test` directory and run:

```
mvn compile exec:java -Dexec.mainClass="com.hawkore.ignite.examples.IgniteServerNode" -Dexec.classpathScope=compile -DnodeName=node2
```


Visit [Hawkore's home page](https://www.hawkore.com).