package ast;

/**
 * Display represents a display statement
 * 
 * @author Rohan Thakur
 * @version 1/10/22
 */
public class Display extends Statement
{
    private Expression exp;
    private Read readStmt;

    /**
     * Display constructor for the construction of a display statement that prints out the value of
     * the input expression and subsequently executes the input read statement
     *
     * @param exp the Expression object that represents the input expression
     * @param readStmt the Read object that represents the read statement
     */
    public Display(Expression exp, Read readStmt)
    {
        this.exp = exp;
        this.readStmt = readStmt;
    }

    /**
     * Gets the Display object's expression
     *
     * @return the Expression object that represents the expression whose value the display
     * statement will print out when executed
     */
    public Expression getExpression()
    {
        return this.exp;
    }

    /**
     * Gets the Display object's read statement
     * 
     * @return the Read object that represents the read statement to be executed after printing out
     * the Display object's expression value
     */
    public Read getReadStmt()
    {
        return this.readStmt;
    }
}
