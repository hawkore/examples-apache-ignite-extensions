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

/**
 * Person for QueryEntity Based Configuration
 *
 * @author Manuel Núñez (manuel.nunez@hawkore.com)
 * 
 */

public class Person {

    private String food;

    private float latitude;

    private float longitude;

    private int number;

    private boolean bool;

    private String phrase;

    private String date;

    private String startDate;

    private String stopDate;

    
    /**
     * 
     */
    public Person() {
        // default constructor
    }


    /**
     * @return the food
     */
    public String getFood() {
        return food;
    }

    /**
     * @param food
     *            the food to set
     */
    public void setFood(String food) {
        this.food = food;
    }

    /**
     * @return the latitude
     */
    public float getLatitude() {
        return latitude;
    }

    /**
     * @param latitude
     *            the latitude to set
     */
    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    /**
     * @return the longitude
     */
    public float getLongitude() {
        return longitude;
    }

    /**
     * @param longitude
     *            the longitude to set
     */
    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    /**
     * @return the number
     */
    public int getNumber() {
        return number;
    }

    /**
     * @param number
     *            the number to set
     */
    public void setNumber(int number) {
        this.number = number;
    }

    /**
     * @return the bool
     */
    public boolean isBool() {
        return bool;
    }

    /**
     * @param bool
     *            the bool to set
     */
    public void setBool(boolean bool) {
        this.bool = bool;
    }

    /**
     * @return the phrase
     */
    public String getPhrase() {
        return phrase;
    }

    /**
     * @param phrase
     *            the phrase to set
     */
    public void setPhrase(String phrase) {
        this.phrase = phrase;
    }

    /**
     * @return the date
     */
    public String getDate() {
        return date;
    }

    /**
     * @param date
     *            the date to set
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * @return the startDate
     */
    public String getStartDate() {
        return startDate;
    }

    /**
     * @param startDate
     *            the startDate to set
     */
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    /**
     * @return the stopDate
     */
    public String getStopDate() {
        return stopDate;
    }

    /**
     * @param stopDate
     *            the stopDate to set
     */
    public void setStopDate(String stopDate) {
        this.stopDate = stopDate;
    }
}
