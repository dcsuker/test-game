package com.demo.rpg.ecs.components;

import com.demo.rpg.ecs.Component;

public record AttackComponent(float damage, float range, boolean attacking) implements Component {
    public AttackComponent withAttacking(boolean value) {
        return new AttackComponent(damage, range, value);
    }
}
