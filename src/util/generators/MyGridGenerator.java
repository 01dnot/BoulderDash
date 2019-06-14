package util.generators;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import datastructures.IGrid;
import datastructures.MyGrid;
import util.IGenerator;

public class MyGridGenerator<T> implements IGenerator<IGrid<T>> {
	/**
	 * Generator for one element of a random grid
	 */
	private final IGenerator<T> elementGenerator;
	/**
	 * Generator for the height of a random grid
	 */
	private final IGenerator<Integer> heightGenerator;
	/**
	 * Generator for the width of a random grid
	 */
	private final IGenerator<Integer> widthGenerator;

	public MyGridGenerator(IGenerator<T> elementGenerator) {
		this.elementGenerator = elementGenerator;
		this.widthGenerator = new IntGenerator(1, 100); // bredde blir et tall
														// fra 1 til 100
		this.heightGenerator = new IntGenerator(1, 100); // høyde blir et tall
															// fra 1 til 100
	}

	public MyGridGenerator(IGenerator<T> elementGenerator, IGenerator<Integer> widthGenerator,
			IGenerator<Integer> heightGenerator) {
		this.elementGenerator = elementGenerator;
		this.widthGenerator = widthGenerator;
		this.heightGenerator = heightGenerator;
	}

	public MyGridGenerator(IGenerator<T> elementGenerator, int maxWidth, int maxHeight) {
		if (maxWidth < 1 || maxHeight < 1) {
			throw new IllegalArgumentException("Width and height must be 1 or greater");
		}

		this.elementGenerator = elementGenerator;
		this.widthGenerator = new IntGenerator(1, maxWidth);
		this.heightGenerator = new IntGenerator(1, maxHeight);
	}

	@Override
	public IGrid<T> generate() {
		return generate(IntGenerator.getRandom());
	}

	@Override
	public IGrid<T> generate(Random r) {
		int width = widthGenerator.generate(r);
		int height = heightGenerator.generate(r);
		T initElem = elementGenerator.generate(r);

		IGrid<T> grid = new MyGrid<T>(width, height, initElem);

		for (int y = 0; y < grid.getHeight(); ++y) {
			for (int x = 0; x < grid.getWidth(); ++x) {
				grid.set(x, y, elementGenerator.generate(r));
			}
		}
		return grid;
	}

	@Override
	public List<IGrid<T>> generateEquals(int n) {
		return generateEquals(IntGenerator.getRandom(), n);
	}

	@Override
	public List<IGrid<T>> generateEquals(Random r, int n) {
		long seed = r.nextLong();

		List<IGrid<T>> list = new ArrayList<>();

		for (int i = 0; i < n; i++) {
			list.add(generate(new Random(seed)));
		}

		return list;
	}

}
