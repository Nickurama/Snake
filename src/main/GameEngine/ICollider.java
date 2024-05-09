package GameEngine;

import Geometry.IGeometricShape;

/**
 * Represents a {@link GameObject game object} that can collide
 * 
 * @author Diogo Fonseca a79858
 * @version 02/05/2024
 *
 * @inv onCollision is called whenever there is a collision
 * @see GameObject
 * @see CollisionManager
 */
public interface ICollider
{
	/**
	 * Event triggered when a collision occurs
	 *
	 * Should only be called by the {@link CollisionManager collision manager}.
	 * @param other the {@link GameObject game object} that collided with the current one
	 */
	public void onCollision(GameObject other);

	/**
	 * The collider's shape, also commonly refered to as a 'hitbox'
	 * @return the collider's shape, also commonly refered to as a 'hitbox'
	 */
	public IGeometricShape<?> getCollider();

	/**
	 * If the Collider should test for deep collisions.
	 * Deep collisions use inclusive intersection and are also triggered if
	 * one collider contains the other.
	 * @return if the collider should test for deep collisions
	 */
	public boolean isDeepCollision();
}
