package edu.postech.csed332.homework2;

import java.util.*;
import java.util.stream.Collectors;

/**
 * A Boolean expression whose top-level operator is && (and).
 */
public class Conjunction implements Exp {
    private final List<Exp> subexps;

    /**
     * Builds a conjunction of a given list of Boolean expressions
     *
     * @param exps
     */
    public Conjunction(Exp... exps) {
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

        if(!(e instanceof Conjunction)) {
            return e.evaluate(assignment);
        }

        Exp left = ((Conjunction) e).getSubexps().get(0);
        Exp right = ((Conjunction) e).getSubexps().get(1);
        return left.evaluate(assignment) && right.evaluate(assignment);
    }

    @Override
    public Exp simplify() {
        // TODO: implement this
        Exp left = subexps.get(0);
        Exp right = subexps.get(1);

        // Identity and idempotent laws
        if (left instanceof Constant && ((Constant) left).getValue()) {
            return right.simplify();
        }
        if (right instanceof Constant && ((Constant) right).getValue()) {
            return left.simplify();
        }

        Exp sL = left.simplify();
        Exp sR = right.simplify();
        if (sL.toString().equals(sR.toString())) {
            return sL;
        }

        // Domination and negation laws
        if (sL instanceof Constant && !((Constant) sL).getValue()) {
            return new Constant(false);
        }
        if (sR instanceof Constant && !((Constant) sR).getValue()) {
            return new Constant(false);
        }

        if (sL instanceof Negation) {
            Exp sLC = ((Negation) sL).getSubexp().simplify();
            if (sLC.toString().equals(sR.toString())) {
                return new Constant(false);
            }
        }
        if (sR instanceof Negation) {
            Exp sRC = ((Negation) sR).getSubexp().simplify();
            if (sRC.toString().equals(sL.toString())) {
                return new Constant(false);
            }
        }

        // Absorption laws
        if (sL instanceof Disjunction) {
            Exp sLL = ((Disjunction) sL).getSubexps().get(0).simplify();
            Exp sLR = ((Disjunction) sL).getSubexps().get(1).simplify();
            if (sR.toString().equals(sLL.toString()) || sR.toString().equals(sLR.toString())) {
                return sR;
            }
        }
        if (sR instanceof Disjunction) {
            Exp sRL = ((Disjunction) sR).getSubexps().get(0).simplify();
            Exp sRR = ((Disjunction) sR).getSubexps().get(1).simplify();
            if (sL.toString().equals(sRL.toString()) || sL.toString().equals(sRR.toString())) {
                return sL;
            }
        }

        // Distributive laws
        if (sR instanceof Variable && sL instanceof Disjunction) {
            Exp exp = new Disjunction(
                    new Conjunction(sR, ((Disjunction) sL).getSubexps().get(0)),
                    new Conjunction(sR, ((Disjunction) sL).getSubexps().get(1))
            );
            return exp.simplify();
        }
        if (sL instanceof Variable && sR instanceof Disjunction) {
            Exp exp = new Disjunction(
                    new Conjunction(sL, ((Disjunction) sR).getSubexps().get(0)),
                    new Conjunction(sL, ((Disjunction) sR).getSubexps().get(1))
            );
            return exp.simplify();
        }

        // Plus alpha
        if (sL instanceof Variable && sR instanceof Conjunction) {
            int leftId = ((Variable) sL).getIdentifier();
            Exp rL = ((Conjunction) sR).getSubexps().get(0);
            if (rL instanceof Negation && ((Negation) rL).getSubexp() instanceof Variable) {
                if (leftId == ((Variable) ((Negation) rL).getSubexp()).getIdentifier()) {
                    return new Constant(false);
                }
            }
            Exp rR = ((Conjunction) sR).getSubexps().get(1);
            if (rR instanceof Negation && ((Negation) rR).getSubexp() instanceof Variable) {
                if (leftId == ((Variable) ((Negation) rR).getSubexp()).getIdentifier()) {
                    return new Constant(false);
                }
            }
        }
        if (sR instanceof Variable && sL instanceof Conjunction) {
            int rightId = ((Variable) sR).getIdentifier();
            Exp lL = ((Conjunction) sL).getSubexps().get(0);
            if (lL instanceof Negation && ((Negation) lL).getSubexp() instanceof Variable) {
                if (rightId == ((Variable) ((Negation) lL).getSubexp()).getIdentifier()) {
                    return new Constant(false);
                }
            }
            Exp lR = ((Conjunction) sL).getSubexps().get(1);
            if (lR instanceof Negation && ((Negation) lR).getSubexp() instanceof Variable) {
                if (rightId == ((Variable) ((Negation) lR).getSubexp()).getIdentifier()) {
                    return new Constant(false);
                }
            }
        }

        return new Conjunction(sL, sR);
    }

    @Override
    public String toString() {
        return "(" + subexps.stream().map(i -> i.toString()).collect(Collectors.joining(" && ")) + ")";
    }
}