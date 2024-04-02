import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class BoundingBoxTests
{
    @Test
    public void ShouldNotInterceptWhenBoxesDontIntercept()
    {
        // Arrange
        BoundingBox bb0 = new BoundingBox(new Point[] {
            new Point(1, 1),
            new Point(2, 1),
            new Point(1, 2),
        });
        BoundingBox bb1 = new BoundingBox(new Point[] {
            new Point(4, 3),
            new Point(3, 4),
            new Point(3, 3),
        });

        // Act
        boolean intercepts = bb0.intercepts(bb1);

        // Assert
        assertFalse(intercepts);
    }

    @Test
    public void ShouldInterceptWhenSegmentsDontInterceptButAreClose()
    {
        // Arrange
        BoundingBox bb0 = new BoundingBox(new Point[] {
            new Point(1, 6),
            new Point(2, 9),
            new Point(3, 6),
            new Point(2, 8),
        });
        BoundingBox bb1 = new BoundingBox(new Point[] {
            new Point(1, 4),
            new Point(2, 7),
            new Point(3, 4),
            new Point(2, 6),
        });

        // Act
        boolean intercepts = bb0.intercepts(bb1);

        // Assert
        assertTrue(intercepts);
    }

    @Test
    public void ShouldNotInterceptWhenSideOverlaps()
    {
        // Arrange
        BoundingBox bb0 = new BoundingBox(new Point[] {
            new Point(1, 1),
            new Point(2, 1),
            new Point(2, 2),
            new Point(1, 2),
        });
        BoundingBox bb1 = new BoundingBox(new Point[] {
            new Point(1, 2),
            new Point(2, 2),
            new Point(2, 3),
            new Point(1, 3),
        });

        // Act
        boolean intercepts = bb0.intercepts(bb1);

        // Assert
        assertFalse(intercepts);
    }
}
