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

package com.buck.jsql;

import java.io.IOException;
import java.io.Writer;

/**
 * Base class for all expressions.
 *
 * @author Robert J. Buck
 */
public abstract class Expression {

    private Expression parent;
    protected Expression[] children;
    protected final ExpressionType type;

    /**
     * Constructor
     *
     * @param type the node type
     */
    protected Expression(ExpressionType type) {
        this.type = type;
    }

    /**
     * Add a child at the specified index.
     *
     * @param node Node
     * @param i    int
     */
    public void addChild(Expression node, int i) {
        if (children == null) {
            children = new Expression[i + 1];
        } else {
            Expression[] newArray = new Expression[i + 1];
            System.arraycopy(children, 0, newArray, 0, children.length);
            children = newArray;
        }
        children[i] = node;
    }

    /**
     * Get the child at the specified index.
     *
     * @param i int
     * @return Node
     */
    public Expression getChild(int i) {
        return children[i];
    }

    /**
     * Get the number of children of this node.
     *
     * @return int
     */
    public int getNumChildren() {
        return children != null ? children.length : 0;
    }

    /**
     * Get the parent expression of this expression.
     *
     * @return Node
     */
    public Expression getParent() {
        return parent;
    }

    /**
     * Set the parent of this expression.
     *
     * @param node Node
     */
    public void setParent(Expression node) {
        parent = node;
    }

    public String toString() {
        return type.toString();
    }

    /**
     * Dump the execution tree
     *
     * @param writer the destination to write to
     * @throws java.io.IOException if a failure occurs while externalizing the
     *                             expression tree
     */
    public void writeTo(Writer writer) throws IOException {
        writer.append("<").append(toString()).append(">");
        if (children != null) {
            for (Expression child : children) {
                if (child != null) {
                    child.writeTo(writer);
                }
            }
        }
        writer.append("</").append(toString()).append(">");
    }

    /**
     * Evaluates an evaluation context.
     *
     * @param context to evaluate
     * @return true or false
     * @throws com.buck.jsql.EvaluationException
     *          if the expression evaluation failed
     */
    public abstract Object evaluate(EvaluationContext context) throws EvaluationException;
}
