package edu.postech.csed332.homework3;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.Set;

/**
 * An abstract test class for MutableTree with blackbox test methods
 *
 * @param <V> type of vertices
 * @param <T> type of Tree
 */
@Disabled
public abstract class AbstractMutableTreeTest<V extends Comparable<V>, T extends MutableTree<V>> {

    T tree;
    V v1, v2, v3, v4, v5, v6, v7, v8;

    abstract boolean checkInv();    // call checkInv of tree


    @Test
    void testGetDepthRoot() {
        tree.addVertex(v1);
        Assertions.assertEquals(tree.getDepth(v1), 0);
    }

    @Test
    void testGetDepthTwo() {
        tree.addVertex(v1);
        tree.addEdge(v1, v2);
        Assertions.assertEquals(tree.getDepth(v2), 1);
    }

    @Test
    void testGetDepthNoRoot() {
        Assertions.assertThrows(IllegalStateException.class, () -> tree.getDepth(v1));
    }

    @Test
    void testGetDepthNoVertex() {
        Assertions.assertTrue(tree.addVertex(v1));
        Assertions.assertThrows(IllegalArgumentException.class, () -> tree.getDepth(v2));
    }

    // TODO: write black-box test casess for each method of MutableTree with respect to
    //  the specification, including the methods of Tree that MutableTree extends.

    @Test
    void testGetRoot() {
        Assertions.assertEquals(tree.getRoot(), Optional.empty());
        tree.addVertex(v1);
        Assertions.assertEquals(tree.getRoot(), Optional.of(v1));
    }

    @Test
    void testGetHeight() {
        Assertions.assertThrows(IllegalStateException.class, () -> tree.getHeight());
        tree.addVertex(v1);
        Assertions.assertEquals(tree.getHeight(), 0);
        tree.addEdge(v1, v2);
        Assertions.assertEquals(tree.getHeight(), 1);
    }

    @Test
    void testGetChildren() {
        Assertions.assertEquals(tree.getChildren(v1), Set.of());
        tree.addVertex(v1);
        Assertions.assertEquals(tree.getChildren(v1), Set.of());
        tree.addEdge(v1, v2);
        Assertions.assertEquals(tree.getChildren(v1), Set.of(v2));
        tree.addEdge(v1, v3);
        Assertions.assertEquals(tree.getChildren(v1), Set.of(v2, v3));
    }

    @Test
    void testGetParent() {
        tree.addVertex(v1);
        Assertions.assertEquals(tree.getParent(v1), Optional.empty());
        tree.addEdge(v1, v2);
        Assertions.assertEquals(tree.getParent(v2), Optional.of(v1));
    }

    @Test
    void testAddVertex() {
        Assertions.assertTrue(tree.addVertex(v1));
        Assertions.assertTrue(!tree.addVertex(v2));
    }

    @Test
    void testRemoveVertex() {
        tree.addVertex(v1);
        Assertions.assertTrue(tree.removeVertex(v1));
        Assertions.assertTrue(!tree.removeVertex(v2));

        tree.addVertex(v1);
        tree.addEdge(v1, v2);
        tree.addEdge(v2, v3);
        Assertions.assertTrue(tree.removeVertex(v1));
        Assertions.assertEquals(tree.getRoot(), Optional.empty());
    }

    @Test
    void testAddEdge() {
        tree.addVertex(v1);
        Assertions.assertTrue(tree.addEdge(v1, v2));
        Assertions.assertTrue(tree.addEdge(v2, v3));
        Assertions.assertTrue(!tree.addEdge(v1, v3));
        Assertions.assertTrue(!tree.addEdge(v1, v2));
        Assertions.assertTrue(!tree.addEdge(v4, v3));
    }

    @Test
    void testRemoveEdge() {
        Assertions.assertTrue(!tree.removeEdge(v1, v2));
        tree.addVertex(v1);
        tree.addEdge(v1, v2);
        Assertions.assertTrue(tree.removeEdge(v1, v2));
        tree.addEdge(v1, v2);
        tree.addEdge(v2, v3);
        Assertions.assertTrue(tree.removeEdge(v1, v2));
        Assertions.assertTrue(!tree.containsEdge(v2, v3));
    }
}
