package GameEngine;

/**
 * Represents the flags the engine will look out for when running
 * In other words, the arguments/options of the engine
 */
public class GameEngineFlags
{
	public enum UpdateMethod {
		STEP,
		AUTO,
		CODE,
	}
	private UpdateMethod updateMethod;
	private boolean isTextual;
	private boolean isRasterized;

	/**
	 * Initalizes GameEngineFlags
	 */
	public GameEngineFlags()
	{
		this.isTextual = true;
		this.isRasterized = true;
		updateMethod = UpdateMethod.STEP;
	}

	/**
	 * If the screen is to be rendered textually or graphically
	 * @return If the screen is to be rendered textually or graphically
	 */
	public boolean isTextual() { return this.isTextual; }

	/**
	 * Sets if the screen is to be rendered textually or graphically
	 * @param arg If the screen is to be rendered textually or graphically
	 */
	public void setTextual(boolean arg) { this.isTextual = arg; }

	/**
	 * If the objects's should be filled when drawn
	 * @return If the objects's should be filled when drawn
	 */
	public boolean isRasterized() { return this.isRasterized; }

	/**
	 * Sets if the objects's should be filled when drawn
	 * @param arg If the objects's should be filled when drawn
	 */
	public void setRasterized(boolean arg) { this.isRasterized = arg; }

	/**
	 * Returns the method in which the engine should be updated
	 * @return If the engine should update step by step or automatically
	 */
	public UpdateMethod updateMethod() { return this.updateMethod; }

	/**
	 * Sets the method the engine should update
	 * STEP - update via input
	 * AUTO - update automatically
	 * CODE - update through function call
	 * @param the method how the engine should update
	 */
	public void setUpdateMethod(UpdateMethod method) { this.updateMethod = method; }
}
