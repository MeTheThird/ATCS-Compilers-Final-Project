package ast;

/**
 * If represents an if statement
 * 
 * @author Rohan Thakur
 * @version 1/10/22
 */
public class If extends Statement
{
    private Expression expr;
    private Statement stmt;
// TODO: what if the expression doesn't have a relop? then eval to true if it's neq to 0
    /**
     * If constructor for the construction of an if statement with the input expression and input
     * statement to execute if the expression evaluates to true
     *
     * @param expr the Expression object that represents the input expression
     * @param stmt the Statement object that represents the input statement to execute if the
     * expression is true
     */
    public If(Expression expr, Statement stmt)
    {
        this.expr = expr;
        this.stmt = stmt;
    }

    /**
     * Gets the If object's expression
     * 
     * @return the Expression object representing the If object's expression
     */
    public Expression getExpression()
    {
        return this.expr;
    }

    /**
     * Gets the If object's statement to execute
     *
     * @return the Statement object representing the If object's statement to execute should the
     * expression evaluate to true
     */
    public Statement getStmt()
    {
        return this.stmt;
    }
}
