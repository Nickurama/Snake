package GameEngine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;

/**
 * Represents the frame in which to render the game in.
 * Contains a raster to draw on.
 * 
 * @author Diogo Fonseca a79858
 * @version 16/05/2024
 */
public class RenderFrame extends JFrame
{
	private JFrame frame;
	private Raster raster;
	
	/**
	 * Instantiates a new frame.
	 * @param width the width of the inner raster
	 * @param height the height of the inner raster
	 * @param title the title of the window
	 * @param color the background color
	 */
	public RenderFrame(int width, int height, String title, Color bgColor)
	{
		frame = new JFrame(title);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		raster = new Raster(width, height, bgColor);
		frame.add(raster);
		frame.pack();
		frame.setLocationRelativeTo(null);
		// frame.setSize(width, height);
		frame.setVisible(true);
	}

	/**
	 * Resets the raster, setting every pixel to the background color
	 */
	public void reset()
	{
		raster.reset();
	}

	/**
	 * Sets a specific pixel to a color
	 * @param x the x coordinate of the pixel to assign the color to
	 * @param y the y coordinate of the pixel to assign the color to
	 * @param drawColor the color to assign to the pixel
	 */
	public void draw(int x, int y, Color drawColor)
	{
		raster.draw(x, y, drawColor);
	}

	@Override
	public void repaint()
	{
		frame.repaint();
		raster.repaint();
	}
}
