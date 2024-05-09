package GameEngine;

import java.io.ByteArrayOutputStream;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import Geometry.*;
import TestUtil.*;

public class TextOverlayTests
{
	@Test
	public void ShouldSetBorders() throws GeometricException, GameEngineException
	{
		// Arrange
		Rectangle camera = new Rectangle(new Point[] { new Point(0, 0), new Point(0, 10), new Point(40, 10), new Point(40, 0)});
		TextOverlay overlay = new TextOverlay(camera);
		Scene sc = new Scene();
		sc.add(overlay);

		GameEngineFlags flags = new GameEngineFlags();
		flags.setUpdateMethod(GameEngineFlags.UpdateMethod.CODE);

		GameEngine engine = GameEngine.getInstance();
		engine.init(flags, sc, camera);
		engine.start();

		ByteArrayOutputStream out = TestUtil.setIOstreams("");
		String expected =	"╔═══════════════════════════════════════╗\n" +
							"║                                       ║\n" +
							"║                                       ║\n" +
							"║                                       ║\n" +
							"║                                       ║\n" +
							"║                                       ║\n" +
							"║                                       ║\n" +
							"║                                       ║\n" +
							"║                                       ║\n" +
							"║                                       ║\n" +
							"╚═══════════════════════════════════════╝\n";

		// Act
		overlay.setOutline('╔', '╗', '╚', '╝', '║', '═');

		engine.step();
		String render = out.toString();
		out.reset();

		// Assert
		assertEquals(expected, render);
	}

	@Test
	public void ShouldSetBordersFromTextOverlayBorder() throws GeometricException, GameEngineException
	{
		// Arrange
		Rectangle camera = new Rectangle(new Point[] { new Point(0, 0), new Point(0, 10), new Point(40, 10), new Point(40, 0)});
		TextOverlay overlay = new TextOverlay(camera);
		Scene sc = new Scene();
		sc.add(overlay);

		GameEngineFlags flags = new GameEngineFlags();
		flags.setUpdateMethod(GameEngineFlags.UpdateMethod.CODE);

		GameEngine engine = GameEngine.getInstance();
		engine.init(flags, sc, camera);
		engine.start();

		ByteArrayOutputStream out = TestUtil.setIOstreams("");
		String expected =	"╔═══════════════════════════════════════╗\n" +
							"║                                       ║\n" +
							"║                                       ║\n" +
							"║                                       ║\n" +
							"║                                       ║\n" +
							"║                                       ║\n" +
							"║                                       ║\n" +
							"║                                       ║\n" +
							"║                                       ║\n" +
							"║                                       ║\n" +
							"╚═══════════════════════════════════════╝\n";

		TextOverlayOutline outline = new TextOverlayOutline();

		// Act
		overlay.setOutline(outline);

		engine.step();
		String render = out.toString();
		out.reset();

		// Assert
		assertEquals(expected, render);
	}

	@Test
	public void ShouldReset() throws GeometricException, GameEngineException
	{
		// Arrange
		Rectangle camera = new Rectangle(new Point[] { new Point(0, 0), new Point(0, 10), new Point(40, 10), new Point(40, 0)});
		TextOverlay overlay = new TextOverlay(camera);
		Scene sc = new Scene();
		sc.add(overlay);

		GameEngineFlags flags = new GameEngineFlags();
		flags.setUpdateMethod(GameEngineFlags.UpdateMethod.CODE);

		GameEngine engine = GameEngine.getInstance();
		engine.init(flags, sc, camera);
		engine.start();

		ByteArrayOutputStream out = TestUtil.setIOstreams("");
		String expected =	"                                         \n" +
							"                                         \n" +
							"                                         \n" +
							"                                         \n" +
							"                                         \n" +
							"                                         \n" +
							"                                         \n" +
							"                                         \n" +
							"                                         \n" +
							"                                         \n" +
							"                                         \n";

		overlay.setOutline('╔', '╗', '╚', '╝', '║', '═');
		overlay.reset();
		// Act

		engine.step();
		String render = out.toString();
		out.reset();

		// Assert
		assertEquals(expected, render);
	}

