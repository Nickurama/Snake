package GameEngine;

/**
 * Represents the flags the engine will look out for when running
 * In other words, the arguments/options of the engine
 *
 * @author Diogo Fonseca a79858
 * @version 30/04/2024
 *
 * @see GameEngine
 */
public class GameEngineFlags
{
	/**
	 * The method the game engine is going to use for
	 * iterating through cycles/updates
	*/
	public enum UpdateMethod {
		/**
		 * Updates in a stepped fashion, through user input.
		 * Updates every time an input is read.
		 */
		STEP,
		/**
		 * Updates automatically.
		 */
		AUTO,
		/**
		 * Updates through code, by calling the {@link GameEngine#step() Step} method.
		 */
		CODE,
	}
	private static final UpdateMethod DEFAULT_UPDATE_METHOD = UpdateMethod.STEP;
	private static final boolean DEFAULT_IS_TEXTUAL = true;

	private UpdateMethod updateMethod;
	private boolean isTextual;

	/**
	 * Initalizes GameEngineFlags with default values
	 */
	public GameEngineFlags()
	{
		this.isTextual = DEFAULT_IS_TEXTUAL;
		updateMethod = DEFAULT_UPDATE_METHOD;
	}

	/**
	 * Copies flags
	 * @param that the flags to copy
	 */
	public GameEngineFlags(GameEngineFlags that)
	{
		this.updateMethod = that.updateMethod();
		this.isTextual = that.isTextual();
	}

	/**
	 * If the screen is to be rendered textually or graphically
	 * @return If the screen is to be rendered textually or graphically
	 */
	public boolean isTextual() { return this.isTextual; }

	/**
	 * Sets if the screen is to be rendered textually or graphically
	 * @param arg if the screen is to be rendered textually or graphically
	 */
	public void setTextual(boolean arg) { this.isTextual = arg; }

	/**
	 * The method in which the engine should be updated
	 * @return the method in which the engine should be updated
	 */
	public UpdateMethod updateMethod() { return this.updateMethod; }

	/**
	 * Sets the method the engine should update
	 * @param the method how the engine should update
	 */
	public void setUpdateMethod(UpdateMethod method) { this.updateMethod = method; }
}
