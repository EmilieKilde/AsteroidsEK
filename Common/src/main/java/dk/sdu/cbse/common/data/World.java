package dk.sdu.cbse.common.data;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class World {
    private final Map<String, Entity> entityMap = new ConcurrentHashMap<>();

    public String addEntity(Entity entity) {
        if (entity != null) {
            entityMap.put(entity.getID(), entity);
            return entity.getID();
        }
        return null;
    }

    public void removeEntity(String entityID) {
        if (entityID != null) {
            entityMap.remove(entityID);
        }
    }

    public void removeEntity(Entity entity) {
        if (entity != null) {
            entityMap.remove(entity.getID());
        }
    }

    public Collection<Entity> getEntities() {
        return new ArrayList<>(entityMap.values());
    }

    @SafeVarargs
    public final <E extends Entity> List<Entity> getEntities(Class<E>... entityTypes) {
        if (entityTypes == null || entityTypes.length == 0) {
            return new ArrayList<>();
        }

        Set<Class<E>> typeSet = new HashSet<>(Arrays.asList(entityTypes));
        return getEntities().stream()
                .filter(entity -> typeSet.contains(entity.getClass()))
                .collect(Collectors.toList());
    }

    public Entity getEntity(String ID) {
        return entityMap.get(ID);
    }

    public int getEntityCount() {
        return entityMap.size();
    }

    public void clear() {
        entityMap.clear();
    }
}