	@Test
	public void ShouldFill() throws GeometricException, GameEngineException
	{
		// Arrange
		Rectangle camera = new Rectangle(new Point[] { new Point(0, 0), new Point(0, 10), new Point(40, 10), new Point(40, 0)});
		TextOverlay overlay = new TextOverlay(camera);
		overlay.setOutline('╔', '╗', '╚', '╝', '║', '═');

		Scene sc = new Scene();
		sc.add(overlay);

		GameEngineFlags flags = new GameEngineFlags();
		flags.setUpdateMethod(GameEngineFlags.UpdateMethod.CODE);

		GameEngine engine = GameEngine.getInstance();
		engine.init(flags, sc, camera);
		engine.start();

		ByteArrayOutputStream out = TestUtil.setIOstreams("");
		String expected =	"╔═══════════════════════════════════════╗\n" +
							"║xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx║\n" +
							"║xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx║\n" +
							"║xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx║\n" +
							"║xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx║\n" +
							"║xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx║\n" +
							"║xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx║\n" +
							"║xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx║\n" +
							"║xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx║\n" +
							"║xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx║\n" +
							"╚═══════════════════════════════════════╝\n";

		// Act
		overlay.fill('x');

		engine.step();
		String render = out.toString();
		out.reset();

		// Assert
		assertEquals(expected, render);
	}

	@Test
	public void ShouldWriteCentered() throws GeometricException, GameEngineException
	{
		// Arrange
		Rectangle camera = new Rectangle(new Point[] { new Point(0, 0), new Point(0, 10), new Point(40, 10), new Point(40, 0)});
		TextOverlay overlay = new TextOverlay(camera);
		overlay.setOutline('╔', '╗', '╚', '╝', '║', '═');

		Scene sc = new Scene();
		sc.add(overlay);

		GameEngineFlags flags = new GameEngineFlags();
		flags.setUpdateMethod(GameEngineFlags.UpdateMethod.CODE);

		GameEngine engine = GameEngine.getInstance();
		engine.init(flags, sc, camera);
		engine.start();

		ByteArrayOutputStream out = TestUtil.setIOstreams("");
		String expected =	"╔═══════════════════════════════════════╗\n" +
							"║                                       ║\n" +
							"║                                       ║\n" +
							"║             Centered text             ║\n" +
							"║                                       ║\n" +
							"║                                       ║\n" +
							"║                                       ║\n" +
							"║                                       ║\n" +
							"║                                       ║\n" +
							"║                                       ║\n" +
							"╚═══════════════════════════════════════╝\n";

		// Act
		overlay.writeCentered("Centered text", 3);

		engine.step();
		String render = out.toString();
		out.reset();

		// Assert
		assertEquals(expected, render);
	}

	@Test
	public void ShouldNotWriteWhenNoSpaceOnWriteCentered() throws GeometricException, GameEngineException
	{
		// Arrange
		Rectangle camera = new Rectangle(new Point[] { new Point(0, 0), new Point(0, 10), new Point(40, 10), new Point(40, 0)});
		TextOverlay overlay = new TextOverlay(camera);
		overlay.setOutline('╔', '╗', '╚', '╝', '║', '═');

		Scene sc = new Scene();
		sc.add(overlay);

		GameEngineFlags flags = new GameEngineFlags();
		flags.setUpdateMethod(GameEngineFlags.UpdateMethod.CODE);

		GameEngine engine = GameEngine.getInstance();
		engine.init(flags, sc, camera);
		engine.start();

		ByteArrayOutputStream out = TestUtil.setIOstreams("");
		String expected =	"╔═══════════════════════════════════════╗\n" +
							"║                                       ║\n" +
							"║                                       ║\n" +
							"║                                       ║\n" +
							"║                                       ║\n" +
							"║                                       ║\n" +
							"║                                       ║\n" +
							"║                                       ║\n" +
							"║                                       ║\n" +
							"║                                       ║\n" +
							"╚═══════════════════════════════════════╝\n";

		// Act
		overlay.writeCentered("Too large of a text to display in the overlay", 3);

		engine.step();
		String render = out.toString();
		out.reset();

		// Assert
		assertEquals(expected, render);
	}

