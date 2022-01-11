package evaluator;

import ast.*;
import ast.Number;
import environment.Environment;

/**
 * Evaluator executes Programs comprised of AST Statements and Expressions
 * 
 * @author Rohan Thakur
 * @version 1/10/22
 */
public class Evaluator
{

    /**
     * Executes the input program under the input environment
     * 
     * @param program the input program
     * @param env the environment to use for variables and procedures
     */
    public void exec(Program program, Environment env)
    {
        for (ProcedureDeclaration procedure : program.getProcedures())
            env.setProcedure(procedure.getName(), procedure.getParams(), procedure.getStmt(),
                procedure.getLocalVars());
        for (Statement stmt : program.getStmts())
            exec(stmt, env);
    }

    /**
     * Executes the input statement
     * 
     * @param stmt the input statement
     * @param env the environment to use for variables and procedures
     */
    private void exec(Statement stmt, Environment env)
    {
        if (stmt.getClass() == Display.class) exec((Display) stmt, env);
        else if (stmt.getClass() == Assign.class) exec((Assign) stmt, env);
        else if (stmt.getClass() == Block.class) exec((Block) stmt, env);
        else if (stmt.getClass() == If.class) exec((If) stmt, env);
        else exec((While) stmt, env);
    }

    /**
     * Executes the input Writeln statement
     * 
     * @param writeln the input Writeln statement
     * @param env the environment to use for variables and procedures
     */
    private void exec(Display writeln, Environment env)
    {
        System.out.println(eval(writeln.getExpression(), env));
    }

    /**
     * Executes the input Assignment statement
     * 
     * @param assignment the input Assignment statement
     * @param env the environment to use for variables and procedures
     */
    private void exec(Assign assignment, Environment env)
    {
        env.setVariable(assignment.getVar(), eval(assignment.getExp(), env));
    }

    /**
     * Executes the input Block statement
     * 
     * @param block the input Block statement
     * @param env the environment to use for variables and procedures
     */
    private void exec(Block block, Environment env)
    {
        for (Statement stmt : block.getStmts()) exec(stmt, env);
    }

    /**
     * Executes the input If statement
     * 
     * @param ifStmt the input If statement
     * @param env the environment to use for variables and procedures
     */
    private void exec(If ifStmt, Environment env)
    {
        if (eval(ifStmt.getExpr(), env)) exec(ifStmt.getStmt(), env);
    }

    /**
     * Executes the input While statement
     * 
     * @param whileStmt the input While statement
     * @param env the environment to use for variables and procedures
     */
    private void exec(While whileStmt, Environment env)
    {
        while (eval(whileStmt.getExpr(), env)) exec(whileStmt.getStmt(), env);
    }
// TODO: for evaluating BinOps, check if the string contains a relop -- return 0 if false, 1 if true
// TODO: that simplifies the If statement cond checking bc we just need to check if it's neq to 0
// TODO: that also means that we'd print out 0/1 for printing out boolean expressions rather than
// TODO: true/false
    /**
     * Evaluates the input expression
     * 
     * @param exp the input expression
     * @param env the environment to use for variables and procedures
     * @return the value of the input expression
     */
    private int eval(Expression exp, Environment env)
    {
        if (exp.getClass() == Number.class) return eval((Number) exp, env);
        if (exp.getClass() == Variable.class) return eval((Variable) exp, env);
        if (exp.getClass() == BinOp.class) return eval((BinOp) exp, env);

        ProcedureCall procedureCall = (ProcedureCall) exp;
        Environment procedureEnv;

        // instantiates the environment for the procedure call
        if (env.getParentEnv() == null) procedureEnv = new Environment(env);
        else procedureEnv = new Environment(env.getParentEnv());

        // declares the return variables and argument variables
        assert (env.getParams(procedureCall.getName()).size() == procedureCall.getArgs().size());
        procedureEnv.declareVariable(procedureCall.getName(), 0);
        for (int i = 0; i < procedureCall.getArgs().size(); i++)
        {
            int value = eval(procedureCall.getArgs().get(i), env);
            procedureEnv.declareVariable(env.getParams(procedureCall.getName()).get(i), value);
        }

        // declares local variables
        for (int i = 0; i < env.getLocalVars(procedureCall.getName()).size(); i++)
            procedureEnv.declareVariable(env.getLocalVars(procedureCall.getName()).get(i), 0);

        exec(env.getProcedure(procedureCall.getName()), procedureEnv);
        return procedureEnv.getVariable(procedureCall.getName());
    }

    /**
     * Evaluates the input Number object
     * 
     * @param num the input Number object
     * @param env the environment to use for variables and procedures
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
     * @param env the environment to use for variables and procedures
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
     * @param env the environment to use for variables and procedures
     * @return the value of the input BinOp expression
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
            default: return exp1Val / exp2Val;
        }
    }

    /**
     * Evaluates the input Condition
     * 
     * @param cond the input Condition
     * @param env the environment to use for variables and procedures
     * @return true if the input Condition evaluates to true, false otherwise
     */
    public boolean eval(Condition cond, Environment env)
    {
        int exp1Val = eval(cond.getExp1(), env);
        int exp2Val = eval(cond.getExp2(), env);
        switch (cond.getRelop())
        {
            case "=":
                return exp1Val == exp2Val;
            case "<>":
                return exp1Val < exp2Val || exp1Val > exp2Val;
            case "<":
                return exp1Val < exp2Val;
            case ">":
                return exp1Val > exp2Val;
            case "<=":
                return exp1Val <= exp2Val;
            default:
                return exp1Val >= exp2Val;
        }
    }
}
