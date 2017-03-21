package inf101.v17.boulderdash.bdobjects;

import java.util.Optional;

import inf101.v17.boulderdash.Direction;
import inf101.v17.boulderdash.IllegalMoveException;
import inf101.v17.boulderdash.Position;
import inf101.v17.boulderdash.maps.BDMap;
import javafx.scene.media.AudioClip;

/**
 * This class implements most of the logic associated with moving objects in the
 * Boulder Dash game, in particular implementations of the prepareMove()-methods
 * which are independent of the concrete type of the object. It also provides an
 * implementation stub of the {@link #step()}-method.
 * 
 * @author larsjaffke
 *
 */
public abstract class AbstractBDMovingObject extends AbstractBDObject implements IBDMovingObject {

	/**
	 * Since the execution of the program is step-based, we have to keep track
	 * of the next position in case a new move has been prepared.
	 */
	protected Position nextPos;

	public AbstractBDMovingObject(BDMap owner) {
		super(owner);
	}

	@Override
	public Position getNextPosition() {
		return nextPos == null ? owner.getPosition(this) : nextPos;
	}

	@Override
	public void prepareMove(int x, int y, Optional<AudioClip> audio) throws IllegalMoveException {
		if (!owner.canGo(x, y)) {
			throw new IllegalMoveException("Cannot move to (" + x + ", " + y + ")");
		}
		// If the new position is legal, register the next position. The update
		// has
		// to be done in the step()-method.
		nextPos = new Position(x, y);
		//if exist audioclip: play it!
		if(audio.isPresent()){
			audio.get().play();
		}
	}

	@Override
	public void prepareMove(Position pos, Optional<AudioClip> audio) throws IllegalMoveException {
		prepareMove(pos.getX(), pos.getY(), audio);
	}

	@Override
	public void prepareMoveTo(Direction dir, Optional<AudioClip> audio) throws IllegalMoveException {
		Position pos = owner.getPosition(this);
		if (!owner.canGo(pos, dir)) {
			throw new IllegalMoveException("Cannot move " + dir + " from (" + pos.getX() + ", " + pos.getY() + ")");
		}
		nextPos = pos.moveDirection(dir);
		if(audio.isPresent()){
			audio.get().play();
		}
	}

	@Override
	public void step() {
		if (nextPos != null) {
			Position pos = owner.getPosition(this);
			// Update the map
			owner.set(pos.getX(), pos.getY(), new BDEmpty(owner));
			owner.set(nextPos.getX(), nextPos.getY(), this);
		}
		this.nextPos = null;
	}


}