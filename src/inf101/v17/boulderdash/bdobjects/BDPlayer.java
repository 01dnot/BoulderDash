package inf101.v17.boulderdash.bdobjects;

import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;

import java.util.Optional;

import inf101.v17.boulderdash.Direction;
import inf101.v17.boulderdash.IllegalMoveException;
import inf101.v17.boulderdash.Position;
import inf101.v17.boulderdash.maps.BDMap;

/**
 * An implementation of the player.
 *
 * @author larsjaffke
 *
 */
public class BDPlayer extends AbstractBDMovingObject implements IBDKillable {
   
	private static Optional<ImagePattern> image = Optional.empty();
	/**
	 * Is the player still alive?
	 */
	protected boolean alive = true;

	/**
	 * The direction indicated by keypresses.
	 */
	protected Direction askedToGo;

	/**
	 * Number of diamonds collected so far.
	 */
	protected int diamondCnt = 0;

	public BDPlayer(BDMap owner) {
		super(owner);
	}

	@Override
	public ImagePattern getColor() {
		if(!image.isPresent()) {
			image = Optional.of(new ImagePattern(new Image("file:graphics/player.png")));
		}
		return image.get();
	}

	/**
	 * @return true if the player is alive
	 */
	public boolean isAlive() {
		return alive;
	}
	/**
	 * creates direction depending on key input.
	 * @param key
	 */
	public void keyPressed(KeyCode key) {
		switch(key) {
		case UP: askedToGo = Direction.NORTH; break;
		case DOWN: askedToGo = Direction.SOUTH; break;
		case LEFT: askedToGo = Direction.WEST; break;
		case RIGHT: askedToGo = Direction.EAST;
		}

	}

	@Override
	public void kill() {
		this.alive = false;
	}

	/**
	 * Returns the number of diamonds collected so far.
	 *
	 * @return
	 */
	public int numberOfDiamonds() {
		return diamondCnt;
	}

	@Override
	public void step() {
		Position playerPos = owner.getPosition(this);
		if(askedToGo != null) {
			boolean canMove = true;
			Position nextPos = playerPos.copy().moveDirection(askedToGo);
			try {
				if(owner.canGo(nextPos)) {
					if(owner.get(nextPos) instanceof BDDiamond) {
						diamondCnt++;
					} else if(owner.get(nextPos) instanceof BDBug) {
						kill();
					} else if(owner.get(nextPos) instanceof BDRock) {
						canMove = ((BDRock)owner.get(nextPos)).push(askedToGo);
					}
					if(canMove) {
						this.prepareMove(nextPos);
					}
				}
			} catch(IllegalMoveException e) {
				System.out.println("Illegal move");
			}
		}
		askedToGo = null;
		super.step();
	}


	@Override
	public boolean isKillable() {
		return true;
	}
}
