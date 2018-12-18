-- populate table - copy ALL lines and paste them on SQLQueryRunner

INSERT INTO "test".user (name, gender, age, animal, food, number, bool, setz, listz, mapz, start_date, stop_date, latitude, longitude) VALUES ( 'Joe', 'male', -2, 'monkey', 'chips', 1, true, public.hkjson_to_set('["s1", "s2"]'), public.hkjson_to_list('["l1", "l2"]'), public.hkjson_to_map('{"k1":"v1","k2":"v2"}'), '2014/01/01','2014/01/31',-87.8, 0.5);
INSERT INTO "test".user (name, gender, age, animal, food, number, bool, setz, listz, mapz, start_date, stop_date, latitude, longitude) VALUES   ( 'Joe', 'male', 1, 'monkey', 'chips', 2, true, public.hkjson_to_set('["s1", "s2"]'), public.hkjson_to_list('["l1", "l2"]'), public.hkjson_to_map('{"k1":"v1","k2":"v2"}'), '2014/02/01','2014/02/28',45.8, 0.5);
INSERT INTO "test".user (name, gender, age, animal, food, number, bool, setz, listz, mapz, start_date, stop_date, latitude, longitude) VALUES   ( 'James', 'male', 1, 'monkey', 'chips', 3, true, public.hkjson_to_set('["s1", "s2"]'), public.hkjson_to_list('["l1", "l2"]'), public.hkjson_to_map('{"k1":"v1","k2":"v2"}'), '2014/03/01','2014/03/31',2.38, -170.6);
INSERT INTO "test".user (name, gender, age, animal, food, number, bool, setz, listz, mapz, start_date, stop_date, latitude, longitude) VALUES   ( 'James', 'male', 2, 'monkey', 'chips', 4, true, public.hkjson_to_set('["s1", "s3"]'), public.hkjson_to_list('["l1", "l2"]'), public.hkjson_to_map('{"k1":"v1"}'), '2014/04/01','2014/04/30',88.9, -156.8);
INSERT INTO "test".user (name, gender, age, animal, food, number, bool, setz, listz, mapz, start_date, stop_date, latitude, longitude) VALUES   ( 'Jane', 'female', -2, 'monkey', 'chips', 5, true, public.hkjson_to_set('["s3", "s4"]'), public.hkjson_to_list('["l1", "l3"]'), public.hkjson_to_map('{"k3":"v3","k2":"v2"}'), '2014/05/01','2014/05/31',1.45, 94.23);
INSERT INTO "test".user (name, gender, age, animal, food, number, bool, date, start_date, stop_date, latitude, longitude) VALUES   ( 'Jane', 'female', -1, 'monkey', 'chips', 6, true, '2014/01/01', '2014/06/01','2014/06/30',-42.8, 12.4);
INSERT INTO "test".user (name, gender, age, animal, food, number, bool, date, start_date, stop_date, latitude, longitude) VALUES   ( 'James', 'male', 0, 'monkey', 'chips', 7, true, '2014/01/02', '2014/07/01','2014/07/31',-12, 13.6);
INSERT INTO "test".user (name, gender, age, animal, food, number, bool, date, start_date, stop_date, latitude, longitude) VALUES   ( 'Jane', 'female', 1, 'monkey', 'chips', 8, true, '2010/01/01', '2014/08/01','2014/08/31',-5.6, 34.6);
INSERT INTO "test".user (name, gender, age, animal, food, number, bool, start_date, stop_date, latitude, longitude) VALUES   ( 'Alicia', 'female', 2, 'monkey', 'chips', 9, true, '2014/09/01','2014/09/30',-32.3, -170.0);
INSERT INTO "test".user (name, gender, age, animal, food, number, bool, start_date, stop_date, latitude, longitude) VALUES   ( 'Joe', 'male', 2, 'monkey', 'chips', 10, false, '2014/10/01','2014/10/31',-32.8, 115.6);
INSERT INTO "test".user (name, gender, age, animal, food, number, bool, start_date, stop_date, latitude, longitude) VALUES   ( 'James', 'male', -2, 'monkey', 'chips', 11, false, '2014/11/01','2014/11/30',-21.4, 32.7);
INSERT INTO "test".user (name, gender, age, animal, food, number, bool, start_date, stop_date, latitude, longitude) VALUES   ( 'James', 'male', -1, 'monkey', 'chips', 12, false, '2014/12/01','2014/12/31',-33.2, 167.0);
INSERT INTO "test".user (name, gender, age, animal, food, number, bool, start_date, stop_date, latitude, longitude) VALUES   ( 'Alicia', 'female', 0, 'monkey', 'chips', 13, false, '2014/01/01','2014/06/30',65.5, 2.3);
INSERT INTO "test".user (name, gender, age, animal, food, number, bool, start_date, stop_date, latitude, longitude) VALUES   ( 'Joe', 'male', -1, 'monkey', 'chips', 14, false, '2014/02/01','2014/07/31',-22.1, 180.0);
INSERT INTO "test".user (name, gender, age, animal, food, number, bool, phrase, start_date, stop_date, latitude, longitude) VALUES   ( 'Joe', 'male', 0, 'monkey', 'chips', 15, false, 'Ligero como una pluma', '2014/03/01','2014/08/31',-35.4, 97.4);
INSERT INTO "test".user (name, gender, age, animal, food, number, bool, phrase, start_date, stop_date, latitude, longitude) VALUES   ( 'Alicia', 'female', -2, 'monkey', 'chips', 16, false, 'Rápido como un puma', '2014/04/01','2014/09/30',0.7, -167);
INSERT INTO "test".user (name, gender, age, animal, food, number, bool, phrase, start_date, stop_date, latitude, longitude) VALUES   ( 'Alicia', 'female', -1, 'monkey', 'chips', 17, false, 'Una mancha en la camisa morada', '2014/05/01','2014/10/31',-42.3, 25.5);
INSERT INTO "test".user (name, gender, age, animal, food, number, bool, phrase, start_date, stop_date, latitude, longitude) VALUES   ( 'Jane', 'female', 0, 'monkey', 'turkey', 18, false, 'Sopa de tortuga', '2014/06/01','2014/11/30',-12.8, 176);
INSERT INTO "test".user (name, gender, age, animal, food, number, bool, phrase, start_date, stop_date, latitude, longitude) VALUES   ( 'Alicia', 'female', 1, 'monkey', 'tuna', 19, false, 'En un lugar de la Mancha', '2014/07/01','2014/12/31',-0.5, 1.2);
INSERT INTO "test".user (name, gender, age, animal, food, number, bool, phrase, start_date, stop_date, latitude, longitude) VALUES   ( 'Alicia', 'female', 3, 'monkey', 'fish', 20, false, 'Una mancha en la camisa manchada', '2014/01/01','2014/12/31',-76.6,5.4);


