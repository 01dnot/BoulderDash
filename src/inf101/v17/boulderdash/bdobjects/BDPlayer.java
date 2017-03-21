package inf101.v17.boulderdash.bdobjects;

import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;

import java.util.ArrayList;
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


	private static ArrayList<ImagePattern> walkRightList = new ArrayList<ImagePattern>();
	private static ArrayList<ImagePattern> walkLeftList = new ArrayList<ImagePattern>();
	private static ArrayList<ImagePattern> standList = new ArrayList<ImagePattern>();

	private static Optional<ImagePattern> image = Optional.empty();
	private int walkingAnimationCounter;
	private int standingAnimationCounter;
	private static final int N_WALK_SPRITES = 8;
	private static final int N_STAND_SPRITES = 25;
	private static final int WAITING_TIME_TO_STOP = 10;
	private int timeWaited = 0;
	private static final  AudioClip emptyTileSound = new AudioClip("file:sound/walk_empty.aiff");
	private static final AudioClip sandTileSound = new AudioClip("file:sound/walk_sand.aiff");
	private static final AudioClip pickUpDiamondSound = new AudioClip("file:sound/diamond_collect.aiff");
	private static final AudioClip killSound = new AudioClip("file:sound/playerKilled.aiff");



	private KeyCode latestKeyPressed;
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
		walkingAnimationCounter = 0;
		standingAnimationCounter = 0;
	}

	@Override
	public ImagePattern getColor() {
		if(!image.isPresent()) {
			int startFrom = 0;
			Image walkingImage = new Image("file:graphics/walkingSprite.png");
			Image standingImage = new Image("file:graphics/standingSprite.png");
			for(int i=0; i<N_WALK_SPRITES; i++) {
				walkLeftList.add(i,new ImagePattern(walkingImage, startFrom, 2, N_WALK_SPRITES, 2.05, true));
				walkRightList.add(i,new ImagePattern(walkingImage, startFrom, 1, N_WALK_SPRITES, 2.05, true));
				startFrom++;
			}
			startFrom = 0;
			for(int i=0; i<N_STAND_SPRITES; i++) {
				standList.add(i,new ImagePattern(standingImage, startFrom++, 1, N_STAND_SPRITES, 1.05, true));
			}
			image = Optional.of(walkLeftList.get(walkingAnimationCounter));
		}

		if(latestKeyPressed == KeyCode.LEFT) {
			return walkLeftList.get(walkingAnimationCounter);
		} else if(latestKeyPressed == KeyCode.RIGHT) {
			return walkRightList.get(walkingAnimationCounter);
		} else {
			return standList.get(standingAnimationCounter);
		}
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
		latestKeyPressed = key;
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
		killSound.play();
		
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
			timeWaited = 0;
			Position nextPos = playerPos.copy().moveDirection(askedToGo);
			try {
				if(owner.canGo(nextPos)) {
					boolean canMove = true;
					IBDObject nextObj = owner.get(nextPos);
					if(nextObj instanceof BDDiamond) {
						diamondCnt++;
					} else if(nextObj instanceof BDBug) {
						kill();
						canMove = false;
						owner.set(this.getX(), this.getY(), new BDEmpty(owner)); //Replace dead player with empty tile. No blood or gore here.
					} else if(nextObj instanceof BDRock) {
						canMove = ((BDRock)nextObj).push(askedToGo);
					}
					
					if(canMove) {
						Optional<AudioClip> moveSound = 
							nextObj instanceof BDSand ? Optional.of(sandTileSound)
							: nextObj instanceof BDEmpty ? Optional.of(emptyTileSound)
							: nextObj instanceof BDDiamond ? Optional.of(pickUpDiamondSound)
							: Optional.empty();
						prepareMove(nextPos, moveSound);
					}
				}
			} catch(IllegalMoveException e) {
				System.out.println("Illegal move");
			}
		} else {
			if (timeWaited >= WAITING_TIME_TO_STOP ) {
				latestKeyPressed = null;
				timeWaited = 0;
			} else {
				timeWaited++;
			}
		}
		walkingAnimationCounter = (walkingAnimationCounter+1)%N_WALK_SPRITES;
		standingAnimationCounter = (standingAnimationCounter+1)%N_STAND_SPRITES;
		
		
		
		
		askedToGo = null;
		
		
		
		super.step();
	}


	@Override
	public boolean isKillable() {
		return true;
	}
}
