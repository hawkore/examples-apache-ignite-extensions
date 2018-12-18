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
package com.hawkore.ignite.examples.entities.profiles;

import org.apache.ignite.cache.query.annotations.QuerySqlField;
import org.apache.ignite.cache.query.annotations.QueryTextField;
import org.apache.ignite.cache.query.annotations.QueryTextField.IntegerMapper;
import org.apache.ignite.cache.query.annotations.QueryTextField.StringMapper;

/**
 * Address
 *
 * <p>
 *  
 * NOTE that below {@code @QueryTextField} annotations will be used for complex
 * java type indexing of Address as a direct member of Profile (out of map and collections).
 * 
 * <p>
 * 
 * They are not required if you define index within Profile.
 *
 * @author Manuel Núñez (manuel.nunez@hawkore.com)
 * 
 */
public class Address {

    @QuerySqlField
    private String street;

    @QueryTextField(stringMappers = @StringMapper(case_sensitive = false))
    @QuerySqlField
    private String city;

    @QueryTextField(integerMappers = @IntegerMapper)
    @QuerySqlField
    private int zip;

    /**
     * 
     */
    public Address() {

    }

    /**
     * 
     * @param city
     * @param street
     * @param zip
     */
    public Address(String city, String street, int zip) {
        super();
        this.city = city;
        this.street = street;
        this.zip = zip;
    }

    /**
     * @return the street
     */
    public String getStreet() {
        return street;
    }

    /**
     * @param street
     *            the street to set
     */
    public void setStreet(String street) {
        this.street = street;
    }

    /**
     * @return the city
     */
    public String getCity() {
        return city;
    }

    /**
     * @param city
     *            the city to set
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * @return the zip
     */
    public int getZip() {
        return zip;
    }

    /**
     * @param zip
     *            the zip to set
     */
    public void setZip(int zip) {
        this.zip = zip;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Address [street=" + street + ", city=" + city + ", zip=" + zip + "]";
    }

}
