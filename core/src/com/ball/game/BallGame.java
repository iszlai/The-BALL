package com.ball.game;

import static com.ball.game.CollisionUtils.handleDownCollision;
import static com.ball.game.CollisionUtils.handleLeftCollision;
import static com.ball.game.CollisionUtils.handleRightCollision;
import static com.ball.game.CollisionUtils.handleUpCollision;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class BallGame extends ApplicationAdapter {

	private static final float BALL_VELOCITY = 250f;
	Paddle paddleUp;
	Paddle paddleDown;
	Paddle paddleLeft;
	Paddle paddleRight;
	Ball ball;
	int WINDOW_WIDTH;
	int WINDOW_HEIGHT;
	private Rectangle field = new Rectangle();
	private ShapeRenderer shapeRenderer;
	private float fieldBottom, fieldLeft, fieldRight, fieldTop;

	@Override
	public void create() {
		shapeRenderer = new ShapeRenderer();
		WINDOW_WIDTH = Gdx.graphics.getWidth();
		WINDOW_HEIGHT = Gdx.graphics.getHeight();
		setUpScreenBounds();
		setUpGameObjects();
		reset();
	}

	private void setUpGameObjects() {
		GameObjectFactory goFactory = new GameObjectFactory(WINDOW_WIDTH, WINDOW_HEIGHT);
		ball = goFactory.getBall();
		paddleUp = goFactory.getRegurarPaddle(PaddleDirection.UP);
		paddleDown = goFactory.getRegurarPaddle(PaddleDirection.DOWN);
		paddleLeft = goFactory.getRegurarPaddle(PaddleDirection.LEFT);
		paddleRight = goFactory.getRegurarPaddle(PaddleDirection.RIGHT);
	}

	private void setUpScreenBounds() {
		field.set(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
		fieldLeft = field.x;
		fieldRight = field.x + field.width;
		fieldBottom = field.y;
		fieldTop = field.y + field.height;
	}

	@Override
	public void render() {
		float dt = Gdx.graphics.getRawDeltaTime();
		update(dt);
		draw(dt);
	}

	private void update(float dt) {
		updateBall(dt);
		updatePaddles(dt);
	}

	private void updatePaddles(float dt) {
		float sign_X = 0;
		float sign_Y = 0;
		boolean moveUp = false;
		boolean moveDown = false;
		boolean moveLeft = false;
		boolean moveRight = false;
		Vector2 touch = null ;
		if (Gdx.input.isTouched()) {
			 touch = new Vector2(Gdx.input.getX(), WINDOW_HEIGHT - Gdx.input.getY());
			// System.out.println(touch);
			if (paddleDown.getBounds().contains(touch)) {
				sign_X = Math.signum(Gdx.input.getDeltaX());
				moveDown = true;
			} else if (paddleUp.getBounds().contains(touch)) {
				sign_X = Math.signum(Gdx.input.getDeltaX());
				moveUp = true;
			} else if (paddleLeft.getBounds().contains(touch)) {
				sign_Y = Math.signum(Gdx.input.getDeltaY());
				moveLeft = true;
			} else if (paddleRight.getBounds().contains(touch)) {
				sign_Y = Math.signum(Gdx.input.getDeltaY());
				moveRight = true;
			}

		}
		if (moveUp) {
			paddleUp.move(touch.x, paddleUp.getY());
		} else if (moveDown) {
			paddleDown.setVelocity(sign_X * 400f, 0f);
		} else if (moveLeft) {
			paddleLeft.setVelocity(0, 400f*-sign_Y*100/paddleLeft.getHeight());
		} else if (moveRight) {
			paddleRight.setVelocity(0, 400f*-sign_Y);
		} else {
			paddleDown.setVelocity(0f, 0f);
			paddleUp.setVelocity(0f, 0f);
			paddleLeft.setVelocity(0f, 0f);
			paddleRight.setVelocity(0f, 0f);
		}
		paddleDown.integrate(dt);
		paddleUp.integrate(dt);
		paddleLeft.integrate(dt);
		paddleRight.integrate(dt);
		
		paddleDown.updateBounds();
		paddleUp.updateBounds();
		paddleLeft.updateBounds();
		paddleRight.updateBounds();
	
		}

	private void draw(float dt) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		shapeRenderer.begin(ShapeType.Filled);
		drawPaddles();
		drawBall(dt);
		shapeRenderer.end();
	}

	private void drawBall(float dt) {
		shapeRenderer.rect(ball.getX(), ball.getY(), ball.getWidth(), ball.getHeight());
	}

	private void drawPaddles() {
		renderPaddle(shapeRenderer, paddleUp);
		renderPaddle(shapeRenderer, paddleDown);
		renderPaddle(shapeRenderer, paddleLeft);
		renderPaddle(shapeRenderer, paddleRight);
	}

	private void renderPaddle(ShapeRenderer renderer, Paddle paddle) {
		renderer.rect(paddle.getX(), paddle.getY(), paddle.getWidth(), paddle.getHeight());
	}

	public void reset() {
		// Reset ball
		ball.move(field.x + (field.width - ball.getWidth()) / 2, field.y + (field.height - ball.getHeight()) / 2);
		Vector2 velocity = ball.getVelocity();
		velocity.set(BALL_VELOCITY, 0f);
		velocity.setAngle(-135f);
		ball.setVelocity(velocity);

	}

	void updateBall(float dt) {
		ball.integrate(dt);
		ball.updateBounds();
		GeometryUtil.ballLimitVelocity(ball);
		// Field collision
		if (ball.left() < fieldLeft) {
			ball.move(fieldLeft, ball.getY());
			ball.reflect(true, false);
		}
		if (ball.right() > fieldRight) {
			ball.move(fieldRight - ball.getWidth(), ball.getY());
			ball.reflect(true, false);
		}
		if (ball.bottom() < fieldBottom) {
			ball.move(ball.getX(), fieldBottom);
			ball.reflect(false, true);
		}
		if (ball.top() > fieldTop) {
			ball.move(ball.getX(), fieldTop - ball.getHeight());
			ball.reflect(false, true);
		}

		// Paddle collision
		if (ball.getBounds().overlaps(paddleLeft.getBounds())) {
			handleLeftCollision(ball, paddleLeft);
		} else if (ball.getBounds().overlaps(paddleRight.getBounds())) {
			handleRightCollision(ball, paddleRight);
		} else if (ball.getBounds().overlaps(paddleUp.getBounds())) {
			handleUpCollision(ball, paddleUp);
		} else if (ball.getBounds().overlaps(paddleDown.getBounds())) {
			handleDownCollision(ball, paddleDown);
		}
	}

}
