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
		reset();
	}

	private void setUpGameObjects() {
		GameObjectFactory goFactory = new GameObjectFactory(WINDOW_WIDTH, WINDOW_HEIGHT);
		ball = goFactory.getBall(field);
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
		boolean moveUp = false;
		boolean moveDown = false;
		boolean moveLeft = false;
		boolean moveRight = false;
		Vector2 touch = null;
		if (Gdx.input.isTouched()) {
			touch = new Vector2(Gdx.input.getX(), WINDOW_HEIGHT - Gdx.input.getY());
			if (paddleDown.getTouchBounds().contains(touch)) {
				moveDown = true;
			} else if (paddleUp.getTouchBounds().contains(touch)) {
				moveUp = true;
			} else if (paddleLeft.getTouchBounds().contains(touch)) {
				moveLeft = true;
			} else if (paddleRight.getTouchBounds().contains(touch)) {
				moveRight = true;
			}

			if (moveUp) {
				paddleUp.move(touch.x - paddleUp.getWidth() / 2, paddleUp.getY());
				paddleUp.updateBounds();
			} else if (moveDown) {
				paddleDown.move(touch.x - paddleDown.getWidth() / 2, paddleDown.getY());
				paddleDown.updateBounds();
			} else if (moveLeft) {
				paddleLeft.move(paddleLeft.getX(), touch.y - paddleLeft.getHeight() / 2);
				paddleLeft.updateBounds();
			} else if (moveRight) {
				paddleRight.move(paddleRight.getX(), touch.y - paddleRight.getHeight() / 2);
				paddleRight.updateBounds();
			}
//			paddleDown.integrate(dt);
//			paddleUp.integrate(dt);
//			paddleLeft.integrate(dt);
//			paddleRight.integrate(dt);

		
		
		
			
		}
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
		setUpGameObjects();
		
		// Reset ball
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
//			ball.move(fieldLeft, ball.getY());
//			ball.reflect(true, false);
			reset();
		}
		if (ball.right() > fieldRight) {
//			ball.move(fieldRight - ball.getWidth(), ball.getY());
//			ball.reflect(true, false);
			reset();
		}
		if (ball.bottom() < fieldBottom) {
//			ball.move(ball.getX(), fieldBottom);
//			ball.reflect(false, true);
			reset();
		}
		if (ball.top() > fieldTop) {
//			ball.move(ball.getX(), fieldTop - ball.getHeight());
//			ball.reflect(false, true);
			reset();
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
