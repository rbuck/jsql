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
 * The NULL predicate tests for null values. The result of a NULL predicate
 * cannot be unknown. If the value of the expression is null, the result is
 * true. If the value is not null, the result is false.
 *
 * @author Robert J. Buck
 */
public class Null extends UnaryExpression {
    /**
     * Constructs a boolean expression that returns true if it evaluates true.
     *
     * @param lhs the expression to evaluate
     */
    public Null(final Expression lhs) {
        super(ExpressionType.NULL, lhs);
    }

    /**
     * Checks if the subexpression returns null.
     *
     * @param context the context to evaluate
     * @return true if the subexpression returns null, otherwise false
     * @throws com.buck.jsql.EvaluationException
     *          if the expression evaluation failed
     */
    public Object evaluate(EvaluationContext context) throws EvaluationException {
        Object obj = getLHS().evaluate(context);
        return obj == null;
    }
}
