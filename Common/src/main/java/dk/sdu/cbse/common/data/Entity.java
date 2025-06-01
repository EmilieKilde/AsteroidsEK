package dk.sdu.cbse.common.data;
import java.io.Serializable;
import java.util.UUID;

public class Entity implements Serializable, IEntityRemoval {
    private final UUID ID = UUID.randomUUID();
    private double[] polygonCoordinates;
    private double x;
    private double y;
    private double rotation;
    private float radius;
    private String type;
    private int health;

    public String getID() {
        return ID.toString();
    }

    public void setPolygonCoordinates(double... coordinates) {
        this.polygonCoordinates = coordinates.clone();
    }

    public double[] getPolygonCoordinates() {
        return polygonCoordinates != null ? polygonCoordinates.clone() : new double[0];
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getX() {
        return x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getY() {
        return y;
    }

    public void setRotation(double rotation) {
        this.rotation = rotation % 360;
    }

    public double getRotation() {
        return rotation;
    }

    public void setRadius(float radius) {
        this.radius = Math.max(0, radius);
    }

    public float getRadius() {
        return this.radius;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = Math.max(0, health);
    }

    @Override
    public void onRemoval(Entity entity, World world) {
        // Default implementation - to be overridden by entities that need special removal behavior
    }

    @Override
    public String toString() {
        return String.format("%s[id=%s, type=%s, pos=(%.1f,%.1f), health=%d]",
                getClass().getSimpleName(), getID().substring(0, 8), type, x, y, health);
    }
}