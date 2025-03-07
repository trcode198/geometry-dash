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
    private final int maxPlatforms = 5;
    private final float platformTGap = 2.0f;
    private final float[] platformIntervals = {150, 300, 200, 200, 300};

    @Override
    public void create() {
        sr = new ShapeRenderer();
        player = new Player(140, 200, 44);
        platforms = new Array<>();
    }

    @Override
    public void render() {
        ScreenUtils.clear(0.01f, 0f, 0.5f, 1f);
        platformTimer += Gdx.graphics.getDeltaTime();

        if (platforms.size < maxPlatforms && platformTimer >= platformTGap) {
            float platformX;
            if (platforms.size == 0) {
                platformX = Gdx.graphics.getWidth() + platformIntervals[0];
            }
            else {
                platformX = platforms.peek().getX() + platformIntervals[platforms.size];
            }
            platforms.add(new Platform(platformX, 200, 100, 20));
            platformTimer = 0;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            player.jump();
        }
        for (Platform platform : platforms) {
            platform.update();
        }
        player.update();
        player.checkPlatforms(platforms);

        sr.begin(ShapeRenderer.ShapeType.Filled);

        sr.setColor(Color.valueOf("1a0355"));
        sr.rect(0, 0, 1980, 200);

        sr.setColor(Color.RED);
        for (Platform platform : platforms) {
            sr.rect(platform.getX(), platform.getY(), platform.getWidth(), platform.getHeight());
        }

        sr.setColor(255, 140, 0, 255);
        sr.rect(player.getX(), player.getY(), player.getSize(), player.getSize());

        sr.end();

    }

    private float platformTGap() {
        return platformIntervals[Math.min(platforms.size, platformIntervals.length - 1)] / 10f;
    }

    @Override
    public void dispose() {
        sr.dispose();
    }
}
