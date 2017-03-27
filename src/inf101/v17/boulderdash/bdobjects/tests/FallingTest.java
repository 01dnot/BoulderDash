package inf101.v17.boulderdash.bdobjects.tests;

import static org.junit.Assert.*;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

import inf101.v17.boulderdash.Direction;
import inf101.v17.boulderdash.IllegalMoveException;
import inf101.v17.boulderdash.Position;
import inf101.v17.boulderdash.bdobjects.AbstractBDFallingObject;
import inf101.v17.boulderdash.bdobjects.BDDiamond;
import inf101.v17.boulderdash.bdobjects.BDEmpty;
import inf101.v17.boulderdash.bdobjects.BDPlayer;
import inf101.v17.boulderdash.bdobjects.BDRock;
import inf101.v17.boulderdash.bdobjects.BDSand;
import inf101.v17.boulderdash.bdobjects.IBDObject;
import inf101.v17.boulderdash.maps.BDMap;
import inf101.v17.datastructures.IGrid;
import inf101.v17.datastructures.MyGrid;
import javafx.scene.media.AudioClip;

public class FallingTest {

	private BDMap map;

	@Before
	public void setup() {
	}

	@Test
	public void diamondFallingTest2() {
		IGrid<Character> grid = new MyGrid<>(2, 5, ' ');
		grid.set(0, 4, 'd');
		grid.set(0, 0, '*');
		map = new BDMap(grid);

		checkFall(new Position(0, 4));
	}
	@Test
	public void rockFallingTest2() {
		IGrid<Character> grid = new MyGrid<>(2, 5, ' ');
		grid.set(0, 4, 'r');
		grid.set(0, 0, '*');
		map = new BDMap(grid);

		checkFall(new Position(0, 4));
	}

	@Test
	public void diamondFallingKillsPlayerTest() {
		// diamond two tiles above kills player
		IGrid<Character> grid = new MyGrid<>(2, 5, ' ');
		grid.set(0, 4, 'd');
		grid.set(0, 2, 'p');
		grid.set(0, 0, '*');
		map = new BDMap(grid);


		checkFall(new Position(0, 4));
		checkFall(new Position(0, 3));
		checkFall(new Position(0, 2));
		assertFalse(map.getPlayer().isAlive());
	}

	@Test
	public void rockFallingKillsPlayerTest() {
		// diamond two tiles above kills player
		IGrid<Character> grid = new MyGrid<>(2, 5, ' ');
		grid.set(0, 4, 'r');
		grid.set(0, 2, 'p');
		grid.set(0, 0, '*');
		map = new BDMap(grid);


		checkFall(new Position(0, 4));
		checkFall(new Position(0, 3));
		checkFall(new Position(0, 2));
		assertFalse(map.getPlayer().isAlive());
	}

	@Test
	public void diamondRestingOnPlayerDoesntKill() {
		// diamond on top of player doesn't kill player
		IGrid<Character> grid = new MyGrid<>(2, 5, ' ');
		grid.set(0, 3, 'd');
		grid.set(0, 2, 'p');
		grid.set(0, 0, '*');
		map = new BDMap(grid);

		// four steps later, diamond still shouldnt fall and kill player.
		map.step();
		map.step();
		map.step();
		map.step();
		assertTrue(map.getPlayer().isAlive());
	}
	@Test
	public void rockRestingOnPlayerDoesntKill() {
		// rock on top of player doesn't kill player
		IGrid<Character> grid = new MyGrid<>(2, 5, ' ');
		grid.set(0, 3, 'r');
		grid.set(0, 2, 'p');
		grid.set(0, 0, '*');
		map = new BDMap(grid);

		// four steps later, rock still shouldnt fall and kill player.
		map.step();
		map.step();
		map.step();
		map.step();
		assertTrue(map.getPlayer().isAlive());
	}

	@Test
	public void diamondFallingTest1() {
		IGrid<Character> grid = new MyGrid<>(2, 5, ' ');
		grid.set(0, 4, 'd');
		grid.set(0, 0, '*');
		grid.set(1, 0, '*');
		map = new BDMap(grid);
		IBDObject obj = map.get(0, 4);
		assertTrue(obj instanceof BDDiamond);

		// four steps later, we've fallen down one step
		map.step();
		map.step();
		map.step();
		map.step();
		assertEquals(obj, map.get(0, 3));

		map.step();
		map.step();
		map.step();
		map.step();
		assertEquals(obj, map.get(0, 2));

		map.step();
		map.step();
		map.step();
		map.step();
		assertEquals(obj, map.get(0, 1));

		// wall reached, no more falling
		for (int i = 0; i < 10; i++)
			map.step();
		assertEquals(obj, map.get(0, 1));
	}

