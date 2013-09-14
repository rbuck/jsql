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

import org.junit.Assert;
import org.junit.Test;

import java.util.Set;
import java.util.Vector;

/**
 * Tests the query engine.
 *
 * @author Robert J. Buck
 */
public class QueryTestCase {
    @Test
    public void testMissingGetter() {
        final class Test {
            // no props
        }
        boolean caught = false;
        try {
            new Query<Test>(Test.class, "age >= 50");
        } catch (QueryException e) {
            caught = true;
        }
        Assert.assertTrue("Missing Getter", caught);
    }

    @Test
    public void testDoubleArithmetic() {
        Vector<Double> doubles = new Vector<Double>();
        doubles.add(3.14);

        // addition
        {
            boolean caught = false;
            try {
                Query<Double> query = new Query<Double>(Double.class, "1.5 + 2.0 > value");
                Set<Double> ans = query.select(doubles);
                Assert.assertEquals(1, ans.size());
            } catch (QueryException e) {
                caught = true;
            }
            Assert.assertFalse("Double Addition", caught);
        }
    }
}
