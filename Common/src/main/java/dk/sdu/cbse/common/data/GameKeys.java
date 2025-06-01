package dk.sdu.cbse.common.data;

public class GameKeys {
    private static final int NUM_KEYS = 4;
    public static final int UP = 0;
    public static final int LEFT = 1;
    public static final int RIGHT = 2;
    public static final int SPACE = 3;

    private final boolean[] keys = new boolean[NUM_KEYS];
    private final boolean[] previousKeys = new boolean[NUM_KEYS];

    public void update() {
        System.arraycopy(keys, 0, previousKeys, 0, NUM_KEYS);
    }

    public void setKey(int keyIndex, boolean pressed) {
        if (keyIndex >= 0 && keyIndex < NUM_KEYS) {
            keys[keyIndex] = pressed;
        }
    }

    public boolean isDown(int keyIndex) {
        return keyIndex >= 0 && keyIndex < NUM_KEYS && keys[keyIndex];
    }

    public boolean isPressed(int keyIndex) {
        return keyIndex >= 0 && keyIndex < NUM_KEYS &&
                keys[keyIndex] && !previousKeys[keyIndex];
    }
}