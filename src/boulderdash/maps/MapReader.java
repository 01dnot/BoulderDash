package boulderdash.maps;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import datastructures.IGrid;
import datastructures.MyGrid;

/**
 * A class to read a Boulder Dash map from a file. After the file is read, the
 * map is stored as an {@link #IGrid} whose entries are characters. This can
 * then be used to be passed to the constructor in {@link #BDMap}.
 * 
 * The first line of the file should could contain two numbers. First the width
 * and then the height of the map. After that follows a matrix of characters
 * describing which object goes where in the map. '*' for wall, ' ' for empty,
 * 'P' for the player, 'b' for a bug, 'r' for a rock, and '#' for sand. For
 * example
 * 
 * <pre>
 * {@code
 * 5 6
 * *## p
 * * rr#
 * *####
 * *   *
 * *   d
 *    b 
 * }
 * </pre>
 * 
 * @author larsjaffke
 *
 */
public class MapReader {

	/**
	 * The path to the file to be read.
	 */
	protected String path;

	/**
	 * The map read from the file, represented as a grid of characters.
	 */
	protected IGrid<Character> characterMap;

	// the width and height of the resulting map.
	protected int width, height;

	/**
	 * Main constructor. Only requires the path for the file to be read. Note
	 * that in order to read the file, the method {@link #read()} has to be
	 * invoked.
	 * 
	 * @param path
	 */
	public MapReader(String path) {
		this.path = path;
	}

	/**
	 * Invoke this method to read the file whose path has been passed to the
	 * constructor.
	 * 
	 * @return the Boulder Dash map as a grid of characters read from the file
	 */
	public IGrid<Character> read() {
		try (Scanner in = new Scanner(new File(path))) {
			this.width = in.nextInt();
			this.height = in.nextInt();
			System.out.println(width + " " + height);
			characterMap = new MyGrid<Character>(width, height, ' ');
			in.nextLine();
			fillMap(in);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return characterMap;
	}

	/**
	 * This method fills the previously initialized {@link #characterMap} with
	 * the characters read from the file.
	 */
	private void fillMap(Scanner in) {
		char[] line;
		int y = 0;
		while (in.hasNextLine()) {
			line = in.nextLine().toCharArray();
			for (int x = 0; x < width; x++) {
				characterMap.set(x, height - y - 1, line[x]);
			}
			y++;
		}
	}
}
