package TestUtil;

import GameEngine.*;
import Geometry.*;

public class MockCollider extends GameObject implements ICollider
{
	IGeometricShape<?> collider;
	IGeometricShape<?> otherShape;
	GameObject other;
	boolean hasCollided;
	boolean isDeepCollision;
	public MockCollider(IGeometricShape<?> collider, boolean isDeepCollision)
	{
		this.collider = collider;
		this.isDeepCollision = isDeepCollision;
		this.otherShape = null;
		this.other = null;
		this.hasCollided = false;
	}
	public IGeometricShape<?> getCollider() { return this.collider; }
	public boolean isDeepCollision() { return this.isDeepCollision; }
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
