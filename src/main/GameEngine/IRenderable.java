package GameEngine;

/**
 * Represents a {@link GameObject GameObject} that should be rendered
 * 
 * @author Diogo Fonseca a79858
 * @version 29/04/2024
 *
 * @see GameObject
 * @see Renderer
 * @see RenderData
 */
public interface IRenderable
{
	/**
	 * The render data of the object
	 * @return the render data of the object
	 */
	public RenderData<?> getRenderData();
}
