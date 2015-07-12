package com.ball.game.screens;

import java.util.concurrent.atomic.AtomicBoolean;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.ball.game.objects.Ball;
import com.ball.game.objects.Magic;
import com.ball.game.objects.Paddle;
import com.ball.game.objects.utils.GameObjectFactory;
import com.ball.game.objects.utils.PaddleDirection;

public class BallGame extends AbstractGameScreen {

	private static final Color BORDER_COLOR = new Color(0.38f, 0.49f, 0.55f, 1);
	public static float BALL_VELOCITY = 0f;
	public static float RESET_BALL_VELOCITY = 0f;
	// FPSLogger logger=new FPSLogger();
	public int gameCount = 0;
	GameObjectFactory goFactory;

	public Paddle paddleUp;
	public Paddle paddleDown;
	public Paddle paddleLeft;
	public Paddle paddleRight;
	public Ball ball;
	Rectangle border;
	public Magic magic;
	public int WINDOW_WIDTH;
	public int WINDOW_HEIGHT;
	private Rectangle field = new Rectangle();
	public ShapeRenderer shapeRenderer;
	public float fieldBottom;
	public float fieldLeft;
	public float fieldRight;
	public float fieldTop;
	public int score;
	private FreeTypeFontGenerator generator;
	private FreeTypeFontParameter parameter;
	private BitmapFont font;
	private GlyphLayout layout;
	private SpriteBatch spriteBatch;
	private AtomicBoolean isMagicOn = new AtomicBoolean(false);

	public BallGame(Game game) {
		super(game);
	}

	@Override
	public void show() {
		shapeRenderer = new ShapeRenderer();
		WINDOW_WIDTH = Gdx.graphics.getWidth();
		WINDOW_HEIGHT = Gdx.graphics.getHeight();
		RESET_BALL_VELOCITY = (WINDOW_HEIGHT + WINDOW_WIDTH) / 5;
		goFactory = new GameObjectFactory(WINDOW_WIDTH, WINDOW_HEIGHT);
		reset();
		setUpScreenBounds();
		// Timer.schedule(new Task() {
		//
		// @Override
		// public void run() {
		// System.out.println("Reset task");
		// BALL_VELOCITY = RESET_BALL_VELOCITY;
		// ball.setVelocity(getBallVelocity());
		//
		// }
		// }, 1.5f);
	}

	private void setUpGameObjects() {

		ball = goFactory.getBall(field);
		paddleUp = goFactory.getRegurarPaddle(PaddleDirection.UP);
		paddleDown = goFactory.getRegurarPaddle(PaddleDirection.DOWN);
		paddleLeft = goFactory.getRegurarPaddle(PaddleDirection.LEFT);
		paddleRight = goFactory.getRegurarPaddle(PaddleDirection.RIGHT);
		border = goFactory.getBorder();
		magic = goFactory.getMagic(border);
		generator = new FreeTypeFontGenerator(Gdx.files.internal("font.ttf"));
		parameter = new FreeTypeFontParameter();
		parameter.size = Math.round(Gdx.graphics.getDensity() * 30);
		font = generator.generateFont(parameter);
		font.setColor(new Color(0.96f, 0.26f, 0.21f, 1f));
		layout = new GlyphLayout();
		spriteBatch = new SpriteBatch();

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
		Ball.updateBall(this, dt);
		Paddle.updatePaddles(this, dt);
		layout.setText(font, "Score: " + score);
		if (score % 10 == 4 && !isMagicOn.get()) {
			isMagicOn.set(true);
			System.out.println("should add block");
		}

	}

	private void draw(float dt) {
		Gdx.graphics.getGL20().glClearColor(1f, 0.95f, 0.88f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		drawBorder();
		shapeRenderer.begin(ShapeType.Filled);
		Ball.drawBall(shapeRenderer, ball, dt);
		Paddle.drawPaddles(this);
		Magic.drawMagic(this,dt);
		shapeRenderer.end();
		drawScore(dt);
	}



	private void drawScore(float dt) {
		spriteBatch.begin();
		font.draw(spriteBatch, layout, GameObjectFactory.BLOCK_SIZE, WINDOW_HEIGHT - GameObjectFactory.BLOCK_SIZE / 3);
		spriteBatch.end();
	}

	private void drawBorder() {
		shapeRenderer.begin(ShapeType.Line);
		shapeRenderer.setColor(BORDER_COLOR);
		for (int i = 1; i < 10; i++) {
			shapeRenderer.rect(border.x - i, border.y - i, border.width + i * 2, border.height + i * 2);
		}
		shapeRenderer.end();
	}

	public void reset() {
		if (gameCount > 4) {
			game.setScreen(new ScoreScreen(game, score));
		} else {
			setUpGameObjects();

			// Reset ball
			Vector2 velocity = getBallVelocity();
			ball.setVelocity(velocity);
		}
	}

	public static Vector2 getBallVelocity() {
		Vector2 velocity = new Vector2();
		velocity.set(RESET_BALL_VELOCITY, 0f);
		velocity.setAngle(-135f);
		return velocity;
	}

	public int getScore(int max, float size) {
		int score = (max - Math.round(size)) / 10;
		return score;
	}

	@Override
	public void dispose() {
		shapeRenderer.dispose();
		spriteBatch.dispose();
		generator.dispose();
	}

}
