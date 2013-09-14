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

import java.beans.Introspector;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Accesses property values via a get/set pair, which may be nonpublic.
 * The default (and recommended strategy).
 *
 * @author Robert J. Buck
 */
class BasicPropertyAccessor implements PropertyAccessor {

    public static final class BasicGetter implements Getter {

        private static final long serialVersionUID = -8122263242128526366L;

        private final Class clazz;
        private final transient Method method;
        private final String propertyName;

        private BasicGetter(Class clazz, Method method, String propertyName) {
            this.clazz = clazz;
            this.method = method;
            this.propertyName = propertyName;
        }

        public Object get(Object target) throws PropertyAccessException {
            try {
                return method.invoke(target);
            } catch (InvocationTargetException ite) {
                Object[] arguments = {clazz.getName(), propertyName};
                String message = ResourceBundle.formatResourceBundleMessage(BasicPropertyAccessor.class,
                        "BASIC_GETTER_ITE", arguments);
                throw new PropertyAccessException(message, ite);
            } catch (IllegalAccessException iae) {
                Object[] arguments = {clazz.getName(), propertyName};
                String message = ResourceBundle.formatResourceBundleMessage(BasicPropertyAccessor.class,
                        "REFLECTION_ILLEGAL_ACCESS", arguments);
                throw new PropertyAccessException(message, iae);
            } catch (IllegalArgumentException iae) {
                Object[] arguments = {clazz.getName(), propertyName};
                String message = ResourceBundle.formatResourceBundleMessage(BasicPropertyAccessor.class,
                        "REFLECTION_ILLEGAL_ARGUMENT_CALLING_GETTER", arguments);
                throw new PropertyAccessException(message, iae);
            }
        }

        public Class getReturnType() {
            return method.getReturnType();
        }

        public Method getMethod() {
            return method;
        }

        public String getMethodName() {
            return method.getName();
        }

        public String toString() {
            return "BasicGetter(" + clazz.getName() + '.' + propertyName + ')';
        }
    }

    public Getter getGetter(Class theClass, String propertyName)
            throws PropertyNotFoundException {
        return createGetter(theClass, propertyName);
    }

    public static Getter createGetter(Class theClass, String propertyName)
            throws PropertyNotFoundException {
        BasicGetter result = getGetterOrNull(theClass, propertyName);
        if (result == null) {
            Object[] arguments = {theClass.getName(), propertyName};
            String message = ResourceBundle.formatResourceBundleMessage(BasicPropertyAccessor.class,
                    "REFLECTION_NO_GETTER_FOUND", arguments);
            throw new PropertyNotFoundException(message);
        }
        return result;
    }

    private static BasicGetter getGetterOrNull(Class theClass, String propertyName) {

        if (theClass == Object.class || theClass == null) {
            return null;
        }

        Method method = getterMethod(theClass, propertyName);

        if (method != null) {
            if (!ReflectionSupport.isPublic(theClass, method)) {
                method.setAccessible(true);
            }
            return new BasicGetter(theClass, method, propertyName);
        } else {
            BasicGetter getter = getGetterOrNull(theClass.getSuperclass(), propertyName);
            if (getter == null) {
                Class[] interfaces = theClass.getInterfaces();
                for (int i = 0; getter == null && i < interfaces.length; i++) {
                    getter = getGetterOrNull(interfaces[i], propertyName);
                }
            }
            return getter;
        }
    }

    private static Method getterMethod(Class theClass, String propertyName) {

        Method[] methods = theClass.getDeclaredMethods();
        for (Method method : methods) {
            // only carry on if the method has no parameters
            if (method.getParameterTypes().length == 0) {
                String methodName = method.getName();

                // try "get"
                if (methodName.startsWith("get")) {
                    String testStdMethod = Introspector.decapitalize(methodName.substring(3));
                    String testOldMethod = methodName.substring(3);
                    if (testStdMethod.equals(propertyName) || testOldMethod.equals(propertyName)) {
                        return method;
                    }
                }

                // if not "get" then try "is"
                if (methodName.startsWith("is")) {
                    String testStdMethod = Introspector.decapitalize(methodName.substring(2));
                    String testOldMethod = methodName.substring(2);
                    if (testStdMethod.equals(propertyName) || testOldMethod.equals(propertyName)) {
                        return method;
                    }
                }
            }
        }
        return null;
    }

    // ------------------------------------------------------------------------

    public static final class BasicSetter implements Setter {
        private final Class clazz;
        private final Method method;
        private final String propertyName;

        private BasicSetter(Class clazz, Method method, String propertyName) {
            this.clazz = clazz;
            this.method = method;
            this.propertyName = propertyName;
        }

