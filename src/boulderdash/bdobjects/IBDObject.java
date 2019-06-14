package boulderdash.bdobjects;

import boulderdash.Position;
import boulderdash.maps.BDMap;
import javafx.scene.paint.Paint;

/**
 * The main interface that all objects in the game (i.e. on the map) have to
 * implement. Its most important functionality is the {@link #step()}-method
 * which updates the state of the object in one time step of the execution of
 * the program.
 *
 * @author larsjaffke
 *
 */
/**
 * @author anya
 *
 */
public interface IBDObject {

	/**
	 * This method is used to get a simple paintable output of this object. Can
	 * be replaced with something fancier.
	 * 
	 * @return
	 */
	Paint getColor();

	/**
	 * Returns the map that contains this object.
	 * 
	 * @return
	 */
	BDMap getMap();

	/**
	 * Get the position of the object
	 * 
	 * @return
	 */
	Position getPosition();

	/**
	 * Get the x-coordinate of the object
	 * 
	 * @return
	 */
	int getX();

	/**
	 * Get the y-coordinate of the object
	 * 
	 * @return
	 */
	int getY();

	/**
	 * Sets the map the object is contained in.
	 * 
	 * @param map
	 */
	void setMap(BDMap map);

	/**
	 * Execute one time step of the object's behavior
	 */
	void step();

	/**
	 * @return True if object represents an empty space
	 */
	boolean isEmpty();

	/**
	 * @return True if object can be killed by another falling object
	 */
	boolean isKillable();

}
