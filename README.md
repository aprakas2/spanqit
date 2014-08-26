# Spanqit
An easy to use, open source, fluent Java API to create SPARQL query strings.
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
>**Note:** Spanqit requires Java 8

You will then be able to add the following dependency to your project(s) pom files:
```
<dependency>
	<groupId>pers.mprakash.rdf</groupId>
	<artifactId>Spanqit</artifactId>
	<version>0.0.1-SNAPSHOT</version>
</dependency>
```
>**Note:** This won't be necessary once Spanqit is actually in a maven repository.

***
## Using Spanqit
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
Spanqit uses three classes to represent the SPARQL graph patterns, all of which implement the `GraphPattern` interface:
- The `TriplePattern` class represents triple patterns.
- The `GraphPatternNotTriple` class represents collections of graph patterns.
- The `SubSelect` class represents a SPARQL sub query.

Graph patterns are created by the more aptly named `GraphPatterns` class. Use `GraphPatterns#tp()` to create a `TriplePattern` instance:
```
Prefix dc = Spanqit.prefix("dc", iri("http://purl.org/dc/elements/1.1/");
Variable book = Spanqit.var("book");

TriplePattern triple = GraphPatterns.tp(book, dc.iri("author"), literal("J.R.R. Tolkien"));
System.out.println(triple.getQueryString());
==> ?book dc:author "J.R.R. Tolkien"
```
Three methods in `GraphPatterns` to create `GraphPatternNotTriple` instances. `GraphPatterns#and()` creates a group graph pattern, consisting of the `GraphPattern` instances passed as parameters:
```
Variable mbox = Spanqit.var("mbox"), x = Spanqit.var("x");
GraphPatternNotTriple groupPattern =
GraphPatterns.and(x.has(foaf.iri("name"), name), x.has(foaf.iri("mbox"), mbox);
System.out.println(groupPattern.getQueryString());
==> { ?x foaf:mbox ?mbox . ?x foaf:name ?name }
```
`GraphPatterns#union()` creates an alternative graph pattern, taking the intersection of the provided `GraphPattern` instances:
```
Prefix dc10 = Spanqit.prefix("dc10", iri("http://purl.org/dc/elements/1.0/")),
	dc11 = Spanqit.prefix("dc11", iri("http://purl.org/dc/elements/1.1/"));
Variable book = Spanqit.var("book"), title = Spanqit.var("title");

GraphPatternNotTriple union = GraphPatterns.union(book.has(dc10.iri("title"), title),
	book.has(dc11.iri("title), title);
System.out.println(union.getQueryString());
==> { ?book dc10:title ?title } UNION { ?book dc11:title ?title }
```
`GraphPatterns#optional()` creates an optional group graph pattern, consisting of the passed in `GraphPattern`s:
```
GraphPatternNotTriple optionalPattern = GraphPatterns.optional(GraphPatterns.tp(x, foaf.iri("mbox"), mbox));
System.out.println(optionalPattern.getQueryString());
==> OPTIONAL { ?x foaf:mbox ?mbox }
```
Finally, `GraphPatterns#select(()` creates an instance of a `SubSelect`, which represents a SPARQL subquery:
```
SubSelect subQuery = GraphPatterns.select();
```
