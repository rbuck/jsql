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

import com.buck.commons.i18n.ResourceBundle;
import com.buck.jsql.identifiers.Identifier;
import com.buck.jsql.reflection.Getter;
import com.buck.jsql.reflection.PropertyAccessException;
import com.buck.jsql.reflection.PropertyNotFoundException;
import com.buck.jsql.reflection.ReflectionSupport;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeMap;

/**
 * Queries a collection of objects.
 *
 * @author Robert J. Buck
 */
public class Query<T> {

    private final Expression expression;
    private final TreeMap<Identifier, Getter> accessors;

    /**
     * Create a Query object.
     *
     * @param type  the class that provides the context in which the query is interpreted
     * @param query the query expression to execute.
     * @throws QueryException if an exception occurred while parsing the query
     *                        or binding to POJO fields
     */
    public Query(Class type, String query) throws QueryException {
        SQLParser parser = new SQLParser();
        SQLParser.ParserState state = parser.parse(query);
        expression = state.getRoot();

        // establish the property accessors
        accessors = new TreeMap<Identifier, Getter>();
        Collection<Identifier> identifiers = state.getIdentifiers();
        for (Identifier id : identifiers) {
            try {
                accessors.put(id, ReflectionSupport.getGetter(type, id.toString()));
            } catch (PropertyNotFoundException e) {
                Object[] arguments = {id.toString(), type.getName()};
                String message = ResourceBundle.formatResourceBundleMessage(Query.class,
                        "QUERY_PROPERTY_NOT_FOUND", arguments);
                throw new QueryException(message, e);
            }
        }
    }

    public Set<T> select(Collection<T> coll) throws QueryException {
        Set<T> set = new HashSet<T>();
        for (final T object : coll) {
            boolean select = (Boolean) expression.evaluate(new EvaluationContext() {
                public Object getValue(Identifier identifier) throws PropertyAccessException {
                    return accessors.get(identifier).get(object);
                }
            });
            if (select) {
                set.add(object);
            }
        }
        return set;
    }

    /**
     * An action that may be applied to elements in a collection that satisfy
     * the query.
     */
    public interface Apply<T> {
        public void action(T object);
    }

    /**
     * Applies an action to objects from a collection which satisfy the query.
     *
     * @param coll  the collection to query and apply an action to
     * @param apply the action to apply to objects satisfying the query
     * @throws QueryException if an exception occurred while parsing the query
     *                        or binding to POJO fields
     */
    public void apply(Collection<T> coll, Apply<T> apply) throws QueryException {
        for (final T object : coll) {
            boolean select = (Boolean) expression.evaluate(new EvaluationContext() {
                public Object getValue(Identifier identifier) throws PropertyAccessException {
                    return accessors.get(identifier).get(object);
                }
            });
            if (select) {
                apply.action(object);
            }
        }
    }
}
