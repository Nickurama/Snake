package GeometryTests;

import Geometry.*;
import static org.junit.jupiter.api.Assertions.*;
import java.text.ParseException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

public class NaturalPointTests
{
	@Test
	public void ShouldNotAllowNegativeNumbers() throws GeometricException
	{
		// Arrange
		// Act
		// Assert
		assertThrows(GeometricException.class, () -> new NaturalPoint(3, -1));
	}

	@Test
	public void ShouldConvertVirtualPointToNaturalPoint() throws GeometricException
	{
		// Arrange
		VirtualPoint vp = new VirtualPoint(4, 5);

		// Act
		NaturalPoint np = new NaturalPoint(vp);

		// Arrange
		assertEquals(vp, np);
	}

	@Test
	public void ShouldConvertPointToNaturalPoint() throws GeometricException
	{
		// Arrange
		Point p = new Point(9, 12);

		// Act
		NaturalPoint np = new NaturalPoint(p);

		// Arrange
		assertEquals(p, np);
	}

	@Test
	public void ShouldNotAllowDecimalNumbers() throws GeometricException
	{
		// Arrange
		Point p = new Point(1.5, 2);

		// Act
		// Assert
		assertThrows(GeometricException.class, () -> new NaturalPoint(p));
	}

	@Test
	public void ShouldMakeCopy() throws GeometricException
	{
		// Arrange
		NaturalPoint np = new NaturalPoint(4, 9);

		// Act
		NaturalPoint copy = new NaturalPoint(np);

		// Assert
		assertEquals(np, copy);
	}
}
