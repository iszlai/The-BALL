package com.ball.game.objects;

import static com.ball.game.util.CollisionUtils.handleDownCollision;
import static com.ball.game.util.CollisionUtils.handleLeftCollision;
import static com.ball.game.util.CollisionUtils.handleRightCollision;
import static com.ball.game.util.CollisionUtils.handleUpCollision;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.ball.game.objects.utils.FSTM.States;
import com.ball.game.objects.utils.GameObject;
import com.ball.game.objects.utils.GameObjectFactory;
import com.ball.game.objects.utils.Registry;
import com.ball.game.screens.BallGame;
import com.ball.game.util.GeometryUtil;

public class Ball extends GameObject {

	private static final Color BALL_COLOR = new Color(0.96f, 0.26f, 0.21f, 1f);
	private static final Registry reg=Registry.INSTANCE;
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

	//TODO: leave only ball related stuff and ballgame go out , the same for paddles
	public static void updateBall(BallGame ballGame, float dt) {
//		System.out.println("bounces"+ballGame.nrOfBounces.get());
		ballGame.ball.integrate(dt);
		ballGame.ball.updateBounds();
		GeometryUtil.ballLimitVelocity(ballGame.ball);
		// Field collision

		//TODO use or-s
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
			ballGame.score += ballGame.getScore(reg.WINDOW_HEIGHT, ballGame.paddleLeft.getHeight());
			handleLeftCollision(ballGame.ball, ballGame.paddleLeft,ballGame);
			ballGame.nrOfBounces.incrementAndGet();
		} else if (ballGame.ball.getBounds().overlaps(ballGame.paddleRight.getBounds())) {
			ballGame.score += ballGame.getScore(reg.WINDOW_HEIGHT, ballGame.paddleRight.getHeight());
			handleRightCollision(ballGame.ball, ballGame.paddleRight,ballGame);
			ballGame.nrOfBounces.incrementAndGet();
		} else if (ballGame.ball.getBounds().overlaps(ballGame.paddleUp.getBounds())) {
			ballGame.score += ballGame.getScore(reg.WINDOW_WIDTH, ballGame.paddleUp.getWidth());
			handleUpCollision(ballGame.ball, ballGame.paddleUp,ballGame);
			ballGame.nrOfBounces.incrementAndGet();
		} else if (ballGame.ball.getBounds().overlaps(ballGame.paddleDown.getBounds())) {
			ballGame.score += ballGame.getScore(reg.WINDOW_WIDTH, ballGame.paddleDown.getWidth());
			handleDownCollision(ballGame.ball, ballGame.paddleDown,ballGame);
			ballGame.nrOfBounces.incrementAndGet();
		} else if (ballGame.magic.isInState(States.VISIBLE) && ballGame.ball.getBounds().overlaps(ballGame.magic.getBounds())) {
		//	System.out.println("collision");
			ballGame.magic.isActive.set(false);
			ballGame.magic.transition(States.SPELL_ACTIVE);
			ballGame.magic.doMagicOnBall(ballGame.ball);
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
	
	public void speedUP(){
		Vector2 velocity2 = this.getVelocity();
		this.setVelocity(velocity2.x*1.5f, velocity2.y*1.5f);
	}
	
	public void slowDown(){
		Vector2 velocity2 = this.getVelocity();
		this.setVelocity(velocity2.x*0.5f, velocity2.y*0.5f);
	}

	public void setRNDPosition() {
		float x=randomWithbounds(3, 6)*this.getWidth()*2;
		float y=randomWithbounds(2, 3)*this.getWidth()*2;
		this.setPosition(new Vector2(x,y));
	}

	private int randomWithbounds(int down, int up) {
		return down+GameObjectFactory.RANDOM.nextInt(up);
	}
	

}
