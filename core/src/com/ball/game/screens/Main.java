package com.ball.game.screens;

import com.badlogic.gdx.Game;

public class Main extends Game {

	@Override
	public void create() {
		setScreen(new MenuScreen(this));
	}

}
