package main;

public class Boss extends Target{
	public void summonZombie(Target[] zombie) {
		for (int i = 0; i < zombie.length; i++) zombie[i].setVisible(true);
	}
}
