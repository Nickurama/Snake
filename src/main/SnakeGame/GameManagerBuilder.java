package SnakeGame;

import Geometry.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

import GameEngine.*;
import GameEngine.GameEngineFlags.*;
import SnakeGame.*;
import SnakeGame.Snake.*;
import SnakeGame.GameManager.*;

/**
 * Builder for GameManager
 * 
 * @author Diogo Fonseca a79858
 * @version 09/05/2024
 * 
 * @see GameManager
 */
public class GameManagerBuilder
{
	private boolean isFilled;
	private int foodScore;
	private int mapWidth;
	private int mapHeight;
	private Point relativeSnakePos;
	private Direction startingSnakeDir;
	private int snakeSize;
	private Point relativeFoodPos;
	private double foodSize;
	private FoodType foodType;
	private boolean isTextual;
	private UpdateMethod updateMethod;
	private ControlMethod controlMethod;
	private long seed;
	private GameManager gmInstance;

	/**
	 * Instantiates a new GameManagerBuilder
	 */
	public GameManagerBuilder()
	{
		GameManager.resetInstance();
		this.gmInstance = GameManager.getInstance();
		this.relativeSnakePos = null;
		this.startingSnakeDir = null;
		this.relativeFoodPos = null;
	}

	/**
	 * If the game should be drawn with filled shapes or
	 * just outlines
	 * @param isFilled if the game should be drawn with filled shapes
	 * @return the GameManagerBuilder instance
	 */
	public GameManagerBuilder setFilled(boolean isFilled)
	{
		this.isFilled = isFilled;
		return this;
	}

	/**
	 * Sets the ammount of score value a food gives for being eaten
	 * @param score the ammount of score a food gives for being eaten
	 * @return the GameManagerBuilder instance
	 */
	public GameManagerBuilder setFoodScore(int score)
	{
		this.foodScore = score;
		return this;
	}

	/**
	 * Sets the width of the map to play in
	 * Must be a multiple of the snake's size
	 * @param width the width of the map
	 * @return the GameManagerBuilder instance
	 */
	public GameManagerBuilder setMapWidth(int width)
	{
		this.mapWidth = width;
		return this;
	}

	/**
	 * Sets the height of the map to play in
	 * Must be a multiple of the snake's size
	 * @param height the height of the map
	 * @return the GameManagerBuilder instance
	 */
	public GameManagerBuilder setMapHeight(int height)
	{
		this.mapHeight = height;
		return this;
	}

	/**
	 * (OPTIONAL)
	 * Sets the initial snake position
	 * The snake position must go with the snake's size, having
	 * to choose a valid position on the map to be the center of the snake
	 *
	 * For example a size 3 snake can be positioned in 3n + 1.
	 * a snake of size 2 can be positioned in 2n + 0.5.
	 *
	 * If not set the snake position will be chosen at random
	 * @param pos the initial snake position (can be null)
	 * @return the GameManagerBuilder instance
	 */
	public GameManagerBuilder setSnakePos(Point pos)
	{
		this.relativeSnakePos = pos;
		return this;
	}

	/**
	 * (OPTIONAL)
	 * Sets the initial snake direction
	 * If not set the snake direction will be chosen at random
	 * @param dir the initial snake direction (can be null)
	 * @return the GameManagerBuilder instance
	 */
	public GameManagerBuilder setSnakeDir(Direction dir)
	{
		this.startingSnakeDir = dir;
		return this;
	}

	/**
	 * Sets the size of a snake unit
	 * @param size the size of the snake
	 * @return the GameManagerBuilder instance
	 */
	public GameManagerBuilder setSnakeSize(int size)
	{
		this.snakeSize = size;
		return this;
	}

