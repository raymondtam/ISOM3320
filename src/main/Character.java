package main;

public abstract class Character extends GameObject{
	//Variable
	private int health;
	
	//Constructor
	Character() {
	}
	
	Character(int health){
		super();
		this.health = health;
	}
	
	//Mutator and accessor
	public int getHealth() {
		return health;
	}
	
	public void setHealth(int health) {
		this.health = health;
	}
	
	//Custom method
	public void minusHealth(int damage) {
		health -= damage;
	}
	
	public boolean isDead() {
		return (health <= 0)? true: false;
	}
	
	@Override
	public String toString(){
		return super.toString() + "health: " + health + "\n";
	}
	
	abstract public boolean isHit();

	abstract public void move(double xIncrement, double yIncrement);
}
