package com.ball.game.objects.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

public class Registry {
    public final static Registry INSTANCE = new Registry();
    public final int WINDOW_WIDTH;
    public final int WINDOW_HEIGHT;
    public final int RESET_BALL_VELOCITY;
    public final float BLOCK_SIZE;
    public final float PADDLE_LENGTH_H;
    public final float PADDLE_HEIGHT;
    public final float PADDLE_LENGTH_V;
    public final float PADDING_H;
    public final float PADDING_V;
    public final float BLOCK_SIZE_HALF;
    public static final float BALL_VELOCITY_MODIFIER = 1.01f;
    private final FreeTypeFontGenerator generator;

    private Registry() {
        //Window
        WINDOW_WIDTH = Gdx.graphics.getWidth();
        WINDOW_HEIGHT = Gdx.graphics.getHeight();
        BLOCK_SIZE = (WINDOW_WIDTH + WINDOW_HEIGHT) / 20;
        BLOCK_SIZE_HALF = BLOCK_SIZE / 2;

        //BALL
        RESET_BALL_VELOCITY = (WINDOW_HEIGHT + WINDOW_WIDTH) / 5;

        //PADDLE
        PADDLE_LENGTH_H = 9.5f * BLOCK_SIZE;
        PADDLE_LENGTH_V = 5f * BLOCK_SIZE;
        PADDLE_HEIGHT = 0.5f * BLOCK_SIZE;

        //PADDINGS
        PADDING_H = 1f * BLOCK_SIZE;
        PADDING_V = 0.5f * BLOCK_SIZE;

        //Font
        generator = new FreeTypeFontGenerator(Gdx.files.internal("font.ttf"));

    }

    public BitmapFont getFont(FontSizes sizes) {

        switch (sizes) {
            case SMALL:
                return generator.generateFont(getFontParam(30));
            case MEDIUM:
                return generator.generateFont(getFontParam(60));
            case LARGE:
                return generator.generateFont(getFontParam(100));
            default:
                return generator.generateFont(getFontParam(30));
        }

    }

    public FreeTypeFontParameter getFontParam(int size) {
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();
        parameter.size = Math.round(Gdx.graphics.getDensity() * size);
        return parameter;
    }


    public void cleanUp() {
        generator.dispose();
    }


}