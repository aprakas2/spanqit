package pers.aprakash.spanqit.constraint;

import java.util.ArrayList;

import pers.aprakash.spanqit.core.Assignable;
import pers.aprakash.spanqit.core.Assignment;
import pers.aprakash.spanqit.core.Spanqit;
import pers.aprakash.spanqit.core.Groupable;
import pers.aprakash.spanqit.core.Orderable;
import pers.aprakash.spanqit.core.QueryElementCollection;
import pers.aprakash.spanqit.core.Variable;

/**
 * A SPARQL expression. Used by filters, having clauses, order and group by
 * clauses, assignments, and as arguments to other expressions.
 *
 * @author Ankit
 * @param <T>
 *            the type of Expression (ie, Function or Operation). Used to
 *            support fluency
 *            
 * @see <a
 *      href="http://www.w3.org/TR/2013/REC-sparql11-query-20130321/#termConstraint">SPARQL
 *      Filters</a>
 *      <br>
 *      <a href="http://www.w3.org/TR/2013/REC-sparql11-query-20130321/#having">
 *      SPARQL HAVING</a>
 *      <br>
 *      <a href=
 *      "http://www.w3.org/TR/2013/REC-sparql11-query-20130321/#modOrderBy"
 *      >SPARQL ORDER BY</a>
 *      <br>
 *      <a
 *      href="http://www.w3.org/TR/2013/REC-sparql11-query-20130321/#groupby">
 *      SPARQL GROUP BY</a>
 *      <br>
 *      <a
 *      href="http://www.w3.org/TR/2013/REC-sparql11-query-20130321/#assignment">
 *      SPARQL Assignments</a>
 */
public abstract class Expression<T extends Expression<T>> extends
		QueryElementCollection<ExpressionOperand> implements ExpressionOperand,
		Orderable, Groupable, Assignable {
	protected SparqlOperator operator;

	Expression(SparqlOperator operator) {
		this(operator, " " + operator.getQueryString() + " ");
	}

	Expression(SparqlOperator operator, String delimeter) {
		super(delimeter, new ArrayList<ExpressionOperand>());
		this.operator = operator;
	}

	// ugh, wish the compiler dug just a little deeper...
	@SuppressWarnings("unchecked")
	T addOperand(ExpressionOperand... operands) {
		for (ExpressionOperand operand : operands) {
			elements.add(operand);
		}

		return (T) this;
	}

	ExpressionOperand getOperand(int index) {
		return ((ArrayList<ExpressionOperand>) elements).get(index);
	}
}