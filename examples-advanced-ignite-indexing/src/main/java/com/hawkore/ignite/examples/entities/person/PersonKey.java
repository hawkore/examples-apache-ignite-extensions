/*
 * Copyright (C) 2018 HAWKORE S.L. (http://hawkore.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hawkore.ignite.examples.entities.person;

import org.apache.ignite.cache.affinity.AffinityKeyMapped;

/**
 * PersonKey for QueryEntity Based Configuration
 *
 * @author Manuel Núñez (manuel.nunez@hawkore.com)
 * 
 */
public class PersonKey {

    @AffinityKeyMapped
    private String name;

    private String gender;

    private String animal;

    private int age;

    /**
     * 
     */
    public PersonKey() {
        // default constructor
    }


    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * @param gender
     *            the gender to set
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * @return the animal
     */
    public String getAnimal() {
        return animal;
    }

    /**
     * @param animal
     *            the animal to set
     */
    public void setAnimal(String animal) {
        this.animal = animal;
    }

    /**
     * @return the age
     */
    public int getAge() {
        return age;
    }

    /**
     * @param age
     *            the age to set
     */
    public void setAge(int age) {
        this.age = age;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "PersonKey [name=" + name + ", gender=" + gender + ", animal=" + animal + ", age=" + age + "]";
    }
}
