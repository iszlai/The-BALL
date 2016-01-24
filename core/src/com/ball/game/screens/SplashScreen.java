package com.ball.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

/**
 * Created by leheli on 2016.01.24..
 */
public class SplashScreen extends AbstractGameScreen {

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
    private FreeTypeFontGenerator.FreeTypeFontParameter parameter;

    public SplashScreen(Game game) {
        super(game);
    }
    
    @Override
    public void render(float delta) {

    }

    @Override
    public void dispose() {
        Gdx.app.debug("Cubocy", "dispose main menu");
        spriteBatch.dispose();
        generator.dispose();
        textureAtlas.dispose();
    }
}
