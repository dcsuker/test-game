package com.demo.rpg;

import com.badlogic.gdx.Game;
import com.demo.rpg.screens.GameScreen;

public class GameMain extends Game {
    private GameScreen gameScreen;

    @Override
    public void create() {
        this.gameScreen = new GameScreen();
        setScreen(gameScreen);
    }

    @Override
    public void dispose() {
        if (gameScreen != null) {
            gameScreen.dispose();
        }
        super.dispose();
    }
}
