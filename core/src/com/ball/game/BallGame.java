package com.ball.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class BallGame extends ApplicationAdapter {
	private static final float BALL_VELOCITY = 550f;
	Paddle paddleUp;
	Paddle paddleDown;
	Paddle paddleLeft;
	Paddle paddleRight;
	Ball ball;
	int width;
	int height;
	private Rectangle field = new Rectangle();
	private ShapeRenderer shapeRenderer;
	private float fieldBottom, fieldLeft, fieldRight, fieldTop;

	@Override
	public void create() {
		shapeRenderer = new ShapeRenderer();
		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();
		field.set(0, 0, width, height);
		fieldLeft = field.x;
		fieldRight = field.x + field.width;
		fieldBottom = field.y;
		fieldTop = field.y + field.height;
		GameObjectFactory goFactory = new GameObjectFactory(width, height);
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
	}
}
