package util.generators;

import java.util.List;

public class Test {

	public static void main(String[] args) {
		StringGenerator gen = new StringGenerator();
		List<String> res = gen.generateEquals(5);
		for (String s : res) {
			System.out.println(s);
		}
	}
}
