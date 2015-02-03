package main;

import javafx.geometry.Point2D;

public class Boss extends Target{
	//Variable
	private int maxHealth;
	
	//Constructor
	public Boss(){
	}
	public Boss(int health, int movingSpeed, int radius) {
		this.setHealth(health);
		this.setMovingSpeed(movingSpeed);
		this.setRadius(radius);
		this.maxHealth = health;
	}
	
	//Custom method
	static public void summonZombie (Target[] zombies, Point2D playerPosition, int range){
		for (int i = 0; i < zombies.length; i++){	//make all zombies visible
			if (zombies[i].isDead()){ 
				zombies[i].setHealth(30);
				zombies[i].setVisible(playerPosition, range);
			}
		}
	}
	
}
