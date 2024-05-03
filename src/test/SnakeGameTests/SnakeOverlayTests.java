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
// import java.time.LocalDate;
// import java.util.Arrays;
//
// public class SnakeOverlayTests
// {
// 	private final String TEST_FILE = TestUtil.TEST_FILES_PATH + "testOverlay.ser";
//
// 	@Test
// 	public void ShouldSetGameplayOverlay() throws GeometricException, GameEngineException
// 	{
// 		// Arrange
// 		Rectangle camera = new Rectangle(new Point[] { new Point(0, 0), new Point(0, 20), new Point(20, 20), new Point(20, 0)});
// 		SnakeOverlay overlay = new SnakeOverlay(camera, SnakeOverlay.OverlayType.GAMEPLAY);
// 		Scene sc = new Scene();
// 		sc.add(overlay);
//
// 		GameEngineFlags flags = new GameEngineFlags();
// 		flags.setUpdateMethod(GameEngineFlags.UpdateMethod.CODE);
// 		flags.setRasterized(true);
//
// 		GameEngine engine = GameEngine.getInstance();
// 		engine.init(flags, sc, camera);
// 		engine.start();
//
// 		ByteArrayOutputStream out = TestUtil.setIOstreams("");
// 		String expected =	"╔═══════════════════╗\n" +
// 							"║                   ║\n" +
// 							"║                   ║\n" +
// 							"║                   ║\n" +
// 							"║                   ║\n" +
// 							"║                   ║\n" +
// 							"║                   ║\n" +
// 							"║                   ║\n" +
// 							"║                   ║\n" +
// 							"║                   ║\n" +
// 							"║                   ║\n" +
// 							"║                   ║\n" +
// 							"║                   ║\n" +
// 							"║                   ║\n" +
// 							"║                   ║\n" +
// 							"║                   ║\n" +
// 							"║                   ║\n" +
// 							"║                   ║\n" +
// 							"║                   ║\n" +
// 							"║Dir: 0     Score: 0║\n" +
// 							"╚═══════════════════╝\n";
//
// 		// Act
// 		engine.step();
// 		String render = out.toString();
// 		out.reset();
//
// 		// Assert
// 		assertEquals(expected, render);
// 	}
//
// 	@Test
// 	public void ShouldSetGameOverOverlay() throws GeometricException, GameEngineException
// 	{
// 		// Arrange
// 		Rectangle camera = new Rectangle(new Point[] { new Point(0, 0), new Point(0, 10), new Point(40, 10), new Point(40, 0)});
// 		SnakeOverlay overlay = new SnakeOverlay(camera, SnakeOverlay.OverlayType.GAMEOVER);
// 		Scene sc = new Scene();
// 		sc.add(overlay);
//
// 		GameEngineFlags flags = new GameEngineFlags();
// 		flags.setUpdateMethod(GameEngineFlags.UpdateMethod.CODE);
// 		flags.setRasterized(true);
//
// 		GameEngine engine = GameEngine.getInstance();
// 		engine.init(flags, sc, camera);
// 		engine.start();
//
// 		ByteArrayOutputStream out = TestUtil.setIOstreams("");
// 		String expected =	"╔═══════════════════════════════════════╗\n" +
// 							"║                                       ║\n" +
// 							"║                                       ║\n" +
// 							"║              Game Over!               ║\n" +
// 							"║                                       ║\n" +
// 							"║               Score: 60               ║\n" +
// 							"║                                       ║\n" +
// 							"║          What is your name?           ║\n" +
// 							"║                                       ║\n" +
// 							"║                                       ║\n" +
// 							"╚═══════════════════════════════════════╝\n";
//
// 		// Act
// 		engine.step();
// 		String render = out.toString();
// 		out.reset();
//
// 		// Assert
// 		assertEquals(expected, render);
// 	}
//
// 	@Test
// 	public void ShouldSetHighscoresOverlay() throws GeometricException, GameEngineException, SnakeGameException
// 	{
// 		// Arrange
// 		Rectangle camera = new Rectangle(new Point[] { new Point(0, 0), new Point(0, 10), new Point(40, 10), new Point(40, 0)});
// 		SnakeOverlay overlay = new SnakeOverlay(camera, SnakeOverlay.OverlayType.HIGHSCORES);
//
// 		Scoreboard.getInstance().setFile(TEST_FILE);
// 		Scoreboard.getInstance().purgeScores();
// 		Scoreboard.getInstance().addEntry(new Score("Jessica", LocalDate.of(2013, 7, 30), 55));
// 		Scoreboard.getInstance().addEntry(new Score("Luna", LocalDate.of(2022, 12, 03), 100));
// 		Scoreboard.getInstance().addEntry(new Score("OwO", LocalDate.of(2015, 9, 20), 79));
// 		Scoreboard.getInstance().addEntry(new Score("Luna", LocalDate.of(2012, 5, 13), 137));
// 		Scoreboard.getInstance().addEntry(new Score("Compl3x7", LocalDate.of(2005, 1, 9), 1));
//
// 		Scene sc = new Scene();
// 		sc.add(overlay);
//
// 		GameEngineFlags flags = new GameEngineFlags();
// 		flags.setUpdateMethod(GameEngineFlags.UpdateMethod.CODE);
// 		flags.setRasterized(true);
//
// 		GameEngine engine = GameEngine.getInstance();
// 		engine.init(flags, sc, camera);
// 		engine.start();
//
// 		ByteArrayOutputStream out = TestUtil.setIOstreams("");
// 		String expected =	"╔═══════════════════════════════════════╗\n" +
// 							"║                                       ║\n" +
// 							"║              Highscores               ║\n" +
// 							"║                                       ║\n" +
// 							"║ 1. Luna      137  13/05/2012          ║\n" +
// 							"║ 2. Luna      100  03/12/2022          ║\n" +
// 							"║ 3. OwO       79   20/09/2015          ║\n" +
// 							"║ 4. Jessica   55   30/07/2013          ║\n" +
// 							"║ 5. Compl3x7  1    09/01/2005          ║\n" +
// 							"║                                       ║\n" +
// 							"╚═══════════════════════════════════════╝\n";
//
// 		// Act
// 		engine.step();
// 		String render = out.toString();
// 		out.reset();
//
// 		// Assert
// 		assertEquals(expected, render);
// 	}
//
// 	@Test
// 	public void ShouldCapHighscoresIfReachEndOfScreen() throws GeometricException, GameEngineException, SnakeGameException
// 	{
// 		// Arrange
// 		Rectangle camera = new Rectangle(new Point[] { new Point(0, 0), new Point(0, 10), new Point(40, 10), new Point(40, 0)});
// 		SnakeOverlay overlay = new SnakeOverlay(camera, SnakeOverlay.OverlayType.HIGHSCORES);
//
// 		Scoreboard.getInstance().setFile(TEST_FILE);
// 		Scoreboard.getInstance().purgeScores();
// 		Scoreboard.getInstance().addEntry(new Score("Jessica", LocalDate.of(2013, 7, 30), 55));
// 		Scoreboard.getInstance().addEntry(new Score("Luna", LocalDate.of(2022, 12, 03), 100));
// 		Scoreboard.getInstance().addEntry(new Score("OwO", LocalDate.of(2015, 9, 20), 79));
// 		Scoreboard.getInstance().addEntry(new Score("Luna", LocalDate.of(2012, 5, 13), 137));
// 		Scoreboard.getInstance().addEntry(new Score("Compl3x7", LocalDate.of(2005, 1, 9), 1));
// 		Scoreboard.getInstance().addEntry(new Score("Compl3x7", LocalDate.of(2005, 1, 9), 3));
// 		Scoreboard.getInstance().addEntry(new Score("Compl3x7", LocalDate.of(2005, 1, 9), 2));
//
// 		Scene sc = new Scene();
// 		sc.add(overlay);
//
// 		GameEngineFlags flags = new GameEngineFlags();
// 		flags.setUpdateMethod(GameEngineFlags.UpdateMethod.CODE);
// 		flags.setRasterized(true);
//
// 		GameEngine engine = GameEngine.getInstance();
// 		engine.init(flags, sc, camera);
// 		engine.start();
//
// 		ByteArrayOutputStream out = TestUtil.setIOstreams("");
// 		String expected =	"╔═══════════════════════════════════════╗\n" +
// 							"║                                       ║\n" +
// 							"║              Highscores               ║\n" +
// 							"║                                       ║\n" +
// 							"║ 1. Luna      137  13/05/2012          ║\n" +
// 							"║ 2. Luna      100  03/12/2022          ║\n" +
// 							"║ 3. OwO       79   20/09/2015          ║\n" +
// 							"║ 4. Jessica   55   30/07/2013          ║\n" +
// 							"║ 5. Compl3x7  3    09/01/2005          ║\n" +
// 							"║                                       ║\n" +
// 							"╚═══════════════════════════════════════╝\n";
//
// 		// Act
// 		engine.step();
// 		String render = out.toString();
// 		out.reset();
//
// 		// Assert
// 		assertEquals(expected, render);
// 	}
//
// 	@Test
// 	public void ShouldSwitchNumberOfHighscoreEntries() throws GeometricException, GameEngineException, SnakeGameException
// 	{
// 		// Arrange
// 		Rectangle camera = new Rectangle(new Point[] { new Point(0, 0), new Point(0, 10), new Point(40, 10), new Point(40, 0)});
// 		SnakeOverlay overlay = new SnakeOverlay(camera, SnakeOverlay.OverlayType.HIGHSCORES);
//
// 		Scoreboard.getInstance().setFile(TEST_FILE);
// 		Scoreboard.getInstance().purgeScores();
// 		Scoreboard.getInstance().addEntry(new Score("Jessica", LocalDate.of(2013, 7, 30), 55));
// 		Scoreboard.getInstance().addEntry(new Score("Luna", LocalDate.of(2022, 12, 03), 100));
// 		Scoreboard.getInstance().addEntry(new Score("OwO", LocalDate.of(2015, 9, 20), 79));
// 		Scoreboard.getInstance().addEntry(new Score("Luna", LocalDate.of(2012, 5, 13), 137));
// 		Scoreboard.getInstance().addEntry(new Score("Compl3x7", LocalDate.of(2005, 1, 9), 1));
//
// 		Scene sc = new Scene();
// 		sc.add(overlay);
//
// 		GameEngineFlags flags = new GameEngineFlags();
// 		flags.setUpdateMethod(GameEngineFlags.UpdateMethod.CODE);
// 		flags.setRasterized(true);
//
// 		GameEngine engine = GameEngine.getInstance();
// 		engine.init(flags, sc, camera);
// 		engine.start();
//
// 		ByteArrayOutputStream out = TestUtil.setIOstreams("");
// 		String expected =	"╔═══════════════════════════════════════╗\n" +
// 							"║                                       ║\n" +
// 							"║          Highscores (Top 2)           ║\n" +
// 							"║                                       ║\n" +
// 							"║ 1. Luna  137  13/05/2012              ║\n" +
// 							"║ 2. Luna  100  03/12/2022              ║\n" +
// 							"║                                       ║\n" +
// 							"║                                       ║\n" +
// 							"║                                       ║\n" +
// 							"║                                       ║\n" +
// 							"╚═══════════════════════════════════════╝\n";
//
// 		// Act
// 		overlay.setMaxHighscoreEntries(2);
// 		engine.step();
// 		String render = out.toString();
// 		out.reset();
//
// 		// Assert
// 		assertEquals(expected, render);
// 	}
//
// 	@Test
// 	public void ShouldSwitchOverlay() throws GeometricException, GameEngineException, SnakeGameException
// 	{
// 		// Arrange
// 		Rectangle camera = new Rectangle(new Point[] { new Point(0, 0), new Point(0, 10), new Point(40, 10), new Point(40, 0)});
// 		SnakeOverlay overlay = new SnakeOverlay(camera, SnakeOverlay.OverlayType.GAMEOVER);
//
// 		Scoreboard.getInstance().setFile(TEST_FILE);
// 		Scoreboard.getInstance().purgeScores();
// 		Scoreboard.getInstance().addEntry(new Score("Jessica", LocalDate.of(2013, 7, 30), 55));
// 		Scoreboard.getInstance().addEntry(new Score("Luna", LocalDate.of(2022, 12, 03), 100));
// 		Scoreboard.getInstance().addEntry(new Score("OwO", LocalDate.of(2015, 9, 20), 79));
// 		Scoreboard.getInstance().addEntry(new Score("Luna", LocalDate.of(2012, 5, 13), 137));
// 		Scoreboard.getInstance().addEntry(new Score("Compl3x7", LocalDate.of(2005, 1, 9), 1));
//
// 		Scene sc = new Scene();
// 		sc.add(overlay);
//
// 		GameEngineFlags flags = new GameEngineFlags();
// 		flags.setUpdateMethod(GameEngineFlags.UpdateMethod.CODE);
// 		flags.setRasterized(true);
//
// 		GameEngine engine = GameEngine.getInstance();
// 		engine.init(flags, sc, camera);
// 		engine.start();
//
// 		ByteArrayOutputStream out = TestUtil.setIOstreams("");
// 		String expected0 =	"╔═══════════════════════════════════════╗\n" +
// 							"║                                       ║\n" +
// 							"║                                       ║\n" +
// 							"║              Game Over!               ║\n" +
// 							"║                                       ║\n" +
// 							"║               Score: 60               ║\n" +
// 							"║                                       ║\n" +
// 							"║          What is your name?           ║\n" +
// 							"║                                       ║\n" +
// 							"║                                       ║\n" +
// 							"╚═══════════════════════════════════════╝\n";
//
// 		String expected1 =	"╔═══════════════════════════════════════╗\n" +
// 							"║                                       ║\n" +
// 							"║              Highscores               ║\n" +
// 							"║                                       ║\n" +
// 							"║ 1. Luna      137  13/05/2012          ║\n" +
// 							"║ 2. Luna      100  03/12/2022          ║\n" +
// 							"║ 3. OwO       79   20/09/2015          ║\n" +
// 							"║ 4. Jessica   55   30/07/2013          ║\n" +
// 							"║ 5. Compl3x7  1    09/01/2005          ║\n" +
// 							"║                                       ║\n" +
// 							"╚═══════════════════════════════════════╝\n";
//
// 		// Act
// 		engine.step();
// 		String render0 = out.toString();
// 		out.reset();
//
// 		overlay.setOverlayType(SnakeOverlay.OverlayType.HIGHSCORES);
//
// 		engine.step();
// 		String render1 = out.toString();
// 		out.reset();
//
// 		// Assert
// 		assertEquals(expected0, render0);
// 		assertEquals(expected1, render1);
// 	}
//
// 	@Test
// 	public void ShouldNotShowAnythingUnderGameOverOverlay() throws GameEngineException, GeometricException
// 	{
// 		// Arrange
// 		Rectangle camera = new Rectangle(new Point[] { new Point(0, 0), new Point(0, 10), new Point(40, 10), new Point(40, 0)});
// 		StaticObstacle obstacle = new StaticObstacle(camera, true, 'x');
// 		SnakeOverlay overlay = new SnakeOverlay(camera, SnakeOverlay.OverlayType.GAMEOVER);
// 		Scene sc = new Scene();
// 		sc.add(overlay);
// 		sc.add(obstacle);
//
// 		GameEngineFlags flags = new GameEngineFlags();
// 		flags.setUpdateMethod(GameEngineFlags.UpdateMethod.CODE);
// 		flags.setRasterized(true);
//
// 		GameEngine engine = GameEngine.getInstance();
// 		engine.init(flags, sc, camera);
// 		engine.start();
//
// 		ByteArrayOutputStream out = TestUtil.setIOstreams("");
// 		String expected =	"╔═══════════════════════════════════════╗\n" +
// 							"║                                       ║\n" +
// 							"║                                       ║\n" +
// 							"║              Game Over!               ║\n" +
// 							"║                                       ║\n" +
// 							"║               Score: 60               ║\n" +
// 							"║                                       ║\n" +
// 							"║          What is your name?           ║\n" +
// 							"║                                       ║\n" +
// 							"║                                       ║\n" +
// 							"╚═══════════════════════════════════════╝\n";
//
// 		// Act
// 		engine.step();
// 		String render = out.toString();
// 		out.reset();
//
// 		// Assert
// 		assertEquals(expected, render);
// 	}
//
// 	@Test
// 	public void ShouldSwitchGameplayVariables() throws GeometricException, GameEngineException
// 	{
// 		throw new Error();
// 	}
// }
//
