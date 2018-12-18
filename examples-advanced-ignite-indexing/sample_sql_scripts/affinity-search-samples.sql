-- before search you must populate cache running from a console shell at 'examples-advanced-ignite-indexing' directory below command: 
--  mvn exec:java -Dexec.mainClass="com.hawkore.ignite.examples.PopulateTweets" -Dexec.classpathScope=compile -DnodeName=populator

-- AFFINITY COLLOCATION tests
-- by running below queries you will see on every console shell running ignite servers whether query is executed
-- you must enable DEBUG 

-- NOTE: tweet's key is a composed key (id, countryCode) with affinity key mapped on 'countryCode'

-- fetch all nodes, all partitions
SELECT * FROM "tweets".tweet WHERE lucene = '{
refresh:true,
filter: [
        {type: "range", field: "time", lower: "2018/12/1", upper: "2018/12/30"},
        {type: "match", field: "countryCode", value: "ES"}
        ]
}' limit 20;

-- fetch one node, all partitions
SELECT * FROM "tweets".tweet USE INDEX (tweet_lucene_idx) WHERE lucene = '{
filter: [
        {type: "range", field: "time", lower: "2018/12/1", upper: "2018/12/30"},
        {type: "match", field: "countryCode", value: "FR"}
        ]
}' and countryCode = 'FR' limit 20; 


-- fetch one node, one partition (query conditions match one full cache key condition)
SELECT * FROM "tweets".tweet USE INDEX (tweet_lucene_idx) WHERE lucene = '{
filter: [
        {type: "match", field: "id", value: 6211},
        {type: "range", field: "time", lower: "2018/12/1", upper: "2019/12/31"},
        {type: "match", field: "countryCode", value: "ES"}
        ]
}' and countryCode = 'ES' and id = 6211;

-- fetch one node, partitions <= 2 (2 * affinity key) (query conditions match: 2 (combinations) * full cache key condition)
SELECT * FROM "tweets".tweet USE INDEX (tweet_lucene_idx) WHERE lucene = '{
filter: [
        {type: "range", field: "time", lower: "2018/12/1", upper: "2020/12/30"},
        {type: "match", field: "countryCode", value: "ES"},
        {
          should: [
                {type: "match", field: "id", value: 6211},
                {type: "match", field: "id", value: 3178}
          ]
        }        
        ]
}' and countryCode = 'ES' and (id = 6211 or id = 3178);


-- fetch from nodes <= 2 (2 * affinity key), partitions = 2 (query conditions match: 4 (combinations) * full cache key condition)
-- internally cache key conditions will be discarted by nodes by affinity collocation, so in this sample lucene partitions to fetch within each node = 2
SELECT * FROM "tweets".tweet USE INDEX (tweet_lucene_idx) WHERE lucene = '{
filter: [
        {type: "range", field: "time", lower: "2018/12/1", upper: "2040/12/30"},
        {
          should: [
                {type: "match", field: "countryCode", value: "ES"},
                {type: "match", field: "countryCode", value: "FR"}
          ]
        },
        {
          should: [
                {type: "match", field: "id", value: 6211},
                {type: "match", field: "id", value: 203178}
          ]
        }
        ]
}' and (countryCode = 'ES' or countryCode = 'FR') and (id = 6211 or id = 203178) limit 10;


-- Search for tweets within a certain date range forcing an explicit refresh:
-- fetch all nodes, all partitions
SELECT * FROM "tweets".tweet WHERE lucene = '{
   refresh:true, 
   filter: {type: "range", field: "time", lower: "2018/12/1", upper: "2018/12/30"}
}' limit 10; 


-- Now, to search the top 100 more relevant tweets where *body* field contains the phrase “big data gives organizations” within the aforementioned date range:
-- fetch all nodes, all partitions
SELECT * FROM "tweets".tweet WHERE lucene = '{
   filter: {type: "range", field: "time", lower: "2018/12/1", upper: "2018/12/30"},
   query: {type: "phrase", field: "body", value: "big data gives organizations", slop: 1}
}' limit 10;  


-- To refine the search to get only the tweets written by users whose names start with "user_1":
-- fetch all nodes, all partitions
SELECT * FROM "tweets".tweet WHERE lucene = '{
   filter: [
      {type: "range", field: "time", lower: "2018/12/1", upper: "2018/12/30"},
      {type: "prefix", field: "user", value: "user_1"}
   ],
   query: {type: "phrase", field: "body", value: "big data gives organizations", slop: 1}
}' limit 10; 


-- To get the 100 more recent filtered results you can use the *sort* option:
-- fetch all nodes, all partitions
SELECT * FROM "tweets".tweet WHERE lucene = '{
   filter: [
      {type: "range", field: "time", lower: "2018/12/1", upper: "2018/12/30"},
      {type: "prefix", field: "user", value: "user_1"}
   ],
   query: {type: "phrase", field: "body", value: "big data gives organizations", slop: 1},
   sort: {field: "time", reverse: true}
}' limit 10; 


