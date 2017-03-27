package inf101.v17.boulderdash.bdobjects;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Deque;

import org.junit.runners.model.FrameworkMember;

import inf101.v17.boulderdash.Direction;
import inf101.v17.boulderdash.Position;
import inf101.v17.boulderdash.maps.BDMap;
import inf101.v17.datastructures.PathNode;
import javafx.scene.input.KeyCode;

public class BDAIPlayer extends BDPlayer {
	/**
	 * An implementation of a playerbot
	 * @author danielnotland
	 */

	/**
	 * The stack of directions to get to a diamond
	 */
	private Deque<Direction> directionPath;

	/**
	 * Indicates if player has is currently searching for a diamond
	 */
	private boolean lookingForDiamond;
	/**
	 * The amount of diamonds AIPlayer have picked up.
	 */
	private int diamondCounter = 0;

	public BDAIPlayer(BDMap owner) {
		super(owner);
		lookingForDiamond = false;
	}

	@Override
	public void keyPressed(KeyCode key){
	}

	@Override
	public void step() {
		//if found diamond, player isnt looking for diamond anymore
		if(diamondCounter != diamondCnt) {
			lookingForDiamond = false;
			diamondCounter = diamondCnt;
		}
		if(!owner.getDiamonds().isEmpty() && !lookingForDiamond) {
			lookingForDiamond = true;
			directionPath = findRoute(this.getPosition(), owner.getDiamonds().pop().getPosition());
		}
		if(lookingForDiamond) {
			askedToGo = directionPath.pop();
		}
		super.step();
	}
	/**
	 * A method that finds the route from a point to a point, and returns it as a stack. 
	 * @param From. The position the AIplayer came from.
	 * @param To. The position the AIplayer want to get.
	 * @return Deque. A Deque where the first direction the player have to move in on top.
	 */
	public Deque<Direction> findRoute(Position from, Position to) {
		ArrayList<PathNode> closedSet = new ArrayList<>();
		ArrayList<PathNode> openSet = new ArrayList<>();
		Deque<Position> pathToGo = new ArrayDeque<>();
		PathNode start = new PathNode(from, to);
		openSet.add(start);
		PathNode current = start;
		while(!openSet.isEmpty()) {
			Collections.sort(openSet);
			current = openSet.get(0);
			System.out.println(current.getPosition());
			if((current.getPosition()).equals(to)) {
				return reconstructPath(pathToGo);	
			}
			openSet.remove(current);
			closedSet.add(current);
			ArrayList<Position> tempList = checkSurroundings(current.getPosition());
			for(Position p: tempList) {
				PathNode node = new PathNode(p,to,current);
				if(closedSet.contains(node)) {
					continue;
				}
				if(!openSet.contains(node)) {
					openSet.add(node);
				} else {
					for(PathNode n: openSet) {
						if(node.equals(n) && node.getGCost() < n.getGCost()) {
							n.setParent(node.getParent());
						}
					}
				}
			}
			pathToGo.push(current.getPosition());                                                                                                                                  
		}
		return null;

	}
	/**
	 * A method thats takes a route of positions and returns a stack of directions on how to get there
	 * in the opposite order
	 * @param pathToGo. The stack of the positions you want to go
	 * @return A stack of dirctions in opposite order.
	 */
	private Deque<Direction> reconstructPath(Deque<Position> pathToGo) {
		Deque<Direction> directionDeque = new ArrayDeque<>();
		Position tempTo = pathToGo.pop();
		Position tempFrom = pathToGo.pop();
		directionDeque.push(findDirection(tempFrom, tempTo));
		while(!pathToGo.isEmpty()) {
			tempTo = tempFrom;
			tempFrom = pathToGo.pop();
			directionDeque.push(findDirection(tempFrom, tempTo));

		}
		return directionDeque;
	}
	/**
	 * A methid that check which directions you need to go to get from a position to another position.
	 * @param tempFrom. The start Position.
	 * @param tempTo. The positions you want to move to.
	 * @return The direction of the tempTo from tempFrom.
	 */
	private Direction findDirection(Position tempFrom, Position tempTo) {
		if(tempFrom.getX() > tempTo.getX()) {
			return Direction.WEST;
		} else if(tempFrom.getX() < tempTo.getX()) {
			return Direction.EAST;
		} else if(tempFrom.getY() > tempTo.getY()) {
			return Direction.SOUTH;
		} else if(tempFrom.getY() < tempTo.getY()) {
			return Direction.NORTH;
		}
		return null;
	}
	/**
	 * Checks if you can move in any of the directions. And returns a list of positions you can move.
	 * @param A position pos.
	 * @return An arrayList of the positions you can move to.
	 */
	private ArrayList<Position> checkSurroundings(Position pos) {
		ArrayList<Position> posList = new ArrayList<>();

		if(owner.canGo(pos.moveDirection(Direction.SOUTH))) {
			posList.add(pos.moveDirection(Direction.SOUTH));
		}
		if(owner.canGo(pos.moveDirection(Direction.NORTH))) {
			posList.add(pos.moveDirection(Direction.NORTH));

		}if(owner.canGo(pos.moveDirection(Direction.WEST))) {
			posList.add(pos.moveDirection(Direction.WEST));

		}if(owner.canGo(pos.moveDirection(Direction.EAST))) {
			posList.add(pos.moveDirection(Direction.EAST));
		}
		return posList;
	}
}