	/**
	 * (OPTIONAL)
	 * Sets the initial food position
	 * The food position must go with the food's size, having
	 * to choose a valid position on the map to be the center of the food
	 *
	 * For example a size 3 food can be positioned in 3n + 1.
	 * a food of size 2 can be positioned in 2n + 0.5.
	 *
	 * If not set the food position will be chosen at random
	 * @param pos the position to spawn the food in
	 * @return the GameManagerBuilder instance
	 */
	public GameManagerBuilder setFoodPos(Point pos)
	{
		this.relativeFoodPos = pos;
		return this;
	}

	/**
	 * Sets the food size.
	 * Must be smaller or equal to the snake's size
	 * @param size the size of the food
	 * @return the GameManagerBuilder instance
	 */
	public GameManagerBuilder setFoodSize(double size)
	{
		this.foodSize = size;
		return this;
	}

	/**
	 * Sets the food type (circle or square)
	 * @param type the type of food
	 * @return the GameManagerBuilder instance
	 */
	public GameManagerBuilder setFoodType(FoodType type)
	{
		this.foodType = type;
		return this;
	}

	/**
	 * Sets the render method as textual/graphical
	 * @param isTextual if the render method should be textual
	 * @return the GameManagerBuilder instance
	 */
	public GameManagerBuilder setTextual(boolean isTextual)
	{
		this.isTextual = isTextual;
		return this;
	}

	/**
	 * Sets the update method for the game.
	 * (if each game cycle should be cycled automatically, through input or through code)
	 * @param method the update method
	 * @return the GameManagerBuilder instance
	 */
	public GameManagerBuilder setUpdateMethod(UpdateMethod method)
	{
		this.updateMethod = method;
		return this;
	}

	/**
	 * Sets the control method for the snake.
	 * (If it should be controlled automatically by an AI
	 * or manually by the player)
	 * @param method the control method for the snake
	 * @return the GameManagerBuilder instance
	 */
	public GameManagerBuilder setControlMethod(ControlMethod method)
	{
		this.controlMethod = method;
		return this;
	}

	/**
	 * (OPTIONAL)
	 * Sets a seed for random operations
	 * @param seed the seed to use for random operations
	 * @return the GameManagerBuilder instance
	 */
	public GameManagerBuilder setSeed(long seed)
	{
		this.seed = seed;
		return this;
	}

	/**
	 * (OPTIONAL)
	 * Adds a static obstacle to the game map.
	 * Can be called multiple times.
	 * @param obstacle the obstacle's shape
	 * @return the GameManagerBuilder instance
	 */
	public GameManagerBuilder addObstacle(Polygon obstacle)
	{
		this.gmInstance.addStaticObstacle(obstacle);
		return this;
	}

	/**
	 * (OPTIONAL)
	 * Adds a dynamic obstacle to the game.
	 * The obstacle will rotate around the anchor point with the given speed.
	 * Can be called multiple times.
	 * @param obstacle the obstacle's shape
	 * @param anchor the point to rotate the obstacle around (can be null)
	 * @param speed the speed the obstacle should rotate around the anchor
	 * @return the GameManagerBuilder instance
	 */
	public GameManagerBuilder addObstacle(Polygon obstacle, VirtualPoint anchor, float speed)
	{
		this.gmInstance.addDynamicObstacle(obstacle, anchor, speed);
		return this;
	}

	/**
	 * (OPTIONAL)
	 * Sets the map's draw character
	 * @param c the map's draw character
	 * @return the GameManagerBuilder instance
	 */
	public GameManagerBuilder setMapChar(char c)
	{
		this.gmInstance.setMap(c);
		return this;
	}

	/**
	 * (OPTIONAL)
	 * Sets the obstacle's draw character
	 * @param c the map's draw character
	 * @return the GameManagerBuilder instance
	 */
	public GameManagerBuilder setObstacleChar(char c)
	{
		this.gmInstance.setObstacle(c);
		return this;
	}

	/**
	 * (OPTIONAL)
	 * Sets the food's draw character
	 * @param c the food's draw character
	 * @return the GameManagerBuilder instance
	 */
	public GameManagerBuilder setFoodChar(char c)
	{
		this.gmInstance.setFood(c);
		return this;
	}

