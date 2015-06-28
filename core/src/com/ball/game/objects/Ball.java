package com.ball.game.objects;

import com.badlogic.gdx.math.Vector2;

public class Ball extends GameObject{

	public Ball(float width, float height) {
		super(width, height);
		
	}

	public void reflect(boolean x, boolean y) {
		Vector2 velocity = getVelocity();
		if(x) velocity.x *= -1;
		if(y) velocity.y *= -1;
		setVelocity(velocity);
	}
}
