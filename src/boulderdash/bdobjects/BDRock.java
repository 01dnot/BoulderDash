package boulderdash.bdobjects;

import java.util.ArrayList;
import java.util.Optional;

import boulderdash.Direction;
import boulderdash.IllegalMoveException;
import boulderdash.Position;
import boulderdash.maps.BDMap;
import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
/**
 * An implementation of a rock.
 *
 * @author danielnotland
 *
 */
public class BDRock extends AbstractBDFallingObject {

	/**
	 * An ImagePattern containing the image of a rock
	 */
	private static Optional<ImagePattern> image = Optional.empty();
	/**
	 * An arrayList containing the sounds for the rock.
	 */
	private static ArrayList<AudioClip> rockClips = new ArrayList<>();

	/**
	 * The standard constructor.
	 * @param owner
	 */
	public BDRock(BDMap owner) {
		super(owner);
		if(rockClips.isEmpty()) {
			rockClips.add(new AudioClip("file:sound/stone.aiff"));
		}
		soundClips = rockClips;

	}

	@Override
	public ImagePattern getColor() {
		/**
		 * initialize the image for rock 
		 */
		if(!image.isPresent()) {
			image = Optional.of(new ImagePattern(new Image("file:graphics/rock.png")));
		}
		return image.get();
	}
	/**
	 * A method that pushes the rock east or west depending on dir.
	 * @param dir
	 * @return
	 * @throws IllegalMoveException
	 */
	public boolean push(Direction dir) throws IllegalMoveException {
		//Checks that moving direction is west or east
		if(dir == Direction.NORTH || dir == Direction.SOUTH) {
			throw new IllegalMoveException("Wrong push direction");
		}
		Position newPos = this.getPosition().copy().moveDirection(dir);

		if(owner.canGo(newPos) && owner.get(newPos) instanceof BDEmpty) {
			try {
				prepareMove(newPos, Optional.of(rockClips.get(random.nextInt(rockClips.size()))));

			} catch(IllegalMoveException e) {
				return false;
			}
			super.step();
			return true;
		}
		return false;	
	}
}