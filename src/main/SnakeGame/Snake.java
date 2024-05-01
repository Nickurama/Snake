package SnakeGame;

import Geometry.*;
import GameEngine.*;

public class Snake extends GameObject
{
	protected static final int LAYER = 2;
	private boolean isFilled;
	private char tailChar;
	private char headChar;
	private int unitSize;
	private boolean isDead;
	private SnakeUnit head;

	public Snake(Point initialPos, int unitSize, boolean isFilled, char tailChar, char headChar) throws SnakeGameException
	{
		this.isFilled = isFilled;
		this.tailChar = tailChar;
		this.headChar = headChar;
		this.unitSize = unitSize;
		this.isDead = false;
		this.head = new SnakeUnit(this, initialPos, headChar);
	}

	public void die()
	{
		if (isDead)
			return;

		this.isDead = true;
	}

	@Override
	public void start()
	{
		try
		{
			super.sceneHandle.add(this.head);
		}
		catch (GameEngineException e)
		{
			Logger.log(Logger.Level.FATAL, "Should never happen: SnakeUnit shouldn't have been added to the scene since Snake created it.\n" + e);
			throw new Error("Should never happen: SnakeUnit shouldn't have been added to the scene since Snake created it.");
		}
	}

	public int unitSize() { return this.unitSize; }

	public boolean isFilled() { return this.isFilled; }

	public boolean isDead() { return this.isDead; }
}
