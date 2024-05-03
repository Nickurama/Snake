package TestUtil;

import SnakeGame.*;

public class MockSnakeController implements ISnakeController
{
	private TurnDirection nextDir;
	public MockSnakeController() { this.nextDir = TurnDirection.NONE; }
	public TurnDirection nextTurn() { return this.nextDir; }
	public void setNextTurn(TurnDirection nextDir) { this.nextDir = nextDir; }
}
