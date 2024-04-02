/**
 * Represents an immutable line described in euclidean space.
 * Uses the general equation for a line in 2D space: ax + by + c = 0
 * 
 * @author Diogo Fonseca a79858
 * @version 13/03/2024
 * 
 * @inv coefficientX is immutable and set when line is created (represents the "a" coefficient)
 * @inv coefficientY is immutable and set when line is created (represents the "b" coefficient)
 * @inv constant is immutable and set when line is created (represents the "c" constant)
 * @inv containedPoint is a point contained in the line
 */
public class Line
{
    private final String ERROR_MESSAGE = "Reta:vi";
    private double coefficientX;
    private double coefficientY;
    private double constant;
    private Point containedPoint;

    /**
     * Initializes a line 
     * @param a first point contained in the line
     * @param b second point contained in the line
     * 
     * @pre a != b
     */
    public Line(Point a, Point b)
    {
        if (a.equals(b))
            Error.terminateProgram(ERROR_MESSAGE);
        
        this.coefficientX = a.Y() - b.Y();
        this.coefficientY = b.X() - a.X();
        this.constant = a.X() * b.Y() - b.X() * a.Y();
        this.containedPoint = new Point(a);
    }

    /**
     * Checks if a point is contained within a line
     * @param point the point to be checked if the line contains
     * @return if the point is contained within the line
     */
    public boolean isCollinear(Point point)
    {
        return MathUtil.areEqual(calcExpr(point), 0);
    }

    /**
     * Plugs a point into the line equation
     * @param point the point to plug into the line equation
     * @return the result of the equation
     */
    private double calcExpr(Point point)
    {
        return point.X() * this.coefficientX + point.Y() * this.coefficientY + constant;
    }

    /**
     * Calculates the point of intersection between the two lines
     * @param that the other line to check the intersection with
     * @pre the lines must not be paralel
     * @return the point that intersects the two lines
     */
    public VirtualPoint calcIntersect(Line that)
    {
        if (isParalel(that))
            Error.terminateProgram("Line.java tried to calculate the intersection of parallel lines (division by 0)");
        
        double x = (this.coefficientY * that.constant - that.coefficientY * this.constant) /
                    (this.coefficientX * that.coefficientY - that.coefficientX * this.coefficientY);
        double y = (that.coefficientX * this.constant - this.coefficientX * that.constant) /
                    (this.coefficientX * that.coefficientY - that.coefficientX * this.coefficientY);
        return new VirtualPoint(x, y);
    }

    /**
     * Checks if the lines are paralel
     * @param that the other line to check if is paralel with the current one
     * @return if the lines are paralel
     */
    public boolean isParalel(Line that)
    {
        return MathUtil.areEqual(this.coefficientX * that.coefficientY - that.coefficientX * this.coefficientY, 0);
    }

    /**
     * Checks if the lines are perpendicular
     * @param that the other line to check if is perpendicular with the current one
     * @return if the lines are perpendicular
     */
    public boolean isPerpendicular(Line that)
    {
        return MathUtil.areEqual(this.coefficientX * that.coefficientX + this.coefficientY * that.coefficientY, 0);
    }

    /**
     * Checks if two lines are equivalent (if they describe the same line in space)
     * @param that the other line to compare with the current one
     * @return if the two lines are equivalent
     */
    @Override
    public boolean equals(Object other)
    {
        if (other == this) return true;
        if (other == null) return false;
        if (getClass() != other.getClass()) return false;
        Line that = (Line) other;
        return this.isParalel(that) && this.isCollinear(that.containedPoint);
    }

    @Override
    public int hashCode()
    {
        throw new UnsupportedOperationException();
    }
}
