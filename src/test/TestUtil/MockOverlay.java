package TestUtil;

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
}
