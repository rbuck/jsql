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

import java.util.List;

/**
 * The IN predicate compares a value or values with a collection of values. The
 * first child is the identifier to compare to, the remaining children are the
 * collection of values to compare the literal to.
 *
 * @author Robert J. Buck
 */
public class In extends UnaryExpression {
    /**
     * Constructs an IN expression.
     *
     * @param lhs   the left hand side expression to evaluate
     * @param items the list of items to check
     */
    public In(final Expression lhs, List<Expression> items) {
        super(ExpressionType.IN, lhs);
        for (int i = 0; i < items.size(); i++) {
            addChild(items.get(i), i);
        }
    }

    /**
     * Evaluate a message to see if a field value is in a collection of values.
     *
     * @param context the context to evaluate
     * @return true if the identifier is in the collection, false otherwise
     * @throws com.buck.jsql.EvaluationException
     *          if the expression evaluation failed
     */
    public Object evaluate(EvaluationContext context) throws EvaluationException {
        // identifier to evaluate
        Object obj = getLHS().evaluate(context);
        if (obj == null) {
            return null;
        }
        for (int i = 1; i < children.length; i++) {
            String literal = (String) getChild(i).evaluate(context);
            if (literal.equals(obj)) {
                return true;
            }
        }
        return false;
    }
}
