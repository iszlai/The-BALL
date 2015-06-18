package com.ball.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.sun.java.swing.plaf.gtk.GTKConstants.ShadowType;

public class BallGame extends ApplicationAdapter {
	Paddle paddleUp =new Paddle(PaddleDirection.UP);
	Paddle paddleDown =new Paddle(PaddleDirection.DOWN);
	Paddle paddleLeft =new Paddle(PaddleDirection.LEFT);
	Paddle paddleRight =new Paddle(PaddleDirection.RIGHT);
	 int width;
	 int heigth;
	
	ShapeRenderer renderer;
	
	@Override
	public void create () {
		renderer=new ShapeRenderer();
		width=Gdx.graphics.getWidth();
		heigth=Gdx.graphics.getHeight();
	}

	@Override
	public void render () {
		update();
		draw();
	}

	private void update() {
	
		
	}

	private void draw() {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		renderer.begin(ShapeType.Filled);
		renderer.rect(paddleDown.getPostionX(),paddleDown.getPostionY(), paddleDown.getWidth(), paddleDown.getHeight());
		renderer.rect(paddleUp.getPostionX(),paddleUp.getPostionY(), paddleUp.getWidth(), paddleUp.getHeight());
		renderer.rect(paddleLeft.getPostionX(),paddleLeft.getPostionY(), paddleLeft.getWidth(), paddleLeft.getHeight());
		renderer.rect(paddleRight.getPostionX(),paddleRight.getPostionY(), paddleRight.getWidth(), paddleRight.getHeight());
		
		renderer.end();
	}
}
