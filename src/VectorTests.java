import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class VectorTests 
{
    @Test
    public void ShouldCalculateLength()
    {
        // Arrange
        Vector v0 = new Vector(0, 5);
        Vector v1 = new Vector(5, 0);
        Vector v2 = new Vector(new VirtualPoint(-2, 3));
        
        // Act
        double len0 = v0.length();
        double expected0 = 5;
        double len1 = v1.length();
        double expected1 = 5;
        double len2 = v2.length();
        double expected2 = 3.60555127546;
        
        // Assert
        assertTrue(MathUtil.areEqual(len0, expected0));
        assertTrue(MathUtil.areEqual(len1, expected1));
        assertTrue(MathUtil.areEqual(len2, expected2));
    }

    @Test
    public void ShouldCalculateDotProduct()
    {
        // Arrange
        Vector v0 = new Vector(-3, 6);
        Vector v1 = new Vector(5, 7);
        Vector v2 = new Vector(0, -1);

        // Act
        double dp0 = v0.dotProduct(v1);
        double expected0 = 27;
        double dp1 = v0.dotProduct(v2);
        double expected1 = -6;
        double dp2 = v1.dotProduct(v2);
        double expected2 = -7;

        // Assert
        assertTrue(MathUtil.areEqual(dp0, expected0));
        assertTrue(MathUtil.areEqual(dp1, expected1));
        assertTrue(MathUtil.areEqual(dp2, expected2));
    }

    // @Test
    // public void ShouldCalculateAngle()
    // {
    //     // Arrange
    //     Vector v0 = new Vector(-1, -2);
    //     Vector v1 = new Vector(2, 0);
    //     Vector v2 = new Vector(0, 5);
    //     Vector v3 = new Vector(2, 4);

    //     // Act
    //     double angle0 = v0.angle(v1);
    //     double expected0 = 2.034443936;
    //     double angle1 = v0.angle(v2);
    //     double expected1 = 2.677945045;
    //     double angle2 = v1.angle(v2);
    //     double expected2 = Math.PI / 2f;
    //     double angle3 = v0.angle(v3);
    //     double expected3 = Math.PI;

    //     // Assert
    //     assertTrue(MathUtil.areEqual(angle0, expected0));
    //     assertTrue(MathUtil.areEqual(angle1, expected1));
    //     assertTrue(MathUtil.areEqual(angle2, expected2));
    //     assertTrue(MathUtil.areEqual(angle3, expected3));
    // }

    @Test
    public void ShouldCalculateIfHasRightAngle()
    {
        // Arrange
        Vector v0 = new Vector(1, 2);
        Vector v1 = new Vector(4, -2);
        Vector v2 = new Vector(5, 0);
        Vector v3 = new Vector(0, -7);

        // Act
        boolean right0 = v0.hasRightAngle(v1);
        boolean right1 = v2.hasRightAngle(v3);
        boolean right2 = v0.hasRightAngle(v2);

        // Assert
        assertTrue(right0);
        assertTrue(right1);
        assertFalse(right2);
    }

    @Test
    public void ShouldGenerateVectorFromPoints()
    {
        // Arrange
        VirtualPoint p0 = new VirtualPoint(1, 1);
        VirtualPoint p1 = new VirtualPoint(3, 3);
        VirtualPoint p2 = new VirtualPoint(0.5, 0);
        VirtualPoint p3 = new VirtualPoint(1, 5);
        Vector expected0 = new Vector(2, 2);
        Vector expected1 = new Vector(-0.5, -1);
        Vector expected2 = new Vector(0, 4);
        Vector expected3 = new Vector(0, 0);

        // Act
        Vector v0 = new Vector(p0, p1);
        Vector v1 = new Vector(p0, p2);
        Vector v2 = new Vector(p0, p3);
        Vector v3 = new Vector(p0, p0);

        // Assert
        assertTrue(v0.equals(expected0));
        assertTrue(v1.equals(expected1));
        assertTrue(v2.equals(expected2));
        assertTrue(v3.equals(expected3));
    }

    @Test
    public void ShouldEquals()
    {
        // Arrange
        Vector v0 = new Vector(1, -3);
        Vector v1 = new Vector(1, -3);
        Vector v2 = new Vector(1, 3);
        Vector v3 = new Vector(5, 8);

        // Act
        boolean equals0 = v0.equals(v1);
        boolean equals1 = v0.equals(v2);
        boolean equals2 = v0.equals(v3);
        boolean equals3 = v0.equals(v0);

        // Assert
        assertTrue(equals0);
        assertFalse(equals1);
        assertFalse(equals2);
        assertTrue(equals3);
    }
}
