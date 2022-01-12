package ast;

/**
 * While represents a while statement
 *
 * @author Rohan Thakur
 * @version 1/11/22
 */
public class While extends Statement
{
    private Expression expr;
    private Program program;

    /**
     * While constructor for the construction of a while statement with the input expression and
     * input program to execute while the expression evaluates to true
     *
     * @param expr the Expression object that represents the input expression
     * @param program the Program object that represents the program to execute while the expression
     * is true
     */
    public While(Expression expr, Program program)
    {
        this.expr = expr;
        this.program = program;
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
     * Gets the While object's program to execute
     *
     * @return the Program object representing the While object's program to execute while the
     * expression evaluates to true
     */
    public Program getProgram()
    {
        return this.program;
    }
}
