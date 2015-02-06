/**
 * This bullet class defines objects which are game objects fired by player. It extends the 
 * GameObject class with additional attributes of bullet damage, magazine size, whether the 
 * bullet is moving or not, the velocity in both x and y directions, as well as the angle to 
 * be fired.  
 * 
 * There are three characteristic functions in the bullet class. 
 * 
 * First, there is a function called "isHit" to check whether the bullet hits the specific 
 * target by comparing the shortest distance between the centres of the two objects -- bullet
 * and target with the sum of radii of the two objects.
 * 
 * Second, the "move" function allows the bullet to change its position by a small x and y 
 * increment specified. Hence, the bullet can move certain distance every time the Gameboard is
 * refreshed.
 * 
 * Third, the getBulletArray function allows the Gameboard to create an array of bullet objects
 * with specified size and attributes.
 */
package main;

public class Bullet extends GameObject {
	private static int bulletDamage;
    private static int magazineSize;
    private boolean isMoving;
    private double xVelocity;
    private double yVelocity;
    private double fireAngle;
  
    Bullet () {
    	Bullet.bulletDamage = 0;
    	Bullet.magazineSize = 0;
    }
    Bullet (int bulletDamage, int magazineSize, double radius, int movingSpeed) {
    	Bullet.bulletDamage = bulletDamage;
    	Bullet.magazineSize = magazineSize;
    	this.setRadius(radius);
    	this.setMovingSpeed(movingSpeed);
    }
    
    public static int getBulletDamage () {
    	return bulletDamage;
    }
    public static void setBulletDamage (int bulletDamage) {
    	Bullet.bulletDamage = bulletDamage;
    }
    public static int getMagazineSize () {
    	return magazineSize;
    }
    public static void setMagazineSize (int magazineSize) {
    	Bullet.magazineSize = magazineSize;
    }
    public boolean getIsMoving () {
    	return isMoving;
    }
    public void setIsMoving (boolean isMoving) {
    	
    	this.isMoving = isMoving;
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
    public double getFireAngle () {
    	return fireAngle;
    }
    public void setFireAngle (double fireAngle) {
    	this.fireAngle = fireAngle;
    }

	@Override
	public boolean isHit() {
		return true;
	}
	public boolean isHit(Target targetShot) {
		if (Math.pow(Math.pow(targetShot.getXcoord() - this.getXcoord(),2.0) //Distance between two centres
			+ Math.pow(targetShot.getYcoord() - this.getYcoord(),2.0), 0.5) 
			<= (targetShot.getRadius() + this.getRadius())){
			return true;
		}
		else {
			return false;}
	}
	
	@Override
	public void move(double xIncrement, double yIncrement) {
			changePosition(xIncrement,yIncrement);
	}
	
	//for gameboard
	public static Bullet[] getBulletArray(int size, int damage, int magazineSize, double radius, int speed) {
		Bullet[] bulletArray = new Bullet [size];
		for (int i = 0; i < bulletArray.length; i++){
			bulletArray[i] = new Bullet (damage, magazineSize, radius, speed);
		}
		return bulletArray;
	}
	
	@Override
	public String toString(){
		return super.toString() + "bullet damage: " + bulletDamage + 
				"magazine size: " + magazineSize + "\n";
	}
}


