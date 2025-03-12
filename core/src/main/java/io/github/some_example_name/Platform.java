package io.github.some_example_name;

import com.badlogic.gdx.math.Rectangle;

public class Platform {
    private float x;
    private float y;
    private float width;
    private float height;
    private float speed;

    public Platform(float x, float y, float width, float height, float speed) {
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
        return new Rectangle(x, y, width, height);
    }
}

