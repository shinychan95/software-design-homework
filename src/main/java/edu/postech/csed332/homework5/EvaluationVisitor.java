package edu.postech.csed332.homework5;

import edu.postech.csed332.homework5.expression.*;

import java.util.Map;
import java.util.logging.Logger;

/**
 * A visitor to evaluate a value of an expression, given a valuation for each variable
 */
public class EvaluationVisitor implements ExpVisitor<Double> {
    private final Map<Integer, Double> valuation;

    public EvaluationVisitor(Map<Integer, Double> valuation) {
        this.valuation = valuation;
    }

    // TODO write and implement the visitor methods for EvaluationVisitor, satisfying
    //  the specification of Exp.eval.
    @Override
    public Double visit(NumberExp cur) {
        return cur.getValue();
    }

    @Override
    public Double visit(VariableExp cur){
        return valuation.get(cur.getName());
    }

    @Override
    public Double visit(BinaryExp curExp, String operator) {
        Double l = curExp.getLeft().accept(this);
        Double r = curExp.getRight().accept(this);

        if (operator.equals("+")) return l+r;
        else if (operator.equals("-")) return l-r;
        else if (operator.equals("*")) return l*r;
        else if (operator.equals("/")) return l/r;
        else if (operator.equals("^")) return Math.pow(l, r);
        else {
            System.out.println("Invalid Argument Error Occures");
            return null;
        }
    }
}
