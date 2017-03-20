package inf101.v17.boulderdash.bdobjects;

import java.util.Optional;

import inf101.v17.boulderdash.maps.BDMap;
import javafx.scene.media.AudioClip;

/**
 * An abstract class that implements the common logic of objects that move in
 * the game.
 *
 * @author larsjaffke
 *
 */


public abstract class AbstractBDKillingObject extends AbstractBDMovingObject {



	public AbstractBDKillingObject(BDMap owner) {
		super(owner);
	}

	/**
	 * Moving objects can kill the player and bugs. This method checks whether
	 * this object will hit an object that implements the IKillable-interface in
	 * the next move and if so, does it.
	 */
	protected void checkAndKillKillable() {
		IBDObject obj = owner.get(this.getNextPosition());
		if (obj instanceof IBDKillable && obj != this) {
			IBDKillable toKill = (IBDKillable) obj;
			if (toKill.getNextPosition().equals(nextPos)) {
				toKill.kill();
			}
		}
	}

	@Override
	public void step() {
		// Check whether the target position hits an object that can be
		// killed.
		checkAndKillKillable();
		super.step();
	}
}
