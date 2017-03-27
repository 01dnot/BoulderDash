package inf101.v17.boulderdash.gui;

import inf101.v17.boulderdash.bdobjects.BDPlayer;
import inf101.v17.boulderdash.maps.BDMap;

public class Camera {

	// Cordinates of the camera
	private int x, y;
	//The max and min the camera can go
	private int offsetMaxX;
	private int	offsetMaxY;
	private static final int offsetMinX = 0;
	private static final int offsetMinY = 0;
	//The size of the camera
	private static int width;
	private static int height;
	private static final int PREFERED_WIDTH = 19;
	private static final int PREFERED_HEIGTH = 11;



	public Camera(int x, int y, BDMap map) {
		setX(x);
		setY(y);
		width = Math.min(map.getWidth(), PREFERED_WIDTH);
		height = Math.min(map.getHeight(), PREFERED_HEIGTH);
		offsetMaxX = map.getWidth() - width;
		offsetMaxY = map.getHeight() - height;

	}
	/**
	 * Updates the camera position depending on the location of the player
	 * @param player
	 */
	public void step(BDPlayer player) {
		setX(player.getX() - width/2);
		setY(player.getY() - height/2);
	}
	/**
	 * Set the cameras x cordinate
	 * @param x
	 */
	public void setX(int x) {
		if(x > offsetMaxX) {
			this.x = offsetMaxX;
		} else if(x < offsetMinX) {
			this.x = offsetMinX;
		} else	{
			this.x = x;
		}
	}
	/**
	 * Set the cameras y cordinate
	 * @param y
	 */
	public void setY(int y) {
		if(y > offsetMaxY) {
			this.y = offsetMaxY;
		} else if(y < offsetMinY) {
			this.y = offsetMinY;
		} else {
			this.y = y;
		}
	}
	/**
	 * Returns the cameras x cordinate
	 * @return
	 */
	public int getX() {
		return x;
	}
	/**
	 * Returns the cameras y cordinate
	 * @return
	 */
	public int getY() {
		return y;
	}
	/**
	 * Returns the width of the camera
	 * @return
	 */
	public int getWidth() {
		return width;
	}
	/**
	 * Returns the height of the camera
	 * @return
	 */
	public int getHeigth() {
		return height;
	}
}
