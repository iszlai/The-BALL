package com.ball.game;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class CollisionUtils {
	private static final float BALL_VELOCITY_MODIFIER = 1.05f;

	public static void handleRightCollision(Ball ball, Paddle paddleRight) {
		if (ball.right() > paddleRight.left() && ball.left() < paddleRight.left()) {
			ball.move(paddleRight.left() - ball.getWidth(), ball.getY());
			ball.reflect(true, false);

			Vector2 velocity = ball.getVelocity();
			velocity.setAngle(180f - GeometryUtil.getReflectionAngle(ball, paddleRight));
			velocity.scl(BALL_VELOCITY_MODIFIER);
			ball.setVelocity(velocity);

			shrinkVerticlePaddle(paddleRight);

		}
	}

	public static void handleLeftCollision(Ball ball, Paddle paddleLeft) {
		if (ball.left() < paddleLeft.right() && ball.right() > paddleLeft.right()) {
			ball.move(paddleLeft.right(), ball.getY());
			ball.reflect(true, false);

			Vector2 velocity = ball.getVelocity();
			velocity.setAngle(GeometryUtil.getReflectionAngle(ball, paddleLeft));
			velocity.scl(BALL_VELOCITY_MODIFIER);
			ball.setVelocity(velocity);
			shrinkVerticlePaddle(paddleLeft);
		}
	}

	public static void handleUpCollision(Ball ball, Paddle paddleUp) {
		if (ball.top() > paddleUp.bottom() && ball.bottom() < paddleUp.bottom()) {
			ball.move(ball.getX(), paddleUp.bottom() - ball.getHeight());
			ball.reflect(false, true);

			Vector2 velocity = ball.getVelocity();
			velocity.setAngle(GeometryUtil.getReflectionAngle(ball, paddleUp));
			velocity.scl(BALL_VELOCITY_MODIFIER);
			ball.setVelocity(velocity);

			shrinkHorizontalPaddle(paddleUp);
		}
	}

	public static void handleDownCollision(Ball ball, Paddle paddleDown) {
		if (ball.bottom() < paddleDown.top() && ball.top() > paddleDown.top()) {
			ball.move(ball.getX(), paddleDown.top());
			ball.reflect(false, true);

			Vector2 velocity = ball.getVelocity();
			velocity.setAngle(GeometryUtil.getReflectionAngle(ball, paddleDown));
			velocity.scl(BALL_VELOCITY_MODIFIER);
			ball.setVelocity(velocity);
			shrinkHorizontalPaddle(paddleDown);
		}
	}

	private static void shrinkVerticlePaddle(Paddle paddle) {
		Rectangle bounds = paddle.getBounds();
		float newHeight = bounds.getHeight() * 0.80f;
		bounds.setHeight(newHeight);
		paddle.setBounds(bounds);
	}

	private static void shrinkHorizontalPaddle(Paddle paddle) {
		Rectangle bounds = paddle.getBounds();
		float newWidth = bounds.getWidth() * 0.80f;
		bounds.setWidth(newWidth);
		paddle.setBounds(bounds);
	}
}
