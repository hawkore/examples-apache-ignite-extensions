
-- before search you must populate cache running from a console shell at 'examples-advanced-ignite-indexing' directory below command: 
-- mvn exec:java -Dexec.mainClass="com.hawkore.ignite.examples.PopulateProfiles" -Dexec.classpathScope=compile -DnodeName=populator

-- search within complex java type

SELECT 
LOGIN, 
FIRSTNAME, 
LASTNAME,
ADDRESS_CITY,
ADDRESS_ZIP,
ADDRESS_STREET
FROM "profiles".profile WHERE lucene = '{
   refresh: true,
   filter: {
      type: "match",
      field: "address.city",
      value: "Chicago"
   }
}';

SELECT 
LOGIN, 
FIRSTNAME, 
LASTNAME,
ADDRESS_CITY,
ADDRESS_ZIP,
ADDRESS_STREET
FROM "profiles".profile WHERE lucene = '{
   refresh: true,
   filter: {
      type: "match",
      field: "address.zip",
      value: 60601
   }
}';

SELECT 
LOGIN, 
FIRSTNAME, 
LASTNAME,
ADDRESS_CITY,
ADDRESS_ZIP,
ADDRESS_STREET
FROM "profiles".profile WHERE lucene = '{
   refresh: true,
   sort: {
      field: "address.zip",
      reverse: true
   }
}';

-- search within collections of simple types

SELECT 
LOGIN, 
FIRSTNAME, 
LASTNAME,
CITIES
FROM "profiles".profile WHERE lucene = '{
   refresh: true,
   filter: {
      type: "match",
      field: "cities",
      value: "London"
   }
}';

SELECT 
LOGIN, 
FIRSTNAME, 
LASTNAME,
CITIES
FROM "profiles".profile WHERE lucene = '{
   refresh: true,
   filter: {
      type: "match",
      field: "cities",
      value: "OtherCity_20"
   }
}';

-- search within collections of complex types

SELECT 
LOGIN, 
FIRSTNAME, 
LASTNAME,
addressList
FROM "profiles".profile WHERE lucene = '{
   refresh: true,
   filter: {
      type: "match",
      field: "addressList.city",
      value: "Chicago"
   }
}';

SELECT 
LOGIN, 
FIRSTNAME, 
LASTNAME,
addressList
FROM "profiles".profile WHERE lucene = '{
   refresh: true,
   filter: {
      type: "match",
      field: "addressList.zip",
      value: 60601
   }
}';



-- search within maps of simple types

SELECT 
LOGIN, 
FIRSTNAME, 
LASTNAME,
textAddresses
FROM "profiles".profile WHERE lucene = '{
   refresh: true,
   filter: {
      type: "match",
      field: "textAddresses$London",
      value: "Camden Road #12"
   }
}';


SELECT 
LOGIN, 
FIRSTNAME, 
LASTNAME,
textAddresses
FROM "profiles".profile WHERE lucene = '{
       filter: {
          type: "match",
          field: "textAddresses._key",
          value: "OtherCity_17"
       }
}';


SELECT 
LOGIN, 
FIRSTNAME, 
LASTNAME,
textAddresses
FROM "profiles".profile WHERE lucene = '{
   refresh: true,
   filter: {
      type: "match",
      field: "textAddresses._value",
      value: "Paseo de la Castellana #50"
   }
}';

-- search within maps of complex types

SELECT 
LOGIN, 
FIRSTNAME, 
LASTNAME,
addresses
FROM "profiles".profile WHERE lucene = '{
   refresh: true,
   filter: {
      type: "match",
      field: "addresses.city$Illinois",
      value: "Chicago"
   }
}';

SELECT 
LOGIN, 
FIRSTNAME, 
LASTNAME,
addresses
FROM "profiles".profile WHERE lucene = '{
   refresh: true,
   filter: {
      type: "match",
      field: "addresses.zip$Illinois",
      value: 60601
   }
}';

SELECT 
LOGIN, 
FIRSTNAME, 
LASTNAME,
addresses
FROM "profiles".profile WHERE lucene = '{
   refresh: true,
   filter: {
      type: "match",
      field: "addresses._key",
      value: "OtherState_17"
   }
}';
