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

import com.buck.jsql.identifiers.Identifier;
import com.buck.jsql.reflection.PropertyAccessException;

/**
 * Stub interface for state to be evaluated.
 *
 * @author Robert J. Buck
 */
public interface EvaluationContext {
    /**
     * Gets the value for the specified identifier.
     *
     * @param identifier the identifier whose value is requested
     * @return the identifier's value
     * @throws EvaluationException evaluation of an identifier failed
     * @throws com.buck.jsql.reflection.PropertyAccessException
     *                             if a property was not accessible using reflection
     */
    public Object getValue(Identifier identifier) throws EvaluationException, PropertyAccessException;
}
