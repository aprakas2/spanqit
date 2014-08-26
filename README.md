# Spanqit
An easy to use, easy to read, fluent Java API to create SPARQL query strings.
***
## Introduction
Spanqit is a fluent Java API used to programmatically create SPARQL query strings. It is not explicitly dependent on any RDF or SPARQL libraries, and can thus be used with any SPARQL query processor (though with a small caveat, to be detailed later).

Spanqit allows the following SPARQL query:
```
PREFIX foaf: <http://xmlns.com/foaf/0.1/>
SELECT ?name
WHERE { ?x foaf:name ?name }
ORDER BY ?name
LIMIT 5
OFFSET 10
```
to be created as simply as:
```
query.prefix(foaf).select(name)
    .where(x.has(foaf.iri("name"), name))
    .orderBy(name)
    .limit(5)
    .offset(10);
```

Spanqit is based on [this](http://www.w3.org/TR/2013/REC-sparql11-query-20130321/) version SPARQL 1.1 Spec. Almost all features of SPARQL 1.1 are supported, excluding some current known limitations.
>**Note:** This document assumes the user is familiar with the SPARQL query language. Please refer to the above specification if not.

## Basics
Spanqit currently supports `SELECT` and `CONSTRUCT` queries. The `Queries` class provides static methods to instantiate `SelectQuery` and `ConstructQuery` objects. 
