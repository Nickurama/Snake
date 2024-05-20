package SnakeGame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

import GameEngine.*;
import Geometry.*;
import SnakeGame.Direction.*;

/**
 * Responsible for controlling the snake automatically
 * 
 * @author Diogo Fonseca a79858
 * @version 08/05/2024
 * 
 * @inv will always give a direction
 * @see ISnakeController
 */
public class AISnakeController extends GameObject implements ISnakeController
{
	/**
	 * Represents a position associated with the position it came from.
	 * In other words a path represented as a linked list of sorts
	*/
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

	/**
	 * Instantiates an AISnakeController
	 * @param snake a provider for the snake to control's values
	 * @param snakeSize the size of the snake being controlled
	 * @param food a provider for the food's values
	 * @param map the map the snake is in
	 */
	public AISnakeController(ISnakeStats snake, int snakeSize, IFoodStats food, GameMap map)
	{
		this.snake = snake;
		this.snakeSize = snakeSize;
		this.food = food;
		this.map = map;
	}

	@Override
	public TurnDirection nextTurn()
	{
		return generateNextDir();
	}

	/**
	 * Calculates the next turn direction the snake should turn to
	 * @return the next direction the snake should turn to
	 */
	private TurnDirection generateNextDir()
	{
		return Direction.getRelativeDir(generateNextAbsoluteDir(), snake.direction());
	}

	/**
	 * Calculates the next absolute direction the snake should go to
	 * @return the next absolute direction the snake should turn to
	 */
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

	/**
	 * Calculates the next position the snake should go towards
	 * @return the next position the snake should go towards
	 */
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

	/**
	 * Finds the shortest path in a grid between start and finish,
	 * not going over invalid position
	 * Uses Dijkstra's/BFS
	 * @param start the starting position
	 * @param finish the finishing position
	 * @param validPositions all the valid positions
	 * @return the shortest path from start to finish using the valid positions,
	 * null if no path is found to finish
	 */
	private Point[] findShortestPath(Point start, Point finish, Point[] validPositions)
	{
		Point startIndex = map.getUnitIndex(start, snakeSize);
		Point finishIndex = map.getUnitIndex(finish, snakeSize);
		boolean[][] relativeMap = map.asArray(validPositions, snakeSize);

		set(finishIndex, relativeMap, true);
		Point back = moveToDir(startIndex, Direction.opposite(snake.direction()), relativeMap);
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

	/**
	 * Moves a point on the map array coordinates in a direction
	 * In case the point doesn't exist as the array's coordinates
	 * null is returned instead;
	 * @param relativePoint the point of the map array
	 * @param dir the direction to turn towards
	 * @param map the map array
	 * @return the moved Point
	 */
	private Point moveToDir(Point relativePoint, Direction dir, boolean[][] map)
	{
		Point point = null;
		switch (dir)
		{
			case UP:
				if (!MathUtil.areEqual(relativePoint.Y(), map.length - 1))
					point = safeTranslate(relativePoint, 0, 1);
				break;
			case DOWN:
				if (!MathUtil.areEqual(relativePoint.Y(), 0))
					point = safeTranslate(relativePoint, 0, -1);
				break;
			case LEFT:
				if (!MathUtil.areEqual(relativePoint.X(), 0))
					point = safeTranslate(relativePoint, -1, 0);
				break;
			case RIGHT:
				if (!MathUtil.areEqual(relativePoint.X(), map[0].length - 1))
					point = safeTranslate(relativePoint, 1, 0);
				break;
		}
		return point;
	}

	/**
	 * Finds the shortest path in points relative to the
	 * map array's indexes
	 * @param relativeStart the map array's indexes for the starting position
	 * @param relativeFinish the map array's indexes for the finishing position
	 * @param map the map array
	 * @return the shortest path relative to the map's indexes
	 */
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

	/**
	 * Finds the shortest path, storing it in nodes holding the map's array indexes
	 * Uses Dijkstra's algorithm / BFS
	 * @param start the starting position relative to the map array's indexes
	 * @param finish the finishing position relative to the map array's indexes
	 * @param map the map array
	 * @return the shortest path stored in nodes holding the map's array indexes
	 * if it can't find a path to finish, returns the longest path found
	 */
	private Node findShortestRelativePathNode(Point start, Point finish, boolean[][] map)
	{
		Queue<Node> toVisit = new LinkedList<Node>();

		toVisit.add(new Node(start, null));
		Node found = null;

		Node current = null;
		while (toVisit.size() > 0)
		{
			current = toVisit.remove();

			if (current.getValue().equals(finish))
			{
				found = current;
				Logger.log(Logger.Level.INFO, mapToStr(map, finish));
			}

			addValidSurroundingUnits(current, toVisit, map);
		}

		// if found is null get's the furthest point
		if (found == null)
			found = current;

		return found;
	}

	/**
	 * Prints a relative map array to string, for debugging purposes
	 * @param map the map to print
	 * @param finish the goal point
	 * @return the map representad as a human-readable string
	 */
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


	/**
	 * Adds all valid surrounding units to a node
	 * @param current the current node
	 * @param toVisit the nodes left to visit where the new nodes will be added
	 * @param map the map
	 */
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

	/**
	 * Performs a point translation that certainle IS valid
	 * @param point the point to translate
	 * @param dx the x difference to translate the point by
	 * @param dy the y difference to translate the point by
	 * @return the translated point
	 */
	private Point safeTranslate(Point point, int dx, int dy)
	{
		try
		{
			return point.translate(dx, dy);
		}
		catch (GeometricException e)
		{
			Logger.log(Logger.Level.FATAL, "Should not be possible. x and y should always be positive.\n" + e);
			throw new RuntimeException("Should not be possible. x and y should always be positive.");
		}
	}

	/**
	 * Checks if a point on the map is valid or not
	 * @param point the point holding tha map's indexes
	 * @param map the map
	 * @return true if the point is valid
	 * null if the provided point is null or if it's noto a valid path
	 */
	private Point validPoint(Point point, boolean[][] map)
	{
		if (point == null)
			return null;
		if (!isValidPath(point, map))
			return null;
		return point;
	}

	/**
	 * Checks if any point is a valid path/point to go towards
	 * @param point the point to test
	 * @param map the map
	 * @return if the point can be traversed to
	 */
	private boolean isValidPath(Point point, boolean[][] map)
	{
		return map[(int)Math.round(point.Y())][(int)Math.round(point.X())];
	}

	/**
	 * Sets a point on the map as valid/invalid
	 * @param point the point to set
	 * @param map the map
	 * @param value the value to set the point as on the map
	 */
	private void set(Point point, boolean[][] map, boolean value)
	{
		map[(int)Math.round(point.Y())][(int)Math.round(point.X())] = value;
	}
}
