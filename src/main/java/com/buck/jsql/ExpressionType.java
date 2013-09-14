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

import com.buck.jsql.expressions.conditional.And;

/**
 * Identifier for a query node type.
 */
public enum ExpressionType {

    //-------------------------------------------------------------------------
    // identifiers
    //-------------------------------------------------------------------------

    /**
     * Node type is an Identifier.
     *
     * @see com.buck.jsql.identifiers.Identifier
     */
    Identifier(0, "Identifier"),

    //-------------------------------------------------------------------------
    // conditional expressions
    //-------------------------------------------------------------------------

    /**
     * Node type is an And.
     *
     * @see com.buck.jsql.expressions.conditional.And
     */
    AND(10, "AND"),

    /**
     * Node type is an And.
     *
     * @see And
     */
    ANY(11, "ANY"),

    /**
     * Node type is a BETWEEN.
     *
     * @see com.buck.jsql.expressions.conditional.Between
     */
    BETWEEN(12, "BETWEEN"),
    /**
     * Node type is an IN.
     *
     * @see com.buck.jsql.expressions.conditional.In
     */
    IN(13, "IN"),
    /**
     * Node type is a LIKE.
     *
     * @see com.buck.jsql.expressions.conditional.Like
     */
    LIKE(14, "LIKE"),
    /**
     * Node type is a NOT.
     *
     * @see com.buck.jsql.expressions.conditional.Not
     */
    NOT(15, "NOT"),
    /**
     * Node type is a NULL.
     *
     * @see com.buck.jsql.expressions.conditional.Null
     */
    NULL(16, "NULL"),
    /**
     * Node type is an Or.
     *
     * @see com.buck.jsql.expressions.conditional.Or
     */
    OR(17, "OR"),
    /**
     * Node type is a Comparison.
     *
     * @see com.buck.jsql.expressions.conditional.Comparison
     */
    ComparisonPredicate(18, "Comparison"),

    //-------------------------------------------------------------------------
    // literals
    //-------------------------------------------------------------------------

    /**
     * Node type is a BooleanLiteral.
     *
     * @see com.buck.jsql.literals.BooleanLiteral
     */
    BooleanLiteral(20, "BooleanLiteral"),
    /**
     * Node type is a NumericLiteral.
     *
     * @see com.buck.jsql.literals.NumericLiteral
     */
    NumericLiteral(23, "NumericLiteral"),
    /**
     * Node type is a PatternLiteral.
     *
     * @see com.buck.jsql.literals.PatternLiteral
     */
    PatternLiteral(24, "PatternLiteral"),
    /**
     * Node type is a StringLiteral.
     *
     * @see com.buck.jsql.literals.StringLiteral
     */
    StringLiteral(25, "StringLiteral"),

    //-------------------------------------------------------------------------
    // Arithmentic operators
    //-------------------------------------------------------------------------

    ADD(30, "+"),

    SUBTRACT(31, "-"),

    DIVIDE(32, "/"),

    MULTIPLY(33, "*"),

    MODULUS(34, "%"),

    NEGATE(35, "-");

    private final int kindIndex;
    private final String kindName;

    /**
     * Package constructor.
     *
     * @param kindIndex the numberic id for the enumeration value
     * @param kindName  the name of the enumeration value
     */
    ExpressionType(int kindIndex, String kindName) {
        this.kindIndex = kindIndex;
        this.kindName = kindName;
    }

    /**
     * Gets the identity for the optin kind.
     *
     * @return kind identity
     */
    int getKindIndex() {
        return kindIndex;
    }

    /**
     * Gets the kindName of the optin kind.
     *
     * @return optin kindName
     */
    String getKindName() {
        return kindName;
    }
}
