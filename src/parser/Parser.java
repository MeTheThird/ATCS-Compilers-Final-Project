package parser;

import java.util.*;

import ast.*;
import ast.Number;
import scanner.ScanErrorException;
import scanner.Scanner;

/**
 * Parser parses the input lexemes from an instance of the Scanner class
 *
 * @author Rohan Thakur
 * @version 1/10/22
 */
public class Parser
{
    private Scanner scanner;
    private String currentToken;

    /**
     * Parser constructor for the construction of a Parser that uses a Scanner as input
     *
     * @param scanner the input Scanner object to use
     */
    public Parser(Scanner scanner)
    {
        this.scanner = scanner;
        try
        {
            this.currentToken = scanner.nextToken();
        }
        catch (ScanErrorException e)
        {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    /**
     * Advances currentToken to the next token by calling scanner.nextToken() if the expected string
     * value matches currentToken, throws an IllegalArgumentException otherwise
     *
     * @param expected the expected value of currentToken
     * @throws IllegalArgumentException when the value of currentToken is not equal to the expected
     * value of currentToken
     */
    private void eat(String expected) throws IllegalArgumentException
    {
        // System.out.println("expected: " + expected + ", currentToken: " + currentToken);
        if (expected.equals(currentToken))
        {
            try
            {
                this.currentToken = scanner.nextToken();
            }
            catch (ScanErrorException e)
            {
                e.printStackTrace();
                System.exit(-1);
            }
        }
        else
            throw new IllegalArgumentException("Illegal character: expected " + expected +
                                            " and found " + currentToken);
    }

    /**
     * Parses the current program
     * 
     * @precondition currentToken begins a program
     * @postcondition currentToken has advanced to the end of the input stream, and all of the
     * tokens in the input stream have been eaten
     * @return a Program AST object that represents the parsed program
     */
    public Program parseProgram()
    {
        List<Statement> stmts = new ArrayList<Statement>();

        Statement stmt = parseStatement();
        stmts.add(stmt);
        while (!currentToken.equals("end") && !currentToken.equals("else"))
        {
            stmt = parseStatement();
            stmts.add(stmt);
        }
        return new Program(stmts);
    }

    /**
     * Parses the current statement or block of statements and returns a new AST object of the
     * appropriate type for evaluation by the Evaluator
     *
     * @precondition currentToken begins a statement
     * @postcondition currentToken has advanced past the current statement, all of the current
     * statement's associated tokens have been eaten
     * @return a Statement AST object that represents the current statement
     */
    private Statement parseStatement()
    {
        switch (currentToken)
        {
            case "display":
                eat("display");
                Expression displayExpression = parseExpression();
                Read readStmt = null;
                if (currentToken.equals("read"))
                {
                    eat("read");
                    readStmt = new Read(currentToken);
                    eat(currentToken);
                }
                return new Display(displayExpression, readStmt);

            case "assign":
                eat("assign");
                String varName = currentToken;
                eat(currentToken);
                eat("=");
                Expression varVal = parseExpression();
                return new Assign(varName, varVal);

            case "while":
                eat("while");
                Expression whileExpression = parseExpression();
                eat("do");
                Program whileProgram = parseProgram();
                eat("end");
                return new While(whileExpression, whileProgram);

            default:
                eat("if");
                Expression ifExpression = parseExpression();
                eat("then");
                Program ifTrueProgram = parseProgram();
                Program ifFalseProgram = null;
                if (!currentToken.equals("end"))
                {
                    eat("else");
                    ifFalseProgram = parseProgram();
                }
                eat("end");
                return new If(ifExpression, ifTrueProgram, ifFalseProgram);
        }
    }

    /**
     * Parses the current integer expression
     *
     * @precondition currentToken begins an integer expression
     * @postcondition currentToken has advanced past the current expression, all of the expression's
     * associated tokens have been eaten
     * @return an Expression AST object that represents the parsed expression
     */
    private Expression parseExpression()
    {
        Expression ret = parseAddExpr();
        while (currentToken.equals("<") || currentToken.equals(">") || currentToken.equals(">=") ||
               currentToken.equals("<=") || currentToken.equals("<>") || currentToken.equals("="))
        {
            String op = currentToken;
            eat(currentToken);
            ret = new BinOp(op, ret, parseAddExpr());
        }
        return ret;
    }

    /**
     * Parses the current integer addition/subtraction expression
     *
     * @precondition currentToken begins an addition/subtraction expression
     * @postcondition currentToken has advanced past the current addition/subtraction expression,
     * all of the current addition/subtraction expression's associated tokens have been eaten
     * @return an Expression AST object that represents the parsed addition/subtraction expression
     */
    private Expression parseAddExpr()
    {
        Expression ret = parseMultExpr();
        while (currentToken.equals("+") || currentToken.equals("-"))
        {
            String op = currentToken;
            eat(currentToken);
            ret = new BinOp(op, ret, parseMultExpr());
        }
        return ret;
    }

    /**
     * Parses the current integer multiplication/division expression
     *
     * @precondition currentToken begins an multiplication/division expression
     * @postcondition currentToken has advanced past the current multiplication/division expression,
     * all of the current multiplication/division expression's associated tokens have been eaten
     * @return an Expression AST object that represents the parsed multiplication/division
     * expression
     */
    private Expression parseMultExpr()
    {
        Expression ret = parseNegExpr();
        while (currentToken.equals("*") || currentToken.equals("/"))
        {
            String op = currentToken;
            eat(currentToken);
            ret = new BinOp(op, ret, parseNegExpr());
        }
        return ret;
    }

    /**
     * Parses the value of the current integer term that may be combined with other terms via
     * addition, subtraction, multiplication, or division
     *
     * @precondition currentToken begins an integer term
     * @postcondition currentToken has advanced past the current integer term, all of the current
     * integer term's associated tokens have been eaten
     * @return an Expression AST object that represents the parsed integer term
     */
    private Expression parseNegExpr()
    {
        if (currentToken.equals("-")) return new BinOp("*", new Number(-1), parseValue());
        return parseValue();
    }

    /**
     * Parses the current integer value, which is a number, variable, or an Expression enclosed in
     * parentheses
     *
     * @precondition currentToken begins an integer value
     * @postcondition currentToken has advanced past the current integer value, all of the value's
     * associated tokens have been eaten
     * @return an Expression AST object that represents the parsed integer value
     */
    private Expression parseValue()
    {
        if (currentToken.equals("("))
        {
            eat("(");
            Expression ret = parseExpression();
            eat(")");
            return ret;
        }
        if (currentToken.matches("[0-9]+"))
        {
            int numVal = Integer.parseInt(currentToken);
            eat(currentToken);
            return new Number(numVal);
        }
        String id = currentToken;
        eat(id);
        return new Variable(id);
    }
}
