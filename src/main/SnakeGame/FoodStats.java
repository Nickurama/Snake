package SnakeGame;

import Geometry.*;

public class FoodStats implements IFoodStats
{
	public Point position()
	{
		return GameManager.getInstance().foodPos();
	}
}
