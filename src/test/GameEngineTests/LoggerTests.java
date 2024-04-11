package GameEngineTests;

import GameEngine.*;
import TestUtil.TestUtil;

import static org.junit.jupiter.api.Assertions.*;
import java.io.ByteArrayOutputStream;
import org.junit.jupiter.api.Test;

public class LoggerTests
{
	@Test
	public void ShouldPrintLogs()
	{
		// Arrange
        ByteArrayOutputStream os = TestUtil.setIOstreams("");
		
		// Act
		Logger.startLogging(Logger.Level.INFO);
		Logger.log(Logger.Level.INFO, "test log");
		String output = os.toString();
		os.reset();

		// Assert
		assertTrue(output.contains("test log"));
	}

	@Test
	public void ShouldNotPrintLogsIfNotStarted()
	{
		// Arrange
        ByteArrayOutputStream os = TestUtil.setIOstreams("");
		
		// Act
		Logger.log(Logger.Level.INFO, "log");
		String output = os.toString();
		os.reset();


		// Assert
		assertEquals("", output);
	}

	@Test
	public void ShouldOnlyPrintLogsOfEqualOrHigherLevel()
	{
		// Arrange
        ByteArrayOutputStream os = TestUtil.setIOstreams("");
		Logger.startLogging(Logger.Level.WARN);
		
		// Act
		Logger.log(Logger.Level.DEBUG, "debug log");
		String output0 = os.toString();
		os.reset();
		Logger.log(Logger.Level.INFO, "info log");
		String output1 = os.toString();
		os.reset();
		Logger.log(Logger.Level.WARN, "warn log");
		String output2 = os.toString();
		os.reset();
		Logger.log(Logger.Level.ERROR, "error log");
		String output3 = os.toString();
		os.reset();
		Logger.log(Logger.Level.FATAL, "fatal log");
		String output4 = os.toString();
		os.reset();

		// Assert
		assertEquals("", output0);
		assertEquals("", output1);
		assertTrue(output2.contains("warn log"));
		assertTrue(output3.contains("error log"));
		assertTrue(output4.contains("fatal log"));
	}

	@Test
	public void ShouldNotPrintLogsIfStoppedLogging()
	{
		// Arrange
        ByteArrayOutputStream os = TestUtil.setIOstreams("");
		
		// Act
		Logger.startLogging(Logger.Level.INFO);
		Logger.log(Logger.Level.INFO, "log0");
		String output0 = os.toString();
		os.reset();

		Logger.stopLogging();
		Logger.log(Logger.Level.INFO, "log1");
		String output1 = os.toString();
		os.reset();

		// Assert
		assertTrue(output0.contains("log0"));
		assertEquals("", output1);
	}
}