-- The previous search can be restricted to tweets created close to a geographical position:
-- fetch all nodes, all partitions
SELECT *, 
PUBLIC.ST_DISTANCE_SPHERE(place, 'POINT (-3.703790 40.416775)')/1000 as distance_km 
FROM "tweets".tweet WHERE lucene = '{
   filter: [
      {type: "range", field: "time", lower: "2018/12/1", upper: "2020/12/30"},
      {type: "prefix", field: "user", value: "user_1"},
      {type: "geo_distance", field: "place", latitude: 40.3930, longitude: -3.7328, max_distance: "20km"}
   ],
   query: {type: "phrase", field: "body", value: "big data gives organizations", slop: 1},
   sort: {field: "time", reverse: true}
}' limit 10;  

-- It is also possible to sort the results by distance to a geographical position:
-- fetch all nodes, all partitions
SELECT *, 
PUBLIC.ST_DISTANCE_SPHERE(place, 'POINT (-3.703790 40.416775)')/1000 as distance_km 
FROM "tweets".tweet 
WHERE lucene = '{
   filter: [
      {type: "range", field: "time", lower: "2018/12/1", upper: "2020/12/30"},
      {type: "prefix", field: "user", value: "user_1"},
      {type: "geo_distance", field: "place", latitude: 40.416775, longitude: -3.703790, max_distance: "20km"},
      {type: "match", field: "countryCode", value: "ES"}
   ],
   query: {type: "phrase", field: "body", value: "big data gives organizations", slop: 1},
   sort: [
      {field: "place", type: "geo_distance", latitude: 40.416775, longitude: -3.703790}
   ]
}' limit 10; 

-- It is also possible to sort the results by reverse distance to a geographical position:
-- fetch all nodes, all partitions
SELECT *, 
PUBLIC.ST_DISTANCE_SPHERE(place, 'POINT (-3.703790 40.416775)')/1000 as distance_km 
FROM "tweets".tweet 
WHERE lucene = '{
   filter: [
      {type: "range", field: "time", lower: "2018/12/1", upper: "2020/12/30"},
      {type: "prefix", field: "user", value: "user_1"},
      {type: "geo_distance", field: "place", latitude: 40.416775, longitude: -3.703790, max_distance: "20km"},
      {type: "match", field: "countryCode", value: "ES"}
   ],
   query: {type: "phrase", field: "body", value: "big data gives organizations", slop: 1},
   sort: [
      {field: "place", type: "geo_distance", latitude: 40.416775, longitude: -3.703790, reverse: true}
   ]
}' limit 10; 

-- We can improve query execution by adding affinity collocation.  
-- Below query will hit only ONE cluster node by adding "AND countryCode = 'ES'" affinity key condition:
SELECT *, 
PUBLIC.ST_DISTANCE_SPHERE(place, 'POINT (-3.703790 40.416775)')/1000 as distance_km 
FROM "tweets".tweet USE INDEX(tweet_lucene_idx)
WHERE lucene = '{
   filter: [
      {type: "range", field: "time", lower: "2018/12/1", upper: "2020/12/30"},
      {type: "prefix", field: "user", value: "user_1"},
      {type: "geo_distance", field: "place", latitude: 40.416775, longitude: -3.703790, max_distance: "20km"},
      {type: "match", field: "countryCode", value: "ES"}
   ],
   query: {type: "phrase", field: "body", value: "big data gives organizations", slop: 1},
   sort: [
      {field: "place", type: "geo_distance", latitude: 40.416775, longitude: -3.703790}
   ]
}' AND countryCode = 'ES' limit 10; 

-- IMPORTANT when you add key conditions (or another indexed condition) to query DO NOT forget to add USE INDEX, otherwise sql execution planner will take another index
-- Below query is same as above one without USE INDEX(tweet_lucene_idx), you will get an EMPTY resulset, as sql execution planner takes AFFINITY index instead of tweet_lucene_idx
SELECT *, 
PUBLIC.ST_DISTANCE_SPHERE(place, 'POINT (-3.703790 40.416775)')/1000 as distance_km 
FROM "tweets".tweet
WHERE lucene = '{
   filter: [
      {type: "range", field: "time", lower: "2018/12/1", upper: "2020/12/30"},
      {type: "prefix", field: "user", value: "user_1"},
      {type: "geo_distance", field: "place", latitude: 40.416775, longitude: -3.703790, max_distance: "20km"},
      {type: "match", field: "countryCode", value: "ES"}
   ],
   query: {type: "phrase", field: "body", value: "big data gives organizations", slop: 1},
   sort: [
      {field: "place", type: "geo_distance", latitude: 40.416775, longitude: -3.703790}
   ]
}' AND countryCode = 'ES' limit 10; 

