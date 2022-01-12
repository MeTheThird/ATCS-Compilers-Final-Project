package scanner;

import java.io.*;
import java.util.regex.Pattern;

/**
 * Scanner is a simple scanner for Compilers and Interpreters (2014-2015) lab exercise 1
 * 
 * @author Rohan Thakur
 * @version 1/12/22
 */
public class Scanner
{
    private BufferedReader in;
    private char currentChar;
    private boolean eof;

    /**
     * Scanner constructor for construction of a scanner that
     * uses an InputStream object for input.
     * 
     * Usage:
     * FileInputStream inStream = new FileInputStream(new File(<file name>);
     * Scanner lex = new Scanner(inStream);
     * 
     * @param inStream the input stream to use
     */
    public Scanner(InputStream inStream)
    {
        in = new BufferedReader(new InputStreamReader(inStream));
        eof = false;
        getNextChar();
    }

    /**
     * Scanner constructor for constructing a scanner that 
     * scans a given input string.  It sets the end-of-file flag an then reads
     * the first character of the input string into the instance field currentChar.
     * 
     * Usage: Scanner lex = new Scanner(input_string);
     * 
     * @param inString the string to scan
     */
    public Scanner(String inString)
    {
        in = new BufferedReader(new StringReader(inString));
        eof = false;
        getNextChar();
    }

    /**
     * Gets the next character in the input stream
     * 
     * @postcondition advanced to the next character in the input stream, eventually
     * setting eof to true once the end of the file has been reached
     */
    private void getNextChar()
    {
        try
        {
            int inp = in.read();
            if (inp == -1)
                eof = true;
            else
                currentChar = (char) inp;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    /**
     * Advances the input stream one character by calling getNextChar() if the
     * expected char value matches currentChar, throws a ScanErrorException otherwise
     * 
     * @param expected the expected value of currentChar
     * @throws ScanErrorException e
     */
    private void eat(char expected) throws ScanErrorException
    {
        if (expected == currentChar)
            getNextChar();
        else
            throw new ScanErrorException("Illegal character: expected " + expected +
                                            " and found " + currentChar);
    }

    /**
     * Returns whether the next character in the input stream exists
     * 
     * @return true if the next character exists, false otherwise
     */
    public boolean hasNext()
    {
        return !eof;
    }


    /**
     * Determines whether the input character is a digit
     * 
     * @param inChar the input character to be checked
     * @return true if the input character is a digit, false otherwise
     */
    public static boolean isDigit(char inChar)
    {
        return Pattern.matches("[0-9]", "" + inChar);
    }

    /**
     * Determines whether the input character is a letter
     * 
     * @param inChar the input character to be checked
     * @return true if the input character is a letter, false otherwise
     */
    public static boolean isLetter(char inChar)
    {
        return Pattern.matches("[a-zA-Z]", "" + inChar);
    }

    /**
     * Determines whether the input character is white space
     * 
     * @param inChar the input character to be checked
     * @return true if the input character is white space, false otherwise
     */
    public static boolean isWhiteSpace(char inChar)
    {
        return Pattern.matches("[ \t\r\n]", "" + inChar);
    }

    /**
     * Determines whether the input character is an operand
     * 
     * @param inChar the input character to be checked
     * @return true if the input character is an operand, false otherwise
     */
    public static boolean isOperand(char inChar)
    {
        return Pattern.matches("[-=+*/%();:<>,]", "" + inChar);
    }

    /**
     * Scans the input and returns the current numeric lexeme, throwing a
     * ScanErrorException if an invalid character is encountered
     * 
     * @precondition currentChar is a digit
     * @postcondition advanced to the next lexeme
     * @return the current numeric lexeme
     * @throws ScanErrorException e
     */
    private String scanNumber() throws ScanErrorException
    {
        String ret = "";
        while (hasNext() && isDigit(currentChar))
        {
            ret += currentChar;
            eat(currentChar);
        }

        if (hasNext() && !isWhiteSpace(currentChar) && !isOperand(currentChar))
            throw new ScanErrorException("Error: expected a number, white space, or an operand" +
                                            " and found " + currentChar);

        assert(Pattern.matches("[0-9]+", ret));
        return ret;
    }

    /**
     * Scans the input and returns the current identifier lexeme, throwing a
     * ScanErrorException if an invalid character is encountered
     * 
     * @precondition currentChar is a letter
     * @postcondition advanced to the next lexeme
     * @return the current identifier lexeme
     * @throws ScanErrorException e
     */
    private String scanIdentifier() throws ScanErrorException
    {
        String ret = "";
        while (hasNext() && (isDigit(currentChar) || isLetter(currentChar)))
        {
            ret += currentChar;
            eat(currentChar);
        }

        if (hasNext() && !isWhiteSpace(currentChar) && !isOperand(currentChar))
            throw new ScanErrorException("Error: expected a number, letter, white space, or an " +
                                            "operand and found " + currentChar);

        assert(Pattern.matches("[a-zA-Z][a-zA-Z0-9]*", ret));
        return ret;
    }

    /**
     * Scans the input and returns the current operand lexeme, throwing a
     * ScanErrorException if an invalid character is encountered
     * 
     * @precondition currentChar is neither a digit nor a letter
     * @postcondition advanced to the next lexeme
     * @return the current operand lexeme
     * @throws ScanErrorException e
     */
    private String scanOperand() throws ScanErrorException
    {	
        String ret = "" + currentChar;
        if (hasNext())
        {
            eat(currentChar);

            char prevChar = ret.charAt(0);
            if (currentChar == '=' && (prevChar == ':' || prevChar == '>' || prevChar == '<'))
            {
                ret += currentChar;
                eat(currentChar);
            }
            else if (currentChar == '>' && prevChar == '<')
            {
                ret += currentChar;
                eat(currentChar);
            }
        }

        if (!Pattern.matches("[-=+*/%();:<>,]+", ret))
            throw new ScanErrorException("Error: expected an operand and found " + ret);

        if (hasNext() && !isWhiteSpace(currentChar) && !isLetter(currentChar) &&
                !isDigit(currentChar) && !isOperand(currentChar))
            throw new ScanErrorException("Error: expected a number, letter, white space, or an " +
                                            "operand and found " + currentChar);
        
        return ret;
    }

    /**
     * Scans the input and returns the next lexeme, throwing a ScanErrorException
     * if an invalid token is encountered
     * 
     * @postcondition advanced to the next lexeme, eof is set to true once the end
     * of the input stream has been reached
     * @return the next lexeme or "end" if the input stream is at the end of the
     * file
     * @throws ScanErrorException e
     */
    public String nextToken() throws ScanErrorException
    {
        while (hasNext() && isWhiteSpace(currentChar))
            eat(currentChar);

        if (!hasNext() || currentChar == '.')
        {
            eof = true;
            return "end";
        }

        if (isDigit(currentChar))
            return scanNumber();
        else if (isLetter(currentChar))
            return scanIdentifier();

        String ret = scanOperand();
        char prevChar = ret.charAt(0);
        if (prevChar == '/' && currentChar == '/')
        {
            try
            {
                in.readLine();
            }
            catch (IOException e)
            {
                e.printStackTrace();
                System.exit(-1);
            }
            eat(currentChar);
            return nextToken();
        }
        return ret;
    }

}
