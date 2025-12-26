package com.demo.rpg.ecs.systems;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.demo.rpg.ecs.SystemBase;
import com.demo.rpg.ecs.World;
import com.demo.rpg.ecs.components.HealthComponent;
import com.demo.rpg.ecs.components.PlayerTag;
import com.demo.rpg.ecs.components.PositionComponent;
import com.demo.rpg.util.Constants;

import java.util.Set;

public class RenderSystem extends SystemBase {
    @Override
    public void render(World world, ShapeRenderer renderer) {
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        drawEntities(world, renderer);
        renderer.end();
    }

    private void drawEntities(World world, ShapeRenderer renderer) {
        Set<Integer> entities = world.getEntitiesWith(PositionComponent.class, HealthComponent.class);
        for (int entityId : entities) {
            PositionComponent position = world.getComponent(entityId, PositionComponent.class).orElse(null);
            HealthComponent health = world.getComponent(entityId, HealthComponent.class).orElse(null);
            if (position == null || health == null) {
                continue;
            }
            boolean isPlayer = world.getComponent(entityId, PlayerTag.class).isPresent();
            renderer.setColor(isPlayer ? Color.CYAN : Color.SCARLET);
            renderer.rect(position.x() - Constants.ENTITY_SIZE / 2f,
                    position.y() - Constants.ENTITY_SIZE / 2f,
                    Constants.ENTITY_SIZE,
                    Constants.ENTITY_SIZE);
            drawHealthBar(renderer, position, health);
        }
    }

    private void drawHealthBar(ShapeRenderer renderer, PositionComponent position, HealthComponent health) {
        float barWidth = Constants.ENTITY_SIZE;
        float barHeight = Constants.HEALTH_BAR_HEIGHT;
        float x = position.x() - barWidth / 2f;
        float y = position.y() + Constants.ENTITY_SIZE / 2f + 4f;
        float healthRatio = health.hp() / health.maxHp();
        renderer.setColor(Color.DARK_GRAY);
        renderer.rect(x, y, barWidth, barHeight);
        renderer.setColor(Color.GREEN);
        renderer.rect(x, y, barWidth * healthRatio, barHeight);
    }
}
