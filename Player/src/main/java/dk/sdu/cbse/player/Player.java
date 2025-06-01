package dk.sdu.cbse.player;

import dk.sdu.cbse.common.data.Entity;
public class Player extends Entity{
    private static final String PLAYER_TYPE = "Player";
    private static final int DEFAULT_HEALTH = 5;
    private static final float DEFAULT_RADIUS = 8.0f;

    public Player() {
        super();
        setType(PLAYER_TYPE);
        setHealth(DEFAULT_HEALTH);
        setRadius(DEFAULT_RADIUS);
        setDefaultShape();
    }

    private void setDefaultShape() {
        setPolygonCoordinates(-5, -5, 10, 0, -5, 5);
    }
}
