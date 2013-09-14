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

package com.buck.jsql.expressions.arithmetic;

import com.buck.jsql.EvaluationContext;
import com.buck.jsql.EvaluationException;
import com.buck.jsql.Expression;
import com.buck.jsql.ExpressionType;
import com.buck.jsql.expressions.UnaryExpression;

/**
 * Performs a unary negate operation on numeric values.
 *
 * @author Robert J. Buck
 */
public class Negate extends UnaryExpression {
    /**
     * Constructs a arithmetic negate expression.
     *
     * @param lhs the left hand side expression to evaluate
     */
    public Negate(final Expression lhs) {
        super(ExpressionType.NEGATE, lhs);
    }

    /**
     * Negates the result of the subexpression.
     *
     * @param context the context to evaluate.
     * @return null if the subexpression returns null, otherwise the negated
     *         logical value
     * @throws com.buck.jsql.EvaluationException
     *          if the expression evaluation failed
     */
    public Object evaluate(EvaluationContext context) throws EvaluationException {
        Object obj = getLHS().evaluate(context);
        if (obj == null) {
            return null;
        }
        if (obj instanceof Number) {
            if (obj instanceof Integer) {
                return -((Integer) obj);
            } else if (obj instanceof Long) {
                return -((Long) obj);
            } else if (obj instanceof Float) {
                return -((Float) obj);
            } else if (obj instanceof Double) {
                return -((Double) obj);
            } else {
                // we do not support big decimal (yet)
            }
        }
        return null;
    }
}
