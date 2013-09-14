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

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Accesses fields directly.
 *
 * @author Robert J. Buck
 */
class DirectPropertyAccessor implements PropertyAccessor {

    public static final class DirectGetter implements Getter {

        private static final long serialVersionUID = -8412018522017290972L;

        private final transient Field field;
        private final Class clazz;
        private final String name;

        DirectGetter(Field field, Class clazz, String name) {
            this.field = field;
            this.clazz = clazz;
            this.name = name;
        }

        public Object get(Object target) throws PropertyAccessException {
            try {
                return field.get(target);
            } catch (Exception e) {
                Object[] arguments = {clazz.getName(), name};
                String message = ResourceBundle.formatResourceBundleMessage(DirectPropertyAccessor.class,
                        "DIRECT_FIELD_VALUE_NOT_FOUND", arguments);
                throw new PropertyAccessException(message, e);
            }
        }

        public Method getMethod() {
            return null;
        }

        public String getMethodName() {
            return null;
        }

        public Class getReturnType() {
            return field.getType();
        }

        public String toString() {
            return "DirectGetter(" + clazz.getName() + '.' + name + ')';
        }
    }

    private static Field getField(Class clazz, String name) throws PropertyNotFoundException {
        if (clazz == null || clazz == Object.class) {
            Object[] arguments = {};
            String message = ResourceBundle.formatResourceBundleMessage(DirectPropertyAccessor.class,
                    "DIRECT_FIELD_ILLEGAL_ARGUMENT", arguments);
            throw new PropertyNotFoundException(message);
        }
        Field field;
        try {
            field = clazz.getDeclaredField(name);
        } catch (NoSuchFieldException nsfe) {
            field = getField(clazz, clazz.getSuperclass(), name);
        }
        if (!ReflectionSupport.isPublic(clazz, field)) {
            field.setAccessible(true);
        }
        return field;
    }

    private static Field getField(Class root, Class clazz, String name) throws PropertyNotFoundException {
        if (clazz == null || clazz == Object.class) {
            Object[] arguments = {root.getName(), name};
            String message = ResourceBundle.formatResourceBundleMessage(DirectPropertyAccessor.class,
                    "DIRECT_FIELD_VALUE_NOT_FOUND", arguments);
            throw new PropertyNotFoundException(message);
        }
        Field field;
        try {
            field = clazz.getDeclaredField(name);
        } catch (NoSuchFieldException nsfe) {
            field = getField(root, clazz.getSuperclass(), name);
        }
        if (!ReflectionSupport.isPublic(clazz, field)) {
            field.setAccessible(true);
        }
        return field;
    }

    public Getter getGetter(Class theClass, String propertyName)
            throws PropertyNotFoundException {
        return new DirectGetter(getField(theClass, propertyName), theClass, propertyName);
    }

    // ------------------------------------------------------------------------

    public static final class DirectSetter implements Setter {
        private final Field field;
        private final Class clazz;
        private final String name;

        DirectSetter(Field field, Class clazz, String name) {
            this.field = field;
            this.clazz = clazz;
            this.name = name;
        }

        public Method getMethod() {
            return null;
        }

        public String getMethodName() {
            return null;
        }

        public void set(Object target, Object value) throws PropertyAccessException {
            try {
                field.set(target, value);
            } catch (Exception e) {
                if (value == null && field.getType().isPrimitive()) {
                    Object[] arguments = {clazz.getName(), name};
                    String message = ResourceBundle.formatResourceBundleMessage(DirectPropertyAccessor.class,
                            "DIRECT_NULL_ASSIGNMENT_TO_PRIMITIVE", arguments);
                    throw new PropertyAccessException(message, e);
                } else {
                    Object[] arguments = {clazz.getName(), name};
                    String message = ResourceBundle.formatResourceBundleMessage(DirectPropertyAccessor.class,
                            "DIRECT_ASSIGNMENT_FAILED", arguments);
                    throw new PropertyAccessException(message, e);
                }
            }
        }

        public String toString() {
            return "DirectSetter(" + clazz.getName() + '.' + name + ')';
        }
    }

    public Setter getSetter(Class theClass, String propertyName)
            throws PropertyNotFoundException {
        return new DirectSetter(getField(theClass, propertyName), theClass, propertyName);
    }
}
