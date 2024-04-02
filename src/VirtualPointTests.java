import java.io.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;


public class VirtualPointTests
{
    @Test
    public void ShouldBeEqualsWhenCoordinatesAreSame()
    {
        // Arrange
        VirtualPoint p0 = new VirtualPoint(5, -12);
        VirtualPoint p1 = new VirtualPoint(5, 4);
        VirtualPoint p2 = new VirtualPoint(2, -12);
        VirtualPoint p3 = new VirtualPoint(5, -12);
        VirtualPoint p4 = new VirtualPoint(p0);

        // Act
        boolean isEqualsIfXisEquals = p0.equals(p1);
        boolean isEqualsIfYisEquals = p0.equals(p2);
        boolean isEqualsIfCoordinatesAreEqual = p0.equals(p3);
        boolean isEqualsIfIsCopy = p0.equals(p4);
        boolean isEqualsIfIsSame = p0.equals(p0);

        // Assert
        assertFalse(isEqualsIfXisEquals);
        assertFalse(isEqualsIfYisEquals);
        assertTrue(isEqualsIfCoordinatesAreEqual);
        assertTrue(isEqualsIfIsCopy);
        assertTrue(isEqualsIfIsSame);
    }

    @Test
    public void ShouldMakeDeepCopyWhenCopying()
    {
        // Arrange
        VirtualPoint p0 = new VirtualPoint(-3, 5);
        VirtualPoint p1 = new VirtualPoint(p0);
        VirtualPoint p2 = new VirtualPoint(p0);

        // Act
        boolean areEqual0 = p0.equals(p1);
        boolean areSame0 = p0 == p1;

        boolean areEqual1 = p0.equals(p2);
        boolean areSame1 = p0 == p2;

        // Assert
        assertTrue(areEqual0);
        assertFalse(areSame0);

        assertTrue(areEqual1);
        assertFalse(areSame1);
    }

    @Test
    public void ShouldMakeDeepCopyWhenCopyingArray()
    {
        // Arrange
        VirtualPoint[] vpArray = {
            new VirtualPoint(1, -2),
            new VirtualPoint(2, -3),
            new VirtualPoint(3, -4),
            new VirtualPoint(4, -5),
            new VirtualPoint(5, -6),
        };

        // Act
        VirtualPoint[] vpArrayCopy = VirtualPoint.copyArray(vpArray);
        vpArrayCopy[0] = new VirtualPoint(0, -1);

        // Assert
        assertFalse(vpArray[0].equals(vpArrayCopy[1]));
        for (int i = 1; i < vpArray.length; i++)
            assertTrue(vpArray[i].equals(vpArrayCopy[i]));
    }

    @Test
    public void ShouldReturnCorrectDistance()
    {
        // Arrange
        VirtualPoint p0 = new VirtualPoint(0, 1);
        VirtualPoint p1 = new VirtualPoint(0, 2);
        VirtualPoint p2 = new VirtualPoint(0, -5);
        VirtualPoint p3 = new VirtualPoint(1, 0);
        VirtualPoint p4 = new VirtualPoint(2, 0);
        VirtualPoint p5 = new VirtualPoint(-5, 0);
        VirtualPoint p6 = new VirtualPoint(-2, -3);
        VirtualPoint p7 = new VirtualPoint(7, 6);
        VirtualPoint p8 = new VirtualPoint(0, 0);

        // Act
        double dist0 = p0.dist(p1);
        double expected0 = 1;
        double dist1 = p0.dist(p2);
        double expected1 = 6;
        double dist2 = p3.dist(p4);
        double expected2 = 1;
        double dist3 = p3.dist(p5);
        double expected3 = 6;
        double dist4 = p6.dist(p7);
        double expected4 = 12.7279220614;
        double dist5 = p7.dist(p6);
        double expected5 = 12.7279220614;
        double dist6 = p8.dist(p8);
        double expected6 = 0;

        // Assert
        assertTrue(MathUtil.areEqual(dist0, expected0));
        assertTrue(MathUtil.areEqual(dist1, expected1));
        assertTrue(MathUtil.areEqual(dist2, expected2));
        assertTrue(MathUtil.areEqual(dist3, expected3));
        assertTrue(MathUtil.areEqual(dist4, expected4));
        assertTrue(MathUtil.areEqual(dist5, expected5));
        assertTrue(MathUtil.areEqual(dist6, expected6));
    }
    
    @Test
    public void ShouldGetInputFromInput()
    {
        // Arrange
        String input = "3 -1\n-12.5 -4\n";
        TestUtil.setIOstreams(input);
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        VirtualPoint p0 = null;
        VirtualPoint p1 = null;
        VirtualPoint expected0 = new VirtualPoint(3, -1);
        VirtualPoint expected1 = new VirtualPoint(-12.5, -4);

        // Act
        try
        {
            p0 = VirtualPoint.getPointFromInput(reader);
            p1 = VirtualPoint.getPointFromInput(reader);
        }
        catch (IOException e) { }

        // Assert
        assertFalse(p0 == null);
        assertFalse(p1 == null);
        assertTrue(p0.equals(expected0));
        assertTrue(p1.equals(expected1));
    } 

