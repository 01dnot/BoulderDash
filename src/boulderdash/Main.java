package boulderdash;

import boulderdash.gui.BoulderDashGUI;
import boulderdash.maps.BDMap;
import boulderdash.maps.MapReader;
import datastructures.IGrid;

/**
 * Contains the main method to execute the program.
 *
 */
public class Main {

	public static void main(String[] args) {
		// This is how you set up the program, change the file path accordingly.
		MapReader reader = new MapReader("level1.txt");
		IGrid<Character> rawGrid = reader.read();
		BDMap map = new BDMap(rawGrid);
		BoulderDashGUI.run(map);
	}

}
