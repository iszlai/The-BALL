package com.ball.game.objects;

import static com.ball.game.util.CollisionUtils.paddleHorizontalCheck;
import static com.ball.game.util.CollisionUtils.paddleVerticalCheck;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.ball.game.objects.utils.GameObject;
import com.ball.game.screens.BallGame;

public class Paddle extends GameObject {
// Constructors
	private Rectangle touchBounds = new Rectangle();
	private float width;
	private float height;
	private float widthBuffer;
	private float heightBuffer;
	private static final Color VERTICAL_COLOR = new Color(0.34f, 0.53f, 0.41f, 1);
	private static final Color HORIZONTAL_COLOR = new Color(0.35f, 0.36f, 0.50f, 1f);
	
	
	public Paddle(float width, float height) {
		super(width,height);
		this.width=width;
		this.widthBuffer=width/3;
		this.height=height;
		this.heightBuffer=height/3;
	}

	public Rectangle getTouchBounds() {
		touchBounds.set(this.getX()-widthBuffer, this.getY()-heightBuffer, width+widthBuffer,height+heightBuffer);
		return touchBounds;
	}
	
	public static void updatePaddles(BallGame ballGame,float dt) {
		boolean moveUp = false;
		boolean moveDown = false;
		boolean moveLeft = false;
		boolean moveRight = false;
		Vector2 touch = null;
		if (Gdx.input.isTouched()) {
			touch = new Vector2(Gdx.input.getX(), ballGame.WINDOW_HEIGHT - Gdx.input.getY());
			if (ballGame.paddleDown.getTouchBounds().contains(touch)) {
				moveDown = true;
			} else if (ballGame.paddleUp.getTouchBounds().contains(touch)) {
				moveUp = true;
			} else if (ballGame.paddleLeft.getTouchBounds().contains(touch)) {
				moveLeft = true;
			} else if (ballGame.paddleRight.getTouchBounds().contains(touch)) {
				moveRight = true;
			}

			if (moveUp) {
				ballGame.paddleUp.move(touch.x - ballGame.paddleUp.getWidth() / 2, ballGame.paddleUp.getY());
				ballGame.paddleUp.updateBounds();
			} else if (moveDown) {
				ballGame.paddleDown.move(touch.x - ballGame.paddleDown.getWidth() / 2, ballGame.paddleDown.getY());
				ballGame.paddleDown.updateBounds();
			} else if (moveLeft) {
				ballGame.paddleLeft.move(ballGame.paddleLeft.getX(), touch.y - ballGame.paddleLeft.getHeight() / 2);
				ballGame.paddleLeft.updateBounds();
			} else if (moveRight) {
				ballGame.paddleRight.move(ballGame.paddleRight.getX(), touch.y - ballGame.paddleRight.getHeight() / 2);
				ballGame.paddleRight.updateBounds();
			}

			ballGame.paddleLeft.integrate(dt);
			ballGame.paddleLeft.updateBounds();
			ballGame.paddleRight.integrate(dt);
			ballGame.paddleRight.updateBounds();
			ballGame.paddleUp.integrate(dt);
			ballGame.paddleUp.updateBounds();
			ballGame.paddleDown.integrate(dt);
			ballGame.paddleDown.updateBounds();

			paddleVerticalCheck(ballGame.paddleLeft, ballGame.paddleUp.bottom(), ballGame.paddleDown.top());
			paddleVerticalCheck(ballGame.paddleRight, ballGame.paddleUp.bottom(), ballGame.paddleDown.top());
			paddleHorizontalCheck(ballGame.paddleUp, ballGame.paddleLeft.right(), ballGame.paddleRight.left());
			paddleHorizontalCheck(ballGame.paddleDown, ballGame.paddleLeft.right(), ballGame.paddleRight.left());
		}
	}
	
	public static void drawPaddles(BallGame ballGame) {
		ballGame.shapeRenderer.setColor(HORIZONTAL_COLOR);
		renderPaddle(ballGame.shapeRenderer, ballGame.paddleUp);
		renderPaddle(ballGame.shapeRenderer, ballGame.paddleDown);
		ballGame.shapeRenderer.setColor(VERTICAL_COLOR);
		renderPaddle(ballGame.shapeRenderer, ballGame.paddleLeft);
		renderPaddle(ballGame.shapeRenderer, ballGame.paddleRight);
	}

	private static void renderPaddle(ShapeRenderer renderer, Paddle paddle) {
		renderer.rect(paddle.getX(), paddle.getY(), paddle.getWidth(), paddle.getHeight());
	}

	
}