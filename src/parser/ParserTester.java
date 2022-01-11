package parser;

import java.io.*;

import ast.Program;
import environment.Environment;
import evaluator.Evaluator;
import scanner.Scanner;

/**
 * ParserTester tests whether the compiler properly compiles a given simplified PASCAL program in
 * addition to testing the parsing and evaluation components of the compiler
 *
 * @author Rohan Thakur
 * @version 1/10/22
 */
public class ParserTester
{
    /**
     * Tests compiling a given simplified PASCAL program by compiling the output of the parseProgram
     * Parser function in addition to testing the parsing and evaluation phases of the compiler
     *
     * @param args the command-line input args
     */
    public static void main(String[] args)
    {
        try
        {
            FileInputStream inStream = new FileInputStream(new File("./src/parser" +
                                                                    "/parserTest0.txt"));
            Scanner scanner = new Scanner(inStream);
            Parser parser = new Parser(scanner);
            Evaluator eval = new Evaluator();
            Environment env = new Environment();
            Compiler compiler = new Compiler();

            Program program = parser.parseProgram();
            compiler.compile(program, "output.asm");
            eval.exec(program, env);
        }
        catch(FileNotFoundException e)
        {
            e.printStackTrace();
            System.exit(-1);
        }
    }
}
