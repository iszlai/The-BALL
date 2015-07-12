package com.ball.game.objects;

import static com.ball.game.util.CollisionUtils.handleDownCollision;
import static com.ball.game.util.CollisionUtils.handleLeftCollision;
import static com.ball.game.util.CollisionUtils.handleRightCollision;
import static com.ball.game.util.CollisionUtils.handleUpCollision;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.ball.game.objects.utils.GameObject;
import com.ball.game.screens.BallGame;
import com.ball.game.util.GeometryUtil;

public class Ball extends GameObject {

	private static final Color BALL_COLOR = new Color(0.96f, 0.26f, 0.21f, 1f);

	public Ball(float width, float height) {
		super(width, height);

	}

	public void reflect(boolean x, boolean y) {
		Vector2 velocity = getVelocity();
		if (x)
			velocity.x *= -1;
		if (y)
			velocity.y *= -1;
		setVelocity(velocity);
	}

	public static void updateBall(BallGame ballGame, float dt) {
		ballGame.ball.integrate(dt);
		ballGame.ball.updateBounds();
		GeometryUtil.ballLimitVelocity(ballGame.ball);
		// Field collision
		if (ballGame.ball.left() < ballGame.fieldLeft) {
			endRound(ballGame);
		}
		if (ballGame.ball.right() > ballGame.fieldRight) {
			endRound(ballGame);
		}
		if (ballGame.ball.bottom() < ballGame.fieldBottom) {
			endRound(ballGame);
		}
		if (ballGame.ball.top() > ballGame.fieldTop) {
			endRound(ballGame);
		}

		// Paddle collision
		if (ballGame.ball.getBounds().overlaps(ballGame.paddleLeft.getBounds())) {
			ballGame.score += ballGame.getScore(ballGame.WINDOW_HEIGHT, ballGame.paddleLeft.getHeight());
			handleLeftCollision(ballGame.ball, ballGame.paddleLeft);
		} else if (ballGame.ball.getBounds().overlaps(ballGame.paddleRight.getBounds())) {
			ballGame.score += ballGame.getScore(ballGame.WINDOW_HEIGHT, ballGame.paddleRight.getHeight());
			handleRightCollision(ballGame.ball, ballGame.paddleRight);
		} else if (ballGame.ball.getBounds().overlaps(ballGame.paddleUp.getBounds())) {
			ballGame.score += ballGame.getScore(ballGame.WINDOW_WIDTH, ballGame.paddleUp.getWidth());
			handleUpCollision(ballGame.ball, ballGame.paddleUp);
		} else if (ballGame.ball.getBounds().overlaps(ballGame.paddleDown.getBounds())) {
			ballGame.score += ballGame.getScore(ballGame.WINDOW_WIDTH, ballGame.paddleDown.getWidth());
			handleDownCollision(ballGame.ball, ballGame.paddleDown);
		} else if (ballGame.magic.isActive() && ballGame.ball.getBounds().overlaps(ballGame.magic.getBounds())) {
			System.out.println("collision");
			ballGame.magic.setActive(false);
		}
	}

	public static void endRound(BallGame ballGame) {
		ballGame.gameCount++;
		ballGame.reset();
	}

	public static void drawBall(ShapeRenderer shapeRenderer, Ball ball, float dt) {
		shapeRenderer.setColor(BALL_COLOR);
		shapeRenderer.rect(ball.getX(), ball.getY(), ball.getWidth(), ball.getHeight());
	}
}
