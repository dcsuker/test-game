package com.demo.rpg.ecs.systems;

import com.demo.rpg.ecs.SystemBase;
import com.demo.rpg.ecs.World;
import com.demo.rpg.ecs.components.AttackComponent;
import com.demo.rpg.ecs.components.HealthComponent;
import com.demo.rpg.ecs.components.PlayerTag;
import com.demo.rpg.ecs.components.PositionComponent;

import java.util.Set;

public class CombatSystem extends SystemBase {
    @Override
    public void update(World world, float deltaTime) {
        Set<Integer> players = world.getEntitiesWith(PlayerTag.class, AttackComponent.class, PositionComponent.class);
        if (players.isEmpty()) {
            return;
        }
        int playerId = players.iterator().next();
        AttackComponent attack = world.getComponent(playerId, AttackComponent.class).orElse(null);
        PositionComponent playerPos = world.getComponent(playerId, PositionComponent.class).orElse(null);
        if (attack == null || playerPos == null || !attack.attacking()) {
            return;
        }

        Set<Integer> monsters = world.getEntitiesWith(PositionComponent.class, HealthComponent.class);
        for (int monsterId : monsters) {
            if (monsterId == playerId) {
                continue;
            }
            PositionComponent monsterPos = world.getComponent(monsterId, PositionComponent.class).orElse(null);
            HealthComponent health = world.getComponent(monsterId, HealthComponent.class).orElse(null);
            if (monsterPos == null || health == null || !health.isAlive()) {
                continue;
            }
            float dx = monsterPos.x() - playerPos.x();
            float dy = monsterPos.y() - playerPos.y();
            float distanceSquared = dx * dx + dy * dy;
            if (distanceSquared <= attack.range() * attack.range()) {
                float remainingHp = health.hp() - attack.damage();
                world.updateComponent(monsterId, new HealthComponent(remainingHp, health.maxHp()).clamp());
                if (remainingHp <= 0f) {
                    world.markForRemoval(monsterId);
                }
            }
        }
        world.updateComponent(playerId, attack.withAttacking(false));
    }
}
