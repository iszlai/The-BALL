package com.ball.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.ball.game.objects.Ball;
import com.ball.game.objects.Magic;
import com.ball.game.objects.Paddle;
import com.ball.game.objects.utils.FSTM;
import com.ball.game.objects.utils.FontSizes;
import com.ball.game.objects.utils.GameObjectFactory;
import com.ball.game.objects.utils.PaddleDirection;
import com.ball.game.objects.utils.Registry;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

public class BallGame extends AbstractGameScreen {

	private static final Color BORDER_COLOR = new Color(0.38f, 0.49f, 0.55f, 1);
	public static float BALL_VELOCITY = 0f;
	public int gameCount = 0;
	public GameObjectFactory goFactory;
	public static Registry reg=Registry.INSTANCE;
	public Paddle paddleUp;
	public Paddle paddleDown;
	public Paddle paddleLeft;
	public Paddle paddleRight;
	public Ball ball;
    public Rectangle border;
    public Magic magic;
    private Rectangle field = new Rectangle();
    public ShapeRenderer shapeRenderer;
    public float fieldBottom;
	public float fieldLeft;
	public float fieldRight;
	public float fieldTop;
	public int score;
	private BitmapFont font;
	private GlyphLayout layout;
	private SpriteBatch spriteBatch;
	public AtomicBoolean isMagicOn = new AtomicBoolean(false);
    public AtomicBoolean isNewMagicNeeded = new AtomicBoolean(false);
    public AtomicLong nrOfBounces = new AtomicLong();

	public BallGame(Game game) {
		super(game);
	}

	@Override
	public void show() {
		shapeRenderer = new ShapeRenderer();
		//TODO: move to other const after show
		goFactory = new GameObjectFactory();
		reset();
		setUpScreenBounds();
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {

            @Override
            public void run() {
                //	System.out.println("----------------");
                if (magic.isInState(FSTM.States.INVISIBLE)) {
                    magic.isActive.set(true);
                    magic.transition(FSTM.States.VISIBLE);
                } else if (magic.isInState(FSTM.States.VISIBLE)) {
                    magic.transition(FSTM.States.INVISIBLE);
                    magic.isActive.set(false);
                    isNewMagicNeeded.set(true);
                } else if (magic.isInState(FSTM.States.SPELL_ACTIVE)) {
                    magic.transition(FSTM.States.SPELL_INACTIVE);
                    magic.undoMagicOnBall(ball);
                    magic.isActive.set(false);
                } else if (magic.isInState(FSTM.States.SPELL_INACTIVE)) {
                    magic.transition(FSTM.States.INVISIBLE);
                    magic.isActive.set(false);

                }
            }
        }, 0, 4000);

	}

	private void setUpGameObjects() {

		ball = goFactory.getBall(field);
		paddleUp = goFactory.getRegurarPaddle(PaddleDirection.UP);
		paddleDown = goFactory.getRegurarPaddle(PaddleDirection.DOWN);
		paddleLeft = goFactory.getRegurarPaddle(PaddleDirection.LEFT);
		paddleRight = goFactory.getRegurarPaddle(PaddleDirection.RIGHT);
		border = goFactory.getBorder();
        //TODO: Check for reuse or seed
		magic = goFactory.getMagic(border);
        font = reg.getFont(FontSizes.SMALL);
        font.setColor(new Color(0.96f, 0.26f, 0.21f, 1f));
        layout = new GlyphLayout();
        spriteBatch = new SpriteBatch();

	}

	private void setUpScreenBounds() {
		field.set(0, 0, reg.WINDOW_WIDTH, reg.WINDOW_HEIGHT);
		fieldLeft = border.x;
		fieldRight = border.x + border.width;
		fieldBottom = border.y;
		fieldTop = border.y + border.height;
	}

	@Override
	public void render(float delta) {
        //TODO: maybe reverse?
		update(delta);
		draw(delta);

	}

	private void update(float dt) {
		Ball.updateBall(this, dt);
        Paddle.updatePaddles(this, dt);
        layout.setText(font, "Score: " + score);
        changeMagicIfNeeded();
        //Magic.updateMagic(this);

	}

	private void draw(float dt) {
		Gdx.graphics.getGL20().glClearColor(1f, 0.95f, 0.88f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		drawBorder();
		shapeRenderer.begin(ShapeType.Filled);
		Ball.drawBall(shapeRenderer, ball, dt);
		Paddle.drawPaddles(this);
        Magic.drawMagic(this, dt);
        shapeRenderer.end();
        drawScore(dt);
    }

	private void drawScore(float dt) {
		spriteBatch.begin();
        font.draw(spriteBatch, layout, reg.BLOCK_SIZE, reg.WINDOW_HEIGHT - reg.BLOCK_SIZE / 3);
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
		velocity.set(reg.RESET_BALL_VELOCITY, 0f);
		velocity.setAngle(-135f);
		return velocity;
	}


    public void changeMagicIfNeeded() {
        if (isNewMagicNeeded.getAndSet(false)) {
            this.magic = goFactory.getMagic(border);
            magic.isActive.set(false);
        }
    }

	public int getScore(int max, float size) {
		return (max - Math.round(size)) / 10;
	}

	@Override
	public void dispose() {
		shapeRenderer.dispose();
		spriteBatch.dispose();
	}

}
