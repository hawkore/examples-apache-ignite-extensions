-- create PERSON table with DDL CREATE TABLE statement

CREATE TABLE IF NOT EXISTS "PUBLIC".PERSON (
    name varchar,
    gender varchar,
    animal varchar,
    age int,
    food varchar,
    latitude decimal,
    longitude decimal,
    number int,
    bool boolean,
    phrase varchar,
    date varchar,
    start_date varchar,
    stop_date varchar,
  PRIMARY KEY (name, gender, animal, age)
) WITH "TEMPLATE=PARTITIONED, affinity_key=name";
  

-- create advanced lucene index with DDL CREATE INDEX statement
CREATE INDEX PERSON_LUCENE_IDX ON "PUBLIC".PERSON(LUCENE)  
FULLTEXT '{
''refresh_seconds'':''60'',
''directory_path'':'''',
''ram_buffer_mb'':''10'',
''max_cached_mb'':''-1'',
''partitioner'':''{"type":"token","partitions":10}'',
''optimizer_enabled'':''true'',
''optimizer_schedule'':''0 1 * * *'',
''version'':''0'',
''schema'':''{
    "default_analyzer":"english",
    "analyzers":{"my_custom_analyzer":{"type":"snowball","language":"Spanish","stopwords":"el,la,lo,loas,las,a,ante,bajo,cabe,con,contra"}},
    "fields":{
      "duration":{"type":"date_range","from":"start_date","to":"stop_date","validated":false,"pattern":"yyyy/MM/dd"},
      "place":{"type":"geo_point","latitude":"latitude","longitude":"longitude"},
      "date":{"type":"date","validated":true,"pattern":"yyyy/MM/dd"},
      "number":{"type":"integer","validated":false,"boost":1.0},
      "gender":{"type":"string","validated":true,"case_sensitive":true},
      "bool":{"type":"boolean","validated":false},
      "phrase":{"type":"text","validated":false,"analyzer":"my_custom_analyzer"},
      "name":{"type":"string","validated":false,"case_sensitive":true},
      "animal":{"type":"string","validated":false,"case_sensitive":true},
      "age":{"type":"integer","validated":false,"boost":1.0},
      "food":{"type":"string","validated":false,"case_sensitive":true}
    }
  }''
}';

-- insert some data

INSERT INTO "PUBLIC".PERSON (name, gender, age, animal, food, number, bool, date, start_date, stop_date, latitude, longitude) VALUES   ( 'Jane', 'female', -1, 'monkey', 'chips', 6, true, '2014/01/01', '2014/06/01','2014/06/30',-42.8, 12.4);
INSERT INTO "PUBLIC".PERSON (name, gender, age, animal, food, number, bool, date, start_date, stop_date, latitude, longitude) VALUES   ( 'James', 'male', 0, 'monkey', 'chips', 7, true, '2014/01/02', '2014/07/01','2014/07/31',-12, 13.6);
INSERT INTO "PUBLIC".PERSON (name, gender, age, animal, food, number, bool, date, start_date, stop_date, latitude, longitude) VALUES   ( 'Jane', 'female', 1, 'monkey', 'chips', 8, true, '2010/01/01', '2014/08/01','2014/08/31',-5.6, 34.6);
INSERT INTO "PUBLIC".PERSON (name, gender, age, animal, food, number, bool, start_date, stop_date, latitude, longitude) VALUES   ( 'Alicia', 'female', 2, 'monkey', 'chips', 9, true, '2014/09/01','2014/09/30',-32.3, -170.0);
INSERT INTO "PUBLIC".PERSON (name, gender, age, animal, food, number, bool, start_date, stop_date, latitude, longitude) VALUES   ( 'Joe', 'male', 2, 'monkey', 'chips', 10, false, '2014/10/01','2014/10/31',-32.8, 115.6);
INSERT INTO "PUBLIC".PERSON (name, gender, age, animal, food, number, bool, start_date, stop_date, latitude, longitude) VALUES   ( 'James', 'male', -2, 'monkey', 'chips', 11, false, '2014/11/01','2014/11/30',-21.4, 32.7);
INSERT INTO "PUBLIC".PERSON (name, gender, age, animal, food, number, bool, start_date, stop_date, latitude, longitude) VALUES   ( 'James', 'male', -1, 'monkey', 'chips', 12, false, '2014/12/01','2014/12/31',-33.2, 167.0);
INSERT INTO "PUBLIC".PERSON (name, gender, age, animal, food, number, bool, start_date, stop_date, latitude, longitude) VALUES   ( 'Alicia', 'female', 0, 'monkey', 'chips', 13, false, '2014/01/01','2014/06/30',65.5, 2.3);
INSERT INTO "PUBLIC".PERSON (name, gender, age, animal, food, number, bool, start_date, stop_date, latitude, longitude) VALUES   ( 'Joe', 'male', -1, 'monkey', 'chips', 14, false, '2014/02/01','2014/07/31',-22.1, 180.0);
INSERT INTO "PUBLIC".PERSON (name, gender, age, animal, food, number, bool, phrase, start_date, stop_date, latitude, longitude) VALUES   ( 'Joe', 'male', 0, 'monkey', 'chips', 15, false, 'Ligero como una pluma', '2014/03/01','2014/08/31',-35.4, 97.4);
INSERT INTO "PUBLIC".PERSON (name, gender, age, animal, food, number, bool, phrase, start_date, stop_date, latitude, longitude) VALUES   ( 'Alicia', 'female', -2, 'monkey', 'chips', 16, false, 'Rápido como un puma', '2014/04/01','2014/09/30',0.7, -167);
INSERT INTO "PUBLIC".PERSON (name, gender, age, animal, food, number, bool, phrase, start_date, stop_date, latitude, longitude) VALUES   ( 'Alicia', 'female', -1, 'monkey', 'chips', 17, false, 'Una mancha en la camisa morada', '2014/05/01','2014/10/31',-42.3, 25.5);
INSERT INTO "PUBLIC".PERSON (name, gender, age, animal, food, number, bool, phrase, start_date, stop_date, latitude, longitude) VALUES   ( 'Jane', 'female', 0, 'monkey', 'turkey', 18, false, 'Sopa de tortuga', '2014/06/01','2014/11/30',-12.8, 176);
INSERT INTO "PUBLIC".PERSON (name, gender, age, animal, food, number, bool, phrase, start_date, stop_date, latitude, longitude) VALUES   ( 'Alicia', 'female', 1, 'monkey', 'tuna', 19, false, 'En un lugar de la Mancha', '2014/07/01','2014/12/31',-0.5, 1.2);
INSERT INTO "PUBLIC".PERSON (name, gender, age, animal, food, number, bool, phrase, start_date, stop_date, latitude, longitude) VALUES   ( 'Alicia', 'female', 3, 'monkey', 'fish', 20, false, 'Una mancha en la camisa manchada', '2014/01/01','2014/12/31',-76.6,5.4);

-- update advanced lucene index (add new lucene indexed field 'gender2' with case insensitive on "gender" column), with additional 10 parallel threads for cache data visitor population
CREATE INDEX PERSON_LUCENE_IDX ON "PUBLIC".PERSON(LUCENE) 
PARALLEL 10 
FULLTEXT '{
''refresh_seconds'':''60'',
''directory_path'':'''',
''ram_buffer_mb'':''10'',
''max_cached_mb'':''-1'',
''partitioner'':''{"type":"token","partitions":10}'',
''optimizer_enabled'':''true'',
''optimizer_schedule'':''0 1 * * *'',
''version'':''0'',
''schema'':''{
    "default_analyzer":"english",
    "analyzers":{"my_custom_analyzer":{"type":"snowball","language":"Spanish","stopwords":"el,la,lo,loas,las,a,ante,bajo,cabe,con,contra"}},
    "fields":{
      "duration":{"type":"date_range","from":"start_date","to":"stop_date","validated":false,"pattern":"yyyy/MM/dd"},
      "place":{"type":"geo_point","latitude":"latitude","longitude":"longitude"},
      "date":{"type":"date","validated":true,"pattern":"yyyy/MM/dd"},
      "number":{"type":"integer","validated":false,"boost":1.0},
      "gender":{"type":"string","validated":true,"case_sensitive":true},
      "gender2":{"type":"string","validated":true,"column":"gender","case_sensitive":false},
      "bool":{"type":"boolean","validated":false},
      "phrase":{"type":"text","validated":false,"analyzer":"my_custom_analyzer"},
      "name":{"type":"string","validated":false,"case_sensitive":true},
      "animal":{"type":"string","validated":false,"case_sensitive":true},
      "age":{"type":"integer","validated":false,"boost":1.0},
      "food":{"type":"string","validated":false,"case_sensitive":true}
    }
    }
  }''
}';

