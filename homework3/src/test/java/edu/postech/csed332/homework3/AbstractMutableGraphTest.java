package edu.postech.csed332.homework3;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * An abstract test class for MutableGraph with blackbox test methods
 *
 * @param <V> type of vertices
 * @param <G> type of Graph
 */
@Disabled
public abstract class AbstractMutableGraphTest<V extends Comparable<V>, G extends MutableGraph<V>> {

    G graph;
    V v1, v2, v3, v4, v5, v6, v7, v8;

    abstract boolean checkInv();    // call checkInv of graph

    @Test
    void testAddVertex() {
        Assertions.assertTrue(graph.addVertex(v1));
        Assertions.assertTrue(graph.containsVertex(v1));
        Assertions.assertTrue(checkInv());
    }

    @Test
    void testAddDuplicateVertices() {
        Assertions.assertTrue(graph.addVertex(v6));
        Assertions.assertTrue(graph.addVertex(v7));
        Assertions.assertFalse(graph.addVertex(v6));
        Assertions.assertTrue(graph.containsVertex(v6));
        Assertions.assertTrue(graph.containsVertex(v7));
        Assertions.assertTrue(checkInv());
    }

    @Test
    void testFindReachableVertices() {
        graph.addEdge(v1, v1);
        graph.addEdge(v1, v2);
        graph.addEdge(v3, v1);
        graph.addEdge(v3, v2);
        graph.addVertex(v4);

        Assertions.assertEquals(graph.findReachableVertices(v1), Set.of(v1, v2));
        Assertions.assertEquals(graph.findReachableVertices(v2), Set.of(v2));
        Assertions.assertEquals(graph.findReachableVertices(v3), Set.of(v1, v2, v3));
        Assertions.assertEquals(graph.findReachableVertices(v4), Set.of(v4));
        Assertions.assertEquals(graph.findReachableVertices(v5), Collections.emptySet());
        Assertions.assertTrue(checkInv());
    }

    // TODO: write black-box test cases for each method of MutableGraph with respect to
    //  the specification, including the methods of Graph that MutableGraph extends.

    @Test
    void testContainsVertex() {
        graph.addVertex(v1);
        Assertions.assertTrue(graph.containsVertex(v1));
        Assertions.assertTrue(!graph.containsVertex(v2));
    }

    @Test
    void testContainsEdge() {
        graph.addVertex(v1);
        graph.addVertex(v2);
        graph.addEdge(v1, v2);
        Assertions.assertTrue(graph.containsEdge(v1, v2));
        Assertions.assertTrue(!graph.containsEdge(v2, v1));
    }

    @Test
    void testGetSources() {
        graph.addVertex(v1);
        graph.addVertex(v2);
        graph.addEdge(v1, v2);
        Assertions.assertEquals(graph.getSources(v1), Set.of());
        Assertions.assertEquals(graph.getSources(v2), Set.of(v1));
    }

    @Test
    void testGetTargets() {
        graph.addVertex(v1);
        graph.addVertex(v2);
        graph.addEdge(v1, v2);
        Assertions.assertEquals(graph.getTargets(v1), Set.of(v2));
        Assertions.assertEquals(graph.getTargets(v2), Set.of());
    }

    @Test
    void testGetVertices() {
        Assertions.assertEquals(graph.getVertices(), Set.of());
        graph.addVertex(v1);
        graph.addVertex(v2);
        graph.addEdge(v1, v2);
        Assertions.assertEquals(graph.getVertices(), Set.of(v1, v2));
    }

    @Test
    void testGetEdges() {
        Assertions.assertEquals(graph.getEdges(), Set.of());
        graph.addVertex(v1);
        graph.addVertex(v2);
        graph.addEdge(v1, v2);

        Set<Edge<V>> set;
        set = graph.getEdges();
        Iterator iter = set.iterator();
        iter.hasNext();
        Edge<V> e = (Edge<V>) iter.next();
        Assertions.assertEquals(e.getSource(), v1);
        Assertions.assertEquals(e.getTarget(), v2);
    }

    @Test
    void testRemoveVertex() {
        graph.addVertex(v1);
        graph.addVertex(v2);
        graph.addEdge(v1, v2);
        graph.addEdge(v2, v1);

        Assertions.assertTrue(graph.removeVertex(v2));
        Assertions.assertTrue(!graph.removeVertex(v2));
        Assertions.assertTrue(graph.containsVertex(v1));
        Assertions.assertTrue(!graph.containsVertex(v2));
        Assertions.assertTrue(!graph.containsEdge(v1, v2));
        Assertions.assertTrue(!graph.containsEdge(v2, v1));
    }

    @Test
    void testAddEdge() {
        graph.addVertex(v1);
        graph.addVertex(v2);
        Assertions.assertTrue(graph.addEdge(v1, v2));
        Assertions.assertTrue(!graph.addEdge(v1, v2));
        Assertions.assertTrue(graph.addEdge(v1, v3));
        Assertions.assertTrue(graph.addEdge(v3, v1));
    }

    @Test
    void testRemoveEdge() {
        graph.addVertex(v1);
        graph.addVertex(v2);
        Assertions.assertTrue(graph.addEdge(v1, v2));
        Assertions.assertTrue(graph.removeEdge(v1, v2));
        Assertions.assertTrue(!graph.removeEdge(v1, v2));
    }

}
