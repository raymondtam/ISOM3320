package main;

import javafx.geometry.Point2D;

public class Player extends Character{
	int numberOfUnusedBullet;
	Bullet[] bullet = null;
	
	//Constructor
	Player() {
	}
	
	Player (Bullet[] bullet) {
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
	public void move(int x, int y) {
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
			numberOfUnusedBullet--;
			bullet[index].setPosition(getXcoord(), getYcoord());
			
			bullet[index].move(fireVector()); // TODO
			// TODO
		}
		else
			reload();
	}
	
	private Point2D fireVector() {
		Point2D bulletPath;
		bulletPath = 
		return bulletPath;
		
	}
	
	@Override
	public String toString(){
		return super.toString() + "numberOfUnusedBullet: " + numberOfUnusedBullet + "\n";
	}
}
