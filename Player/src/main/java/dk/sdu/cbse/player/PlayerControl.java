package dk.sdu.cbse.player;

import dk.sdu.cbse.common.service.IEntityProcessingService;
import dk.sdu.cbse.commonbullet.BulletSPI;
import dk.sdu.cbse.common.data.*;

import java.io.IOException;
import java.util.Collection;
import java.util.ServiceLoader;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class PlayerControl implements IEntityProcessingService {

    private static final double ROTATION_SPEED = 5.0;
    private static final double MOVEMENT_SPEED = 1.0;
    private static final int BOUNDARY_MARGIN = 1;

    private final PlayerPlugin playerRespawn;

    public PlayerControl() {
        this.playerRespawn = new PlayerPlugin();
    }

    @Override
    public void process(GameData gameData, World world) {
        Collection<Entity> players = world.getEntities(Player.class);

        for (Entity player : players) {
            handleInput(gameData, world, player);
            enforceBoundaries(gameData, player);
        }

        // Respawn player if none exists
        if (players.isEmpty()) {
            respawnPlayer(gameData, world);
        }
    }

    private void handleInput(GameData gameData, World world, Entity player) {
        GameKeys keys = gameData.getKeys();

        if (keys.isDown(GameKeys.LEFT)) {
            player.setRotation(player.getRotation() - ROTATION_SPEED);
        }

        if (keys.isDown(GameKeys.RIGHT)) {
            player.setRotation(player.getRotation() + ROTATION_SPEED);
        }

        if (keys.isDown(GameKeys.UP)) {
            movePlayerForward(player);
        }

        if (keys.isDown(GameKeys.SPACE)) {
            fireBullet(player, gameData, world);
        }
    }

    private void movePlayerForward(Entity player) {
        double radians = Math.toRadians(player.getRotation());
        double changeX = Math.cos(radians) * MOVEMENT_SPEED;
        double changeY = Math.sin(radians) * MOVEMENT_SPEED;

        player.setX(player.getX() + changeX);
        player.setY(player.getY() + changeY);
    }

    private void enforceBoundaries(GameData gameData, Entity player) {
        double x = player.getX();
        double y = player.getY();

        if (x < BOUNDARY_MARGIN) {
            player.setX(BOUNDARY_MARGIN);
        } else if (x > gameData.getDisplayWidth() - BOUNDARY_MARGIN) {
            player.setX(gameData.getDisplayWidth() - BOUNDARY_MARGIN);
        }

        if (y < BOUNDARY_MARGIN) {
            player.setY(BOUNDARY_MARGIN);
        } else if (y > gameData.getDisplayHeight() - BOUNDARY_MARGIN) {
            player.setY(gameData.getDisplayHeight() - BOUNDARY_MARGIN);
        }
    }

    private void fireBullet(Entity player, GameData gameData, World world) {
        getBulletSPIs().stream()
                .findFirst()
                .ifPresent(spi -> world.addEntity(spi.createBullet(player, gameData)));
    }

    private void respawnPlayer(GameData gameData, World world) {
        try {
            gameData.increasePlayerDeaths();
        } catch (IOException e) {
            System.err.println("Failed to update player deaths: " + e.getMessage());
        }
        playerRespawn.start(gameData, world);
    }

    private Collection<? extends BulletSPI> getBulletSPIs() {
        return ServiceLoader.load(BulletSPI.class)
                .stream()
                .map(ServiceLoader.Provider::get)
                .collect(Collectors.toList());
    }
}