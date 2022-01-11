package ast;

/**
 * Read represents a read statement
 * 
 * @author Rohan Thakur
 * @version 1/10/22
 */
public class Read extends Statement
{
    private String var;

    /**
     * Read constructor for the construction of a read statement that prompts the user to provide a
     * value for the input variable
     * 
     * @param var the String representing the input variable's name
     */
    public Read(String var)
    {
        this.var = var;
    }

    /**
     * Gets the name of the Read object's variable
     *
     * @return the String representing the name of the Read object's corresponding variable
     */
    public String getVar()
    {
        return this.var;
    }
}
