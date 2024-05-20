package GameEngine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.PaintEvent;
import java.awt.event.WindowEvent;

/**
 * Represents the window in which to render the game in.
 * Contains a raster to draw on.
 * 
 * @author Diogo Fonseca a79858
 * @version 16/05/2024
 * @see Raster
 */
public class GraphicWindow extends JFrame
{
	private Raster raster;
	private JLayeredPane layeredPane;
	private JPanel overlay;
	
	/**
	 * Instantiates a new window.
	 * @param width the width of the inner raster
	 * @param height the height of the inner raster
	 * @param title the title of the window
	 * @param color the background color
	 */
	public GraphicWindow(int width, int height, String title, Color bgColor)
	{
		super(title);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());

		this.layeredPane = new JLayeredPane();
		add(this.layeredPane);

		this.raster = new Raster(width, height, bgColor);
		this.layeredPane.setPreferredSize(this.raster.getPreferredSize());
		this.layeredPane.add(this.raster, JLayeredPane.DEFAULT_LAYER);

		// add(raster);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
		setFocusable(true);
		requestFocus();

		this.overlay = null;
	}

	/**
	 * Sets the icon for the window
	 * @param filename the name of the file to set as an icon for the window
	 */
	public void setIcon(String filename)
	{
		this.setIconImage(new ImageIcon(filename).getImage());
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
		dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
		this.raster = null;
	}

	/**
	 * Sets the overlay for the window
	 * @param panel the overlay's panel
	 */
	public void setOverlay(JPanel panel)
	{
		if (this.overlay != null && this.overlay.equals(panel))
			return;

		if (this.overlay != null)
			this.layeredPane.remove(this.overlay);


		this.overlay = panel;
		this.layeredPane.add(panel, JLayeredPane.POPUP_LAYER);
	}

	@Override
	public void paint(Graphics g)
	{
		super.paint(g);

		// weird hack to overflow the EventQueue
		// in order to force it to flush and by
		// consequence force it to paint immediately
		for (int i = 0; i < 100; i++)
			super.paint(g);
	}
}
