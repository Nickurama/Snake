package GameEngine;

import java.awt.Color;

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
	private TerminalColour.Foreground terminalColour;
	private Color graphicalColor;

	// public RenderData(T shape, boolean isFilled, int layer, Color color)
	// {
	// 	this.shape = shape;
	// 	this.isFilled = isFilled;
	// 	this.layer = layer;
	// 	this.character = null;
	// 	this.terminalColour = null;
	// 	this.color = color;
	// }

	/**
	 * Initializes a RenderData
	 * @param shape the shape to be rendered
	 * @param isFilled if the shape is to be filled
	 * @param layer the layer to render the shape in
	 * @param character the character to render the shape with
	 * @param graphicalColor the colour to render the shape with (can be null)
	 */
	public RenderData(T shape, boolean isFilled, int layer, Character character, TerminalColour.Foreground terminalColour, Color graphicalColor)
	{
		this.shape = shape;
		this.isFilled = isFilled;
		this.layer = layer;
		this.character = character;
		this.terminalColour = terminalColour;
		this.graphicalColor = graphicalColor;
	}

	/**
	 * Initializes a RenderData
	 * Same as {@link RenderData#RenderData(T,boolean,int,Character,TerminalColour.Foreground)} but colour is null
	 * (renders without colour)
	 */
	public RenderData(T shape, boolean isFilled, int layer, Character character)
	{
		this(shape, isFilled, layer, character, null, null);
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
	 * The colour the shape should be rendered with textually
	 * @return the colour the shape should be rendered with
	 */
	public TerminalColour.Foreground getTerminalColour() { return this.terminalColour; }

	/**
	 * The color the shape should be rendered with graphically
	 * @return the color the shape should be rendered with
	 */
	public Color getGraphicalColor() { return this.graphicalColor; }

	@Override
	public int compareTo(RenderData<T> that)
	{
		return this.layer - that.layer;
	}
}
