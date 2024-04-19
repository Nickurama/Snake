package Geometry;

public class NaturalPoint extends Point
{
	final private int intX, intY;

	public NaturalPoint(int x, int y) throws GeometricException
	{
		super(x, y);
		this.intX = x;
		this.intY = y;
	}

	public NaturalPoint(Point p) throws GeometricException
	{
		super(p);
		this.intX = (int) p.X();
		this.intY = (int) p.Y();
		validatePoint();
	}

	private void validatePoint() throws GeometricException
	{
		if (!MathUtil.areEqual(this.intX, super.x) || !MathUtil.areEqual(this.intY, super.y))
			throw new GeometricException("Natural Point cannot have double values.");
	}

	public NaturalPoint(VirtualPoint p) throws GeometricException
	{
		super(p);
		this.intX = (int) p.X();
		this.intY = (int) p.Y();
		validatePoint();
	}

	public int intX() { return this.intX; }
	public int intY() { return this.intY; }
}
