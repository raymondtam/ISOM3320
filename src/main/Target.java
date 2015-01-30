// tune radius, movingSpeed, health, attackDamage
package main;

import javafx.geometry.Point2D;

public class Target extends Character{
	private static final int NUMBER_OF_ZOMBIES = 10;
	private int attackDamage = 5;
	private double xVelocity;
	private double yVelocity;
	
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
		if (Math.pow(Math.pow(getShot.getXcoord() - this.getXcoord(),2.0) + Math.pow(getShot.getYcoord() - this.getYcoord(),2.0), 0.5) <= 0){
			return true;
		}
		return false;
	}

	public static Target[] getTargetArray(int numberOfZombies, int health, int movingSpeed, int radius) {
		Target[] TargetArray = new Target [numberOfZombies];
		for (int i = 0; i < TargetArray.length; i++){
			TargetArray[i] = new Target(health, movingSpeed, radius);
		}
		return TargetArray;
	}
	
	public double xVelocity(Point2D playerPosition){	
		double angle = getAngleOfChase(playerPosition);
		
	}
	
	public double yVelocity(Point2D playerPosition){
		double angle = getAngleOfChase(playerPosition);
		
	}
	
	// AI code to chase the Player
	public double getAngleOfChase(Point2D playerPosition){
		Point2D zombiePosition = this.getPosition();
		Point2D Horizons = new Point2D(this.getXcoord() + 1, 0);
		double angleOfChase = zombiePosition.angle(Horizons, playerPosition);
		zombiePosition = null;
		Horizons = null;
		return angleOfChase;
	}
	
	public 

	
	public void move(double xIncrement, double yIncrement) {
		this.setVisible(true); 
			changePosition(xIncrement,yIncrement);
	}
	
	public int getAttackDamage(){
		return this.
	}
	
	public int getAttackDamage(){
		return this.
	}

	public String toString(){
		return "This Target is at" + this.position.toString() + "with radius: " + this.getRadius() 
				+ "and is " + ((visible)?"Visible":"Invisible") + " with movingspeed " + movingSpeed +
				+ " attackDamage" + this.attackDamage + "\n" ;
	}

}