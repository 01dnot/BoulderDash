package boulderdash.bdobjects;

import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;

import java.util.ArrayList;
import java.util.Optional;

import boulderdash.Direction;
import boulderdash.IllegalMoveException;
import boulderdash.Position;
import boulderdash.maps.BDMap;

/**
 * An implementation of the player.
 *
 * @author larsjaffke
 *
 */
public class BDPlayer extends AbstractBDMovingObject implements IBDKillable {

	/**
	 * Lists containing the sprite animations for walking left, right and standing still.
	 */
	private static Optional<ArrayList<ImagePattern>> walkRightList = Optional.empty();
	private static Optional<ArrayList<ImagePattern>> walkLeftList = Optional.empty();
	private static Optional<ArrayList<ImagePattern>> standList = Optional.empty();
	/**
	 * Animations get triggered by the animationsCounter which get updated in the step method. 
	 */
	private int walkingAnimationCounter;
	private int standingAnimationCounter;

	private static final int N_WALK_SPRITES = 8;
	private static final int N_STAND_SPRITES = 25;
	/**
	 * The time to wait before going back to standing animation from walking.
	 */
	private static final int WAITING_TIME_TO_STOP_ANIMATION = 10;
	private int timeWaited = 0;

	/**
	 * The sounds for BDPlayer
	 */
	private static final  AudioClip emptyTileSound = new AudioClip("file:sound/walk_empty.aiff");
	private static final AudioClip sandTileSound = new AudioClip("file:sound/walk_sand.aiff");
	private static final AudioClip pickUpDiamondSound = new AudioClip("file:sound/diamond_collect.aiff");
	private static final AudioClip killSound = new AudioClip("file:sound/playerKilled.aiff");


	/**
	 * The last key from keyboard input
	 */
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
		/**
		 * initialize the images for player 
		 */
		if(!walkRightList.isPresent() || !walkLeftList.isPresent() || !standList.isPresent()) {
			Image walkingImage = new Image("file:graphics/walkingSprite.png");
			Image standingImage = new Image("file:graphics/standingSprite.png");
			ArrayList<ImagePattern> tempLeft = new ArrayList<>();
			ArrayList<ImagePattern> tempRight = new ArrayList<>();
			ArrayList<ImagePattern> tempStand = new ArrayList<>();

			int startFrom = 0;
			for(int i=0; i<N_WALK_SPRITES; i++) {
				tempLeft.add(i,new ImagePattern(walkingImage, startFrom, 2, N_WALK_SPRITES, 2.05, true));
				tempRight.add(i,new ImagePattern(walkingImage, startFrom, 1, N_WALK_SPRITES, 2.05, true));
				startFrom++;
			}
			walkLeftList = Optional.of(tempLeft);
			walkRightList = Optional.of(tempRight);

			startFrom = 0;
			for(int i=0; i<N_STAND_SPRITES; i++) {
				tempStand.add(i,new ImagePattern(standingImage, startFrom++, 1, N_STAND_SPRITES, 1.05, true));
			}
			standList = Optional.of(tempStand);
		}
		//Return the animation for which the last keyboard input toggled.
		if(latestKeyPressed == KeyCode.LEFT) {
			return walkLeftList.get().get(walkingAnimationCounter);
		} else if(latestKeyPressed == KeyCode.RIGHT) {
			return walkRightList.get().get(walkingAnimationCounter);
		} else {
			return standList.get().get(standingAnimationCounter);
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
		//If player is asked to move.
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
						//Find correct audio depending on tile type and the prepare the move to this position.
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
			//Keep hold on long time to return to standing animation.
			if (timeWaited >= WAITING_TIME_TO_STOP_ANIMATION ) {
				latestKeyPressed = null;
				timeWaited = 0;
			} else {
				timeWaited++;
			}
		}
		//Update the animation
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