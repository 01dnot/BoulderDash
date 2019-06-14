package boulderdash.bdobjects;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;

import java.util.Optional;

import boulderdash.maps.BDMap;

/**
 * An implementation of sand which simply disappears when the player walks over
 * it. Nothing to do here.
 *
 * @author larsjaffke
 *
 */
public class BDSand extends AbstractBDObject {

	private static Optional<ImagePattern> image = Optional.empty();


	public BDSand(BDMap owner) {
		super(owner);
	}

	@Override
	public ImagePattern getColor() {
		/**
		 * initialize the images for sand 
		 */
		if(!image.isPresent()) {
			image = Optional.of((new ImagePattern(new Image("file:graphics/sand.png"))));
		}
		return image.get();
	}

	@Override
	public void step() {
		// DO NOTHING
	}
}