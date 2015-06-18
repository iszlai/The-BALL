package com.ball.game;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class GameObject {

	private Vector2 position=new Vector2();
	private Rectangle bounds=new Rectangle();

	public GameObject() {
	}
	
	public void setProperties(float x, float y,int height ,int width) {
		position.set(x,y);
		bounds.setHeight(height);
		bounds.setWidth(width);
	}

	public GameObject(int height ,int width) {
		bounds.setHeight(height);
		bounds.setWidth(width);
	}

	public float getPostionX() {
		return position.x;
	}

	public float getPostionY() {
		return position.y;
	}
	
	public void setPosition(Vector2 position) {
		this.position = position;
	}

	public float getHeight() {
		return bounds.getHeight();
	}

	public float getWidth() {
		return bounds.getWidth();
	}


}
