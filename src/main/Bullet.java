package main;

public class Bullet extends GameObject {
    //Attribute
	private static int bulletDamage;
    private static int magazineSize;
    private boolean isMoving;
    private double xVelocity;
    private double yVelocity;
    private double fireAngle;
  
    //Constructors
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
    public static int getMagazineSize () {
    	return magazineSize;
    }
    public boolean getIsMoving () {
    	return isMoving;
    }
    public double getXVelocity () {
    	return xVelocity;
    }
    public double getYVelocity () {
    	return yVelocity;
    }
    public double getFireAngle () {
    	return fireAngle;
    }
    public static void setBulletDamage (int bulletDamage) {
    	Bullet.bulletDamage = bulletDamage;
    }
    public static void setMagazineSize (int magazineSize) {
    	Bullet.magazineSize = magazineSize;
    }
    public void setIsMoving (boolean isMoving) {
    	this.isMoving = isMoving;
    }
    public void setXVelocity (double xVelocity) {
    	this.xVelocity = xVelocity;
    }
    public void setYVelocity (double yVelocity) {
    	this.yVelocity = yVelocity;
    }
    public void setFireAngle (double fireAngle) {
    	this.fireAngle = fireAngle;
    }
    //Method
	@Override
	public boolean isHit() {
		return true;
	}
	public boolean isHit(Target targetShot) {
			if (Math.pow(Math.pow(targetShot.getXcoord() - this.getXcoord(),2.0) 
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


