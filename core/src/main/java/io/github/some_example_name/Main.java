package io.github.some_example_name;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.math.MathUtils;

public class Main extends ApplicationAdapter {
    private ShapeRenderer sr;
    private Player player;
    private Array<Platform> platforms;
    private float platformTimer;
    private final float platformspawn = 1f;
    private final int screenW = 1980;
    private final int screenH = 1080;
    private final int groundH = 200;

    @Override
    public void create() {
        sr = new ShapeRenderer();
        player = new Player(140, groundH, 44);

        platforms = new Array<>();
        platformTimer = 0;
        platforms.add(new Platform(screenW, 300, 120, 20, 4));
    }

    @Override
    public void render() {
        float deltaTime = Gdx.graphics.getDeltaTime();
        ScreenUtils.clear(0.01f, 0f, 0.5f, 1f);

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            player.jump();
        }

        platformTimer += deltaTime;
        if (platformTimer >= platformspawn) {
            platformTimer = 0;
            int platformHeight = MathUtils.random(groundH + 50, (screenH / 2)-100);
            int platformWidth = MathUtils.random(80, 200);
            float platformSpeed = 4f;

            platforms.add(new Platform(screenW, platformHeight, platformWidth, 20, platformSpeed));
        }

        for (int i = platforms.size - 1; i >= 0; i--) {
            Platform platform = platforms.get(i);
            platform.update(deltaTime);

            if (platform.getX() + platform.getWidth() < 0) {
                platforms.removeIndex(i);
            }
        }
        player.update(deltaTime, platforms, groundH);

        sr.begin(ShapeRenderer.ShapeType.Filled);
        // player
        sr.setColor(255, 140, 0, 255);
        sr.rect(player.getX(), player.getY(), player.getSize(), player.getSize());
        // ground
        sr.setColor(Color.valueOf("ffffff"));
        sr.rect(0, 0, screenW, groundH);
        sr.setColor(Color.valueOf("0a0644"));
        sr.rect(0, 0, screenW, groundH-1);
        // plat
        sr.setColor(Color.valueOf("55c4ee"));
        for (Platform platform : platforms) {
            sr.rect(platform.getX(), platform.getY(), platform.getWidth(), platform.getHeight());
        }
        sr.end();
    }

    @Override
    public void dispose() {
        sr.dispose();
    }
}

