-- search by shape

-- insert some geometries within polygon POLYGON((-80.14247278479951 25.795756477689594, -66.11315588592869 18.47447597127288, -64.82713517019887 32.33019640254669, -80.14247278479951 25.795756477689594))

INSERT INTO "shapes".block(_key, id, shape) VALUES (1,1, 'POINT(-74.72900390625 26.37218544169562)');
INSERT INTO "shapes".block(_key, id, shape) VALUES (2,2, 'POINT(-69.89501953125 27.97499795326776)');
INSERT INTO "shapes".block(_key, id, shape) VALUES (3,3, 'POINT(-69.89501953125 27.97499795326776)');
INSERT INTO "shapes".block(_key, id, shape) VALUES (4,4, 'POINT(-69.89501953125 27.97499795326776)');

-- insert some geometries within a buffer 10 kilometers of the Florida's coastlinen (LINESTRING(-80.90 29.05, -80.51 28.47, -80.60 28.12, -80.00 26.85, -80.05 26.37)) and a polygon that intersets with this 20km buffer

INSERT INTO "shapes".block(_key, id, shape) VALUES (5,5, 'POINT(-80.6485413219623 28.640536042944007)');
INSERT INTO "shapes".block(_key, id, shape) VALUES (6,6, 'POINT(-80.58958155284962 27.988011858490253)');
INSERT INTO "shapes".block(_key, id, shape) VALUES (7,7, 'POINT(-80.26109141065051 27.31654822659739)');
INSERT INTO "shapes".block(_key, id, shape) VALUES (8,8, 'POLYGON((-80.95740306914789 27.806634697867153, -80.36001876156914 27.838020410985237, -80.60843599838408 27.602406791332584, -81.15258804093104 27.623370824383542, -80.95740306914789 27.806634697867153))');


-- search for shapes within a polygon

SELECT * FROM "shapes".block WHERE lucene = '{
   refresh: true,
   filter: {
      type: "geo_shape",
      field: "place",
      shape: {
         type: "wkt",
         value: "POLYGON((-80.14247278479951 25.795756477689594, -66.11315588592869 18.47447597127288, -64.82713517019887 32.33019640254669, -80.14247278479951 25.795756477689594))"
      }
    }
}';

-- search for shapes intersecting with a shape defined by a buffer 10 kilometers around a segment of the Florida's coastline

SELECT * FROM "shapes".block WHERE lucene = '{
	   refresh: true,
       filter: {
          type: "geo_shape",
          field: "place",
          operation: "intersects",
          shape: {
             type: "buffer",
             max_distance: "10km",
             shape: {
                type: "wkt",
                value: "LINESTRING(-80.90 29.05, -80.51 28.47, -80.60 28.12, -80.00 26.85, -80.05 26.37)"
             }
          }
       }
 }';


-- cities samples

INSERT INTO "shapes".city(_key, name, shape) VALUES ('birmingham', 'birmingham', 'POLYGON((-2.25 52.63, -2.26 52.49, -2.13 52.36, -1.80 52.34, -1.57 52.54, -1.89 52.67, -2.25 52.63))');
INSERT INTO "shapes".city(_key, name, shape) VALUES ('london', 'london', 'POLYGON((-0.55 51.50, -0.13 51.19, 0.21 51.35, 0.30 51.62, -0.02 51.75, -0.34 51.69, -0.55 51.50))');

-- first  calculate distance to Manchester - POINT(-2.2430027137209585 53.47203269837166)

SELECT name,
PUBLIC.ST_DISTANCE_SPHERE(shape, 'POINT(-2.2430027137209585 53.47203269837166)')/1000 as from_shape_to_manchester,
PUBLIC.ST_DISTANCE_SPHERE(PUBLIC.ST_CENTROID(shape), 'POINT(-2.2430027137209585 53.47203269837166)')/1000 as from_shape_centroid_to_manchester,
PUBLIC.ST_DISTANCE_SPHERE(PUBLIC.ST_BUFFER(shape, 50*0.0111), 'POINT(-2.2430027137209585 53.47203269837166)')/1000 as from_shape_buffer_to_manchester
FROM "shapes".city;

