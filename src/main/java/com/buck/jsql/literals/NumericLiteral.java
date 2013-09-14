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

package com.buck.jsql.literals;

import com.buck.jsql.EvaluationContext;
import com.buck.jsql.EvaluationException;
import com.buck.jsql.Expression;
import com.buck.jsql.ExpressionType;

/**
 * A literal value for a number.
 *
 * @author Robert J. Buck
 */
public class NumericLiteral extends Expression {

    private final Number literal;

    /**
     * Constructs a numeric literal.
     *
     * @param literal the numeric literal
     */
    public NumericLiteral(final Number literal) {
        super(ExpressionType.NumericLiteral);
        this.literal = literal;
    }

    /**
     * The numeric literal is returned.
     *
     * @param context unused
     * @return the boolean literal
     * @throws EvaluationException
     */
    public Object evaluate(EvaluationContext context) throws EvaluationException {
        return literal;
    }
}
