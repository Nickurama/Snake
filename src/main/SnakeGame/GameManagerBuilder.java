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

	public GameManagerBuilder()
	{
		GameManager.resetInstance();
		this.gmInstance = GameManager.getInstance();
		this.relativeSnakePos = null;
		this.startingSnakeDir = null;
		this.relativeFoodPos = null;
	}

	public GameManagerBuilder setFilled(boolean isFilled)
	{
		this.isFilled = isFilled;
		return this;
	}

	public GameManagerBuilder setFoodScore(int score)
	{
		this.foodScore = score;
		return this;
	}

	public GameManagerBuilder setMapWidth(int width)
	{
		this.mapWidth = width;
		return this;
	}

	public GameManagerBuilder setMapHeight(int height)
	{
		this.mapHeight = height;
		return this;
	}

	public GameManagerBuilder setSnakePos(Point pos)
	{
		this.relativeSnakePos = pos;
		return this;
	}

	public GameManagerBuilder setSnakeDir(Direction dir)
	{
		this.startingSnakeDir = dir;
		return this;
	}

	public GameManagerBuilder setSnakeSize(int size)
	{
		this.snakeSize = size;
		return this;
	}

	public GameManagerBuilder setFoodPos(Point pos)
	{
		this.relativeFoodPos = pos;
		return this;
	}

	public GameManagerBuilder setFoodSize(double size)
	{
		this.foodSize = size;
		return this;
	}

	public GameManagerBuilder setFoodType(FoodType type)
	{
		this.foodType = type;
		return this;
	}

	public GameManagerBuilder setTextual(boolean isTextual)
	{
		this.isTextual = isTextual;
		return this;
	}

	public GameManagerBuilder setUpdateMethod(UpdateMethod method)
	{
		this.updateMethod = method;
		return this;
	}

	public GameManagerBuilder setControlMethod(ControlMethod method)
	{
		this.controlMethod = method;
		return this;
	}

	public GameManagerBuilder setSeed(long seed)
	{
		this.seed = seed;
		return this;
	}

	public GameManagerBuilder addObstacle(Polygon obstacle)
	{
		this.gmInstance.addStaticObstacle(obstacle);
		return this;
	}

	public GameManagerBuilder addObstacle(Polygon obstacle, VirtualPoint anchor, float speed)
	{
		this.gmInstance.addDynamicObstacle(obstacle, anchor, speed);
		return this;
	}

	public GameManagerBuilder setMapChar(char c)
	{
		this.gmInstance.setMap(c);
		return this;
	}

	public GameManagerBuilder setObstacleChar(char c)
	{
		this.gmInstance.setObstacle(c);
		return this;
	}

	public GameManagerBuilder setFoodChar(char c)
	{
		this.gmInstance.setFood(c);
		return this;
	}

	public GameManagerBuilder setSnakeHeadChar(char c)
	{
		this.gmInstance.setSnakeHead(c);
		return this;
	}

	public GameManagerBuilder setSnakeTailChar(char c)
	{
		this.gmInstance.setSnakeTail(c);
		return this;
	}

	public GameManagerBuilder setMaxScoresDisplay(int maxScoresDisplay)
	{
		this.gmInstance.setMaxScoresDisplay(maxScoresDisplay);
		return this;
	}

	public GameManagerBuilder setInputPreset(InputSnakeController.InputPreset inputPreset)
	{
		this.gmInstance.setInputPresetMethod(inputPreset);
		return this;
	}

	public GameManagerBuilder setBackgroundColour(Colour.Background colour)
	{
		this.gmInstance.setBackgroundColour(colour);
		return this;
	}

	public GameManagerBuilder setSnakeColour(Colour.Foreground colour)
	{
		this.gmInstance.setSnakeColour(colour);
		return this;
	}

	public GameManagerBuilder setFoodColour(Colour.Foreground colour)
	{
		this.gmInstance.setFoodColour(colour);
		return this;
	}

	public GameManagerBuilder setObstaclesColour(Colour.Foreground colour)
	{
		this.gmInstance.setObstaclesColour(colour);
		return this;
	}

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

	public GameManager build() throws SnakeGameException
	{
		validate();
		this.gmInstance.init(mapWidth, mapHeight, relativeSnakePos, startingSnakeDir,
			snakeSize, isFilled, relativeFoodPos, foodSize, foodType, foodScore,
			isTextual, updateMethod, controlMethod, seed);
		return gmInstance;
	}
}
