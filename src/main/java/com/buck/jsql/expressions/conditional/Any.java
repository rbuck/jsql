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
import com.buck.jsql.expressions.NAryExpression;

/**
 * A specialization of the ANY logical operator, this operator is N-Ary.
 *
 * @author Robert J. Buck
 */
public class Any extends NAryExpression {
    /**
     * Constructs an ANY expression.
     *
     * @param arr the array of expressions to evaluate
     */
    public Any(Expression... arr) {
        super(ExpressionType.ANY, arr);
    }

    /**
     * Evaluates multiple boolean terms returning true if one evaluates as
     * true.
     *
     * @param context the context to evaluate
     * @return true if one of the terms evaluate as true
     * @throws com.buck.jsql.EvaluationException
     *          if the expression evaluation failed
     */
    public Object evaluate(EvaluationContext context) throws EvaluationException {
        // null && null => null
        boolean allNull = true;
        for (Expression child : children) {
            allNull &= child == null;
        }
        if (allNull) {
            return null;
        }

        // if any node evaluates true, return true, otherwise return false
        for (Expression child : children) {
            if (child != null) {
                Boolean value = (Boolean) child.evaluate(context);
                if (value) {
                    return true;
                }
            }
        }
        return false;
    }
}
