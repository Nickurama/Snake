package GameEngine;

import Geometry.*;

public class RenderData implements Comparable<RenderData>
{
	private Polygon shape;
	private boolean isFilled;
	private int layer;
	private Character character;

	public RenderData(Polygon shape, boolean isFilled, int layer, Character character)
	{
		this.shape = shape;
		this.isFilled = isFilled;
		this.layer = layer;
		this.character = character;
	}

	@Override
	public int compareTo(RenderData that)
	{
		return this.layer - that.layer;
	}

	public Polygon getShape() { return this.shape; }
	public boolean isFilled() { return this.isFilled; }
	public int getLayer() { return this.layer; }
	public Character getCharacter() { return this.character; }
}
