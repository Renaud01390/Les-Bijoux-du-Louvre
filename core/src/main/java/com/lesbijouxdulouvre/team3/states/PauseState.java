package com.lesbijouxdulouvre.team3.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.lesbijouxdulouvre.team3.managers.GameStateManager;

import levels.*;

public class PauseState extends GameState {

    private Texture btnReprendre;
    private Texture btnQuitter;

    private Rectangle rReprendre;
    private Rectangle rQuitter;

    private ShapeRenderer shape;
    private BitmapFont font;

    public PauseState(GameStateManager gsm) {
        super(gsm);

        btnReprendre = new Texture("ui/reprendre.png");
        btnQuitter = new Texture("ui/quitter.png");

        float bw = 300;
        float bh = 85;
        float cx = VIEWPORT_WIDTH / 2f - bw / 2f;
        float cy = VIEWPORT_HEIGHT / 2f + 80;

        rReprendre = new Rectangle(cx, cy, bw, bh);
        rQuitter = new Rectangle(cx, cy - 260, bw, bh);

        shape = new ShapeRenderer();
        font = new BitmapFont();
        font.getData().setScale(1.5f);
    }

   @Override
public void handleInput() {
    if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
        gsm.pop();
    }

    if (Gdx.input.justTouched()) {
        com.badlogic.gdx.math.Vector3 worldCoords = cam.unproject(
            new com.badlogic.gdx.math.Vector3(Gdx.input.getX(), Gdx.input.getY(), 0)
        );
        
        float mx = worldCoords.x;
        float my = worldCoords.y;

        if (rReprendre.contains(mx, my)) {
            gsm.pop();
        }

        if (rQuitter.contains(mx, my)) {
            Gdx.app.exit();
        }
    }
}

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {

        shape.setProjectionMatrix(cam.combined);
        shape.begin(ShapeRenderer.ShapeType.Filled);
        shape.setColor(new Color(0, 0, 0, 0.55f));
        shape.rect(0, 0, VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
        shape.end();

        sb.setProjectionMatrix(cam.combined);
        sb.begin();

        sb.draw(btnReprendre, rReprendre.x, rReprendre.y, rReprendre.width, rReprendre.height);
        sb.draw(btnQuitter, rQuitter.x, rQuitter.y, rQuitter.width, rQuitter.height);

        sb.end();
    }

    @Override
    public void dispose() {
        btnReprendre.dispose();
        btnQuitter.dispose();
        shape.dispose();
        font.dispose();
    }
}