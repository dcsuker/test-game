package com.demo.rpg.ecs.systems;

import com.demo.rpg.ecs.SystemBase;
import com.demo.rpg.ecs.World;
import com.demo.rpg.ecs.components.PositionComponent;
import com.demo.rpg.ecs.components.VelocityComponent;

import java.util.Set;

public class MovementSystem extends SystemBase {
    @Override
    public void update(World world, float deltaTime) {
        Set<Integer> entities = world.getEntitiesWith(PositionComponent.class, VelocityComponent.class);
        for (int entityId : entities) {
            PositionComponent position = world.getComponent(entityId, PositionComponent.class).orElse(null);
            VelocityComponent velocity = world.getComponent(entityId, VelocityComponent.class).orElse(null);
            if (position == null || velocity == null) {
                continue;
            }
            float newX = position.x() + velocity.vx() * deltaTime;
            float newY = position.y() + velocity.vy() * deltaTime;
            world.updateComponent(entityId, new PositionComponent(newX, newY));
        }
    }
}
