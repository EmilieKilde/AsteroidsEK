package dk.sdu.cbse.enemy;

import dk.sdu.cbse.common.data.Entity;
import dk.sdu.cbse.common.data.GameData;
import dk.sdu.cbse.common.data.World;
import dk.sdu.cbse.common.service.IEntityProcessingService;
import dk.sdu.cbse.player.Player;
import dk.sdu.cbse.commonbullet.Bullet;

import java.util.Random;

public class EnemyProcess implements IEntityProcessingService {
    private Random random = new Random();
    @Override
    public void process(GameData gameData, World world){
        for(Entity enemy : world.getEntities(Enemy.class)){
            Entity player = world.getEntities(Player.class).stream().findFirst().orElse(null);
            if (player != null) {
                double dx = player.getX() - enemy.getX();
                double dy = player.getY() - enemy.getY();
                double angle = Math.atan2(dy, dx);
                enemy.setRotation(Math.toDegrees(angle));
                double changeX = Math.cos(angle);
                double changeY = Math.sin(angle);
                enemy.setX(enemy.getX() + changeX * 0.5);
                enemy.setY(enemy.getY() + changeY * 0.5);
            }
            if (enemy.getX() < 0) {
                enemy.setX(enemy.getX() - gameData.getDisplayWidth());
            }

            if (enemy.getX() > gameData.getDisplayWidth()) {
                enemy.setX(enemy.getX() % gameData.getDisplayWidth());
            }

            if (enemy.getY() < 0) {
                enemy.setY(enemy.getY() - gameData.getDisplayHeight());
            }

            if (enemy.getY() > gameData.getDisplayHeight()) {
                enemy.setY(enemy.getY() % gameData.getDisplayHeight());
            }
            if(random.nextInt(100)<1){
                shoot(enemy, gameData, world);
            }
        }
    }
    private void shoot(Entity enemy, GameData gameData, World world) {
        Entity bullet = new Bullet();
        bullet.setPolygonCoordinates(2, -2, 2, 2, -2, 2, -2, -2);
        bullet.setX(enemy.getX());
        bullet.setY(enemy.getY());
        bullet.setRotation(enemy.getRotation());
        bullet.setRadius(1);

        double changeX = Math.cos(Math.toRadians(bullet.getRotation()));
        double changeY = Math.sin(Math.toRadians(bullet.getRotation()));
        bullet.setX(bullet.getX() + changeX * 40);
        bullet.setY(bullet.getY() + changeY * 40);

        world.addEntity(bullet);
    }
}
