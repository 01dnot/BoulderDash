package inf101.v17.datastructures;

import inf101.v17.boulderdash.Position;

public class PathNode implements Comparable  {
	/**
	 * An node keeps track of the path for a AIPlayers movment to its target
	 */
	/**
	 * The total cost from player to this node
	 */
	private int gCost;
	/**
	 * The total cost from this node to the target
	 */
	private int hCost;
	/**
	 * gCost + hCost
	 */
	private int fCost;
	/**
	 * This nodes parent
	 */
	private PathNode parent;
	/**
	 * The position of this node
	 */
	private Position position;
	/**
	 * The final destination for the node.
	 */
	private Position target;

	public PathNode(Position position, Position target, PathNode parent) {
		this.parent = parent;
		this.position = position;
		this.target = target;
		costCalculator();
	}

	public PathNode(Position position, Position target) {
		this.position = position;
		this.target = target;
	}
	/**
	 * Returns this nodes position
	 * @return
	 */
	public Position getPosition() {
		return position;
	}
	/**
	 * Returns this nodes gcost
	 * @return
	 */
	public int getGCost() {
		return gCost;
	}
	/**
	 * Returns this nodes hcost
	 * @return
	 */
	public int getHCost() {
		return hCost;
	}
	/**
	 * Returns this nodes fcost
	 * @return
	 */
	public int getFCost() {
		return fCost;
	}
	/**
	 * Returns this nodes parent
	 * @return
	 */
	public PathNode getParent() {
		return parent;
	}
	/**
	 * Sets this nodes parent
	 * @return nothing
	 */
	public void setParent(PathNode parent) {
		this.parent = parent;
		costCalculator();
	}
	/**
	 * Calculates the gcost, hcost and fcost
	 * @return
	 */
	public void costCalculator() {
		if(parent == null) {
			gCost = 0;
		} else {
			gCost = parent.getGCost() + parent.getPosition().distanceTo(position);
		}
		hCost = position.distanceTo(target);
		fCost = gCost + hCost; 
	}

	@Override 
	public int compareTo(Object obj) {
		return  this.getFCost()-((PathNode)obj).getFCost();

	}

	@Override
	public boolean equals(Object obj) {
		return this.getPosition().equals(((PathNode)obj).getPosition());
	}
}
