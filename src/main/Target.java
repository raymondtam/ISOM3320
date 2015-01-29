// tune radius, movingSpeed, health, attackDamage
package main;

import javafx.geometry.Point2D;

public class Target extends Character{
	private static final int NUMBER_OF_ZOMBIES = 10;
	private int attackDamage = 5;
	
	public Target(){
		this(3, 3, 15);
	}
	
	public Target(int health, int movingSpeed, int radius){
		this.setHealth(health);
		this.setMovingSpeed(movingSpeed);
		this.setRadius(radius);
	}
	
	public boolean isHit() {
		return true;
	}
	
	public boolean isHit(Bullet getShot){	
		if (Math.pow(Math.pow(getShot.getXcoord() - this.getXcoord(),2.0) + Math.pow(getShot.getYcoord() - this.getYcoord(),2.0), 0.5) >= 0){
			return true;
		}
		return false;
	}
	
	public static TargetArray[] getTargetArray(int size, int damage, int magazineSize, double radius) {
		Bullet[] TargetArray = new Bullet [size];
		for (int i = 0; i < TargetArray.length; i++){
			TargetArray[i] = new TargetArray (damage, magazineSize, radius);
		}
		return TargetArray;
	}
	
	public chase(){
		Point2D playerPosition = Gameboard.getPlyaerPosition();
		
	}

	public void move(int x, int y) {
		
	}

	public void move(double xIncrement, double yIncrement) {
		// TODO Auto-generated method stub
		
	}
