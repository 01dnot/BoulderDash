package inf101.v17.boulderdash.gui;

import inf101.v17.boulderdash.maps.BDMap;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * The main class of the BoulderDash program. Sets up a simple gui of a given
 * map and handles the timing.
 *
 */
public class BoulderDashGUI extends Application implements EventHandler<KeyEvent> {
	
	private VBox vbox;
	private Camera cam;
	/**
	 * Determines how many milliseconds pass between two steps of the program.
	 */
	private static final int SPEED = 70;

	private static BDMap theMap;

	public static final double NOMINAL_WIDTH = 1900;
	public static final double NOMINAL_HEIGHT = 1000;
	private Stage stage;

	/**
	 * Runs the program on a given map.
	 *
	 * @param map
	 */
	public static void run(BDMap map) {
		theMap = map;
		launch();
	}

	@Override
	public void start(Stage stage) throws Exception {
		cam = new Camera(0, 0,theMap);
		this.stage = stage;
		double spacing = 10;
		
		Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();

		Group root = new Group();
		int padding = BDMapComponent.CELL_PADDING;
		
		double width = Math.min(primaryScreenBounds.getWidth() - 40, cam.getWidth()*(16+padding)+padding);				//TODO
		double height = Math.min(primaryScreenBounds.getHeight() - 100, cam.getHeigth()*(16+padding)+padding + 40);		//TODO
		Scene scene = new Scene(root, width, height,
				Color.BLACK);
		stage.setScene(scene);

		message = new Text(10, 0, "");
		message.setFont(new Font(26));
		message.setFill(Color.WHEAT);
		message.setText("Diamonds: " + map.getPlayer().numberOfDiamonds());

		mapComponent = new BDMapComponent(map);
		mapComponent.widthProperty().bind(scene.widthProperty());
		mapComponent.heightProperty().bind(scene.heightProperty());
		mapComponent.heightProperty().bind(Bindings.subtract(Bindings.subtract(scene.heightProperty(), 
				message.getLayoutBounds().getHeight()), spacing));
		// mapComponent.setScaleY(-1.0);


		timer = new AnimationTimer() {

			private long lastUpdateTime;

			@Override
			public void handle(long now) {
				if (now - lastUpdateTime > SPEED * 1_000_000) {
					lastUpdateTime = now;
					step();
				}
			}

		};

		vbox = new VBox(); 							//TODO
		vbox.setSpacing(0); 						//TODO
		VBox testVBox = new VBox();					//TODO
		testVBox.getChildren().add(message);		//TODO
		testVBox.setPrefHeight(40);					//TODO
		vbox.getChildren().add(testVBox);			//TODO
		vbox.getChildren().add(mapComponent);		//TODO
		root.getChildren().add(vbox);				//TODO

		stage.addEventHandler(KeyEvent.KEY_PRESSED, this); //TODO
		timer.start();
		stage.show();
		scene.heightProperty().addListener((obs, old, newH) -> {
			
		});
	}

	private BDMap map;
	private BDMapComponent mapComponent;

	private Text message;

	private AnimationTimer timer;

	public BoulderDashGUI() {
		this.map = theMap;
	}

	protected void step() {
		if (map.getPlayer().isAlive()) {
			map.step();
			cam.step(map.getPlayer());
			message.setText("Diamonds: " + map.getPlayer().numberOfDiamonds());
		} else {
			message.setText("Player is dead.");
		}
		mapComponent.draw(cam, vbox.getMaxHeight());
	}

	@Override
	public void handle(KeyEvent event) {
		KeyCode code = event.getCode();
		if (code == KeyCode.ESCAPE || code == KeyCode.Q) {
			System.exit(0);
		} else if (code == KeyCode.F) {
			stage.setFullScreen(!stage.isFullScreen());
		} else {

			map.getPlayer().keyPressed(code);
		}
	}
}
