package boulderdash.bdobjects;

/**
 * An object that can be killed such as the player or a bug. Note that an object
 * that can be killed always also can move.
 *
 * @author larsjaffke
 *
 */
public interface IBDKillable extends IBDMovingObject {

	/**
	 * In this method the consequences of an object implementing this interface
	 * being killed in the game are realized.
	 */
	public void kill();

}
