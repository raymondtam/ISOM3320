package main;

public class Character extends GameObject{
	int health;
	
	@Override
	public boolean isHit() {
		// TODO
		return false;
	}

	@Override
	public boolean move(int x, int y) {
		// TODO
		return false;
	}
	
	public int getHealth() {
		return health;
	}
	
	public void setHealth(int health) {
		this.health = health;
	}
	
	public void minusHealth(int damage) {
		health -= damage;
	}
	
	public boolean isDead() {
		return (health <= 0)? false: true;
	}
	
	@Override
	public String toString(){
		return super.toString() + "health: " + health + "\n";
	}
}
