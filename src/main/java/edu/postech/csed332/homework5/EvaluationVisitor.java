package edu.postech.csed332.homework5;

import edu.postech.csed332.homework5.expression.BinaryExp;
import edu.postech.csed332.homework5.expression.NumberExp;
import edu.postech.csed332.homework5.expression.VariableExp;

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
        switch (operator) {
            case "+":
                return l+r;
            case "-":
                return l-r;
            case "*":
                return l*r;
            case "/":
                return l/r;
            case "^":
                return Math.pow(l, r);
        }
        System.out.println("INVALID ARGUMENT ERROR!");
        return null;
    }
}
