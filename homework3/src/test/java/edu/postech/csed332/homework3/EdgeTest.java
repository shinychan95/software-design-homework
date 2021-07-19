package edu.postech.csed332.homework3;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class EdgeTest {
    @Test
    void testGetSourceAndTarget() {
        Edge e = new Edge(1,2);
        Assertions.assertEquals(e.getSource(), 1);
        Assertions.assertEquals(e.getTarget(), 2);
        Assertions.assertNotEquals(e.getSource(), 2);
        Assertions.assertNotEquals(e.getTarget(), 1);
    }


    @Test
    void testCompareTo() {
        Edge e1 = new Edge(1,2);
        Edge e2 = new Edge(1,2);
        Edge e3 = new Edge(2,3);
        Assertions.assertEquals(e1.compareTo(e2), 0);
        Assertions.assertEquals(e2.compareTo(e3), -1);
    }

    @Test
    void testToString() {
        Edge e = new Edge(1,2);
        Assertions.assertEquals(e.toString(), "(1,2)");
    }

    @Test
    void testEquals() {
        Edge e1 = new Edge(1,2);
        Edge e2 = new Edge(1,2);
        Edge e3 = new Edge(2,3);
        Assertions.assertEquals(e1.equals(e2), true);
        Assertions.assertEquals(e2.equals(e3), false);
    }


}
