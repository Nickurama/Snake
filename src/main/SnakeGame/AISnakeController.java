package SnakeGame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

import GameEngine.*;
import Geometry.*;

public class AISnakeController extends GameObject implements ISnakeController
{
	private class Node
	{
		private Node parent;
		private Point value;
		public Node(Point value, Node parent)
		{
			this.parent = parent;
			this.value = value;
		}
		public Node getParent() { return this.parent; }
		public Point getValue() { return this.value; }
	}

	private ISnakeStats snake;
	private int snakeSize;
	private ISpatialComponent food;
	private GameMap map;

	public AISnakeController(ISnakeStats snake, int snakeSize, IFoodStats food, GameMap map)
	{
		this.snake = snake;
		this.snakeSize = snakeSize;
		this.food = food;
		this.map = map;
	}

	public TurnDirection nextTurn()
	{
		return generateNextDir();
	}

	private TurnDirection generateNextDir()
	{
		return Direction.getRelativeDir(generateNextAbsoluteDir(), snake.direction());
	}

	private Direction generateNextAbsoluteDir()
	{
		Direction nextAbsoluteDir = null;
		Point nextPos = findNextPos();
		if (nextPos == null)
			return null;

		if (nextPos.X() > snake.position().X() && MathUtil.areEqual(nextPos.Y(), snake.position().Y()))
			nextAbsoluteDir = Direction.RIGHT;
		else if (nextPos.X() < snake.position().X() && MathUtil.areEqual(nextPos.Y(), snake.position().Y()))
			nextAbsoluteDir = Direction.LEFT;
		else if (nextPos.Y() > snake.position().Y() && MathUtil.areEqual(nextPos.X(), snake.position().X()))
			nextAbsoluteDir = Direction.UP;
		else if (nextPos.Y() < snake.position().Y() && MathUtil.areEqual(nextPos.X(), snake.position().X()))
			nextAbsoluteDir = Direction.DOWN;

		Logger.log(Logger.Level.DEBUG, "Absolute dir: " + nextAbsoluteDir);
		Logger.log(Logger.Level.DEBUG, "Relative dir: " + Direction.getRelativeDir(nextAbsoluteDir, snake.direction()));

		return nextAbsoluteDir;
	}

	private Point findNextPos()
	{
		Point[] validPositions = this.map.getAllValidUnitSpawnPositions(this.snakeSize);
		Point start = this.snake.position();
		Point finish = this.map.getOuterPosition(this.food.position(), this.snakeSize);

		Point[] path = findShortestPath(start, finish, validPositions);
		if (path == null || path.length == 0)
			return null;
		return path[0];
	}

	private Point[] findShortestPath(Point start, Point finish, Point[] validPositions)
	{
		Point startIndex = map.getUnitIndex(start, snakeSize);
		Point finishIndex = map.getUnitIndex(finish, snakeSize);
		boolean[][] relativeMap = map.asArray(validPositions, snakeSize);

		set(finishIndex, relativeMap, true);
		Point back = moveToDir(startIndex, oppositeDir(snake.direction()), relativeMap);
		if (back != null)
			set(back, relativeMap, false);

		ArrayList<Point> shortestRelativePath = findShortestRelativePath(startIndex, finishIndex, relativeMap);

		if (shortestRelativePath == null)
			return null;

		Point[] path = new Point[shortestRelativePath.size()];
		int i = 0;
		for (Point point : shortestRelativePath)
			path[i++] = map.getUnitPoint(point, snakeSize);

		return path;
	}

	private Point moveToDir(Point relativePoint, Direction dir, boolean[][] map)
	{
		Point point = null;
		switch (dir)
		{
			case Direction.UP:
				if (!MathUtil.areEqual(relativePoint.Y(), map.length - 1))
					point = safeTranslate(relativePoint, 0, 1);
				break;
			case Direction.DOWN:
				if (!MathUtil.areEqual(relativePoint.Y(), 0))
					point = safeTranslate(relativePoint, 0, -1);
				break;
			case Direction.LEFT:
				if (!MathUtil.areEqual(relativePoint.X(), 0))
					point = safeTranslate(relativePoint, -1, 0);
				break;
			case Direction.RIGHT:
				if (!MathUtil.areEqual(relativePoint.X(), map[0].length - 1))
					point = safeTranslate(relativePoint, 1, 0);
				break;
		}
		return point;
	}

