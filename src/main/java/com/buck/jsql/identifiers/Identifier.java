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

package com.buck.jsql.identifiers;

import com.buck.jsql.EvaluationContext;
import com.buck.jsql.EvaluationException;
import com.buck.jsql.Expression;
import com.buck.jsql.ExpressionType;
import com.buck.jsql.reflection.PropertyAccessException;

/**
 * Returns an identifier from an object evaluated. This class must be
 * implemented by
 *
 * @author Robert J. Buck
 */
public class Identifier extends Expression implements Comparable<Identifier> {

    private final String id;

    /**
     * Constructs an identifier.
     *
     * @param id the identity of the identifier
     */
    public Identifier(final String id) {
        super(ExpressionType.Identifier);
        this.id = id;
    }

    public Object evaluate(EvaluationContext context) throws EvaluationException {
        try {
            return context.getValue(this);
        } catch (PropertyAccessException e) {
            throw new EvaluationException(e);
        }
    }

    public int compareTo(Identifier other) {
        return id.compareTo(other.id);
    }

    public String toString() {
        return id;
    }

    public int hashCode() {
        return id.hashCode();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Identifier) {
            Identifier other = (Identifier) obj;
            return id.equals(other.id);
        }
        return false;
    }
}
