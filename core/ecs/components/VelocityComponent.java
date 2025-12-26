package com.demo.rpg.ecs.components;

import com.demo.rpg.ecs.Component;

public record VelocityComponent(float vx, float vy) implements Component {
}
