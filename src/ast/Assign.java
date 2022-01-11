package ast;

/**
 * Assign represents assign statements
 * 
 * @author Rohan Thakur
 * @version 1/10/22
 */
public class Assign extends Statement
{
    private String var;
    private Expression exp;

    /**
     * Assign constructor for the construction of an assign statement that sets the input
     * variable to the input expression
     *
     * @param var the input variable name
     * @param exp the input expression
     */
    public Assign(String var, Expression exp)
    {
        this.var = var;
        this.exp = exp;
    }

    /**
     * Gets the Assign object's variable name
     * 
     * @return the name of the variable in the assign statement as a String
     */
    public String getVar()
    {
        return this.var;
    }

    /**
     * Gets the Assign object's expression
     * 
     * @return the Expression object being assigned to the Assign object's variable
     */
    public Expression getExp()
    {
        return this.exp;
    }
}
