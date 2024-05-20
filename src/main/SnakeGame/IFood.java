package SnakeGame;

import GameEngine.*;

/**
 * Represents a food object.
 * Can be consumed.
 * 
 * @author Diogo Fonseca a79858
 * @version 08/05/2024
 */
public interface IFood extends ICollider, IRenderable, ISpatialComponent
{
	/**
	 * Consumes the food, uninstantiating it
	 */
	public void consume();

	/**
	 * Checks if the food has been consumed
	 * @return if the food has been consumed
	 */
	public boolean wasConsumed();
}