	@Test
	public void ShouldWriteRight() throws GeometricException, GameEngineException
	{
		// Arrange
		Rectangle camera = new Rectangle(new Point[] { new Point(0, 0), new Point(0, 10), new Point(40, 10), new Point(40, 0)});
		TextOverlay overlay = new TextOverlay(camera);
		overlay.setOutline('╔', '╗', '╚', '╝', '║', '═');

		Scene sc = new Scene();
		sc.add(overlay);

		GameEngineFlags flags = new GameEngineFlags();
		flags.setUpdateMethod(GameEngineFlags.UpdateMethod.CODE);

		GameEngine engine = GameEngine.getInstance();
		engine.init(flags, sc, camera);
		engine.start();

		ByteArrayOutputStream out = TestUtil.setIOstreams("");
		String expected =	"╔═══════════════════════════════════════╗\n" +
							"║                                       ║\n" +
							"║                                       ║\n" +
							"║                                       ║\n" +
							"║                                       ║\n" +
							"║                                       ║\n" +
							"║                                       ║\n" +
							"║                                       ║\n" +
							"║                            Sample text║\n" +
							"║                                       ║\n" +
							"╚═══════════════════════════════════════╝\n";

		// Act
		overlay.writeRight("Sample text", 8);

		engine.step();
		String render = out.toString();
		out.reset();

		// Assert
		assertEquals(expected, render);
	}

	@Test
	public void ShouldNotWriteWhenNoSpaceOnWriteRight() throws GeometricException, GameEngineException
	{
		// Arrange
		Rectangle camera = new Rectangle(new Point[] { new Point(0, 0), new Point(0, 10), new Point(40, 10), new Point(40, 0)});
		TextOverlay overlay = new TextOverlay(camera);
		overlay.setOutline('╔', '╗', '╚', '╝', '║', '═');

		Scene sc = new Scene();
		sc.add(overlay);

		GameEngineFlags flags = new GameEngineFlags();
		flags.setUpdateMethod(GameEngineFlags.UpdateMethod.CODE);

		GameEngine engine = GameEngine.getInstance();
		engine.init(flags, sc, camera);

		ByteArrayOutputStream out = TestUtil.setIOstreams("");
		engine.start();
		out.reset();

		String expected =	"╔═══════════════════════════════════════╗\n" +
							"║                                       ║\n" +
							"║                                       ║\n" +
							"║                                       ║\n" +
							"║                                       ║\n" +
							"║                                       ║\n" +
							"║                                       ║\n" +
							"║                                       ║\n" +
							"║                                       ║\n" +
							"║                                       ║\n" +
							"╚═══════════════════════════════════════╝\n";

		// Act
		overlay.writeRight("A string too long for the current screen", 8);

		engine.step();
		String render = out.toString();
		out.reset();

		// Assert
		assertEquals(expected, render);
	}

	@Test
	public void ShouldWriteLeft() throws GeometricException, GameEngineException
	{
		// Arrange
		Rectangle camera = new Rectangle(new Point[] { new Point(0, 0), new Point(0, 10), new Point(40, 10), new Point(40, 0)});
		TextOverlay overlay = new TextOverlay(camera);
		overlay.setOutline('╔', '╗', '╚', '╝', '║', '═');

		Scene sc = new Scene();
		sc.add(overlay);

		GameEngineFlags flags = new GameEngineFlags();
		flags.setUpdateMethod(GameEngineFlags.UpdateMethod.CODE);

		GameEngine engine = GameEngine.getInstance();
		engine.init(flags, sc, camera);
		engine.start();

		ByteArrayOutputStream out = TestUtil.setIOstreams("");
		String expected =	"╔═══════════════════════════════════════╗\n" +
							"║                                       ║\n" +
							"║                                       ║\n" +
							"║                                       ║\n" +
							"║                                       ║\n" +
							"║                                       ║\n" +
							"║                                       ║\n" +
							"║                                       ║\n" +
							"║Sample text                            ║\n" +
							"║                                       ║\n" +
							"╚═══════════════════════════════════════╝\n";

		// Act
		overlay.writeLeft("Sample text", 8);

		engine.step();
		String render = out.toString();
		out.reset();

		// Assert
		assertEquals(expected, render);
	}

