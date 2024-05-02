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

			if (collider.getCollider().intersectsInclusive(other.getCollider()))
				invokeCollision(collider, other);
			else if ((collider.isDeepCollision() || other.isDeepCollision()))
				if (collider.getCollider().contains(other.getCollider()) || other.getCollider().contains(collider.getCollider()))
					invokeCollision(collider, other);
		}
	}

	public static void detectCollisions(Scene scene)
	{
		int offset = 1;
		ICollider[] colliders = scene.collidersArr();
		for (int i = 0; i < colliders.length; i++)
			detectCollision(colliders, colliders[i], offset++);
	}
}
