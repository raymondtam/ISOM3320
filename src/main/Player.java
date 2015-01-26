package main;

public class Player extends Character{
	int numberOfUnusedBullet;
	Bullet bullet= new Bullet();
	
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
	
	@Override
	public boolean isDead() {
		return false;
	}
	
	public void reload() {
		if (numberOfUnusedBullet < bullet.getMagazineSize()) {
			numberOfUnusedBullet = bullet.getMagazineSize();
		}
	}
	
	public void fire() {
		if (numberOfUnusedBullet > 0) {
			
		}
		//TODO
	}
	
	public static void main(String[] args){
	}
	
}
