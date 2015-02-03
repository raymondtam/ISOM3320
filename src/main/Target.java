// tune radius, movingSpeed, health, attackDamage
package main;

import javafx.geometry.Point2D;

public class Target extends Character{
	//Variable
	private int attackDamage;
	private boolean isMoving;
	private static int maxHealth; 
	
	//Constructor
	public Target(){
		this(3, 3, 15);
	}
	
	public Target(int health, int movingSpeed, int radius){
		this.setHealth(health);
		this.setMovingSpeed(movingSpeed);
		this.setRadius(radius);
		maxHealth = health;
	}
	
	//Mutator and accessor
	public int getAttackDamage(){
		return this.attackDamage;
	}
	
	public void setAttackDamage(int attackDamage){
		this.attackDamage = attackDamage;
	}
	
    public boolean getIsMoving () {
    	return isMoving;
    }
    
    public void setIsMoving (boolean isMoving) {
    	this.isMoving = isMoving;
    }
    
	//Custom method
	@Override
	public boolean isHit() {
		return true;
	}
	
	public boolean isHit(Bullet getShot){	
		if (Math.pow(Math.pow(getShot.getXcoord() - this.getXcoord(),2.0) + Math.pow(getShot.getYcoord() - this.getYcoord(),2.0), 0.5) <= this.getRadius()){
			return true;
		}
		return false;
	}
	
