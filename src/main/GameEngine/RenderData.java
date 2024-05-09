package GameEngine;

import Geometry.*;

/**
 * Holds the data necessary for a {@link GameObject GameObject} to be rendered.
 *
 * The class can be sorted by layer
 * 
 * @author Diogo Fonseca a79858
 * @version 08/05/2024
 *
 * @see GameObject
 * @see Renderer
 * @see IGeometricShape
 */
public class RenderData<T extends IGeometricShape<?>> implements Comparable<RenderData<T>>
{
	private T shape;
	private boolean isFilled;
	private int layer;
	private Character character;
	private Colour.Foreground colour;

	/**
	 * Initializes a RenderData
	 * @param shape the shape to be rendered
	 * @param isFilled if the shape is to be filled
	 * @param layer the layer to render the shape in
	 * @param character the character to render the shape with
	 * @param colour the colour to render the shape with (can be null)
	 */
	public RenderData(T shape, boolean isFilled, int layer, Character character, Colour.Foreground colour)
	{
		this.shape = shape;
		this.isFilled = isFilled;
		this.layer = layer;
		this.character = character;
		this.colour = colour;
	}

	/**
	 * Initializes a RenderData
	 * Same as {@link RenderData#RenderData(T,boolean,int,Character,Colour.Foreground)} but colour is null
	 * (renders without colour)
	 */
	public RenderData(T shape, boolean isFilled, int layer, Character character)
	{
		this(shape, isFilled, layer, character, null);
	}

	/**
	 * The shape to render
	 * @return the shape to render
	 */
	public T getShape() { return this.shape; }

	/**
	 * If the shape to render should be filled
	 * @return if the shape to render should be filled
	 */
	public boolean isFilled() { return this.isFilled; }

	/**
	 * The layer the shape should be rendered in
	 * @return the layer the shape should be rendered in
	 */
	public int getLayer() { return this.layer; }

	/**
	 * The character the shape should be rendered with
	 * @return the character the shape should be rendered with
	 */
	public Character getCharacter() { return this.character; }

	/**
	 * The colour the shape should be rendered with
	 * @return the colour the shape should be rendered with
	 */
	public Colour.Foreground getColour() { return this.colour; }

	@Override
	public int compareTo(RenderData<T> that)
	{
		return this.layer - that.layer;
	}
}
