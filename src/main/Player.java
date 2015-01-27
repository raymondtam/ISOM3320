package main;

import javafx.geometry.Point2D;

public class Player extends Character{
	int numberOfUnusedBullet;
	Bullet bullet= new Bullet();
	
	//Constructor
	Player() {
	}
	
	Player (int numberOfUnusedBullet) {
		super();
		this.numberOfUnusedBullet = numberOfUnusedBullet;
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
	public boolean move(int x, int y) {
		Point2D position = getPosition();
		position = position.add(x,y);
		return false;
	}
	
	@Override
	public boolean isDead() {
		return false;
	}
	
	public void reload() {
		if (numberOfUnusedBullet < bullet.getMagazineSize()) {
			setNumberOfUnusedBullet (bullet.getMagazineSize());
		}
	}
	
	public void fire() {
		if (numberOfUnusedBullet > 0) {
			bullet.setVisible(true);
			// bullet.setPosition(x, y);
			// bullet.move(x, y);
			// TODO
		}
		else
			reload();
	}
	
	@Override
	public String toString(){
		return super.toString() + "numberOfUnusedBullet: " + numberOfUnusedBullet + "\n";
	}
}
