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
 * Base class for all binary operators.
 *
 * @author Robert J. Buck
 */
public abstract class BinaryExpression extends Expression {
    /**
     * Constructor
     *
     * @param type the node type
     * @param lhs  the left hand side expression to evaluate
     * @param rhs  the right hand side expression to evaluate
     */
    protected BinaryExpression(ExpressionType type, Expression lhs, Expression rhs) {
        super(type);
        addChild(lhs, 0);
        lhs.setParent(this);
        addChild(rhs, 1);
        rhs.setParent(this);
    }

    protected Expression getLHS() {
        return getChild(0);
    }

    protected Expression getRHS() {
        return getChild(1);
    }
}
