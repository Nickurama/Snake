package SnakeGame;

import GameEngine.*;
import Geometry.*;
import TestUtil.*;

import org.junit.jupiter.api.Test;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;
import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.util.Arrays;

public class HighscoresOverlayTests
{
	private final String TEST_FILE = TestUtil.TEST_FILES_PATH + "testOverlay.ser";

	@Test
	public void ShouldSetHighscoresOverlay() throws GeometricException, GameEngineException, SnakeGameException
	{
		// Arrange
		Rectangle camera = new Rectangle(new Point[] { new Point(0, 0), new Point(0, 10), new Point(40, 10), new Point(40, 0)});
		HighscoresOverlay overlay = new HighscoresOverlay(Scoreboard.getInstance(), camera, '╔', '╗', '╚', '╝', '║', '═');

		Scoreboard.getInstance().setFile(TEST_FILE);
		Scoreboard.getInstance().purgeScores();
		Scoreboard.getInstance().addEntry(new Score("Jessica", LocalDate.of(2013, 7, 30), 55));
		Scoreboard.getInstance().addEntry(new Score("Luna", LocalDate.of(2022, 12, 03), 100));
		Scoreboard.getInstance().addEntry(new Score("OwO", LocalDate.of(2015, 9, 20), 79));
		Scoreboard.getInstance().addEntry(new Score("Luna", LocalDate.of(2012, 5, 13), 137));
		Scoreboard.getInstance().addEntry(new Score("Compl3x7", LocalDate.of(2005, 1, 9), 1));

		Scene sc = new Scene();

		GameEngineFlags flags = new GameEngineFlags();
		flags.setUpdateMethod(GameEngineFlags.UpdateMethod.CODE);

		GameEngine engine = GameEngine.getInstance();
		engine.init(flags, sc, camera);

		ByteArrayOutputStream out = TestUtil.setIOstreams("");
		String expected =	"╔═══════════════════════════════════════╗\n" +
							"║                                       ║\n" +
							"║              Highscores               ║\n" +
							"║                                       ║\n" +
							"║ 1. Luna      137  13/05/2012          ║\n" +
							"║ 2. Luna      100  03/12/2022          ║\n" +
							"║ 3. OwO       79   20/09/2015          ║\n" +
							"║ 4. Jessica   55   30/07/2013          ║\n" +
							"║ 5. Compl3x7  1    09/01/2005          ║\n" +
							"║                                       ║\n" +
							"╚═══════════════════════════════════════╝\n";

		// Act
		sc.add(overlay);
		engine.start();
		String render = out.toString();
		out.reset();

		// Assert
		assertEquals(expected, render);
	}

	@Test
	public void ShouldCapHighscoresIfReachEndOfScreen() throws GeometricException, GameEngineException, SnakeGameException
	{
		// Arrange
		Rectangle camera = new Rectangle(new Point[] { new Point(0, 0), new Point(0, 10), new Point(40, 10), new Point(40, 0)});
		HighscoresOverlay overlay = new HighscoresOverlay(Scoreboard.getInstance(), camera, '╔', '╗', '╚', '╝', '║', '═');

		Scoreboard.getInstance().setFile(TEST_FILE);
		Scoreboard.getInstance().purgeScores();
		Scoreboard.getInstance().addEntry(new Score("Jessica", LocalDate.of(2013, 7, 30), 55));
		Scoreboard.getInstance().addEntry(new Score("Luna", LocalDate.of(2022, 12, 03), 100));
		Scoreboard.getInstance().addEntry(new Score("OwO", LocalDate.of(2015, 9, 20), 79));
		Scoreboard.getInstance().addEntry(new Score("Luna", LocalDate.of(2012, 5, 13), 137));
		Scoreboard.getInstance().addEntry(new Score("Compl3x7", LocalDate.of(2005, 1, 9), 1));
		Scoreboard.getInstance().addEntry(new Score("Compl3x7", LocalDate.of(2005, 1, 9), 3));
		Scoreboard.getInstance().addEntry(new Score("Compl3x7", LocalDate.of(2005, 1, 9), 2));

		Scene sc = new Scene();

		GameEngineFlags flags = new GameEngineFlags();
		flags.setUpdateMethod(GameEngineFlags.UpdateMethod.CODE);

		GameEngine engine = GameEngine.getInstance();
		engine.init(flags, sc, camera);

		ByteArrayOutputStream out = TestUtil.setIOstreams("");
		String expected =	"╔═══════════════════════════════════════╗\n" +
							"║                                       ║\n" +
							"║              Highscores               ║\n" +
							"║                                       ║\n" +
							"║ 1. Luna      137  13/05/2012          ║\n" +
							"║ 2. Luna      100  03/12/2022          ║\n" +
							"║ 3. OwO       79   20/09/2015          ║\n" +
							"║ 4. Jessica   55   30/07/2013          ║\n" +
							"║ 5. Compl3x7  3    09/01/2005          ║\n" +
							"║                                       ║\n" +
							"╚═══════════════════════════════════════╝\n";

		// Act
		sc.add(overlay);
		engine.start();
		String render = out.toString();
		out.reset();

		// Assert
		assertEquals(expected, render);
	}

	@Test
	public void ShouldSwitchNumberOfHighscoreEntries() throws GeometricException, GameEngineException, SnakeGameException
	{
		// Arrange
		Rectangle camera = new Rectangle(new Point[] { new Point(0, 0), new Point(0, 10), new Point(40, 10), new Point(40, 0)});
		HighscoresOverlay overlay = new HighscoresOverlay(Scoreboard.getInstance(), camera, 2, '╔', '╗', '╚', '╝', '║', '═');

		Scoreboard.getInstance().setFile(TEST_FILE);
		Scoreboard.getInstance().purgeScores();
		Scoreboard.getInstance().addEntry(new Score("Jessica", LocalDate.of(2013, 7, 30), 55));
		Scoreboard.getInstance().addEntry(new Score("Luna", LocalDate.of(2022, 12, 03), 100));
		Scoreboard.getInstance().addEntry(new Score("OwO", LocalDate.of(2015, 9, 20), 79));
		Scoreboard.getInstance().addEntry(new Score("Luna", LocalDate.of(2012, 5, 13), 137));
		Scoreboard.getInstance().addEntry(new Score("Compl3x7", LocalDate.of(2005, 1, 9), 1));

		Scene sc = new Scene();

		GameEngineFlags flags = new GameEngineFlags();
		flags.setUpdateMethod(GameEngineFlags.UpdateMethod.CODE);

		GameEngine engine = GameEngine.getInstance();
		engine.init(flags, sc, camera);

		ByteArrayOutputStream out = TestUtil.setIOstreams("");
		String expected =	"╔═══════════════════════════════════════╗\n" +
							"║                                       ║\n" +
							"║          Highscores (Top 2)           ║\n" +
							"║                                       ║\n" +
							"║ 1. Luna  137  13/05/2012              ║\n" +
							"║ 2. Luna  100  03/12/2022              ║\n" +
							"║                                       ║\n" +
							"║                                       ║\n" +
							"║                                       ║\n" +
							"║                                       ║\n" +
							"╚═══════════════════════════════════════╝\n";

		// Act
		sc.add(overlay);
		engine.start();
		String render = out.toString();
		out.reset();

		// Assert
		assertEquals(expected, render);
	}
}

