package util.generators;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import util.IGenerator;

public class IntGenerator implements IGenerator<Integer> {
	private static final Random commonRandom = new Random();

	public static Random getRandom() {
		return commonRandom;
	}

	/**
	 * Generate a random long in the interval [0,n)
	 *
	 * @param rng
	 *            A random generator
	 * @param n
	 *            The maximum value (exclusive)
	 * @return A uniformly distributed random integer in the range 0 (inclusive)
	 *         to n (exclusive)
	 *
	 * @author Adapted from JDK implementation for nextInt(n)
	 */
	public static long nextLong(Random rng, long n) {
		if (n <= 0) {
			throw new IllegalArgumentException("n must be positive");
		}

		long bits, val;
		do {
			bits = rng.nextLong() & ~Long.MIN_VALUE; // force to positive
			val = bits % n;
		} while (bits - val + n - 1L < 0L);
		return val;
	}

	private final long diff;

	private final long minValue;

	/**
	 * Make a generator for the full range of integers.
	 */
	public IntGenerator() {
		this.minValue = Integer.MIN_VALUE;
		long maxValue = Integer.MAX_VALUE + 1L; // generate up to and including
												// Integer.MAX_VALUE
		diff = maxValue - minValue;
	}

	/**
	 * Make a generator for positive numbers from 0 (inclusive) to maxValue
	 * (exclusive).
	 *
	 * @param maxValue
	 *            The max value, or 0 for the full range of positive integers
	 */
	public IntGenerator(int maxValue) {
		if (maxValue < 0) {
			throw new IllegalArgumentException("maxValue must be positive or 0");
		}
		this.minValue = 0;
		long mv = Integer.MAX_VALUE + 1L; // generate up to and including
											// Integer.MAX_VALUE
		if (maxValue != 0) {
			mv = maxValue;
		}
		diff = mv - minValue;
	}

	/**
	 * Make a generator for numbers from minValue (inclusive) to maxValue
	 * (exclusive).
	 *
	 * @param minValue
	 *            The minimum value
	 * @param maxValue
	 *            The maximum value, minValue < maxValue
	 */
	public IntGenerator(int minValue, int maxValue) {
		if (minValue >= maxValue) {
			throw new IllegalArgumentException("minValue must be less than maxValue");
		}
		this.minValue = minValue;
		diff = (long) maxValue - (long) minValue;
	}

	@Override
	public Integer generate() {
		return generate(commonRandom);
	}

	@Override
	public Integer generate(Random rng) {
		long r = minValue + nextLong(rng, diff);

		return (int) r;
	}

	@Override
	public List<Integer> generateEquals(int n) {
		return generateEquals(commonRandom, n);
	}

	@Override
	public List<Integer> generateEquals(Random rng, int n) {
		Integer gen = generate(rng);
		List<Integer> list = new ArrayList<Integer>(n);
		for (int i = 0; i < n; i++) {
			list.add(gen);
		}
		return list;
	}
}
