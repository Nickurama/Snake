package GameEngine;

import Geometry.IGeometricShape;

public interface ICollider
{
	public void onCollision(GameObject other);
	public IGeometricShape<?> getCollider();
	public GameObject getGameObject();
	public boolean isDeepCollision();
}
