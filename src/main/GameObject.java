package main;

import javafx.geometry.Point2D;

abstract class GameObject {
	//variable
	private int xcoord, ycoord;
	private Point2D position;
	private double radius;
	private boolean	visible;
	private int movingSpeed;
	
	//Constructor
	GameObject(){
		this(0, 0, 0, false, 0);
	}
	
	GameObject(int xcoord, int ycoord, double radius, boolean visible, int movingSpeed){
		this.xcoord = xcoord;
		this.ycoord = ycoord;
		position = new Point2D(xcoord, ycoord);
		this.radius = radius;
		this.visible = false;
		this.movingSpeed = movingSpeed;
	}
	
	//mutator and accessor
	
	public int getXcoord(){
		return xcoord;
	}
	
	private void setXcoord(int xcoord){
		this.xcoord = xcoord;
		position = new Point2D(xcoord, ycoord);
	}
	
	public int getYcoord(){
		return ycoord;
	}
	
	private void setYcoord(int ycoord){
		this.ycoord = ycoord;
		position = new Point2D(xcoord, ycoord);
	}
	
	public Point2D getPosition(){
		return position;
	}
	
	public void setPostion(int xcoord, int ycoord){
		setXcoord(xcoord);
		setYcoord(ycoord);
		position = new Point2D(xcoord, ycoord);
		//TO-DO: set graph property
	}
	
	
	public double getRadius(){
		return radius;
	}
	
	public void setRadius(int radius){
		this.radius = radius;
	}
	
	public boolean isVisible(){
		return visible;
	}
	
	public void setVisible(boolean visible){
		this.visible = visible;
		//TO-DO: set graph property
	}
	
	public int getMovingSpeed(){
		return movingSpeed;
	}
	
	public void setMovingSpeed(int movingSpeed){
		this.movingSpeed = movingSpeed;
	} 
	
	//Custom method
	

	
	
	@Override
	public String toString(){
		return "The Gameobject is at x: " + xcoord + "y: " + ycoord + "with radius: " + radius 
				+ "and is " + ((visible)?"Visible":"Invisible") + " with movingspeed " + movingSpeed+"\n" ;
	}
		
	//to-do
	
	abstract public boolean isHit();
	
	abstract public boolean move(int x, int y);
	
}
