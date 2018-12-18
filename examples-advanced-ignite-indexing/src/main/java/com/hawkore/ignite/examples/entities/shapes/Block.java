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
package com.hawkore.ignite.examples.entities.shapes;

import org.apache.ignite.cache.query.annotations.QuerySqlField;
import org.apache.ignite.cache.query.annotations.QueryTextField;
import org.apache.ignite.cache.query.annotations.QueryTextField.GeoShapeMapper;
import org.apache.ignite.cache.query.annotations.QueryTextField.GeoShapeMapper.GeoTransformation;
import org.apache.ignite.cache.query.annotations.QueryTextField.GeoTransformationType;

/**
 * Border
 *
 * @author Manuel Núñez (manuel.nunez@hawkore.com)
 * 
 */
public class Block {

    @QuerySqlField(index = true, notNull=true)
    private int id;

    @QueryTextField(geoShapeMappers = {
            @GeoShapeMapper(name = "place", column="shape"),
            @GeoShapeMapper(name = "place_convexhull", column="shape", transformations = @GeoTransformation(type = GeoTransformationType.CONVEX_HULL)),
            @GeoShapeMapper(name = "place_bbox", column="shape", transformations = @GeoTransformation(type = GeoTransformationType.BBOX))
    })
    @QuerySqlField
    private String shape;

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the shape
     */
    public String getShape() {
        return shape;
    }

    /**
     * @param shape
     *            the shape to set
     */
    public void setShape(String shape) {
        this.shape = shape;
    }

}
