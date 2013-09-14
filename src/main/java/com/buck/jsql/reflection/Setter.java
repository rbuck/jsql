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

import java.lang.reflect.Method;

/**
 * Sets values to a particular property.
 *
 * @author Robert J. Buck
 */
public interface Setter {
    /**
     * Set the property value from the given instance
     *
     * @param target The instance upon which to set the given value.
     * @param value  The value to be set on the target.
     * @throws PropertyAccessException if a property is reflectively inaccessible
     */
    public void set(Object target, Object value) throws PropertyAccessException;

    /**
     * Optional operation (return null)
     *
     * @return the method name
     */
    public String getMethodName();

    /**
     * Optional operation (return null)
     *
     * @return the method
     */
    public Method getMethod();
}
