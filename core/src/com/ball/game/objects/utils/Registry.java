package com.ball.game.objects.utils;

import com.badlogic.gdx.Gdx;

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

    private Registry() {
        //Window
        WINDOW_WIDTH = Gdx.graphics.getWidth();
        WINDOW_HEIGHT = Gdx.graphics.getHeight();
        BLOCK_SIZE = (WINDOW_WIDTH + WINDOW_HEIGHT) / 20;

        //BALL
        RESET_BALL_VELOCITY = (WINDOW_HEIGHT + WINDOW_WIDTH) / 5;

        //PADDLE
        PADDLE_LENGTH_H = 9.5f * BLOCK_SIZE;
        PADDLE_LENGTH_V = 5f * BLOCK_SIZE;
        PADDLE_HEIGHT = 0.5f * BLOCK_SIZE;

        //PADDINGS
        PADDING_H = 1f * BLOCK_SIZE;
        PADDING_V = 0.5f * BLOCK_SIZE;
    }
}