package ast;

/**
 * BinOp represents binary operator expressions
 * 
 * @author Rohan Thakur
 * @version 1/10/22
 */
public class BinOp extends Expression
{
// TODO: note that the op could be a relop
    private String op;
    private Expression exp1;
    private Expression exp2;

    /**
     * BinOp constructor for the construction of a binary operator expression with two expressions
     * separated by a binary operator
     *
     * @param op the binary operator as a String
     * @param exp1 the Expression object that represents the expression on the left-hand side of the
     * binary operator
     * @param exp2 the Expression object that represents the expression on the right-hand side of
     * the binary operator
     */
    public BinOp(String op, Expression exp1, Expression exp2)
    {
        this.op = op;
        this.exp1 = exp1;
        this.exp2 = exp2;
    }

    /**
     * Gets the BinOp object's binary operator
     * 
     * @return the binary operator as a String
     */
    public String getOp()
    {
        return this.op;
    }

    /**
     * Gets the BinOp object's expression on the left-hand side of the binary operator
     *
     * @return the Expression object that represents the expression on the left-hand side of the
     * binary operator
     */
    public Expression getExp1()
    {
        return this.exp1;
    }

    /**
     * Gets the BinOp object's expression on the right-hand side of the binary operator
     * 
     * @return the Expression object that represents the expression on the right-hand side of the
     * binary operator
     */
    public Expression getExp2()
    {
        return this.exp2;
    }
}
