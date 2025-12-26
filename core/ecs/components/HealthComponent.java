package com.demo.rpg.ecs.components;

import com.demo.rpg.ecs.Component;

public record HealthComponent(float hp, float maxHp) implements Component {
    public HealthComponent clamp() {
        float clampedHp = Math.max(0f, Math.min(hp, maxHp));
        return new HealthComponent(clampedHp, maxHp);
    }

    public boolean isAlive() {
        return hp > 0f;
    }
}
