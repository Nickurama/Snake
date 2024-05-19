package GameEngine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;

/**
 * Represents the window in which to render the game in.
 * Contains a raster to draw on.
 * 
 * @author Diogo Fonseca a79858
 * @version 16/05/2024
 * @see Raster
 */
public class GraphicWindow
{
	private JFrame frame;
	private Raster raster;
	
	/**
	 * Instantiates a new window.
	 * @param width the width of the inner raster
	 * @param height the height of the inner raster
	 * @param title the title of the window
	 * @param color the background color
	 */
	public GraphicWindow(int width, int height, String title, Color bgColor)
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
		// Timer timer = new Timer(1000, new (this));
		frame.setFocusable(true);
		frame.requestFocus();
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

	/**
	 * Disposes the frame, closing the window.
	 */
	public void close()
	{
		this.frame.dispatchEvent(new WindowEvent(this.frame, WindowEvent.WINDOW_CLOSING));
		// this.dispatchEvent(new WindowEvent(this.frame, WindowEvent.WINDOW_CLOSING));

		this.frame = null;
		this.raster = null;
	}

	/**
	 * Repaints everything
	 */
	public void repaint()
	{
		raster.forceNextRepaint();
		frame.repaint();
		raster.repaint();
	}

	/**
	 * Adds a key listener to the frame
	 * @param listener the listener to add to the frame
	 */
	public void addKeyListener(KeyListener listener)
	{
		frame.addKeyListener(listener);
	}
}
