package main;

import javafx.geometry.Point2D;

public class Player extends Character{
	private int numberOfUnusedBullet;
	Bullet[] bullet = null;
	
	//Constructor
	Player() {
	}
	
	Player(Bullet[] bullet) {
		super();
		this.bullet = bullet;
		this.numberOfUnusedBullet = Bullet.getMagazineSize();
	}
	
	public int getNumberOfUnusedBullet() {
		return numberOfUnusedBullet;
	}
	
	private void setNumberOfUnusedBullet(int numberOfUnusedBullet) {
		this.numberOfUnusedBullet = numberOfUnusedBullet;
	}
	
	@Override
	public boolean isHit() {
//		if (tagetPosition.distance(getPlayerPosition()) <= target.radius + player.radius) 
//		    return true;
//		else 
//		    return false;
		// TODO
		return false;
	}

	@Override
	public void move(double xIncrement, double yIncrement) {
//		if ((600 - getYcoord() >= 15 ) || (getYcoord() <= 15) ) //TODO boundary condition
			changePosition(xIncrement,yIncrement);
	}
		
	public void reload() {
		if (numberOfUnusedBullet < Bullet.getMagazineSize()) {
			setNumberOfUnusedBullet (Bullet.getMagazineSize());
		}
	}
	
	public void fire(double xCursor, double yCursor) {
		if (numberOfUnusedBullet > 0) {
			int index = Bullet.getMagazineSize() - numberOfUnusedBullet;
			double xPlayer = getXcoord(); 
			double yPlayer = getYcoord();
			double distance = Math.pow(Math.pow((xCursor - xPlayer),2.0) +Math.pow((yCursor - yPlayer), 2.0), 0.5);
			double nomralizedX = (xCursor - xPlayer) / distance;
			double normalizedY = (yCursor - yPlayer) / distance;
			numberOfUnusedBullet--;
			bullet[index].setPosition(xPlayer,yPlayer);
			bullet[index].setXVelocity(nomralizedX);
			bullet[index].setYVelocity(normalizedY);
		}
		else
			reload();
	}
	
//	private Point2D fireVector(double xPlayer, double yPlayer, double xCursor, double yCursor) {
//		Point2D bulletPath = new Point2D(xCursor, yCursor);
//		bulletPath = bulletPath.subtract(xPlayer, yPlayer);
//		return bulletPath;
//	}
	
	@Override
	public String toString(){
		return super.toString() + "numberOfUnusedBullet: " + numberOfUnusedBullet + "\n";
	}
}
