package edu.postech.csed332.homework5;

import edu.postech.csed332.homework5.expression.Exp;
import edu.postech.csed332.homework5.expression.NumberExp;
import edu.postech.csed332.homework5.expression.VariableExp;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * When evaluating the expression, use the default value for variables not present in the valuation.
 */
public class DefaultValueExpDecorator extends ExpDecorator {
    @NotNull
    private final Double defaultValue;

    public DefaultValueExpDecorator(@NotNull Exp e, @NotNull Double defaultValue) {
        super(e);
        this.defaultValue = defaultValue;
    }

    @Override
    @NotNull
    public Double eval(@NotNull Map<Integer, Double> valuation) {
        // TODO implement this
        // EvaluationVisitor 내 valuation map에 값이 존재하지 않을 경우,
        EvaluationVisitor visitor = new EvaluationVisitor(valuation) {
            @Override
            public Double visit(VariableExp cur) {
                int v = cur.getName();
                if (!valuation.containsKey(v)) return defaultValue;
                return valuation.get(v);
            }
        };
        return accept(visitor);
    }
}
