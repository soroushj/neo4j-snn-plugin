# Neo4j SNN Plugin
This is a user-defined function for Neo4j which returns the number of shared nearest neighbors for two nodes.
## Test & Build
```shell
gradle build
```
## Deploy
Copy the built jar file found in `./build/libs/` to the Neo4j `plugins` directory. For the default Neo4j `plugins` path on Ubuntu 16.04+:
```shell
sudo cp ./build/libs/neo4j-snn-plugin.jar /var/lib/neo4j/plugins/
```
After copying the jar file, you need to restart the Neo4j service. On Ubuntu 16.04+:
```shell
sudo service neo4j restart
```
## Usage
Now you can use the function `com.github.soroushj.snn` in Cypher queries:
```cypher
MATCH (a:User {username: "alice"}), (b:User {username: "bob"})
RETURN com.github.soroushj.snn(a, b)
```
