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
package com.hawkore.ignite.examples.entities.blog;

import java.util.ArrayList;

import org.apache.ignite.cache.query.annotations.QuerySqlField;
import org.apache.ignite.cache.query.annotations.QueryTextField;
import org.apache.ignite.cache.query.annotations.QueryTextField.StringMapper;
import org.apache.ignite.cache.query.annotations.QueryTextField.TextMapper;

/**
 * BlogEntry 
 *
 * @author Manuel Núñez (manuel.nunez@hawkore.com)
 *
 *
 */
public class BlogEntry {

    @QuerySqlField
    @QueryTextField(textMappers=@TextMapper(analyzer="english"))
    private String comment;
    
    @QuerySqlField
    @QueryTextField(stringMappers=@StringMapper(case_sensitive=false))
    private ArrayList<String> tags;
    
    
    /**
     * 
     */
    public BlogEntry() {
        // TODO Auto-generated constructor stub
    }


    /**
     * @return the comment
     */
    public String getComment() {
        return comment;
    }


    /**
     * @param comment the comment to set
     */
    public void setComment(String comment) {
        this.comment = comment;
    }


    /**
     * @return the tags
     */
    public ArrayList<String> getTags() {
        return tags;
    }


    /**
     * @param tags the tags to set
     */
    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }
    
    
    
}
