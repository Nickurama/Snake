package GameEngine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import Geometry.*;

public class Renderer
{
	private static class RasterPoint
	{
		private LineSegment segment;
		private double x;

		public RasterPoint(double x, LineSegment segment)
		{
			this.x = x;
			this.segment = segment;
		}

		public double x() { return this.x; }
		public LineSegment segment() { return this.segment; }
	}

	private static Renderer instance = null;
	private BoundingBox camera;
	private Character backgroundChar;
	private Character drawChar;
	private Character[][] raster;

	private Renderer()
	{
		// Singleton
	}

	public static Renderer getInstance()
	{
		if (instance == null)
			instance = new Renderer();

		return instance;
	}

	public void init(Rectangle camera, Character backgroundChar)
	{
		this.camera = new BoundingBox(camera);
		this.backgroundChar = backgroundChar;
		generateRaster(this.camera);
	}

	private void generateRaster(BoundingBox cam)
	{
		int dy = (int)Math.floor(cam.maxPoint().Y()) - (int)Math.ceil(cam.minPoint().Y()) + 1;
		int dx = (int)Math.floor(cam.maxPoint().X()) - (int)Math.ceil(cam.minPoint().X()) + 1;

		raster = new Character[dy][dx];
		for (int i = 0; i < dy; i++) // iterate over y
		{
			raster[i] = new Character[dx];
			for (int j = 0; j < dx; j++) // iterate over x
			{
				raster[i][j] = backgroundChar;
			}
		}
	}

	private void draw(int x, int y)
	{
		if (x < camera.minPoint().X() || x > camera.maxPoint().X())
			return;
		if (y < camera.minPoint().Y() || y > camera.maxPoint().Y())
			return;
	}

	public static NaturalPoint[] rasterize(Polygon poly) throws GeometricException
	{
		ArrayList<NaturalPoint> rasterPoints = new ArrayList<NaturalPoint>();

		// find minY and maxY
		Point[] vertices = poly.vertices();
		int minY = (int) vertices[0].Y();
		int maxY = minY;
		for (Point vertice : poly.vertices())
		{
			minY = Math.min((int) vertice.Y(), minY);
			maxY = Math.max((int) Math.ceil(vertice.Y()), maxY);
		}

		// scan every Y
		for (int currY = minY; currY <= maxY; currY++)
		{
			// generate current scanline
			Line scanLine = new Line(new Point(0.0, currY), new Point(1.0, currY));


			// calculate all intersections
			ArrayList<RasterPoint> intersections = new ArrayList<RasterPoint>();
			for (LineSegment segment : poly.sides())
			{
				if (segment.line().isParalel(scanLine) && MathUtil.areEqual(segment.firstPoint().Y(), currY))
				{
					double firstX = segment.firstPoint().X(); // TODO can be joined with below statements
					double secondX = segment.secondPoint().X();

					intersections.add(new RasterPoint(firstX, segment));
					intersections.add(new RasterPoint(secondX, segment));
				}
				else if (segment.intersectsInclusive(scanLine))
				{
					double x = segment.line().intersection(scanLine).X();
					intersections.add(new RasterPoint(x, segment));
				}
			}

			// sort intersections
			Collections.sort(intersections, (rp0, rp1) -> MathUtil.areEqual(rp0.x(), rp1.x()) ? 0 : (rp0.x() < rp1.x() ? -1 : 1));

			// add all raster points to list
			RasterPoint last = null;
			boolean isPrinting = false;
			boolean isHorizontal = false;
			boolean lastHorizontalMin = false;
			for (RasterPoint curr : intersections)
			{
				if (last == null)
				{
					last = curr;
					isPrinting = true;
					continue;
				}

				if (MathUtil.areEqual(last.x(), curr.x()))
				{

					VirtualPoint intersection = new VirtualPoint(curr.x(), currY);
					VirtualPoint minPointLast = last.segment().firstPoint().Y() < last.segment().secondPoint().Y() ? last.segment().firstPoint() : last.segment().secondPoint();
					VirtualPoint minPointCurr = curr.segment().firstPoint().Y() < curr.segment().secondPoint().Y() ? curr.segment().firstPoint() : curr.segment().secondPoint();

					boolean isMinPointOfLastSegment = intersection.equals(minPointLast);
					boolean isMinPointOfCurrSegment = intersection.equals(minPointCurr);
					if (scanLine.isParalel(last.segment().line()) || scanLine.isParalel(curr.segment().line()))
					{
						if (!isHorizontal)
						{
							isHorizontal = true;
							lastHorizontalMin = isMinPointOfLastSegment && isMinPointOfCurrSegment;
							continue;
						}
						else
						{
							isHorizontal = false;
							boolean currHorizontalMin = isMinPointOfLastSegment && isMinPointOfCurrSegment;
							boolean changedDirection = currHorizontalMin == lastHorizontalMin;

							if (isPrinting)
							{
								if (changedDirection)
									isPrinting = false;
								// else
								// 	isPrinting = true;
							}
							else
							{
								if (changedDirection)
									isPrinting = true;
								// else
								// 	isPrinting = false;
							}
						}
					}

					if ((!isMinPointOfLastSegment && isMinPointOfCurrSegment) || (isMinPointOfLastSegment && !isMinPointOfCurrSegment))
					{
						// if reached this part then it's a connection point between segments but not a valley or peak
						// should not draw yet as it only has the first point
						continue;
					}
					// if reached this part then it's a peak or a valley, so it should continue and draw that one point
				}
				
				if (isPrinting)
				{
					// draw from last to curr
					for (int x = (int) Math.ceil(last.x()); x <= Math.floor(curr.x()); x++)
						rasterPoints.add(new NaturalPoint(x, currY));
					isPrinting = false;
				}
				else
				{
					isPrinting = true;
				}
				last = curr;
			}
		}

		// Add sides
		rasterPoints.addAll(Arrays.asList(rasterizeSides(poly)));
		
		// Convert arrayList to array
		NaturalPoint[] result = new NaturalPoint[rasterPoints.size()];
		rasterPoints.toArray(result);
		return result;
	}

