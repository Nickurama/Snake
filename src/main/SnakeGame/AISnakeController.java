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
	private ISpatialComponent foodPos;
	private GameMap map;

	public AISnakeController(ISnakeStats snake, int snakeSize, ISpatialComponent foodPos, GameMap map)
	{
		this.snake = snake;
		this.snakeSize = snakeSize;
		this.foodPos = foodPos;
		this.map = map;
	}

	public TurnDirection nextTurn()
	{
		return generateNextDir();
	}

	private TurnDirection generateNextDir()
	{
		return getRelativeDir(generateNextAbsoluteDir());
	}

	private TurnDirection getRelativeDir(Direction absoluteDir)
	{
		TurnDirection result = TurnDirection.NONE;

		if (absoluteDir == null)
			return TurnDirection.NONE;
		else if (isLeftTurn(absoluteDir, snake.direction()))
			result = TurnDirection.LEFT;
		else if (isRightTurn(absoluteDir, snake.direction()))
			result = TurnDirection.RIGHT;

		System.out.println("Relative dir: " + result);

		return result;
	}

	private boolean isLeftTurn(Direction absoluteDir, Direction snakeDir)
	{
		switch (snakeDir)
		{
			case Direction.UP:
				if (absoluteDir.equals(Direction.LEFT))
					return true;
				break;
			case Direction.DOWN:
				if (absoluteDir.equals(Direction.RIGHT))
					return true;
				break;
			case Direction.LEFT:
				if (absoluteDir.equals(Direction.DOWN))
					return true;
				break;
			case Direction.RIGHT:
				if (absoluteDir.equals(Direction.UP))
					return true;
				break;
		}
		return false;
	}

	private boolean isRightTurn(Direction absoluteDir, Direction snakeDir)
	{
		switch (snakeDir)
		{
			case Direction.UP:
				if (absoluteDir.equals(Direction.RIGHT))
					return true;
				break;
			case Direction.DOWN:
				if (absoluteDir.equals(Direction.LEFT))
					return true;
				break;
			case Direction.LEFT:
				if (absoluteDir.equals(Direction.UP))
					return true;
				break;
			case Direction.RIGHT:
				if (absoluteDir.equals(Direction.DOWN))
					return true;
				break;
		}
		return false;
	}

	private Direction generateNextAbsoluteDir()
	{
		Direction nextAbsoluteDir = null;
		Point nextPos = findNextPos();
		if (nextPos == null)
			return null;

		System.out.println("Current pos: " + snake.position());
		System.out.println("Next pos: " + nextPos);

		if (nextPos.X() > snake.position().X() && MathUtil.areEqual(nextPos.Y(), snake.position().Y()))
			nextAbsoluteDir = Direction.RIGHT;
		else if (nextPos.X() < snake.position().X() && MathUtil.areEqual(nextPos.Y(), snake.position().Y()))
			nextAbsoluteDir = Direction.LEFT;
		else if (nextPos.Y() > snake.position().Y() && MathUtil.areEqual(nextPos.X(), snake.position().X()))
			nextAbsoluteDir = Direction.UP;
		else if (nextPos.Y() < snake.position().Y() && MathUtil.areEqual(nextPos.X(), snake.position().X()))
			nextAbsoluteDir = Direction.DOWN;

		System.out.println("Absolute dir: " + nextAbsoluteDir);

		return nextAbsoluteDir;
	}

	private Point findNextPos()
	{
		Point[] validPositions = this.map.getAllValidUnitSpawnPositions(this.snakeSize);
		Point start = this.snake.position();
		Point finish = this.map.getOuterPosition(this.foodPos.position(), this.snakeSize);

		Point[] path = findShortestPath(start, finish, validPositions);
		if (path == null)
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
		if (MathUtil.areEqual(relativePoint.Y(), map.length - 1) || MathUtil.areEqual(relativePoint.Y(), 0) ||
			MathUtil.areEqual(relativePoint.X(), map[0].length - 1) || MathUtil.areEqual(relativePoint.X(), 0))
			return null;

		Point point = null;
		switch (dir)
		{
			case Direction.UP:
				point = validPoint(safeTranslate(relativePoint, 0, 1), map);
				break;
			case Direction.DOWN:
				point = validPoint(safeTranslate(relativePoint, 0, -1), map);
				break;
			case Direction.LEFT:
				point = validPoint(safeTranslate(relativePoint, 1, 0), map);
				break;
			case Direction.RIGHT:
				point = validPoint(safeTranslate(relativePoint, -1, 0), map);
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
		// path.add(found.getValue());
		// path.removeLast();

		Collections.reverse(path); // somewhat pointless and inefficient, but the loss in performance is trivial for snake game

		return path;
	}

	private Node findShortestRelativePathNode(Point start, Point finish, boolean[][] map)
	{
		Queue<Node> toVisit = new LinkedList<Node>();

		toVisit.add(new Node(start, null));
		Node found = null;

		// int n = 0;
		while (toVisit.size() > 0)
		{
			// n++;
			// if (n > 20)
			// 	System.exit(0);
			Node current = toVisit.remove();
			// System.out.println("=============");
			// System.out.println("Visiting: " + current.getValue()); // + " isValid: " + isValidPath(current.getValue(), map));

			if (current.getValue().equals(finish))
			{
				System.out.println("Found!");
				found = current;
				break;
			}

			addSurroundingUnits(current, toVisit, map);
			// set(current.getValue(), map, false);
			// System.out.println("=============");
		}

		return found;
	}

	private void addSurroundingUnits(Node current, Queue<Node> toVisit, boolean[][] map)
	{
		Point[] surroundings = new Point[4];
		surroundings[0] = moveToDir(current.getValue(), Direction.UP, map);
		surroundings[1] = moveToDir(current.getValue(), Direction.DOWN, map);
		surroundings[2] = moveToDir(current.getValue(), Direction.LEFT, map);
		surroundings[3] = moveToDir(current.getValue(), Direction.RIGHT, map);
		// surroundings[0] = getUp(current.getValue(), map);
		// surroundings[1] = getDown(current.getValue(), map);
		// surroundings[2] = getLeft(current.getValue(), map);
		// surroundings[3] = getRight(current.getValue(), map);

		// for (Point point : surroundings)
		// 	if (point != null)
		// 		System.out.println("validPoint? (" + (int)Math.round(point.X()) + "," + (int)Math.round(point.Y()) + ") = " + map[(int)Math.round(point.Y())][(int)Math.round(point.X())]);

		for (Point point : surroundings)
		{
			if (point != null)
			{
				set(point, map, false);
				toVisit.add(new Node(point, current));
			}
		}
	}

	// private Point getUp(Point current, boolean[][] map)
	// {
	// 	if (MathUtil.areEqual(current.Y(), map.length - 1))
	// 		return null;
	//
	// 	return validPoint(safeTranslate(current, 0, 1), map);
	// }
	//
	// private Point getDown(Point current, boolean[][] map)
	// {
	// 	if (MathUtil.areEqual(0, current.Y()))
	// 		return null;
	//
	// 	return validPoint(safeTranslate(current, 0, -1), map);
	// }
	//
	// private Point getRight(Point current, boolean[][] map)
	// {
	// 	if (MathUtil.areEqual(current.X(), map[0].length - 1))
	// 		return null;
	//
	// 	return validPoint(safeTranslate(current, 1, 0), map);
	// }
	//
	// private Point getLeft(Point current, boolean[][] map)
	// {
	// 	if (MathUtil.areEqual(0, current.X()))
	// 		return null;
	//
	// 	return validPoint(safeTranslate(current, -1, 0), map);
	// }

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
		// System.out.println("Point being set: " + point);
		// System.out.println("Indexes being set: (" + (int)Math.round(point.X()) + "," + (int)Math.round(point.Y()) + ")");
		map[(int)Math.round(point.Y())][(int)Math.round(point.X())] = value;
		// System.out.println("set: (" + (int)Math.round(point.X()) + "," + (int)Math.round(point.Y()) + ") to " + value);
	}
}
