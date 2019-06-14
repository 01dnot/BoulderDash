package boulderdash;

/**
 * An exception to be thrown when an moving object tries to do an illegal move,
 * for example to a position outside the map.
 * 
 * @author larsjaffke
 *
 */
public class IllegalMoveException extends Exception {

	/**
	 *
	 */
	private static final long serialVersionUID = 7641529271996915740L;

	public IllegalMoveException(String message) {
		super(message);
	}
}
