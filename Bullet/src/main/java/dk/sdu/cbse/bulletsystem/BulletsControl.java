package dk.sdu.cbse.bulletsystem;

import dk.sdu.cbse.commonbullet.*;
import dk.sdu.cbse.common.data.*;
import dk.sdu.cbse.common.service.IEntityProcessingService;

public class BulletsControl implements IEntityProcessingService, BulletSPI {
    @Override
    public void process(GameData gameData, World world) {

        for (Entity bullet : world.getEntities(Bullet.class)) {
            double changeX = Math.cos(Math.toRadians(bullet.getRotation()));
            double changeY = Math.sin(Math.toRadians(bullet.getRotation()));
            bullet.setX(bullet.getX() + changeX * 6);
            bullet.setY(bullet.getY() + changeY * 6);

//          //fjerner bullets udenfor skærmen
            float screenWidth = gameData.getDisplayWidth();
            float screenHeight = gameData.getDisplayHeight();
            if((bullet.getX()<0)|| (bullet.getX()>screenWidth)|| (bullet.getY() < 0) || (bullet.getY() > screenHeight)){
                world.removeEntity(bullet);
            }
        }
    }
        @Override
        public Entity createBullet(Entity shooter, GameData gameData){
            Entity bullet = new Bullet();
            bullet.setPolygonCoordinates(1, -1, 1, 1, -1, 1, -1, -1);
            double changeX = Math.cos(Math.toRadians(shooter.getRotation()));
            double changeY = Math.sin(Math.toRadians(shooter.getRotation()));
            bullet.setX(shooter.getX() + changeX * 10);
            bullet.setY(shooter.getY() + changeY * 10);
            bullet.setRotation(shooter.getRotation());
            bullet.setRadius(1);
            bullet.setType("Bullet");
            bullet.setHealth(1);
            return bullet;
        }
    }

