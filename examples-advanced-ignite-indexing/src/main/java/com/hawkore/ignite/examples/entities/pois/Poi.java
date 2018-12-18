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
package com.hawkore.ignite.examples.entities.pois;

import org.apache.ignite.cache.query.annotations.QuerySqlField;
import org.apache.ignite.cache.query.annotations.QueryTextField;
import org.apache.ignite.cache.query.annotations.QueryTextField.GeoShapeMapper;
import org.apache.ignite.cache.query.annotations.QueryTextField.GeoShapeMapper.GeoTransformation;
import org.apache.ignite.cache.query.annotations.QueryTextField.GeoTransformationType;
import org.apache.ignite.cache.query.annotations.QueryTextField.IndexOptions;
import org.apache.ignite.cache.query.annotations.QueryTextField.IntegerMapper;
import org.apache.ignite.cache.query.annotations.QueryTextField.StringMapper;

import com.hawkore.ignite.lucene.MultiValueIsoText;
import com.vividsolutions.jts.geom.Geometry;

/**
 * Poi
 *
 * @author Manuel Núñez (manuel.nunez@hawkore.com)
 *
 */

@QueryTextField(
    // Index will be refreshed every 60 seconds, with lucene index optimization every
    // 10 minutes
    indexOptions = @IndexOptions(refreshSeconds = 60, partitions = 10, optimizerSchedule = "*/10 * * * *"),

    // this will create a field named "id" into Lucene Document that will be
    // indexed; mapped to composed primary key PoiKey's @QuerySqlField "id"
    integerMappers = @IntegerMapper(name = "id"),

    // this will create a field named "countryCode" into Lucene Document that
    // will be indexed; mapped to composed primary PoiKey's @QuerySqlField
    // "countryCode"
    stringMappers = @StringMapper(name = "countryCode"))
public class Poi {

    private PoiKey key;

    @QueryTextField
    @QuerySqlField
    private MultiValueIsoText name;


    @QueryTextField
    @QuerySqlField
    private MultiValueIsoText description;

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

    @QuerySqlField(index = true)
    private String type;
    
    /**
     * 
     */
    public Poi() {
        // default constructor
    }

    /**
     * 
     * @param id
     *            - poi id
     * @param countryCode
     *            - poi country code, key for affinity collocation
     * @param place
     *            - Geometry place
     * @param type
     *            - the poi type       
     */
    public Poi(Integer id, String countryCode, Geometry place, PoiType type) {
        this.key = new PoiKey(id, countryCode);
        this.place = place;
        this.type = type.toString();
    }

    /**
     * @return the key
     */
    public PoiKey getKey() {
        return key;
    }

    /**
     * @param key the key to set
     */
    public void setKey(PoiKey key) {
        this.key = key;
    }

    /**
     * @return the name
     */
    public MultiValueIsoText getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(MultiValueIsoText name) {
        this.name = name;
    }

    /**
     * @return the description
     */
    public MultiValueIsoText getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(MultiValueIsoText description) {
        this.description = description;
    }

    /**
     * @return the place
     */
    public Geometry getPlace() {
        return place;
    }

    /**
     * @param place the place to set
     */
    public void setPlace(Geometry place) {
        this.place = place;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }
}
