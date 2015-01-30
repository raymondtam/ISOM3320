// tune radius, movingSpeed, health, attackDamage
package main;

import javafx.geometry.Point2D;

public class Target extends Character{
	private int attackDamage;
	private double xVelocity;
	private double yVelocity;
	private boolean isMoving;
	
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
	
    public double getXVelocity () {
    	return xVelocity;
    }
    
    public void setXVelocity (double xVelocity) {
    	this.xVelocity = xVelocity;
    }
    
    public double getYVelocity () {
    	return yVelocity;
    }
    
    public void setYVelocity (double yVelocity) {
    	this.yVelocity = yVelocity;
    }
    
    public boolean getIsMoving () {
    	return isMoving;
    }
    
    public void setIsMoving (boolean isMoving) {
    	this.isMoving = isMoving;
    }
    
	public int getAttackDamage(){
		return this.attackDamage;
	}
	
	public void setAttackDamage(int attackDamage){
		this.attackDamage = attackDamage;
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
	
	public void move(double xIncrement, double yIncrement) {
		this.setVisible(true); 
		this.changePosition(xIncrement,yIncrement);
	}


	public String toString(){
		return "This Target is at" + this.position.toString() + "with radius: " + this.getRadius()
				+ "and is " + ((visible)?"Visible":"Invisible") + " with movingspeed " + this.getmovingSpeed +
				+ " attackDamage" + this.attackDamage + "\n" ;
	}

}