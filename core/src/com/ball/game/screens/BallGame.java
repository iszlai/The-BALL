package com.ball.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.ball.game.objects.Ball;
import com.ball.game.objects.GameObjectFactory;
import com.ball.game.objects.Paddle;
import com.ball.game.objects.PaddleDirection;
import static com.ball.game.util.CollisionUtils.*;
import com.ball.game.util.GeometryUtil;

public class BallGame extends AbstractGameScreen {

	private static final Color VERTICAL_COLOR = new Color(0.34f, 0.53f, 0.41f, 1);
	private static final Color HORIZONTAL_COLOR = new Color(0.35f, 0.36f, 0.50f, 1f);
	private static final Color BALL_COLOR = new Color(0.96f, 0.26f, 0.21f, 1f);
	private static final Color BORDER_COLOR = new Color(0.38f, 0.49f, 0.55f, 1);
	private static final float BALL_VELOCITY = 350f;
	// FPSLogger logger=new FPSLogger();
	int gameCount = 0;
	GameObjectFactory goFactory;

	Paddle paddleUp;
	Paddle paddleDown;
	Paddle paddleLeft;
	Paddle paddleRight;
	Ball ball;
	Rectangle border;
	int WINDOW_WIDTH;
	int WINDOW_HEIGHT;
	private Rectangle field = new Rectangle();
	private ShapeRenderer shapeRenderer;
	private float fieldBottom, fieldLeft, fieldRight, fieldTop;
	int score;

	public BallGame(Game game) {
		super(game);
	}

	@Override
	public void show() {
		shapeRenderer = new ShapeRenderer();
		WINDOW_WIDTH = Gdx.graphics.getWidth();
		WINDOW_HEIGHT = Gdx.graphics.getHeight();
		goFactory = new GameObjectFactory(WINDOW_WIDTH, WINDOW_HEIGHT);
		reset();
		setUpScreenBounds();
	}

	private void setUpGameObjects() {

		ball = goFactory.getBall(field);
		paddleUp = goFactory.getRegurarPaddle(PaddleDirection.UP);
		paddleDown = goFactory.getRegurarPaddle(PaddleDirection.DOWN);
		paddleLeft = goFactory.getRegurarPaddle(PaddleDirection.LEFT);
		paddleRight = goFactory.getRegurarPaddle(PaddleDirection.RIGHT);
		border = goFactory.getBorder();

	}

	private void setUpScreenBounds() {
		field.set(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
		fieldLeft = border.x;
		fieldRight = border.x + border.width;
		fieldBottom = border.y;
		fieldTop = border.y + border.height;
	}

	@Override
	public void render(float delta) {

		update(delta);
		draw(delta);

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
			paddleLeft.integrate(dt);
			paddleLeft.updateBounds();
			paddleRight.integrate(dt);
			paddleRight.updateBounds();
			paddleUp.integrate(dt);
			paddleUp.updateBounds();
			paddleDown.integrate(dt);
			paddleDown.updateBounds();
			paddleVerticalCheck(paddleLeft, fieldTop, fieldBottom);
			paddleVerticalCheck(paddleRight, fieldTop, fieldBottom);
			paddleHorizontalCheck(paddleUp, fieldLeft, fieldRight);
			paddleHorizontalCheck(paddleDown, fieldLeft, fieldRight);

		}
	}

	private void draw(float dt) {
		Gdx.graphics.getGL20().glClearColor(1f, 0.95f, 0.88f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		drawBorder();
		shapeRenderer.begin(ShapeType.Filled);
		drawBall(dt);
		drawPaddles();
		shapeRenderer.end();
	}

	private void drawBorder() {
		shapeRenderer.begin(ShapeType.Line);
		shapeRenderer.setColor(BORDER_COLOR);
		for (int i = 1; i < 10; i++) {
			shapeRenderer.rect(border.x - i, border.y - i, border.width + i * 2, border.height + i * 2);
		}
		shapeRenderer.end();
	}

	private void drawBall(float dt) {
		shapeRenderer.setColor(BALL_COLOR);
		shapeRenderer.circle(ball.getX(), ball.getY(), ball.getWidth());
	}

	private void drawPaddles() {
		shapeRenderer.setColor(HORIZONTAL_COLOR);
		renderPaddle(shapeRenderer, paddleUp);
		renderPaddle(shapeRenderer, paddleDown);
		shapeRenderer.setColor(VERTICAL_COLOR);
		renderPaddle(shapeRenderer, paddleLeft);
		renderPaddle(shapeRenderer, paddleRight);
	}

	private void renderPaddle(ShapeRenderer renderer, Paddle paddle) {
		renderer.rect(paddle.getX(), paddle.getY(), paddle.getWidth(), paddle.getHeight());
	}

	public void reset() {
		if (gameCount > 4) {
			game.setScreen(new ScoreScreen(game, score));
		} else {
			gameCount++;
			System.out.println("RESET");
			setUpGameObjects();

			// Reset ball
			Vector2 velocity = ball.getVelocity();
			velocity.set(BALL_VELOCITY, 0f);
			velocity.setAngle(-135f);
			ball.setVelocity(velocity);
		}
	}

	void updateBall(float dt) {
		ball.integrate(dt);
		ball.updateBounds();
		GeometryUtil.ballLimitVelocity(ball);
		// Field collision
		if (ball.left() < fieldLeft) {
			// ball.move(fieldLeft, ball.getY());
			// ball.reflect(true, false);
			reset();
		}
		if (ball.right() > fieldRight) {
			// ball.move(fieldRight - ball.getWidth(), ball.getY());
			// ball.reflect(true, false);
			reset();
		}
		if (ball.bottom() < fieldBottom) {
			// ball.move(ball.getX(), fieldBottom);
			// ball.reflect(false, true);
			reset();
		}
		if (ball.top() > fieldTop) {
			// ball.move(ball.getX(), fieldTop - ball.getHeight());
			// ball.reflect(false, true);
			reset();
		}

		// Paddle collision
		if (ball.getBounds().overlaps(paddleLeft.getBounds())) {
			score+=getScore(paddleLeft.getHeight());
			handleLeftCollision(ball, paddleLeft);
		} else if (ball.getBounds().overlaps(paddleRight.getBounds())) {
			score+=getScore(paddleRight.getHeight());
			handleRightCollision(ball, paddleRight);
		} else if (ball.getBounds().overlaps(paddleUp.getBounds())) {
			score+=getScore(paddleUp.getWidth());
			handleUpCollision(ball, paddleUp);
		} else if (ball.getBounds().overlaps(paddleDown.getBounds())) {
			score+=getScore(paddleDown.getWidth());
			handleDownCollision(ball, paddleDown);
		}
	}
	
	public static int getScore(float size){
		return (1000-Math.round(size))/100;
	}

}
