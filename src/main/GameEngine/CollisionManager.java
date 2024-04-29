package GameEngine;

public class CollisionManager
{
	private static void invokeCollision(ICollider collider, ICollider other)
	{
		collider.onCollision(other.getGameObject());
		other.onCollision(collider.getGameObject());
	}

	private static void detectCollision(ICollider[] colliders, ICollider collider, int offset)
	{
		for (int i = offset; i < colliders.length; i++)
		{
			ICollider other = colliders[i];

			if (other == collider)
				continue;

			if (collider.getCollider().intersects(other.getCollider()))
				invokeCollision(collider, other);
		}
	}

	public static void detectCollisions(Scene scene)
	{
		int offset = 1;
		for (ICollider collider : scene.colliders())
			detectCollision(scene.collidersArr(), collider, offset++);
	}
}
