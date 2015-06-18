package com.ball.game;

public class Paddle extends GameObject {

	public Paddle(PaddleDirection direction) {
		super();
		switch (direction) {
		case DOWN:
			setProperties(0, 0, 32, 400);
			break;
		case UP:
			setProperties(0, 448, 32, 400);
			break;
		case LEFT:
			setProperties(0, 0, 400, 32);
			break;
		case RIGHT:
			setProperties(600, 0, 400, 32);
			break;
		}
	}

}
