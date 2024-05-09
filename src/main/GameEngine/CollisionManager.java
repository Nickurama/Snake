package GameEngine;

import java.util.ArrayList;

/**
 * Class responsible for detecting collisions and calling
 * the {@link ICollider#onCollision(GameObject) onCollision} event
 * 
 * @author Diogo Fonseca a79858
 * @version 05/05/2024
 *
 * @see Scene
 * @see ICollider
 */
public class CollisionManager
{
	/**
	 * Detects collisions in a scene.
	 * calls the {@link ICollider#onCollision(GameObject) onCollision} event in case of collision
	 *
	 * @param scene the scene to check collisions in
	 */
	public static void detectCollisions(Scene scene)
	{
		int offset = 1;
		ICollider[] colliders = scene.collidersArr();
		for (int i = 0; i < colliders.length; i++)
			detectCollision(colliders, colliders[i], offset++);
	}

	/**
	 * Detects if a {@link ICollider collider} collided with any other and
	 * calls the {@link ICollider#onCollision(GameObject) onCollision} event in case of collision
	 * with the {@link GameObject game object} it collided with as argument.
	 *
	 * It has an offset for efficiency purposes, so that the same collisions needn't be checked twice,
	 * starting the collision checking at the offset provided in the colliders array.
	 *
	 * @param colliders all the other colliders
	 * @param collider the collider to check for collision
	 * @param offset the offset (starting index) to start checking collisions from in the colliders array
	 */
	private static void detectCollision(ICollider[] colliders, ICollider collider, int offset)
	{
		for (int i = offset; i < colliders.length; i++)
		{
			ICollider other = colliders[i];

			if (collides(collider, other))
				invokeCollision(collider, other);
		}
	}

	/**
	 * Detects if two colliders collide.
	 * A collider never collides with itself.
	 *
	 * @param collider the collider to check collision with
	 * @param other the other collider to check for collision
	 * @return if the two colliders collide (false if they're the same collider)
	 */
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

	/**
	 * Call the {@link ICollider#onCollision(GameObject) onCollision} event
	 * between the two colliders. providing each one with the other as the
	 * {@link GameObject game object} they collided with.
	 *
	 * @param collider the first collider to invoke the collision on
	 * @param other the second collider to invoke the collision on
	 */
	private static void invokeCollision(ICollider collider, ICollider other)
	{
		collider.onCollision((GameObject)other);
		other.onCollision((GameObject)collider);
	}

	/**
	 * Checks if a collider collides with any other in a given scene.
	 *
	 * @param collider the collider to check for collisions
	 * @param scene the scene to check for collisions in
	 * @return if the collider collides with anything in the scene
	 */
	public static boolean collidesAny(ICollider collider, Scene scene)
	{
		for (ICollider other : scene.colliders())
			if (collides(collider, other))
				return true;
		return false;
	}

	/**
	 * Gets all the game objects that collide with the provided collider in a scene
	 *
	 * @param collider the collider to check for collisions
	 * @param scene the scene to check for collisions in
	 * @return the game objects in the scene that collide with the provided collider
	 */
	public static GameObject[] getCollisions(ICollider collider, Scene scene)
	{
		ArrayList<GameObject> collisions = new ArrayList<GameObject>();
		for (ICollider other : scene.colliders())
			if (collides(collider, other))
				collisions.add((GameObject)other);

		return collisions.toArray(new GameObject[0]);
	}
}
