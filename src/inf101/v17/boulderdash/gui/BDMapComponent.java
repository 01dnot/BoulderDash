package inf101.v17.boulderdash.gui;

import inf101.v17.boulderdash.maps.BDMap;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

/**
 * A container class for a Boulder Dash map inside a paintable component.
 * 
 * @author larsjaffke
 *
 */
public class BDMapComponent extends Canvas {
	// parameters on the sizes between the cells.
	public static final int CELL_PADDING = 0;

	// The map to be painted
	private BDMap map;

	public BDMapComponent(BDMap map) {
		this.map = map;
	}

	public void draw(Camera cam, double vboxHeigth) {
		GraphicsContext g = getGraphicsContext2D();
		g.clearRect(0, 0, getWidth(), getHeight());
		g.save();
		double cellDim = Math.min(getWidth() / map.getWidth() - CELL_PADDING,
				getHeight() / map.getHeight() - CELL_PADDING);
		double scale = (getHeight()-vboxHeigth) / (cam.getHeigth()*(cellDim+CELL_PADDING)+CELL_PADDING);
		g.scale(scale, scale);
		

		int width = cam.getWidth(), height = cam.getHeigth();
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				g.setFill(map.get(cam.getX() + x, cam.getY() + height - y - 1).getColor());
				g.fillRect(x * (cellDim + CELL_PADDING) + CELL_PADDING, y * (cellDim + CELL_PADDING) + CELL_PADDING, cellDim,
						cellDim);
			}
		}
		g.restore();
	}

}
