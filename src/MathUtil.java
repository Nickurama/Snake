/**
 * Holds math related tools for the other classes to use
 * 
 * @author Diogo Fonseca a79858
 * @version 21/02/2024
 */
public class MathUtil
{
    public static final double CALC_ERROR_MARGIN = 0.000000001;

    /**
     * Checks if two doubles are equal within a constant margin of error
     * @param a the first value to compare
     * @param b the second value to compare
     * @return if the two doubles are equal given the error margin
     */
    public static boolean areEqual(double a, double b)
    {
        return Math.abs(b - a) < CALC_ERROR_MARGIN;
    }

    /**
     * Checks if a double is greater or equal than another (given the margin of error)
     * @param a the value left of >=
     * @param b the value right of >=
     * @return if a is greater or equal than b
     */
    public static boolean isGreaterOrEqualThan(double a, double b)
    {
        return a > (b - CALC_ERROR_MARGIN);
    }

    /**
     * Checks if a double is less or equal than another (given the margin of error)
     * @param a the value left of <=
     * @param b the value right of <=
     * @return if a is less or equal than b
     */
    public static boolean isLessOrEqualThan(double a, double b)
    {
        return a < (b + CALC_ERROR_MARGIN);
    }
}
