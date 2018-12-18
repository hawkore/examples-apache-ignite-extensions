-- PERFORMANCE TEST: Advanced Lucene index geo-search versus Grid H2 spatial index search

-- LOW DENSITY POINTS per area: Grid H2 spatial index is FASTER than Advanced Lucene index geo-search - testing 10 Km radius

-- show density in 10km radius
-- added condition PUBLIC.ST_DISTANCE_SPHERE(place, 'POINT (-3.703790 40.416775)') <= 10000 to enforce exact radius
SELECT count(*) 
FROM "tweets".tweet USE INDEX(tweet_place_idx)
where
place && public.ST_GEOCIRCLE('POINT (-3.703790 40.416775)', 10000) 
and PUBLIC.ST_DISTANCE_SPHERE(place, 'POINT (-3.703790 40.416775)') <= 10000
and countryCode = 'ES' ;

-- Using Advanced Lucene index geo-search
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
}' AND countryCode = 'ES' limit 100; 

--  Using Grid H2 spatial index
-- added condition PUBLIC.ST_DISTANCE_SPHERE(place, 'POINT (-3.703790 40.416775)') <= 10000 to enforce exact radius
SELECT *, 
PUBLIC.ST_DISTANCE_SPHERE(place, 'POINT (-3.703790 40.416775)')/1000 as distance_km 
FROM "tweets".tweet USE INDEX (tweet_place_idx)
WHERE
place && public.ST_GEOCIRCLE('POINT (-3.703790 40.416775)', 10000) 
and PUBLIC.ST_DISTANCE_SPHERE(place, 'POINT (-3.703790 40.416775)') <= 10000
and countryCode = 'ES' 
order by distance_km desc
limit 100;


-- HIGH DENSITY POINTS per area:  Advanced Lucene index geo-search is FASTER than Grid H2 spatial index - testing 200 Km radius

-- show density in 200km circle
-- added condition PUBLIC.ST_DISTANCE_SPHERE(place, 'POINT (-3.703790 40.416775)') <= 200000 to enforce exact radius
SELECT count(*) 
FROM "tweets".tweet USE INDEX(tweet_place_idx)
where
place && public.ST_GEOCIRCLE('POINT (-3.703790 40.416775)', 200000) 
and PUBLIC.ST_DISTANCE_SPHERE(place, 'POINT (-3.703790 40.416775)') <= 200000
and countryCode = 'ES' ;


-- Using Advanced Lucene index geo-search
SELECT *, 
PUBLIC.ST_DISTANCE_SPHERE(place, 'POINT (-3.703790 40.416775)')/1000 as distance_km 
FROM "tweets".tweet USE INDEX(tweet_lucene_idx)
WHERE lucene = '{
   filter: [
      {type: "geo_distance", field: "place", latitude: 40.416775, longitude: -3.703790, max_distance: "200km"}
   ],
   sort: [
      {field: "place", type: "geo_distance", latitude: 40.416775, longitude: -3.703790, reverse: true}
   ]
}' AND countryCode = 'ES' limit 100; 


--  Using Grid H2 spatial index
-- added condition PUBLIC.ST_DISTANCE_SPHERE(place, 'POINT (-3.703790 40.416775)') <= 200000 to enforce exact radius
SELECT *, 
PUBLIC.ST_DISTANCE_SPHERE(place, 'POINT (-3.703790 40.416775)')/1000 as distance_km 
FROM "tweets".tweet USE INDEX (tweet_place_idx)
WHERE
place && public.ST_GEOCIRCLE('POINT (-3.703790 40.416775)', 200000) 
and PUBLIC.ST_DISTANCE_SPHERE(place, 'POINT (-3.703790 40.416775)') <= 200000
and countryCode = 'ES' 
order by distance_km desc
limit 100;