package edu.postech.csed332.homework2;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ExpTest {

    @Test
    void testParserOK() {
        Exp exp = ExpParser.parse("p1 || p2 && ! p3 || true");
        assertEquals(exp.toString(), "((p1 || (p2 && (! p3))) || true)");
    }

    @Test
    void testParserError() {
        assertThrows(IllegalStateException.class, () -> {
            Exp exp = ExpParser.parse("p1 || p2 && ! p0 || true");
        });
    }

    /*
     * TODO: add  test methods to achieve at least 80% branch coverage of the classes:
     *  Conjunction, Constant, Disjunction, Negation, Variable.
     * Each test method should have appropriate JUnit assertions to test a single behavior.
     * You should not add arbitrary code to test methods to just increase coverage.
     */

    @Test
    void testToString() {
        Exp exp = ExpParser.parse("p1 && ! p2 && ! p2 || p1");
        assertEquals(exp.toString(), "(((p1 && (! p2)) && (! p2)) || p1)");
    }

    @Test
    void testIdentityAndIdempotentLaws() {
        Exp exp = ExpParser.parse("p1 && true");
        assertEquals(exp.simplify().toString(), "p1");

        exp = ExpParser.parse("true && p1");
        assertEquals(exp.simplify().toString(), "p1");

        exp = ExpParser.parse("p1 || false");
        assertEquals(exp.simplify().toString(), "p1");

        exp = ExpParser.parse("false || p1");
        assertEquals(exp.simplify().toString(), "p1");

        exp = ExpParser.parse("p1 || false || p2");
        assertEquals(exp.simplify().toString(), "(p1 || p2)");

        exp = ExpParser.parse("p1 && p1");
        assertEquals(exp.simplify().toString(), "p1");

        exp = ExpParser.parse("p1 || p1");
        assertEquals(exp.simplify().toString(), "p1");

        exp = ExpParser.parse("p1 && p2 && true");
        assertEquals(exp.simplify().toString(), "(p1 && p2)");

        exp = ExpParser.parse("p1 && p2 && false");
        assertEquals(exp.simplify().toString(), "false");

        exp = ExpParser.parse("p1 && p2 && p3 && p4");
        assertEquals(exp.simplify().toString(), "(((p1 && p2) && p3) && p4)");

        exp = ExpParser.parse("p1 && p1 && p2");
        assertEquals(exp.simplify().toString(), "(p1 && p2)");

        exp = ExpParser.parse("( p1 && p2 ) && ( p1 && p2 )");
        assertEquals(exp.simplify().toString(), "(p1 && p2)");

        exp = ExpParser.parse("p1 && ( ( p2 && true ) && p2 )");
        assertEquals(exp.simplify().toString(), "(p1 && p2)");

        exp = ExpParser.parse("( ( p1 && true) && ( ( p2 && true ) && p2 ) )");
        assertEquals(exp.simplify().toString(), "(p1 && p2)");

        exp = ExpParser.parse("! ( ! p1 ) && ! ( ! p1 )");
        assertEquals(exp.simplify().toString(), "p1");

        exp = ExpParser.parse("! ( ! p1 ) || ! ( ! p1 )");
        assertEquals(exp.simplify().toString(), "p1");
    }

    @Test
    void testDominationAndNegationLaws() {
        Exp exp = ExpParser.parse("p1 && false");
        assertEquals(exp.simplify().toString(), "false");

        exp = ExpParser.parse("false && p1");
        assertEquals(exp.simplify().toString(), "false");

        exp = ExpParser.parse("p1 || true");
        assertEquals(exp.simplify().toString(), "true");

        exp = ExpParser.parse("true || p1");
        assertEquals(exp.simplify().toString(), "true");

        exp = ExpParser.parse("p1 && ! p1");
        assertEquals(exp.simplify().toString(), "false");

        exp = ExpParser.parse("(p1 && p2 && p3) || (p1 && p2 && p3)");
        assertEquals(exp.simplify().toString(), "((p1 && p2) && p3)");

        exp = ExpParser.parse("! p1 && p1");
        assertEquals(exp.simplify().toString(), "false");

        exp = ExpParser.parse("p1 || ! p1");
        assertEquals(exp.simplify().toString(), "true");

        exp = ExpParser.parse("( p1 || ! p1 ) || false");
        assertEquals(exp.simplify().toString(), "true");

        exp = ExpParser.parse("false || ( p1 || ! p1 )");
        assertEquals(exp.simplify().toString(), "true");
    }

    @Test
    void testDeMorgansLaws() {
        Exp exp = ExpParser.parse("! ( p1 && p2 )");
        assertEquals(exp.simplify().toString(), "((! p1) || (! p2))");

        exp = ExpParser.parse("! ( ! p1 && ! p2 )");
        assertEquals(exp.simplify().toString(), "(p1 || p2)");

        exp = ExpParser.parse("! ( p1 || p2 )");
        assertEquals(exp.simplify().toString(), "((! p1) && (! p2))");

        exp = ExpParser.parse("! ( ! p1 || ! p2 )");
        assertEquals(exp.simplify().toString(), "(p1 && p2)");

        exp = ExpParser.parse("( ! p1 || ! p1 )");
        assertEquals(exp.simplify().toString(), "(! p1)");

        exp = ExpParser.parse("( ! p1 && ! p1 )");
        assertEquals(exp.simplify().toString(), "(! p1)");
    }

    @Test
    void testAbsirotionLaw() {
        Exp exp = ExpParser.parse("p1 || ( p1 && p2 )");
        assertEquals(exp.simplify().toString(), "p1");

        exp = ExpParser.parse("( p1 && p2 ) || p1");
        assertEquals(exp.simplify().toString(), "p1");

        exp = ExpParser.parse("p1 && ( p1 || p2 )");
        assertEquals(exp.simplify().toString(), "p1");

        exp = ExpParser.parse("( p1 || p2 ) && p1");
        assertEquals(exp.simplify().toString(), "p1");
    }

    @Test
    void testDoubleNegationLaw() {
        Exp exp = ExpParser.parse("! ( ! p1 )");
        assertEquals(exp.simplify().toString(), "p1");

        exp = ExpParser.parse("! ( ! ( ! p2 ) )");
        assertEquals(exp.simplify().toString(), "(! p2)");
    }

    @Test
    void testDistributiveLaws() {
        Exp exp = ExpParser.parse("p1 || ( p2 && p3 )");
        assertEquals(exp.simplify().toString(), "((p1 || p2) && (p1 || p3))");

        exp = ExpParser.parse("p1 && ( p2 || p3 )");
        assertEquals(exp.simplify().toString(), "((p1 && p2) || (p1 && p3))");

        exp = ExpParser.parse("( p2 && p3 ) || p1");
        assertEquals(exp.simplify().toString(), "((p1 || p2) && (p1 || p3))");

        exp = ExpParser.parse("( p2 || p3 ) && p1");
        assertEquals(exp.simplify().toString(), "((p1 && p2) || (p1 && p3))");

    }

    @Test
    void testPlusAlphaLaws() {
        Exp exp = ExpParser.parse("p1 && ( p2 && ! p1 )");
        assertEquals(exp.simplify().toString(), "false");

        exp = ExpParser.parse("p1 && ( ! p1 && p2 )");
        assertEquals(exp.simplify().toString(), "false");

        exp = ExpParser.parse("( p2 && ! p1 ) && p1");
        assertEquals(exp.simplify().toString(), "false");

        exp = ExpParser.parse("( ! p1 && p2 ) && p1");
        assertEquals(exp.simplify().toString(), "false");

        exp = ExpParser.parse("( p1 && true) && ( p2 && ! ( ! p1 && ! p2 ) )");
        assertEquals(exp.toString(), "((p1 && true) && (p2 && (! ((! p1) && (! p2)))))");
        assertEquals(exp.simplify().toString(), "(p1 && p2)");

        exp = ExpParser.parse("( true && p1) && ( ! ( ! p1 && ! p2 ) && p2 )");
        assertEquals(exp.toString(), "((true && p1) && ((! ((! p1) && (! p2))) && p2))");
        assertEquals(exp.simplify().toString(), "(p1 && p2)");

        exp = ExpParser.parse("( p2 && ! ( ! p1 && ! p2 ) ) && ( p1 && true )");
        assertEquals(exp.simplify().toString(), "(p2 && p1)");
    }

    @Test
    void testVar() {
        Exp exp = ExpParser.parse("p1");
        assertEquals(exp.vars(), new HashSet<>(Arrays.asList(1)));

        exp = ExpParser.parse("true");
        assertEquals(exp.vars(), new HashSet<>(Arrays.asList()));

        exp = ExpParser.parse("! p1");
        assertEquals(exp.vars(), new HashSet<>(Arrays.asList(1)));

        exp = ExpParser.parse("p1 && p2");
        assertEquals(exp.vars(), new HashSet<>(Arrays.asList(1, 2)));

        exp = ExpParser.parse("p1 || p2");
        assertEquals(exp.vars(), new HashSet<>(Arrays.asList(1, 2)));
    }

    @Test
    void testEvaluate() {
        Exp exp = ExpParser.parse("p1 && p2");
        assertEquals(exp.evaluate(Map.of(1, true, 2, true)), true);

        exp = ExpParser.parse("p1 || p2");
        assertEquals(exp.evaluate(Map.of(1, true, 2, false)), true);

        exp = ExpParser.parse("( p1 || p2 ) && ( p2 || ! p3 )");
        assertEquals(exp.evaluate(Map.of(1, true, 2, false, 3, false)), true);

        exp = ExpParser.parse("! p1");
        assertEquals(exp.evaluate(Map.of(1, true)), false);

        exp = ExpParser.parse("p1");
        assertEquals(exp.evaluate(Map.of(1, true)), true);

        exp = ExpParser.parse("!(p1 || p2)");
        assertEquals(exp.evaluate(Map.of(1, true, 2, false)), false);

        exp = ExpParser.parse("!(p1 && p2)");
        assertEquals(exp.evaluate(Map.of(1, true, 2, false)), true);

        exp = ExpParser.parse("(p1 && p1)");
        assertEquals(exp.evaluate(Map.of(1, true)), true);

        exp = ExpParser.parse("(p1 || p1)");
        assertEquals(exp.evaluate(Map.of(1, true)), true);

        exp = ExpParser.parse("(p1 && p1) && (p1 && p2)");
        assertEquals(exp.evaluate(Map.of(1, true, 2, true)), true);

        exp = ExpParser.parse("(p1 && p2) && (p3 || p4)");
        assertEquals(exp.evaluate(Map.of(1, true, 2, true, 3, false, 4, true)), true);
    }

}
