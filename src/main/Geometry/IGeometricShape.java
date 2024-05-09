package Geometry;

/**
 * Represents a geometric shape, containing common
 * operations within geometric shapes
 * 
 * @author Diogo Fonseca a79858
 * @version 02/05/2024
 */
public interface IGeometricShape<T>
{
	/**
	 * The perimeter of the shape
	 * @return the perimeter of the shape
	 */
	public double perimeter();

	/**
	 * Checks intersection with another geometric shape
	 * @param that the geometric shape to check intersection with
	 * @return if the two geometric shapes intersect
	 */
	public boolean intersects(IGeometricShape<?> that);

	/**
	 * Checks intersection with another geometric shape (inclusive)
	 * @param that the geometric shape to check intersection with
	 * @return if the two geometric shapes intersect (inclusive)
	 */
	public boolean intersectsInclusive(IGeometricShape<?> that);

	/**
	 * Checks if the geometric shape contains the other geometric shape
	 * @param that the geometric shape to check if is contained within this geometric shape
	 * @return if the other geometric shape is contained within this geometric shape
	 */
	public boolean contains(IGeometricShape<?> that);

	/**
	 * Returns a copy of the shape, rotated by the specified angle (in radians)
	 * around the anchor point
	 * @param angle the angle (in radians) to rotate the shape by
	 * @param anchor the point to rotate the shape around
	 * @return a rotated copy of the shape
	 * @throws GeometricException if the shape is rotated to an invalid position
	 */
	public T rotate(double angle, VirtualPoint anchor) throws GeometricException;

	/**
	 * Returns a copy of the shape, rotated by the specified angle (in radians)
	 * around it's centroid
	 * @param angle the angle (in radians) to rotate the shape by
	 * @return a rotated copy of the shape
	 * @throws GeometricException if the shape is rotated to an invalid position
	 */
	public T rotate(double angle) throws GeometricException;

	/**
	 * Returns a copy of the shape, rotated by the specified angle (in degrees)
	 * around the anchor point
	 * @param angle the angle (int degrees) to rotate the shape by
	 * @param anchor the point to rotate the shape around
	 * @return a rotated copy of the shape
	 * @throws GeometricException if the shape is rotated to an invalid position
	 */
	public T rotateDegrees(double angle, VirtualPoint anchor) throws GeometricException;

	/**
	 * Returns a copy of the shape, rotated by the specified angle (in degrees)
	 * around it's centroid
	 * @param angle the angle (in degrees) to rotate the shape by
	 * @return a rotated copy of the shape
	 * @throws GeometricException if the shape is rotated to an invalid position
	 */
	public T rotateDegrees(double angle) throws GeometricException;

	/**
	 * Returns a copy of the shape, translated by a vector
	 * @param vector the vector to apply to the shape
	 * @return a translated copy of the shape
	 * @throws GeometricException if the shape is translated to an invalid position
	 */
	public T translate(Vector vector) throws GeometricException;

	/**
	 * Returns a copy of the shape, translating it so that
	 * it's centroid is located at the specified point
	 * @param newCentroid the new centroid of the shape
	 * @return a copy of the shape, relocated to then new centroid
	 * @throws GeometricException if the shape is moved to an invalid position
	 */
	public T moveCentroid(Point newCentroid) throws GeometricException;

	/**
	 * The shape's centroid
	 * @return the shape's centroid
	 */
	public Point getCentroid();
}
