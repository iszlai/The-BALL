package com.ball.game.objects.utils;

import java.security.SecureRandom;
import java.util.Random;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.ball.game.objects.Ball;
import com.ball.game.objects.Magic;
import com.ball.game.objects.Paddle;

public class GameObjectFactory {
    Registry reg = Registry.INSTANCE;
    public static final Random RANDOM = new SecureRandom();


    public Paddle getRegurarPaddle(PaddleDirection paddleDirection) {
        switch (paddleDirection) {
            case DOWN: {
                Paddle paddle = getPaddle(reg.PADDLE_LENGTH_H, reg.PADDLE_HEIGHT);
                paddle.setPosition(new Vector2(reg.PADDING_H, reg.PADDING_V));
                return paddle;
            }
            case UP: {
                Paddle paddle = getPaddle(reg.PADDLE_LENGTH_H, reg.PADDLE_HEIGHT);
                paddle.setPosition(new Vector2(reg.PADDING_H, reg.PADDING_H + reg.PADDLE_LENGTH_V));
                return paddle;
            }
            case LEFT: {
                Paddle paddle = getPaddle( reg.PADDLE_HEIGHT,reg.PADDLE_LENGTH_V);
                paddle.setPosition(new Vector2(reg.PADDING_V, reg.PADDING_H));
                return paddle;
            }
            case RIGHT: {
                Paddle paddle = getPaddle( reg.PADDLE_HEIGHT,reg.PADDLE_LENGTH_V);
                paddle.setPosition(new Vector2(reg.PADDING_H + reg.PADDLE_LENGTH_H, reg.PADDING_H));
                return paddle;
            }
        }
        throw new IllegalArgumentException("Unexpected PaddleDirection value");
    }

    private Paddle getPaddle(float width, float height) {
        return new Paddle(width, height);
    }

    public static <T extends Enum<?>> T randomEnum(Class<T> clazz) {
        int x = RANDOM.nextInt(clazz.getEnumConstants().length);
        return clazz.getEnumConstants()[x];
    }

    public Magic getMagic(Rectangle border) {

        Magic magic = new Magic(reg.BLOCK_SIZE / 2, reg.BLOCK_SIZE / 2, randomEnum(MagicType.class));
        float x = border.getX() + randomWithbounds(3, 6) * reg.BLOCK_SIZE;
        float y = border.getY() + randomWithbounds(2, 3) * reg.BLOCK_SIZE;
        magic.setPosition(new Vector2(x, y));
        return magic;
    }

    public float randomWithbounds(int down, int up) {
        return down + RANDOM.nextInt(up);
    }

    public Ball getBall(Rectangle field) {
        Ball ball = new Ball(reg.BLOCK_SIZE / 2, reg.BLOCK_SIZE / 2);
        ball.move(field.x + (field.width - ball.getWidth()) / 2, field.y + (field.height - ball.getHeight()) / 2);
        return ball;
    }

    public Rectangle getBorder() {
        return new Rectangle(reg.PADDLE_HEIGHT - 5, reg.PADDLE_HEIGHT - 5, 10.5f * reg.BLOCK_SIZE + 10, 6f * reg.BLOCK_SIZE + 5);
    }
}
