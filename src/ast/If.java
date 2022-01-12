package ast;

/**
 * If represents an if statement
 * 
 * @author Rohan Thakur
 * @version 1/11/22
 */
public class If extends Statement
{
    private Expression expr;
    private Program trueProgram;
    private Program falseProgram;

    /**
     * If constructor for the construction of an if statement with the input expression, input
     * program to execute if the expression evaluates to true, and input program to execute if the
     * expression evaluates to false
     *
     * @param expr the Expression object that represents the input expression
     * @param trueProgram the Program object that represents the program to execute if the
     * expression is true
     * @param falseProgram the Program object that represents the program to execute if the
     * expression is false
     */
    public If(Expression expr, Program trueProgram, Program falseProgram)
    {
        this.expr = expr;
        this.trueProgram = trueProgram;
        this.falseProgram = falseProgram;
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
     * Gets the If object's program to execute if the expression is true
     *
     * @return the Program object representing the If object's program to execute should the
     * expression evaluate to true
     */
    public Program getTrueProgram()
    {
        return this.trueProgram;
    }

    /**
     * Gets the If object's program to execute if the expression is false
     *
     * @return the Program object representing the If object's program to execute should the
     * expression evaluate to false
     */
    public Program getFalseProgram()
    {
        return this.falseProgram;
    }
}
