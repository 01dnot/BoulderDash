package inf101.v17.boulderdash.bdobjects;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;

import inf101.v17.boulderdash.Direction;
import inf101.v17.boulderdash.IllegalMoveException;
import inf101.v17.boulderdash.Position;
import inf101.v17.boulderdash.maps.BDMap;
import javafx.scene.media.AudioClip;

/**
 * Contains most of the logic associated with objects that fall such as rocks
 * and diamonds.
 *
 * @author larsjaffke
 *
 */
public abstract class AbstractBDFallingObject extends AbstractBDKillingObject {

	/**
	 * Keeps the objects audioclips
	 */
	protected ArrayList<AudioClip> soundClips = new ArrayList<>();

	/**
	 * Random generator for falling direction
	 */
	protected static Random random = new Random();
	/**
	 * A timeout between the moment when an object can fall (e.g. the tile
	 * underneath it becomes empty) and the moment it does. This is necessary to
	 * make sure that the player doesn't get killed immediately when walking
	 * under a rock.
	 */
	protected static final int WAIT = 3;

	protected boolean falling = false;

	/**
	 * An enum that keeps the state of which way the object can fall
	 * @author Daniel
	 *
	 */
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
					fallState canFall = canObjectFallToSide();
					//If possible get soundclip for the object
					Optional<AudioClip> fallSound = Optional.of(soundClips.get(random.nextInt(soundClips.size())));
					// fall one step if tile below is empty or killable
					if ((under instanceof BDEmpty || under instanceof IBDKillable) && hardTileExistTwoBelow()) {
						prepareMoveTo(Direction.SOUTH, fallSound);

					} else if(under instanceof BDEmpty || under instanceof IBDKillable) {
						prepareMoveTo(Direction.SOUTH, Optional.empty());
					}
					else if(canFall == fallState.CAN_FALL_BOTH) {
						if(random.nextBoolean()) {
							prepareMoveTo(Direction.EAST, fallSound);
						} else {
							prepareMoveTo(Direction.WEST, fallSound);
						}
					} else if(canFall == fallState.CAN_FALL_LEFT) {
						prepareMoveTo(Direction.WEST, fallSound);
					} else if(canFall == fallState.CAN_FALL_RIGHT) {
						prepareMoveTo(Direction.EAST, fallSound);
					}

					else {
						falling = false;
					}
				} else {
					// start falling if tile below is empty or can fall to one of the sides
					falling = under instanceof BDEmpty || canObjectFallToSide() != fallState.CAN_NOT_FALL;
					fallingTimeWaited = 1;
				}
			} catch (IllegalMoveException e) {
				// This should never happen.
				System.out.println(e);
				System.exit(1);
			}
		}
	}

	//Checks that tile below is a hard object
	private boolean hardTileExistTwoBelow() {
		Position thisPos = owner.getPosition(this);
		if(thisPos.getY() <= 1) {
			return false;
		}
		Position twoUnder = thisPos.moveDirection(Direction.SOUTH).moveDirection(Direction.SOUTH);
		return !(owner.get(twoUnder) instanceof BDBug || owner.get(twoUnder) instanceof BDEmpty);
	}

	/**
	 * returns a fallState which indicates what sides the object can fall onto.
	 * @return
	 */
	private fallState canObjectFallToSide() {
		//object can only fall if it lays in a rock, wall og diamond
		boolean rockUnder = owner.get(getPosition().moveDirection(Direction.SOUTH)) instanceof BDRock;
		boolean wallUnder = owner.get(getPosition().moveDirection(Direction.SOUTH)) instanceof BDWall;
		boolean diamondUnder = owner.get(getPosition().moveDirection(Direction.SOUTH)) instanceof BDDiamond;
		if(!rockUnder && !wallUnder && !diamondUnder) { //If not lying on rock or wall, do nothing
			return fallState.CAN_NOT_FALL;
		}

		boolean fallLeft = false;
		boolean fallRight = false;
		//Can possibly fall left if not on the leftmost tile
		if(owner.getPosition(this).getX() >= 1) {
			fallLeft = canObjectFallThisDirection(Direction.WEST);
		}
		//Can possibly fall right if not on the rightmost tile
		if(owner.getPosition(this).getX() < owner.getWidth()-1) {
			fallRight = canObjectFallThisDirection(Direction.EAST);
		}
		if(fallLeft && fallRight) return fallState.CAN_FALL_BOTH;
		else if(fallLeft) return fallState.CAN_FALL_LEFT;
		else if(fallRight) return fallState.CAN_FALL_RIGHT;
		else return fallState.CAN_NOT_FALL;

	}
	/**
	 * checks if the object has clear way to fall in a direction.
	 * @param d
	 * @return
	 */
	private boolean canObjectFallThisDirection(Direction d) {
		IBDObject dirObject = owner.get(getPosition().moveDirection(d));
		IBDObject dirSouthObject = owner.get(getPosition().moveDirection(d).moveDirection(Direction.SOUTH));
		return dirObject instanceof BDEmpty && dirSouthObject instanceof BDEmpty;
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
