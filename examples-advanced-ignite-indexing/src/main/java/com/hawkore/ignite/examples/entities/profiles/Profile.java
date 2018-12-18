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

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ignite.cache.query.annotations.QuerySqlField;
import org.apache.ignite.cache.query.annotations.QueryTextField;
import org.apache.ignite.cache.query.annotations.QueryTextField.IntegerMapper;
import org.apache.ignite.cache.query.annotations.QueryTextField.StringMapper;

/**
 * Profile 
 *
 * @author Manuel Núñez (manuel.nunez@hawkore.com)
 *
 *
 */
public class Profile {

    @QuerySqlField(index=true)
    private String login;
    
    @QuerySqlField
    private String firstName;
    
    @QuerySqlField
    private String lastName ;
    
    /**
     * Simple types can be indexed inside collections
     */
    @QueryTextField(stringMappers=@StringMapper(case_sensitive=false))
    @QuerySqlField
    private ArrayList<String> cities;
    
    /**
     * Simple types can be indexed inside maps
     */
    @QueryTextField(
        stringMappers={
                // below  mapper is equivalent to @StringMapper(name="textAddresses", case_sensitive=true)
                // as if name is not provided will takes annotated property name
                @StringMapper(case_sensitive=true),
                @StringMapper(name="textAddresses._key", case_sensitive=true),
                @StringMapper(name="textAddresses._value", case_sensitive=false)
                }
    )
    @QuerySqlField
    private HashMap<String, String> textAddresses;

    /**
     * Complex java type's components can be indexed
     * 
     * <p>
     * NOTE that if components within Address were not annotated with {@code @QueryTextField} you need to
     * define index here as follows:
     * 
     * <pre>
     * {@code @QueryTextField}(
     *   stringMappers={
     *           {@code @StringMapper}(name="address.city", case_sensitive=false)
     *           },
     *   integerMappers = {@code @IntegerMapper}(name="address.zip")
     * )
     * </pre>
     */
    @QueryTextField
    @QuerySqlField (hidden=true)
    private Address address;
    
   /**
    * Complex java type's components can be indexed even while being inside maps
    * 
    * <p>
    * NOTE that components within Address do not require to be annotated with {@code @QueryTextField}
    * as index MUST be defined here
    */
    @QueryTextField(
        stringMappers={
                @StringMapper(name="addresses._key", case_sensitive=true),
                @StringMapper(name="addresses.city", case_sensitive=false)
                },
        integerMappers = @IntegerMapper(name="addresses.zip")
    )
    @QuerySqlField
    private HashMap<String, Address> addresses;
    
    /**
     * Complex java type's components can be indexed even while being inside collections
     * 
     * <p>
     * NOTE that components within Address do not require to be annotated with {@code @QueryTextField}
     * as index MUST be defined here
     * 
     */
    @QueryTextField(
        stringMappers={
                @StringMapper(name="addressList.city", case_sensitive=false)
                },
        integerMappers = @IntegerMapper(name="addressList.zip")
    )
    @QuerySqlField
    private ArrayList<Address> addressList;

    /**
     * 
     */
    public Profile() {
        
    }
    
    /**
     * @return the addressList
     */
    public ArrayList<Address> getAddressList() {
        return addressList;
    }

    /**
     * @param addressList the addressList to set
     */
    public void setAddressList(ArrayList<Address> addressList) {
        this.addressList = addressList;
    }

    
    /**
     * @return the login
     */
    public String getLogin() {
        return login;
    }

    /**
     * @param login the login to set
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName the firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return the address
     */
    public Address getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(Address address) {
        this.address = address;
    }

    /**
     * @return the cities
     */
    public ArrayList<String> getCities() {
        return cities;
    }

    /**
     * @param cities the cities to set
     */
    public void setCities(ArrayList<String> cities) {
        this.cities = cities;
    }

    /**
     * @return the addresses
     */
    public HashMap<String, Address> getAddresses() {
        return addresses;
    }

    /**
     * @param addresses the addresses to set
     */
    public void setAddresses(HashMap<String, Address> addresses) {
        this.addresses = addresses;
    }

    /**
     * @return the textAddresses
     */
    public HashMap<String, String> getTextAddresses() {
        return textAddresses;
    }

    /**
     * @param textAddresses the textAddresses to set
     */
    public void setTextAddresses(HashMap<String, String> textAddresses) {
        this.textAddresses = textAddresses;
    }
    
    
}
