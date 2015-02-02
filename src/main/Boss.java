package main;

import javafx.geometry.Point2D;

public class Boss extends Target{
	
	private int maxHealth;

	public Boss(){
	}
	
	public Boss(int health, int movingSpeed, int radius) {
		this.setHealth(health);
		this.setMovingSpeed(movingSpeed);
		this.setRadius(radius);
		this.maxHealth = health;
		//maxHealth = health;
	}
	
	static public void summonZombie (Target[] zombies, Point2D playerPosition, int range){
		for (int i = 0; i < zombies.length; i++){
			if (zombies[i].isDead()){ 
				zombies[i].setHealth(100);
				zombies[i].setVisible(playerPosition, range);
			}
		}
	}
	
}