-- create regular SORTED index "FOOD_IDX" on FOOD column
CREATE INDEX FOOD_IDX ON "PUBLIC".PERSON(food);

-- you could test drop and recreate lucene index by DDL statement: DROP INDEX "PUBLIC".PERSON_LUCENE_IDX

-- Force refresh lucene index to make data available and do not wait to configured 60 refresh seconds
SELECT * FROM "PUBLIC".PERSON USE INDEX (PERSON_LUCENE_IDX)
WHERE lucene = '{ refresh : true}';

-- Boolean queries
SELECT * FROM "PUBLIC".PERSON 
WHERE lucene = '{ query : {
                              type : "boolean",
                              must : [{type : "wildcard", field : "name", value : "J*"},
                                      {type : "wildcard", field : "food", value : "tu*"}]}}';

SELECT * FROM "PUBLIC".PERSON 
WHERE lucene = '{ query : {
                              type  : "boolean",
                              not   : [{type : "wildcard", field : "name", value : "J*"}],
                              must  : [{type : "wildcard", field : "food", value : "tu*"}]}}';

SELECT * FROM "PUBLIC".PERSON 
WHERE lucene = '{ query : {
                              type   : "boolean",
                              should : [{type : "wildcard", field : "name", value : "J*"},
                                        {type : "wildcard", field : "food", value : "tu*"}]}}';

