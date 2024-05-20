package GameEngine;

import Geometry.*;

import java.awt.Dimension;
import javax.swing.JPanel;

/**
 * A helper class for generating graphic overlays
 * 
 * @author Diogo Fonseca a79858
 * @version 18/05/2024
 *
 * @see IOverlay
 * @see ISnakeStats
 */
public class GraphicOverlay extends JPanel
{
	private int width;
	private int height;

	/**
	 * Initializes a GraphicOverlay
	 * @param camera the camera to draw the overlay in
	 */
	public GraphicOverlay(Rectangle camera)
	{
		super();

		BoundingBox bounds = new BoundingBox(camera);
		this.width = (int)Math.floor(bounds.maxPoint().X()) - (int)Math.ceil(bounds.minPoint().X()) + 1;
		this.height = (int)Math.floor(bounds.maxPoint().Y()) - (int)Math.ceil(bounds.minPoint().Y()) + 1;

		setBounds(0, 0, width, height);
		setPreferredSize(new Dimension(width, height));
		setOpaque(false);
		setLayout(null);
	}

	/**
	 * The width of the panel
	 * @return the width of the panel
	 */
	public int width() { return this.width; }

	/**
	 * The height of the panel
	 * @return the height of the panel
	 */
	public int height() { return this.height; }
}
