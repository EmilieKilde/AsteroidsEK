package dk.sdu.cbse.collision;

import dk.sdu.cbse.common.data.GameData;
import dk.sdu.cbse.common.data.World;
import dk.sdu.cbse.common.data.Entity;
import dk.sdu.cbse.common.service.IPostEntityProcessingService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CollisionDetect implements IPostEntityProcessingService {
    public CollisionDetect(){
    }

    @Override
    public void process(GameData gameData, World world) {
        //midlertidig liste af alle entities
        List<Entity> allEntities = new ArrayList<>(world.getEntities());

        //iterér igennem all entities
        for (int i = 0; i< allEntities.size(); i++) {
            Entity entity1 = allEntities.get(i);
            for (int j = i + 1; j < allEntities.size(); j++) {
                Entity entity2 = allEntities.get(j);

                if (collision(entity1, entity2)) {
                    world.removeEntity(entity1);
                    world.removeEntity(entity2);
                }
            }
        }
    }
    private boolean collision(Entity entity1, Entity entity2){
        float dx = (float) entity1.getX()-(float)entity2.getX();
        float dy = (float)entity1.getY()-(float)entity2.getY();
        float distance = (dx*dy)+(dy*dy);
        return distance < (entity1.getRadius()+entity2.getRadius());
    }
}
