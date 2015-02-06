/**
 * 
 */
package main;

public class Player extends Character{
	//Variable
	private int numberOfUnusedBullet;
	Bullet[] bullet = null;
	
	//Constructor
	Player() {
	}
	
	public Player(Bullet[] bullet, int movingSpeed, int health) {
		this.bullet = bullet;
		this.numberOfUnusedBullet = Bullet.getMagazineSize();
		this.setHealth(health);
		this.setMovingSpeed(movingSpeed);
	}
	
	//Mutator and accessor
	public int getNumberOfUnusedBullet() {
		return numberOfUnusedBullet;
	}
	
	public void setNumberOfUnusedBullet(int numberOfUnusedBullet) {
		this.numberOfUnusedBullet = numberOfUnusedBullet;
	}
	
	//Custom method
	@Override
	public boolean isHit() {
		return true;
	}
	
	public boolean isHit(Target target) {
		if (target.getPosition().distance(getPosition()) <= this.getRadius() + target.getRadius()) {
			return true;
		}
		return false;
	}

	@Override
	public void move(double xIncrement, double yIncrement) {
			changePosition(xIncrement,yIncrement);
	}
		
	public boolean reload() {
		if (numberOfUnusedBullet < Bullet.getMagazineSize()) {
			setNumberOfUnusedBullet (Bullet.getMagazineSize());
			return true;
		}
		else
			return false;
	}
	
	public boolean fire(double xCursor, double yCursor, double vectorAngle) {
		if (numberOfUnusedBullet > 0) {
			int index = Bullet.getMagazineSize() - numberOfUnusedBullet;
			double xPlayer = getXcoord(); 
			double yPlayer = getYcoord();
			double distance = Math.pow(Math.pow((xCursor - xPlayer),2.0) +Math.pow((yCursor - yPlayer), 2.0), 0.5);
			double normalizedX = (xCursor - xPlayer) / distance;
			double normalizedY = (yCursor - yPlayer) / distance;
			numberOfUnusedBullet--;
			bullet[index].setVisible(true);
			bullet[index].setPosition(xPlayer,yPlayer);
			bullet[index].setIsMoving(true);
			bullet[index].setXVelocity(normalizedX);
			bullet[index].setYVelocity(normalizedY);
			bullet[index].setFireAngle(vectorAngle);
			return true;
		}
		else
			return false;
	}
	
	@Override
	public String toString(){
		return super.toString() + "numberOfUnusedBullet: " + numberOfUnusedBullet + "\n";
	}
}
