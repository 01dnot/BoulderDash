package inf101.v17.boulderdash.bdobjects;

import java.util.Optional;

import inf101.v17.boulderdash.Direction;
import inf101.v17.boulderdash.IllegalMoveException;
import inf101.v17.boulderdash.Position;
import javafx.scene.media.AudioClip;

/**
 * The main interface that all objects that move in the game have to implement.
 * Since the game is step-based, moves are not executed directly when the
 * corresponding methods are called but prepared and then executed the next time
 * the program does a step. (Hence the naming 'prepareMove(..)' rather than
 * 'move(..)'.
 *
 * @author larsjaffke
 *
 */
public interface IBDMovingObject extends IBDObject {

	/**
	 * This method returns the position this object should have after the next
	 * time the {@link #IBDObject.step()} method is executed on it. If a move is
	 * planned, the method returns the position prepared by one of the
	 * {@link #prepareMove(int, int)} methods and if the object stays in its
	 * place for the next round, the current position is returned.
	 *
	 * @return
	 */
	Position getNextPosition();

	/**
	 * Tries to prepare the next object's move to the position (x, y).
	 * 
	 * @param x
	 * @param y
	 * @throws IllegalMoveException
	 *             When the object cannot go to position (x, y)
	 */
	void prepareMove(int x, int y, Optional<AudioClip> audio) throws IllegalMoveException;

	/**
	 * See {@link #prepareMove(int, int)}
	 * 
	 * @param pos
	 */
	void prepareMove(Position pos, Optional<AudioClip> audio) throws IllegalMoveException;

	/**
	 * Tries to prepare the move into direction dir from the current position of
	 * this object. See also {@link #prepareMove(int, int)}.
	 * 
	 * @param dir
	 */
	void prepareMoveTo(Direction dir, Optional<AudioClip> audio) throws IllegalMoveException;
}
