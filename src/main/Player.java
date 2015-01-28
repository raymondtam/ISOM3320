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
		
		// TODO
		return false;
	}

	@Override
	public void move(double x, double y) {
		Point2D position = getPosition();
		position = position.add(x,y);
	}
		
	public void reload() {
		if (numberOfUnusedBullet < Bullet.getMagazineSize()) {
			setNumberOfUnusedBullet (Bullet.getMagazineSize());
		}
	}
	
	public void fire() {
		if (numberOfUnusedBullet > 0) {
			int index = Bullet.getMagazineSize() - numberOfUnusedBullet;
			double xPlayer = getXcoord(); 
			double yPlayer = getYcoord();
			double xCursor; //TODO
			double yCursor; //TODO
			numberOfUnusedBullet--;
			bullet[index].setPosition(xPlayer,yPlayer);
			bullet[index].move(fireVector(xPlayer,yPlayer, xCursor, yCursor)); // TODO
			// TODO
		}
		else
			reload();
	}
	
	private Point2D fireVector(double xPlayer, double yPlayer, double xCursor, double yCursor) {
		Point2D bulletPath = new Point2D(xCursor, yCursor);
		bulletPath.subtract(xPlayer, yPlayer);
		return bulletPath;
		
	}
	
	@Override
	public String toString(){
		return super.toString() + "numberOfUnusedBullet: " + numberOfUnusedBullet + "\n";
	}
}
