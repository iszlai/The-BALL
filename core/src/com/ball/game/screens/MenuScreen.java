package com.ball.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

public class MenuScreen extends AbstractGameScreen {

	SpriteBatch spriteBatch;
	float time = 0;
	private BitmapFont font;
	private int WINDOW_WIDTH;
	private int WINDOW_HEIGHT;
	private GlyphLayout layout;
	private TextureAtlas textureAtlas;
	private TextureRegion play;
	private Sprite sprite;
	private FreeTypeFontGenerator generator;
	private FreeTypeFontParameter parameter;

	public MenuScreen(Game game) {
		super(game);
	}

	@Override
	public void show() {
		Gdx.app.debug("#GAME#", "in Show");
		spriteBatch = new SpriteBatch();
		WINDOW_WIDTH = Gdx.graphics.getWidth();
		WINDOW_HEIGHT = Gdx.graphics.getHeight();
		//System.out.println(Gdx.graphics.getDensity() * 128);
		textureAtlas = new TextureAtlas(Gdx.files.internal("ballTextures.atlas"));
		play = textureAtlas.findRegion("play");
		sprite = new Sprite(play);
		textureAtlas.findRegion("play-down");
		layout = new GlyphLayout();
		generator = new FreeTypeFontGenerator(Gdx.files.internal("font.ttf"));
		parameter = new FreeTypeFontParameter();
		parameter.size = Math.round(Gdx.graphics.getDensity() * 100);
		font = generator.generateFont(parameter);
		font.setColor(new Color(0.96f, 0.26f, 0.21f, 1f));
		sprite.scale(1.1f);
		sprite.setPosition(WINDOW_WIDTH / 3 - sprite.getWidth() / 2, WINDOW_HEIGHT / 4 - sprite.getHeight() / 2);
	}

	@Override
	public void render(float delta) {
		Gdx.graphics.getGL20().glClearColor(1f, 0.95f, 0.88f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		layout.setText(font, "Vivace");
		spriteBatch.begin();
		font.draw(spriteBatch, layout, WINDOW_WIDTH / 2 - layout.width / 2, WINDOW_HEIGHT - WINDOW_HEIGHT / 8);
		// spriteBatch.draw(play, 100, 100, 100, 100);
		sprite.draw(spriteBatch);
		spriteBatch.end();
		update(delta);
		// game.setScreen(new IntroScreen(game));
	}

	private void update(float delta) {
		if (Gdx.input.isTouched()) {
			float x = Gdx.input.getX();
			float y = WINDOW_HEIGHT - Gdx.input.getY();

			if (sprite.getBoundingRectangle().contains(x, y)) {
				game.setScreen(new BallGame(game));
				return;
			}
		}

	}

	@Override
	public void dispose() {
		Gdx.app.debug("Cubocy", "dispose main menu");
		spriteBatch.dispose();
		generator.dispose();
		textureAtlas.dispose();
	}

}
