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

import org.apache.ignite.cache.affinity.AffinityKeyMapped;
import org.apache.ignite.cache.query.annotations.QuerySqlField;

/**
 * 
 * TweetKey for affinity collocation based on country code
 *
 * @author Manuel Núñez (manuel.nunez@hawkore.com)
 *
 *
 */
public class TweetKey {

    // index=true will create a Grid H2 index (TWEETKEY_ID_IDX index name) for
    // non lucene searches
    @QuerySqlField(index = true)
    private Integer id;

    // affinity key annotated is auto-indexed on a Grid H2 index (AFFINITY_KEY
    // index name) for non lucene searches annotated it with @QuerySqlField to publish as table column to allow use
    // it from a lucene index mapper
    @QuerySqlField
    @AffinityKeyMapped
    private String countryCode;

    /**
     * 
     */
    public TweetKey() {
        // default constructor
    }

    /**
     * 
     * @param id
     *            - tweet id
     * @param countryCode
     *            - tweet country code, key for affinity collocation
     */
    public TweetKey(Integer id, String countryCode) {
        super();
        this.id = id;
        this.countryCode = countryCode;
    }

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @return the countryCode
     */
    public String getCountryCode() {
        return countryCode;
    }
}
