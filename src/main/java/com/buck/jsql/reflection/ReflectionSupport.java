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

import java.lang.reflect.Member;
import java.lang.reflect.Modifier;

/**
 * Miscellaneous reflection support.
 *
 * @author Robert J. Buck
 */
@SuppressWarnings({"FieldCanBeLocal"})
public final class ReflectionSupport {

    private static final PropertyAccessor BASIC_PROPERTY_ACCESSOR = new BasicPropertyAccessor();
    private static final PropertyAccessor DIRECT_PROPERTY_ACCESSOR = new DirectPropertyAccessor();

    public static Getter getGetter(Class clazz, String name) throws PropertyNotFoundException {
        try {
            return BASIC_PROPERTY_ACCESSOR.getGetter(clazz, name);
        } catch (PropertyNotFoundException pnfe) {
            return DIRECT_PROPERTY_ACCESSOR.getGetter(clazz, name);
        }
    }

    public static Class classForName(String name) throws ClassNotFoundException {
        try {
            ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
            if (contextClassLoader != null) {
                return contextClassLoader.loadClass(name);
            }
        } catch (Throwable t) {
            // ignore
        }
        return Class.forName(name);
    }

    public static boolean isPublic(Class clazz, Member member) {
        return Modifier.isPublic(member.getModifiers()) && Modifier.isPublic(clazz.getModifiers());
    }
}
