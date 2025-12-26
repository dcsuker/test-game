package com.demo.rpg.ecs.systems;

import com.demo.rpg.ecs.SystemBase;
import com.demo.rpg.ecs.World;

public class CleanupSystem extends SystemBase {
    @Override
    public void update(World world, float deltaTime) {
        // Removal handled by world at end of update cycle
    }
}