	@Test
	public void rockFallingTest1() {
		IGrid<Character> grid = new MyGrid<>(2, 5, ' ');
		grid.set(0, 4, 'r');
		grid.set(0, 0, '*');
		grid.set(1, 0, '*');
		map = new BDMap(grid);
		IBDObject obj = map.get(0, 4);
		assertTrue(obj instanceof BDRock);

		// four steps later, we've fallen down one step
		map.step();
		map.step();
		map.step();
		map.step();
		assertEquals(obj, map.get(0, 3));

		map.step();
		map.step();
		map.step();
		map.step();
		assertEquals(obj, map.get(0, 2));

		map.step();
		map.step();
		map.step();
		map.step();
		assertEquals(obj, map.get(0, 1));

		// wall reached, no more falling
		for (int i = 0; i < 10; i++)
			map.step();
		assertEquals(obj, map.get(0, 1));
	}

	@Test
	public void pushRockFromSideMovesRockTest() {
		IGrid<Character> grid = new MyGrid<>(5, 2, ' ');
		grid.set(3, 0, 'r');


		map = new BDMap(grid);
		IBDObject rock = map.get(3, 0);
		assertTrue(rock instanceof BDRock);

		try {
			((BDRock)rock).push(Direction.WEST);
		} catch (IllegalMoveException e) {
			fail("Cant push rock");
		}

		assertTrue(map.get(2, 0) instanceof BDRock);
		assertTrue(map.get(3, 0) instanceof BDEmpty);

	}

	@Test
	public void pushRockFromBelowDoesntMoveRockTest() {
		IGrid<Character> grid = new MyGrid<>(2, 5, ' ');
		grid.set(0, 2, 'r');
		map = new BDMap(grid);

		IBDObject rock = map.get(0, 2);
		assertTrue(rock instanceof BDRock);


		try {
			((BDRock)rock).push(Direction.NORTH);
		} catch (IllegalMoveException e) {
			return;
		}

		assertTrue(map.get(2, 0) instanceof BDRock);
		assertTrue(map.get(3, 0) instanceof BDEmpty);

	}

	@Test
	public void pushRockIntoObjectDoesntMoveRockTest() {
		IGrid<Character> grid = new MyGrid<>(3, 5, ' ');
		grid.set(2, 0, 'r');
		grid.set(1, 0, '#');
		map = new BDMap(grid);

		IBDObject rock = map.get(2, 0);
		IBDObject sand = map.get(1, 0);
		assertTrue(sand instanceof BDSand);
		assertTrue(rock instanceof BDRock);



		try {
			((BDRock)rock).push(Direction.WEST);
		} catch (IllegalMoveException e) {
			return;
		}

		assertTrue(map.get(2, 0) instanceof BDRock);
		assertTrue(map.get(1, 0) instanceof BDSand);
	}

	@Test
	public void fancyRocksFallingTest1() {
		IGrid<Character> grid = new MyGrid<>(2, 3, ' ');
		grid.set(1, 0, 'r');
		grid.set(1, 1, 'r');
		map = new BDMap(grid);

		IBDObject rock = map.get(1, 0);
		assertTrue(rock instanceof BDRock);
		rock = map.get(1,1);
		assertTrue(rock instanceof BDRock);
		for(int i=0; i<10; i++) {
			map.step();
		}


		IBDObject obj = map.get(0, 0);
		assertTrue(obj instanceof BDRock);


	}

	@Test
	public void fancyRocksFallingRandomTest2() {
		IGrid<Character> grid = new MyGrid<>(3, 3, ' ');
		grid.set(1, 0, 'r');
		grid.set(1, 1, 'r');
		map = new BDMap(grid);

		IBDObject rock = map.get(1, 0);
		assertTrue(rock instanceof BDRock);
		rock = map.get(1,1);
		assertTrue(rock instanceof BDRock);
		for(int i=0; i<10; i++) {
			map.step();
		}


		IBDObject objOne = map.get(0, 0);
		IBDObject objTwo = map.get(2, 0);

		assertTrue(objOne instanceof BDRock || objTwo instanceof BDRock);
	}

	protected Position checkFall(Position pos) {
		IBDObject obj = map.get(pos);
		if (obj instanceof AbstractBDFallingObject) {
			Position next = pos.moveDirection(Direction.SOUTH);
			if (map.canGo(next)) {
				IBDObject target = map.get(next);
				if (target.isEmpty() || target.isKillable()) {
				} else {
					next = pos;
				}
			} else {
				next = pos;
			}

			//map.step(); System.out.println(map.getPosition(object));
			map.step();
			map.step();
			map.step();
			map.step();
			assertEquals(obj, map.get(next));
			return next;
		} else
			return pos;
	}

}
