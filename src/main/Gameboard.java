package main;

import java.nio.file.Paths;
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
import javafx.scene.Node;
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


public class Gameboard extends Application implements EventHandler<ActionEvent> {
	//852 * 7680
	//constant
	final int NO_OF_BULLET_TYPE = 3; 
	final static int[] BULLET_DAMAGE = {2, 3, 5};
	final static int[] MAGAZINE_SIZE = {15, 40, 100};
	final static int MAX_MAGAZINE_SIZE = 100;
	final static int DEFAULT_BULLET_DAMAGE = BULLET_DAMAGE[0];
	final static int DEFAULT_MAGAZINE_SIZE = MAGAZINE_SIZE[0];
	final static double DEFAULT_RADIUS = 20;
	final static int NUMBER_OF_ZOMBIES = 10;
	final Font DEFAULT_FONT = Font.font("irisupc", 50);
	
	//game variable and objects
	String name = "Player1";
	short weaponSetting = 0; //0default 
	int score = 0;
	String[] topThreeScores={"nil", "nil", "nil"};
	
	long reloadStartTime = 0, startTime = 0;
	
	static Bullet[] bullet = Bullet.getBulletArray(MAX_MAGAZINE_SIZE, DEFAULT_BULLET_DAMAGE, DEFAULT_MAGAZINE_SIZE, DEFAULT_RADIUS, 20);
	static Player player = new Player(bullet, 5);
	static Target[] target = Target.getTargetArray(NUMBER_OF_ZOMBIES, 10, 3, 50); 
	//Boss boss;
	
	Point2D CursorPosition;
	
	//graphics and animation variable
	Pane pane;
	Scene scene;
	Timeline timeline, refreshScreen;
	
	private ImageView backgroundImageView, HPIconImageView, rifleIconImageView, machinegunIconImageView, bossImageView;
	private ImageView[] bulletImageView, targetImageView, playerImageView; 

	private AudioClip[] gunShoot, gunReload; 
	//handGunShoot, handGunReload, machineGunShoot, machineGunReload;
    private Label HPLabel = new Label(), BulletLabel = new Label();
    private IntegerProperty HPIntegerProperty, BulletIntegerProperty;
    
    boolean moveLeft = false, moveRight = false, moveUp = false, moveDown = false;
    
    boolean mousePressed = false, handgunTrigger = false;
    double angle, mouseX, mouseY;
    long lastShootTime;
	
	public static void main(String[] arg){
		launch(arg);
	}
	
