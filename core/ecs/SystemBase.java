package com.demo.rpg.ecs;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public abstract class SystemBase {
    public void update(World world, float deltaTime) {
        // default no-op
    }

    public void render(World world, ShapeRenderer renderer) {
        // default no-op
    }
}