	/**
	 * (OPTIONAL)
	 * Sets the snake's head draw character
	 * @param c the snake's head draw character
	 * @return the GameManagerBuilder instance
	 */
	public GameManagerBuilder setSnakeHeadChar(char c)
	{
		this.gmInstance.setSnakeHead(c);
		return this;
	}

	/**
	 * (OPTIONAL)
	 * Sets the snake's tail draw character
	 * @param c the snake's tail draw character
	 * @return the GameManagerBuilder instance
	 */
	public GameManagerBuilder setSnakeTailChar(char c)
	{
		this.gmInstance.setSnakeTail(c);
		return this;
	}

	/**
	 * (OPTIONAL)
	 * Sets the max scores to display.
	 * If not set it will display as many scores as it can.
	 * @param maxScoresDisplay the max scores to display
	 * @return the GameManagerBuilder instance
	 */
	public GameManagerBuilder setMaxScoresDisplay(int maxScoresDisplay)
	{
		this.gmInstance.setMaxScoresDisplay(maxScoresDisplay);
		return this;
	}

	/**
	 * Sets a preset for the input recognized as controls for the snake
	 * @param inputPreset the preset to use as input
	 * @return the GameManagerBuilder instance
	 */
	public GameManagerBuilder setInputPreset(InputSnakeController.InputPreset inputPreset)
	{
		this.gmInstance.setInputPresetMethod(inputPreset);
		return this;
	}

	/**
	 * Sets the background colour for the rendered game
	 * @param colour the colour to use as background
	 * @return the GameManagerBuilder instance
	 */
	public GameManagerBuilder setBackgroundColour(Colour.Background colour)
	{
		this.gmInstance.setBackgroundColour(colour);
		return this;
	}

	/**
	 * Sets the snake's colour
	 * @param colour the snake's colour
	 * @return the GameManagerBuilder instance
	 */
	public GameManagerBuilder setSnakeColour(Colour.Foreground colour)
	{
		this.gmInstance.setSnakeColour(colour);
		return this;
	}

	/**
	 * Sets the food's colour
	 * @param colour the food's colour
	 * @return the GameManagerBuilder instance
	 */
	public GameManagerBuilder setFoodColour(Colour.Foreground colour)
	{
		this.gmInstance.setFoodColour(colour);
		return this;
	}

	/**
	 * Sets the obstacle's colour
	 * @param colour the obstacle's colour
	 * @return the GameManagerBuilder instance
	 */
	public GameManagerBuilder setObstaclesColour(Colour.Foreground colour)
	{
		this.gmInstance.setObstaclesColour(colour);
		return this;
	}

	/**
	 * Checks if the GameManager to be build has valid parameters
	 * @throws SnakeGameException if the parameters are not valid
	 */
	private void validate() throws SnakeGameException
	{
		if (this.foodType == null)
		{
			Logger.log(Logger.Level.FATAL, "Food type can't be null.");
			throw new SnakeGameException("Food type can't be null.");
		}
		if (this.updateMethod == null)
		{
			Logger.log(Logger.Level.FATAL, "Update method can't be null.");
			throw new SnakeGameException("Update method can't be null.");
		}
		if (this.controlMethod == null)
		{
			Logger.log(Logger.Level.FATAL, "Control method can't be null.");
			throw new SnakeGameException("Control method can't be null.");
		}
	}

	/**
	 * Builds the GameManager, and by consequence the game.
	 * @return the GameManager built
	 * @throws SnakeGameException if there were invalid parameters or initialization failed
	 */
	public GameManager build() throws SnakeGameException
	{
		validate();
		this.gmInstance.init(mapWidth, mapHeight, relativeSnakePos, startingSnakeDir,
			snakeSize, isFilled, relativeFoodPos, foodSize, foodType, foodScore,
			isTextual, updateMethod, controlMethod, seed);
		return gmInstance;
	}
}
