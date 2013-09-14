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
import com.buck.jsql.expressions.BinaryExpression;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Performs a regular expression comparison. The first child is the literal to match,
 * the second child is the pattern.
 *
 * @author Robert J. Buck
 */
public class Like extends BinaryExpression {
    /**
     * Constructs a logical OR operator expression.
     *
     * @param lhs the left hand side expression to evaluate
     * @param rhs the right hand side expression to evaluate
     */
    public Like(final Expression lhs, final Expression rhs) {
        super(ExpressionType.LIKE, lhs, rhs);
    }

    /**
     * Evaluates a message, performing a regular expression comparison with a
     * literal.
     *
     * @param context the context to evaluate
     * @return true if the literal matches
     * @throws com.buck.jsql.EvaluationException
     *          if the expression evaluation failed
     */
    public Object evaluate(EvaluationContext context) throws EvaluationException {
        String svalue = (String) getLHS().evaluate(context);
        Pattern pattern = (Pattern) getRHS().evaluate(context);
        Matcher matcher = pattern.matcher(svalue);
        return matcher.matches();
    }
}
