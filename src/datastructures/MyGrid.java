package datastructures;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * A Grid contains a set of cells in a square 2D matrix.
 *
 */
public class MyGrid<T> implements IGrid<T> {
	private List<T> cells;
	private int height;
	private int width;

	/**
	 *
	 * Construct a grid with the given dimensions.
	 *
	 * @param width
	 * @param height
	 * @param initElement
	 *            What the cells should initially hold (possibly null)
	 */
	public MyGrid(int width, int height, T initElement) {
		if (width <= 0 || height <= 0) {
			throw new IllegalArgumentException();
		}

		this.height = height;
		this.width = width;
		cells = new ArrayList<T>(height * width);
		for (int i = 0; i < height * width; ++i) {
			cells.add(initElement);
		}
	}

	@Override
	public IGrid<T> copy() {
		MyGrid<T> newGrid = new MyGrid<>(getWidth(), getHeight(), null);

		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				newGrid.set(x, y, get(x, y));
			}
		}
		return newGrid;
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
		MyGrid<?> other = (MyGrid<?>) obj;
		if (cells == null) {
			if (other.cells != null) {
				return false;
			}
		} else if (!cells.equals(other.cells)) {
			return false;
		}
		if (height != other.height) {
			return false;
		}
		if (width != other.width) {
			return false;
		}
		return true;
	}

	@Override
	public T get(int x, int y) {
		if (x < 0 || x >= width) {
			throw new IndexOutOfBoundsException();
		}
		if (y < 0 || y >= height) {
			throw new IndexOutOfBoundsException();
		}

		return cells.get(x + y * width);
	}

	@Override
	public int getHeight() {
		return height;
	}

	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (cells == null ? 0 : cells.hashCode());
		result = prime * result + height;
		result = prime * result + width;
		return result;
	}

	@Override
	public void set(int x, int y, T elem) {
		if (x < 0 || x >= width) {
			throw new IndexOutOfBoundsException();
		}
		if (y < 0 || y >= height) {
			throw new IndexOutOfBoundsException();
		}

		cells.set(x + y * width, elem);
	}

}
