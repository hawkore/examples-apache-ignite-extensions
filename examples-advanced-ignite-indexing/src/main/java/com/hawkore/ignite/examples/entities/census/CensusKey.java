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

import org.apache.ignite.cache.affinity.AffinityKeyMapped;
import org.apache.ignite.cache.query.annotations.QuerySqlField;

/**
 * CensusKey 
 *
 * @author Manuel Núñez (manuel.nunez@hawkore.com)
 *
 *
 */
public class CensusKey {
    
    @QuerySqlField
    @AffinityKeyMapped
    private String name;
    
    @QuerySqlField
    private String vtFrom;    
    
    @QuerySqlField
    private String ttFrom;

    /**
     * 
     */
    public CensusKey() {
        // default constructor
    }
    
    /**
     * 
     * @param name
     * @param vtFrom
     * @param ttFrom
     */
    public CensusKey(String name, String vtFrom, String ttFrom) {
        super();
        this.name = name;
        this.vtFrom = vtFrom;
        this.ttFrom = ttFrom;
    }

    
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the vtFrom
     */
    public String getVtFrom() {
        return vtFrom;
    }

    /**
     * @param vtFrom the vtFrom to set
     */
    public void setVtFrom(String vtFrom) {
        this.vtFrom = vtFrom;
    }

    /**
     * @return the ttFrom
     */
    public String getTtFrom() {
        return ttFrom;
    }

    /**
     * @param ttFrom the ttFrom to set
     */
    public void setTtFrom(String ttFrom) {
        this.ttFrom = ttFrom;
    }
}
