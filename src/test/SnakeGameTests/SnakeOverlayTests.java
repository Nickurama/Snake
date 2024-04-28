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
// import java.util.Arrays;
//
// public class SnakeOverlayTests
// {
// 	@Test
// 	public void ShouldSetEmptyOverlayIfNotOverlaySet()
// 	{
// 		// Arrange
// 		SnakeOverlay overlay = new SnakeOverlay();
//
// 		Rectangle camera = new Rectangle(new Point[] { new Point(0, 0), new Point(0, 10), new Point(10, 10), new Point(10, 0)});
// 		Scene sc = new Scene();
// 		sc.add(overlay);
//
// 		// Act
// 		Character[][] overlayArray = sc.getOverlay();
//
// 		// Assert
// 		for (int i = 0; i < 10; i++)
// 			for (int j = 0; j < 10; j++)
// 				assertEquals(null, overlayArray[i][j]);
// 	}
//
// 	@Test
// 	public void ShouldSetOverlays()
// 	{
// 		// Arrange
// 		SnakeOverlay overlay = new SnakeOverlay();
//
// 		Rectangle camera = new Rectangle(new Point[] { new Point(0, 0), new Point(0, 10), new Point(10, 10), new Point(10, 0)});
// 		Scene sc = new Scene();
// 		sc.add(overlay);
//
// 		// Act
// 		overlay.setGameplayOverlay();
// 		Character[][] gameplayOverlay = sc.getOverlay();
// 		overlay.setLoseOverlay();
// 		Character[][] loseOverlay = sc.getOverlay();
//
// 		// Can be any overlay so we can only test if an overlay has been set at all
// 		boolean notNullGameplay = false;
// 		boolean notNullLose = false;
// 		for (int i = 0; i < 10; i++)
// 			for (int j = 0; j < 10; j++)
// 				if (gameplayOverlay[i][j] != null)
// 					notNullGameplay = true;
// 		for (int i = 0; i < 10; i++)
// 			for (int j = 0; j < 10; j++)
// 				if (gameplayOverlay[i][j] != null)
// 					notNullLose = true;
//
// 		// Assert
// 		assertTrue(notNullGameplay);
// 		assertTrue(notNullLose);
// 		assertFalse(Arrays.equals(gameplayOverlay, loseOverlay));
// 	}
// }
//
