package io.github.some_example_name;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

public class Player {
    private float x, y;
    private int size;
    private float velocityY;
    private boolean isJumping;
    private float gravity = -0.876f;
    private final float jumpVel = 30f;
    private Platform currentPlatform;
    private float rotation = 0;
    private float rotationSpeed = 0;

    public Player(int x, int y, int size) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.velocityY = 0;
        this.isJumping = false;
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
        // Use the full square bounds for collision detection
        // The visual rotation doesn't affect collision
        return new Rectangle(x, y, size, size);
    }

    public void jump() {
        if (!isJumping) {
            isJumping = true;
            velocityY = jumpVel;
            currentPlatform = null;

            // Start with a backwards rotation
            rotationSpeed = -2.0f;
        }
    }

    public void update(float deltaTime, Array<Platform> platforms, int groundLevel) {
        if (isJumping) {
            y += velocityY;
            velocityY += gravity * deltaTime * 60;

            // Adjust rotation speed based on vertical velocity
            // Reverse direction for correct forward flip
            if (velocityY > 0) {
                rotationSpeed = -5.0f;  // Moving up - rotate backwards
            } else {
                rotationSpeed = -Math.min(8.0f, Math.abs(velocityY) * 0.5f);  // Falling - rotate backwards faster
            }

            // Apply rotation
            rotation += rotationSpeed * deltaTime * 60;

            // Keep rotation between 0 and 360
            while (rotation < 0) rotation += 360;
            while (rotation >= 360) rotation -= 360;

            if (y <= groundLevel) {
                y = groundLevel;
                isJumping = false;
                velocityY = 0;
                currentPlatform = null;
                rotation = 0;  // Immediately reset rotation on ground
            } else {
                checkPlatformCollisions(platforms);
            }
        } else if (currentPlatform != null) {
            y = currentPlatform.getY() + currentPlatform.getHeight();

            // Check if player should fall off platform
            if (x + size < currentPlatform.getX() || x > currentPlatform.getX() + currentPlatform.getWidth()) {
                isJumping = true;
                velocityY = 0;
                currentPlatform = null;
            } else {
                rotation = 0;  // Reset rotation when on platform
            }
        } else {
            rotation = 0;  // Reset rotation when on ground
        }
    }

    private void checkPlatformCollisions(Array<Platform> platforms) {
        // Only check for platform collisions if we're falling
        if (velocityY >= 0) return;

        Rectangle playerBounds = getEdges();

        for (Platform platform : platforms) {
            Rectangle platformBounds = platform.getEdges();

            if (playerBounds.overlaps(platformBounds)) {
                // Calculate vertical positions
                float playerBottom = y;
                float platformTop = platform.getY() + platform.getHeight();
                float prevPlayerBottom = playerBottom - velocityY;

                // Only land if the player was above the platform in the previous frame
                if (prevPlayerBottom >= platformTop) {
                    y = platformTop;
                    isJumping = false;
                    velocityY = 0;
                    currentPlatform = platform;
                    rotation = 0;  // Reset rotation immediately on landing
                    break;
                }
            }
        }
    }
}