	private Direction oppositeDir(Direction dir)
	{
		Direction opposite = null;
		switch (dir)
		{
			case Direction.UP:
				opposite = Direction.DOWN;
				break;
			case Direction.DOWN:
				opposite = Direction.UP;
				break;
			case Direction.LEFT:
				opposite = Direction.RIGHT;
				break;
			case Direction.RIGHT:
				opposite = Direction.LEFT;
				break;
		}
		return opposite;
	}

	private ArrayList<Point> findShortestRelativePath(Point relativeStart, Point relativeFinish, boolean[][] map)
	{
		Node found = findShortestRelativePathNode(relativeStart, relativeFinish, map);
		if (found == null)
			return null;

		ArrayList<Point> path = new ArrayList<Point>();

		while(found.getParent() != null)
		{
			path.add(found.getValue());
			found = found.getParent();
		} // last node is not added as it is the initial position

		Collections.reverse(path); // somewhat pointless and inefficient, but the loss in performance is trivial for snake game

		return path;
	}

	private Node findShortestRelativePathNode(Point start, Point finish, boolean[][] map)
	{
		Queue<Node> toVisit = new LinkedList<Node>();

		toVisit.add(new Node(start, null));
		Node found = null;

		while (toVisit.size() > 0)
		{
			Node current = toVisit.remove();

			if (current.getValue().equals(finish))
			{
				found = current;
				Logger.log(Logger.Level.INFO, mapToStr(map, finish));
			}

			addValidSurroundingUnits(current, toVisit, map);
		}

		if (found == null)
			found = getAdjacentValidPosition(new Node(start, null));

		return found;
	}

	private Node getAdjacentValidPosition(Node start)
	{
		Point[] validPositions = map.getAllValidUnitSpawnPositions(this.snakeSize);
		boolean[][] relativeMap = map.asArray(validPositions, snakeSize);

		Queue<Node> toVisit = new LinkedList<Node>();
		toVisit.add(start);
		Node current = null;
		while (toVisit.size() > 0)
		{
			current = toVisit.remove();
			addValidSurroundingUnits(current, toVisit, relativeMap);
		}

		if (current == null)
			return null;

		return current;
	}

	private String mapToStr(boolean[][] map, Point finish)
	{
		StringBuilder builder = new StringBuilder();
		for (int i = map.length - 1; i >= 0; i--)
		{
			for (int j = 0; j < map[0].length; j++)
			{
				if (map[i][j])
					builder.append('-');
				else
				{
					if (i == (int)Math.round(finish.Y()) && j == (int)Math.round(finish.X()))
						builder.append('!');
					else
						builder.append('x');
				}
			}
			builder.append('\n');
		}
		return builder.toString();
	}


	private void addValidSurroundingUnits(Node current, Queue<Node> toVisit, boolean[][] map)
	{
		Point[] surroundings = new Point[4];
		surroundings[0] = validPoint(moveToDir(current.getValue(), Direction.UP, map), map);
		surroundings[1] = validPoint(moveToDir(current.getValue(), Direction.DOWN, map), map);
		surroundings[2] = validPoint(moveToDir(current.getValue(), Direction.LEFT, map), map);
		surroundings[3] = validPoint(moveToDir(current.getValue(), Direction.RIGHT, map), map);

		for (Point point : surroundings)
		{
			if (point != null)
			{
				set(point, map, false);
				toVisit.add(new Node(point, current));
			}
		}
	}

	private Point safeTranslate(Point point, int x, int y)
	{
		try
		{
			return point.translate(x, y);
		}
		catch (GeometricException e)
		{
			Logger.log(Logger.Level.FATAL, "Should not be possible. x and y should always be positive.\n" + e);
			throw new RuntimeException("Should not be possible. x and y should always be positive.");
		}
	}

	private Point validPoint(Point point, boolean[][] map)
	{
		if (point == null)
			return null;
		if (!isValidPath(point, map))
			return null;
		return point;
	}

	private boolean isValidPath(Point point, boolean[][] map)
	{
		return map[(int)Math.round(point.Y())][(int)Math.round(point.X())];
	}

	private void set(Point point, boolean[][] map, boolean value)
	{
		map[(int)Math.round(point.Y())][(int)Math.round(point.X())] = value;
	}
}
