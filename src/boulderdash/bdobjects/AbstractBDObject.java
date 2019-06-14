package boulderdash.bdobjects;

import boulderdash.Position;
import boulderdash.maps.BDMap;

/**
 * Implements the common features of all {@link #IBDObject}s, i.e.\ several get-
 * and set-methods.
 * 
 * @author larsjaffke
 *
 */
public abstract class AbstractBDObject implements IBDObject {

	/**
	 * The map this object is contained in. This is crucial to implement some of
	 * the interactions of this object with objects of other types.
	 */
	protected BDMap owner;

	public AbstractBDObject(BDMap owner) {
		this.owner = owner;
	}

	@Override
	public BDMap getMap() {
		return owner;
	}

	@Override
	public Position getPosition() {
		return owner.getPosition(this);
	}

	@Override
	public int getX() {
		return owner.getPosition(this).getX();
	}

	@Override
	public int getY() {
		return owner.getPosition(this).getY();
	}

	@Override
	public void setMap(BDMap map) {
		this.owner = map;
	}

	@Override
	public boolean isEmpty() {
		return false;
	}

	@Override
	public boolean isKillable() {
		return false;
	}
}