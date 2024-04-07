package GameEngineTests;

import static org.junit.Assert.*;

import org.junit.jupiter.api.Test;

import GameEngine.*;

public class GameEngineFlagsTests
{
	@Test
	public void ShouldSetOpts()
	{
		// Arrange
		GameEngineFlags opts = new GameEngineFlags();

		// Act
		opts.setTextual(true);
		opts.setRasterized(true);
		opts.setUpdateMethod(GameEngineFlags.UpdateMethod.CODE);

		// Assert
		assertTrue(opts.isTextual());
		assertTrue(opts.isRasterized());
		assertEquals(opts.updateMethod(), GameEngineFlags.UpdateMethod.CODE);
	}
}
