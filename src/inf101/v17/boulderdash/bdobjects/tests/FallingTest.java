package inf101.v17.boulderdash.bdobjects.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import inf101.v17.boulderdash.Direction;
import inf101.v17.boulderdash.Position;
import inf101.v17.boulderdash.bdobjects.AbstractBDFallingObject;
import inf101.v17.boulderdash.bdobjects.BDDiamond;
import inf101.v17.boulderdash.bdobjects.BDRock;
import inf101.v17.boulderdash.bdobjects.IBDObject;
import inf101.v17.boulderdash.maps.BDMap;
import inf101.v17.datastructures.IGrid;
import inf101.v17.datastructures.MyGrid;

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
	public void diamondFallingKills1() {
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
	public void rockFallingKills1() {
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
	public void diamondRestingDoesntKill1() {
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
	public void rockRestingDoesntKill1() {
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
