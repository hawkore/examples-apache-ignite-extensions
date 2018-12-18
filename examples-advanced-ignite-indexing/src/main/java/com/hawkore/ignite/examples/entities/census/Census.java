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
package com.hawkore.ignite.examples.entities.census;

import org.apache.ignite.cache.query.annotations.QuerySqlField;
import org.apache.ignite.cache.query.annotations.QueryTextField;
import org.apache.ignite.cache.query.annotations.QueryTextField.BitemporalMapper;
import org.apache.ignite.cache.query.annotations.QueryTextField.IndexOptions;

/**
 * Census
 *
 * @author Manuel Núñez (manuel.nunez@hawkore.com)
 *
 *
 */
@QueryTextField(
    // Index configuration
    indexOptions = @IndexOptions(refreshSeconds = 60, partitions = 10),

    bitemporalMappers = @BitemporalMapper(
        name= "bitemporal",
        tt_from = "ttFrom", tt_to = "ttTo", vt_from = "vtFrom", vt_to = "vtTo", pattern = "yyyy/MM/dd", now_value = "2200/12/31"
    )
)
public class Census {
    
    @QuerySqlField
    private String city;
    
    @QuerySqlField
    private String vtTo;
    
    @QuerySqlField
    private String ttTo;

    /**
     * 
     */
    public Census() {
        // default constructor
    }


    /**
     * @return the city
     */
    public String getCity() {
        return city;
    }

    /**
     * @param city the city to set
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * @return the vtTo
     */
    public String getVtTo() {
        return vtTo;
    }

    /**
     * @param vtTo the vtTo to set
     */
    public void setVtTo(String vtTo) {
        this.vtTo = vtTo;
    }

    /**
     * @return the ttTo
     */
    public String getTtTo() {
        return ttTo;
    }

    /**
     * @param ttTo the ttTo to set
     */
    public void setTtTo(String ttTo) {
        this.ttTo = ttTo;
    }

 
    
}
