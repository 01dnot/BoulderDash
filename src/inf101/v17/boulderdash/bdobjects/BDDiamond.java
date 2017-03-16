package inf101.v17.boulderdash.bdobjects;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;

import java.util.Optional;

import inf101.v17.boulderdash.maps.BDMap;

/**
 * A diamond object. All its logic is implemented in the abstract superclass.
 *
 * @author larsjaffke
 *
 */
public class BDDiamond extends AbstractBDFallingObject {

	private static Optional<ImagePattern> image = Optional.empty();


	public BDDiamond(BDMap owner) {
		super(owner);
	}

	@Override
	public ImagePattern getColor() {
		if(!image.isPresent()) {
			image = Optional.of(new ImagePattern(new Image("file:graphics/diamond.png")));
		}
		return image.get();
	}


}
