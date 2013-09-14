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

/**
 * Compares two values using a specified operator.
 *
 * @author Robert J. Buck
 */
public class Comparison extends BinaryExpression {
    /**
     * Operators applicable to comparison predicates.
     */
    public enum Operator {
        /**
         * Equal to operator.
         */
        EQ("="),
        /**
         * Not equal to operator.
         */
        NE("<>"),
        /**
         * Less than operator.
         */
        LT("<"),
        /**
         * Greater than operator.
         */
        GT(">"),
        /**
         * Less than or equal to operator.
         */
        LE("<="),
        /**
         * Greater than or equal to operator.
         */
        GE(">=");

        /**
         * Private enum constructor
         *
         * @param name identifies the name of the operator
         */
        private Operator(String name) {
            this.name = name;
        }

        /**
         * Gets the name of the operator
         *
         * @return name of the operator
         */
        public String operator() {
            return name;
        }

        private final String name;
    }

    private final Operator operator;

    /**
     * Constructs a logical comparison expression. Logical comparison
     * expressions include: =, >, >=, <, <=, <>.
     *
     * @param operator the type of comparison to perform
     * @param lhs      the left hand side expression to evaluate
     * @param rhs      the right hand side expression to evaluate
     */
    public Comparison(final Operator operator, final Expression lhs, final Expression rhs) {
        super(ExpressionType.ComparisonPredicate, lhs, rhs);
        this.operator = operator;
    }


    /**
     * Compares two children using a specified operator
     *
     * @param context the context to evaluate
     * @return true if the operator-expression returns true, otherwise false
     * @throws com.buck.jsql.EvaluationException
     *          if the expression evaluation failed
     */
    public Object evaluate(EvaluationContext context) throws EvaluationException {
        Object lhs = getLHS().evaluate(context);
        if (lhs == null) {
            return null;
        }
        Object rhs = getRHS().evaluate(context);
        if (rhs == null) {
            return null;
        }
        if (lhs instanceof String && rhs instanceof String) {
            String lvalue = (String) lhs;
            String rvalue = (String) rhs;
            switch (operator) {
                case EQ: {
                    return lhs == rhs || lhs.equals(rhs);
                }
                case NE: {
                    return !lhs.equals(rhs);
                }
                case LT: {
                    return lvalue.compareTo(rvalue) < 0;
                }
                case GT: {
                    return lvalue.compareTo(rvalue) > 0;
                }
                case LE: {
                    return lvalue.compareTo(rvalue) <= 0;
                }
                case GE: {
                    return lvalue.compareTo(rvalue) >= 0;
                }
            }
        } else if (lhs instanceof Number && rhs instanceof Number) {
            Number lvalue = (Number) lhs;
            Number rvalue = (Number) rhs;
            switch (operator) {
                case EQ: {
                    if ((lvalue instanceof Float || lvalue instanceof Double) || (rvalue instanceof Float || rvalue instanceof Double)) {
                        return lvalue.doubleValue() == rvalue.doubleValue();
                    } else {
                        return lvalue.longValue() == rvalue.longValue();
                    }
                }
                case NE: {
                    if ((lvalue instanceof Float || lvalue instanceof Double) || (rvalue instanceof Float || rvalue instanceof Double)) {
                        return lvalue.doubleValue() != rvalue.doubleValue();
                    } else {
                        return lvalue.longValue() != rvalue.longValue();
                    }
                }
                case LT: {
                    if ((lvalue instanceof Float || lvalue instanceof Double) || (rvalue instanceof Float || rvalue instanceof Double)) {
                        return lvalue.doubleValue() < rvalue.doubleValue();
                    } else {
                        return lvalue.longValue() < rvalue.longValue();
                    }
                }
                case GT: {
                    if ((lvalue instanceof Float || lvalue instanceof Double) || (rvalue instanceof Float || rvalue instanceof Double)) {
                        return lvalue.doubleValue() > rvalue.doubleValue();
                    } else {
                        return lvalue.longValue() > rvalue.longValue();
                    }
                }
                case LE: {
                    if ((lvalue instanceof Float || lvalue instanceof Double) || (rvalue instanceof Float || rvalue instanceof Double)) {
                        return lvalue.doubleValue() <= rvalue.doubleValue();
                    } else {
                        return lvalue.longValue() <= rvalue.longValue();
                    }
                }
                case GE: {
                    if ((lvalue instanceof Float || lvalue instanceof Double) || (rvalue instanceof Float || rvalue instanceof Double)) {
                        return lvalue.doubleValue() >= rvalue.doubleValue();
                    } else {
                        return lvalue.longValue() >= rvalue.longValue();
                    }
                }
            }
        } else if (lhs instanceof Boolean && rhs instanceof Boolean) {
            Boolean lvalue = (Boolean) lhs;
            Boolean rvalue = (Boolean) rhs;
            switch (operator) {
                case EQ: {
                    return lvalue.equals(rvalue);
                }
                case NE: {
                    return !lvalue.equals(rvalue);
                }
            }
        }
        return false;
    }

}
