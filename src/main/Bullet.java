package main;

public class Bullet extends GameObject {
    //Variable
	private static int bulletDamage;
    private static int magazineSize;
    private boolean isMoving;
    private double xVelocity;
    private double yVelocity;
    private double fireAngle;
  
    //Constructor
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
    
    //Mutator and accessor
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
<<<<<<< HEAD
    
    //Custom Method
=======
   
    //Method
>>>>>>> origin/master
	@Override
	public boolean isHit() {
		return true;
	}
	
	public boolean isHit(Target targetShot) {
<<<<<<< HEAD
		if (Math.pow(Math.pow(targetShot.getXcoord() - this.getXcoord(),2.0) 
			+ Math.pow(targetShot.getYcoord() - this.getYcoord(),2.0), 0.5) 
			<= (targetShot.getRadius() + this.getRadius())){
			return true;
		}
		else {
			return false;}
=======
			if (Math.pow(Math.pow(targetShot.getXcoord() - this.getXcoord(),2.0) 
					+ Math.pow(targetShot.getYcoord() - this.getYcoord(),2.0), 0.5) //Distance between centres < sum of radius 
					<= (targetShot.getRadius() + this.getRadius())){
				return true;
			}
			else {
		return false;}
>>>>>>> origin/master
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


