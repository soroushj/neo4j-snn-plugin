package com.github.soroushj;

import java.util.Map;
import java.util.HashMap;
import java.lang.RuntimeException;
import org.neo4j.procedure.UserFunction;
import org.neo4j.procedure.Description;
import org.neo4j.procedure.Name;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Result;

public class Neo4jSnnPlugin {
  @UserFunction
  @Description("Returns the number of shared nearest neighbors.")
  public long snn(@Name("Node A") Node nodeA, @Name("Node B") Node nodeB) {
    if (nodeA == null || nodeB == null) {
      throw new RuntimeException("Nodes must not be null.");
    }
    Map<String, Object> params = new HashMap<>();
    params.put("idA", nodeA.getId());
    params.put("idB", nodeB.getId());
    try (Result result = nodeA.getGraphDatabase().execute(
        "MATCH (a)--(n)--(b) " +
        "WHERE id(a) = $idA AND id(b) = $idB " +
        "RETURN count(n) AS snn", params)) {
      return (long)result.next().get("snn");
    } catch (Exception e) {
      throw new RuntimeException(e.getMessage());
    }
  }
}
