import java.util.ArrayList;

/**
 * A collection of unique polygons
 * where no two polygons are equal
 * 
 * @author Diogo Fonseca a79858
 * @version 16/03/2024
 * 
 * @inv no two polygons are equal
 */
public class UniquePolygons
{
    private final String ERROR_MESSAGE = "Duplicados";
    ArrayList<Poligono> polygons;

    /**
     * Initializes a UniquePolygons colection
     */
    public UniquePolygons()
    {
        polygons = new ArrayList<Poligono>();
    }

    /**
     * Adds a polygon to the collection
     * @param poly the polygon to add
     * 
     * @pre should not be equal to any other polygon in the collection
     */
    public void add(Poligono poly)
    {
        if (contains(poly))
            Error.terminateProgram(ERROR_MESSAGE);
        polygons.add(poly);
    }

    /**
     * Tests if the polygon is contained in the collection
     * @param poly the polygon to test if the collection has
     * @return if the polygon is already in the collection
     */
    public boolean contains(Poligono poly)
    {
        for (Poligono p : polygons)
            if (poly.equals(p))
                return true;
        return false;
    }

    /**
     * Turns every polygon in the collection into a string
     * @return an array containing each polygon's toString
     */
    public String[] toStrings()
    {
        String[] strings = new String[this.polygons.size()];
        for (int i = 0; i < this.polygons.size(); i++)
            strings[i] = polygons.get(i).toString();
        return strings;
    }

    /**
     * Gets a polygon at a given index in the collection
     * @param index the index of the polygon to retrieve
     * @return the polygon at the specified index
     * 
     * @pre the index must be smaller than the size of the collection
     */
    public Poligono get(int index)
    {
        if (index >= this.polygons.size())
            Error.terminateProgram("UniquePolygons::get tried to acess polygon outside of index");
        return this.polygons.get(index);
    }

    /**
     * The current size of the collection
     */
    public int size()
    {
        return this.polygons.size();
    }
}
