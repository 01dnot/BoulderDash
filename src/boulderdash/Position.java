package boulderdash;

/**
 * A class to represent an (x, y)-position on a grid.
 * 
 */
public class Position {

	/**
	 * value of the x-coordinate
	 */
	private int x;
	/**
	 * value of the y-coordinate
	 */
	private int y;

	/**
	 * Main constructor. Initializes a new {@link #Position} objects with the
	 * corresponding values of x and y.
	 * 
	 * @param x
	 * @param y
	 */
	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Creates a copy of this position. Returns a new object with the same x-
	 * and y-coordinates.
	 * 
	 * @return
	 */
	public Position copy() {
		return new Position(x, y);
	}

	/**
	 * Computes the distance to another position as the sum of the absolute
	 * difference between the x- and y-coordinates.
	 *
	 * @param anotherPosition
	 * @return
	 */
	public int distanceTo(Position anotherPosition) {
		return Math.abs(this.x - anotherPosition.x) + Math.abs(this.y - anotherPosition.y);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Position other = (Position) obj;
		if (x != other.x) {
			return false;
		}
		if (y != other.y) {
			return false;
		}
		return true;
	}

	/**
	 * Gets the x-coordinate
	 * 
	 * @return
	 */
	public int getX() {
		return x;
	}

	/**
	 * Gets the y-coordinate
	 * 
	 * @return
	 */
	public int getY() {
		return y;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	/**
	 * Checks whether you can go towards direction dir without becoming
	 * 'illegal', i.e. having x or y become negative.
	 * 
	 * @param dir
	 * @return
	 */
	public boolean legalDirection(Direction dir) {
		if (dir == Direction.WEST) {
			return x > 0;
		}
		if (dir == Direction.SOUTH) {
			return y > 0;
		}
		return true;
	}

	/**
	 * Returns a position one step towards direction dir, if possible
	 * 
	 * @param dir
	 * @return
	 */
	public Position moveDirection(Direction dir) {
		switch (dir) {
		case EAST:
			return new Position(x + 1, y);
		case NORTH:
			return new Position(x, y + 1);
		case SOUTH:
			return new Position(x, y - 1);
		case WEST:
			return new Position(x - 1, y);
		default:
			throw new IllegalArgumentException();
		}
	}

	@Override
	public String toString() {
		return "(x=" + x + ",y=" + y + ")";
	}

}