-- Refresh index
SELECT * FROM "test".user
WHERE lucene = '{ refresh : true}';

-- Boolean queries
SELECT * FROM "test".user 
WHERE lucene = '{ query : {
                              type : "boolean",
                              must : [{type : "wildcard", field : "name", value : "J*"},
                                      {type : "wildcard", field : "food", value : "tu*"}]}}';

SELECT * FROM "test".user 
WHERE lucene = '{ query : {
                              type  : "boolean",
                              not   : [{type : "wildcard", field : "name", value : "J*"}],
                              must  : [{type : "wildcard", field : "food", value : "tu*"}]}}';

SELECT * FROM "test".user 
WHERE lucene = '{ query : {
                              type   : "boolean",
                              should : [{type : "wildcard", field : "name", value : "J*"},
                                        {type : "wildcard", field : "food", value : "tu*"}]}}';

-- Contains queries
SELECT * FROM "test".user
WHERE lucene = '{ filter : {
                              type   : "contains",
                              field  : "name",
                              values : ["Alicia","mancha"]}}';

SELECT * FROM "test".user
WHERE lucene = '{ filter : {
                              type   : "contains",
                              field  : "date",
                              values : ["2014/01/01", "2014/01/02", "2014/01/03"]}}';

-- Date Range queries
SELECT * FROM "test".user
WHERE lucene = '{filter : {
                              type      : "date_range",
                              field     : "duration",
                              from      : "2014/01/01",
                              to        : "2014/12/31",
                              operation : "intersects"}}';

SELECT * FROM "test".user
WHERE lucene = '{filter : {
                              type      : "date_range",
                              field     : "duration",
                              from      : "2014/06/01",
                              to        : "2014/06/02",
                              operation : "contains"}}';

SELECT * FROM "test".user
WHERE lucene = '{filter : {
                              type      : "date_range",
                              field     : "duration",
                              from      : "2014/01/01",
                              to        : "2014/12/31",
                              operation : "is_within"}}';

-- Fuzzy queries
SELECT * FROM "test".user 
WHERE lucene = '{query     : {
                          type      : "fuzzy",
                          field     : "phrase",
                          value     : "puma",
                          max_edits : 1 }}';
SELECT * FROM "test".user
WHERE lucene = '{filter : { type : "fuzzy",
                                     field         : "phrase",
                                     value         : "puma",
                                     max_edits     : 1,
                                     prefix_length : 2 }}';


