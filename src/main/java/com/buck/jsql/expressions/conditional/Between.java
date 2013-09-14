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
import com.buck.jsql.expressions.TernaryExpression;

/**
 * The range comparison (BETWEEN) predicate compares a value with a range of values.
 *
 * @author Robert J. Buck
 */
public class Between extends TernaryExpression {
    /**
     * Constructs a SQL BETWEEN expression.
     *
     * @param op1 the first expression to evaluate
     * @param op2 the second expression to evaluate
     * @param op3 the third expression to evaluate
     */
    public Between(Expression op1, Expression op2, Expression op3) {
        super(ExpressionType.BETWEEN, op1, op2, op3);
    }

    /**
     * Evaluates three terms; the first term being the value, the second and
     * third terms define the lower and upper values for a range.
     *
     * @param context the context to evaluate
     * @return true if the value is between the lower and upper bounds
     * @throws com.buck.jsql.EvaluationException
     *
     */
    public Object evaluate(EvaluationContext context) throws EvaluationException {
        // identifier to evaluate
        Object obj = getFirst().evaluate(context);
        if (obj == null) {
            return null;
        }
        // lower range term
        Object lower = getSecond().evaluate(context);
        if (lower == null) {
            return null;
        }
        // upper range term
        Object upper = getThird().evaluate(context);
        if (upper == null) {
            return null;
        }
        if ((obj instanceof Double) || (lower instanceof Double) || (upper instanceof Double) ||
                (obj instanceof Float) || (lower instanceof Float) || (upper instanceof Float)) {
            assert obj instanceof Number;
            assert lower instanceof Number;
            assert upper instanceof Number;
            Number lvalue = (Number) lower;
            Number rvalue = (Number) upper;
            Number value = (Number) obj;
            return lvalue.doubleValue() <= value.doubleValue() && value.doubleValue() <= rvalue.doubleValue();
        } else {
            assert obj instanceof Number;
            assert lower instanceof Number;
            assert upper instanceof Number;
            Number lvalue = (Number) lower;
            Number rvalue = (Number) upper;
            Number value = (Number) obj;
            return lvalue.longValue() <= value.longValue() && value.longValue() <= rvalue.longValue();
        }
    }
}
