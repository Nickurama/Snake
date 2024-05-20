package GameEngine;

import javax.swing.JPanel;

/**
 * Represents a {@link GameObject GameObject} that should be rendered as an overlay
 * 
 * @author Diogo Fonseca a79858
 * @version 29/04/2024
 *
 * @see GameObject
 * @see Renderer
 */
public interface IOverlay
{
	/**
	 * The array to be overlayed over the rendered {@link Scene scene}
	 * @return the array to be overlayed over the rendered {@link Scene scene}
	 */
	public char[][] getOverlay();

	/**
	 * Returns the panel overlay
	 * @return the panel overlay
	 */
	public JPanel getPanel();
}
