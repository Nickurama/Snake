package GameEngine;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Represents a graphical raster, in which each pixel can be drawn on.
 * 
 * @author Diogo Fonseca a79858
 * @version 16/05/2024
 * @see GraphicWindow
 */
public class Raster extends JPanel
{
	private BufferedImage img;
	private Color bgColor;
	private Dimension preferredSize;
	private boolean forceNextRepaint;

	/**
	 * Instantiates a new raster 
	 * @param width the width of the raster
	 * @param height the height of the raster
	 * @param bgColor the backgrond color of the raster
	 */
	public Raster(int width, int height, Color bgColor)
	{
		super();
		img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		this.preferredSize = new Dimension(width, height);
		this.bgColor = bgColor;
		this.forceNextRepaint = true;
		reset();
	}

	/**
	 * Resets the raster, setting every pixel to the background color.
	 */
	public void reset()
	{
		for (int y = 0; y < img.getHeight(); y++)
			for (int x = 0; x < img.getWidth(); x++)
				img.setRGB(x, y, this.bgColor.getRGB());
	}

	/**
	 * Sets a color to the specified pixel
	 * @param x the x coordinate of the pixel
	 * @param y the y coordiante of the pixel
	 * @param drawColor the color to set the pixel to
	 */
	public void draw(int x, int y, Color drawColor)
	{
		if (x < 0 || x >= img.getWidth() || y < 0 || y >= img.getHeight())
			return;
		img.setRGB(x, y, drawColor.getRGB());
	}

	@Override
	public Dimension getPreferredSize() { return this.preferredSize; }

	@Override
	public void paint(Graphics graphics)
	{
		super.paint(graphics);

		Graphics2D g2d = (Graphics2D) graphics;
		g2d.drawImage(img, 0, 0, this);
		if (forceNextRepaint)
		{
			repaint(0, 0, img.getWidth(), img.getHeight());
			this.forceNextRepaint = false;
		}
	}

	/**
	 * Sets the next repaint to be forced and immediate
	 */
	public void forceNextRepaint()
	{
		this.forceNextRepaint = true;
	}
}
