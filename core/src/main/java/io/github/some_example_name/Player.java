package io.github.some_example_name;

import com.badlogic.gdx.utils.Array;

public class Player {
    private int x, y;
    private int size;
    private double velocityY;
    private boolean Jumping;
    private double gravity = -0.876;
    private final int jumpStrength = 15;
    private final int floor;
    private boolean onPlatform;

    public Player(int x, int y, int size) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.floor = y;
        this.velocityY = 0;
        this.Jumping = false;
        this.onPlatform = false;

    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getSize() {
        return size;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void jump() {
        if (!Jumping) {
            Jumping = true;
            velocityY = jumpStrength;
        }
    }

    public void update() {
        if (Jumping) {
            y += velocityY;
            velocityY += gravity;

            if (y <= floor) {
                y = floor;
                Jumping = false;
                velocityY = 0;
            }
        }
    }

    public void checkPlatforms(Array<Platform> platforms) {
        onPlatform = false;

        for (Platform platform : platforms) {
            if (platform.Collision(this)) {
                if (velocityY <= 0 && y + size > platform.getY()) {
                    y = (int) (platform.getY() + platform.getHeight());
                    Jumping = false;
                    velocityY = 0;
                    onPlatform = true;
                    break;
                }
            }
        }

        if (!onPlatform && !Jumping && y > floor) {
            Jumping = true;
            velocityY = 0;
        }
    }
}
