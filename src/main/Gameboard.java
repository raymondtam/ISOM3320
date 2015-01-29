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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;


public class Gameboard extends Application implements EventHandler<ActionEvent> {
	
	//constant
	final int NO_OF_BULLET_TYPE = 3; 
	final static int[] BULLET_DAMAGE = {2, 3, 5};
	final static int[] MAGAZINE_SIZE = {15, 40, 100};
	final static int MAX_MAGAZINE_SIZE = 100;
	final static int DEFAULT_BULLET_DAMAGE = BULLET_DAMAGE[0];
	final static int DEFAULT_MAGAZINE_SIZE = MAGAZINE_SIZE[0];
	final static double DEFAULT_RADIUS = 0;
	final Font DEFAULT_FONT = Font.font("irisupc", 50);
	
	//game variable and objects
	String name = "Player1";
	int score = 0;
	String[] topThreeScores={"nil", "nil", "nil"};
	
	static Bullet[] bullet = Bullet.getBulletArray(MAX_MAGAZINE_SIZE, DEFAULT_BULLET_DAMAGE, DEFAULT_MAGAZINE_SIZE, DEFAULT_RADIUS);
	static Player player = new Player(bullet);
	//Target target;
	//Boss boss;
	
	Point2D CursorPosition;
	
	//graphics and animation variable
	Timeline timeline;
	private ImageView backgroundImageView, playerImageView, zombieImageView, HPIconImageView;
	private ImageView[] bulletImageView; 
    private Label HPLabel = new Label(), BulletLabel = new Label();
    private IntegerProperty HPIntegerProperty, BulletIntegerProperty;
    
    boolean moveLeft = false, moveRight = false, moveUp = false, moveDown = false;
	
	public static void main(String[] arg){
		launch(arg);
	}
	
	@Override
	public void start(Stage stage){
				
		//player = new Player(); 
		
		Pane pane = new Pane();
		Image roadImage = null;
		Image playerImage= null;
		Image zombieImage = null, bulletImage = null;
		Image machinegunImage = null, rifileImage = null, HPIconImage = null;
		ImageView dummy, dummy1;
		timeline = new Timeline();				
		
		//Loading images and setting GUI
		try {
			roadImage =  new Image("background.jpg");
			playerImage = new Image("pistol.png");
			machinegunImage = new Image("machinegun.png");
			rifileImage = new Image("rifile.png");
			zombieImage = new Image("boss.gif");
			HPIconImage = new Image("HP.gif");
			bulletImage = new Image("bullet.png");
			System.out.println("Image being imported.");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Initialize the variable and set necessary property
		backgroundImageView = new ImageView(roadImage);
		playerImageView = new ImageView(playerImage);
		playerImageView.setRotate(90);
		zombieImageView = new ImageView(zombieImage);
		zombieImageView.setRotate(270);
		HPIconImageView = new ImageView(HPIconImage);
		HPIconImageView.setOpacity(0.6);
		
		dummy = new ImageView(machinegunImage);
		dummy.setRotate(90);
		dummy.setY(200);
		dummy1 = new ImageView(rifileImage);
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
        
        bulletImageView = new ImageView[MAX_MAGAZINE_SIZE];
        
        for(int i=0 ; i<bulletImageView.length ; i++ ){
        	bulletImageView[i] = new ImageView(bulletImage);
        }
		
        
		pane.getChildren().addAll(backgroundImageView, playerImageView, zombieImageView, HPLabel, BulletLabel, dummy, dummy1, HPIconImageView);
		

		for(ImageView i : bulletImageView){
			pane.getChildren().addAll(i);
			i.setVisible(false);
		} 
		
		Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX(primaryScreenBounds.getMinX());
        stage.setY(primaryScreenBounds.getMinY());
        stage.setWidth(primaryScreenBounds.getWidth());
        stage.setHeight(primaryScreenBounds.getHeight());
        
        //Set Positioning
        
        player.setPosition(10, primaryScreenBounds.getHeight()/2);
        
		playerImageView.setY(primaryScreenBounds.getHeight()/2);
		playerImageView.setX(10);
		zombieImageView.setX(200);
		zombieImageView.setY(200);
		HPLabel.setTranslateX(70);
		HPLabel.setTranslateY(600);
		HPIconImageView.setTranslateX(5);
		HPIconImageView.setTranslateY(585);
		BulletLabel.setTranslateY(600);
		BulletLabel.setTranslateX(845);
		
		Scene scene = new Scene(pane);
		
		
		
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent key) {
            	KeyCode keycode = key.getCode();
                switch(keycode){
	            	case W:
	            		moveUp = true;
	            		break;
	            	case A:
	            		moveLeft = true;
	            		break;
	            	case S:
	            		moveDown = true;
	            		break;
	            	case D:
	            		moveRight = true;
            	}
               	key.consume();
//             	System.out.println("UP: " + moveUp + " Down: " + moveDown + " Left: " + moveLeft + " Right: " + moveRight);
//             	System.out.println(key.toString());
            }
        });
        
        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent key) {  //KeyEvent.keyreleased
            	KeyCode keycode = key.getCode();
            	switch(keycode){
	            	case W:
	            		moveUp = false;
	            		break;
	            	case A:
	            		moveLeft = false;
	            		break;
	            	case S:
	            		moveDown = false;
	            		break;
	            	case D:
	            		moveRight = false;
            	}
            	key.consume();
//             	System.out.println("UP: " + moveUp + " Down: " + moveDown + " Left: " + moveLeft + " Right: " + moveRight);
//             	System.out.println(key.toString());

            }
        });
        
        scene.setOnMouseClicked(new EventHandler<MouseEvent>(){
        	@Override
			public void handle(MouseEvent mouse) {
//				System.out.println("mouse clicked, Number of Unused Bullets: "+player.getNumberOfUnusedBullet());
        		double angle = getFireAngle(mouse.getX(), mouse.getY()); 
				//player.fire(mouse.getX(), mouse.getY(), angle);
        		player.fire(mouse.getX(), mouse.getY());
//				System.out.println("After fired,  "+player.getNumberOfUnusedBullet());
			}
        	
        });
        
        scene.setOnMouseMoved(new EventHandler<MouseEvent>() {
        	public void handle(MouseEvent mouse){
        		double angle = getFireAngle(mouse.getX(), mouse.getY()); 
        		//System.out.println(angle);
                if(mouse.getY()<player.getYcoord())
                	playerImageView.setRotate(90-angle);
                else
                	playerImageView.setRotate(90+angle);
        	}
        });
                
		stage.setScene(scene);
		stage.sizeToScene();
		stage.setResizable(false);
		stage.setTitle("ISOM3320 Game");
		stage.setFullScreen(false);
		
