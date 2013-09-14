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

/**
 * Represents a person.
 *
 * @author Robert J. Buck
 */
public class Person {

    private int age;

    public static enum Gender {
        MALE,
        FEMALE
    }

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private Gender gender;

    private boolean firedNearRetirement = false;

    public Person() {
    }

    final void yourFired() {
        firedNearRetirement = true;
    }

    final void yourHired() {
        firedNearRetirement = false;
    }

    final boolean isFiredNearRetirement() {
        return firedNearRetirement;
    }

    final int getAge() {
        return age;
    }

    final void setAge(int age) {
        this.age = age;
    }

    final Gender getGender() {
        return gender;
    }

    final void setGender(Gender gender) {
        this.gender = gender;
    }
}
