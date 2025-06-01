package dk.sdu.cbse.commonbullet;

import dk.sdu.cbse.common.data.Entity;

public class Bullet extends Entity {
    private static final String BULLET_TYPE = "Bullet";
    private static final int DEFAULT_HEALTH = 1;
    private static final float DEFAULT_RADIUS = 1.0f;

    public Bullet() {
        super();
        setType(BULLET_TYPE);
        setHealth(DEFAULT_HEALTH);
        setRadius(DEFAULT_RADIUS);
        setDefaultShape();
    }

    private void setDefaultShape() {
        setPolygonCoordinates(1, -1, 1, 1, -1, 1, -1, -1);
    }
}