-- Geo bbox queries
SELECT * FROM "test".user
WHERE lucene = '{filter : { type : "geo_bbox",
                                     field : "place",
                                     min_latitude : -90.0,
                                     max_latitude : 90.0,
                                     min_longitude : -180.0,
                                     max_longitude : 180.0 }}';

SELECT * FROM "test".user
WHERE lucene = '{filter : { type : "geo_bbox",
                                     field : "place",
                                     min_latitude : -90.0,
                                     max_latitude : 90.0,
                                     min_longitude : 0.0,
                                     max_longitude : 10.0 }}';

SELECT * FROM "test".user
WHERE lucene = '{filter : { type : "geo_bbox",
                                     field : "place",
                                     min_latitude : 0.0,
                                     max_latitude : 10.0,
                                     min_longitude : -180.0,
                                     max_longitude : 180.0 }}';
-- Geo distance queries
 SELECT * FROM "test".user
    WHERE lucene = '{filter : { type : "geo_distance",
                                         field : "place",
                                         latitude : 40.225479,
                                         longitude : -3.999278,
                                         max_distance : "1km"}}';

SELECT * FROM "test".user
    WHERE lucene = '{filter : { type : "geo_distance",
                                     field : "place",
                                     latitude : 40.225479,
                                     longitude : -3.999278,
                                     max_distance : "10yd" ,
                                     min_distance : "1yd" }}';

-- Match queries
SELECT * FROM "test".user 
WHERE lucene = '{query : {
                            type  : "match",
                            field : "name",
                            value : "Alicia" }}';
SELECT * FROM "test".user
WHERE lucene = '{query : {
                            type  : "match",
                            field : "phrase",
                            value : "mancha" }}';
SELECT * FROM "test".user 
WHERE lucene = '{query : {
                            type  : "match",
                            field : "date",
                            value : "2014/01/01" }}';


-- Match all queries
SELECT * FROM "test".user
WHERE lucene = '{filter : { type  : "all" }}';

-- Phrase queries
SELECT * FROM "test".user 
WHERE lucene = '{query : {
                            type   : "phrase",
                            field  : "phrase",
                            value : "camisa manchada" }}';
SELECT * FROM "test".user 
WHERE lucene = '{query : {
                            type   : "phrase",
                            field  : "phrase",
                            value : "mancha camisa",
                            slop   : 2 }}';

-- Prefix queries
SELECT * FROM "test".user 
WHERE lucene = '{query : {
                            type          : "prefix",
                            field         : "phrase",
                            value         : "lu" }}';

-- Range queries
SELECT * FROM "test".user 
WHERE lucene = '{query : {
                            type          : "range",
                            field         : "age",
                            lower         : 1,
                            include_lower : true }}';
SELECT * FROM "test".user 
WHERE lucene = '{query : {
                            type          : "range",
                            field         : "age",
                            upper         : 0,
                            include_upper : true }}';
SELECT * FROM "test".user 
WHERE lucene = '{query : {
                            type          : "range",
                            field         : "age",
                            lower         : -1,
                            upper         : 1,
                            include_lower : true,
                            include_upper : true }}';
SELECT * FROM "test".user 
WHERE lucene = '{query : {
                            type          : "range",
                            field         : "date",
                            lower         : "2014/01/01",
                            upper         : "2014/01/02",
                            include_lower : true,
                            include_upper : true }}';

-- Regexp queries
SELECT * FROM "test".user 
WHERE lucene = '{query : {
                            type  : "regexp",
                            field : "name",
                            value : "([J][aeiou]).*" }}';

-- Wildcard queries
SELECT * FROM "test".user 
WHERE lucene = '{query : {
                            type  : "wildcard",
                            field : "food",
                            value : "tu*" }}';


-- USING Lucene classic query parser syntax
-- see https://lucene.apache.org/core/5_5_4/queryparser/org/apache/lucene/queryparser/classic/package-summary.html

-- Wildcard queries
SELECT * FROM "test".user 
WHERE lucene = 'name:Ja*';

-- Regexp queries
SELECT * FROM "test".user 
WHERE lucene = 'name:/([J][aeiou]).*/';

-- Fuzzy queries
SELECT * FROM "test".user 
WHERE lucene = 'food:chip~';

-- Fuzzy text search on any field
SELECT * FROM "test".user 
WHERE lucene = 'fema~';

-- Proximity search
SELECT * FROM "test".user 
WHERE lucene = 'phrase:"mancha camisa"~2';

-- Range Searches
SELECT * FROM "test".user 
WHERE lucene = 'name:[Alicia TO Joe}';


