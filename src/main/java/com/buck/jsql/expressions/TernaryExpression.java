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
public abstract class TernaryExpression extends Expression {
    /**
     * Constructor
     *
     * @param type the node type
     * @param op1  the first expression to evaluate
     * @param op2  the second expression to evaluate
     * @param op3  the third expression to evaluate
     */
    protected TernaryExpression(ExpressionType type, Expression op1, Expression op2, Expression op3) {
        super(type);
        addChild(op1, 0);
        op1.setParent(this);
        addChild(op2, 1);
        op2.setParent(this);
        addChild(op3, 2);
        op3.setParent(this);
    }

    protected Expression getFirst() {
        return getChild(0);
    }

    protected Expression getSecond() {
        return getChild(1);
    }

    protected Expression getThird() {
        return getChild(2);
    }
}
