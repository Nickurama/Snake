package SnakeGameTests;

import SnakeGame.*;
import GameEngine.*;
import Geometry.*;
import TestUtil.*;

import org.junit.jupiter.api.Test;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;
import java.io.ByteArrayOutputStream;
import java.util.Arrays;

public class SnakeOverlayTests
{
	@Test
	public void ShouldSetGameplayOverlay() throws GeometricException, GameEngineException
	{
		// Arrange
		Rectangle camera = new Rectangle(new Point[] { new Point(0, 0), new Point(0, 20), new Point(20, 20), new Point(20, 0)});
		SnakeOverlay overlay = new SnakeOverlay(camera, SnakeOverlay.OverlayType.GAMEPLAY);
		Scene sc = new Scene();
		sc.add(overlay);

		GameEngineFlags flags = new GameEngineFlags();
		flags.setUpdateMethod(GameEngineFlags.UpdateMethod.CODE);
		flags.setRasterized(true);

		GameEngine engine = GameEngine.getInstance();
		engine.init(flags, sc, camera);
		engine.start();

		ByteArrayOutputStream out = TestUtil.setIOstreams("");
		String expected =	"╔═══════════════════╗\n" +
							"║                   ║\n" +
							"║                   ║\n" +
							"║                   ║\n" +
							"║                   ║\n" +
							"║                   ║\n" +
							"║                   ║\n" +
							"║                   ║\n" +
							"║                   ║\n" +
							"║                   ║\n" +
							"║                   ║\n" +
							"║                   ║\n" +
							"║                   ║\n" +
							"║                   ║\n" +
							"║                   ║\n" +
							"║                   ║\n" +
							"║                   ║\n" +
							"║                   ║\n" +
							"║                   ║\n" +
							"║Dir: 0    Score: 0 ║\n" +
							"╚═══════════════════╝\n";

		// Act
		engine.step();
		String render = out.toString();
		out.reset();

		// Assert
		assertEquals(expected, render);
	}

	@Test
	public void ShouldSetLoseOverlay()
	{
		throw new Error();
	}

	@Test
	public void ShouldSwitchOverlay()
	{
		throw new Error();
	}
}

