package main;

public class Player extends Character{
	private int numberOfUnusedBullet;
	Bullet[] bullet = null;
	
	//Constructor
	Player() {
	}
	
	Player(Bullet[] bullet, int movingSpeed) {
		this.bullet = bullet;
		this.numberOfUnusedBullet = Bullet.getMagazineSize();
		this.setMovingSpeed(movingSpeed);
	}
	
	public int getNumberOfUnusedBullet() {
		return numberOfUnusedBullet;
	}
	
	private void setNumberOfUnusedBullet(int numberOfUnusedBullet) {
		this.numberOfUnusedBullet = numberOfUnusedBullet;
	}
		
	@Override
	public boolean isHit() {
		return true;
	}
	
	public boolean isHit(Target target,int attackDamage) {
		if (target.getPosition().distance(getPosition()) <= this.getRadius() + target.getRadius()) {
			minusHealth(attackDamage);
			return true;
		}
		return false;
	}

	@Override
	public void move(double xIncrement, double yIncrement) {
			changePosition(xIncrement,yIncrement);
	}
		
	public void reload() {
		if (numberOfUnusedBullet < Bullet.getMagazineSize()) {
			setNumberOfUnusedBullet (Bullet.getMagazineSize());
		}
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
			bullet[index].setVisible(true);
			bullet[index].setPosition(xPlayer,yPlayer);
			bullet[index].setIsMoving(true);
			bullet[index].setXVelocity(normalizedX);
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
