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
import com.buck.jsql.expressions.BinaryExpression;

/**
 * Performs an MULTIPLY binary expression on two numbers.
 *
 * @author Robert J. Buck
 */
public class Multiply extends BinaryExpression {
    /**
     * Constructs an arithmetic MULTIPLY operator expression.
     *
     * @param lhs the left hand side expression to evaluate
     * @param rhs the right hand side expression to evaluate
     */
    public Multiply(final Expression lhs, final Expression rhs) {
        super(ExpressionType.MULTIPLY, lhs, rhs);
    }

    /**
     * The MULTIPLY operation. The terms specified by child 0 and child 1 are
     * applied to the given object and the MULTIPLY operator is applied to their
     * results.
     *
     * @param context the context to evaluate
     * @return true if both terms evaluate as true
     * @throws com.buck.jsql.EvaluationException
     *          if the expression evaluation failed
     */
    @SuppressWarnings({"SimplifiableIfStatement"})
    public Object evaluate(EvaluationContext context) throws EvaluationException {
        Object lhs = getLHS().evaluate(context);
        Object rhs = getRHS().evaluate(context);
        // null && null => null
        if (lhs == null && rhs == null) {
            return null;
        }
        if ((lhs instanceof Float || lhs instanceof Double) || (rhs instanceof Float || rhs instanceof Double)) {
            assert lhs instanceof Number;
            assert rhs instanceof Number;
            return ((Number) lhs).doubleValue() * ((Number) rhs).doubleValue();
        } else {
            assert lhs instanceof Number;
            assert rhs instanceof Number;
            return ((Number) lhs).longValue() * ((Number) rhs).longValue();
        }
    }
}
