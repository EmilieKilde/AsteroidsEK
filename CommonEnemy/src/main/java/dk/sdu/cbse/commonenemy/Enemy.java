package dk.sdu.cbse.commonenemy;
 import dk.sdu.cbse.common.data.Entity;
public class Enemy extends Entity {
    private static final String ENEMY_TYPE = "Enemy";
    private static final int DEFAULT_HEALTH = 3;
    private static final float DEFAULT_RADIUS = 8.0f;

    public Enemy() {
        super();
        setType(ENEMY_TYPE);
        setHealth(DEFAULT_HEALTH);
        setRadius(DEFAULT_RADIUS);
        setDefaultShape();
    }

    private void setDefaultShape() {
        setPolygonCoordinates(-5, -5, 10, 0, -5, 5);
    }
}