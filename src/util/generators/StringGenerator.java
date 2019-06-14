package util.generators;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import util.IGenerator;

public class StringGenerator implements IGenerator<String> {

	public static void main(String[] args) {
		Random r = new Random();

		System.out.println(new StringGenerator().generateEquals(r, 10));
	}

	private final int maxLength;

	private final int minLength;

	public StringGenerator() {
		this.minLength = 0;
		this.maxLength = 15;
	}

	public StringGenerator(int maxLength) {
		if (maxLength < 0) {
			throw new IllegalArgumentException("maxLength must be positive or 0");
		}
		this.minLength = 0;
		this.maxLength = maxLength;
	}

	public StringGenerator(int minLength, int maxLength) {
		if (minLength >= maxLength) {
			throw new IllegalArgumentException("minLength must be less than maxLength");
		}
		this.minLength = minLength;
		this.maxLength = maxLength;
	}

	@Override
	public String generate() {
		return generate(IntGenerator.getRandom());
	}

	@Override
	public String generate(Random r) {
		int len = r.nextInt(maxLength - minLength) + minLength;
		StringBuilder b = new StringBuilder();
		for (int i = 0; i < len; i++) {
			b.append(generateChar(r));
		}
		return b.toString();
	}

	public char generateChar(Random r) {
		int kind = r.nextInt(7);

		switch (kind) {
		case 0:
		case 1:
		case 2:
			return (char) ('a' + r.nextInt(26));
		case 3:
		case 4:
			return (char) ('A' + r.nextInt(26));
		case 5:
			return (char) ('0' + r.nextInt(10));
		case 6:
			return (char) (' ' + r.nextInt(16));
		}
		return ' ';
	}

	@Override
	public List<String> generateEquals(int n) {
		return generateEquals(IntGenerator.getRandom(), n);
	}

	@Override
	public List<String> generateEquals(Random r, int n) {
		long seed = r.nextLong();

		List<String> list = new ArrayList<String>();

		for (int i = 0; i < n; i++) {
			list.add(generate(new Random(seed)));
		}

		return list;
	}
}
