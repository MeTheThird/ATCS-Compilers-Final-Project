package environment;

import java.util.HashMap;

/**
 * Environment manages creating, updating, and fetching variables
 *
 * @author Rohan Thakur
 * @version 1/10/22
 */
public class Environment
{
    private HashMap<String, Integer> vars;

    /**
     * Environment constructor for the construction of an Environment object to use for storing
     * variables
     */
    public Environment()
    {
        this.vars = new HashMap<String, Integer>();
    }

    /**
     * Sets a variable to have a given value in the current environment
     *
     * @param variable the name of the variable to declare
     * @param value the value to assign to the variable
     */
    public void setVariable(String variable, int value)
    {
        this.vars.put(variable, value);
    }

    /**
     * Gets the value of the input variable from the current environment
     *
     * @param variable the name of the input variable
     * @return the value of the input variable
     */
    public int getVariable(String variable)
    {
        return this.vars.get(variable);
    }

}
