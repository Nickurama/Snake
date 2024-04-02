import java.io.*;

/**
 * Helper class for tests
 * @author Diogo Fonseca a79858
 * @version 16/03/2024
 */
public class TestUtil
{
    /**
     * Feeds a string into stdin and gets the
     * stdout stream for getting the stdout output
     * if needed
     * @param input the string that should be fed into stdin
     * @return the stdout stream
     * @author Made by UAlg (not Diogo Fonseca)
     */
    static public ByteArrayOutputStream setIOstreams(String input)
    {
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(output);
        System.setOut(ps);
        return output;
    }
}
