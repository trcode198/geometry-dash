package io.github.some_example_name;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

public class Player {
    private float x, y;
    private int size;
    private float velocityY;
    private boolean isJumping;
    private float gravity = -0.876f;
    private final float jumpStrength = 15f;
    private Platform currentPlatform;

    public Player(int x, int y, int size) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.velocityY = 0;
        this.isJumping = false;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public int getSize() {
        return size;
    }

    public Rectangle getEdges() {
        return new Rectangle(x, y, size, size);
    }

    public void jump() {
        if (!isJumping) {
            isJumping = true;
            velocityY = jumpStrength;
            currentPlatform = null;
        }
    }

    public void update(float deltaTime, Array<Platform> platforms, int groundLevel) {
        if (isJumping) {
            y += velocityY;
            velocityY += gravity * deltaTime * 60;

            if (y <= groundLevel) {
                y = groundLevel;
                isJumping = false;
                velocityY = 0;
                currentPlatform = null;
            } else {
                checkPlatformCollisions(platforms);
            }
        } else if (currentPlatform != null) {
            y = currentPlatform.getY() + currentPlatform.getHeight();

            if (x + size < currentPlatform.getX() || x > currentPlatform.getX() + currentPlatform.getWidth()) {
                isJumping = true;
                velocityY = 0;
                currentPlatform = null;
            }
        }
    }

    private void checkPlatformCollisions(Array<Platform> platforms) {
        Rectangle playerBounds = getEdges();

        for (Platform platform : platforms) {
            Rectangle platformBounds = platform.getEdges();

            if (velocityY < 0 && playerBounds.overlaps(platformBounds)) {
                float playerBottom = y;
                float platformTop = platform.getY() + platform.getHeight();

                if (playerBottom - velocityY >= platformTop && playerBottom < platformTop + 10) {
                    y = platformTop;
                    isJumping = false;
                    velocityY = 0;
                    currentPlatform = platform;
                    break;
                }
            }
        }
    }
}
