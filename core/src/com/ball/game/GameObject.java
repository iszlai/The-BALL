package com.ball.game;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;


public abstract class GameObject {
	// Fields
		private Rectangle bounds = new Rectangle();
		private Vector2 position = new Vector2();
		private Vector2 velocity = new Vector2();

	// Constructors
		public GameObject(float width, float height) {
			bounds.setWidth(width);
			bounds.setHeight(height);
		}

	// Getters/Setters
		public Rectangle getBounds() {
			updateBounds();
			return bounds;
		}
		
		public void setBounds(Rectangle bounds) {
			this.bounds = bounds;
		}
		
		public void updateBounds() {
			bounds.set(position.x, position.y, bounds.width, bounds.height);
		}

		public Vector2 getPosition() {
			return position;
		}

		public void setPosition(Vector2 position) {
			this.position = position;
		}

		public Vector2 getVelocity() {
			return velocity;
		}

		public void setVelocity(Vector2 velocity) {
			this.velocity = velocity;
		}

	// Methods
		public float bottom() {
			return bounds.y;
		}
		
		public float getHeight() {
			return bounds.height;
		}
		
		public float getVelocityX() {
			return velocity.x;
		}
		
		public float getVelocityY() {
			return velocity.y;
		}
		
		public float getWidth() {
			return bounds.width;
		}

		public float getX() {
			return position.x;
		}
		
		public float getY() {
			return position.y;
		}
		
		public void integrate(float dt) {
			position.add(velocity.x * dt, velocity.y * dt);	
		}

		public float left() {
			return bounds.x;
		}
		
		public void move(float x, float y) {
			position.set(x, y);
		}
		
		public float right() {
			return bounds.x + bounds.width;
		}

		public void setVelocity(float x, float y) {
			velocity.set(x, y);
		}
		
		public float top() {
			return bounds.y + bounds.height;
		}
		
		public void translate(float x, float y) {
			position.add(x, y);
		}
	}