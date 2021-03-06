/**
 * This target class defines objects which are character who attack player.
 * It allows the gameboard to get an array of target objects.
 * 
 * It defines how targets do to the player. The target objects find angles with the 
 * player and move toward player in coordinates.
 * 
 * It checks the interaction with the player, and do the corresponding damages to player's health.
 * 
 * It contains functions to generate zombies from four edges of the stage. If the player is near
 * boundary, the zombies can be regenerated and set visible on the other side of the boundary.
 * 
 */
import javafx.geometry.Point2D;

public class Target extends Character{
	private int attackDamage;
	private boolean isMoving;
	private int maxHealth; 
	
	public Target(){
		this(3, 5, 3, 15);
	}
	
	public Target(int health, int attackDamage, int movingSpeed, int radius){
		this.setHealth(health);
		this.setMovingSpeed(movingSpeed);
		this.setRadius(radius);
		maxHealth = health;
	}
	
	public int getAttackDamage(){
		return this.attackDamage;
	}
	
	public void setAttackDamage(int attackDamage){
		this.attackDamage = attackDamage;
	}
	
	public int getMaxHealth(){
		return this.maxHealth;
	}
	
	public void setMaxHealth(int health){
		this.setMaxHealth(health);
	}
    public boolean getIsMoving () {
    	return isMoving;
    }
    
    public void setIsMoving (boolean isMoving) {
    	this.isMoving = isMoving;
    }
    
	@Override
	public boolean isHit() {
		return true;
	}
	
	public boolean isHit(Bullet getShot){	
		if (Math.pow(Math.pow(getShot.getXcoord() - this.getXcoord(),2.0) 
				+ Math.pow(getShot.getYcoord() - this.getYcoord(),2.0), 0.5) //Distance between centres
				<= this.getRadius() + getShot.getRadius()){
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
	
	//for gameboard
	public static Target[] getTargetArray(int numberOfZombies, int health,
			int attackDamage, int movingSpeed, int radius) {
		Target[] TargetArray = new Target [numberOfZombies];
		for (int i = 0; i < TargetArray.length; i++){
			TargetArray[i] = new Target(health, attackDamage, movingSpeed, radius);
		}
		return TargetArray;
	}
    
	public double getAngleOfChase(Point2D playerPosition){
		Point2D zombiePosition = this.getPosition();
		Point2D yVector;
		double angleOfChase;
		if(this.getYcoord()>0)
			yVector = new Point2D(this.getXcoord(), 0);
		else
			yVector = new Point2D(this.getXcoord(), this.getYcoord()-1);
		angleOfChase = zombiePosition.angle(yVector, playerPosition);
		if(playerPosition.getX() < zombiePosition.getX())
			angleOfChase *= -1; //zombie rotates anticlockwisely toward player for the condition
		return angleOfChase;
	}
	
	@Override
	public void move(double xIncrement, double yIncrement){
		 this.changePosition(xIncrement, yIncrement);
	}
	
	//for targets chase toward player
	public void move(Point2D playerPosition) {
		double xIncrement, yIncrement;
		double distance, xDifference, yDifference;
		xDifference = playerPosition.getX() - this.getXcoord();
		yDifference = playerPosition.getY() - this.getYcoord();
		distance = Math.pow(Math.pow(xDifference, 2.0) + Math.pow(yDifference, 2.0), 0.5);
		xIncrement = this.getMovingSpeed() * xDifference / distance;
		yIncrement = this.getMovingSpeed() * yDifference / distance;
		this.move(xIncrement, yIncrement);
	}
	
	//set zombies randomly coming out from four edges
	public void setVisible(Point2D playerPosition){
		double randomEdge = Math.random();
		double randomCoord = Math.random() - 0.5;
		if (randomEdge >= 0 && randomEdge < 0.25){
			this.setPosition((int)(playerPosition.getX() + randomCoord * 900), (int)(playerPosition.getY() - (750 / 2))); 
		}
		else if (randomEdge >= 0.25 && randomEdge < 0.5){
			this.setPosition((int)(playerPosition.getX() + 500 ), (int)(playerPosition.getY() + randomCoord * 600)); 
		}
		else if (randomEdge >= 0.5 && randomEdge < 0.75){
			this.setPosition((int)(playerPosition.getX() + randomCoord * 900), (int)(playerPosition.getY() + (750 / 2))); 
		}
		else {
			this.setPosition((int)(playerPosition.getX() - 500 ), (int)(playerPosition.getY() + randomCoord * 600)); 
		}
		this.setVisible(true);
	}
	
	//set with specified range around playerposition
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
		this.setPosition((int)(playerPosition.getX() + randomCoord * 900), (int)(playerPosition.getY() - (750 / 2))); 
		//zombies set from only lower edge
		this.setVisible(true);
		System.out.println("Call zombies near lower boundary with zombies coming from top");
	}
	
	public void setVisibleAtBottom(Point2D playerPosition){
		double randomCoord = Math.random() - 0.5;
		this.setPosition((int)(playerPosition.getX() + randomCoord * 900), (int)(playerPosition.getY() + (750 / 2))); 
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
		for (int i = 0, j = 0; i < numberOfZombiesToReborn && j < zombies.length; i++, j++){
			if (zombies[j].isDead()){ 
				zombies[j].setHealth(zombies[j].getMaxHealth());
				zombies[j].setVisible(playerPosition);
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
			// lower boundary of map
			for (int i = 0, j = 0; i < numberOfZombiesToReborn && j < zombies.length; i++, j++){
				if (zombies[j].isDead()){
					zombies[j].setHealth(zombies[j].getMaxHealth());
					zombies[j].setVisibleAtBottom(playerPosition);
				}
			}
		}
		else if (boundaryLimit <= - 1721){
			// upper boundary of map
			for (int i = 0, j = 0; i < numberOfZombiesToReborn && j < zombies.length; i++, j++){
				if (zombies[j].isDead()){
					zombies[j].setHealth(zombies[j].getMaxHealth());
					zombies[j].setVisibleAtTop(playerPosition);
				}
			}
		}
	}
	
	@Override
	public String toString(){
		return "This Target is at" + this.getPosition().toString() + "with radius: " + this.getRadius()
				+ "and is " + ((this.isVisible())?"Visible":"Invisible") + " with movingspeed " + this.getMovingSpeed() +
			    " and attackDamage:" + this.getAttackDamage() + "/n";
	}
}