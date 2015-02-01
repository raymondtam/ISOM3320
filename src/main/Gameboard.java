package main;

import java.util.concurrent.TimeUnit;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.util.Duration;
import javafx.application.Application;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
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
	//852 * 7680
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
	
	long reloadStartTime = 0;
	
//	static Bullet[] bullet = Bullet.getBulletArray(MAX_MAGAZINE_SIZE, DEFAULT_BULLET_DAMAGE, DEFAULT_MAGAZINE_SIZE, DEFAULT_RADIUS, 20);
	static Bullet[] bullet = Bullet.getBulletArray(100, DEFAULT_BULLET_DAMAGE, DEFAULT_MAGAZINE_SIZE, DEFAULT_RADIUS, 20);
	static Player player = new Player(bullet, 5);
	static Target[] target = Target.getTargetArray(10, 10, 7, 50); 
	//Boss boss;
	
	
	Point2D CursorPosition;
	
	//graphics and animation variable
	Pane pane;
	Scene scene;
	Timeline timeline, refreshScreen;
	private ImageView backgroundImageView, playerImageView, zombieImageView, HPIconImageView;
	private ImageView[] bulletImageView, targetImageView; 
	private MediaPlayer playHandGunShoot, playHandGunReload, playMachineGunShoot, playMachineGunReload; //Media
	private MediaView playHandGunShootMediaView, playHandGunReloadMediaView, playMachineGunShootMediaView, playMachineGunReloadMediaView;
    private Label HPLabel = new Label(), BulletLabel = new Label();
    private IntegerProperty HPIntegerProperty, BulletIntegerProperty;
    
    boolean moveLeft = false, moveRight = false, moveUp = false, moveDown = false;
	
	public static void main(String[] arg){
		launch(arg);
	}
	
	@Override
	public void start(Stage stage){
				
		//player = new Player(); 
		
		pane = new Pane();
		Image roadImage = null;
		Image playerImage= null;
		Image zombieImage = null, bulletImage = null;
		Image machinegunImage = null, rifileImage = null, HPIconImage = null;
		ImageView dummy, dummy1;
		Media handGunShoot = null; //Media
		Media handGunReload = null; //Media
		Media machineGunShoot = null; //Media
		Media machineGunReload = null; //Media
		timeline = new Timeline();
		refreshScreen = new Timeline();				
		
		//Loading images and setting GUI
		try {
			roadImage =  new Image("newBackground.jpg");
			playerImage = new Image("pistol.png");
			machinegunImage = new Image("machinegun.png");
			rifileImage = new Image("rifile.png");
			zombieImage = new Image("zombie1.png");
			HPIconImage = new Image("HP.gif");
			bulletImage = new Image("bullet.png");
			handGunShoot = new Media("HandGun shoot.mp3"); //Media
			handGunReload = new Media("HandGun Reload.mp3"); //Media
			machineGunShoot = new Media("HandGun single shoot.mp3"); //Media
			machineGunReload = new Media("HandGun reload.mp3"); //Media
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
		
		//MediaPlayer
		playHandGunShoot = new MediaPlayer(handGunShoot);
		playHandGunReload = new MediaPlayer(handGunReload);
		playMachineGunShoot = new MediaPlayer(machineGunShoot);
		playMachineGunReload = new MediaPlayer(machineGunReload);
		//MediaView
		playHandGunShootMediaView = new MediaView(playHandGunShoot);
		playHandGunReloadMediaView = new MediaView(playHandGunReload);
		playMachineGunShootMediaView = new MediaView(playMachineGunShoot);
		playMachineGunReloadMediaView = new MediaView(playMachineGunReload);
		
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
        
        BulletIntegerProperty = new SimpleIntegerProperty(100);
        BulletLabel.textProperty().bind(BulletIntegerProperty.asString());
        BulletLabel.setTextFill(Color.YELLOW);
        BulletLabel.setFont(DEFAULT_FONT);
        BulletLabel.setOpacity(0.75);
        
        bulletImageView = new ImageView[MAX_MAGAZINE_SIZE];
        
        for(int i=0 ; i<bulletImageView.length ; i++ ){
        	bulletImageView[i] = new ImageView(bulletImage);
        }
        
        targetImageView = new ImageView[10];
        for(int i=0 ; i<targetImageView.length ; i++ ){
        	targetImageView[i] = new ImageView(zombieImage);
        }
       
		pane.getChildren().addAll(backgroundImageView, playerImageView, HPLabel, BulletLabel, HPIconImageView);
		

		for(ImageView i : bulletImageView){
			pane.getChildren().addAll(i);
			i.setVisible(false);
		} 

		
		for(ImageView i: targetImageView){
			pane.getChildren().addAll(i);
        	i.setVisible(false);
        }

		
//		Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
//        stage.setX(primaryScreenBounds.getMinX());
//        stage.setY(primaryScreenBounds.getMinY());
//        stage.setWidth(primaryScreenBounds.getWidth());
//        stage.setHeight(primaryScreenBounds.getHeight());
          
        //Set Positioning
        
		scene = new Scene(pane);
        
        player.setPosition(450, 275); 
//        		primaryScreenBounds.getHeight()/2);
        
		playerImageView.setY(player.getYcoord());
		playerImageView.setX(player.getXcoord());
		
		zombieImageView.setX(200);
		zombieImageView.setY(200);
		HPLabel.setTranslateX(70);
		HPLabel.setTranslateY(530);
		HPIconImageView.setX(5);
		HPIconImageView.setY(500);
		BulletLabel.setTranslateY(530);
		BulletLabel.setTranslateX(845);
		
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
	            		break;
					case R:
	        			reloadStartTime = System.currentTimeMillis();
						player.reload();
						BulletIntegerProperty.setValue(Bullet.getMagazineSize());
						break;
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
        		
        		if((System.currentTimeMillis() - reloadStartTime) < 2000)
        			return;
        		
        		if(player.fire(mouse.getX(), mouse.getY(), angle)){ 
        			BulletIntegerProperty.setValue(BulletIntegerProperty.getValue()-1);
        		} 
        		else{  //failed to fire, reload
        			reloadStartTime = System.currentTimeMillis();
        			player.reload();
        			BulletIntegerProperty.setValue(Bullet.getMagazineSize());
        		}
//				System.out.println("After fired,  "+player.getNumberOfUnusedBullet());
			}
        	
        });
               
        scene.setOnMouseMoved(new EventHandler<MouseEvent>() {
        	public void handle(MouseEvent mouse){
        		double angle = getFireAngle(mouse.getX(), mouse.getY()); 
//        		System.out.println(angle);
        		if(mouse.getX()>player.getXcoord())
        			playerImageView.setRotate(angle);
        		else
        			playerImageView.setRotate(-1*angle);
        		
//                if(mouse.getY()<player.getYcoord())
//                	playerImageView.setRotate(90-angle);
//                else
//                	playerImageView.setRotate(90+angle);
        	}
        });

		stage.setScene(scene);
		stage.setHeight(600);
		stage.setWidth(900);
		//stage.sizeToScene();
		stage.setResizable(false);
		stage.setTitle("ISOM3320 Game");
		stage.setFullScreen(false);

		refreshScreen.getKeyFrames().add( new KeyFrame(new Duration(33), this));
		refreshScreen.setCycleCount(Timeline.INDEFINITE);
		refreshScreen.play();
		
		stage.show();
		System.out.println("Stage being showed.");
		
