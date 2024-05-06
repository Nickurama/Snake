package SnakeGame;

import GameEngine.*;

public interface IFood extends ICollider, IRenderable
{
	public void consume();
	public boolean wasConsumed();
}
