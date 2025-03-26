package io.github.some_example_name;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

public class Player {
    private final Array<Object> trails;
    private float x, y;
    private int size;
    private float velocityY;
    private boolean isJumping;
    private float gravity = -0.876f;
    private final float jumpVel = 15f;
    private Platform currentPlatform;
    private float rotation = 0;
    private float rotationSpeed = 0;

    public Player(int x, int y, int size) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.velocityY = 0;
        this.isJumping = false;
        this.trails = new Array<>();
    }

    public float getRotation() {
        return rotation;
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
            velocityY = jumpVel;
            currentPlatform = null;

            rotationSpeed = -2.0f;
        }
    }

    public void update(float deltaTime, Array<Platform> platforms, int groundLevel) {
        if (isJumping) {
            y += velocityY;
            velocityY += gravity * deltaTime * 60;

            if (velocityY > 0) {
                rotationSpeed = -5.0f;
            } else {
                rotationSpeed = -Math.min(8.0f, Math.abs(velocityY) * 0.7f);
            }

            rotation += rotationSpeed * deltaTime * 60;

            while (rotation < 0) rotation += 360;
            while (rotation >= 360) rotation -= 360;

            if (y <= groundLevel) {
                y = groundLevel;
                isJumping = false;
                velocityY = 0;
                currentPlatform = null;
                rotation = 0;
            } else {
                checkPlatformCollisions(platforms);
            }
        } else if (currentPlatform != null) {
            y = currentPlatform.getY() + currentPlatform.getHeight();

            if (x + size < currentPlatform.getX() || x > currentPlatform.getX() + currentPlatform.getWidth()) {
                isJumping = true;
                velocityY = 0;
                currentPlatform = null;
            } else {
                rotation = 0;
            }
        } else {
            rotation = 0;
        }
    }

    private void checkPlatformCollisions(Array<Platform> platforms) {
        if (velocityY >= 0) return;

        Rectangle playerBounds = getEdges();

        for (Platform platform : platforms) {
            Rectangle platformBounds = platform.getEdges();

            if (playerBounds.overlaps(platformBounds)) {
                float playerBottom = y;
                float platformTop = platform.getY() + platform.getHeight();
                float prevPlayerBottom = playerBottom - velocityY;

                if (prevPlayerBottom >= platformTop) {
                    y = platformTop;
                    isJumping = false;
                    velocityY = 0;
                    currentPlatform = platform;
                    rotation = 0;
                    break;
                }
            }
        }
    }
}
