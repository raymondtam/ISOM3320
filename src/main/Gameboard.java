package main;

import java.nio.file.Paths;
import java.util.Arrays;
import javax.swing.JOptionPane;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import javafx.application.Application;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.media.AudioClip;
import javafx.scene.ImageCursor;
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
import javafx.stage.Stage;

final public class Gameboard extends Application implements EventHandler<ActionEvent> {
	//852 * 7680
	//Constant
	final int NO_OF_BULLET_TYPE = 3; 
	final static int[] BULLET_DAMAGE = {3, 4, 5};
	final static int[] MAGAZINE_SIZE = {15, 30, 100};
	final static int MAX_MAGAZINE_SIZE = 100;
	final static int DEFAULT_BULLET_DAMAGE = BULLET_DAMAGE[0];
	final static int DEFAULT_MAGAZINE_SIZE = MAGAZINE_SIZE[0];
	final static double BULLET_DEFAULT_RADIUS = 10;
//	final static double PLAYER_DEFAULT_RADIUS = 10;
	final static int TARGET_DEFAULT_RADIUS = 30;
	final static int NUMBER_OF_ZOMBIES = 10;
	final static int ZOMBIES_DAMAGE = 5;
	final static int BOSS_DAMAGE = 20;
	final static int PLAYER_MAXHEALTH = 100;
	final static int TARGET_HEALTH = 12;
	final static int BOSS_HEALTH = 300;
	final static int BULLET_MOVEMENT_SPEED = 20;
	final static int PLAYER_MOVEMENT_SPEED = 5;
	final static int TARGET_MOVEMENT_SPEED = 5;
	
	final Font DEFAULT_FONT = Font.font("irisupc", 50);
	final static double screenWidth = 900;
	final static double screenHeight = 600;
	
	//Game variable and objects
	String name = "Player1";
	short weaponSetting = 0; //0 default 
	int score = 0;
	
	long reloadStartTime = 0, startTime = 0, zombieReborn = 0;
	
	static Bullet[] bullet = Bullet.getBulletArray(MAX_MAGAZINE_SIZE, DEFAULT_BULLET_DAMAGE, DEFAULT_MAGAZINE_SIZE, BULLET_DEFAULT_RADIUS, BULLET_MOVEMENT_SPEED);
	static Player player = new Player(bullet, PLAYER_MOVEMENT_SPEED, PLAYER_MAXHEALTH);
	static Target[] target = Target.getTargetArray(NUMBER_OF_ZOMBIES, TARGET_HEALTH, ZOMBIES_DAMAGE, TARGET_MOVEMENT_SPEED, TARGET_DEFAULT_RADIUS); 
	static String[] topThree = {"","",""};
	static int[] topThreeScores = {0, 0, 0};
	static long[] topThreeTime = {0, 0, 0};
	static Boss boss = new Boss (BOSS_HEALTH, BOSS_DAMAGE, 2, 110);
	static int infectionThreshold = 0;
	static long timeElapsed = 0;
	static int minutesToDisplay, secondsToDisplay;
	static int bossShowCount = 0;
	boolean summonZombie = false;
	
	//Graphics and animation variable
	Pane pane;
	Scene scene;
	Timeline timeline, refreshScreen;
	
	// Main graphics component
	private ImageView backgroundImageView, HPIconImageView, bossImageView;
	private ImageView[] bulletImageView, targetImageView, playerImageView;	
	
	//Pick up Weapon 
	private ImageView[] weaponIconImageView; 
	private long[] weaponIconDistance;
	
	//Sound effect
	private AudioClip[] gunShoot, gunReload, zombieSound;
	private AudioClip footSteps;
	
	//Screen Graphics
    private Label HPLabel = new Label(), BulletLabel = new Label(), ScoreLabel = new Label(),
    		MinuteLabel = new Label(), SecondLabel = new Label(), ColonLabel = new Label();
    private IntegerProperty HPIntegerProperty, BulletIntegerProperty, ScoreIntegerProperty, MinutesIntegerProperty, SecondsIntegerProperty;
    
    //Movement variable
    boolean moveLeft = false, moveRight = false, moveUp = false, moveDown = false;
    
    //Shooting variable
    boolean mousePressed = false, handgunTrigger = false;
    double angle, mouseX, mouseY;
    long lastShootTime;
	
    //Graphics Tanslation
    double[] playerTranslateX, playerTranslateY, targetTranslateX, targetTranslateY, 
    	weaponIconTranslateX, weaponIconTranslateY;
    double bossTranslateX, bossTranslateY, bulletTranslateX, bulletTranslateY, cursorTranslateX, cursorTranslateY;
    
