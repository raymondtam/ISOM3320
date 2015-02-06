/**
 * This GameObject class defines the game objects to be used in the game. It is an 
 * abstract class and the superclass to character and bullet class. It defines the 
 * common attributes of all the game objects including position, radius, whether the
 * object is visible or not and the moving speed.
 * It defines two abstract method -- "isHit" and "move" for the game objects to inherit. 
 */
package main;

import javafx.geometry.Point2D;

abstract class GameObject {
	private Point2D position;
	private double radius;
	private boolean	visible;
	private int movingSpeed;
	
	GameObject(){
		this(0, 0, 0, false, 0);
	}
	
	GameObject(double xcoord, double ycoord, double radius, boolean visible, int movingSpeed){
		position = new Point2D(xcoord, ycoord);
		this.radius = radius;
		this.visible = false;
		this.movingSpeed = movingSpeed;
	}
	
	public double getXcoord(){
		return position.getX();
	}
	
	private void setXcoord(double xcoord){
		position = new Point2D(xcoord, position.getY());
	}
	
	public double getYcoord(){
		return position.getY();
	}
	
	private void setYcoord(double ycoord){
		position = new Point2D(position.getX(), ycoord);
	}
	
	public Point2D getPosition(){
		return position;
	}
	
	public void setPosition(double xcoord, double ycoord){
		position = new Point2D(xcoord, ycoord);
	}
	
	public void changePosition(double xIncrement, double yIncrement){
		Point2D vector = new Point2D(xIncrement, yIncrement);
		position = position.add(vector);
	}
	
	public double getRadius(){
		return radius;
	}
	
	public void setRadius(double radius){
		this.radius = radius;
	}
	
	public boolean isVisible(){
		return visible;
	}
	
	public void setVisible(boolean visible){
		this.visible = visible;
	}
	
	public int getMovingSpeed(){
		return movingSpeed;
	}
	
	public void setMovingSpeed(int movingSpeed){
		this.movingSpeed = movingSpeed;
	} 
	
	@Override
	public String toString(){
		return "The Gameobject is at" + position.toString() + "with radius: " + radius 
				+ "and is " + ((visible)?"Visible":"Invisible") + " with movingspeed " + movingSpeed+"\n" ;
	}
		
	abstract public boolean isHit();
	
	abstract public void move(double xIncrement, double yIncrement);
}
