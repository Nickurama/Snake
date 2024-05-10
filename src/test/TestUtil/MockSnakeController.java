package TestUtil;

import SnakeGame.*;

public class MockSnakeController implements ISnakeController
{
	private Direction.TurnDirection nextDir;
	public MockSnakeController() { this.nextDir = Direction.TurnDirection.NONE; }
	public Direction.TurnDirection nextTurn() { return this.nextDir; }
	public void setNextTurn(Direction.TurnDirection nextDir) { this.nextDir = nextDir; }
}
