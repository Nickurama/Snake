package TestUtil;

import GameEngine.*;
import Geometry.*;

public class MockCollider extends GameObject implements ICollider
{
	IGeometricShape collider;
	IGeometricShape other;
	boolean hasCollided;
	public MockCollider(Polygon collider)
	{
		this.collider = collider;
		this.other = null;
		this.hasCollided = false;
	}
	public IGeometricShape getCollider() { return this.collider; }
	public IGeometricShape getOther() { return this.other; }
	public boolean hasCollided() { return this.hasCollided; }
	public void onCollision(GameObject other)
	{
		this.other = ((ICollider) other).getCollider();
		this.hasCollided = true;
	}
}
