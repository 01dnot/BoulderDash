package inf101.v17.boulderdash.bdobjects;

import java.util.Random;

import inf101.v17.boulderdash.Direction;
import inf101.v17.boulderdash.IllegalMoveException;
import inf101.v17.boulderdash.Position;
import inf101.v17.boulderdash.maps.BDMap;

/**
 * Contains most of the logic associated with objects that fall such as rocks
 * and diamonds.
 *
 * @author larsjaffke
 *
 */
public abstract class AbstractBDFallingObject extends AbstractBDKillingObject {
	Random random = new Random();
	/**
	 * A timeout between the moment when an object can fall (e.g. the tile
	 * underneath it becomes empty) and the moment it does. This is necessary to
	 * make sure that the player doesn't get killed immediately when walking
	 * under a rock.
	 */
	protected static final int WAIT = 3;

	protected boolean falling = false;
	
	private enum fallState {
		CAN_FALL_LEFT, CAN_FALL_RIGHT, CAN_FALL_BOTH, CAN_NOT_FALL
	}
	/**
	 * A counter to keep track when the falling should be executed next, see the
	 * WAIT constant.
	 */
	protected int fallingTimeWaited = 0;

	public AbstractBDFallingObject(BDMap owner) {
		super(owner);
	}

	/**
	 * This method implements the logic of the object falling. It checks whether
	 * it can fall, depending on the object in the tile underneath it and if so,
	 * tries to prepare the move.
	 */
	public void fallIfPossible() {
		// Wait until its time to fall
		if (falling && fallingTimeWaited < WAIT) {
			fallingTimeWaited++;
			return;
		}
		// The timeout is over, try and prepare the move
		fallingTimeWaited = 0;

		Position pos = owner.getPosition(this);
		// The object cannot fall if it is on the lowest row.
		if (pos.getY() > 0) {
			try {
				// Get the object in the tile below.
				Position below = pos.copy().moveDirection(Direction.SOUTH);
				

				IBDObject under = owner.get(below);


				if (falling) {
					// fall one step if tile below is empty or killable
					if (under instanceof BDEmpty || under instanceof IBDKillable) {
						prepareMoveTo(Direction.SOUTH);
					}

					 else {
						falling = false;
					}
				} else if(falling = under instanceof BDEmpty) {
					// start falling if tile below is empty
			
					fallingTimeWaited = 1;
				}
//				else {
//					switch(canObjectFall()) {
//					case CAN_FALL_BOTH: 
//					case CAN_FALL_LEFT: prepareMoveTo(Direction.WEST); break;
//					case CAN_FALL_RIGHT:prepareMoveTo(Direction.EAST); break;
//					}
//				}
			} catch (IllegalMoveException e) {
				// This should never happen.
				System.out.println(e);
				System.exit(1);
			}
		}
	}

	private fallState canObjectFall() {
		if(!(owner.get(getPosition().moveDirection(Direction.NORTH)) instanceof BDRock)) {
			return fallState.CAN_NOT_FALL;
		}
		boolean fallLeft = canObjectFallThisDirection(Direction.WEST);
		boolean fallRight = canObjectFallThisDirection(Direction.EAST);
		if(fallLeft && fallRight) return fallState.CAN_FALL_BOTH;
		else if(fallLeft) return fallState.CAN_FALL_LEFT;
		else if(fallRight) return fallState.CAN_FALL_RIGHT;
		else return fallState.CAN_NOT_FALL;
		
	}

	private boolean canObjectFallThisDirection(Direction d) {
		IBDObject dirObject = owner.get(getPosition().moveDirection(d));
		IBDObject dirSouthObject = owner.get(getPosition().moveDirection(d).moveDirection(Direction.SOUTH));
		return dirObject instanceof BDEmpty && (dirSouthObject instanceof BDEmpty || dirSouthObject instanceof IBDObject);
	}
	
	@Override
	public void step() {
		// (Try to) fall if possible
		fallIfPossible();
		super.step();
	}

	@Override
	public boolean isEmpty() {
		return false;
	}
}
