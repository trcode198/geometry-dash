package io.github.some_example_name;

import com.badlogic.gdx.graphics.Color;

public class Trail {
    private float x;
    private float y;
    private float size;
    private float rotation;
    private float alpha;
    private static final float low_taper_fade = 2.5f;

    public Trail(float x, float y, float size, float rotation) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.rotation = rotation;
        this.alpha = 1f;
    }

    public void update(float deltaTime) {
        alpha -= low_taper_fade * deltaTime;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getSize() {
        return size;
    }

    public float getRotation() {
        return rotation;
    }

    public float getAlpha() {
        return alpha;
    }

    public boolean isExpired() {
        return alpha <= 0;
    }
}
