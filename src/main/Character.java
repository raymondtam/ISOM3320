/** 
 * This is an abstract class of Character which is the superclass of Player and Target. 
 * It extends the GameObject class with additional attribute of health. 
 * There are two characteristic functions in the Character class.
 * 
 * First, the "minusHealth" function describes the situation when a character is being hit.
 * It reduces the health of the specific character with specified damage. 
 * 
 * Second, the "isDead" function checks whether the specific character's health has dropped to
 * zero. If it does, it means that the character is dead and this will trigger other functions
 * such as GameOver for player and being destroyed for target.
 */

public abstract class Character extends GameObject{
	private int health;
	
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
	
	//Custom method
	public void minusHealth(int damage) {
		if (this.getHealth() >= damage){
			this.setHealth(this.getHealth() - damage);
		}
		else {
			this.setHealth(0);
		}
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
