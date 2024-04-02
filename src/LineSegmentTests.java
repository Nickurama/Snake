import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;


public class LineSegmentTests
{
    @Test
    public void ShouldIntercept()
    {
        // Arrange
        LineSegment s0 = new LineSegment(new Point(1, 1), new Point(3, 2));
        LineSegment s1 = new LineSegment(new Point(1, 3), new Point(2, 0));
        
        // Act
        boolean intercepts = s0.intersects(s1);

        // Assert
        assertTrue(intercepts);
    }

    @Test
    public void ShouldNotInterceptEvenIfInherentLinesIntercept()
    {
        // Arrange
        LineSegment s0 = new LineSegment(new Point(1, 1), new Point(3, 4));
        LineSegment s1 = new LineSegment(new Point(4, 1), new Point(3, 2));
        
        // Act
        boolean intercepts = s0.intersects(s1);

        // Assert
        assertFalse(intercepts);
    }

    @Test
    public void ShouldNotInterceptIfSegmentsOverlap()
    {
        // Arrange
        LineSegment s0 = new LineSegment(new Point(1, 1), new Point(3, 1));
        LineSegment s1 = new LineSegment(new Point(1, 1), new Point(4, 1));
        
        // Act
        boolean intercepts = s0.intersects(s1);

        // Assert
        assertFalse(intercepts);
    }

    @Test
    public void ShouldNotInterceptIfOnlyTheEndsOverlap()
    {
        // Arrange
        LineSegment s0 = new LineSegment(new Point(1, 1), new Point(3, 3));
        LineSegment s1 = new LineSegment(new Point(3, 1), new Point(3, 3));
        LineSegment s2 = new LineSegment(new Point(1, 1), new Point(1, 3));
        
        // Act
        boolean intercepts0 = s0.intersects(s1);
        boolean intercepts1 = s0.intersects(s2);

        // Assert
        assertFalse(intercepts0);
        assertFalse(intercepts1);
    }

    @Test
    public void ShouldNotInterceptIfOneEndTouchesTheSegment()
    {
        // Arrange
        LineSegment s0 = new LineSegment(new Point(1, 1), new Point(1, 3));
        LineSegment s1 = new LineSegment(new Point(1, 2), new Point(3, 2));
        
        // Act
        boolean intercepts = s0.intersects(s1);

        // Assert
        assertFalse(intercepts);
    }

    @Test
    public void ShouldCalculateLength()
    {
        // Arrange
        LineSegment s0 = new LineSegment(new Point(0, 0), new Point(0, 5));
        double expected0 = 5;
        LineSegment s1 = new LineSegment(new Point(0, 0), new Point(5, 0));
        double expected1 = 5;
        LineSegment s2 = new LineSegment(new Point(1, 1), new Point(3, 5));
        double expected2 = 4.472135955;

        // Act
        double len0 = s0.length();
        double len1 = s1.length();
        double len2 = s2.length();

        // Assert
        assertTrue(MathUtil.areEqual(len0, expected0));
        assertTrue(MathUtil.areEqual(len1, expected1));
        assertTrue(MathUtil.areEqual(len2, expected2));
    }

    @Test
    public void ShouldBeImmutable()
    {
        // Arrange
        Point p = new Point(0, 0);
        LineSegment s = new LineSegment(p, new Point(5, 5));

        // Act
        p = new Point(1, 1);

        // 
        assertFalse(p.equals(s.getFirstPoint()));
    }
}
