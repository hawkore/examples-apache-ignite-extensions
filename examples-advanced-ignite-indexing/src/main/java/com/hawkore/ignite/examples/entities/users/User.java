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
package com.hawkore.ignite.examples.entities.users;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import org.apache.ignite.cache.query.annotations.QuerySqlField;
import org.apache.ignite.cache.query.annotations.QueryTextField;
import org.apache.ignite.cache.query.annotations.QueryTextField.BooleanMapper;
import org.apache.ignite.cache.query.annotations.QueryTextField.DateMapper;
import org.apache.ignite.cache.query.annotations.QueryTextField.DateRangeMapper;
import org.apache.ignite.cache.query.annotations.QueryTextField.GeoPointMapper;
import org.apache.ignite.cache.query.annotations.QueryTextField.IndexOptions;
import org.apache.ignite.cache.query.annotations.QueryTextField.IntegerMapper;
import org.apache.ignite.cache.query.annotations.QueryTextField.SnowballAnalyzer;
import org.apache.ignite.cache.query.annotations.QueryTextField.StringMapper;
import org.apache.ignite.cache.query.annotations.QueryTextField.TextMapper;

/**
 * User
 *
 * @author Manuel Núñez (manuel.nunez@hawkore.com)
 * 
 */

@QueryTextField(
    // Index configuration
    indexOptions = @IndexOptions(refreshSeconds = 60, partitions = 10, ramBufferMB = 10, defaultAnalyzer = "english", snowballAnalyzers = {
            @SnowballAnalyzer(name = "my_custom_analyzer", language = "Spanish", stopwords = "el,la,lo,loas,las,a,ante,bajo,cabe,con,contra")
    }),

    // this will create an additional field named "place" into Lucene Document
    // that will be indexed
    geoPointMappers = @GeoPointMapper(name = "place", latitude = "latitude", longitude = "longitude"),

    // this will create an additional field named "duration" into Lucene
    // Document
    // that will be indexed
    dateRangeMappers = @DateRangeMapper(name = "duration", from = "start_date", to = "stop_date", pattern = "yyyy/MM/dd")

)
public class User {

    @QuerySqlField(index = true)
    @QueryTextField(stringMappers = @StringMapper)
    private String food;

    @QuerySqlField
    private float latitude;

    @QuerySqlField
    private float longitude;

    @QueryTextField(integerMappers = @IntegerMapper)
    private int number;

    @QueryTextField(booleanMappers = @BooleanMapper)
    private boolean bool;

    @QuerySqlField
    @QueryTextField(textMappers = @TextMapper(analyzer = "my_custom_analyzer"))
    private String phrase;

    @QueryTextField(dateMappers = @DateMapper(validated = true, pattern = "yyyy/MM/dd"))
    private String date;

    @QuerySqlField(name = "start_date")
    private String startDate;

    @QuerySqlField(name = "stop_date")
    private String stopDate;

    /** must be a concrete Set implementation */
    @QueryTextField(stringMappers = @StringMapper)
    private HashSet<String> setz;

    /** must be a concrete List implementation */
    @QueryTextField(stringMappers = @StringMapper)
    private ArrayList<String> listz;

    /** must be a concrete Map implementation */
    @QueryTextField(stringMappers = @StringMapper)
    private HashMap<String, String> mapz;

    /**
     * 
     */
    public User() {
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

    /**
     * @return the setz
     */
    public HashSet<String> getSetz() {
        return setz;
    }

    /**
     * @param setz
     *            the setz to set
     */
    public void setSetz(HashSet<String> setz) {
        this.setz = setz;
    }

    /**
     * @return the listz
     */
    public ArrayList<String> getListz() {
        return listz;
    }

    /**
     * 
     * @param listz
     *            the listz to set
     */
    public void setListz(ArrayList<String> listz) {
        this.listz = listz;
    }

    /**
     * 
     * @return the mapz
     */
    public HashMap<String, String> getMapz() {
        return mapz;
    }

    /**
     * 
     * @param mapz
     *            the mapz to set
     */
    public void setMapz(HashMap<String, String> mapz) {
        this.mapz = mapz;
    }

}
