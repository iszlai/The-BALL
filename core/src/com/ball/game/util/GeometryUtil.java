package com.ball.game.util;

import com.badlogic.gdx.math.Vector2;
import com.ball.game.objects.Ball;
import com.ball.game.objects.Paddle;

public class GeometryUtil {

	private static final float BALL_REFLECT_ANGLE = 70f;
	private static final float VELOCITY_SLOWDOWN = 0.90f;
	private static final float TERMINAL_VELOCITY = 550f;

	public static float getReflectionAngle(Ball ball, Paddle paddle) {
		float ballCenterY = ball.getY() + (ball.getHeight() / 2f);
		float paddleCenterY = paddle.getY() + (paddle.getHeight() / 2f);
		float difference = ballCenterY - paddleCenterY;
		float position = difference / paddle.getHeight();
		return BALL_REFLECT_ANGLE * position;
	}

	public static void ballLimitVelocity(Ball ball) {
		if (ball.getVelocityX() > TERMINAL_VELOCITY || ball.getVelocityY() > TERMINAL_VELOCITY) {
			Vector2 velocity = ball.getVelocity();
			velocity.scl(VELOCITY_SLOWDOWN);
			ball.setVelocity(velocity);
		}

	}
}
