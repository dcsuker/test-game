package com.demo.rpg;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.demo.rpg.util.Constants;

public class DesktopLauncher {
    public static void main(String[] arg) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setTitle("ECS RPG Demo");
        config.setWindowedMode((int) Constants.WORLD_WIDTH, (int) Constants.WORLD_HEIGHT);
        config.useVsync(true);
        new Lwjgl3Application(new GameMain(), config);
    }
}
