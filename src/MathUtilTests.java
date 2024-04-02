import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;


public class MathUtilTests
{
    private final double d0 = 0;
    private final double d1 = (9f/10f) * MathUtil.CALC_ERROR_MARGIN;
    private final double d2 = -(9f/10f) * MathUtil.CALC_ERROR_MARGIN;
    private final double d3 = MathUtil.CALC_ERROR_MARGIN;
    private final double d4 = -MathUtil.CALC_ERROR_MARGIN;
    private final double d5 = (5f/10f) * MathUtil.CALC_ERROR_MARGIN;
    private final double d6 = MathUtil.CALC_ERROR_MARGIN;

    @Test
    public void ShouldBeEqualWithinErrorLimit()
    {
        // Arrange
        // Act
        boolean shouldBeEqualsIfGreaterButWithinErrorLimit = MathUtil.areEqual(d0, d1);
        boolean shouldBeEqualsIfLesserButWithinErrorLimit = MathUtil.areEqual(d0, d2);
        boolean shouldNotBeEqualsIfGreaterButOutsideErrorLimit = MathUtil.areEqual(d0, d3);
        boolean shouldNotBeEqualsIfLesserButOutsideErrorLimit = MathUtil.areEqual(d0, d4);
        boolean shouldBeEqualsIfWithinErrorLimit = MathUtil.areEqual(d5, d6);

        // Arrange
        assertTrue(shouldBeEqualsIfGreaterButWithinErrorLimit);
        assertTrue(shouldBeEqualsIfLesserButWithinErrorLimit);
        assertFalse(shouldNotBeEqualsIfGreaterButOutsideErrorLimit);
        assertFalse(shouldNotBeEqualsIfLesserButOutsideErrorLimit);
        System.out.println(d5);
        assertTrue(shouldBeEqualsIfWithinErrorLimit);
    }

    @Test
    public void ShouldBeGreaterOrEqualIfGreaterOrWithinErrorLimit()
    {
        // Arrange
        // Act
        boolean shouldBeTrueIfGreater = MathUtil.isGreaterOrEqualThan(d1, d0);
        boolean shouldBeTrueIfLesserButWithinErrorLimit = MathUtil.isGreaterOrEqualThan(d2, d0);
        boolean shouldBeTrueIfGreaterAndOutsideErrorLimit = MathUtil.isGreaterOrEqualThan(d3, d0);
        boolean shouldBeFalseIfLesserButOutsideErrorLimit = MathUtil.isGreaterOrEqualThan(d4, d0);
        boolean shouldBeTrueIfWithinErrorLimit = MathUtil.isGreaterOrEqualThan(d5, d6);

        // Arrange
        assertTrue(shouldBeTrueIfGreater);
        assertTrue(shouldBeTrueIfLesserButWithinErrorLimit);
        assertTrue(shouldBeTrueIfGreaterAndOutsideErrorLimit);
        assertFalse(shouldBeFalseIfLesserButOutsideErrorLimit);
        assertTrue(shouldBeTrueIfWithinErrorLimit);
    }
   
    @Test
    public void ShouldBeLessOrEqualIfLessOrWithinErrorLimit()
    {
        // Arrange
        // Act
        boolean shouldBeTrueIfGreaterButWithinErrorLimit = MathUtil.isLessOrEqualThan(d1, d0);
        boolean shouldBeTrueIfLesser = MathUtil.isLessOrEqualThan(d2, d0);
        boolean shouldBeFalseIfGreaterAndOutsideErrorLimit = MathUtil.isLessOrEqualThan(d3, d0);
        boolean shouldBeTrueIfLesserAndOutsideErrorLimit = MathUtil.isLessOrEqualThan(d4, d0);
        boolean shouldBeTrueIfWithinErrorLimit = MathUtil.isLessOrEqualThan(d5, d6);

        // Arrange
        assertTrue(shouldBeTrueIfGreaterButWithinErrorLimit);
        assertTrue(shouldBeTrueIfLesser);
        assertFalse(shouldBeFalseIfGreaterAndOutsideErrorLimit);
        assertTrue(shouldBeTrueIfLesserAndOutsideErrorLimit);
        assertTrue(shouldBeTrueIfWithinErrorLimit);
    }
}
