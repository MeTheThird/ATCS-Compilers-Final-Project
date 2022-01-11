package ast;

import java.util.List;

/**
 * Program represents a program
 * 
 * @author Rohan Thakur
 * @version 1/10/22
 */
public class Program
{
    private List<Statement> stmts;

    /**
     * Program constructor for the construction of a program with an input list of statements to be
     * executed
     *
     * @param stmts the input List of Statement objects
     */
    public Program(List<Statement> stmts)
    {
        this.stmts = stmts;
    }

    /**
     * Gets the program's list of statements that will be executed in the evaluation phase
     * 
     * @return the List of Statement objects for the current program
     */
    public List<Statement> getStmts()
    {
        return this.stmts;
    }

}
