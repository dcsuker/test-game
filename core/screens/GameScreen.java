package com.demo.rpg.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.demo.rpg.ecs.World;
import com.demo.rpg.ecs.components.AttackComponent;
import com.demo.rpg.ecs.components.HealthComponent;
import com.demo.rpg.ecs.components.PlayerTag;
import com.demo.rpg.ecs.components.PositionComponent;
import com.demo.rpg.ecs.components.VelocityComponent;
import com.demo.rpg.ecs.systems.CleanupSystem;
import com.demo.rpg.ecs.systems.CombatSystem;
import com.demo.rpg.ecs.systems.InputSystem;
import com.demo.rpg.ecs.systems.MovementSystem;
import com.demo.rpg.ecs.systems.RenderSystem;
import com.demo.rpg.util.Constants;

public class GameScreen extends ScreenAdapter {
    private final World world;
    private final ShapeRenderer shapeRenderer;

    public GameScreen() {
        this.world = new World();
        this.shapeRenderer = new ShapeRenderer();
        setupEntities();
        registerSystems();
    }

    private void setupEntities() {
        int player = world.createEntity();
        world.addComponent(player, new PositionComponent(Constants.WORLD_WIDTH / 2f, Constants.WORLD_HEIGHT / 2f));
        world.addComponent(player, new VelocityComponent(0f, 0f));
        world.addComponent(player, new HealthComponent(Constants.PLAYER_MAX_HP, Constants.PLAYER_MAX_HP));
        world.addComponent(player, new AttackComponent(Constants.PLAYER_DAMAGE, Constants.PLAYER_ATTACK_RANGE, false));
        world.addComponent(player, new PlayerTag());

        int monster = world.createEntity();
        world.addComponent(monster, new PositionComponent(Constants.WORLD_WIDTH / 2f + 150f, Constants.WORLD_HEIGHT / 2f));
        world.addComponent(monster, new VelocityComponent(0f, 0f));
        world.addComponent(monster, new HealthComponent(Constants.MONSTER_MAX_HP, Constants.MONSTER_MAX_HP));
    }

    private void registerSystems() {
        world.addSystem(new InputSystem());
        world.addSystem(new MovementSystem());
        world.addSystem(new CombatSystem());
        world.addSystem(new CleanupSystem());
        world.addSystem(new RenderSystem());
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(Color.DARK_GRAY.r, Color.DARK_GRAY.g, Color.DARK_GRAY.b, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        world.update(delta);
        world.render(shapeRenderer);
    }

    @Override
    public void resize(int width, int height) {
        shapeRenderer.getProjectionMatrix().setToOrtho2D(0f, 0f, width, height);
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
    }
}
