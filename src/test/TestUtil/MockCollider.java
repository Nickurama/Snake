package TestUtil;

import GameEngine.*;
import Geometry.*;

public class MockCollider extends GameObject implements ICollider
{
	IGeometricShape<?> collider;
	IGeometricShape<?> otherShape;
	GameObject other;
	boolean hasCollided;
	public MockCollider(IGeometricShape<?> collider)
	{
		this.collider = collider;
		this.otherShape = null;
		this.other = null;
		this.hasCollided = false;
	}
	public IGeometricShape<?> getCollider() { return this.collider; }
	public GameObject getGameObject() { return this; }
	public IGeometricShape<?> getOtherShape() { return this.otherShape; }
	public GameObject getOther() { return this.other; }
	public boolean hasCollided() { return this.hasCollided; }
	public void onCollision(GameObject other)
	{
		this.otherShape = ((ICollider) other).getCollider();
		this.other = other;
		this.hasCollided = true;
	}
}