//		System.out.println(backgroundImageView.getLayoutX()+" "+backgroundImageView.getLayoutY());
		
		for(int i=0;i<target.length;i++){
			target[i].setVisible(player.getPosition());
			System.out.println("X: "+target[i].getXcoord()+" Y: "+ target[i].getYcoord());
			targetImageView[i].setRotate(target[i].getAngleOfChase(player.getPosition()));
			targetImageView[i].setX(target[i].getXcoord());
			targetImageView[i].setY(target[i].getYcoord());
			targetImageView[i].setVisible(true);
		}

		
		
		
		//TO-DO, bind integer property with health and bulletnumber
//		
//		Target target = new Target();
//		target.setPosition(3, 3);
//		Bullet bullet1 = new Bullet();
//		bullet1.setPosition(200, 3);
//		System.out.println(bullet1.isHit(target));
//		
	}
	
	double getFireAngle(double x, double y){
//		Point2D Vector = player.getPosition().multiply(-1).add(x, y);   // cursor vector subtract player vector
//	equivalent to
//		Point2D mouseVector = new Point2D(mouse.getX(), mouse.getY());
//		Point2D Vector = mouseVector.subtract(player.getPosition());
		//double angle = xVector.angle(Vector);
		
		Point2D dummyVector = new Point2D(player.getXcoord(),0), cursorVector = new Point2D(x,y);
		double angle = player.getPosition().angle(dummyVector, cursorVector);
		//calculate the angle of player from vertical axis to cursor		
        return angle; 
	}

	@Override
	public void handle(ActionEvent e) {
		// TODO Auto-generated method stub
//		System.out.println(player.getYcoord()+" "+scene.getHeight()+scene.getY());
		//player movement
        if(moveUp){
        	backgroundImageView.setTranslateY(backgroundImageView.getTranslateY()+5);
        	for(Target i : target){
        		i.changePosition(0, 5);
        	}
//        	player.move(0, -5);
        }
        if(moveDown){
        	backgroundImageView.setTranslateY(backgroundImageView.getTranslateY()-5);
        	for(Target i : target){
        		i.changePosition(0, -5);
        	}
//        	player.move(0, 5);
        }
        if(moveLeft && backgroundImageView.getTranslateX() < 0){
        	backgroundImageView.setTranslateX(backgroundImageView.getTranslateX()+5);
        	for(Target i : target){
        		i.changePosition(+5, 0);
        	}
        	//System.out.println(backgroundImageView.getTranslateX());
//        	player.move(-5, 0);
        }
        if(moveRight){ //backgroundImageView.getTranslateX()*-1 < MAX
        	backgroundImageView.setTranslateX(backgroundImageView.getTranslateX()-5);
        	for(Target i : target){
        		i.changePosition(-5, 0);
        	}
//        	System.out.println(backgroundImageView.getTranslateX());  //0 return to startpoint else negative
        	//player.move(5,0);
        }
//        playerImageView.setX(player.getXcoord());
//        playerImageView.setY(player.getYcoord());
        
        //bullet movement
        for(int i=0; i < Bullet.getMagazineSize() ; i++){
        	if(bullet[i].getIsMoving()){
        	//if(bullet[i].getXVelocity()>0 || bullet[i].getYVelocity()>0){
        		bulletImageView[i].setRotate(bullet[i].getFireAngle());
        		bulletImageView[i].setVisible(true);
        		bullet[i].move(bullet[i].getXVelocity()*20, bullet[i].getYVelocity()*20);
        		bulletImageView[i].setX(bullet[i].getXcoord());
        		bulletImageView[i].setY(bullet[i].getYcoord());
        	}
        }
        
        //target movement
        
		for(int i=0;i<target.length ; i++){
			target[i].move(player.getPosition());
			targetImageView[i].setRotate(target[i].getAngleOfChase(player.getPosition()));
			targetImageView[i].setX(target[i].getXcoord());
			targetImageView[i].setY(target[i].getYcoord());
		}
        
//		
		for(int i=0;i<target.length;i++){
			
			System.out.println("X: "+target[i].getXcoord()+" Y: "+ target[i].getYcoord());
			System.out.println("IX: "+targetImageView[i].getX()+" IY: "+ targetImageView[i].getY());
		}
//		
        //check ishit()
		
	}
	
	public static Point2D getPlayerPosition(){
		Point2D position = player.getPosition();
		return position;
	} 
	
	
	
}
