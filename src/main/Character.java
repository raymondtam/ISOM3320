package main;

public abstract class Character {
	int health;
	int movingSpeed;
	
	public void minusHealth(int damage) {
		health -= damage;
	}
	
	public abstract boolean isDead();
}
