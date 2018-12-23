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
package com.hawkore.ignite.examples.entities.tweets;

import java.util.Date;

import org.apache.ignite.cache.query.annotations.QuerySqlField;
import org.apache.ignite.cache.query.annotations.QueryTextField;
import org.apache.ignite.cache.query.annotations.QueryTextField.DateMapper;
import org.apache.ignite.cache.query.annotations.QueryTextField.GeoShapeMapper;
import org.apache.ignite.cache.query.annotations.QueryTextField.GeoShapeMapper.GeoTransformation;
import org.apache.ignite.cache.query.annotations.QueryTextField.GeoTransformationType;
import org.apache.ignite.cache.query.annotations.QueryTextField.IndexOptions;
import org.apache.ignite.cache.query.annotations.QueryTextField.IntegerMapper;
import org.apache.ignite.cache.query.annotations.QueryTextField.StringMapper;
import org.apache.ignite.cache.query.annotations.QueryTextField.TextMapper;
import org.locationtech.jts.geom.Geometry;

/**
 * Tweet
 *
 * @author Manuel Núñez (manuel.nunez@hawkore.com)
 *
 */

@QueryTextField(
    // Index will be refreshed every 60 seconds, with lucene index optimization every
    // 10 minutes
    indexOptions = @IndexOptions(refreshSeconds = 60, partitions = 10, optimizerSchedule = "*/10 * * * *"),

    // this will create a field named "id" into Lucene Document that will be
    // indexed; mapped to composed primary key TweetKey's @QuerySqlField "id"
    integerMappers = @IntegerMapper(name = "id"),

    // this will create a field named "countryCode" into Lucene Document that
    // will be indexed; mapped to composed primary TweetKey's @QuerySqlField
    // "countryCode"
    stringMappers = @StringMapper(name = "countryCode"))
public class Tweet {

    private TweetKey key;

    // this will create an field named "user" into Lucene Document that will be
    // indexed
    @QueryTextField(stringMappers = @StringMapper())
    @QuerySqlField
    private String user;

    // this will create an field named "body" into Lucene Document that will be
    // indexed
    @QueryTextField(textMappers = @TextMapper(analyzer = "english"))
    @QuerySqlField
    private String body;

    // this will create an field named "time" into Lucene Document that will be
    // indexed
    @QueryTextField(dateMappers = @DateMapper(pattern = "yyyy/MM/dd"))
    @QuerySqlField
    private Date time;

    /**
     * WGS84 coordinates lat/lon - EPSG:4326 - SRID
     * <p>
     * Added double index for testing purposes:
     * <ul>
     * <li>Grid H2 geospatial index
     * <li>
     * <li>Lucene indexed field with geoShapeMapper
     * <li>
     * </ul>
     */
    @QuerySqlField(index = true)
    @QueryTextField(geoShapeMappers = {
            @GeoShapeMapper(transformations = @GeoTransformation(type = GeoTransformationType.CENTROID))
    })
    private Geometry place;

    /**
     * 
     */
    public Tweet() {
        // default constructor
    }

    /**
     * 
     * @param id
     *            - tweet id
     * @param countryCode
     *            - tweet country code, key for affinity collocation
     * @param place
     *            - Geometry shape
     */
    public Tweet(Integer id, String countryCode, Geometry place) {
        this.key = new TweetKey(id, countryCode);
        this.place = place;
    }

    /**
     * @return the user
     */
    public String getUser() {
        return user;
    }

    /**
     * @param user
     *            the user to set
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * @return the body
     */
    public String getBody() {
        return body;
    }

    /**
     * @param body
     *            the body to set
     */
    public void setBody(String body) {
        this.body = body;
    }

    /**
     * @return the time
     */
    public Date getTime() {
        return time;
    }

    /**
     * @param time
     *            the time to set
     */
    public void setTime(Date time) {
        this.time = time;
    }

    /**
     * @return the place
     */
    public Geometry getPlace() {
        return place;
    }

    /**
     * @param place
     *            the place to set
     */
    public void setPlace(Geometry place) {
        this.place = place;
    }

    /**
     * @return the key
     */
    public TweetKey getKey() {
        return key;
    }
}
