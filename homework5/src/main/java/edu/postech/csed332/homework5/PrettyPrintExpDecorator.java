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
        ToStringVisitor visitor = new ToStringVisitor() {
            // variable 및 binaryExp 같은 경우 다르게 오버라이드 될 필요가 없다.

            /** Double 연산에서 10.0000 + 3.0000은 13.0000이라고 예상되지만,
             * 실제로는 13.000001999999999이 나오게 된다.
             *
             * */
            @Override
            public String visit(NumberExp cur){
                // Double 그대로 BigDecimal에 넣어주면, 실제와 다른 값으로 된다.
                BigDecimal b = new BigDecimal(cur.getValue().toString());
                return b.toPlainString();
            }
        };

        return accept(visitor);
    }

}