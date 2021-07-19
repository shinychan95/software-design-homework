package edu.postech.csed332.homework3;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DoubleParentPointerMutableTreeTest extends AbstractMutableTreeTest<Double, ParentPointerTree<Double>> {

    @BeforeEach
    void setUp() {
        tree = new ParentPointerTree<>();
        v1 = 1.9;
        v2 = 2.8;
        v3 = 3.7;
        v4 = 4.6;
        v5 = 5.5;
        v6 = 6.4;
        v7 = 7.3;
        v8 = 8.2;
    }

    @Override
    boolean checkInv() {
        return tree.checkInv();
    }

    // TODO: write more white-box test cases to achieve more code coverage, if needed.
    // You do not need to add more test methods, if you tests already meet the desired coverage.

    @Test
    void testToString() {
        Assertions.assertTrue(tree.addVertex(v1));
        Assertions.assertTrue(tree.addEdge(v1, v2));
        Assertions.assertEquals(tree.toString(), "[root: 1.9, vertex: {1.9, 2.8}, edge: {(null,1.9), (1.9,2.8)}]");
    }

    @Test
    void testCheckInv() {
        Assertions.assertTrue(tree.addVertex(v1));
        Assertions.assertTrue(tree.addEdge(v1, v2));
        Assertions.assertTrue(tree.addEdge(v1, v3));
        Assertions.assertTrue(!tree.addEdge(v2, v3));
        Assertions.assertTrue(tree.addEdge(v3, v4));
        Assertions.assertTrue(tree.addEdge(v2, v6));
        Assertions.assertTrue(tree.checkInv());
    }

}
