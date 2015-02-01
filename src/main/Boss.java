package main;

import javafx.geometry.Point2D;

public class Boss extends Target{
	
	public Boss(){
	}
	
	public void summonZombie(Target[] zombie, Point2D playerposition) {
		for (int i = 0; i < zombie.length; i++) zombie[i].setVisible(playerposition);
	}
	
}
