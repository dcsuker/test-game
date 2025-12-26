package com.demo.rpg.ecs;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class World {
    private int nextEntityId = 1;
    private final Map<Class<? extends Component>, Map<Integer, Component>> components = new HashMap<>();
    private final List<SystemBase> systems = new ArrayList<>();
    private final Set<Integer> toRemove = new HashSet<>();

    public int createEntity() {
        return nextEntityId++;
    }

    public <T extends Component> void addComponent(int entityId, T component) {
        components.computeIfAbsent(component.getClass(), key -> new HashMap<>()).put(entityId, component);
    }

    public <T extends Component> Optional<T> getComponent(int entityId, Class<T> componentClass) {
        Map<Integer, Component> storage = components.get(componentClass);
        if (storage == null) {
            return Optional.empty();
        }
        Component component = storage.get(entityId);
        if (componentClass.isInstance(component)) {
            return Optional.of(componentClass.cast(component));
        }
        return Optional.empty();
    }

    public <T extends Component> void updateComponent(int entityId, T component) {
        Map<Integer, Component> storage = components.computeIfAbsent(component.getClass(), key -> new HashMap<>());
        storage.put(entityId, component);
    }

    public void addSystem(SystemBase system) {
        systems.add(system);
    }

    public void update(float deltaTime) {
        for (SystemBase system : systems) {
            system.update(this, deltaTime);
        }
        if (!toRemove.isEmpty()) {
            toRemove.forEach(this::removeEntity);
            toRemove.clear();
        }
    }

    public void render(ShapeRenderer renderer) {
        for (SystemBase system : systems) {
            system.render(this, renderer);
        }
    }

    public Set<Integer> getEntitiesWith(Class<? extends Component>... componentClasses) {
        Set<Integer> result = new HashSet<>();
        if (componentClasses.length == 0) {
            return result;
        }
        Class<? extends Component> first = componentClasses[0];
        Map<Integer, Component> firstStorage = components.get(first);
        if (firstStorage == null) {
            return result;
        }
        result.addAll(firstStorage.keySet());
        for (int i = 1; i < componentClasses.length; i++) {
            Map<Integer, Component> storage = components.get(componentClasses[i]);
            if (storage == null) {
                result.clear();
                break;
            }
            result.retainAll(storage.keySet());
            if (result.isEmpty()) {
                break;
            }
        }
        return result;
    }

    public void markForRemoval(int entityId) {
        toRemove.add(entityId);
    }

    private void removeEntity(int entityId) {
        components.values().forEach(storage -> storage.remove(entityId));
    }
}