	public boolean didAttackPlayer(Point2D playerPosition, int playerRadius){
		if (Math.pow(Math.pow(playerPosition.getX() - this.getXcoord(),2.0) + Math.pow(playerPosition.getY() - this.getYcoord(),2.0), 0.5) 
				<= this.getRadius() + playerRadius){
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
        
	public double getAngleOfChase(Point2D playerPosition){
		Point2D zombiePosition = this.getPosition();
		Point2D yVector;
		
		if(this.getYcoord()>0)
			yVector = new Point2D(this.getXcoord(), 0);
		else
			yVector = new Point2D(this.getXcoord(), this.getYcoord()-1);
		
		double angleOfChase = zombiePosition.angle(yVector, playerPosition);
		if(playerPosition.getX()<zombiePosition.getX())
			angleOfChase*=-1;
		return angleOfChase;
	}
	
	@Override
	public void move(double xIncrement, double yIncrement){
		 this.changePosition(xIncrement, yIncrement);
	}
	
	public void move(Point2D playerPosition) {
		double xIncrement, yIncrement;
		double distance, xDifference, yDifference;
		// double angle = getAngleOfChase(playerPosition);
		// double cosAngle = Math.cos(angle);
		// double sinAngle = Math.sin(angle);
		xDifference = playerPosition.getX() - this.getXcoord();
		yDifference = playerPosition.getY() - this.getYcoord();
		distance = Math.pow(Math.pow(xDifference, 2.0) + Math.pow(yDifference, 2.0), 0.5);
		
		//if (xDifference > 0 && yDifference > 0){
		xIncrement = this.getMovingSpeed() * xDifference / distance;
		yIncrement = this.getMovingSpeed() * yDifference / distance;
		this.move(xIncrement, yIncrement);
		//}
		// xIncrement = this.getMovingSpeed() * sinAngle;
		// yIncrement = this.getMovingSpeed() * cosAngle *-1;  // to compensate to revser of y axis of javafx
		// this.move(xIncrement, yIncrement);
		
	}
	
	public void setVisible(Point2D playerPosition){
		double randomEdge = Math.random();
		double randomCoord = Math.random() - 0.5;
		if (randomEdge >= 0 && randomEdge < 0.25){
			this.setPosition((int)(playerPosition.getX() + randomCoord * 900), (int)(playerPosition.getY() - (600 / 2))); 
		}
		else if (randomEdge >= 0.25 && randomEdge < 0.5){
			this.setPosition((int)(playerPosition.getX() + (900 / 2)), (int)(playerPosition.getY() + randomCoord * 600)); 
		}
		else if (randomEdge >= 0.5 && randomEdge < 0.75){
			this.setPosition((int)(playerPosition.getX() + randomCoord * 900), (int)(playerPosition.getY() + (600 / 2))); 
		}
		else {
			this.setPosition((int)(playerPosition.getX() - (900 / 2)), (int)(playerPosition.getY() + randomCoord * 600)); 
		}
		this.setVisible(true);
	}
	
	public void setVisible(Point2D playerPosition, int range){
		double randomEdge = Math.random();
		double randomCoord = Math.random() - 0.5;
		if (randomEdge >= 0 && randomEdge < 0.25){
			this.setPosition((int)(playerPosition.getX() + randomCoord * range), (int)(playerPosition.getY() - (range / 2))); 
		}
		else if (randomEdge >= 0.25 && randomEdge < 0.5){
			this.setPosition((int)(playerPosition.getX() + (range / 2)), (int)(playerPosition.getY() + randomCoord * range)); 
		}
		else if (randomEdge >= 0.5 && randomEdge < 0.75){
			this.setPosition((int)(playerPosition.getX() + randomCoord * range), (int)(playerPosition.getY() + (range / 2))); 
		}
		else {
			this.setPosition((int)(playerPosition.getX() - (range / 2)), (int)(playerPosition.getY() + randomCoord * range)); 
		}
		this.setVisible(true);
	}
	
	
	public void setVisibleAtTop(Point2D playerPosition){
		double randomCoord = Math.random() - 0.5;
		this.setPosition((int)(playerPosition.getX() + randomCoord * 900), (int)(playerPosition.getY() - (600 / 2))); 
		this.setVisible(true);
		System.out.println("Call zombies near lower boundary with zombies coming from top");
	}
	
	public void setVisibleAtBottom(Point2D playerPosition){
		double randomCoord = Math.random() - 0.5;
		this.setPosition((int)(playerPosition.getX() + randomCoord * 900), (int)(playerPosition.getY() + (600 / 2))); 
		this.setVisible(true);
		System.out.println("Call zombies near top boundary with zombies coming from below");
	}
	
	
	static public void rebornZombie (Target[] zombies, Point2D playerPosition){
		double random = Math.random(); 
		int numberOfDeadZombies = 0;
		int numberOfZombiesToReborn = 0;
		for (int i = 0; i < zombies.length; i++){
			if (zombies[i].isDead()) numberOfDeadZombies++;
		}
		numberOfZombiesToReborn = (int)(random * numberOfDeadZombies);
		for (int i = 0, j = 0; i < numberOfZombiesToReborn && j < zombies.length; j++){
			if (zombies[j].isDead()){ 
				zombies[j].setHealth(maxHealth);
				zombies[j].setVisible(playerPosition);
				i++;
			}
		}
	}
	
	static public void rebornZombieNearBoundary (Target[] zombies, Point2D playerPosition, int boundaryLimit){
		double random = Math.random(); 
		int upperSideLimit = - 581;
		int lowerSideLimit = - 1721;
		int numberOfDeadZombies = 0;
		int numberOfZombiesToReborn = 0;
		for (int i = 0; i < zombies.length; i++){
			if (zombies[i].isDead()) numberOfDeadZombies++;
		}
		numberOfZombiesToReborn = (int)(random * numberOfDeadZombies);
		if (boundaryLimit >= - 581){
			for (int i = 0, j = 0; i < numberOfZombiesToReborn && j < zombies.length; j++){
				if (zombies[j].isDead()){ 
					zombies[j].setHealth(maxHealth);
					zombies[j].setVisibleAtBottom(playerPosition);
					i++;
				}
			}
		}
		else if (boundaryLimit <= - 1721){
			for (int i = 0, j = 0; i < numberOfZombiesToReborn && j < zombies.length; j++){
				if (zombies[j].isDead()){ 
					zombies[j].setHealth(maxHealth);
					zombies[j].setVisibleAtTop(playerPosition);
					i++;
				}
			}
		}
	}
	
	public void rebornZombie (Target[] zombies, int level){
		double random = Math.random(); 
	}
	
	@Override
	public String toString(){
		return "This Target is at" + this.getPosition().toString() + "with radius: " + this.getRadius()
				+ "and is " + ((this.isVisible())?"Visible":"Invisible") + " with movingspeed " + this.getMovingSpeed() +
			    " and attackDamage:" + this.getAttackDamage() + "/n";
	}
	

}