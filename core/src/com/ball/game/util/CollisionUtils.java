package com.ball.game.util;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.ball.game.objects.Ball;
import com.ball.game.objects.Paddle;
import com.ball.game.objects.utils.GameObjectFactory;
import com.ball.game.objects.utils.PaddleDirection;
import com.ball.game.screens.BallGame;

public class CollisionUtils {
	private static final float BALL_VELOCITY_MODIFIER = 1.01f;

	public static void handleRightCollision(Ball ball, Paddle paddleRight,BallGame ballGame) {
		if (ball.right() > paddleRight.left() && ball.left() < paddleRight.left()) {
			ball.move(paddleRight.left() - ball.getWidth(), ball.getY());
			ball.reflect(true, false);

			Vector2 velocity = ball.getVelocity();
			velocity.setAngle(180f - GeometryUtil.getReflectionAngle(ball, paddleRight));
			velocity.scl(BALL_VELOCITY_MODIFIER);
			ball.setVelocity(velocity);

			if (shrinkVerticlePaddle(ball.getHeight() * 2, paddleRight)){
				ballGame.paddleRight=ballGame.goFactory.getRegurarPaddle(PaddleDirection.RIGHT);
			}

		}
	}

	public static void handleLeftCollision(Ball ball, Paddle paddleLeft,BallGame ballGame) {
		if (ball.left() < paddleLeft.right() && ball.right() > paddleLeft.right()) {
			ball.move(paddleLeft.right(), ball.getY());
			ball.reflect(true, false);

			Vector2 velocity = ball.getVelocity();
			velocity.setAngle(GeometryUtil.getReflectionAngle(ball, paddleLeft));
			velocity.scl(BALL_VELOCITY_MODIFIER);
			ball.setVelocity(velocity);
			if(shrinkVerticlePaddle(ball.getHeight() * 2, paddleLeft)){
				ballGame.paddleLeft=ballGame.goFactory.getRegurarPaddle(PaddleDirection.LEFT);
			}
		}
	}

	public static void handleUpCollision(Ball ball, Paddle paddleUp,BallGame ballGame) {
		if (ball.top() > paddleUp.bottom() && ball.bottom() < paddleUp.bottom()) {
			ball.move(ball.getX(), paddleUp.bottom() - ball.getHeight());
			ball.reflect(false, true);

			Vector2 velocity = ball.getVelocity();
			velocity.setAngle(GeometryUtil.getReflectionAngle(ball, paddleUp));
			velocity.scl(BALL_VELOCITY_MODIFIER);
			ball.setVelocity(velocity);

			if(shrinkHorizontalPaddle(ball.getWidth() * 2, paddleUp)){
				ballGame.paddleUp=ballGame.goFactory.getRegurarPaddle(PaddleDirection.UP);
			}
		}
	}

	public static void handleDownCollision(Ball ball, Paddle paddleDown,BallGame ballGame) {
		if (ball.getBounds().overlaps(paddleDown.getBounds())) {
			ball.move(ball.getX(), paddleDown.top());
			ball.reflect(false, true);

			Vector2 velocity = ball.getVelocity();
			velocity.setAngle(GeometryUtil.getReflectionAngle(ball, paddleDown));
			velocity.scl(BALL_VELOCITY_MODIFIER);
			ball.setVelocity(velocity);
			if(shrinkHorizontalPaddle(ball.getWidth() * 2, paddleDown)){
				ballGame.paddleDown=ballGame.goFactory.getRegurarPaddle(PaddleDirection.DOWN);
			}
		}
	}

	private static boolean shrinkVerticlePaddle(float shrinkBase, Paddle paddle) {
		Rectangle bounds = paddle.getBounds();
		float newHeight = bounds.getHeight() - shrinkBase;
		if(newHeight<GameObjectFactory.BLOCK_SIZE){
			return true;
		}
		bounds.setHeight(newHeight);
		paddle.setBounds(bounds);
		return false;
	}

	private static boolean shrinkHorizontalPaddle(float shrinkBase, Paddle paddle) {
		Rectangle bounds = paddle.getBounds();
		float newWidth = bounds.getWidth() - shrinkBase;
		if(newWidth<GameObjectFactory.BLOCK_SIZE){
			return true;
		}
		bounds.setWidth(newWidth);
		paddle.setBounds(bounds);
		return false;
	}

	public static void paddleVerticalCheck(Paddle paddle1, float fieldTop, float fieldBottom) {
		if (paddle1.top() > fieldTop) {
			paddle1.move(paddle1.getX(), fieldTop - paddle1.getHeight());
			paddle1.setVelocity(0f, 0f);
		}
		if (paddle1.bottom() < fieldBottom) {
			paddle1.move(paddle1.getX(), fieldBottom);
			paddle1.setVelocity(0f, 0f);
		}
	}

	public static void paddleHorizontalCheck(Paddle paddle1, float fieldLeft, float fieldRight) {
		if (paddle1.left() < fieldLeft) {
			paddle1.move(fieldLeft, paddle1.getY());
			paddle1.setVelocity(0f, 0f);
		}
		if (paddle1.right() > fieldRight) {
			paddle1.move(fieldRight - paddle1.getWidth(), paddle1.getY());
			paddle1.setVelocity(0f, 0f);
		}
	}



}
