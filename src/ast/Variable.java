package ast;

/**
 * Variable represents a variable
 * 
 * @author Rohan Thakur
 * @version 1/10/22
 */
public class Variable extends Expression
{
    private String name;

    /**
     * Variable constructor for the construction of a variable with the input name
     * 
     * @param name the input variable name as a String
     */
    public Variable(String name)
    {
        this.name = name;
    }

    /**
     * Gets the Variable object's variable name
     * 
     * @return the variable name of the Variable object as a String
     */
    public String getName()
    {
        return this.name;
    }
}
