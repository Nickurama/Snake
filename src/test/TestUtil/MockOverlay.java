package TestUtil;

import javax.swing.JPanel;

import GameEngine.*;
import Geometry.*;

public class MockOverlay extends GameObject implements IOverlay
{
	private char[][] overlay;
	public MockOverlay(char[][] overlay) throws GeometricException
	{
		this.overlay = overlay;
	}
	public char[][] getOverlay() { return this.overlay; }
	public JPanel getPanel() { return null; }
}
