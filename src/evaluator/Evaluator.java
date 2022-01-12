package evaluator;

import java.util.Scanner;

import ast.*;
import ast.Number;
import environment.Environment;

/**
 * Evaluator executes Programs comprised of AST Statements and Expressions
 * 
 * @author Rohan Thakur
 * @version 1/11/22
 */
public class Evaluator
{
    /**
     * Executes the input program under the input environment
     * 
     * @param program the input program
     * @param env the environment to use for variables
     */
    public void exec(Program program, Environment env)
    {
        for (Statement stmt : program.getStmts()) exec(stmt, env);
    }

    /**
     * Executes the input statement
     * 
     * @param stmt the input statement
     * @param env the environment to use for variables
     */
    private void exec(Statement stmt, Environment env)
    {
        if (stmt.getClass() == Display.class) exec((Display) stmt, env);
        else if (stmt.getClass() == Assign.class) exec((Assign) stmt, env);
        else if (stmt.getClass() == If.class) exec((If) stmt, env);
        else exec((While) stmt, env);
    }

    /**
     * Executes the input display statement
     * 
     * @param display the input Display statement
     * @param env the environment to use for variables
     */
    private void exec(Display display, Environment env)
    {
        System.out.println(eval(display.getExpression(), env));
        if (display.getReadStmt() != null) exec(display.getReadStmt(), env);
    }

    /**
     * Executes the input read statement
     * 
     * @param readStmt the input Read statement
     * @param env the environment to use for variables
     */
    private void exec(Read readStmt, Environment env)
    {
        System.out.print("Enter a value for the variable " + readStmt.getVar() + ": ");
        Scanner scanner = new Scanner(System.in);
        int val = Integer.parseInt(scanner.nextLine());
        scanner.close();
        env.setVariable(readStmt.getVar(), val);
    }

    /**
     * Executes the input assign statement
     * 
     * @param assign the input Assign statement
     * @param env the environment to use for variables
     */
    private void exec(Assign assign, Environment env)
    {
        env.setVariable(assign.getVar(), eval(assign.getExp(), env));
    }

    /**
     * Executes the input if/if-else statement
     * 
     * @param ifStmt the input If statement
     * @param env the environment to use for variables
     */
    private void exec(If ifStmt, Environment env)
    {
        if (eval(ifStmt.getExpression(), env) != 0) exec(ifStmt.getTrueProgram(), env);
        else if (ifStmt.getFalseProgram() != null) exec(ifStmt.getFalseProgram(), env);
    }

    /**
     * Executes the input While statement
     * 
     * @param whileStmt the input While statement
     * @param env the environment to use for variables
     */
    private void exec(While whileStmt, Environment env)
    {
        while (eval(whileStmt.getExpression(), env) != 0) exec(whileStmt.getProgram(), env);
    }

    /**
     * Evaluates the input expression
     * 
     * @param exp the input expression
     * @param env the environment to use for variables
     * @return the value of the input expression
     */
    private int eval(Expression exp, Environment env)
    {
        if (exp.getClass() == Number.class) return eval((Number) exp, env);
        if (exp.getClass() == Variable.class) return eval((Variable) exp, env);
        return eval((BinOp) exp, env);
    }

    /**
     * Evaluates the input Number object
     * 
     * @param num the input Number object
     * @param env the environment to use for variables
     * @return the value of the input Number object
     */
    private int eval(Number num, Environment env)
    {
        return num.getValue();
    }

    /**
     * Evaluates the input Variable
     * 
     * @param var the input Variable
     * @param env the environment to use for variables
     * @return the value of the input Variable
     */
    private int eval(Variable var, Environment env)
    {
        return env.getVariable(var.getName());
    }

    /**
     * Evaluates the input BinOp expression
     * 
     * @param binop the input BinOp expression
     * @param env the environment to use for variables
     * @return the value of the input BinOp expression, taking the value of boolean expressions to
     * be 1 if true and 0 if false
     */
    private int eval(BinOp binop, Environment env)
    {
        int exp1Val = eval(binop.getExp1(), env);
        int exp2Val = eval(binop.getExp2(), env);
        switch (binop.getOp())
        {
            case "+": return exp1Val + exp2Val;
            case "-": return exp1Val - exp2Val;
            case "*": return exp1Val * exp2Val;
            case "/": return exp1Val / exp2Val;
            case "=": return exp1Val == exp2Val ? 1 : 0;
            case "<>": return exp1Val != exp2Val ? 1 : 0;
            case "<": return exp1Val < exp2Val ? 1 : 0;
            case ">": return exp1Val > exp2Val ? 1 : 0;
            case "<=": return exp1Val <= exp2Val ? 1 : 0;
            default: return exp1Val >= exp2Val ? 1 : 0;
        }
    }

}
