package inf101.v17.boulderdash.bdobjects.tests;

import static org.junit.Assert.*;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

import inf101.v17.boulderdash.Direction;
import inf101.v17.boulderdash.IllegalMoveException;
import inf101.v17.boulderdash.Position;
import inf101.v17.boulderdash.bdobjects.AbstractBDFallingObject;
import inf101.v17.boulderdash.bdobjects.BDBug;
import inf101.v17.boulderdash.bdobjects.BDDiamond;
import inf101.v17.boulderdash.bdobjects.IBDObject;
import inf101.v17.boulderdash.maps.BDMap;
import inf101.v17.datastructures.IGrid;
import inf101.v17.datastructures.MyGrid;

public class BugTest {

	private BDMap map;

	@Before
	public void setup() {
	}

	@Test
	public void bugMovesAfterMultipleStepsTest() {
		IGrid<Character> grid = new MyGrid<>(4, 4, ' ');
		grid.set(2, 2, 'b');
		map = new BDMap(grid);

		// find the bug
		Position bugPos = new Position(2,2);
		IBDObject bug = map.get(bugPos);
		assertTrue(bug instanceof BDBug);

		for(int i = 0; i < 100; i++) {
			map.step();
			if(map.get(bugPos) != bug) { // bug has moved
				// reported position should be different
				assertNotEquals(bugPos, map.getPosition(bug));
				// bug moved –  we're done
				return;
			}

		}
		fail("Bug should have moved in 100 steps!");
	}

	@Test 
	public void bugDoesntMoveInWallTest() {
		IGrid<Character> grid = new MyGrid<>(4, 4, '*');
		grid.set(2, 2, 'b');
		map = new BDMap(grid);

		// find the bug
		Position bugPos = new Position(2,2);
		IBDObject bug = map.get(bugPos);
		assertTrue(bug instanceof BDBug);

		for(int i = 0; i < 100; i++) {
			map.step();
			if(map.get(bugPos) != bug) { // bug has moved
				fail("Bug shouldnt move when surounded by walls!");
			}
		}
	}

	@Test 
	public void bugdoesntMoveInSandTest() {
		IGrid<Character> grid = new MyGrid<>(4, 4, '#');
		grid.set(2, 2, 'b');
		map = new BDMap(grid);

		// find the bug
		Position bugPos = new Position(2,2);
		IBDObject bug = map.get(bugPos);
		assertTrue(bug instanceof BDBug);

		for(int i = 0; i < 100; i++) {
			map.step();
			if(map.get(bugPos) != bug) { // bug has moved
				fail("Bug shouldnt move when surounded by sand!");
			}
		}
	}

	@Test
	public void bugKillsPlayerTest() {
		IGrid<Character> grid = new MyGrid<>(4, 4, ' ');
		grid.set(1, 1, 'b');
		grid.set(1, 2, 'p');
		map = new BDMap(grid);

		for(int i = 0; i < 400; i++) {
			map.step();
		}
		//After multiple step bug should have killed plater
		assertFalse(map.getPlayer().isAlive());
	}

	@Test
	public void killedBugSprayesDiamonds() {
		IGrid<Character> grid = new MyGrid<>(4, 4, ' ');
		grid.set(2, 2, 'b');
		map = new BDMap(grid);
		IBDObject obj = map.get(2, 2);
		assertTrue(obj instanceof BDBug);

		((BDBug)obj).kill();

		for(int i=0; i<grid.getHeight(); i++) {
			for(int j=0; j<grid.getWidth(); j++) {
				if(map.get(j, i) instanceof BDDiamond) {
					//Found a diamond!
					return;
				}
			}
		}
		fail("Bug didnt spray any diamonds when killed");

	}

	@Test
	public void bugMovesInRightDirectionTest() {
		IGrid<Character> grid = new MyGrid<>(5, 5, ' ');
		grid.set(2, 2, 'b');
		map = new BDMap(grid);
		IBDObject obj = map.get(2, 2);
		assertTrue(obj instanceof BDBug); 

		try {
			((BDBug)obj).prepareMoveTo(Direction.EAST, Optional.empty());
		} catch (IllegalMoveException e) {
			fail("Bug couldnt move!");
		}
		map.step();
		map.step();
		map.step();
		map.step();
		assertTrue(map.get(3,2) instanceof BDBug);

		try {
			((BDBug)obj).prepareMoveTo(Direction.WEST, Optional.empty());
		} catch (IllegalMoveException e) {
			fail("Bug couldnt move!");
		}
		map.step();
		map.step();
		map.step();
		map.step();
		assertTrue(map.get(2,2) instanceof BDBug);

		try {
			((BDBug)obj).prepareMoveTo(Direction.NORTH, Optional.empty());
		} catch (IllegalMoveException e) {
			fail("Bug couldnt move!");
		}
		map.step();
		map.step();
		map.step();
		map.step();
		assertTrue(map.get(2,2) instanceof BDBug);

		try {
			((BDBug)obj).prepareMoveTo(Direction.SOUTH, Optional.empty());
		} catch (IllegalMoveException e) {
			fail("Bug couldnt move!");
		}
		map.step();
		map.step();
		map.step();
		map.step();
		assertTrue(map.get(2,1) instanceof BDBug);
	}
}
