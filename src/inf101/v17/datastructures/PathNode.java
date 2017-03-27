package inf101.v17.datastructures;

import inf101.v17.boulderdash.Position;

public class PathNode implements Comparable  {

	private int gCost;
	private int hCost;
	private int fCost;
	private PathNode parent;
	private Position position;
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

	public Position getPosition() {
		return position;
	}
	public int getGCost() {
		return gCost;
	}
	public int getHCost() {
		return hCost;
	}
	public int getFCost() {
		return fCost;
	}
	public PathNode getParent() {
		return parent;
	}
	public void setParent(PathNode parent) {
		this.parent = parent;
		costCalculator();
	}
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
