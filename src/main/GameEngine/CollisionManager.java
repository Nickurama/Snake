package GameEngine;

import java.util.ArrayList;

public class CollisionManager
{
	public static void detectCollisions(Scene scene)
	{
		int offset = 1;
		ICollider[] colliders = scene.collidersArr();
		for (int i = 0; i < colliders.length; i++)
			detectCollision(colliders, colliders[i], offset++);
	}

	private static void detectCollision(ICollider[] colliders, ICollider collider, int offset)
	{
		for (int i = offset; i < colliders.length; i++)
		{
			ICollider other = colliders[i];

			if (collides(collider, other))
				invokeCollision(collider, other);
			// if (other == collider)
			// 	continue;
			//
			// if (collider.getCollider().intersectsInclusive(other.getCollider()))
			// 	invokeCollision(collider, other);
			// else if ((collider.isDeepCollision() || other.isDeepCollision()))
			// 	if (collider.getCollider().contains(other.getCollider()) || other.getCollider().contains(collider.getCollider()))
			// 		invokeCollision(collider, other);
		}
	}

	public static boolean collides(ICollider collider, ICollider other)
	{
		if (other == collider)
			return false;

		boolean result = false;
		if (collider.getCollider().intersectsInclusive(other.getCollider()))
			result = true;
		else if ((collider.isDeepCollision() || other.isDeepCollision()))
			if (collider.getCollider().contains(other.getCollider()) || other.getCollider().contains(collider.getCollider()))
				result = true;
		return result;
	}

	private static void invokeCollision(ICollider collider, ICollider other)
	{
		collider.onCollision(other.getGameObject());
		other.onCollision(collider.getGameObject());
	}

	public static boolean collidesAny(ICollider collider, Scene scene)
	{
		for (ICollider other : scene.colliders())
			if (collides(collider, other))
				return true;
		return false;
	}

	public static GameObject[] getCollisions(ICollider collider, Scene scene)
	{
		ArrayList<GameObject> collisions = new ArrayList<GameObject>();
		for (ICollider other : scene.colliders())
			if (collides(collider, other))
				collisions.add(other.getGameObject());

		return collisions.toArray(new GameObject[0]);
	}
}
