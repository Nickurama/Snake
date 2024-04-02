/**
 * Hold error handling utility for other classes to use
 * 
 * @author Diogo Fonseca a79858
 * @version 16/03/2024
 */
public class Error
{
    /**
     * Terminates execution of the program after printing an error message
     * @param message the message to print before terminating the program
     */
    public static void terminateProgram(String message)
    {
        System.out.println(message);
        System.exit(0);
    }

    /**
     * Terminates execution of the program after printing an error message (without a new line)
     * @param message the message to print before terminating the program
     */
    public static void terminateProgramNoNewLine(String message)
    {
        System.out.print(message);
        System.exit(0);
    }
}
