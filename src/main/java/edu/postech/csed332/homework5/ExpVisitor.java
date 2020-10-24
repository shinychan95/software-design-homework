package edu.postech.csed332.homework5;

import edu.postech.csed332.homework5.expression.BinaryExp;
import edu.postech.csed332.homework5.expression.NumberExp;
import edu.postech.csed332.homework5.expression.VariableExp;

/**
 * A visitor interface for expressions
 *
 * @param <T> type of return values
 */
public interface ExpVisitor<T> {
    // TODO define the visit methods for Exp
    public T visit(NumberExp cur);
    public T visit(VariableExp cur);
    public T visit(BinaryExp curExp, String operator);
}
