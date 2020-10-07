package edu.postech.csed332.homework3;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StringAdjacencyMutableGraphTest extends AbstractMutableGraphTest<String, AdjacencyListGraph<String>> {

    @BeforeEach
    void setUp() {
        graph = new AdjacencyListGraph<>();
        v1 = "1";
        v2 = "2";
        v3 = "3";
        v4 = "4";
        v5 = "5";
        v6 = "6";
        v7 = "7";
        v8 = "8";
    }

    @Override
    boolean checkInv() {
        return graph.checkInv();
    }

    // TODO: write more white-box test cases to achieve more code coverage, if needed.
    // You do not need to add more test methods, if you tests already meet the desired coverage.

    @Test
    void testCheckInv() {
        Assertions.assertTrue(graph.addVertex(v1));
        Assertions.assertTrue(graph.addEdge(v1, v2));
        Assertions.assertTrue(!graph.addEdge(v1, v2));
        Assertions.assertTrue(graph.addEdge(v1, v3));
        Assertions.assertTrue(graph.addEdge(v1, v4));
        Assertions.assertTrue(graph.addEdge(v2, v5));
        Assertions.assertTrue(graph.addEdge(v3, v6));
        Assertions.assertTrue(graph.checkInv());
    }
}