-- Contains queries
SELECT * FROM "PUBLIC".PERSON
WHERE lucene = '{ filter : {
                              type   : "contains",
                              field  : "name",
                              values : ["Alicia","mancha"]}}';

SELECT * FROM "PUBLIC".PERSON
WHERE lucene = '{ filter : {
                              type   : "contains",
                              field  : "date",
                              values : ["2014/01/01", "2014/01/02", "2014/01/03"]}}';

-- Date Range queries
SELECT * FROM "PUBLIC".PERSON
WHERE lucene = '{filter : {
                              type      : "date_range",
                              field     : "duration",
                              from      : "2014/01/01",
                              to        : "2014/12/31",
                              operation : "intersects"}}';

SELECT * FROM "PUBLIC".PERSON
WHERE lucene = '{filter : {
                              type      : "date_range",
                              field     : "duration",
                              from      : "2014/06/01",
                              to        : "2014/06/02",
                              operation : "contains"}}';

SELECT * FROM "PUBLIC".PERSON
WHERE lucene = '{filter : {
                              type      : "date_range",
                              field     : "duration",
                              from      : "2014/01/01",
                              to        : "2014/12/31",
                              operation : "is_within"}}';

-- Fuzzy queries
SELECT * FROM "PUBLIC".PERSON 
WHERE lucene = '{query     : {
                          type      : "fuzzy",
                          field     : "phrase",
                          value     : "puma",
                          max_edits : 1 }}';


SELECT * FROM "PUBLIC".PERSON
WHERE lucene = '{filter : { type    : "fuzzy",
                                     field         : "phrase",
                                     value         : "puma",
                                     max_edits     : 1,
                                     prefix_length : 2 }}';


-- Geo bbox queries
SELECT * FROM "PUBLIC".PERSON
WHERE lucene = '{filter : { type : "geo_bbox",
                                     field : "place",
                                     min_latitude : -90.0,
                                     max_latitude : 90.0,
                                     min_longitude : -180.0,
                                     max_longitude : 180.0 }}';

SELECT * FROM "PUBLIC".PERSON
WHERE lucene = '{filter : { type : "geo_bbox",
                                     field : "place",
                                     min_latitude : -90.0,
                                     max_latitude : 90.0,
                                     min_longitude : 0.0,
                                     max_longitude : 10.0 }}';

