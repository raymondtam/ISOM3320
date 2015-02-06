/**
 * 
 */
package main;

public class Player extends Character{
	private int numberOfUnusedBullet;
	Bullet[] bullet = null;
	
	public Player() {
	}
	
	
	public Player(Bullet[] bullet, int movingSpeed, int health) {
		this.bullet = bullet;
		this.numberOfUnusedBullet = Bullet.getMagazineSize();
		this.setHealth(health);
		this.setMovingSpeed(movingSpeed);
	}
	
	public int getNumberOfUnusedBullet() {
		return numberOfUnusedBullet;
	}
	
	public void setNumberOfUnusedBullet(int numberOfUnusedBullet) {
		this.numberOfUnusedBullet = numberOfUnusedBullet;
	}
	
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
			double fireAngle;
			if (normalizedX >= 0)
				fireAngle = vectorAngle;
			else
				fireAngle = -vectorAngle;
			numberOfUnusedBullet--;
			//fire out one bullet from player angle by cursor
			bullet[index].setVisible(true);
			bullet[index].setPosition(xPlayer,yPlayer);
			bullet[index].setIsMoving(true);
			bullet[index].setXVelocity(normalizedX); //speed set by ratio of distance in x to total distance
			bullet[index].setYVelocity(normalizedY);
			bullet[index].setFireAngle(fireAngle);
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
