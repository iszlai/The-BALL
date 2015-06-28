package com.ball.game.objects;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class GameObjectFactory {

	public final float BLOCK_SIZE;

	public GameObjectFactory(int gameWidth, int gameHeight) {
		BLOCK_SIZE = (gameWidth + gameHeight)/20;
	}

	public Paddle getRegurarPaddle(PaddleDirection paddleDirection) {
		switch (paddleDirection) {
		case DOWN: {
			Paddle paddle = getPaddle(9.5f * BLOCK_SIZE,0.5f* BLOCK_SIZE);
			paddle.setPosition(new Vector2(1f * BLOCK_SIZE, 0.5f * BLOCK_SIZE));
			return paddle;
		}
		case UP: {
			Paddle paddle = getPaddle(9.5f * BLOCK_SIZE,0.5f* BLOCK_SIZE);
			paddle.setPosition(new Vector2(1f * BLOCK_SIZE, 6f * BLOCK_SIZE));
			return paddle;
		}
		case LEFT: {
			Paddle paddle = getPaddle(0.5f * BLOCK_SIZE, 5f * BLOCK_SIZE);
			paddle.setPosition(new Vector2(0.5f*BLOCK_SIZE, 1f * BLOCK_SIZE));
			return paddle;
		}
		case RIGHT: {
			Paddle paddle = getPaddle(0.5f * BLOCK_SIZE, 5f * BLOCK_SIZE);
			paddle.setPosition(new Vector2(10.5f * BLOCK_SIZE, 1f * BLOCK_SIZE));
			return paddle;
		}
		}
		throw new IllegalArgumentException("Unexpected PaddleDirection value");
	}

	private Paddle getPaddle(float width, float height) {
		return new Paddle(width, height);
	}
	
	public Ball getBall(Rectangle field){
		Ball ball= new Ball(BLOCK_SIZE/3, BLOCK_SIZE/3);
		ball.move(field.x + (field.width - ball.getWidth()) / 2, field.y + (field.height - ball.getHeight()) / 2);
		return ball;
	}
	
	public Rectangle getBorder (){
		return new Rectangle(0.5f*BLOCK_SIZE-5, 0.5f*BLOCK_SIZE-5, 10.5f*BLOCK_SIZE+10, 6f*BLOCK_SIZE+5);
	}
}
