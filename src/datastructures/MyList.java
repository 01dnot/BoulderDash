package datastructures;

import java.util.Arrays;

public class MyList<T> implements IList<T> {
	private T[] data;
	private int length;

	@SuppressWarnings("unchecked")
	public MyList() {
		length = 0;
		data = (T[]) new Object[10];
	}

	@SuppressWarnings("unchecked")
	public MyList(int initialSize) {
		if (initialSize < 0) {
			throw new IllegalArgumentException("initialSize must be positive: " + initialSize);
		}
		length = 0;
		data = (T[]) new Object[initialSize];
	}

	@Override
	public void add(T s) {
		if (length == data.length) {
			data = Arrays.copyOf(data, data.length * 2);
		}

		data[length] = s;
		length = length + 1;
	}

	@Override
	public void clear() {
		length = 0;
	}

	@Override
	public T get(int i) {
		return data[i];
	}

	@Override
	public boolean isEmpty() {
		return length == 0;
	}

	@Override
	public T remove(int i) {
		T element = data[i];

		for (int x = i; x < length - 1; x++) {
			data[x] = data[x + 1];
		}

		length = length - 1;
		return element;
	}

	@Override
	public void set(int i, T s) {
		data[i] = s;
	}

	@Override
	public int size() {
		return length;
	}

}
