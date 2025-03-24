package io.github.some_example_name;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.math.MathUtils;

import static com.badlogic.gdx.math.MathUtils.random;

public class Main extends ApplicationAdapter {
    private ShapeRenderer sr;
    private Player player;
    private Array<Platform> platforms;
    private Array<Spike> spikes;
    private float platformTimer;
    private float spikeTimer;
    private float spikespawn = 1.4f;
    private final float platformspawn = 1f;
    private final int screenW = 1980;
    private final int screenH = 1080;
    private final int groundH = 200;
    private final int playerStartX = 140;
    private final int playerStartY = 200;
    private Matrix4 defaultMatrix;

    @Override
    public void create() {
        sr = new ShapeRenderer();
        resetGame();
    }

    @Override
    public void render() {
        float deltaTime = Gdx.graphics.getDeltaTime();
        ScreenUtils.clear(0.01f, 0f, 0.5f, 1f);

        handleInput();
        updatePlatforms(deltaTime);
        updateSpikes(deltaTime);
        player.update(deltaTime, platforms, groundH);
        checkSpikeCollisions();

        drawGame();
    }
    private void resetGame() {
        player = new Player(playerStartX, playerStartY, 44);

        platforms = new Array<>();
        spikes = new Array<>();
        platformTimer = 0;
        spikeTimer = 0;

        platforms.add(new Platform(screenW, 300, 120, 20, 5));
    }


    private void updateSpikes(float deltaTime) {
        spikeTimer += deltaTime;
        if (spikeTimer > spikespawn) {
            spawnNewSpike();
        }
        for (int i = spikes.size -1; i >= 0; i--) {
            Spike spike = spikes.get(i);
            spike.update(deltaTime);

            if (spike.getX() + spike.getWidth() < 0) {
                spikes.removeIndex(i);
            }
        }
    }

    private void spawnNewSpike() {
        spikeTimer = 0;
        if ((random.nextInt(1)) < 0.6f ) {
            int spikeWidth = MathUtils.random(30, 40);
            int spikeHeight = MathUtils.random(30, 50);
            float spikeSpeed = 5f;
            spikes.add(new Spike(screenW, groundH, spikeWidth, spikeHeight, spikeSpeed));
        }
    }

    private void checkSpikeCollisions() {
        for (Spike spike : spikes) {
            if (player.getEdges().overlaps(spike.getEdges())) {
                resetGame();
                return;
            }
        }
    }

    private void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            player.jump();
        }
    }

    private void updatePlatforms(float deltaTime) {
        platformTimer += deltaTime;
        if (platformTimer >= platformspawn) {
            spawnNewPlatform();
        }

        for (int i = platforms.size - 1; i >= 0; i--) {
            Platform platform = platforms.get(i);
            platform.update(deltaTime);

            if (platform.getX() + platform.getWidth() < 0) {
                platforms.removeIndex(i);
            }
        }
    }

    private void spawnNewPlatform() {
        platformTimer = 0;
        int platformHeight = random(groundH + 50, (screenH / 2)-100);
        int platformWidth = random(120, 210);
        float platformSpeed = 5f;

        platforms.add(new Platform(screenW, platformHeight, platformWidth, 20, platformSpeed));
    }



    private void drawGame() {
        sr.begin(ShapeRenderer.ShapeType.Filled);

        drawGround();
        drawPlatforms();
        drawPlayer();
        drawSpikes();

        sr.end();
    }

    private void drawGround() {
        sr.setColor(Color.valueOf("ffffff"));
        sr.rect(0, 0, screenW, groundH);
        sr.setColor(Color.valueOf("0a0644"));
        sr.rect(0, 0, screenW, groundH-1);
    }

    private void drawPlatforms() {
        sr.setColor(Color.valueOf("55c4ee"));
        for (Platform platform : platforms) {
            sr.rect(platform.getX(), platform.getY(), platform.getWidth(), platform.getHeight());
        }
    }

    private void drawSpikes() {
        sr.setColor(Color.valueOf("ff0000"));
        for (Spike spike : spikes) {
            float x = spike.getX();
            float y = spike.getY();
            float width = spike.getWidth();
            float height = spike.getHeight();
            sr.triangle(
                x, y,
                x+width, y,
                x+(width/2), y + height);
        }
    }

    private void drawPlayer() {
        defaultMatrix = sr.getTransformMatrix().cpy();

        float playerCenterX = player.getX() + player.getSize() / 2;
        float playerCenterY = player.getY() + player.getSize() / 2;
        float size = player.getSize();

        Matrix4 playerMatrix = createPlayerMatrix(playerCenterX, playerCenterY, player.getRotation());
        sr.setTransformMatrix(playerMatrix);

        drawPlayerSquare(size);

        sr.setTransformMatrix(defaultMatrix);
    }

    private Matrix4 createPlayerMatrix(float centerX, float centerY, float rotation) {
        Matrix4 matrix = new Matrix4();
        matrix.setToTranslation(centerX, centerY, 0);
        matrix.rotate(0, 0, 1, rotation);
        return matrix;
    }

    private void drawPlayerSquare(float size) {
        sr.setColor(255, 140, 0, 255);

        sr.triangle(
            -size/2, -size/2,
            size/2, -size/2,
            size/2, size/2
        );

        sr.triangle(
            -size/2, -size/2,
            size/2, size/2,
            -size/2, size/2
        );
    }

    @Override
    public void dispose() {
        sr.dispose();
    }
}
