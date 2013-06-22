package com.raffledoocious.blasterbattle;

public class Bullet {
	private Player player;
	private int x;
	private int y;
	private int velocity;
	
	public Bullet(int x, int y, Player player, int velocity){
		this.x = x;
		this.y = y;
		this.player = player;
		this.velocity = velocity;
	}
	
	public Player getPlayer(){
		return player;
	}
	
	public void setX(int x){
		this.x = x;
	}
	
	public int getX(){
		return x;
	}
	
	public void setY(int y){
		this.y = y;
	} 
	
	public int getY(){
		return y;
	}
	
	public int getVelocity(){
		return velocity;
	}
}