//		timeline.getKeyFrames().add(new KeyFrame(new Duration(100), this));
//		timeline.setCycleCount(Timeline.INDEFINITE);
//		timeline.play();
				
		timeline.getKeyFrames().add( new KeyFrame(new Duration(33), this));
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.play();
		
		stage.show();
		System.out.println("Stage being showed.");
		
		
		//new Thread(this).start();
		
		//TO-DO, bind integer property with health and bulletnumber
			

		
	}
	
	double getFireAngle(double x, double y){
		Point2D xVector = new Point2D(1, 0);
		Point2D Vector = player.getPosition().multiply(-1).add(x, y);   // cursor vector subtract player vector
//	equivalent to
//		Point2D mouseVector = new Point2D(mouse.getX(), mouse.getY());
//		Point2D Vector = mouseVector.subtract(player.getPosition());
		double angle = xVector.angle(Vector);
		
        return angle; 
	}
	
	

//    public void run() {
//        while(true) {
//            try {
//
//                Thread.sleep(200);
//            } catch(Exception exc) {
//                exc.printStackTrace();
//                break;
//            }
//        }
//    }

	@Override
	public void handle(ActionEvent e) {
		// TODO Auto-generated method stub
		
		//player movement
        if(moveUp)
        	player.move(0, -5);
        if(moveDown)
        	player.move(0, 5);
        if(moveLeft)
        	player.move(-5, 0);
        if(moveRight)
        	player.move(5, 0);
        playerImageView.setX(player.getXcoord());
        playerImageView.setY(player.getYcoord());
        
        //bullet movement
        for(int i=0; i < Bullet.getMagazineSize() ; i++){
        	if(bullet[i].getIsMoving()){
        	//if(bullet[i].getXVelocity()>0 || bullet[i].getYVelocity()>0){
        		bulletImageView[i].setVisible(true);
        		bullet[i].move(bullet[i].getXVelocity()*20, bullet[i].getYVelocity()*20);
        		bulletImageView[i].setX(bullet[i].getXcoord());
        		bulletImageView[i].setY(bullet[i].getYcoord());
        	}
        }
        
        //target movement
        
        
        //check ishit()
		
	}
	
	public static Point2D getPlyaerPosition(){
		Point2D position = player.getPosition();
		return position;
	} 
	
	
	
}
