// tune radius, movingSpeed, health
import java.lang.Math.*;


public class Target extends Character{
	private static final int NUMBER_OF_ZOMBIES = 10;
	
	public Target(){
		this(3, 3, 15);
	}
	
	public Target(int health, int movingSpeed, int radius){
		this.setHealth(health);
		this.setMovingSpeed(movingSpeed);
		this.setRadius(radius);
	}
	
	public boolean isHit() {
		return true;
	}
	
	public boolean isHit(Bullet getShot){
		double distance = 0.0d;
		if (distance = (Math.pow(Math.pow(getShot.getXcoord() - this.getXcoord(),2) + Math.pow(getShot.getYcoord() - this.getYcoord(),2), 0.5) >= 0){
			return true;
		}
		return false;
	}
	
	public chase()

}


	public void move(int x, int y) {
		// TODO Auto-generated method stub
		
	}

	public void move(double xIncrement, double yIncrement) {
		// TODO Auto-generated method stub
		
	}
