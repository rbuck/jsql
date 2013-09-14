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

package com.buck.jsql.sample.collections;

import com.buck.jsql.Query;
import com.buck.jsql.QueryException;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collection;
import java.util.Random;
import java.util.Vector;

/**
 * Tests querying a collection of person objects.
 *
 * @author Robert J. Buck
 */
public class PersonTestCase {

    @Test
    public void testPerformanceOfApplies() throws QueryException {
        final int count = 1000;
        final int repeat = 1000;
        long nres = 0;
        Vector<Person> people = new Vector<Person>();
        Random r = new Random();
        for (int i = 0; i < count; i++) {
            Person person = new Person();
            person.setAge(r.nextInt(100));
            people.add(person);
        }
        final long start = System.currentTimeMillis();
        Query<Person> query = new Query<Person>(Person.class, "age >= 50");
        Query.Apply<Person> fire = new Query.Apply<Person>() {
            public void action(Person employee) {
                employee.yourFired();
            }
        };
        Query.Apply<Person> hire = new Query.Apply<Person>() {
            public void action(Person employee) {
                employee.yourHired();
            }
        };
        for (int i = 0; i < repeat; i++) {
            query.apply(people, fire);
            query.apply(people, hire);
        }
        final long end = System.currentTimeMillis();
        final long elapsed = end - start;
        final double rate = ((double) count * repeat) * 1000 / ((double) elapsed);
        System.out.println("Apply Performance: ");
        System.out.println("\trate: " + rate);

    }

    @Test
    public void testPerformanceOfQueries() throws QueryException {
        final int count = 1000;
        final int repeat = 1000;
        long nres = 0;
        Vector<Person> people = new Vector<Person>();
        Random r = new Random();
        for (int i = 0; i < count; i++) {
            Person person = new Person();
            person.setAge(r.nextInt(100));
            people.add(person);
        }
        final long start = System.currentTimeMillis();
        Query<Person> squery = new Query<Person>(Person.class, "age >= 50 AND firedNearRetirement IS FALSE");
        for (int i = 0; i < repeat; i++) {
            Collection<Person> results = squery.select(people);
            nres += results.size();
            for (Person p : results) {
                p.getAge();
            }
        }
        final long end = System.currentTimeMillis();
        final long elapsed = end - start;
        final double rate = ((double) count * repeat) * 1000 / ((double) elapsed);
        System.out.println("Query Performance: ");
        System.out.println("\trate: " + rate);
        System.out.println("\tnres: " + nres);
    }

    @Test
    public void testQueryOfPersons() throws QueryException {

        final int count = 1000;

        // generate seed data

        Vector<Person> people = new Vector<Person>();
        Random r = new Random();
        for (int i = 0; i < count; i++) {
            Person person = new Person();
            person.setAge(r.nextInt(100));
            people.add(person);
        }

        // create query

        // US companies fire all employees approaching retirement age for cost
        // savings reasons.
        Query<Person> query = new Query<Person>(Person.class, "age >= 50");
        Query.Apply<Person> apply = new Query.Apply<Person>() {
            public void action(Person employee) {
                employee.yourFired();
            }
        };
        query.apply(people, apply);

        // benchmark

        // US companies check to make sure there are no stragglers.

        Query<Person> squery = new Query<Person>(Person.class, "age < 50 AND firedNearRetirement IS TRUE");
        Collection<Person> stragglers = squery.select(people);
        Assert.assertEquals(0, stragglers.size());
    }

    @Test
    public void testLikePredicate() throws QueryException {
        Vector<Person> people = new Vector<Person>();
        Person p = new Person();
        p.setName("bob");
        people.add(p);
        Query<Person> query = new Query<Person>(Person.class, "name LIKE '_ob'");
        Collection<Person> results = query.select(people);
        Assert.assertEquals(1, results.size());
    }

    @Test
    public void testNullPredicate() throws QueryException {
        Vector<Person> people = new Vector<Person>();
        Person p = new Person();
        people.add(p);
        Query<Person> query = new Query<Person>(Person.class, "name is NULL");
        Collection<Person> results = query.select(people);
        Assert.assertEquals(1, results.size());
    }

    @Test
    public void testNotNullPredicate() throws QueryException {
        Vector<Person> people = new Vector<Person>();
        Person p = new Person();
        people.add(p);
        Query<Person> query = new Query<Person>(Person.class, "name is not NULL");
        Collection<Person> results = query.select(people);
        Assert.assertEquals(0, results.size());
    }

    @Test
    public void testBetweenPredicate() throws QueryException {
        Vector<Person> people = new Vector<Person>();
        Person p = new Person();
        p.setAge(54);
        people.add(p);
        Query<Person> query = new Query<Person>(Person.class, "age between 50 and 60");
        Collection<Person> results = query.select(people);
        Assert.assertEquals(1, results.size());
    }

    @Test
    public void testNotBetweenPredicate() throws QueryException {
        Vector<Person> people = new Vector<Person>();
        Person p = new Person();
        p.setAge(54);
        people.add(p);
        Query<Person> query = new Query<Person>(Person.class, "age not between 50 and 60");
        Collection<Person> results = query.select(people);
        Assert.assertEquals(0, results.size());
    }

    @Test
    public void testBooleanTest() throws QueryException {
        Vector<Person> people = new Vector<Person>();
        Person p = new Person();
        people.add(p);
        Query<Person> query = new Query<Person>(Person.class, "firedNearRetirement is false");
        Collection<Person> results = query.select(people);
        Assert.assertEquals(1, results.size());
    }

    @Test
    public void testNotBooleanTest() throws QueryException {
        Vector<Person> people = new Vector<Person>();
        Person p = new Person();
        people.add(p);
        Query<Person> query = new Query<Person>(Person.class, "firedNearRetirement is not false");
        Collection<Person> results = query.select(people);
        Assert.assertEquals(0, results.size());
    }
}
