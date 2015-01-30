// tune radius, movingSpeed, health, attackDamage
package main;

import javafx.geometry.Point2D;

public class Target extends Character{
	private int attackDamage;
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
		Point2D Horizons = new Point2D(1, 0);
		double angleOfChase = zombiePosition.angle(Horizons, playerPosition);
		zombiePosition = null;
		Horizons = null;
		return angleOfChase;
	}
	
	public void move(double xIncrement, double yIncrement){
		 this.changePosition(xIncrement, yIncrement);
	}
	
	public void move(Point2D playerPosition) {
		double xIncrement, yIncrement;
		double angle = getAngleOfChase(playerPosition);
		double cosAngle = Math.cos(this.getAngleOfChase(playerPosition));
		double sinAngle = Math.sin(this.getAngleOfChase(playerPosition));
		
		if (this.getXcoord() < playerPosition.getX() && this.getYcoord() < playerPosition.getY()){
			xIncrement = this.getMovingSpeed() * cosAngle;
			yIncrement = this.getMovingSpeed() * sinAngle;
		}
		else if (this.getXcoord() < playerPosition.getX() && this.getYcoord() > playerPosition.getY()){
			xIncrement = this.getMovingSpeed() * cosAngle;
			yIncrement = - this.getMovingSpeed() * sinAngle;
		}
		else if (this.getXcoord() > playerPosition.getX() && this.getYcoord() < playerPosition.getY()){
			xIncrement = - this.getMovingSpeed() * cosAngle;
			yIncrement = this.getMovingSpeed() * sinAngle;
		}
		else {
			xIncrement = - this.getMovingSpeed() * cosAngle;
			yIncrement = - this.getMovingSpeed() * sinAngle;
		}
		this.move(xIncrement, yIncrement);
	}
	
	public void setVisible(Point2D playerPosition){
		int randomNum = -1 + (Math.random() * 2 - 1);
		
		if (number > 0.0 && number <= 0.25){
			this.setXcoord = playerPosition.getX() - 450;
			
		}
		else{
			
		}
		this.setVisible(true);
		
	}

    /*
	public String toString(){
		return "This Target is at" + this.getPosition().toString() + "with radius: " + this.getRadius()
				+ "and is " + ((visible)?"Visible":"Invisible") + " with movingspeed " + this.getmovingSpeed +
				+ " attackDamage" + this.attackDamage + "\n" ;
	}
	*/

}