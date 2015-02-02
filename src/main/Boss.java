package main;

public class Boss extends Target{
	private static int maxHealth;
	
	public Boss(){
	}
	
	public Boss(int health, int movingSpeed, int radius) {
		this.setHealth(health);
		this.setMovingSpeed(movingSpeed);
		this.setRadius(radius);
		maxHealth = health;
	}
	
	public void summonZombie(Target[] zombie) {
		for (int i = 0; i < zombie.length; i++) zombie[i].setVisible(true);
	}
	
}
