package util;

import java.util.List;

import util.generators.StringGenerator;

public class StringGeneratorDemo {

	public static void main(String[] args) {
		IGenerator<String> stringGen = new StringGenerator();
		for (int i = 0; i < 5; ++i) {
			System.out.println(stringGen.generate());
		}
		List<String> strlist = stringGen.generateEquals(5);
		for (int i = 0; i < strlist.size(); ++i) {
			System.out.println(strlist.get(i));
		}
	}

}
