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
 * A string literal to compare a message against.
 *
 * @author Robert J. Buck
 */
public class StringLiteral extends Expression {

    private final String literal;

    /**
     * Default constructor.
     *
     * @param literal the literal value
     */
    public StringLiteral(final String literal) {
        super(ExpressionType.StringLiteral);
        this.literal = literal;
    }

    /**
     * Returns the string literal
     *
     * @param context unused
     * @return the string literal
     * @throws com.buck.jsql.EvaluationException
     *
     */
    public Object evaluate(EvaluationContext context) throws EvaluationException {
        return literal;
    }
}
