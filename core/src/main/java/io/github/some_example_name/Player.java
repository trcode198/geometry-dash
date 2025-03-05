package io.github.some_example_name;

public class Player {
    private int x, y;
    private int size;
    private double velocityY;
    private boolean isJumping;
    private double gravity = -0.876;
    private final int jumpStrength = 15;
    private final int groundLevel;

    public Player(int x, int y, int size) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.groundLevel = y;
        this.velocityY = 0;
        this.isJumping = false;
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
        if (!isJumping) {
            isJumping = true;
            velocityY = jumpStrength;
        }
    }

    public void update() {
        if (isJumping) {
            y += velocityY;
            velocityY += gravity;
            if (y <= groundLevel) {
                y = groundLevel;
                isJumping = false;
                velocityY = 0;
            }
        }
    }
}
