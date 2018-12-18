-- insert blog some entries
INSERT INTO "blog".blogEntry(id, author, comment, tags) VALUES(1, 'Jose', 'Apache Ignite is awesome', public.hkjson_to_list('["Apache Ignite"]'));
INSERT INTO "blog".blogEntry(id, author, comment, tags) VALUES(2, 'Manuel', 'Apache Ignite''s web console app is great', public.hkjson_to_list('["Apache Ignite", "management"]'));
INSERT INTO "blog".blogEntry(id, author, comment, tags) VALUES(3, 'Jorge', 'New Apache Ignite 2.6.0 has been released', public.hkjson_to_list('["Apache Ignite", "development"]'));
INSERT INTO "blog".blogEntry(id, author, comment, tags) VALUES(4, 'Arturo', 'Is there any embedded Apache Ignite service for tests?', public.hkjson_to_list('["Apache Ignite", "testing"]'));
INSERT INTO "blog".blogEntry(id, author, comment, tags) VALUES(5, 'Manuel', 'Apache Ignite''s SQL distributed database is a surprising feature.', public.hkjson_to_list('["Apache Ignite", "management", "development", "testing"]'));

-- search for blog entries that contains "Apache Ignite" on tags
SELECT * FROM "blog".blogEntry WHERE lucene = '{
   refresh: true,
   filter: {
      type: "boolean",
      must: [
         {type: "match", field: "tags", value: "Apache Ignite"}
      ]
   }
}';

-- search for blog entries that contains "Apache Ignite" and "management" on tags
SELECT * FROM "blog".blogEntry WHERE lucene = '{
   refresh: true,
   filter: {
      type: "boolean",
      must: [
         {type: "match", field: "tags", value: "Apache Ignite"},
         {type: "match", field: "tags", value: "management"}
      ]
   }
}';
