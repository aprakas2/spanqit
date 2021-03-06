package pers.aprakash.spanqit.graphpattern;

import pers.aprakash.spanqit.core.QueryElement;

/**
 * Denotes a SPARQL Graph Pattern
 * 
 * @author Ankit
 *
 * @see <a
 *      href="http://www.w3.org/TR/2013/REC-sparql11-query-20130321/#GraphPattern">
 *      SPARQL Graph Patterns</a>
 */
public interface GraphPattern extends QueryElement {
	/**
	 * @return if this pattern is a collection of GraphPatterns (ie., Group or
	 *         Alternative patterns), returns if the collection contains any
	 *         patterns
	 */
	public boolean isEmpty();
}