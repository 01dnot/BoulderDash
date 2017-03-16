package inf101.v17.boulderdash.bdobjects;

import javafx.scene.paint.Color;

import inf101.v17.boulderdash.maps.BDMap;

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
