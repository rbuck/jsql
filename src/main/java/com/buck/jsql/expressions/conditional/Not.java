/*
 * Copyright 2010-2013 Robert J. Buck
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.buck.jsql.expressions.conditional;

import com.buck.jsql.EvaluationContext;
import com.buck.jsql.EvaluationException;
import com.buck.jsql.Expression;
import com.buck.jsql.ExpressionType;
import com.buck.jsql.expressions.UnaryExpression;

/**
 * Implements the logical NEGATION operator.
 *
 * @author Robert J. Buck
 */
public class Not extends UnaryExpression {
    /**
     * Constructs a logical NOT operator expression.
     *
     * @param lhs the left hand side expression to evaluate
     */
    public Not(final Expression lhs) {
        super(ExpressionType.NOT, lhs);
    }

    /**
     * Not's the result of the subexpression.
     *
     * @param context the context to evaluate.
     * @return null if the subexpression returns null, otherwise the negated
     *         logical value
     * @throws EvaluationException if the expression evaluation failed
     */
    public Object evaluate(EvaluationContext context) throws EvaluationException {
        Object obj = getLHS().evaluate(context);
        if (obj == null) {
            return null;
        }
        Boolean value = (Boolean) obj;
        return !value;
    }
}
