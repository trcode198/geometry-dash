package io.github.some_example_name;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;

public class Main extends ApplicationAdapter {
    private ShapeRenderer sr;
    private Player player;

    @Override
    public void create() {
        sr = new ShapeRenderer();
        player = new Player(140, 200, 44);
    }

    @Override
    public void render() {
        ScreenUtils.clear(0.01f, 0f, 0.5f, 1f);

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            player.jump();
        }

        player.update();

        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.setColor(255, 140, 0, 255);
        sr.rect(player.getX(), player.getY(), player.getSize(), player.getSize());
        sr.setColor(Color.valueOf("1a0355"));
        sr.rect(0,0,1980,200);
        sr.end();
    }

    @Override
    public void dispose() {
        sr.dispose();
    }
}