-- +------------+--------------------------+-----------------------------------+---------------------------------+
-- |       NAME | FROM_SHAPE_TO_MANCHESTER | FROM_SHAPE_CENTROID_TO_MANCHESTER | FROM_SHAPE_BUFFER_TO_MANCHESTER |
-- +============+==========================+===================================+=================================+
-- | birmingham |        93.63106100706167 |                109.09669692793412 |               32.61094197850765 |
-- +------------+--------------------------+-----------------------------------+---------------------------------+
-- |     london |       247.42114600716855 |                 263.4855758215071 |              195.00559063088554 |
-- +------------+--------------------------+-----------------------------------+---------------------------------

-- search for shapes within a distance of 50km from Manchester POINT(-2.2430027137209585 53.47203269837166)
-- this will return an EMPTY resulset - as cities (place - polygon) are at a distante of 93.63106100706167 KM and 247.42114600716855 KM 
SELECT * FROM "shapes".city WHERE lucene = '{
	   refresh: true,
       filter: {
          type: "geo_shape",
          field: "place",
          operation: "intersects",
          shape: {
             type: "buffer",
             max_distance: "50km",
             shape: {
                type: "wkt",
                value: "POINT(-2.2430027137209585 53.47203269837166)"
             }
          }
       }
 }';

-- search for shape centroides within a distance of 50km from Manchester POINT(-2.2430027137209585 53.47203269837166)
-- this will return an EMPTY resulset - as cities (place_centroid - centroid of shape) are at a distante of 109.09669692793412 KM and 263.4855758215071 KM
SELECT * FROM "shapes".city WHERE lucene = '{
	   refresh: true,
       filter: {
          type: "geo_shape",
          field: "place_centroid",
          operation: "intersects",
          shape: {
             type: "buffer",
             max_distance: "50km",
             shape: {
                type: "wkt",
                value: "POINT(-2.2430027137209585 53.47203269837166)"
             }
          }
       }
 }';

-- search for shape buffers within a distance of 50km from Manchester POINT(-2.2430027137209585 53.47203269837166)
-- this will return a row as birmingham (place_buffer - buffer of shape) is at a distante of 32.61094197850765
SELECT * FROM "shapes".city WHERE lucene = '{
	   refresh: true,
       filter: {
          type: "geo_shape",
          field: "place_buffer",
          operation: "intersects",
          shape: {
             type: "buffer",
             max_distance: "50km",
             shape: {
                type: "wkt",
                value: "POINT(-2.2430027137209585 53.47203269837166)"
             }
          }
       }
 }';

-- +------------+------------------------------------------------------------------------------------------------------+
-- |       NAME |                                                                                                SHAPE |
-- +============+======================================================================================================+
-- | birmingham | POLYGON ((-2.25 52.63, -2.26 52.49, -2.13 52.36, -1.8 52.34, -1.57 52.54, -1.89 52.67, -2.25 52.63)) |
-- +------------+------------------------------------------------------------------------------------------------------+

-- borders samples
INSERT INTO "shapes".border(_key, name, shape) VALUES ('france','france', 'LINESTRING(-1.7692623510307564 43.33616076038274, -1.2505740047697933 43.05871685595872, -0.2260349939872593 42.77098869300744, 0.681245743448637 42.818493812398145, 2.1252193765092477 42.468351698279776, 3.1051001377164886 42.43862842055378)');
INSERT INTO "shapes".border(_key, name, shape) VALUES ('portugal','portugal', 'LINESTRING(-8.856095991604167 41.939536163204366, -8.167701975450063 42.1743738747258, -8.130491488090382 41.80099080619647, -6.642071993703126 41.967209197025284, -6.251361876426471 41.5926099563257, -6.921150648900735 41.019594111075804, -6.995571623620098 39.71552328866082, -7.516518446655637 39.68689405837003, -6.958361136260418 39.039601047883025, -7.29325552249755 38.53201174087454, -6.976966379940258 38.196488460238356, -7.4979132029757976 37.579789414825896, -7.404886984576595 37.19544025819424)');

-- you should play with it ;)