    //Play/Pause flag
    boolean play=false;
    
	public static void main(String[] arg){
		launch(arg);
	}
	
	@Override
	public void start(Stage stage){
		pane = new Pane();
		Image roadImage = null;

		Image playerImageHandGun = null, playerImageRifle = null, playerImageMachineGun = null;
		Image zombieImage = null, zombieImage1 = null, zombieImage2 = null, bulletImage = null, bossImage = null;
		
		Image HPIconImage = null;
		Image machinegunIconImage = null, rifleIconImage = null;
		Image crossHairImage = null;
		
		timeline = new Timeline();
		refreshScreen = new Timeline();				
		
		gunShoot = new AudioClip[3];
		gunReload = new AudioClip[3];
		zombieSound = new AudioClip[4];
		
		//Loading images and setting GUI
		try {
			roadImage =  new Image("FullBackground.png");
			playerImageHandGun = new Image(Paths.get("src\\pistol.png").toUri().toString());
			playerImageRifle = new Image("rifle.png");
			playerImageMachineGun = new Image("machinegun.png");
			
			machinegunIconImage = new Image ("machinegun_icon.png");
			rifleIconImage = new Image ("rifle_icon.png");
			
			zombieImage = new Image("zombie1.png");
			zombieImage1 = new Image("zombie2.png");
			zombieImage2 = new Image("zombie3.png");
			bossImage = new Image ("boss.gif");
			HPIconImage = new Image("HP.gif");
			bulletImage = new Image("bullet.png");
			crossHairImage = new Image("crosshair_pick3.png");
			gunShoot[0] = new AudioClip(Paths.get("src\\HandGunShoot.mp3").toUri().toString());
			gunShoot[1] = new AudioClip(Paths.get("src\\RifleShoot.mp3").toUri().toString());
			gunShoot[2] = new AudioClip(Paths.get("src\\MachineGunShoot.mp3").toUri().toString());
			gunReload[0] = new AudioClip(Paths.get("src\\HandGunReload.mp3").toUri().toString());
			gunReload[1] = new AudioClip(Paths.get("src\\RifleReload.mp3").toUri().toString());
			gunReload[2] = new AudioClip(Paths.get("src\\MachineGunReload.mp3").toUri().toString());
			zombieSound[0] = new AudioClip(Paths.get("src\\BGM.mp3").toUri().toString());
			zombieSound[1] = new AudioClip(Paths.get("src\\ZombieReborn.mp3").toUri().toString());
			zombieSound[2] = new AudioClip(Paths.get("src\\ZombieBite.mp3").toUri().toString());
			zombieSound[3] = new AudioClip(Paths.get("src\\BossLaugh.mp3").toUri().toString());
			footSteps = new AudioClip(Paths.get("src\\Footsteps.mp3").toUri().toString());

			// System.out.println("Image being imported.");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		playerTranslateX = new double[3];
		playerTranslateX[0] = playerImageHandGun.getWidth()/2;
		playerTranslateX[1] = playerImageRifle.getWidth()/2;
		playerTranslateX[2] = playerImageMachineGun.getWidth()/2;
		
		playerTranslateY = new double[3];
		playerTranslateY[0] = playerImageHandGun.getHeight()/2;
		playerTranslateY[1] = playerImageRifle.getHeight()/2;
		playerTranslateY[2] = playerImageMachineGun.getHeight()/2;
		
		targetTranslateX = new double[3];
		targetTranslateX[0] = zombieImage.getWidth()/2;
		targetTranslateX[1] = zombieImage1.getWidth()/2;
		targetTranslateX[2] = zombieImage2.getWidth()/2;
		
		targetTranslateY = new double[3];
		targetTranslateY[0] = zombieImage.getHeight()/2;
		targetTranslateY[1] = zombieImage.getHeight()/2;
		targetTranslateY[2] = zombieImage.getHeight()/2;
		
		weaponIconTranslateX = new double[2];
		targetTranslateX[0] = machinegunIconImage.getWidth()/2;
		targetTranslateX[1] = rifleIconImage.getWidth()/2;
		
		weaponIconTranslateY = new double[2];
		weaponIconTranslateY[0] = rifleIconImage.getHeight()/2; 
		weaponIconTranslateY[1] = machinegunIconImage.getHeight()/2;
		
		bossTranslateX = bossImage.getWidth()/2;
		bossTranslateY = bossImage.getHeight()/2;
		
		bulletTranslateX =  bulletImage.getWidth()/2;
		bulletTranslateY =	bulletImage.getHeight()/2;
		
		cursorTranslateX = crossHairImage.getWidth()/2;
		cursorTranslateY = crossHairImage.getWidth()/2;
		
		//Initialize the variable and set necessary property

		backgroundImageView = new ImageView(roadImage);
		
		playerImageView = new ImageView[3];
		playerImageView[0] = new ImageView(playerImageHandGun);
		playerImageView[1] = new ImageView(playerImageRifle);
		playerImageView[2] = new ImageView(playerImageMachineGun);
		
		playerImageView[0].setRotate(90);
		playerImageView[1].setRotate(90);
		playerImageView[1].setVisible(false);
		playerImageView[2].setRotate(90);
		playerImageView[2].setVisible(false);
		
		HPIconImageView = new ImageView(HPIconImage);
		HPIconImageView.setOpacity(0.6);
		
		weaponIconImageView = new ImageView[2];
		weaponIconImageView[0] = new ImageView (rifleIconImage); 
		weaponIconImageView[1] = new ImageView (machinegunIconImage);
		
		weaponIconDistance = new long[2];
		weaponIconDistance[0] = -2000;
		weaponIconDistance[1] = -4000;
		
		bossImageView = new ImageView (bossImage);
		bossImageView.setRotate(270);
		bossImageView.setVisible(false);
		
		HPIntegerProperty = new SimpleIntegerProperty(100);
        HPLabel.textProperty().bind(HPIntegerProperty.asString());
        HPLabel.setTextFill(Color.YELLOW);
        HPLabel.setFont(DEFAULT_FONT);
        HPLabel.setOpacity(0.8);
        
        ScoreIntegerProperty = new SimpleIntegerProperty(0);
        ScoreLabel.textProperty().bind(ScoreIntegerProperty.asString());
        ScoreLabel.setTextFill(Color.YELLOW);
        ScoreLabel.setFont(DEFAULT_FONT);
        ScoreLabel.setOpacity(0.8);
        
        MinutesIntegerProperty = new SimpleIntegerProperty(0);
        MinuteLabel.textProperty().bind(MinutesIntegerProperty.asString());
        MinuteLabel.setTextFill(Color.YELLOW);
        MinuteLabel.setFont(DEFAULT_FONT);
        MinuteLabel.setOpacity(0.8);
        
        SecondsIntegerProperty = new SimpleIntegerProperty(0);
        SecondLabel.textProperty().bind(SecondsIntegerProperty.asString());
        SecondLabel.setTextFill(Color.YELLOW);
        SecondLabel.setFont(DEFAULT_FONT);
        SecondLabel.setOpacity(0.8);
        
        BulletIntegerProperty = new SimpleIntegerProperty(DEFAULT_MAGAZINE_SIZE);
        BulletLabel.textProperty().bind(BulletIntegerProperty.asString());
        BulletLabel.setTextFill(Color.YELLOW);
        BulletLabel.setFont(DEFAULT_FONT);
        BulletLabel.setOpacity(0.75);
        
        ColonLabel.setText(":");
        ColonLabel.setTextFill(Color.YELLOW);
        ColonLabel.setFont(DEFAULT_FONT);
        ColonLabel.setOpacity(0.8);
        
        bulletImageView = new ImageView[MAX_MAGAZINE_SIZE];
        for(int i=0 ; i<bulletImageView.length ; i++ ){
        	bulletImageView[i] = new ImageView(bulletImage);
        }
        
        targetImageView = new ImageView[12];
        for(int i=0 ; i<targetImageView.length/3 ; i++ ){
        	targetImageView[i] = new ImageView(zombieImage);
        	targetImageView[i+4] = new ImageView(zombieImage1);
        	targetImageView[i+8] = new ImageView(zombieImage2);        	
        }
        
		pane.getChildren().addAll(backgroundImageView, HPLabel, BulletLabel, ScoreLabel, 
				MinuteLabel, SecondLabel, ColonLabel, HPIconImageView);
		pane.getChildren().addAll(playerImageView[0], playerImageView[1], playerImageView[2]);
		
		for(ImageView i : bulletImageView){
			pane.getChildren().add(i);
			i.setVisible(false);
		} 

		for(ImageView i: targetImageView){
			pane.getChildren().add(i);
        	i.setVisible(false);
        }
		
		for(ImageView i: weaponIconImageView){
			pane.getChildren().add(i);
		}

		pane.getChildren().add(bossImageView);
          
		scene = new Scene(pane);
        
		//Set Positioning		
		HPLabel.setTranslateX(70);
		HPLabel.setTranslateY(530);
		ScoreLabel.setTranslateX(300);
		ScoreLabel.setTranslateY(530);
		HPIconImageView.setX(5);
		HPIconImageView.setY(500);
		BulletLabel.setTranslateY(530);
		BulletLabel.setTranslateX(835);
		MinuteLabel.setTranslateX(400);
		MinuteLabel.setTranslateY(20);
		SecondLabel.setTranslateX(450);
		SecondLabel.setTranslateY(20);
		ColonLabel.setTranslateX(430);
		ColonLabel.setTranslateY(17);
		
		refreshScreen.getKeyFrames().add( new KeyFrame(new Duration(33), this));
		refreshScreen.setCycleCount(Timeline.INDEFINITE);
		
		//Movement buffer
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
						if (player.reload()) {
		        			reloadStartTime = System.currentTimeMillis();
		        			BulletIntegerProperty.setValue(Bullet.getMagazineSize());
		        			gunReload[weaponSetting].play(100);
						}
						break;
					case SPACE:
						if(!play){
							refreshScreen.play();
							play = true;
							if(startTime==0)
								startTime = System.currentTimeMillis();
						}
						else{
							refreshScreen.pause();
							play = false;
						}
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
        
        //Shooting Mechanism
        scene.setOnMouseDragged(new EventHandler<MouseEvent>() {
        	public void handle(MouseEvent mouse){
        		mouseX = mouse.getX();
        		mouseY = mouse.getY();
        		double angle = getFireAngle(mouseX, mouseY); 
        		if(mouseX>player.getXcoord())
        			playerImageView[weaponSetting].setRotate(angle);
        		else
        			playerImageView[weaponSetting].setRotate(-1*angle);
        	}
        });
        
        scene.setOnMousePressed(new EventHandler<MouseEvent>() {
        	public void handle(MouseEvent mouse){
        		mousePressed = true;
        		handgunTrigger = true;
        	}
        });
        
        scene.setOnMouseReleased(new EventHandler<MouseEvent>() {
        	public void handle(MouseEvent mouse){
        		mousePressed = false;
        		handgunTrigger = false;
//        		System.out.println("Mouse Released");
        	}
        });
               
        scene.setOnMouseMoved(new EventHandler<MouseEvent>() {
        	public void handle(MouseEvent mouse){
        		mouseX = mouse.getX();
        		mouseY = mouse.getY();
        		double angle = getFireAngle(mouseX, mouseY); 
//        		System.out.println(angle);
        		if(mouse.getX()>player.getXcoord())
        			playerImageView[weaponSetting].setRotate(angle);
        		else
        			playerImageView[weaponSetting].setRotate(-1*angle);
        	}
        });
		
        scene.setCursor(new ImageCursor(crossHairImage, cursorTranslateX, cursorTranslateY));
        
        initialize();
        
		stage.setScene(scene);
		stage.setHeight(screenHeight);
		stage.setWidth(screenWidth);
		stage.setResizable(false);
		stage.setTitle("ISOM3320 Game");
		stage.setFullScreen(false);
		stage.show();
		zombieSound[0].play();
		System.out.println("Stage being showed.");
		
		zombieReborn = System.currentTimeMillis();
				
	}
	
	double getFireAngle(double x, double y){
		Point2D dummyVector = new Point2D(player.getXcoord(),0), cursorVector = new Point2D(x,y);
		double angle = player.getPosition().angle(dummyVector, cursorVector);
		//calculate the angle of player from vertical axis to cursor		
        return angle; 
	}

	@Override
	public void handle(ActionEvent e) {
		
//Updating Time
        timeElapsed = (System.currentTimeMillis() - startTime) / 1000;
        minutesToDisplay = (int)(timeElapsed / 60);
        secondsToDisplay = ((int)(timeElapsed)) % 60;
        MinutesIntegerProperty.setValue(minutesToDisplay);
        SecondsIntegerProperty.setValue(secondsToDisplay);
		
//Shooting
		if(mousePressed && ( (weaponSetting > 0 &&  System.currentTimeMillis() - lastShootTime > 33*5) || ( handgunTrigger && System.currentTimeMillis() - lastShootTime > 33*10))){
			handgunTrigger = false;
			double angle = getFireAngle(mouseX, mouseY);
			if((System.currentTimeMillis() - reloadStartTime) > 2000){
				if(player.fire(mouseX, mouseY, angle)){ 
					gunShoot[weaponSetting].play(100);
					lastShootTime = System.currentTimeMillis();
				} 
				else{  //failed to fire, reload
					reloadStartTime = System.currentTimeMillis();
					player.reload();
					gunReload[weaponSetting].play(100);
				}		
				BulletIntegerProperty.setValue(player.getNumberOfUnusedBullet());
			}
		}

//Player movement with relative translation of graphics
        if(moveUp && backgroundImageView.getTranslateY() < - 300){
        	// && backgroundImageView.getTranslateY() > - 500
        	backgroundImageView.setTranslateY(backgroundImageView.getTranslateY() + 5);
        	for(ImageView i : weaponIconImageView){
        		if(i.isVisible())
        			i.setY(i.getY()+5);
        	}
        	for(Target i : target){
        		i.changePosition(0, 5);
        	}
        	boss.changePosition(0, 5);
        	//player.move(0, -5);
        }
        
        if(moveDown && backgroundImageView.getTranslateY() > - 1920){
        	// && backgroundImageView.getTranslateY() < 500
        	backgroundImageView.setTranslateY(backgroundImageView.getTranslateY() - 5);
        	for(ImageView i : weaponIconImageView){
        		if(i.isVisible())
        			i.setY(i.getY()-5);
        	}
        	for(Target i : target){
        		i.changePosition(0, -5);
        	}
        	boss.changePosition(0, -5);
        	//player.move(0, 5);
        }
        
        if(moveLeft){
        	if(backgroundImageView.getTranslateX() < 0){
        		backgroundImageView.setTranslateX(backgroundImageView.getTranslateX()+5);
        		for(Target i : target){
            		i.changePosition(+5, 0);
            	}
        	}
        	else{
        		if(player.getXcoord()>0){
        			player.move(-5, 0);
        			playerImageView[weaponSetting].setX(player.getXcoord());
        		}
        	}
        	for(ImageView i : weaponIconImageView){
        		if(i.isVisible())
        			i.setX(i.getX()+5);
        	}
        	boss.changePosition(+5, 0);
        	//player.move(-5, 0);
        }
        
        if(moveRight){ //backgroundImageView.getTranslateX()*-1 < MAX
        	if(player.getXcoord()<scene.getWidth()/2){
        		player.move(5, 0);
    			playerImageView[weaponSetting].setX(player.getXcoord());
        	}
        	else{
        		backgroundImageView.setTranslateX(backgroundImageView.getTranslateX()-5);
        		for(Target i : target){
            		i.changePosition(-5, 0);
        		}
        	}
        	
        	for(ImageView i : weaponIconImageView){
        		if(i.isVisible())
        			i.setX(i.getX()-5);
        	}
        	boss.changePosition(-5, 0);
        	//player.move(5,0);
        }
        
//Bullet movement
        for(int i=0; i < Bullet.getMagazineSize() ; i++){
        	if(bullet[i].getIsMoving()){
        		bulletImageView[i].setRotate(bullet[i].getFireAngle());
        		bulletImageView[i].setVisible(true);
        		bullet[i].move(bullet[i].getXVelocity()*bullet[i].getMovingSpeed(), bullet[i].getYVelocity()*bullet[i].getMovingSpeed());
        		bulletImageView[i].setX(bullet[i].getXcoord()-bulletTranslateX);
        		bulletImageView[i].setY(bullet[i].getYcoord()-bulletTranslateY);
        	}
        }
        		
//		for(int i = 0; i < target.length; i++){
//			if(target[i].isVisible()){
//				target[i].move(player.getPosition());
//				targetImageView[i].setRotate(target[i].getAngleOfChase(player.getPosition()));
//				targetImageView[i].setX(target[i].getXcoord()-targetTranslateX[i/4]);
//				targetImageView[i].setY(target[i].getYcoord()-targetTranslateY[i/4]);
//			}
//		}
		
//Target movement
		double minTargetToTargetDistance = 900;
		double minDistance;
		double targetToTargetDistance;
		double targetToPlayerDistance;
		for(int i = 0; i < target.length; i++){
			if (!target[i].isDead()){
				minDistance = 30;
				targetToPlayerDistance = Math.pow(Math.pow(target[i].getXcoord() - player.getXcoord(), 2.0) 
						+ Math.pow(target[i].getYcoord() - player.getYcoord(), 2.0), 0.5);
				for(int j = 0; j < target.length; j++){
					if (i == j){
						break;
					}
					targetToTargetDistance = Math.pow(Math.pow(target[i].getXcoord() - target[j].getXcoord(), 2.0) 
						+ Math.pow(target[i].getYcoord() - target[j].getYcoord(), 2.0), 0.5);
					if (targetToTargetDistance <= minTargetToTargetDistance){
						minTargetToTargetDistance = targetToTargetDistance;
					}
				}
				if (targetToPlayerDistance <= minTargetToTargetDistance){
					minDistance = targetToPlayerDistance;
				}
				else {
					minDistance = minTargetToTargetDistance;
				}
				if (minDistance >= 30){
					target[i].move(player.getPosition());
					targetImageView[i].setRotate(target[i].getAngleOfChase(player.getPosition()));
					targetImageView[i].setX(target[i].getXcoord()-targetTranslateX[i/4]);
					targetImageView[i].setY(target[i].getYcoord()-targetTranslateY[i/4]);
				}
				else {
					targetImageView[i].setRotate(target[i].getAngleOfChase(player.getPosition()));
					targetImageView[i].setX(target[i].getXcoord()-targetTranslateX[i/4]);
					targetImageView[i].setY(target[i].getYcoord()-targetTranslateY[i/4]);
				}
			}
		}
		
//Boss movement
		boss.move(player.getPosition());
		bossImageView.setRotate(boss.getAngleOfChase(player.getPosition()));
		bossImageView.setX(boss.getXcoord()-bossTranslateX);
		bossImageView.setY(boss.getYcoord()-bossTranslateY);
		
		//Boss show and check if bullets hit Boss or Zombies
		for(int i=0 ; i < bullet.length ; i++){
			if(boss.isVisible() && bullet[i].isVisible() && bullet[i].isHit(boss)){
				System.out.println("hit bossed");
				boss.minusHealth(Bullet.getBulletDamage());
				bullet[i].setVisible(false);
				bullet[i].setIsMoving(false);
				bullet[i].setPosition(-999, -999);  //void the bullet
				bulletImageView[i].setVisible(false);
				
				if(boss.getHealth() % 60 == 0){
					summonZombie = true;
				}
				
				if(boss.isDead()){
					boss.setVisible(false);
					bossImageView.setVisible(false);
					boss.setPosition(-999,-999);
					score += 1000;
					score = score - (int)(timeElapsed) + player.getHealth()*10;
					// System.out.println("Boss Dead");
					endGame();
				}
			}
			
//Check ishit and isdead
			for(int j=0 ; j<target.length;j++){
				if(target[j].isVisible() && bullet[i].isVisible() && bullet[i].isHit(target[j])){
					target[j].minusHealth(Bullet.getBulletDamage());
					 System.out.println("Zombie " + j + " is hit ");
					bullet[i].setVisible(false);
					bullet[i].setIsMoving(false);
					bullet[i].setPosition(-999, -999);  //void the bullet
					bulletImageView[i].setVisible(false);
					
					if(target[j].isDead()){
						target[j].setVisible(false);
						targetImageView[j].setVisible(false);
						target[j].setPosition(-999,-999);
						score += 10;
						 System.out.println("Zombie " + j + " Dead");
					}
					break;
				}
			}
			ScoreIntegerProperty.setValue(score);
		}
		
//Check if Zombies hit Player and minus health correspondingly
		for(int i = 0; i < target.length; i++){
			if(targetImageView[i].isVisible() && player.isHit(target[i])){
				infectionThreshold += 1;
			}
			infected(30, ZOMBIES_DAMAGE);
		}
		
//Check if Boss hit Player and minus health correspondingly
		if(bossImageView.isVisible() && player.isHit(boss)){
			infectionThreshold += 3;
			infected(30, BOSS_DAMAGE);
		}
		
//Generate zombie, reborn
		if(System.currentTimeMillis() - zombieReborn > 20000 && bossShowCount < 1 ){
			System.out.println("Zombie Reborn");
			zombieSound[1].play(500);
			if (backgroundImageView.getTranslateY() < - 581 && backgroundImageView.getTranslateY() > -1721){
				Target.rebornZombie(target, player.getPosition());
				zombieReborn=System.currentTimeMillis();
				for(int i=0; i<target.length ; i++){
					if(target[i].isVisible()){
						targetImageView[i].setVisible(true);
						targetImageView[i].setRotate(target[i].getAngleOfChase(player.getPosition()));
						targetImageView[i].setX(target[i].getXcoord()-targetTranslateX[i/4]);
						targetImageView[i].setY(target[i].getYcoord()-targetTranslateY[i/4]);
					}
				}
			}
			else {
				for(int i=0; i<target.length ; i++){
					target[i].rebornZombieNearBoundary(target, player.getPosition(), (int)(backgroundImageView.getTranslateY()));
					zombieReborn=System.currentTimeMillis();
					if(target[i].isVisible()){
						targetImageView[i].setVisible(true);
						targetImageView[i].setRotate(target[i].getAngleOfChase(player.getPosition()));
						targetImageView[i].setX(target[i].getXcoord()-targetTranslateX[i/4]);
						targetImageView[i].setY(target[i].getYcoord()-targetTranslateY[i/4]);
					}
				}
			}
		}
				
//Show zombie
		for(int i=0 ; i<target.length ; i++){
			if(target[i].isVisible())
				targetImageView[i].setVisible(true);
		}
		
//Show Boss
		if(!bossImageView.isVisible() && backgroundImageView.getTranslateX() < -5000 
			&& backgroundImageView.getTranslateY() < - 300 
			&& backgroundImageView.getTranslateY() > - 1920 && bossShowCount < 1){
			// System.out.println("Boss");
			zombieSound[3].play(1000);
			boss.setVisible(true);
			boss.setPosition(600, 150);
			bossImageView.setVisible(true);
			bossImageView.setX(600);
			bossImageView.setY(150);
			bossShowCount += 1;
			boss.summonZombie(target, player.getPosition(), 200);
			System.out.println("Summon Zombie!!!");
		}
		
//Show new weapon
		for(int i = 0 ; i<weaponIconImageView.length ; i++){
			if(!weaponIconImageView[i].isVisible() && backgroundImageView.getTranslateX() < weaponIconDistance[i]){
				newWeapon(weaponIconImageView[i]);
			}
			if((Math.pow(Math.pow(player.getXcoord() - weaponIconImageView[i].getX(),2.0) 
				+ Math.pow(player.getYcoord() - weaponIconImageView[i].getY(),2.0), 0.5) 
				<= (player.getRadius() + 50)) && weaponIconImageView[i].isVisible()){
				//change weapon
				pickWeapon(weaponIconImageView[i], i+1);
			}
		}
		
//Summon zombie
		if(boss.isVisible() && summonZombie){
			boss.summonZombie(target, player.getPosition(), 200);
			zombieSound[3].play(1000);
			summonZombie = false;
			System.out.println("Summon Zombie!!!");
		}
		
}		
	
//Show and set new weapon icon
	public void newWeapon(ImageView weapon) {
		weapon.setVisible(true);
		weapon.setX((int)(player.getXcoord() + 300));
		weapon.setY((int)(player.getYcoord()));
	}
	
//Pick up new weapon
	public void pickWeapon (ImageView weapon, int index) {
//		weapon.setVisible(false);
		weapon.setX(-999);
		weapon.setY(-999);
		Bullet.setBulletDamage(BULLET_DAMAGE[index]);
		Bullet.setMagazineSize(MAGAZINE_SIZE[index]);
		playerImageView[weaponSetting].setVisible(false); // original
		weaponSetting = (short)index;
		playerImageView[weaponSetting].setVisible(true);
		playerImageView[weaponSetting].setX(player.getXcoord() - playerTranslateX[weaponSetting]);
		playerImageView[weaponSetting].setY(player.getYcoord() - playerTranslateY[weaponSetting]);
		player.reload();
		BulletIntegerProperty.setValue(Bullet.getMagazineSize());
	}
	
	// calculate total score
		//public double totalScore() {
			//To Do, e.g.
			// number of Target kill * 10 + Boss kill + player's HP * 10 - time required 
		//}
	
	private void initialize(){
		//initialize scene
		
		zombieSound[0].play();
		
        MinutesIntegerProperty.setValue(0);
        SecondsIntegerProperty.setValue(0);
		
		for(ImageView i:playerImageView){
			i.setVisible(false);
		}
		
		backgroundImageView.setTranslateX(0);
		backgroundImageView.setTranslateY(-1431);
		
        player.setPosition(screenWidth/2, screenHeight/2); 
        player.setHealth(PLAYER_MAXHEALTH);
        
		System.out.println(player.getHealth());
        weaponSetting = 0;
        playerImageView[weaponSetting].setVisible(true);
		playerImageView[weaponSetting].setX(player.getXcoord()-playerTranslateX[weaponSetting]);
		playerImageView[weaponSetting].setY(player.getYcoord()-playerTranslateY[weaponSetting]);
				
		weaponIconImageView[0].setVisible(false);
		weaponIconImageView[1].setVisible(false);
        
		for(Bullet i:bullet){
			i.setBulletDamage(DEFAULT_BULLET_DAMAGE);;
			i.setMagazineSize(DEFAULT_MAGAZINE_SIZE);
			i.setIsMoving(false);
			i.setVisible(false);
			i.setPosition(-999, -999);
		}
		
		for(ImageView i:bulletImageView){
			i.setVisible(false);
		}
		
		player.setNumberOfUnusedBullet(DEFAULT_MAGAZINE_SIZE);		
		BulletIntegerProperty.setValue(player.getNumberOfUnusedBullet());
		HPIntegerProperty.setValue(player.getHealth());
		
		for(int i=0;i<target.length;i++){
			target[i].setVisible(player.getPosition());
			target[i].setHealth(TARGET_HEALTH);
			System.out.println("X: "+target[i].getXcoord()+" Y: "+ target[i].getYcoord());
			targetImageView[i].setRotate(target[i].getAngleOfChase(player.getPosition()));
			targetImageView[i].setX(target[i].getXcoord()-targetTranslateX[i/4]);
			targetImageView[i].setY(target[i].getYcoord()-targetTranslateY[i/4]);
			targetImageView[i].setVisible(true);
		}
		
		boss.setHealth(BOSS_HEALTH);
		boss.setPosition(-999, -999);
		boss.setVisible(false);
		bossImageView.setVisible(false);
		
		score = 0;
		startTime = System.currentTimeMillis();
		infectionThreshold = 0;
		timeElapsed = 0;
		bossShowCount = 0;
		
		summonZombie = false;
		
		moveLeft = false; 
		moveRight = false;
		moveUp = false;
		moveDown = false;

	    mousePressed = false;
	    handgunTrigger = false;	    
	    
	    play = false;
	}
	
	void infected(int infection, int damage){
		if(infectionThreshold >= infection){
			zombieSound[2].play(500);
			player.minusHealth(damage);
			HPIntegerProperty.setValue(player.getHealth());
			infectionThreshold = 0;
			System.out.println("Health: "+player.getHealth());
		}
		if(player.isDead()){
			score = (int)(timeElapsed) + player.getHealth()*10;
			//End Game
			endGame();
		}
	}
	
	void endGame(){
		refreshScreen.stop();
		String name;
		if(player.isDead())
			name = JOptionPane.showInputDialog(
					null, "Game Over! \n Your calculated total Score is " + score + ". Please enter your name: " , "Game Over",
					JOptionPane.QUESTION_MESSAGE);
		else
			name= JOptionPane.showInputDialog(
					null, "Well Done! \n Your used " + minutesToDisplay + " minutes " + secondsToDisplay
					+" seconds to defeat the Boss \n Your calculated total Score is " + score + ". \n Please enter your name: " , "Congratulation",
					JOptionPane.QUESTION_MESSAGE);
		if(getRank(name))
			JOptionPane.showMessageDialog(null, "Congratulations!! \n The New Ranking is \n"+showRank());
		else
			JOptionPane.showMessageDialog(null, "Sorry You didn't break the record\n"+showRank());
		zombieSound[0].stop();
		initialize();
	}
	
	
	boolean getRank(String name) {
		System.out.println(score + " " + topThreeScores[2]);
		if (score > topThreeScores[2] || ( score == topThreeScores[2] && timeElapsed < topThreeTime[2])) {
			int[] newScores = {0, 0, 0};
			String[] newName = {" "," "," "};
			long[] newTime = {0, 0, 0};

			topThreeScores[2] = score;
			topThree[2] = name;
			topThreeTime[2] = timeElapsed;
			
			int[] temp = Arrays.copyOf(topThreeScores, 3);
			Arrays.sort(temp);
			
			for(int i=0 ; i<temp.length ; i++){
				int index = Arrays.binarySearch(temp, topThreeScores[i]);
				newScores[2-index] = topThreeScores[i];
				newName[2-index] = topThree[i];
				newTime[2-index] = topThreeTime[i];
			}
			
			topThreeScores = newScores;
			topThree = newName;
			topThreeTime = newTime;
			return true;
		}
		return false;
	}
	
	String showRank(){
		String rank = "";
		for(int i=0 ; i < topThreeScores.length ; i++){
			rank = rank.concat("Rank "+ (i+1) +" : "+topThreeScores[i]+"  "+topThree[i]+"  "+topThreeTime[i]+"\n");
			System.out.println("Rank "+i+" : "+topThreeScores[i]+"  "+topThree[i]+"  "+topThreeTime[i]+"\n");
		}
		return rank;
	}
	
}
