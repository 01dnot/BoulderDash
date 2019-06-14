package boulderdash.bdobjects;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;

import java.util.Optional;

import boulderdash.maps.BDMap;

/**
 * Implementation of a piece of a wall.
 *
 * @author larsjaffke
 *
 */
public class BDWall extends AbstractBDObject {

	private static Optional<ImagePattern> image = Optional.empty();


	public BDWall(BDMap owner) {
		super(owner);
	}

	@Override
	public ImagePattern getColor() {
		/**
		 * initialize the images for wall 
		 */
		if(!image.isPresent()) {
			image = Optional.of((new ImagePattern(new Image("file:graphics/wall.png"))));
		}
		return image.get();
	}

	@Override
	public void step() {
		// DO NOTHING, IT'S A WALL
	}
}