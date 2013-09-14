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

package com.buck.jsql.expressions;

import com.buck.jsql.Expression;
import com.buck.jsql.ExpressionType;

/**
 * Base class for all n-ary operators. N-ary operators are those operators
 * that have an unbounded number of expressions to evaluate.
 *
 * @author Robert J. Buck
 */
public abstract class NAryExpression extends Expression {
    /**
     * Constructs a unary operator.
     *
     * @param type the node type
     * @param arr  the array of expressions to evaluate
     */
    protected NAryExpression(ExpressionType type, Expression... arr) {
        super(type);
        for (int i = 0; i < arr.length; i++) {
            addChild(arr[i], i);
            arr[i].setParent(this);
        }
    }

    protected Expression getLHS() {
        return getChild(0);
    }
}
