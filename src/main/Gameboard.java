package main;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;


public class Gameboard extends Application implements EventHandler<ActionEvent> {
	
	//constant
	final int NO_OF_BULLET_TYPE = 3; 
	final int[] BULLET_DAMAGE = {1, 3, 10};
	final int[] MAGAZINE_SIZE = {10, 40, 30};
	final int MAX_MAGAZINE_SIZE = 40;
	final int DEFAULT_BULLET_DAMAGE = BULLET_DAMAGE[0];
	final int DEFAULT_MAGAZINE_SIZE = MAGAZINE_SIZE[1];
	
	
	//game variable and objects
	String name = "Player1";
	int score = 0;
	String[] topThreeScores={"nil", "nil", "nil"};
	
	Bullet[] bullet = Bullet.getBulletArray(DEFAULT_BULLET_DAMAGE, MAX_MAGAZINE_SIZE);
	Player player;
	//Target target;
	//Boss boss;
	
	//graphics and animation variable
	Timeline timeline;
	private ImageView background;
	private ImageView playerImage, zombieImage;

	
	public static void main(String[] arg){
		launch(arg);
	}
	
	@Override
	public void start(Stage stage){
				
		//player = new Player(); 
		
		Pane pane = new Pane();
		Image road = null, player= null, zombie = null, bullet = null;
		
		//Loading images and setting GUI
		try {
			road =  new Image("road.jpg");
			player = new Image("player.png");
			zombie = new Image("zombie.png");
			System.out.println("Image being imported.");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		background = new ImageView(road);
		playerImage = new ImageView(player);
		zombieImage = new ImageView(zombie);
		zombieImage.setRotate(270);
			
		pane.getChildren().addAll(background, playerImage, zombieImage);

		stage.setScene(new Scene(pane));
		stage.sizeToScene();
		stage.setResizable(false);
		stage.setTitle("ISOM3320 Game");
		
		Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX(primaryScreenBounds.getMinX());
        stage.setY(primaryScreenBounds.getMinY());
        stage.setWidth(primaryScreenBounds.getWidth());
        stage.setHeight(primaryScreenBounds.getHeight());
                
		playerImage.setY(primaryScreenBounds.getHeight()/2);
		zombieImage.setX(primaryScreenBounds.getWidth()/2);
		zombieImage.setY(primaryScreenBounds.getHeight()/2);
		
		stage.show();
		System.out.println("Stage being showed.");
		
		
		
		
		
		
		
		Timeline he = new Timeline();
		he.getKeyFrames().add( new KeyFrame(new Duration(12000), this));
		
		
	}

	@Override
	public void handle(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
}
