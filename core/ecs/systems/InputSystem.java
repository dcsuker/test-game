package com.demo.rpg.ecs.systems;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.demo.rpg.ecs.SystemBase;
import com.demo.rpg.ecs.World;
import com.demo.rpg.ecs.components.AttackComponent;
import com.demo.rpg.ecs.components.PlayerTag;
import com.demo.rpg.ecs.components.VelocityComponent;
import com.demo.rpg.util.Constants;

import java.util.Set;

public class InputSystem extends SystemBase {
    @Override
    public void update(World world, float deltaTime) {
        Set<Integer> players = world.getEntitiesWith(PlayerTag.class, VelocityComponent.class, AttackComponent.class);
        for (int playerId : players) {
            float vx = 0f;
            float vy = 0f;
            if (Gdx.input.isKeyPressed(Input.Keys.W)) {
                vy += Constants.PLAYER_SPEED;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.S)) {
                vy -= Constants.PLAYER_SPEED;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.A)) {
                vx -= Constants.PLAYER_SPEED;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.D)) {
                vx += Constants.PLAYER_SPEED;
            }
            world.updateComponent(playerId, new VelocityComponent(vx, vy));
            world.getComponent(playerId, AttackComponent.class).ifPresent(attack -> {
                boolean attackTrigger = Gdx.input.isKeyJustPressed(Input.Keys.SPACE);
                world.updateComponent(playerId, attack.withAttacking(attackTrigger));
            });
        }
    }
}
