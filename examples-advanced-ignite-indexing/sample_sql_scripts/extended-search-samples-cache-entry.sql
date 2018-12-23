-- Lucene index search on cache entry with simple types

insert into "str".TABLE (_key,_val) values (1, 'a very large text one');
insert into "str".TABLE (_key,_val) values (2, 'a very large text two');
insert into "str".TABLE (_key,_val) values (3, 'a very large text three');
insert into "str".TABLE (_key,_val) values (4, 'a very large text four');


-- Refresh index
SELECT * FROM "str".TABLE
WHERE lucene = '{ refresh : true}';


-- Phrase query on _VAL cache entry
SELECT * FROM "str".TABLE
WHERE lucene = '{refresh : true, query : {
                            type   : "phrase",
                            field  : "_VAL",
                            value : "three" }}';

-- Match query on _KEY cache entry
SELECT * FROM "str".TABLE
WHERE lucene = '{refresh : true, query : {
                            type   : "match",
                            field  : "_KEY",
                            value : 2 }}';