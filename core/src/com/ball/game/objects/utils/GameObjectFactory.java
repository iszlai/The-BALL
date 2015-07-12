package com.ball.game.objects.utils;

import java.util.Random;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.ball.game.objects.Ball;
import com.ball.game.objects.Magic;
import com.ball.game.objects.Paddle;

public class GameObjectFactory {

	public static float BLOCK_SIZE;
	public static final Random RANDOM=new Random();

	public GameObjectFactory(int gameWidth, int gameHeight) {
		BLOCK_SIZE = (gameWidth + gameHeight) / 20;
	}

	public Paddle getRegurarPaddle(PaddleDirection paddleDirection) {
		switch (paddleDirection) {
		case DOWN: {
			Paddle paddle = getPaddle(9.5f * BLOCK_SIZE, 0.5f * BLOCK_SIZE);
			paddle.setPosition(new Vector2(1f * BLOCK_SIZE, 0.5f * BLOCK_SIZE));
			return paddle;
		}
		case UP: {
			Paddle paddle = getPaddle(9.5f * BLOCK_SIZE, 0.5f * BLOCK_SIZE);
			paddle.setPosition(new Vector2(1f * BLOCK_SIZE, 6f * BLOCK_SIZE));
			return paddle;
		}
		case LEFT: {
			Paddle paddle = getPaddle(0.5f * BLOCK_SIZE, 5f * BLOCK_SIZE);
			paddle.setPosition(new Vector2(0.5f * BLOCK_SIZE, 1f * BLOCK_SIZE));
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

	public static <T extends Enum<?>> T randomEnum(Class<T> clazz) {
		int x = RANDOM.nextInt(clazz.getEnumConstants().length);
		return clazz.getEnumConstants()[x];
	}
	
	public Magic getMagic(Rectangle border){
		
		Magic magic =new Magic(BLOCK_SIZE/2, BLOCK_SIZE/2,randomEnum(MagicType.class));
		float x=border.getX()+randomWithbounds(3, 6)*BLOCK_SIZE;
		float y=border.getY()+randomWithbounds(2, 3)*BLOCK_SIZE;		
		magic.setPosition(new Vector2(x, y));
		return magic;
	}

	public float randomWithbounds(int down, int up) {
		return down+RANDOM.nextInt(up);
	}

	public Ball getBall(Rectangle field) {
		Ball ball = new Ball(BLOCK_SIZE / 2, BLOCK_SIZE / 2);
		ball.move(field.x + (field.width - ball.getWidth()) / 2, field.y + (field.height - ball.getHeight()) / 2);
		return ball;
	}

	public Rectangle getBorder() {
		return new Rectangle(0.5f * BLOCK_SIZE - 5, 0.5f * BLOCK_SIZE - 5, 10.5f * BLOCK_SIZE + 10, 6f * BLOCK_SIZE + 5);
	}
}
