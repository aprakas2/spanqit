package pers.aprakash.spanqit.core;

import pers.aprakash.spanqit.graphpattern.GraphPattern;
import pers.aprakash.spanqit.graphpattern.GraphPatternNotTriple;
import pers.aprakash.spanqit.graphpattern.GraphPatterns;

/**
 * A SPARQL Query Pattern (<code>WHERE</code> clause)
 * 
 * @author Ankit
 *
 * @see <a
 *      href="http://www.w3.org/TR/2013/REC-sparql11-query-20130321/#GraphPattern">
 *      Query Pattern Definition</a>
 */
public class QueryPattern implements QueryElement {
	private static final String WHERE = "WHERE";

	private GraphPatternNotTriple where;

	// Package-protect default constructor
	QueryPattern() {
		
	}

	/**
	 * Add graph patterns to this query pattern. Adds the given patterns into
	 * this query pattern's group graph pattern
	 * 
	 * @param patterns
	 *            the patterns to add
	 * @return this
	 */
	public QueryPattern where(GraphPattern... patterns) {
		if(where == null) {
			where = GraphPatterns.and(patterns);
		} else {
			where.and(patterns);
		}

		return this;
	}
	
	@Override
	public String getQueryString() {
		StringBuilder whereClause = new StringBuilder();
		
		whereClause.append(WHERE).append(" ").append(where.getQueryString());

		return whereClause.toString();
	}
}