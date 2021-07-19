package edu.postech.csed332.homework5;

import edu.postech.csed332.homework5.expression.*;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;

/**
 * A visitor to check whether a given expression is syntactically equivalent to another expression.
 */
public class EquivalenceVisitor implements ExpVisitor<Boolean> {
    Exp other;
    public EquivalenceVisitor(@NotNull Exp other) {
        // TODO: implement this
        this.other = other;
    }

    // TODO write and implement the visitor methods for EquivalenceVisitor, satisfying
    //  the specification of Exp.equiv. You may need to add more member variables.
    @Override
    public Boolean visit(NumberExp cur) {
        if (!(other instanceof NumberExp)) return false;
        BigDecimal other_ = new BigDecimal(((NumberExp) other).getValue().toString());
        BigDecimal cur_ = new BigDecimal(cur.getValue().toString());
        // 소수점 맨 끝의 0을 무시하고 값이 동일하면 0, 적으면 -1, 많으면 1 반환
        return other_.compareTo(cur_) == 0;
    }

    @Override
    public Boolean visit(VariableExp cur){
        if (!(other instanceof VariableExp)) return false;
        int other_ = ((VariableExp) other).getName();
        int cur_ = cur.getName();
        return (other_ == cur_);
    }

    @Override
    public Boolean visit(BinaryExp curExp, String operator){
        // 단순 number나 variable이 들어오지 않아야 한다.
        if (!(other instanceof BinaryExp)) return false;

        // Plus(NumberExp(1.0), MultiplyExp(NumberExp(2.0),VariableExp(1))) 같이
        // 복잡한 BinaryExp에 대한 처리 -> 타고 타고
        Boolean op = false;
        if (operator.equals("+") && other instanceof PlusExp) op = true;
        else if (operator.equals("-") && other instanceof MinusExp) op = true;
        else if (operator.equals("*") && other instanceof MultiplyExp) op = true;
        else if (operator.equals("/") && other instanceof DivideExp) op = true;
        else if (operator.equals("^") && other instanceof ExponentiationExp) op = true;
        else System.out.println("Invalid Argument Error Occures");

        Exp left = ((BinaryExp) other).getLeft();
        Exp right = ((BinaryExp) other).getRight();
        other = left;
        Boolean l = curExp.getLeft().accept(this);
        other = right;
        Boolean r = curExp.getRight().accept(this);

        return l && op && r;
    }
}
