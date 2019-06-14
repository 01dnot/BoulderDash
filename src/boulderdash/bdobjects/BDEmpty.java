package boulderdash.bdobjects;

import boulderdash.maps.BDMap;
import javafx.scene.paint.Color;

/**
 * An empty tile.
 *
 * @author larsjaffke
 *
 */
public class BDEmpty extends AbstractBDObject {

	public BDEmpty(BDMap owner) {
		super(owner);
	}

	@Override
	public Color getColor() {
		return Color.BLACK;
	}

	@Override
	public void step() {
		// DO NOTHING
	}

	@Override
	public boolean isEmpty() {
		return true;
	}
}
