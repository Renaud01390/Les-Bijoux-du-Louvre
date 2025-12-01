package com.lesbijouxdulouvre.team3.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;
import com.lesbijouxdulouvre.team3.managers.GameStateManager;
import com.lesbijouxdulouvre.team3.managers.LevelManager;

import levels.*;

public class MainMenu extends GameState {

    private Texture backgroundTexture;
    private Texture playButtonTexture;
    private Rectangle playButtonBounds;
    private OrthographicCamera Camm;
    private TextureRegion playButtonRegion;
    private Animation<TextureRegion> titleAnimation;
    private float stateTime;
    private Texture titleTexture;

    private static final int BUTTON_WIDTH = 250;
    private static final int BUTTON_HEIGHT = 250;

    public MainMenu(GameStateManager gsm) {
        super(gsm);
        
        this.stateTime = 0f;
        titleTexture = new Texture("element/titlefinal.png");
        TextureRegion[][] tmp = TextureRegion.split(titleTexture, 72, 32);
        int totalFrames = tmp.length * tmp[0].length;
        TextureRegion[] animFrames = new TextureRegion[totalFrames];
        int index = 0;
        for (int row = 0; row < tmp.length; row++) {
            for (int col = 0; col < tmp[row].length; col++) {
                animFrames[index++] = tmp[row][col];
            }
        }
        titleAnimation = new Animation<>(0.1f, animFrames);
        titleAnimation.setPlayMode(Animation.PlayMode.LOOP);
        
        Camm = new OrthographicCamera();
        Camm.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Camm.update();
        
        try {
            backgroundTexture = new Texture(Gdx.files.internal("background.png"));
            playButtonTexture = new Texture(Gdx.files.internal("play.png"));
            playButtonRegion = new TextureRegion(playButtonTexture, 0, 0, 9, 9);
        } catch (Exception e) {
            Gdx.app.error("MainMenu", "Erreur: Impossible de charger un asset.", e);
        }

        int x = (Gdx.graphics.getWidth() / 2) - (BUTTON_WIDTH / 2);
        int y = (Gdx.graphics.getHeight() / 2) - (BUTTON_HEIGHT / 2);

        playButtonBounds = new Rectangle(x, y, BUTTON_WIDTH, BUTTON_HEIGHT);
    }

    @Override
    public void handleInput() {
        if (Gdx.input.justTouched()) {
            float x = Gdx.input.getX();
            float y = Gdx.graphics.getHeight() - Gdx.input.getY();

            if (playButtonBounds.contains(x, y)) {
                gsm.setGameState(LevelManager.RandomLevel(gsm));
            }
        }
    }

    @Override
    public void update(float dt) {
        stateTime += dt;
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        ScreenUtils.clear(0, 0, 0, 1);

        com.badlogic.gdx.math.Matrix4 identityMatrix = new com.badlogic.gdx.math.Matrix4();
        identityMatrix.setToOrtho2D(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        sb.setProjectionMatrix(identityMatrix);
        
        sb.begin();

        if (backgroundTexture != null) {
            sb.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        }
        
        TextureRegion frame = titleAnimation.getKeyFrame(stateTime);
        float scale = 10f;
        float width = frame.getRegionWidth() * scale;
        float height = frame.getRegionHeight() * scale;
        float x = (Gdx.graphics.getWidth() - width) / 2;
        float y = (Gdx.graphics.getHeight() - height) / 1;
        sb.draw(frame, x, y, width, height);

        if (playButtonRegion != null) {
            sb.draw(playButtonRegion,
                    playButtonBounds.x,
                    playButtonBounds.y,
                    playButtonBounds.width,
                    playButtonBounds.height);
        }

        sb.end();
    }
    
    @Override
    public void dispose() {
        if (backgroundTexture != null) {
            backgroundTexture.dispose();
        }
        if (playButtonTexture != null) {
            playButtonTexture.dispose();
        }
        if (titleTexture != null) {
            titleTexture.dispose();
        }
    }
}