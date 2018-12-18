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
package com.hawkore.ignite.examples.entities.person2;

import org.apache.ignite.cache.affinity.AffinityKeyMapped;
import org.apache.ignite.cache.query.annotations.QuerySqlField;
import org.apache.ignite.cache.query.annotations.QueryTextField;
import org.apache.ignite.cache.query.annotations.QueryTextField.IntegerMapper;
import org.apache.ignite.cache.query.annotations.QueryTextField.StringMapper;

/**
 * PersonKey for Annotation Based Configuration
 *
 * @author Manuel Núñez (manuel.nunez@hawkore.com)
 * 
 */
public class PersonKey {

    @QuerySqlField(index = true)
    @QueryTextField(stringMappers = @StringMapper)
    @AffinityKeyMapped
    private String name;

    @QuerySqlField(index = true)
    @QueryTextField(stringMappers = @StringMapper(validated = true))
    private String gender;

    @QuerySqlField(index = true)
    @QueryTextField(stringMappers = @StringMapper)
    private String animal;

    @QuerySqlField(index = true)
    @QueryTextField(integerMappers = @IntegerMapper)
    private int age;

    /**
     * 
     */
    public PersonKey() {
        // default constructor
    }

    /**
     * 
     * @param name
     * @param gender
     * @param animal
     * @param age
     */
    public PersonKey(String name, String gender, String animal, int age) {
        super();
        this.name = name;
        this.gender = gender;
        this.animal = animal;
        this.age = age;
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
