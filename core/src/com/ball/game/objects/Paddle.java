package com.ball.game.objects;

import com.badlogic.gdx.math.Rectangle;

public class Paddle extends GameObject {
// Constructors
	private Rectangle touchBounds = new Rectangle();
	private float width;
	private float height;
	private float widthBuffer;
	private float heightBuffer;
	
	
	public Paddle(float width, float height) {
		super(width,height);
		this.width=width;
		this.widthBuffer=width/3;
		this.height=height;
		this.heightBuffer=height/3;
	}

	public Rectangle getTouchBounds() {
		touchBounds.set(this.getX()-widthBuffer, this.getY()-heightBuffer, width+widthBuffer,height+heightBuffer);
		return touchBounds;
	}

	
}