	@Test
	public void ShouldNotWriteWhenNoSpaceOnWriteLeft() throws GeometricException, GameEngineException
	{
		// Arrange
		Rectangle camera = new Rectangle(new Point[] { new Point(0, 0), new Point(0, 10), new Point(40, 10), new Point(40, 0)});
		TextOverlay overlay = new TextOverlay(camera);
		overlay.setOutline('╔', '╗', '╚', '╝', '║', '═');

		Scene sc = new Scene();
		sc.add(overlay);

		GameEngineFlags flags = new GameEngineFlags();
		flags.setUpdateMethod(GameEngineFlags.UpdateMethod.CODE);

		GameEngine engine = GameEngine.getInstance();
		engine.init(flags, sc, camera);

		ByteArrayOutputStream out = TestUtil.setIOstreams("");
		engine.start();
		out.reset();

		String expected =	"╔═══════════════════════════════════════╗\n" +
							"║                                       ║\n" +
							"║                                       ║\n" +
							"║                                       ║\n" +
							"║                                       ║\n" +
							"║                                       ║\n" +
							"║                                       ║\n" +
							"║                                       ║\n" +
							"║                                       ║\n" +
							"║                                       ║\n" +
							"╚═══════════════════════════════════════╝\n";

		// Act
		overlay.writeLeft("A string too long for the current screen", 8);

		engine.step();
		String render = out.toString();
		out.reset();

		// Assert
		assertEquals(expected, render);
	}

	@Test
	public void ShouldWriteParagraph() throws GeometricException, GameEngineException
	{
		// Arrange
		Rectangle camera = new Rectangle(new Point[] { new Point(0, 0), new Point(0, 10), new Point(40, 10), new Point(40, 0)});
		TextOverlay overlay = new TextOverlay(camera);
		overlay.setOutline('╔', '╗', '╚', '╝', '║', '═');

		Scene sc = new Scene();
		sc.add(overlay);

		GameEngineFlags flags = new GameEngineFlags();
		flags.setUpdateMethod(GameEngineFlags.UpdateMethod.CODE);

		GameEngine engine = GameEngine.getInstance();
		engine.init(flags, sc, camera);
		engine.start();

		ByteArrayOutputStream out = TestUtil.setIOstreams("");
		String expected =	"╔═══════════════════════════════════════╗\n" +
							"║                                       ║\n" +
							"║                                       ║\n" +
							"║                                       ║\n" +
							"║                                       ║\n" +
							"║Really long string of text that will ge║\n" +
							"║t fragmented into multiple lines automa║\n" +
							"║tically                                ║\n" +
							"║                                       ║\n" +
							"║                                       ║\n" +
							"╚═══════════════════════════════════════╝\n";

		// Act
		overlay.writeParagraph("Really long string of text that will get fragmented into multiple lines automatically", 5);

		engine.step();
		String render = out.toString();
		out.reset();

		// Assert
		assertEquals(expected, render);
	}

	@Test
	public void ShouldStopWritingWhenReachedEndOnWriteParagraph() throws GeometricException, GameEngineException
	{
		// Arrange
		Rectangle camera = new Rectangle(new Point[] { new Point(0, 0), new Point(0, 10), new Point(40, 10), new Point(40, 0)});
		TextOverlay overlay = new TextOverlay(camera);
		overlay.setOutline('╔', '╗', '╚', '╝', '║', '═');

		Scene sc = new Scene();
		sc.add(overlay);

		GameEngineFlags flags = new GameEngineFlags();
		flags.setUpdateMethod(GameEngineFlags.UpdateMethod.CODE);

		GameEngine engine = GameEngine.getInstance();
		engine.init(flags, sc, camera);
		engine.start();

		ByteArrayOutputStream out = TestUtil.setIOstreams("");
		String expected =	"╔═══════════════════════════════════════╗\n" +
							"║                                       ║\n" +
							"║                                       ║\n" +
							"║                                       ║\n" +
							"║                                       ║\n" +
							"║                                       ║\n" +
							"║                                       ║\n" +
							"║                                       ║\n" +
							"║Really long string of text that will ge║\n" +
							"║t fragmented into multiple lines automa║\n" +
							"╚═══════════════════════════════════════╝\n";

		// Act
		overlay.writeParagraph("Really long string of text that will get fragmented into multiple lines automatically", 8);

		engine.step();
		String render = out.toString();
		out.reset();

		// Assert
		assertEquals(expected, render);
	}

