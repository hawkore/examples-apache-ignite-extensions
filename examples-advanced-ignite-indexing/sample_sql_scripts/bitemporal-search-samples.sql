
-- For example, we want to implement a system for census bureau to track where resides a citizen and when the census bureau knows this. 

-- The population of 5 citizens lives in each city from 2015/01/01 until now 

INSERT INTO "test".census(name, city, vtFrom, vtTo, ttFrom, ttTo) VALUES ('John', 'Madrid', '2015/01/01', '2200/12/31', '2015/01/01', '2200/12/31');
INSERT INTO "test".census(name, city, vtFrom, vtTo, ttFrom, ttTo) VALUES ('Margaret', 'Barcelona', '2015/01/01', '2200/12/31', '2015/01/01', '2200/12/31');
INSERT INTO "test".census(name, city, vtFrom, vtTo, ttFrom, ttTo) VALUES ('Cristian', 'Ceuta', '2015/01/01', '2200/12/31', '2015/01/01', '2200/12/31');
INSERT INTO "test".census(name, city, vtFrom, vtTo, ttFrom, ttTo) VALUES ('Edward', 'New York','2015/01/01', '2200/12/31', '2015/01/01', '2200/12/31');
INSERT INTO "test".census(name, city, vtFrom, vtTo, ttFrom, ttTo) VALUES ('Johnatan', 'San Francisco', '2015/01/01', '2200/12/31', '2015/01/01', '2200/12/31');


-- John moves to Amsterdam in '2015/03/05' but he does not communicate this to census bureau until '2015/06/29' because he need it to apply for taxes reduction.

-- So, the system need to update last information from John,and insert the new. This is done with batch execution updating the transaction time end of previous data and inserting new. 


-- This update until when the system believed in this false information
UPDATE "test".census SET ttTo = '2015/06/29' WHERE name = 'John' AND vtFrom = '2015/01/01' AND ttFrom = '2015/01/01' AND ttTo = '2200/12/31';

-- Here inserts the new knowledge about the period where john resided in Madrid
INSERT INTO "test".census(name, city, vtFrom, vtTo, ttFrom, ttTo) VALUES ('John', 'Madrid', '2015/01/01', '2015/03/04', '2015/06/30', '2200/12/31');

-- This inserts the new knowledge about the period where john resides in Amsterdam
INSERT INTO "test".census(name, city, vtFrom, vtTo, ttFrom, ttTo) VALUES ('John', 'Amsterdam', '2015/03/05', '2200/12/31', '2015/06/30', '2200/12/31');

-- Now , we can see the main difference between valid time and transaction time. The system knows from '2015/01/01' to '2015/06/29' that John resides in Madrid from '2015/01/01' until now, and resides in Amsterdam from '2015/03/05' until now.

-- There are several types of queries concerning this type of indexing.

-- If its needed to get all the data in the table (with refresh:true to ensure that lucene data is committed before search):

SELECT name, city, vtFrom, vtTo, ttFrom, ttTo 
FROM "test".census USE INDEX (census_lucene_idx)
WHERE lucene = '{ refresh: true }';

-- If you want to know what is the last info about where John have resided, you perform a query with tt_from and tt_to set to now_value:

SELECT name, city, vtFrom, vtTo, ttFrom, ttTo 
FROM "test".census USE INDEX (census_lucene_idx) 
WHERE lucene = '{
    filter : {
      type : "bitemporal", 
      field : "bitemporal", 
      vt_from : 0, 
      vt_to : "2200/12/31", 
      tt_from : "2200/12/31", 
      tt_to : "2200/12/31"
    }
}' AND name='John';


-- If you want to know what is the last info about where John resides now, you perform a query with tt_from, tt_to, vt_from, vt_to set to now_value:

SELECT name, city, vtFrom, vtTo, ttFrom, ttTo 
FROM "test".census USE INDEX (census_lucene_idx) 
WHERE lucene = '{
    filter : {
      type : "bitemporal",
      field : "bitemporal",
      vt_from : "2200/12/31",
      vt_to : "2200/12/31",
      tt_from : "2200/12/31",
      tt_to : "2200/12/31"
    }
}' AND name='John';

-- If the test case needs to know what the system was thinking at '2015/03/01' about where John resides in "2015/03/01":

SELECT name, city, vtFrom, vtTo, ttFrom, ttTo 
FROM "test".census USE INDEX (census_lucene_idx) 
WHERE lucene = '{
    filter : {
      type : "bitemporal", 
      field : "bitemporal", 
      vt_from: "2015/03/01",
      vt_to : "2015/03/01",
      tt_from : "2015/03/01",
      tt_to : "2015/03/01"
    }
}' AND name='John';

-- If the test case needs to know what the system was thinking at '2015/07/05' about where John resides:

SELECT name, city, vtFrom, vtTo, ttFrom, ttTo 
FROM "test".census USE INDEX (census_lucene_idx) 
WHERE lucene = '{
    filter : {
      type : "bitemporal",
      field : "bitemporal",
      tt_from : "2015/07/05",
      tt_to : "2015/07/05"
    }
}' AND name='John';



