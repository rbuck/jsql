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

import com.buck.commons.i18n.ResourceBundle;

/**
 * A factory for building/retrieving PropertyAccessor instances.
 *
 * @author Robert J. Buck
 */
class PropertyAccessorFactory {

    private static final PropertyAccessor BASIC_PROPERTY_ACCESSOR = new BasicPropertyAccessor();
    private static final PropertyAccessor DIRECT_PROPERTY_ACCESSOR = new DirectPropertyAccessor();

    /**
     * Retreives a PropertyAccessor specific for a PojoRepresentation with the given access strategy.
     *
     * @param pojoAccessorStrategy The access strategy.
     * @return An appropriate accessor.
     * @throws PropertyAccessException if access to the property is not
     *                                 permitted or the property does not exist
     */
    public static PropertyAccessor getPojoPropertyAccessor(String pojoAccessorStrategy) throws PropertyAccessException {
        if (StringHelper.isEmpty(pojoAccessorStrategy) || "property".equals(pojoAccessorStrategy)) {
            return BASIC_PROPERTY_ACCESSOR;
        } else if ("field".equals(pojoAccessorStrategy)) {
            return DIRECT_PROPERTY_ACCESSOR;
        } else {
            return resolveCustomAccessor(pojoAccessorStrategy);
        }
    }

    private static PropertyAccessor resolveCustomAccessor(String accessorName) throws PropertyAccessException {
        Class accessorClass;
        try {
            accessorClass = ReflectionSupport.classForName(accessorName);
        } catch (ClassNotFoundException cnfe) {
            Object[] arguments = {accessorName};
            String message = ResourceBundle.formatResourceBundleMessage(BasicPropertyAccessor.class,
                    "PROPERTY_ACCESSOR_NOT_FOUND", arguments);
            throw new PropertyAccessException(message, cnfe);
        }
        try {
            return (PropertyAccessor) accessorClass.newInstance();
        } catch (Exception e) {
            Object[] arguments = {accessorName};
            String message = ResourceBundle.formatResourceBundleMessage(BasicPropertyAccessor.class,
                    "PROPERTY_ACCESSOR_INSTANTIATION", arguments);
            throw new PropertyAccessException(message, e);
        }
    }

    private PropertyAccessorFactory() {
    }
}