	@Test
	public void ShouldNotWriteWhenInvalidIndex() throws GeometricException, GameEngineException
	{
		// Arrange
		Rectangle camera = new Rectangle(new Point[] { new Point(0, 0), new Point(0, 10), new Point(40, 10), new Point(40, 0)});
		TextOverlay overlay = new TextOverlay(camera);
		overlay.setOutline('╔', '╗', '╚', '╝', '║', '═');

		Scene sc = new Scene();
		sc.add(overlay);

		GameEngineFlags flags = new GameEngineFlags();
		flags.setUpdateMethod(GameEngineFlags.UpdateMethod.CODE);

		GameEngine engine = GameEngine.getInstance();
		engine.init(flags, sc, camera);
		engine.start();

		ByteArrayOutputStream out = TestUtil.setIOstreams("");
		String expected =	"╔═══════════════════════════════════════╗\n" +
							"║                                       ║\n" +
							"║                                       ║\n" +
							"║                                       ║\n" +
							"║                                       ║\n" +
							"║                                       ║\n" +
							"║                                       ║\n" +
							"║                                       ║\n" +
							"║                                       ║\n" +
							"║                                       ║\n" +
							"╚═══════════════════════════════════════╝\n";

		// Act
		overlay.writeCentered("text", -1);
		overlay.writeLeft("text", -1);
		overlay.writeRight("text", -1);
		overlay.writeParagraph("text text text text text text text text text text text ", -1);

		overlay.writeCentered("text", 11);
		overlay.writeLeft("text", 11);
		overlay.writeRight("text", 11);
		overlay.writeParagraph("text text text text text text text text text text text ", 11);


		engine.step();
		String render = out.toString();
		out.reset();

		// Assert
		assertEquals(expected, render);
	}

	@Test
	public void ShouldGetWidth() throws GeometricException, GameEngineException
	{
		// Arrange
		Rectangle camera = new Rectangle(new Point[] { new Point(0, 0), new Point(0, 10), new Point(40, 10), new Point(40, 0)});
		TextOverlay overlay = new TextOverlay(camera);
		int expected = 41;

		// Act
		int gotten = overlay.width();

		// Assert
		assertEquals(expected, gotten);
	}
	@Test
	public void ShouldGetHeight() throws GeometricException, GameEngineException
	{
		// Arrange
		Rectangle camera = new Rectangle(new Point[] { new Point(0, 0), new Point(0, 10), new Point(40, 10), new Point(40, 0)});
		TextOverlay overlay = new TextOverlay(camera);
		int expected = 11;

		// Act
		int gotten = overlay.height();

		// Assert
		assertEquals(expected, gotten);
	}
	@Test
	public void ShouldGetInnerWidth() throws GeometricException, GameEngineException
	{
		// Arrange
		Rectangle camera = new Rectangle(new Point[] { new Point(0, 0), new Point(0, 10), new Point(40, 10), new Point(40, 0)});
		TextOverlay overlay = new TextOverlay(camera);
		int expected = 39;

		// Act
		int gotten = overlay.innerWidth();

		// Assert
		assertEquals(expected, gotten);
	}
	@Test
	public void ShouldGetInnerHeight() throws GeometricException, GameEngineException
	{
		// Arrange
		Rectangle camera = new Rectangle(new Point[] { new Point(0, 0), new Point(0, 10), new Point(40, 10), new Point(40, 0)});
		TextOverlay overlay = new TextOverlay(camera);
		int expected = 9;

		// Act
		int gotten = overlay.innerHeight();

		// Assert
		assertEquals(expected, gotten);
	}
}
