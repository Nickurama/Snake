package GameEngine;

import static org.junit.Assert.*;

import org.junit.jupiter.api.Test;

public class GameEngineFlagsTests
{
	@Test
	public void ShouldSetOpts()
	{
		// Arrange
		GameEngineFlags opts = new GameEngineFlags();

		// Act
		opts.setTextual(true);
		opts.setUpdateMethod(GameEngineFlags.UpdateMethod.CODE);

		// Assert
		assertTrue(opts.isTextual());
		assertEquals(opts.updateMethod(), GameEngineFlags.UpdateMethod.CODE);
	}

	@Test
	public void ShouldMakeDeepCopy()
	{
		// Arrange
		GameEngineFlags opts0 = new GameEngineFlags();
		opts0.setTextual(true);

		// Act
		GameEngineFlags opts1 = new GameEngineFlags(opts0);
		opts1.setTextual(false);

		// Assert
		assertEquals(true, opts0.isTextual());
	}
}
