package main;

public abstract class Character extends GameObject{
	//variable
	private int health;
	
	//Constructor
	Character() {
	}
	
	Character(int health){
		super();
		this.health = health;
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
	
	abstract public boolean isHit();

	abstract public void move(int x, int y);
}
