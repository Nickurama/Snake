import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;


public class PointTests
{
    @Test
    public void ShouldMakeDeepCopyWhenCopying()
    {
        // Arrange
        Point p0 = new Point(3, 5);
        // Point p1 = (Point) p0.copy();
        Point p2 = new Point(p0);

        // Act
        // boolean areEqual0 = p0.equals(p1);
        // boolean areSame0 = p0 == p1;

        boolean areEqual1 = p0.equals(p2);
        boolean areSame1 = p0 == p2;

        // Assert
        // assertTrue(areEqual0);
        // assertFalse(areSame0);

        assertTrue(areEqual1);
        assertFalse(areSame1);
    }

    @Test
    public void ShouldMakeDeepCopyWhenCopyingArray()
    {
        // Arrange
        Point[] vpArray = {
            new Point(1, 2),
            new Point(2, 3),
            new Point(3, 4),
            new Point(4, 5),
            new Point(5, 6),
        };

        // Act
        Point[] vpArrayCopy = Point.copyArray(vpArray);
        vpArrayCopy[0] = new Point(0, 1);

        // Assert
        assertFalse(vpArray[0].equals(vpArrayCopy[1]));
        for (int i = 1; i < vpArray.length; i++)
            assertTrue(vpArray[i].equals(vpArrayCopy[i]));
    }

    @Test
    public void ShouldTurnArrayOfPointsToString()
    {
        // Arrange
        Point[] vps = new Point[] {
            new Point(3, 5),
            new Point(9, 312),
            new Point(512, 1024),
            new Point(7, 1.5),
            new Point(0, 0),
            new Point(0, 12),
        };
        String expected = "[(3,5), (9,312), (512,1024), (7,1), (0,0), (0,12)]";

        // Act
        String str = Point.arrayToString(vps);

        // Arrange
        assertTrue(str.equals(expected));
    }
    
    @Test
    public void ShouldTurnStringToPoints()
    {
        // Arrange
        String str0 = "4 1 1 1 2 2 2 2 1";
        String str1 = "1 1 1 2 2 2 2 1";
        String expected = "[(1,1), (1,2), (2,2), (2,1)]";

        // Act
        Point[] points0 = Point.parseToArray(str0);
        Point[] points1 = Point.parseToArray(str1, 4);

        // Assert
        assertTrue(Point.arrayToString(points0).equals(expected));
        assertTrue(Point.arrayToString(points1).equals(expected));
    }

    @Test
    public void ShouldApplyTranslation()
    {
        // Arrange
        Point point = new Point(11, 12);
        Vector v0 = new Vector(3, 4);
        Vector v1 = new Vector(-3, -7);
        Vector v2 = new Vector(-1, 5);
        Vector v3 = new Vector(9, -2);
        Vector v4 = new Vector(0, 0);
        Point expected0 = new Point(14, 16);
        Point expected1 = new Point(8, 5);
        Point expected2 = new Point(10, 17);
        Point expected3 = new Point(20, 10);
        Point expected4 = new Point(11, 12);

        // Act
        Point p0 = point.translate(v0);
        Point p1 = point.translate(v1);
        Point p2 = point.translate(v2);
        Point p3 = point.translate(v3);
        Point p4 = point.translate(v4);

        // Assert
        assertTrue(p0.equals(expected0));
        assertTrue(p1.equals(expected1));
        assertTrue(p2.equals(expected2));
        assertTrue(p3.equals(expected3));
        assertTrue(p4.equals(expected4));
    }


    @Test
    public void ShouldBeImmutableOnTranslation()
    {
        // Arrange
        Point point = new Point(1, 2);
        Point replica = new Point(1, 2);

        // Act
        point.translate(new Vector(4, 6));

        // Assert
        assertTrue(point.equals(replica));
    }
}
