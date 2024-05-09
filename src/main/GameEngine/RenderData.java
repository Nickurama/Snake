package GameEngine;

import Geometry.*;

public class RenderData<T extends IGeometricShape<?>> implements Comparable<RenderData<T>>
{
	private T shape;
	private boolean isFilled;
	private int layer;
	private Character character;
	private Colour.Foreground colour;

	public RenderData(T shape, boolean isFilled, int layer, Character character, Colour.Foreground colour)
	{
		this.shape = shape;
		this.isFilled = isFilled;
		this.layer = layer;
		this.character = character;
		this.colour = colour;
	}

	public RenderData(T shape, boolean isFilled, int layer, Character character)
	{
		this(shape, isFilled, layer, character, null);
	}

	@Override
	public int compareTo(RenderData<T> that)
	{
		return this.layer - that.layer;
	}

	public T getShape() { return this.shape; }
	public boolean isFilled() { return this.isFilled; }
	public int getLayer() { return this.layer; }
	public Character getCharacter() { return this.character; }
	public Colour.Foreground getColour() { return this.colour; }
}
