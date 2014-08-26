# Spanqit
An easy to use, easy to read, open source, fluent Java API to create SPARQL query strings.
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
***
## Getting Spanqit
Obtain Spanqit by cloning this repo:
```
git clone https://github.com/aprakas2/spanqit.git
```
For maven users, change into the newly created `spanqit` and run the maven install command:
```
cd spanqit/
mvn install
```
Afterwards, add the following dependency to your project(s) pom files:
```
<dependency>
	<groupId>pers.mprakash.rdf</groupId>
	<artifactId>Spanqit</artifactId>
	<version>0.0.1-SNAPSHOT</version>
</dependency>
```
>**Note:** This won't be necessary once Spanqit is actually in a maven repository.
***
## Quick Start
### Queries
Spanqit currently supports `SELECT` and `CONSTRUCT` queries. The `Queries` class provides static methods to instantiate `SelectQuery` and `ConstructQuery` objects:
```
SelectQuery select = Queries.SELECT();
ConstructQuery construct = Queries.CONSTRUCT();
```
Query objects provide methods to set or add the various elements appropriate for the type of query:
```
Prefix ex;
Variable product;
TriplePattern personWroteBook, personAuthoredBook;
...
select.prefix(ex).select(product).where(product.isA(ex.iri("book"));
construct.prefix(ex).construct(personWroteBook).where(personAuthoredBook);
```
### Elements
Most elements of a query are created by the static `Spanqit` class. Exceptions are graph patterns and query constraints.
```
Variable price = Spanqit.var("price");
System.out.println(price.getQueryString());
==> ?price

Prefix foaf = Spanqit.prefix("foaf", iri("http://xmlns.com/foaf/0.1/"));
System.out.println(foaf.getQueryString());
==> PREFIX foaf: <http://xmlns.com/foaf/0.1/>
```
>**Hint:** All query elements created by Spanqit implement the `QueryElement` interface, which provides the `getQueryString()` method. This can be used to get the String representing the SPARQL syntax of any element.


### Graph Patterns
Graph patterns come from the more aptly named `GraphPatterns` class. 
