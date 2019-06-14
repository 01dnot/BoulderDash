package tests;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import datastructures.IGrid;
import util.IGenerator;
import util.generators.IntGenerator;
import util.generators.MyGridGenerator;
import util.generators.StringGenerator;

public class MyGridTest {
	private static final int N = 1000000;

	private IGenerator<String> strGen = new StringGenerator();
	private IGenerator<IGrid<String>> gridGen = new MyGridGenerator<String>(strGen);

	/**
	 * A set on (x1,y1) doesn't affect a get on a different (x2,y2)
	 */
	private <T> void setGetIndependentProperty(IGrid<T> grid, int x1, int y1, int x2, int y2, T val) {
		if (x1 != x2 && y1 != y2) {
			T s = grid.get(x2, y2);
			grid.set(x1, y1, val);
			assertEquals(s, grid.get(x2, y2));
		}
	}

	@Test
	public void setGetIndependentTest() {
		IGrid<String> grid = gridGen.generate();

		IGenerator<Integer> wGen = new IntGenerator(0, grid.getWidth());
		IGenerator<Integer> hGen = new IntGenerator(0, grid.getHeight());

		for (int i = 0; i < N; i++) {
			int x1 = wGen.generate();
			int y1 = hGen.generate();
			int x2 = wGen.generate();
			int y2 = hGen.generate();
			String s = strGen.generate();

			setGetIndependentProperty(grid, x1, y1, x2, y2, s);
		}
	}

	/**
	 * get(x,y) is val after set(x, y, val)
	 */
	private <T> void setGetProperty(IGrid<T> grid, int x, int y, T val) {
		if (x >= grid.getWidth() || x < 0) {
			return;
		}
		if (y >= grid.getHeight() || y < 0) {
			return;
		}

		grid.set(x, y, val);
		assertEquals(grid.get(x, y), val);
	}

	/**
	 * Test that get gives back the same value after set.
	 */
	@Test
	public void setGetTest() {
		IGrid<String> grid = gridGen.generate();

		IGenerator<Integer> wGen = new IntGenerator(0, grid.getWidth());
		IGenerator<Integer> hGen = new IntGenerator(0, grid.getHeight());

		for (int i = 0; i < N; i++) {
			int x = wGen.generate();
			int y = hGen.generate();
			String s = strGen.generate();

			setGetProperty(grid, x, y, s);
		}
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

}
