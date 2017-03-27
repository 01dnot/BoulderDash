package inf101.v17.boulderdash.bdobjects;

import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;

import java.util.ArrayList;
import java.util.Optional;

import inf101.v17.boulderdash.maps.BDMap;

/**
 * A diamond object. All its logic is implemented in the abstract superclass.
 *
 * @author larsjaffke
 *
 */
public class BDDiamond extends AbstractBDFallingObject {

	/**
	 * Holds the BDDiamonds sprites
	 */
	private static Optional<ArrayList<ImagePattern>> spriteList = Optional.empty();
	/**
	 * Keeps track of which sprite to show.
	 */
	private int animationCounter;
	/**
	 * Total amount of sprites
	 */
	final private int N_SPRITES = 8;
	/**
	 * Total amount of sounds
	 */
	final private int N_SOUNDS = 8;
	/**
	 * A list of diamonds sounds
	 */
	private static ArrayList<AudioClip> diamondSounds = new ArrayList<>();

	public BDDiamond(BDMap owner) {
		super(owner);
		animationCounter = 0;
		if(diamondSounds.isEmpty()) {
			//Add diamondsounds
			for(int i=0; i<N_SOUNDS; i++) {
				String file = "file:sound/diamond_" + (i+1) +".aiff";
				diamondSounds.add(new AudioClip(file));
			}			
		}
		soundClips = diamondSounds;
	}

	@Override
	public ImagePattern getColor() {
		/**
		 * initialize the images for player 
		 */
		if(!spriteList.isPresent()) {
			int startFrom = 0;
			Image fileImage = new Image("file:graphics/diamondSprite.png");
			ArrayList<ImagePattern> tempList = new ArrayList<>();
			for(int i = 0; i < N_SPRITES; i++) {
				tempList.add(i,new ImagePattern(fileImage, startFrom++, 0, N_SPRITES, 1, true));
			}
			spriteList = Optional.of(tempList);
		}

		return spriteList.get().get(animationCounter);
	}

	@Override
	public void step() {
		// Update which picture to show
		animationCounter = (animationCounter+1)%N_SPRITES;
		super.step();
	}
}
