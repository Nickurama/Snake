package TestUtil;

import GameEngine.*;
import Geometry.*;

public class MockRenderable extends GameObject implements IRenderable
{
	private RenderData<?> rData;
	public MockRenderable(RenderData<?> rData) throws GeometricException
	{
		this.rData = rData;
	}
	public RenderData<?> getRenderData() { return this.rData; }
}
