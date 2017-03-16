package inf101.v17.boulderdash.bdobjects;

import java.util.Optional;

import inf101.v17.boulderdash.Direction;
import inf101.v17.boulderdash.IllegalMoveException;
import inf101.v17.boulderdash.Position;
import inf101.v17.boulderdash.maps.BDMap;
import javafx.scene.image.Image;
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

	private static Optional<ImagePattern> image = Optional.empty();

	/**
	 * The standard constructor.
	 * @param owner
	 */
	public BDRock(BDMap owner) {
		super(owner);
	}

	@Override
	public ImagePattern getColor() {
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
			prepareMove(newPos);
			super.step();
			return true;
		}
		return false;	
	}
}