        public void set(Object target, Object value) throws PropertyAccessException {
            try {
                method.invoke(target, value);
            } catch (NullPointerException npe) {
                if (value == null && method.getParameterTypes()[0].isPrimitive()) {
                    Object[] arguments = {clazz.getName(), propertyName};
                    String message = ResourceBundle.formatResourceBundleMessage(BasicPropertyAccessor.class,
                            "BASIC_NULL_ASSIGNMENT_TO_PRIMITIVE", arguments);
                    throw new PropertyAccessException(message, npe);
                } else {
                    Object[] arguments = {clazz.getName(), propertyName};
                    String message = ResourceBundle.formatResourceBundleMessage(BasicPropertyAccessor.class,
                            "BASIC_ASSIGNMENT_NPE", arguments);
                    throw new PropertyAccessException(message, npe);
                }
            } catch (InvocationTargetException ite) {
                Object[] arguments = {clazz.getName(), propertyName};
                String message = ResourceBundle.formatResourceBundleMessage(BasicPropertyAccessor.class,
                        "BASIC_SETTER_ITE", arguments);
                throw new PropertyAccessException(message, ite);
            } catch (IllegalAccessException iae) {
                Object[] arguments = {clazz.getName(), propertyName};
                String message = ResourceBundle.formatResourceBundleMessage(BasicPropertyAccessor.class,
                        "REFLECTION_ILLEGAL_ACCESS", arguments);
                throw new PropertyAccessException(message, iae);
            } catch (IllegalArgumentException iae) {
                if (value == null && method.getParameterTypes()[0].isPrimitive()) {
                    Object[] arguments = {clazz.getName(), propertyName};
                    String message = ResourceBundle.formatResourceBundleMessage(BasicPropertyAccessor.class,
                            "BASIC_NULL_ASSIGNMENT_TO_PRIMITIVE", arguments);
                    throw new PropertyAccessException(message, iae);
                } else {
                    // possible mismatched types
                    Object[] arguments = {clazz.getName(), propertyName};
                    String message = ResourceBundle.formatResourceBundleMessage(BasicPropertyAccessor.class,
                            "REFLECTION_ILLEGAL_ARGUMENT_CALLING_SETTER", arguments);
                    throw new PropertyAccessException(message, iae);
                }
            }
        }

        public Method getMethod() {
            return method;
        }

        public String getMethodName() {
            return method.getName();
        }

        public String toString() {
            return "BasicSetter(" + clazz.getName() + '.' + propertyName + ')';
        }
    }

    public Setter getSetter(Class theClass, String propertyName)
            throws PropertyNotFoundException {
        return createSetter(theClass, propertyName);
    }

    private static Setter createSetter(Class theClass, String propertyName)
            throws PropertyNotFoundException {
        BasicSetter result = getSetterOrNull(theClass, propertyName);
        if (result == null) {
            Object[] arguments = {theClass.getName(), propertyName};
            String message = ResourceBundle.formatResourceBundleMessage(BasicPropertyAccessor.class,
                    "REFLECTION_NO_SETTER_FOUND", arguments);
            throw new PropertyNotFoundException(message);
        }
        return result;
    }

    private static BasicSetter getSetterOrNull(Class theClass, String propertyName) {

        if (theClass == Object.class || theClass == null) {
            return null;
        }

        Method method = setterMethod(theClass, propertyName);

        if (method != null) {
            if (!ReflectionSupport.isPublic(theClass, method)) method.setAccessible(true);
            return new BasicSetter(theClass, method, propertyName);
        } else {
            BasicSetter setter = getSetterOrNull(theClass.getSuperclass(), propertyName);
            if (setter == null) {
                Class[] interfaces = theClass.getInterfaces();
                for (int i = 0; setter == null && i < interfaces.length; i++) {
                    setter = getSetterOrNull(interfaces[i], propertyName);
                }
            }
            return setter;
        }

    }

    private static Method setterMethod(Class theClass, String propertyName) {

        BasicGetter getter = getGetterOrNull(theClass, propertyName);
        Class returnType = (getter == null) ? null : getter.getReturnType();

        Method[] methods = theClass.getDeclaredMethods();
        Method potentialSetter = null;
        for (Method method : methods) {
            String methodName = method.getName();
            if (method.getParameterTypes().length == 1 && methodName.startsWith("set")) {
                String testStdMethod = Introspector.decapitalize(methodName.substring(3));
                String testOldMethod = methodName.substring(3);
                if (testStdMethod.equals(propertyName) || testOldMethod.equals(propertyName)) {
                    potentialSetter = method;
                    if (returnType == null || method.getParameterTypes()[0].equals(returnType)) {
                        return potentialSetter;
                    }
                }
            }
        }
        return potentialSetter;
    }
}
