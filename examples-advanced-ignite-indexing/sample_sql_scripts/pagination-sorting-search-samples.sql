-- before search you must populate cache running from a console shell at 'examples-advanced-ignite-indexing' directory below command: 
--  mvn exec:java -Dexec.mainClass="com.hawkore.ignite.examples.PopulateTweets" -Dexec.classpathScope=compile -DnodeName=populator

-- SQL Pagination support: limit <page size> offset <number of elements to exclude>
-- WARNING: SQL query is an Ignite map-reduce operation, on map phase will iterates over LIMIT + OFFSET elements, on reduce phase will discart OFFSET elements
-- so pagination with high OFFSET is not recomended on huge tables because it's a very expensive operation

-- Using Advanced Lucene index geo_distance search with sort by distance to Madrid(Spain)
SELECT *, 
PUBLIC.ST_DISTANCE_SPHERE(place, 'POINT (-3.703790 40.416775)')/1000 as distance_km
FROM "tweets".tweet USE INDEX(tweet_lucene_idx)
WHERE lucene = '{
   refresh:true,
   filter: [
      {type: "geo_distance", field: "place", latitude: 40.416775, longitude: -3.703790, max_distance: "10km"}
   ],
   sort: [
      {field: "place", type: "geo_distance", latitude: 40.416775, longitude: -3.703790, reverse: true}
   ]
}'
limit 100 offset 20;

--  Using Grid H2 spatial index search with sort by distance to Madrid(Spain)
SELECT *, 
PUBLIC.ST_DISTANCE_SPHERE(place, 'POINT (-3.703790 40.416775)')/1000 as distance_km 
FROM "tweets".tweet USE INDEX (tweet_place_idx)
WHERE
place && public.ST_GEOCIRCLE('POINT (-3.703790 40.416775)', 10000)
and PUBLIC.ST_DISTANCE_SPHERE(place, 'POINT (-3.703790 40.416775)') <= 10000
order by distance_km desc
limit 100 offset 20; 


-- LUCENE SORTING
-- when you use lucene search, you must apply sorting within JSON lucene query, standard ORDER BY will be removed on map-reduce query generation phase.
-- By default lucene searches will be ordered by internal _SCORE_DOC field that contains information about how sort rows based on defined JSON lucene sorting.

-- MANUAL order by _SCORE_DOC
-- If you have complex queries like below, you could add manual order by internal _SCORE_DOC field on main SELECT statement (as on this example, main query is a "reduce query")
-- Below example is intended ONLY TO ILLUSTRATE MANUAL SORT BY inernal _SCORE_DOC field... this query has not sense, or maybe yes?

-- On this example we want to retrieve 20 nearest tweets to Madrid (Spain - latitude: 40.416775, longitude: -3.703790) as follows: 
-- * 10 nearest written in Spain.
-- * 10 nearest written in France.
-- * sorted by defined lucene sorting (_SCORE_DOC)

SELECT 
C.ID,
C.COUNTRYCODE,
C.USER,
C.BODY,
C.TIME,
C.PLACE,
C.DISTANCE_KM
from (
     select * from (
        -- 10 nearest written in Spain order by distance to Madrid
        SELECT *, 
        PUBLIC.ST_DISTANCE_SPHERE(place, 'POINT (-3.703790 40.416775)')/1000 as distance_km,
        _SCORE_DOC -- to allow main reduce-query sort by lucene sort
        FROM "tweets".tweet USE INDEX(tweet_lucene_idx)
        WHERE lucene = '{
           refresh:true,
           filter: [
              {type: "geo_distance", field: "place", latitude: 40.416775, longitude: -3.703790, max_distance: "6000km"},
              {type: "match", field: "countryCode", value: "ES"}
           ],
           sort: [
                 {field: "place", type: "geo_distance", latitude: 40.416775, longitude: -3.703790, reverse: false}
           ]
        }'
        and countryCode = 'ES' -- this condition will route query to UNIQUE NODE containing affinity key condition (countryCode='ES')
        limit 10) A
    union all
    select * from (
        -- 10 nearest written in France order by distance to Madrid
        SELECT *, 
        PUBLIC.ST_DISTANCE_SPHERE(place, 'POINT (-3.703790 40.416775)')/1000 as distance_km,
        _SCORE_DOC -- to allow main reduce-query sort by lucene sort
        FROM "tweets".tweet USE INDEX(tweet_lucene_idx)
        WHERE lucene = '{
           refresh:true,
           filter: [
              {type: "geo_distance", field: "place", latitude: 40.416775, longitude: -3.703790, max_distance: "6000km"},
              {type: "match", field: "countryCode", value: "FR"}
           ],
           sort: [
                 {field: "place", type: "geo_distance", latitude: 40.416775, longitude: -3.703790, reverse: false}
           ]
        }'
        and countryCode = 'FR' -- this condition will route query to UNIQUE NODE containing affinity key condition (countryCode='FR')
        limit 10) B
) C
ORDER by C._SCORE_DOC
limit 20;  

-- you can also reverse sorting on main reduce-query by adding 'DESC' to ORDER by C._SCORE_DOC

SELECT 
C.ID,
C.COUNTRYCODE,
C.USER,
C.BODY,
C.TIME,
C.PLACE,
C.DISTANCE_KM
from (
     select * from (
        -- 10 nearest written in Spain order by distance to Madrid
        SELECT *, 
        PUBLIC.ST_DISTANCE_SPHERE(place, 'POINT (-3.703790 40.416775)')/1000 as distance_km,
        _SCORE_DOC -- to allow main reduce-query sort by lucene sort
        FROM "tweets".tweet USE INDEX(tweet_lucene_idx)
        WHERE lucene = '{
           refresh:true,
           filter: [
              {type: "geo_distance", field: "place", latitude: 40.416775, longitude: -3.703790, max_distance: "6000km"},
              {type: "match", field: "countryCode", value: "ES"}
           ],
           sort: [
                 {field: "place", type: "geo_distance", latitude: 40.416775, longitude: -3.703790, reverse: false}
           ]
        }'
        and countryCode = 'ES' -- this condition will route query to UNIQUE NODE containing affinity key condition (countryCode='ES')
        limit 10) A
    union all
    select * from (
        -- 10 nearest written in France order by distance to Madrid
        SELECT *, 
        PUBLIC.ST_DISTANCE_SPHERE(place, 'POINT (-3.703790 40.416775)')/1000 as distance_km,
        _SCORE_DOC -- to allow main reduce-query sort by lucene sort
        FROM "tweets".tweet USE INDEX(tweet_lucene_idx)
        WHERE lucene = '{
           refresh:true,
           filter: [
              {type: "geo_distance", field: "place", latitude: 40.416775, longitude: -3.703790, max_distance: "6000km"},
              {type: "match", field: "countryCode", value: "FR"}
           ],
           sort: [
                 {field: "place", type: "geo_distance", latitude: 40.416775, longitude: -3.703790, reverse: false}
           ]
        }'
        and countryCode = 'FR' -- this condition will route query to UNIQUE NODE containing affinity key condition (countryCode='FR')
        limit 10) B
) C
ORDER by C._SCORE_DOC DESC
limit 20; 
