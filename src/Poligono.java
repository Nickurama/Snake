/**
 * Represents an immutable simple polygon
 * 
 * @author Diogo Fonseca a79858
 * @version 18/03/2024
 * 
 * @inv sides are the sides of the polygon
 * @inv vertices are the vertices of the polygon
 */
public class Poligono {
  private static final String ERROR_MESSAGE = "Poligono:vi";
  protected LineSegment[] sides;
  protected Point[] vertices;

  /**
   * Initializes a polygon
   * 
   * @param vertices the vertices of the polygon
   * @pre points must contain at least 3 points
   * @pre points must generate valid sides (that don't collide)
   */
  public Poligono(Point[] points) {
    if (points.length < 3)
      Error.terminateProgram(ERROR_MESSAGE);

    Point[] verticesGen = Point.copyArray(points);
    LineSegment[] segments = generateSegments(verticesGen);

    if (isAnyGeneratedPointCollinear(verticesGen, segments) || doSidesIntersect(segments))
      Error.terminateProgram(ERROR_MESSAGE);

    this.vertices = verticesGen;
    this.sides = segments;
  }

  /**
   * Initializes a polygon
   * Parses a string into a polygon
   * 
   * @param str the string to parse
   */
  public Poligono(String str) {
    this(Point.parseToArray(str));
  }

  public Poligono(Poligono poly) {
    this(poly.vertices);
  }

  /**
   * generates the segments from the given points, representing the sides of the
   * polygon
   * 
   * @param points the points that contain the vertices of the polygon
   * @return segments generated from the order of the given points
   */
  private LineSegment[] generateSegments(Point[] points) {
    LineSegment[] segments = new LineSegment[points.length];
    for (int i = 1; i < points.length; i++)
      segments[i - 1] = new LineSegment(points[i - 1], points[i]);
    segments[points.length - 1] = new LineSegment(points[points.length - 1], points[0]);
    return segments;
  }

  /**
   * Checks if there are any collinear points in the generated segments
   * 
   * @param points   the points that contain the vertices of the polygon
   * @param segments the segments generated from the vertices
   * @return if there are any collinear (invalid) points
   */
  private static boolean isAnyGeneratedPointCollinear(Point[] points, LineSegment[] segments) {
    for (int i = 0; i < points.length; i++)
      if (segments[i].line().isCollinear(points[(i + 2) % points.length]))
        return true;
    return false;
  }

  /**
   * Checks if any segments are colliding (intersecting eachother)
   * 
   * @param segments the segments to check the intersections of
   * @return if there is any collision within the segments
   */
  private static boolean doSidesIntersect(LineSegment[] segments) {
    for (int i = 2; i < segments.length - 1; i++)
      for (int j = 0; j < (i - 1); j++)
        if (segments[i].intersects(segments[j]))
          return true;
    for (int j = 1; j < segments.length - 2; j++)
      if (segments[segments.length - 1].intersects(segments[j]))
        return true;

    return false;
  }

  /**
   * Checks if the polygon is intercected by a segment
   * 
   * @param that the segment to check intercection with
   * @return if the polygon is intercected by the segment
   */
  public boolean intersects(LineSegment that) {
    for (LineSegment side : sides)
      if (side.intersects(that))
        return true;
    return false;
  }

  /**
   * Returns true if a polygon intercepts another one
   * 
   * @param that the polygon to test collision with
   * @return if the polygon intercepts the other
   */
  public boolean intersects(Poligono that) {
    for (LineSegment segment : this.sides)
      if (that.intersects(segment))
        return true;
    return false;
  }

  /**
   * Calculates the perimeter of the polygon
   * 
   * @return the perimeter of the polygon
   */
  public double perimeter() {
    double result = 0;
    for (LineSegment side : sides)
      result += side.length();
    return result;
  }

  @Override
  public String toString() {
    return "Poligono de " + this.vertices.length + " vertices: " + VirtualPoint.arrayToString(this.vertices);
  }

  @Override
  public int hashCode() {
    throw new UnsupportedOperationException();
  }

  @Override
  public final boolean equals(Object other) {
    if (other == this)
      return true;
    if (other == null)
      return false;
    if (!Poligono.class.isInstance(other))
      return false; // if not Polygon or child of Polygon
    Poligono that = (Poligono) other;
    return this.isPolygonEqual(that);
  }

