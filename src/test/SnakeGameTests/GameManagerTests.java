// package SnakeGameTests;
//
// import SnakeGame.*;
// import GameEngine.*;
// import Geometry.*;
// import TestUtil.*;
//
// import org.junit.jupiter.api.Test;
// import static org.junit.Assert.assertThrows;
// import static org.junit.jupiter.api.Assertions.*;
// import java.io.ByteArrayOutputStream;
//
// public class GameManagerTests
// {
// 	@Test
// 	public void ShouldHavePlayingStateWhenStarting()
// 	{
// 		// Arrange
// 		GameManager game = GameManager.getInstance();
//
// 		// Act
// 		game.play();
//
// 		// Arrange
// 		assertEquals(GameManager.GameState.PLAYING, snake.direction());
// 	}
//
// 	@Test
// 	public void ShouldHaveGameOverStatusWhenSnakeDies()
// 	{
// 		// Arrange
// 		GameManager game = GameManager.getInstance();
//
// 		// Act
// 		game.play();
// 		Snake snek = game.getSnake();
// 		snek.die();
//
// 		// Arrange
// 		assertEquals(GameManager.GameState.GAMEOVER, snake.direction());
// 	}
// }