SELECT * FROM "PUBLIC".PERSON
WHERE lucene = '{filter : { type : "geo_bbox",
                                     field : "place",
                                     min_latitude : 0.0,
                                     max_latitude : 10.0,
                                     min_longitude : -180.0,
                                     max_longitude : 180.0 }}';
-- Geo distance queries
 SELECT * FROM "PUBLIC".PERSON
    WHERE lucene = '{filter : { type : "geo_distance",
                                         field : "place",
                                         latitude : 40.225479,
                                         longitude : -3.999278,
                                         max_distance : "1km"}}';

SELECT * FROM "PUBLIC".PERSON
    WHERE lucene = '{filter : { type : "geo_distance",
                                     field : "place",
                                     latitude : 40.225479,
                                     longitude : -3.999278,
                                     max_distance : "10yd" ,
                                     min_distance : "1yd" }}';

-- Match queries
SELECT * FROM "PUBLIC".PERSON 
WHERE lucene = '{query : {
                            type  : "match",
                            field : "name",
                            value : "Alicia" }}';

SELECT * FROM "PUBLIC".PERSON 
WHERE lucene = '{query : {
                            type  : "match",
                            field : "gender",
                            value : "female" }}';

-- this WILL NOT return data as gender mapper is case sensitive
SELECT * FROM "PUBLIC".PERSON 
WHERE lucene = '{query : {
                            type  : "match",
                            field : "gender",
                            value : "feMale" }}';

-- this WILL return data as gender2 mapper is case in-sensitive
SELECT * FROM "PUBLIC".PERSON 
WHERE lucene = '{query : {
                            type  : "match",
                            field : "gender2",
                            value : "feMale" }}';

SELECT * FROM "PUBLIC".PERSON
WHERE lucene = '{query : {
                            type  : "match",
                            field : "phrase",
                            value : "mancha" }}';
SELECT * FROM "PUBLIC".PERSON 
WHERE lucene = '{query : {
                            type  : "match",
                            field : "date",
                            value : "2014/01/01" }}';


-- Match all queries
SELECT * FROM "PUBLIC".PERSON
WHERE lucene = '{filter : { type  : "all" }}';

-- Phrase queries
SELECT * FROM "PUBLIC".PERSON 
WHERE lucene = '{query : {
                            type   : "phrase",
                            field  : "phrase",
                            value : "camisa manchada" }}';

SELECT * FROM "PUBLIC".PERSON 
WHERE lucene = '{query : {
                            type   : "phrase",
                            field  : "phrase",
                            value : "mancha camisa",
                            slop   : 2 }}';

-- Prefix queries
SELECT * FROM "PUBLIC".PERSON 
WHERE lucene = '{query : {
                            type          : "prefix",
                            field         : "phrase",
                            value         : "lu" }}';

-- Range queries
SELECT * FROM "PUBLIC".PERSON 
WHERE lucene = '{query : {
                            type          : "range",
                            field         : "age",
                            lower         : 1,
                            include_lower : true }}';
SELECT * FROM "PUBLIC".PERSON 
WHERE lucene = '{query : {
                            type          : "range",
                            field         : "age",
                            upper         : 0,
                            include_upper : true }}';
SELECT * FROM "PUBLIC".PERSON 
WHERE lucene = '{query : {
                            type          : "range",
                            field         : "age",
                            lower         : -1,
                            upper         : 1,
                            include_lower : true,
                            include_upper : true }}';
SELECT * FROM "PUBLIC".PERSON 
WHERE lucene = '{query : {
                            type          : "range",
                            field         : "date",
                            lower         : "2014/01/01",
                            upper         : "2014/01/02",
                            include_lower : true,
                            include_upper : true }}';

-- Regexp queries
SELECT * FROM "PUBLIC".PERSON 
WHERE lucene = '{query : {
                            type  : "regexp",
                            field : "name",
                            value : "([J][aeiou]).*" }}';

-- Wildcard queries
SELECT * FROM "PUBLIC".PERSON 
WHERE lucene = '{query : {
                            type  : "wildcard",
                            field : "food",
                            value : "tu*" }}';