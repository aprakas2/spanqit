package pers.aprakash.spanqit.core;

import pers.aprakash.spanqit.rdf.IRI;

/**
 * A SPARQL Base declaration
 * 
 * @author Ankit
 *
 * @see <a
 *      href="http://www.w3.org/TR/2013/REC-sparql11-query-20130321/#relIRIs">
 *      SPARQL Relative IRIs</a>
 */
public class Base implements QueryElement {
	private static final String BASE = "BASE";

	private IRI iri;

	Base(IRI iri) {
		this.iri = iri;
	}

	@Override
	public String getQueryString() {
		return iri == null ? "" : BASE + " " + iri.getQueryString();
	}
}