package com.ball.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class BallGame extends ApplicationAdapter {
	private static final float BALL_VELOCITY = 150f;
	private static final float BALL_VELOCITY_MODIFIER = 1.05f;
	private static final float BALL_REFLECT_ANGLE = 70f;
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
		field.set(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
		fieldLeft = field.x;
		fieldRight = field.x + field.width;
		fieldBottom = field.y;
		fieldTop = field.y + field.height;
		GameObjectFactory goFactory = new GameObjectFactory(WINDOW_WIDTH, WINDOW_HEIGHT);
		ball=goFactory.getBall();
		paddleUp = goFactory.getRegurarPaddle(PaddleDirection.UP);
		paddleDown = goFactory.getRegurarPaddle(PaddleDirection.DOWN);
		paddleLeft = goFactory.getRegurarPaddle(PaddleDirection.LEFT);
		paddleRight = goFactory.getRegurarPaddle(PaddleDirection.RIGHT);
		reset();
	}

	@Override
	public void render() {
		float dt= Gdx.graphics.getRawDeltaTime();
		update(dt);
		draw(dt);
	}

	private void update(float dt) {
		updateBall(dt);
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
	
	void updateBall(float dt){
		ball.integrate(dt);
		ball.updateBounds();
		// Field collision
				if(ball.left() < fieldLeft) {
					ball.move(fieldLeft, ball.getY());
					ball.reflect(true, false);
				}
				if(ball.right() > fieldRight) {
					ball.move(fieldRight - ball.getWidth(), ball.getY());
					ball.reflect(true, false);
				}
				if(ball.bottom() < fieldBottom) {
					ball.move(ball.getX(), fieldBottom);
					ball.reflect(false, true);
				}
				if(ball.top() > fieldTop) {
					ball.move(ball.getX(), fieldTop - ball.getHeight());
					ball.reflect(false, true);
				}
				
				// Paddle collision
				if(ball.getBounds().overlaps(paddleLeft.getBounds())) {
					handleLeftCollision();
				} else if(ball.getBounds().overlaps(paddleRight.getBounds())) {
					handleRightCollision();
				}else if(ball.getBounds().overlaps(paddleUp.getBounds())) {
					handleUpCollision();
				}
	}

	private void handleRightCollision() {
		if(ball.right() > paddleRight.left() && ball.left() < paddleRight.left()) {
			ball.move(paddleRight.left() - ball.getWidth(), ball.getY());
			ball.reflect(true, false);

			Vector2 velocity = ball.getVelocity();
			velocity.setAngle(180f - getReflectionAngle(paddleRight));
			velocity.scl(BALL_VELOCITY_MODIFIER);
			ball.setVelocity(velocity);
		}
	}

	private void handleLeftCollision() {
		if(ball.left() < paddleLeft.right() && ball.right() > paddleLeft.right()) {
			ball.move(paddleLeft.right(), ball.getY());
			ball.reflect(true, false);

			Vector2 velocity = ball.getVelocity();
			velocity.setAngle(getReflectionAngle(paddleLeft));
			velocity.scl(BALL_VELOCITY_MODIFIER);
			ball.setVelocity(velocity);
		}
	}
	
	private void handleUpCollision() {
			System.out.println("b-t:"+ball.top()+" p-b:"+paddleUp.bottom() +"v:"+(ball.top() < paddleUp.bottom()));
		if(ball.top() >paddleUp.bottom() && ball.bottom() > paddleUp.bottom()) {
			System.out.println("handel up collision");
			ball.move(paddleUp.bottom()-ball.getHeight(), ball.getX());
			ball.reflect(true, false);

			Vector2 velocity = ball.getVelocity();
			velocity.setAngle(getReflectionAngle(paddleUp));
			velocity.scl(BALL_VELOCITY_MODIFIER);
			ball.setVelocity(velocity);
		}
	}
	
	private void handleDownCollision() {
		if(ball.bottom() < paddleLeft.right() && ball.right() > paddleLeft.right()) {
			ball.move(paddleLeft.right(), ball.getY());
			ball.reflect(true, false);

			Vector2 velocity = ball.getVelocity();
			velocity.setAngle(getReflectionAngle(paddleLeft));
			velocity.scl(BALL_VELOCITY_MODIFIER);
			ball.setVelocity(velocity);
		}
	}

	private float getReflectionAngle(Paddle paddle) {
		float ballCenterY = ball.getY() + (ball.getHeight() / 2f);
		float paddleCenterY = paddle.getY() + (paddle.getHeight() / 2f);
		float difference = ballCenterY - paddleCenterY;
		float position = difference / paddle.getHeight();
		return BALL_REFLECT_ANGLE * position;
	}
}
