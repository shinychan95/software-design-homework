package edu.postech.csed332.homework5;

import edu.postech.csed332.homework5.expression.BinaryExp;
import edu.postech.csed332.homework5.expression.Exp;
import edu.postech.csed332.homework5.expression.NumberExp;
import edu.postech.csed332.homework5.expression.VariableExp;

/**
 * A visitor to compute the string expression of a given expression
 */
public class ToStringVisitor implements ExpVisitor<String> {

    // TODO write and implement the visitor methods for ToStringVisitor, satisfying
    //  the specification of Exp.toString. A double value is written using exponents,
    //  e.g., using Double.toString().
    @Override
    public String visit(NumberExp cur) {
        // 괄호 안하면 오류 발생. 진심...하아...
        if (cur.getValue() < 0) return "(" + cur.getValue().toString() + ")";
        return cur.getValue().toString();
    }

    @Override
    public String visit(VariableExp cur) {
        int exp = cur.getName();
        return "x" + Integer.toString(exp);
    }

    @Override
    public String visit(BinaryExp curExp, String operator) {
        Exp el = curExp.getLeft();
        Exp er = curExp.getRight();
        // 괄호 생략 시 ExpRandomTest에서 에러 발생
        // exp.toString() 후 다른 모듈로 계산한 값과 비교한다.
        return "(" + el.accept(this) + " " + operator + " " + er.accept(this) + ")";
    }
}
