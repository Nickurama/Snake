import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class UniquePolygonsTest
{
    @Test
    public void ShouldAddDifferentPolygons()
    {
        // Arrange
        UniquePolygons unique = new UniquePolygons();
        Poligono p0 = new Quadrado(new Point[] {
            new Point(1, 1),
            new Point(1, 2),
            new Point(2, 2),
            new Point(2, 1),
        });
        Poligono p1 = new Poligono(new Point[] {
            new Point(1, 1),
            new Point(1, 2),
            new Point(3, 3),
            new Point(2, 1),
        });
        Poligono p2 = new Poligono(new Point[] {
            new Point(1, 1),
            new Point(1, 2),
            new Point(4, 4),
            new Point(2, 1),
        });

        // Act
        unique.add(p0);
        unique.add(p1);
        unique.add(p2);
        String[] strings = unique.toStrings();

        // Assert
        assertTrue(strings[0].equals(p0.toString()));
        assertTrue(strings[1].equals(p1.toString()));
        assertTrue(strings[2].equals(p2.toString()));
    }

    @Test
    public void ShouldGetPolygon()
    {
        // Arrange
        UniquePolygons unique = new UniquePolygons();
        Poligono p0 = new Quadrado(new Point[] {
            new Point(1, 1),
            new Point(1, 2),
            new Point(2, 2),
            new Point(2, 1),
        });
        Poligono p1 = new Poligono(new Point[] {
            new Point(1, 1),
            new Point(1, 2),
            new Point(3, 3),
            new Point(2, 1),
        });
        Poligono p2 = new Poligono(new Point[] {
            new Point(1, 1),
            new Point(1, 2),
            new Point(4, 4),
            new Point(2, 1),
        });

        // Act
        unique.add(p0);
        unique.add(p1);
        unique.add(p2);

        // Assert
        assertTrue(unique.get(0).equals(p0));
        assertTrue(unique.get(1).equals(p1));
        assertTrue(unique.get(2).equals(p2));
    }

    @Test
    public void ShouldContain()
    {
        // Arrange
        UniquePolygons unique = new UniquePolygons();
        Poligono p0 = new Quadrado(new Point[] {
            new Point(1, 1),
            new Point(1, 2),
            new Point(2, 2),
            new Point(2, 1),
        });
        Poligono p1 = new Poligono(new Point[] {
            new Point(1, 1),
            new Point(1, 2),
            new Point(3, 3),
            new Point(2, 1),
        });
        Poligono p2 = new Poligono(new Point[] {
            new Point(1, 1),
            new Point(1, 2),
            new Point(4, 4),
            new Point(2, 1),
        });

        // Act
        unique.add(p0);
        unique.add(p1);
        unique.add(p2);

        // Assert
        assertTrue(unique.contains(p1));
    }

    @Test
    public void ShouldNotContain()
    {
        // Arrange
        UniquePolygons unique = new UniquePolygons();
        Poligono p0 = new Quadrado(new Point[] {
            new Point(1, 1),
            new Point(1, 2),
            new Point(2, 2),
            new Point(2, 1),
        });
        Poligono p1 = new Poligono(new Point[] {
            new Point(1, 1),
            new Point(1, 2),
            new Point(3, 3),
            new Point(2, 1),
        });
        Poligono p2 = new Poligono(new Point[] {
            new Point(1, 1),
            new Point(1, 2),
            new Point(4, 4),
            new Point(2, 1),
        });
        Poligono p3 = new Poligono(new Point[] {
            new Point(1, 1),
            new Point(1, 2),
            new Point(4, 4),
            new Point(3, 1),
        });


        // Act
        unique.add(p0);
        unique.add(p1);
        unique.add(p2);

        // Assert
        assertFalse(unique.contains(p3));
    }

    @Test
    public void ShouldReturnCorrectSize()
    {
        // Arrange
        UniquePolygons unique = new UniquePolygons();
        Poligono p0 = new Quadrado(new Point[] {
            new Point(1, 1),
            new Point(1, 2),
            new Point(2, 2),
            new Point(2, 1),
        });
        Poligono p1 = new Poligono(new Point[] {
            new Point(1, 1),
            new Point(1, 2),
            new Point(3, 3),
            new Point(2, 1),
        });
        Poligono p2 = new Poligono(new Point[] {
            new Point(1, 1),
            new Point(1, 2),
            new Point(4, 4),
            new Point(2, 1),
        });
        Poligono p3 = new Poligono(new Point[] {
            new Point(1, 1),
            new Point(1, 2),
            new Point(4, 4),
            new Point(3, 1),
        });


        // Act
        unique.add(p0);
        unique.add(p1);
        unique.add(p2);
        int size0 = unique.size();
        unique.add(p3);
        int size1 = unique.size();

        // Assert
        assertEquals(size0, 3);
        assertEquals(size1, 4);
    }
}
