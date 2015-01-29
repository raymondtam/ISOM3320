package main;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import javafx.application.Application;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;


public class Gameboard extends Application implements EventHandler<ActionEvent> {
	
	//constant
	final int NO_OF_BULLET_TYPE = 3; 
	final int[] BULLET_DAMAGE = {2, 3, 5};
	final int[] MAGAZINE_SIZE = {15, 40, 100};
	final int MAX_MAGAZINE_SIZE = 40;
	final int DEFAULT_BULLET_DAMAGE = BULLET_DAMAGE[0];
	final int DEFAULT_MAGAZINE_SIZE = MAGAZINE_SIZE[1];
	final double DEFAULT_RADIUS = 0;
	final Font DEFAULT_FONT = Font.font("irisupc", 50);
	
	//game variable and objects
	String name = "Player1";
	int score = 0;
	String[] topThreeScores={"nil", "nil", "nil"};
	
	Bullet[] bullet = Bullet.getBulletArray(MAX_MAGAZINE_SIZE, DEFAULT_BULLET_DAMAGE, DEFAULT_MAGAZINE_SIZE, DEFAULT_RADIUS);
	static Player player;
	//Target target;
	//Boss boss;
	
	//graphics and animation variable
	Timeline timeline;
	private ImageView background, playerImage, zombieImage, bulletImage, HPIconImage;
    private Label HPLabel = new Label(), BulletLabel = new Label();
    private IntegerProperty HPIntegerProperty, BulletIntegerProperty;

	
	public static void main(String[] arg){
		launch(arg);
	}
	
	@Override
	public void start(Stage stage){
				
		//player = new Player(); 
		
		Pane pane = new Pane();
		Image road = null, player= null, zombie = null, bullet = null;
		Image machinegun = null, rifile = null, HPIcon = null;
		ImageView dummy, dummy1;
		
		//Loading images and setting GUI
		try {
			road =  new Image("background.jpg");
			player = new Image("pistol.png");
			machinegun = new Image("machinegun.png");
			rifile = new Image("rifile.png");
			zombie = new Image("zombie.png");
			HPIcon = new Image("HP.gif");
			System.out.println("Image being imported.");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Initialize the variable and set necessary property
		background = new ImageView(road);
		playerImage = new ImageView(player);
		playerImage.setRotate(90);
		zombieImage = new ImageView(zombie);
		zombieImage.setRotate(270);
		HPIconImage = new ImageView(HPIcon);
		HPIconImage.setOpacity(0.6);
		
		dummy = new ImageView(machinegun);
		dummy.setRotate(90);
		dummy.setY(200);
		dummy1 = new ImageView(rifile);
		dummy1.setRotate(90);
		
		HPIntegerProperty = new SimpleIntegerProperty(100);
        HPLabel.textProperty().bind(HPIntegerProperty.asString());
        HPLabel.setTextFill(Color.YELLOW);
        HPLabel.setFont(DEFAULT_FONT);
        HPLabel.setOpacity(0.8);
        
        BulletIntegerProperty = new SimpleIntegerProperty(15);
        BulletLabel.textProperty().bind(BulletIntegerProperty.asString());
        BulletLabel.setTextFill(Color.YELLOW);
        BulletLabel.setFont(DEFAULT_FONT);
        BulletLabel.setOpacity(0.75);
        
		pane.getChildren().addAll(background, playerImage, zombieImage, HPLabel, BulletLabel, dummy, dummy1, HPIconImage);

		stage.setScene(new Scene(pane));
		stage.sizeToScene();
		stage.setResizable(false);
		stage.setTitle("ISOM3320 Game");
		stage.setFullScreen(false);
		
		Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX(primaryScreenBounds.getMinX());
        stage.setY(primaryScreenBounds.getMinY());
        stage.setWidth(primaryScreenBounds.getWidth());
        stage.setHeight(primaryScreenBounds.getHeight());
        
        //Set Positioning
		playerImage.setY(primaryScreenBounds.getHeight()/2);
		playerImage.setX(10);
		zombieImage.setX(primaryScreenBounds.getWidth()/2);
		zombieImage.setY(primaryScreenBounds.getHeight()/2);
//		HPLabel.setTranslateX(800);
		HPLabel.setTranslateX(70);
		HPLabel.setTranslateY(600);
		HPIconImage.setTranslateX(5);
		HPIconImage.setTranslateY(585);
		BulletLabel.setTranslateY(600);
		BulletLabel.setTranslateX(845);
		
		stage.show();
		System.out.println("Stage being showed.");
		
		
		//TO-DO, bind integer property with health and bulletnumber
		
		
		
		
		Timeline he = new Timeline();
		he.getKeyFrames().add( new KeyFrame(new Duration(12000), this));
		
		
	}

	@Override
	public void handle(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public static Point2D getPlyaerPosition(){
		Point2D position = player.getPosition();
		return position;
	} 
	
}
