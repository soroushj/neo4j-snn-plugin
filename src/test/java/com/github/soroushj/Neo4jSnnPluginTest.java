package com.github.soroushj;

import org.junit.Rule;
import org.junit.Test;
import org.junit.Assert;
import org.neo4j.harness.junit.Neo4jRule;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.Config;
import org.neo4j.driver.v1.Session;

public class Neo4jSnnPluginTest {
  @Rule
  public Neo4jRule neo4j = new Neo4jRule().withFunction(Neo4jSnnPlugin.class);

  @Test
  public void testSnn() {
    try (Driver driver = GraphDatabase.driver(
        neo4j.boltURI(), Config.build().withoutEncryption().toConfig())) {
      Session session = driver.session();
      session.run(
          "CREATE " +
          "(a:A), " +
          "(b:B), " +
          "(n1), " +
          "(n2), " +
          "(n3), " +
          "(a)-[:R]->(n1)<-[:R]-(b), " +
          "(a)-[:R]->(n2)<-[:R]-(b), " +
          "(a)-[:R]->(n3)");
      long snn = session.run(
          "MATCH (a:A), (b:B) " +
          "RETURN com.github.soroushj.snn(a, b) AS snn")
          .single().get("snn").asLong();
      Assert.assertEquals(2, snn);
    }
  }

  @Test
  public void testZeroSnn() {
    try (Driver driver = GraphDatabase.driver(
        neo4j.boltURI(), Config.build().withoutEncryption().toConfig())) {
      Session session = driver.session();
      session.run(
          "CREATE " +
          "(a:A), " +
          "(b:B)");
      long snn = session.run(
          "MATCH (a:A), (b:B) " +
          "RETURN com.github.soroushj.snn(a, b) AS snn")
          .single().get("snn").asLong();
      Assert.assertEquals(0, snn);
    }
  }
}
