package edu.postech.csed332.homework2;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * A Boolean expression whose top-level operator is ! (not).
 */
public class Negation implements Exp {
    private final Exp subexp;

    /**
     * Builds a negated expression of a given Boolean expression.
     *
     * @param exp a Boolean expression
     */
    public Negation(Exp exp) {
        subexp = exp;
    }

    /**
     * Returns the immediate sub-expression of this expression.
     *
     * @return a sub-expression
     */
    public Exp getSubexp() {
        return subexp;
    }

    @Override
    public Set<Integer> vars() {
        // TODO: implement this
        return new HashSet<>(subexp.vars());
    }

    @Override
    public Boolean evaluate(Map<Integer, Boolean> assignment) {
        // TODO: implement this
        Exp exp = subexp.simplify();
        return !exp.evaluate(assignment);
    }

    @Override
    public Exp simplify() {
        // TODO: implement this
        Exp exp = null;
        if (subexp instanceof Conjunction) {
            exp = new Disjunction(
                    new Negation(((Conjunction) subexp).getSubexps().get(0)),
                    new Negation(((Conjunction) subexp).getSubexps().get(1)));
            return exp.simplify();
        }

        if (subexp instanceof Disjunction) {
            exp = new Conjunction(
                    new Negation(((Disjunction) subexp).getSubexps().get(0)),
                    new Negation(((Disjunction) subexp).getSubexps().get(1)));
            return exp.simplify();
        }

        if (subexp instanceof Negation) {
            exp = new Disjunction(((Negation) subexp).getSubexp(), new Constant(false));
            return exp.simplify();
        }

        return this;
    }

    @Override
    public String toString() {
        return "(! " + subexp + ")";
    }
}
