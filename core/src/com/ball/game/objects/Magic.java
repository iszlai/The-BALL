package com.ball.game.objects;

import java.util.concurrent.atomic.AtomicBoolean;

import com.badlogic.gdx.graphics.Color;
import com.ball.game.objects.utils.FSTM;
import com.ball.game.objects.utils.GameObject;
import com.ball.game.objects.utils.MagicType;
import com.ball.game.screens.BallGame;

public class Magic extends GameObject {

	public AtomicBoolean isActive = new AtomicBoolean(false);
	private Color color;
	private MagicType magicType;
	private FSTM machine;

	public Magic(float width, float height, MagicType magicType) {
		super(width, height);
		machine=new FSTM(FSTM.States.INVISIBLE);
		this.magicType=magicType;
		switch (magicType) {
		case FAST:
			color = new Color(0.99f, 0.85f, 0.21f, 1f);
			break;
		case SLOW:
			color = new Color(0.51f, 0.83f, 0.98f, 1f);
			break;
		case TELEPORT:
			color = new Color(0,0,0,1);
			break;
		default:
			color = new Color();
			break;
		}
	}

	public void doMagicOnBall(Ball ball) {
		//System.out.println("DOMAGIc");
		switch (magicType) {
		case FAST:
			ball.speedUP();
			break;
		case SLOW:
			ball.slowDown();
			break;
		case TELEPORT:
			ball.setRNDPosition();
			break;
		default:
			break;
		}
	}
	
	public void undoMagicOnBall(Ball ball) {
		//System.out.println("UndoMAGIc");
		switch (magicType) {
		case FAST:
			ball.slowDown();
			break;
		case SLOW:
			ball.speedUP();
			break;
		default:
			break;
		}
	}


	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
	
	public static void drawMagic(BallGame ballGame,float dt) {
		if (ballGame.magic.isActive.get()) {
			ballGame.shapeRenderer.setColor(ballGame.magic.getColor());
			ballGame.shapeRenderer.rect(ballGame.magic.getX(), ballGame.magic.getY(), ballGame.magic.getWidth(), ballGame.magic.getHeight());
		}
	}
	
	public boolean isInState(FSTM.States state){
		return machine.getCurrentState().equals(state);
	} 
	
	public void transition(FSTM.States state){
		machine.transitionTo(state);
	}
	


}