	public static NaturalPoint[] rasterizeSides(Polygon poly) throws GeometricException
	{
		ArrayList<NaturalPoint> points = new ArrayList<NaturalPoint>();

		for (LineSegment side : poly.sides())
			points.addAll(Arrays.asList(rasterize(side)));

		NaturalPoint[] result = new NaturalPoint[points.size()];
		points.toArray(result);

		return result;
	}

	public static NaturalPoint[] rasterize(LineSegment segment) throws GeometricException
	{
		int x = (int)Math.round(segment.firstPoint().X());
		int y = (int)Math.round(segment.firstPoint().Y());
		int x1 = (int)Math.round(segment.secondPoint().X());
		int y1 = (int)Math.round(segment.secondPoint().Y());

		int dx = Math.abs(x1 - x);
		int dy = Math.abs(y1 - y);
		int len = Math.max(dx, dy) + 1;

		boolean isRight = x1 > x;
		boolean isUp = y1 >= y;
		boolean isCloserToVerticalCenter = dy >= dx;

		if (isRight && isUp && !isCloserToVerticalCenter) // first octave
			return rasterizeLine(x, y, dx, dy, len, false, false);
		else if (isRight && isUp && isCloserToVerticalCenter) // second octave
			return rasterizeLine(y, x, dy, dx, len, true, false);
		else if (!isRight && isUp && isCloserToVerticalCenter) // third octave
			return rasterizeLine(y, x, dy, dx, len, true, true);
		else if (!isRight && isUp && !isCloserToVerticalCenter) // fourth octave
			return rasterizeLine(x1, y1, dx, dy, len, false, true);
		else if (!isRight && !isUp && !isCloserToVerticalCenter) // fifth octave
			return rasterizeLine(x1, y1, dx, dy, len, false, false);
		else if (!isRight && !isUp && isCloserToVerticalCenter) // sixth octave
			return rasterizeLine(y1, x1, dy, dx, len, true, false);
		else if (isRight && !isUp && isCloserToVerticalCenter) // seventh octave
			return rasterizeLine(y1, x1, dy, dx, len, true, true);
		else // eith octave
			return rasterizeLine(x, y, dx, dy, len, false, true);
	}

	// private static NaturalPoint[] rasterizeLine(int x, int y, int dx, int dy, int len, boolean swapAxis, boolean decrementY) throws GeometricException
	// {
	// 	NaturalPoint[] result = new NaturalPoint[len];
	//
	// 	int P = 2 * dy - dx;
	//
	// 	for (int i = 0; i < len; i++)
	// 	{
	// 		result[i] = swapAxis ? new NaturalPoint(y, x) : new NaturalPoint(x, y);
	//
	// 		x++;
	// 		if (P < 0)
	// 			P = P + 2 * dy;
	// 		else
	// 		{
	// 			P = P + 2 * dy - 2 * dx;
	// 			y += decrementY ? -1 : 1;
	// 		}
	// 	}
	//
	// 	return result;
	// }

	private void rasterizeLine(int x, int y, int dx, int dy, int len, boolean swapAxis, boolean decrementY) throws GeometricException
	{
		int P = 2 * dy - dx;

		for (int i = 0; i < len; i++)
		{
			draw(x, y);

			x++;
			if (P < 0)
				P = P + 2 * dy;
			else
			{
				P = P + 2 * dy - 2 * dx;
				y += decrementY ? -1 : 1;
			}
		}
	}
}
