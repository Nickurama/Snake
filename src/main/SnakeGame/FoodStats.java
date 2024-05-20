package SnakeGame;

import Geometry.*;

/**
 * A class containing the required information to be
 * able to get the snake's properties on command
 * 
 * @author Diogo Fonseca a79858
 * @version 08/05/2024
 * 
 * @see IFoodStats
 * @see IFood
 */
public class FoodStats implements IFoodStats
{
	@Override
	public Point position()
	{
		return GameManager.getInstance().foodPos();
	}
}
