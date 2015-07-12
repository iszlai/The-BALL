package com.ball.game.objects;

import java.util.concurrent.atomic.AtomicBoolean;

import com.badlogic.gdx.graphics.Color;
import com.ball.game.objects.utils.GameObject;
import com.ball.game.objects.utils.MagicType;
import com.ball.game.screens.BallGame;

public class Magic extends GameObject {

	private AtomicBoolean isActive = new AtomicBoolean(false);
	private Color color;
	private final float BALL_MODIFY;

	public Magic(float width, float height, MagicType magicType) {
		super(width, height);
		switch (magicType) {
		case FAST:
			color = new Color(0.99f, 0.85f, 0.21f, 1f);
			BALL_MODIFY = 1.5f;
			break;
		case SLOW:
			color = new Color(0.51f, 0.83f, 0.98f, 1f);
			BALL_MODIFY = 0.3f;
			break;
		default:
			BALL_MODIFY = 1f;
			color = new Color();
			break;
		}
	}

	public void doMagicOnBall(Ball ball) {
		if (isActive.get()) {
			System.out.println("Before" + ball.getVelocityX() + " " + ball.getVelocityY());
			ball.setVelocity(BallGame.getBallVelocity().scl(BALL_MODIFY));
			System.out.println("After" + ball.getVelocityX() + " " + ball.getVelocityY());
		}
	}

	public boolean isActive() {
		return isActive.get();
	}

	public void setActive(boolean isActive) {
		this.isActive.set(isActive);
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
	
	public static void drawMagic(BallGame ballGame,float dt) {
		if (ballGame.magic.isActive()) {
			ballGame.shapeRenderer.setColor(ballGame.magic.getColor());
			ballGame.shapeRenderer.rect(ballGame.magic.getX(), ballGame.magic.getY(), ballGame.magic.getWidth(), ballGame.magic.getHeight());
		}
	}

}
