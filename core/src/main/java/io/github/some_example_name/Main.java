package io.github.some_example_name;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;

public class Main extends ApplicationAdapter {
    private ShapeRenderer sr;
    private Player player;
    private Array<Platform> platforms;
    private float platformTimer = 0;
    private final float platformInterval = 2.0f;

    @Override
    public void create() {
        sr = new ShapeRenderer();
        player = new Player(140, 200, 44);
        platforms = new Array<>();
        // Add initial ground platform
        platforms.add(new Platform(0, 150, Gdx.graphics.getWidth(), 20));
    }

    @Override
    public void render() {
        ScreenUtils.clear(0.01f, 0f, 0.5f, 1f);
        float deltaTime = Gdx.graphics.getDeltaTime();

        // Update platform spawning
        platformTimer += deltaTime;
        if (platformTimer >= platformInterval) {
            platformTimer = 0;
            platforms.add(new Platform(Gdx.graphics.getWidth(), 200 + (float)(Math.random() * 200), 200, 20));
        }

        // Update platforms and remove off-screen ones
        for (int i = platforms.size - 1; i >= 0; i--) {
            Platform platform = platforms.get(i);
            platform.update();
            if (platform.getX() + platform.getWidth() < 0) {
                platforms.removeIndex(i);
            }
        }

        // Handle player input and update
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            player.jump();
        }

        // Update player and check collisions
        player.update();
        for (Platform platform : platforms) {
            player.checkPlatforms(platform);
        }

        // Draw everything
        sr.begin(ShapeRenderer.ShapeType.Filled);

        // Draw the base floor first
        sr.setColor(Color.valueOf("1a0355"));
        sr.rect(0, 0, 1980, 200);

        // Draw platforms
        sr.setColor(Color.valueOf("1a0355"));
        for (Platform platform : platforms) {
            sr.rect(platform.getX(), platform.getY(), platform.getWidth(), platform.getHeight());
        }

        // Draw player
        sr.setColor(255, 140, 0, 255);
        sr.rect(player.getX(), player.getY(), player.getSize(), player.getSize());

        sr.end();
    }

    @Override
    public void dispose() {
        sr.dispose();
    }
}
