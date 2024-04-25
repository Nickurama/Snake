package SnakeGameTests;

import SnakeGame.*;
import GameEngine.*;
import Geometry.*;
import TestUtil.*;

import org.junit.jupiter.api.Test;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;
import java.io.ByteArrayOutputStream;

public class SnakeAITests
{
	@Test
	public void ShouldAvoidWallTopRight()
	{
		// Arrange
		Rectangle mapRect = new Rectangle(new Point[]
		{
			new Point(0, 0),
			new Point(0, 4),
			new Point(4, 4),
			new Point(4, 0)
		});
		GameMap map = new GameMap(mapRect);
		Snake snake = new Snake(3, new Point(1, 3), Snake.Direction.UP);

		Scene sc = new Scene();
		sc.add(snake);
		sc.add(map);

		Snake.Direction expected = Snake.Direction.RIGHT;


		// Act
		Snake.Direction nextDir = SnakeAI.getNextMove(sc);
		
		// Arrange
		assertEquals(expected, nextDir);
	}

	@Test
	public void ShouldAvoidWallTopLeft()
	{
		// Arrange
		Rectangle mapRect = new Rectangle(new Point[]
		{
			new Point(0, 0),
			new Point(0, 4),
			new Point(4, 4),
			new Point(4, 0)
		});
		GameMap map = new GameMap(mapRect);
		Snake snake = new Snake(3, new Point(3, 3), Snake.Direction.UP);

		Scene sc = new Scene();
		sc.add(snake);
		sc.add(map);

		Snake.Direction expected = Snake.Direction.LEFT;


		// Act
		Snake.Direction nextDir = SnakeAI.getNextMove(sc);
		
		// Arrange
		assertEquals(expected, nextDir);
	}

	@Test
	public void ShouldAvoidWallDownRight()
	{
		// Arrange
		Rectangle mapRect = new Rectangle(new Point[]
		{
			new Point(0, 0),
			new Point(0, 4),
			new Point(4, 4),
			new Point(4, 0)
		});
		GameMap map = new GameMap(mapRect);
		Snake snake = new Snake(3, new Point(3, 1), Snake.Direction.DOWN);

		Scene sc = new Scene();
		sc.add(snake);
		sc.add(map);

		Snake.Direction expected = Snake.Direction.RIGHT;


		// Act
		Snake.Direction nextDir = SnakeAI.getNextMove(sc);
		
		// Arrange
		assertEquals(expected, nextDir);
	}

	@Test
	public void ShouldAvoidWallDownLeft()
	{
		// Arrange
		Rectangle mapRect = new Rectangle(new Point[]
		{
			new Point(0, 0),
			new Point(0, 4),
			new Point(4, 4),
			new Point(4, 0)
		});
		GameMap map = new GameMap(mapRect);
		Snake snake = new Snake(3, new Point(1, 1), Snake.Direction.DOWN);

		Scene sc = new Scene();
		sc.add(snake);
		sc.add(map);

		Snake.Direction expected = Snake.Direction.LEFT;


		// Act
		Snake.Direction nextDir = SnakeAI.getNextMove(sc);
		
		// Arrange
		assertEquals(expected, nextDir);
	}
}
