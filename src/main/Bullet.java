package main;

public class Bullet { //extends GameObject 
  private final int bulletDamage;
  private final int magazineSize;
  
  Bullet () {
    this.bulletDamage = 0;
    this.magazineSize = 0;
  }
  
  Bullet (int bulletDamage, int magazineSize) {
    this.bulletDamage = bulletDamage;
    this.magazineSize = magazineSize;
  }
  
  public int getBulletDamage () {
    return bulletDamage;
  }
  public int getMagazineSize () {
    return magazineSize;
  }
  
   public static void main (String[] args){
    Bullet b1 = new Bullet();
    System.out.println("Bullet Damage is " + b1.getBulletDamage() + 
                       " and magazine size is " + b1.getMagazineSize());
    Bullet b2 = new Bullet (5, 100);
     System.out.println("Bullet Damage is " + b2.getBulletDamage() + 
                       " and magazine size is " + b2.getMagazineSize());
    
   }
}
    //Bullet b2 = new Bullet;
    
  
  //Bullet[][] bulletArray  = new Bullet[][]
  
  