    @Test
    public void ShouldIncludeIntegersInStringInsteadOfDoubles()
    {
        // Arrange
        VirtualPoint p0 = new VirtualPoint(5, -3);
        String expected0 = "(5,-3)";
        VirtualPoint p1 = new VirtualPoint(-13, 47);
        String expected1 = "(-13,47)";
        VirtualPoint p2 = new VirtualPoint(14.7, 3512);
        String expected2 = "(14,3512)";

        // Act
        String s0 = p0.toString();
        String s1 = p1.toString();
        String s2 = p2.toString();

        // Arrange
        assertTrue(s0.equals(expected0));
        assertTrue(s1.equals(expected1));
        assertTrue(s2.equals(expected2));
    }

    @Test
    public void ShouldTurnArrayOfPointsToString()
    {
        // Arrange
        VirtualPoint[] vps = new VirtualPoint[] {
            new VirtualPoint(3, -5),
            new VirtualPoint(-9, 312),
            new VirtualPoint(512, -1024),
            new VirtualPoint(7, -1.5),
            new VirtualPoint(0, 0),
            new VirtualPoint(0, 12),
        };
        String expected = "[(3,-5), (-9,312), (512,-1024), (7,-1), (0,0), (0,12)]";

        // Act
        String str = VirtualPoint.arrayToString(vps);

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
        VirtualPoint[] points0 = VirtualPoint.parseToArray(str0);
        VirtualPoint[] points1 = VirtualPoint.parseToArray(str1, 4);

        // Assert
        assertTrue(VirtualPoint.arrayToString(points0).equals(expected));
        assertTrue(VirtualPoint.arrayToString(points1).equals(expected));
    }

    @Test
    public void ShouldRotateAroundAnchor()
    {
        // Arrange
        VirtualPoint vp0 = new VirtualPoint(-2, -2);
        VirtualPoint vp1 = new VirtualPoint(3, 5);
        VirtualPoint anchor = new VirtualPoint(0, 0);
        VirtualPoint expected0 = new VirtualPoint(vp0);
        VirtualPoint expected1 = new VirtualPoint(2, -2);
        VirtualPoint expected2 = new VirtualPoint(-2, 2);
        VirtualPoint expected3 = new VirtualPoint(2, 2);
        VirtualPoint expected4 = new VirtualPoint(-2.459697694, -1.158529015);
        VirtualPoint expected5 = new VirtualPoint(vp0);
        VirtualPoint expected6 = new VirtualPoint(1.96972375, 4.57306188);

        // Act
        VirtualPoint rotation0 = vp0.rotate(0, anchor);
        VirtualPoint rotation1 = vp0.rotate(Math.PI / 2f, anchor);
        VirtualPoint rotation2 = vp0.rotate(-Math.PI / 2f, anchor);
        VirtualPoint rotation3 = vp0.rotate(Math.PI, anchor);
        VirtualPoint rotation4 = vp0.rotate(1f, new VirtualPoint(-3, -2));
        VirtualPoint rotation5 = vp0.rotate(Math.PI, vp0);
        VirtualPoint rotation6 = vp1.rotate(3f, new VirtualPoint(2.5, 4.75));

        // Assert
        assertTrue(rotation0.equals(expected0));
        assertTrue(rotation1.equals(expected1));
        assertTrue(rotation2.equals(expected2));
        assertTrue(rotation3.equals(expected3));
        assertTrue(rotation4.equals(expected4));
        assertTrue(rotation5.equals(expected5));
        assertTrue(rotation6.equals(expected6));
    }

    @Test
    public void ShouldBeImmutableWhenRotating()
    {
        // Assert
        VirtualPoint point = new VirtualPoint(2, 3);
        VirtualPoint replica = new VirtualPoint(2, 3);

        // Act
        point.rotate(Math.PI / 2f, new VirtualPoint(0, 0));

        // Arrange
        assertTrue(point.equals(replica));
    }

    @Test
    public void ShouldApplyTranslation()
    {
        // Arrange
        VirtualPoint point = new VirtualPoint(1, 2);
        Vector v0 = new Vector(3, 4);
        Vector v1 = new Vector(-3, -7);
        Vector v2 = new Vector(-1, 5);
        Vector v3 = new Vector(9, -2);
        Vector v4 = new Vector(0, 0);
        VirtualPoint expected0 = new VirtualPoint(4, 6);
        VirtualPoint expected1 = new VirtualPoint(-2, -5);
        VirtualPoint expected2 = new VirtualPoint(0, 7);
        VirtualPoint expected3 = new VirtualPoint(10, 0);
        VirtualPoint expected4 = new VirtualPoint(1, 2);

        // Act
        VirtualPoint p0 = point.translate(v0);
        VirtualPoint p1 = point.translate(v1);
        VirtualPoint p2 = point.translate(v2);
        VirtualPoint p3 = point.translate(v3);
        VirtualPoint p4 = point.translate(v4);

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
        VirtualPoint point = new Point(1, 2);
        VirtualPoint replica = new Point(1, 2);

        // Act
        point.translate(new Vector(4, 6));

        // Assert
        assertTrue(point.equals(replica));
    }
}