  /**
   * Tests if two polygons are equal or equivalent to eachother
   * 
   * @param that the polygon to test with
   * @return if the two polygons are equal
   */
  private boolean isPolygonEqual(Poligono that) {
    // linear Algorithm that checks if two polygons are equal
    if (this.vertices.length != that.vertices.length)
      return false;
    int startInd = that.findVertice(this.vertices[0]);
    if (startInd < 0)
      return false;

    int direction = 1;
    Point next = that.vertices[Math.floorMod((startInd + 1), that.vertices.length)];
    Point previous = that.vertices[Math.floorMod((startInd - 1), that.vertices.length)];

    if (this.vertices[1].equals(next))
      direction = 1;
    else if (this.vertices[1].equals(previous))
      direction = -1;
    else
      return false;

    for (int i = 2; i < this.vertices.length; i++)
      if (!this.vertices[i].equals(that.vertices[Math.floorMod((startInd + (i * direction)), that.vertices.length)]))
        return false;
    return true;
  }

  /**
   * Finds a polygon's vertice and returns it's index on
   * polygon's array of vertices
   * returns -1 if the vertice wasn't found
   * 
   * @param vertice the vertice to find within the polygon
   * @return the index of the vertice or -1 if it wasn't found
   */
  private int findVertice(Point vertice) {
    for (int i = 0; i < this.sides.length; i++)
      if (vertice.equals(this.vertices[i]))
        return i;
    return -1;
  }

  /**
   * Rotates a polygon around a fixed point (anchor)
   * 
   * @param angle  the angle (in radians) to rotate the polygon by
   * @param anchor the fixed point to rotate the polygon around
   * @return a polygon with the rotation applied to it
   */
  public Poligono rotate(double angle, VirtualPoint anchor) {
    Point[] newVertices = new Point[this.vertices.length];
    for (int i = 0; i < this.vertices.length; i++)
      newVertices[i] = new Point(this.vertices[i].rotate(angle, anchor));
    return new Poligono(newVertices);
  }

  /**
   * Rotates a polygon around a fixed point(anchor)
   * 
   * @param angle  the angle in degrees to rotate the polygon by
   * @param anchor the fixed point to rotate the polygon around
   * @return a polygon with the rotation applied to it
   */
  public Poligono rotateDegrees(double angle, VirtualPoint anchor) {
    return this.rotate(Math.toRadians(angle), anchor);
  }

  /**
   * Rotates a polygon around it's centroid
   * 
   * @param angle the angle (in radians) to rotate the polygon by
   * @return a polygon with the rotation applied to it
   */
  public Poligono rotate(double angle) {
    return this.rotate(angle, getCentroid());
  }

  /**
   * Rotates a polygon around it's centroid
   * 
   * @param angle the angle in degrees to rotate the polygon by
   * @return a polygon with the rotation applied to it
   */
  public Poligono rotateDegrees(double angle) {
    return this.rotate(Math.toRadians(angle));
  }

  /**
   * Calculates the centroid of the polygon
   * 
   * @return the centroid of the polygon
   */
  private Point getCentroid() {
    double x = 0;
    double y = 0;

    for (Point vertice : this.vertices) {
      x += vertice.X();
      y += vertice.Y();
    }

    x /= this.vertices.length;
    y /= this.vertices.length;

    return new Point(x, y);
  }

  /**
   * Acessor method to get the number of sides the polygon has
   * 
   * @return the number of sides the polygon has
   */
  public int getNumSides() {
    return this.vertices.length;
  }

  /**
   * Translates a polygon by a vector
   * 
   * @param vector the vector to translate the polygon
   * @return the translated polygon
   */
  public Poligono translate(Vector vector) {
    Point[] newPoints = new Point[this.vertices.length];
    for (int i = 0; i < this.vertices.length; i++)
      newPoints[i] = this.vertices[i].translate(vector);
    return new Poligono(newPoints);
  }

  /**
   * Translates a polygon by moving it's centroid
   * 
   * @param newCentroid the location where the centroid should be moved to
   * @return the translated polygon
   */
  public Poligono moveCentroid(Point newCentroid) {
    return this.translate(new Vector(this.getCentroid(), newCentroid));
  }
}
