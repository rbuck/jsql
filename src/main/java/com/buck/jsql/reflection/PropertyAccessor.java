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

package com.buck.jsql.reflection;

/**
 * Abstracts the notion of a "property". Defines a strategy for accessing the
 * value of an attribute.
 *
 * @author Robert J. Buck
 */
public interface PropertyAccessor {
    /**
     * Create a "getter" for the named attribute
     *
     * @param theClass     the class on which a getter is requested
     * @param propertyName the property for which to create a getter
     * @return the reflective getter for the property
     * @throws PropertyNotFoundException if there is no is* or get* method for
     *                                   the requested property
     */
    public Getter getGetter(Class theClass, String propertyName) throws PropertyNotFoundException;

    /**
     * Create a "setter" for the named attribute
     *
     * @param theClass     the class on which a setter is requested
     * @param propertyName the property for which to create a getter
     * @return the reflective setter for the property
     * @throws PropertyNotFoundException if there is no set* method for
     *                                   the requested property
     */
    public Setter getSetter(Class theClass, String propertyName) throws PropertyNotFoundException;
}
