package SnakeGame;

import GameEngine.*;

public interface IFood extends ICollider, IRenderable, ISpatialComponent
{
	public void consume();
	public boolean wasConsumed();
}
