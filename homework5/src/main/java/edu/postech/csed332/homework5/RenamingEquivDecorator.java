package edu.postech.csed332.homework5;

import edu.postech.csed332.homework5.expression.Exp;
import edu.postech.csed332.homework5.expression.VariableExp;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

/**
 * This expression is equivalent to another expression that is syntactically identical up to renaming.
 * For example, "(x1 + x2) * x3 + 1.0 * x1" is equivalent to "(x3 + x1) * x2 + 1.0 * x3", but not
 * equivalent to "(x3 + x1) * x2 + 1.0 * x1".
 */
public class RenamingEquivDecorator extends ExpDecorator {

    public RenamingEquivDecorator(Exp e) {
        super(e);
    }

    @Override
    public boolean equiv(@NotNull Exp other) {
        // TODO implement this
        EquivalenceVisitor visitor = new EquivalenceVisitor(other) {

            /**
             * "(x1 + x2) * x3 + 1.0 * x1"
             * ==
             * "(x3 + x1) * x2 + 1.0 * x3"
             * */

            // variable 같은 지 비교할 때, 두 값이 같다면 그 값은 모두 계속 같아야 하고,
            // 두 값이 다른 값이라면, 모든 경우에 동일하게 바뀌어 있도록,
            // mapping 정보 저장
            Map<Integer, Integer> mapping = new HashMap<>();

            @Override
            public Boolean visit(VariableExp cur){
                if (!(other instanceof VariableExp)) return false;
                int other_ = ((VariableExp) other).getName();
                int cur_ = cur.getName();

                if (!mapping.containsKey(cur_)) {
                    mapping.put(cur_, other_);
                    return true;
                } else {
                    if (mapping.get(cur_) == other_) return true;
                    else return false;
                }
            }
        };

        return accept(visitor);
    }
}
