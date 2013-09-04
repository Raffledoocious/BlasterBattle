package com.raffledoocious.blasterbattle;

public class Bullet {
	private Player player;
	public int x;
	public int y;
	private int velocity;
	private boolean destroyed;
	
	private static final int player1Velocity = -8;
	private static final int player2Velocity = Math.abs(player1Velocity);
	
	public Bullet(int x, int y, Player player, int speedScale){
		this.x = x;
		this.y = y;
		this.player = player;
		this.destroyed = false;
		if (player == Player.One){
			this.velocity = player1Velocity * speedScale;
		}
		else {
			this.velocity = player2Velocity * speedScale;
		}
	}
	
	public void markBulletDestroyed(){
		this.destroyed = true;
	}
	
	public boolean isDestroyed(){
		return this.destroyed;
	}
	
	public Player getPlayer(){
		return player;
	}

	public void updateBulletLocation(){
		this.y += this.velocity;
	}
}