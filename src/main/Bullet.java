package main;

public class Bullet extends GameObject {
    //Attribute
	private static int bulletDamage;
    private static int magazineSize;
  
    //Constructors
    Bullet () {
    	Bullet.bulletDamage = 0;
    	Bullet.magazineSize = 0;
    }
  
    Bullet (int bulletDamage, int magazineSize, double radius) {
    	Bullet.bulletDamage = bulletDamage;
    	Bullet.magazineSize = magazineSize;
    	this.setRadius(radius);
    }
  
    public static int getBulletDamage () {
    	return bulletDamage;
    }
    public static int getMagazineSize () {
    	return magazineSize;
    }
  
    //Method
	@Override
	public boolean isHit() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public void move(double x, double y) {
		this.setVisible(true);
		this.getPosition()
		
		// TODO Auto-generated method stub
	}
	
	public static Bullet[] getBulletArray(int size, int damage, int magazineSize, double radius) {
		
		Bullet[] bulletArray = new Bullet [size];
		for (int i = 0; i < bulletArray.length; i++){
			bulletArray[i] = new Bullet (damage, magazineSize, radius);
		}
		return bulletArray;
	}
	
	@Override
	public String toString(){
		return super.toString() + "bullet damage: " + bulletDamage + 
				"magazine size: " + magazineSize + "\n";
	}

	public static void main (String[] args){
		
//		Bullet b1 = new Bullet(10,20);
//		System.out.println("Bullet Damage is " + b1.getBulletDamage() + 
//                   " and magazine size is " + b1.getMagazineSize());
//		Bullet b2 = new Bullet (15, 15);
//		System.out.println("Bullet Damage is " + b2.getBulletDamage() + 
//                   " and magazine size is " + b2.getMagazineSize());
//		Bullet b3 = new Bullet (20, 10);
//		System.out.println("Bullet Damage is " + b3.getBulletDamage() + 
//                   " and magazine size is " + b3.getMagazineSize());
		
		//Bullet [][] bulletArray = new Bullet[3][];
		//bulletArray[0].length = b1.getMagazineSize();
		//bulletArray[1].length = b2.getMagazineSize();
		//bulletArray[2].length = b3.getMagazineSize();
	}
}


