package GameEngine;

import Geometry.*;

public class RenderData implements Comparable<RenderData>
{
	private Polygon shape;
	private boolean isRasterized;
	private int layer;
	private Character character;

	public RenderData(Polygon shape, boolean isRasterized, int layer, Character character)
	{
		this.shape = shape;
		this.isRasterized = isRasterized;
		this.layer = layer;
		this.character = character;
	}

	@Override
	public int compareTo(RenderData that)
	{
		return this.layer - that.layer;
	}

	public Polygon getShape() { return this.shape; }
	public boolean isRasterized() { return this.isRasterized; }
	public int getLayer() { return this.layer; }
	public Character getCharacter() { return this.character; }
}
