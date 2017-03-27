package inf101.v17.boulderdash.bdobjects.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import inf101.v17.boulderdash.Position;
import inf101.v17.boulderdash.bdobjects.BDBug;
import inf101.v17.boulderdash.bdobjects.BDEmpty;
import inf101.v17.boulderdash.bdobjects.BDPlayer;
import inf101.v17.boulderdash.bdobjects.BDRock;
import inf101.v17.boulderdash.bdobjects.BDWall;
import inf101.v17.boulderdash.bdobjects.IBDObject;
import inf101.v17.boulderdash.maps.BDMap;
import inf101.v17.datastructures.IGrid;
import inf101.v17.datastructures.MyGrid;
import javafx.scene.input.KeyCode;

public class PlayerTest {

	private BDMap map;


	@Test
	public void playerMoveTest() {
		IGrid<Character> grid = new MyGrid<>(4, 4, ' ');
		grid.set(2, 2, 'p');
		map = new BDMap(grid);

		// find the player
		Position playerPos = new Position(2,2);
		IBDObject player = map.get(playerPos);
		assertTrue(player instanceof BDPlayer);

		// move player left
		((BDPlayer)player).keyPressed(KeyCode.LEFT);
		map.step();
		assertEquals(map.get(1,2), player);

		// move player right
		((BDPlayer)player).keyPressed(KeyCode.RIGHT);
		map.step();
		assertEquals(map.get(2,2), player);

		// move player up
		((BDPlayer)player).keyPressed(KeyCode.UP);
		map.step();
		assertEquals(map.get(2,3), player);

		// move player down
		((BDPlayer)player).keyPressed(KeyCode.DOWN);
		map.step();
		assertEquals(map.get(2,2), player);


	}

	@Test
	public void playerMovesOldPositionIsEmptyTest() {
		IGrid<Character> grid = new MyGrid<>(4, 4, ' ');
		grid.set(2, 2, 'p');
		map = new BDMap(grid);

		// find the player
		Position playerPos = new Position(2,2);
		IBDObject player = map.get(playerPos);
		assertTrue(player instanceof BDPlayer);

		// move player left
		((BDPlayer)player).keyPressed(KeyCode.LEFT);
		map.step();
		assertEquals(map.get(1,2), player);

		//check that old position is now BDEmpty
		assertTrue(map.get(2,2) instanceof BDEmpty);
	}

	@Test
	public void playerPicksUpDiamondTest() {
		IGrid<Character> grid = new MyGrid<>(4, 4, ' ');
		grid.set(2, 2, 'p');
		grid.set(2, 3, 'd');
		map = new BDMap(grid);

		// find the player
		Position playerPos = new Position(2,2);
		IBDObject player = map.get(playerPos);
		assertTrue(player instanceof BDPlayer);
		int diamondCountBefore = ((BDPlayer)player).numberOfDiamonds();

		// move player up
		((BDPlayer)player).keyPressed(KeyCode.UP);
		map.step();
		//Check if diamond is picked up
		int diamondCountAfter = ((BDPlayer)player).numberOfDiamonds();
		assertEquals(diamondCountAfter,(diamondCountBefore + 1));	
	}
	@Test
	public void playerPushRockTest() {
		IGrid<Character> grid = new MyGrid<>(4, 4, ' ');
		grid.set(0, 0, 'p');
		grid.set(1, 0, 'r');
		map = new BDMap(grid);

		// find the player
		Position playerPos = new Position(0,0);
		IBDObject player = map.get(playerPos);
		assertTrue(player instanceof BDPlayer);

		// move player right
		((BDPlayer)player).keyPressed(KeyCode.RIGHT);
		map.step();

		//check that rock has moved to the right
		assertTrue(map.get(2, 0) instanceof BDRock);
		//check that player has replaced the rocks old position
		assertTrue(map.get(1,0) instanceof BDPlayer);

	}

	@Test
	public void playerCantGoTest() {
		IGrid<Character> grid = new MyGrid<>(4, 4, ' ');
		grid.set(2, 2, 'p');
		grid.set(3, 2, '*');

		map = new BDMap(grid);

		// find the player
		Position playerPos = new Position(2,2);
		IBDObject player = map.get(playerPos);
		assertTrue(player instanceof BDPlayer);

		// move player right
		((BDPlayer)player).keyPressed(KeyCode.RIGHT);
		map.step();

		assertTrue(map.get(3, 2) instanceof BDWall);
		assertTrue(map.get(2, 2) instanceof BDPlayer);

	}

}
