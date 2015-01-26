package main;

public class Player extends Character{
	int numberOfUnusedBullet;
	Bullet bullet= new Bullet();
	
	public int getNumberOfUnusedBullet() {
		return numberOfUnusedBullet;
	}
	
	private void setNumberOfUnusedBullet(int numberOfUnusedBullet) {
		this.numberOfUnusedBullet = numberOfUnusedBullet;
	}
	
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
			setNumberOfUnusedBullet (bullet.getMagazineSize());
		}
	}
	
	public void fire() {
		if (numberOfUnusedBullet > 0) {
			bullet.setVisible(true);
			// bullet.setPosition(x, y);
			// bullet.move(x, y);
		}
		else
			reload();
	}
	
	@Override
	public String toString(){
		return super.toString() + "numberOfUnusedBullet: " + numberOfUnusedBullet + "\n";
	}
}
