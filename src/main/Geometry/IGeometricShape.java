package Geometry;

public interface IGeometricShape<T>
{
	public double perimeter();
	public boolean intersects(IGeometricShape<?> that);
	public boolean contains(IGeometricShape<?> that);
	public T rotate(double angle, VirtualPoint anchor) throws GeometricException;
	public T rotate(double angle) throws GeometricException;
	public T rotateDegrees(double angle, VirtualPoint anchor) throws GeometricException;
	public T rotateDegrees(double angle) throws GeometricException;
	public T translate(Vector vector) throws GeometricException;
	public T moveCentroid(Point newCentroid) throws GeometricException;
	public Point getCentroid();
}
