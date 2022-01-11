package ast;

/**
 * While represents a while statement
 *
 * @author Rohan Thakur
 * @version 1/10/22
 */
public class While extends Statement
{
    private Expression expr;
    private Statement stmt;
// TODO: what if the expression doesn't have a relop? then eval to true if it's neq to 0
    /**
     * While constructor for the construction of a while statement with the input expression and
     * input statement to execute while the expression evaluates to true
     *
     * @param expr the Expression object that represents the input expression
     * @param stmt the Statement object that represents the input statement to execute while the
     * expression is true
     */
    public While(Expression expr, Statement stmt)
    {
        this.expr = expr;
        this.stmt = stmt;
    }

    /**
     * Gets the While object's expression
     *
     * @return the Expression object representing the While object's expression
     */
    public Expression getExpression()
    {
        return this.expr;
    }

    /**
     * Gets the While object's statement to execute
     *
     * @return the Statement object representing the While object's statement to execute while the
     * expression evaluates to true
     */
    public Statement getStmt()
    {
        return this.stmt;
    }
}
