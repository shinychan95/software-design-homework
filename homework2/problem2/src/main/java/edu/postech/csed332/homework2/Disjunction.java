package edu.postech.csed332.homework2;

import java.util.*;
import java.util.stream.Collectors;

/**
 * A Boolean expression whose top-level operator is || (or).
 */
public class Disjunction implements Exp {
    private final List<Exp> subexps;

    /**
     * Builds a disjunction of a given list of Boolean expressions
     *
     * @param exps
     */
    public Disjunction(Exp... exps) {
        subexps = Arrays.asList(exps);
    }

    /**
     * Returns the immediate sub-expressions of this expression.
     *
     * @return a list of sub-expressions
     */
    public List<Exp> getSubexps() {
        return subexps;
    }

    @Override
    public Set<Integer> vars() {
        // TODO: implement this
        Set<Integer> s = new HashSet<>();
        for(Exp e: subexps){
            s.addAll(e.vars());
        }
        return s;
    }

    @Override
    public Boolean evaluate(Map<Integer, Boolean> assignment) {
        // TODO: implement this
        Exp e = this.simplify();

        if(!(e instanceof Disjunction)) {
            return e.evaluate(assignment);
        }

        Exp left = ((Disjunction) e).getSubexps().get(0);
        Exp right = ((Disjunction) e).getSubexps().get(1);
        return left.evaluate(assignment) || right.evaluate(assignment);
    }

    @Override
    public Exp simplify() {
        // TODO: implement this
        Exp left = subexps.get(0);
        Exp right = subexps.get(1);

        // Identity and idempotent laws
        if (left instanceof Constant && !((Constant) left).getValue()) {
            return right.simplify();
        }
        if (right instanceof Constant && !((Constant) right).getValue()) {
            return left.simplify();
        }

        Exp sL = left.simplify();
        Exp sR = right.simplify();
        if (sL.toString().equals(sR.toString())) {
            return sL;
        }

        // Domination and negation laws
        if (sL instanceof Constant && ((Constant) sL).getValue()) {
            return new Constant(true);
        }
        if (sR instanceof Constant && ((Constant) sR).getValue()) {
            return new Constant(true);
        }

        if (sL instanceof Negation) {
            Exp sLC = ((Negation) sL).getSubexp().simplify();
            if (sLC.toString().equals(sR.toString())) {
                return new Constant(true);
            }
        }
        if (sR instanceof Negation) {
            Exp sRC = ((Negation) sR).getSubexp().simplify();
            if (sRC.toString().equals(sL.toString())) {
                return new Constant(true);
            }
        }

        // Absorption laws
        if (sL instanceof Conjunction) {
            Exp sLL = ((Conjunction) sL).getSubexps().get(0).simplify();
            Exp sLR = ((Conjunction) sL).getSubexps().get(1).simplify();
            if (sR.toString().equals(sLL.toString()) || sR.toString().equals(sLR.toString())) {
                return sR;
            }
        }
        if (sR instanceof Conjunction) {
            Exp sRL = ((Conjunction) sR).getSubexps().get(0).simplify();
            Exp sRR = ((Conjunction) sR).getSubexps().get(1).simplify();
            if (sL.toString().equals(sRL.toString()) || sL.toString().equals(sRR.toString())) {
                return sL;
            }
        }

        // Distributive laws
        if (sR instanceof Variable && sL instanceof Conjunction) {
            Exp exp = new Conjunction(
                    new Disjunction(sR, ((Conjunction) sL).getSubexps().get(0)),
                    new Disjunction(sR, ((Conjunction) sL).getSubexps().get(1))
            );
            return exp.simplify();
        }
        if (sL instanceof Variable && sR instanceof Conjunction) {
            Exp exp = new Conjunction(
                    new Disjunction(sL, ((Conjunction) sR).getSubexps().get(0)),
                    new Disjunction(sL, ((Conjunction) sR).getSubexps().get(1))
            );
            return exp.simplify();
        }

        return new Disjunction(sL, sR);
    }



    @Override
    public String toString() {
        return "(" + subexps.stream().map(i -> i.toString()).collect(Collectors.joining(" || ")) + ")";
    }
}