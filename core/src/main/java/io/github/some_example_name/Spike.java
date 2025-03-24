package io.github.some_example_name;

import com.badlogic.gdx.math.Rectangle;

public class Spike {
    private float x;
    private float y;
    private float width;
    private float height;
    private float speed;

    public Spike(float x, float y, float width, float height, float speed) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.speed = speed;
    }

    public void update(float deltaTime) {
        x -= speed * 60 * deltaTime;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public Rectangle getEdges() {
        float collisionW = width * 0.2f;
        return new Rectangle(x + collisionW, y, width - (collisionW * 2), height);
    }
}
