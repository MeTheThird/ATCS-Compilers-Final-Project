package ast;

/**
 * Number represents an integer
 * 
 * @author Rohan Thakur
 * @version 1/10/22
 */
public class Number extends Expression
{
    private int value;

    /**
     * Number constructor for the construction of a Number object with the input integer value
     * 
     * @param value the input integer value
     */
    public Number(int value)
    {
        this.value = value;
    }

    /**
     * Gets the Number object's integer value
     * 
     * @return the integer value of the Number object
     */
    public int getValue()
    {
        return this.value;
    }
}
