package main;

public class Bullet extends GameObject {
    //Attribute
	private final int bulletDamage;
    private final int magazineSize;
  
    //Constructors
    Bullet () {
    	this.bulletDamage = 0;
    	this.magazineSize = 0;
    }
  
    Bullet (int bulletDamage, int magazineSize) {
    	this.bulletDamage = bulletDamage;
    	this.magazineSize = magazineSize;
    }
    Bullet (int bulletDamage, int magazineSize, int radius) {
    	this.bulletDamage = bulletDamage;
    	this.magazineSize = magazineSize;
    	this.setRadius(radius);
    }
  
    public int getBulletDamage () {
    	return bulletDamage;
    }
    public int getMagazineSize () {
    	return magazineSize;
    }
  
    //Method
	@Override
	public boolean isHit() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean move(int x, int y) {
		this.setVisible(true);
		
		// TODO Auto-generated method stub
		return false;
	}
	
	public static Bullet[] getBulletArray(int damage, int size) {
		
		Bullet[] bulletArray = new Bullet [size];
		for (int i = 0; i < bulletArray.length; i++){
			bulletArray[i] = new Bullet (damage, size);
		}
		return bulletArray;
	}
	
	@Override
	public String toString(){
		return super.toString() + "bullet damage: " + bulletDamage + 
				"magazine size: " + magazineSize + "\n";
	}

	public static void main (String[] args){
		Bullet b1 = new Bullet(10,20);
		System.out.println("Bullet Damage is " + b1.getBulletDamage() + 
                   " and magazine size is " + b1.getMagazineSize());
		Bullet b2 = new Bullet (15, 15);
		System.out.println("Bullet Damage is " + b2.getBulletDamage() + 
                   " and magazine size is " + b2.getMagazineSize());
		Bullet b3 = new Bullet (20, 10);
		System.out.println("Bullet Damage is " + b3.getBulletDamage() + 
                   " and magazine size is " + b3.getMagazineSize());
		
		//Bullet [][] bulletArray = new Bullet[3][];
		//bulletArray[0].length = b1.getMagazineSize();
		//bulletArray[1].length = b2.getMagazineSize();
		//bulletArray[2].length = b3.getMagazineSize();
	}
}


