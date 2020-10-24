package edu.postech.csed332.homework5;

import edu.postech.csed332.homework5.expression.BinaryExp;
import edu.postech.csed332.homework5.expression.Exp;
import edu.postech.csed332.homework5.expression.NumberExp;
import edu.postech.csed332.homework5.expression.VariableExp;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;

/**
 * The string representation is given without exponents of double values. For example,
 * 12345678, not 1.2345678E7. (Hint: use java.math.BigDecimal)
 */
public class PrettyPrintExpDecorator extends ExpDecorator {

    public PrettyPrintExpDecorator(Exp e) {
        super(e);
    }

    @NotNull
    @Override
    public String toString() {
        // TODO implement this
        // variable 및 binaryExp 같은 경우 다르게 오버라이드 될 필요가 없다.
        ToStringVisitor visitor = new ToStringVisitor() {
            @Override
            public String visit(NumberExp cur){
                BigDecimal b = new BigDecimal(cur.getValue());
                return b.toPlainString();
            }
        };

        return accept(visitor);
    }

}