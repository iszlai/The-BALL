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
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

public class ScoreScreen extends AbstractGameScreen{

	private SpriteBatch spriteBatch;
	private int WINDOW_WIDTH;
	private int WINDOW_HEIGHT;
	private GlyphLayout layout;
	private BitmapFont font;
	private int score;
	private TextureAtlas textureAtlas;
	private AtlasRegion reload;
	private Sprite sprite;
	private FreeTypeFontGenerator generator;
	private FreeTypeFontParameter parameter;
	
	public ScoreScreen(Game game,int score) {
		super(game);
		this.score=score;
	}
	
	@Override
	public void show() {
		Gdx.app.debug("#GAME#", "in Show");
		spriteBatch = new SpriteBatch();
		WINDOW_WIDTH = Gdx.graphics.getWidth();
		WINDOW_HEIGHT = Gdx.graphics.getHeight();
		layout = new GlyphLayout();
		font = new BitmapFont();
		textureAtlas = new TextureAtlas(Gdx.files.internal("vivace.pack"));
		reload = textureAtlas.findRegion("reset");
		sprite = new Sprite(reload);
		generator = new FreeTypeFontGenerator(Gdx.files.internal("font.ttf"));
		parameter = new FreeTypeFontParameter();
		parameter.size = Math.round (Gdx.graphics.getDensity()*60);
		font = generator.generateFont(parameter);
		font.setColor(new Color(0.96f, 0.26f, 0.21f, 1f));
		sprite.scale(1.1f);
		sprite.setPosition(WINDOW_WIDTH/3-sprite.getWidth()/2, WINDOW_HEIGHT/4-sprite.getHeight()/2);
	}
	@Override
	public void render(float delta) {
		Gdx.graphics.getGL20().glClearColor(1f, 0.95f, 0.88f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		layout.setText(font, "Your score: "+score);
		spriteBatch.begin();
		font.draw(spriteBatch, layout, WINDOW_WIDTH / 2 - layout.width / 2, WINDOW_HEIGHT - WINDOW_HEIGHT / 3);
		sprite.draw(spriteBatch);
		spriteBatch.end();
		update(delta);
	}

	private void update(float delta) {
		if (Gdx.input.isTouched()) {
			float x = Gdx.input.getX();
			float y = WINDOW_HEIGHT -Gdx.input.getY();

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
