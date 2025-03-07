package io.github.some_example_name;

import com.badlogic.gdx.utils.Array;

public class Platform extends Array<Platform> {
    private float x, y;
    private float width, height;
    private float scrollSpd = 7;

    public Platform(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void update() {
        x -= scrollSpd;
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



    public boolean Collision(Player player) {
        return player.getX() < x + width &&
            player.getX() + player.getSize() > x &&
            player.getY() < y + height &&
            player.getY() + player.getSize() > y;
    }


}