	@Override
	public void start(Stage stage){
				
		//player = new Player(); 
		
		pane = new Pane();
		Image roadImage = null;

		Image playerImageHandGun = null, playerImageRifle = null, playerImageMachineGun = null;
		Image zombieImage = null, zombieImage1 = null, zombieImage2 = null, bulletImage = null, bossImage = null;
		
		Image machinegunImage = null, rifleImage = null, HPIconImage = null;
		Image machinegunIconImage = null, rifleIconImage = null;
		Image crossHairImage = null;
		
		ImageView dummy, dummy1;
		timeline = new Timeline();
		refreshScreen = new Timeline();				
		
		gunShoot = new AudioClip[3];
		gunReload = new AudioClip[3];
		
		playerImageView = new ImageView[3];
		
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


			System.out.println("Image being imported.");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Initialize the variable and set necessary property
		backgroundImageView = new ImageView(roadImage);
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
		rifleIconImageView = new ImageView (rifleIconImage);
		machinegunIconImageView = new ImageView (machinegunIconImage);
		bossImageView = new ImageView (bossImage);
		bossImageView.setRotate(270);
		
		dummy = new ImageView(machinegunImage);
		dummy.setRotate(90);
		dummy.setY(200);
		dummy1 = new ImageView(rifleImage);
		dummy1.setRotate(90);
		
		HPIntegerProperty = new SimpleIntegerProperty(100);
        HPLabel.textProperty().bind(HPIntegerProperty.asString());
        HPLabel.setTextFill(Color.YELLOW);
        HPLabel.setFont(DEFAULT_FONT);
        HPLabel.setOpacity(0.8);
        
        BulletIntegerProperty = new SimpleIntegerProperty(DEFAULT_MAGAZINE_SIZE);
        BulletLabel.textProperty().bind(BulletIntegerProperty.asString());
        BulletLabel.setTextFill(Color.YELLOW);
        BulletLabel.setFont(DEFAULT_FONT);
        BulletLabel.setOpacity(0.75);
        
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
       
		pane.getChildren().addAll(backgroundImageView, HPLabel, BulletLabel, HPIconImageView, rifleIconImageView, machinegunIconImageView, bossImageView);
		pane.getChildren().addAll(playerImageView[0], playerImageView[1], playerImageView[2]);
		
		for(ImageView i : bulletImageView){
			pane.getChildren().addAll(i);
			i.setVisible(false);
		} 

		for(ImageView i: targetImageView){
			pane.getChildren().addAll(i);
        	i.setVisible(false);
        }

		bossImageView.setVisible(false);

		
//		Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
//        stage.setX(primaryScreenBounds.getMinX());
//        stage.setY(primaryScreenBounds.getMinY());
//        stage.setWidth(primaryScreenBounds.getWidth());
//        stage.setHeight(primaryScreenBounds.getHeight());
          
        //Set Positioning
        
		scene = new Scene(pane);
        
        player.setPosition(450, 275); 
//        		primaryScreenBounds.getHeight()/2);
        
		playerImageView[weaponSetting].setY(player.getYcoord());
		playerImageView[weaponSetting].setX(player.getXcoord());
		
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
						if (player.reload()) {
		        			reloadStartTime = System.currentTimeMillis();
		        			BulletIntegerProperty.setValue(Bullet.getMagazineSize());
		        			gunReload[weaponSetting].play(100);
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
        
        scene.setOnMouseClicked(new EventHandler<MouseEvent>(){
        	@Override
			public void handle(MouseEvent mouse) {
//				System.out.println("mouse clicked, Number of Unused Bullets: "+player.getNumberOfUnusedBullet());
//        		double angle = getFireAngle(mouse.getX(), mouse.getY());
//        		
//        		if((System.currentTimeMillis() - reloadStartTime) < 2000)
//        			return;
//        		
//        		if(player.fire(mouse.getX(), mouse.getY(), angle)){ 
//        			BulletIntegerProperty.setValue(BulletIntegerProperty.getValue()-1);
//        			gunShoot[weaponSetting].play(100);
//        		} 
//        		else{  //failed to fire, reload
//        			reloadStartTime = System.currentTimeMillis();
//        			player.reload();
//        			BulletIntegerProperty.setValue(Bullet.getMagazineSize());
//        			gunReload[weaponSetting].play(100);
//        		}
//				System.out.println("After fired,  "+player.getNumberOfUnusedBullet());
			}
        	
        });
        
        scene.setOnMousePressed(new EventHandler<MouseEvent>() {
        	public void handle(MouseEvent mouse){
        		mousePressed = true;
        		handgunTrigger = true;
//        		mouseX = mouse.getX();
//        		mouseY = mouse.getY();
        	}
        });
        
        scene.setOnMouseReleased(new EventHandler<MouseEvent>() {
        	public void handle(MouseEvent mouse){
        		mousePressed = false;
        		handgunTrigger = false;
        		System.out.println("Mouse Released");
        	}
        });
               
        scene.setOnMouseMoved(new EventHandler<MouseEvent>() {
        	public void handle(MouseEvent mouse){
        		double angle = getFireAngle(mouse.getX(), mouse.getY()); 
//        		System.out.println(angle);
        		mouseX = mouse.getX();
        		mouseY = mouse.getY();
        		if(mouse.getX()>player.getXcoord())
        			playerImageView[weaponSetting].setRotate(angle);
        		else
        			playerImageView[weaponSetting].setRotate(-1*angle);
        		
//                if(mouse.getY()<player.getYcoord())
//                	playerImageView.setRotate(90-angle);
//                else
//                	playerImageView.setRotate(90+angle);
        	}
        });
        
        scene.setCursor(new ImageCursor(crossHairImage));

		stage.setScene(scene);
		stage.setHeight(600);
		stage.setWidth(900);
		//stage.sizeToScene();
		stage.setResizable(false);
		stage.setTitle("ISOM3320 Game");
		stage.setFullScreen(false);

		refreshScreen.getKeyFrames().add( new KeyFrame(new Duration(33d), this));
		refreshScreen.setCycleCount(Timeline.INDEFINITE);
		refreshScreen.play();
		
		stage.show();
		rifleIconImageView.setVisible(false);
		machinegunIconImageView.setVisible(false);
		System.out.println("Stage being showed.");
		startTime = System.currentTimeMillis();
		
		backgroundImageView.setTranslateY(- 1431);
		
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
		
		if(mousePressed && ( (weaponSetting > 0 &&  System.currentTimeMillis() - lastShootTime > 33*5) || ( handgunTrigger && System.currentTimeMillis() - lastShootTime > 33*20))){
			handgunTrigger = false;
			double angle = getFireAngle(mouseX, mouseY);
			if((System.currentTimeMillis() - reloadStartTime) > 2000){
				if(player.fire(mouseX, mouseY, angle)){ 
					BulletIntegerProperty.setValue(BulletIntegerProperty.getValue()-1);
					gunShoot[weaponSetting].play(100);
					lastShootTime = System.currentTimeMillis();
				} 
				else{  //failed to fire, reload
					reloadStartTime = System.currentTimeMillis();
					player.reload();
					BulletIntegerProperty.setValue(Bullet.getMagazineSize());
					gunReload[weaponSetting].play(100);
				}		
			}
		}
		
//		System.out.println("mouse clicked, Number of Unused Bullets: "+player.getNumberOfUnusedBullet());
//		double angle = getFireAngle(mouse.getX(), mouse.getY());
//		
//		if((System.currentTimeMillis() - reloadStartTime) < 2000)
//			return;
//		
//		if(player.fire(mouse.getX(), mouse.getY(), angle)){ 
//			BulletIntegerProperty.setValue(BulletIntegerProperty.getValue()-1);
//			gunShoot[weaponSetting].play(100);
//		} 
//		else{  //failed to fire, reload
//			reloadStartTime = System.currentTimeMillis();
//			player.reload();
//			BulletIntegerProperty.setValue(Bullet.getMagazineSize());
//			gunReload[weaponSetting].play(100);
//		}
//		System.out.println("After fired,  "+player.getNumberOfUnusedBullet());
		
		//player movement
        if(moveUp && backgroundImageView.getTranslateY() < - 300){
        	// && backgroundImageView.getTranslateY() > - 500
        	backgroundImageView.setTranslateY(backgroundImageView.getTranslateY()+5);
        	if(rifleIconImageView.isVisible()){
        	//rifleIconImageView.setTranslateY(rifleIconImageView.getTranslateY()+5);
        	//rifleIconImageView.setX(rifleIconImageView.getX());
        	rifleIconImageView.setY(rifleIconImageView.getY()+5);
        	}
        	if(machinegunIconImageView.isVisible()){
        	//machinegunIconImageView.setTranslateY(machinegunIconImageView.getTranslateY()+5);
        	//machinegunIconImageView.setX(machinegunIconImageView.getX());
        	machinegunIconImageView.setY(machinegunIconImageView.getY()+5);
        	}
        	for(Target i : target){
        		i.changePosition(0, 5);
        	}
//        	player.move(0, -5);
        }
        
        if(moveDown && backgroundImageView.getTranslateY() > - 1920){
        	// && backgroundImageView.getTranslateY() < 500
        	backgroundImageView.setTranslateY(backgroundImageView.getTranslateY() - 5);
        	if(rifleIconImageView.isVisible()){
        	//rifleIconImageView.setTranslateY(rifleIconImageView.getTranslateY()-5);
        	//rifleIconImageView.setX(rifleIconImageView.getX());
        	rifleIconImageView.setY(rifleIconImageView.getY() - 5);
        	}
        	if(machinegunIconImageView.isVisible()){
        	//machinegunIconImageView.setTranslateY(machinegunIconImageView.getTranslateY() - 5);
        		//machinegunIconImageView.setX(machinegunIconImageView.getX());
            	machinegunIconImageView.setY(machinegunIconImageView.getY() - 5);
        	}
        	for(Target i : target){
        		i.changePosition(0, -5);
        	}
//        	player.move(0, 5);
        }
        
        if(moveLeft && backgroundImageView.getTranslateX() < 0){
        	backgroundImageView.setTranslateX(backgroundImageView.getTranslateX()+5);
        	if(rifleIconImageView.isVisible()){
        	//rifleIconImageView.setTranslateX(rifleIconImageView.getTranslateX()+5);
        	rifleIconImageView.setX(rifleIconImageView.getX()+5);
        	//rifleIconImageView.setY(rifleIconImageView.getY());
        	}
        	if(machinegunIconImageView.isVisible()){
        	//machinegunIconImageView.setTranslateX(machinegunIconImageView.getTranslateX()+5);
        		machinegunIconImageView.setX(machinegunIconImageView.getX()+5);
            	//machinegunIconImageView.setY(machinegunIconImageView.getY());
        	}
        	for(Target i : target){
        		i.changePosition(+5, 0);
        	}
        	//System.out.println(backgroundImageView.getTranslateX());
//        	player.move(-5, 0);
        }
        
        if(moveRight){ //backgroundImageView.getTranslateX()*-1 < MAX
        	backgroundImageView.setTranslateX(backgroundImageView.getTranslateX()-5);
        	if(rifleIconImageView.isVisible()){
        	//rifleIconImageView.setTranslateX(rifleIconImageView.getTranslateX()-5);
        	rifleIconImageView.setX(rifleIconImageView.getX()-5);
        	//rifleIconImageView.setY(rifleIconImageView.getY());
        	}
        	if(machinegunIconImageView.isVisible()){
        	//machinegunIconImageView.setTranslateX(machinegunIconImageView.getTranslateX()-5);
        		machinegunIconImageView.setX(machinegunIconImageView.getX()-5);
            	//machinegunIconImageView.setY(machinegunIconImageView.getY());
        	}
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
		        
		for(int i=0 ; i<bullet.length ; i++){
			for(int j=0 ; j<target.length;j++){
				if(target[j].isVisible() && bullet[i].isHit(target[j])){
					target[j].minusHealth(Bullet.getBulletDamage());
					
					bullet[i].setVisible(false);
					bullet[i].setIsMoving(false);
					bullet[i].setPosition(-999, -999);  //void the bullet
					bulletImageView[i].setVisible(false);
					
					if(target[j].isDead()){
						targetImageView[j].setVisible(false);
						target[j].setPosition(-999,-999);
						score++;
//						System.out.println(score);
					}
					
					break;
				}
			}
		}
		
		//reborbZombie
		if(System.currentTimeMillis()-startTime>20000){
			Target.rebornZombie(target, player.getPosition());
			System.out.println("reborned");
			startTime=System.currentTimeMillis();
			for(int i=0; i<target.length ; i++){
				if(target[i].isVisible()){
					targetImageView[i].setVisible(true);
					targetImageView[i].setRotate(target[i].getAngleOfChase(player.getPosition()));
					targetImageView[i].setX(target[i].getXcoord());
					targetImageView[i].setY(target[i].getYcoord());
				}
			}
		}
		
//		for(int i=0;i<target.length;i++){
//			System.out.println("X: "+target[i].getXcoord()+" Y: "+ target[i].getYcoord());
//			System.out.println("IX: "+targetImageView[i].getX()+" IY: "+ targetImageView[i].getY());
//		}
//		
		//show weapon
		if(!rifleIconImageView.isVisible() && backgroundImageView.getTranslateX() < -1000 ){
			newWeapon(rifleIconImageView);
			// System.out.println("Rifile");
		}
		if(!machinegunIconImageView.isVisible() && backgroundImageView.getTranslateX() < -2000){
			newWeapon(machinegunIconImageView);
			// System.out.println("MachineGUnww");
		}
//change weapon

		if ((Math.pow(Math.pow(player.getXcoord() - rifleIconImageView.getX(),2.0) 
				+ Math.pow(player.getYcoord() - rifleIconImageView.getY(),2.0), 0.5) 
				<= (player.getRadius() + 50)) && rifleIconImageView.isVisible()){
			pickWeapon(rifleIconImageView, 1);
		}
		if ((Math.pow(Math.pow(player.getXcoord() - machinegunIconImageView.getX(),2.0) 
					+ Math.pow(player.getYcoord() - machinegunIconImageView.getY(),2.0), 0.5) 
					<= (player.getRadius() + 50)) && machinegunIconImageView.isVisible()){
			pickWeapon(machinegunIconImageView, 2);
		}
		
		//Show Boss
		if(!bossImageView.isVisible() && backgroundImageView.getTranslateX() < -3000 ){
			bossImageView.setVisible(true);
			bossImageView.setX(600);
			bossImageView.setY(150);
			System.out.println("Boss");
		}
		
		//Game Over
		//if (player.isDead()) {
		//Change background to black, maybe
		//Systme.out.println (Game over and total score is ....);
		//Ask to input name
		//show top 3 score and show a button for restart
		//}
		
		//Win Game
		//if (Boss.isDead()){
		//Change background to black, maybe
		//Systme.out.println (Congratulation and total score is ....);
		//Ask to input name
		//show top 3 score and show a button for restart
		//}
	}
	
	
	public void newWeapon(ImageView weapon) {
			weapon.setVisible(true);
			weapon.setX((int)(player.getXcoord() + Math.random()*400));
			weapon.setY((int)(Math.random()*600));
	}
	public void pickWeapon (ImageView weapon, int index) {
		weapon.setVisible(false);
		pane.getChildren().remove(weapon);
		Bullet.setBulletDamage(BULLET_DAMAGE[index]);
		Bullet.setMagazineSize(MAGAZINE_SIZE[index]);
		playerImageView[weaponSetting].setVisible(false); // original
		weaponSetting = (short)index;
		playerImageView[weaponSetting].setVisible(true);
		playerImageView[weaponSetting].setX(player.getXcoord());
		playerImageView[weaponSetting].setY(player.getYcoord());
		player.reload();
		BulletIntegerProperty.setValue(Bullet.getMagazineSize());
		
	}

	public static Point2D getPlayerPosition(){
		Point2D position = player.getPosition();
		return position;
	}
	
	// calculate total score
		//public double totalScore() {
			//To Do, e.g.
			// number of Target kill * 10 + Boss kill + player's HP * 10 - time required 
		//}
	
	
}


