package com.lesbijouxdulouvre.team3;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.lesbijouxdulouvre.team3.managers.GameStateManager;
import levels.*;
import com.lesbijouxdulouvre.team3.states.MainMenu;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class MainGame extends ApplicationAdapter {
    private SpriteBatch batch;
    private GameStateManager gsm;
    private SpriteBatch sb;
    private Music music;


    @Override
    public void create() {
        this.music = Gdx.audio.newMusic(Gdx.files.internal("sound/Stolethediamond.mp3"));
        this.gsm = new GameStateManager();
        this.sb = new SpriteBatch();
        this.gsm.push(new MainMenu(this.gsm));
        music.setLooping(true);
        music.setVolume(0.5f);
        music.play();  
    }

    @Override
    public void render() {
        this.gsm.update(Gdx.graphics.getDeltaTime());
        this.gsm.render(this.sb);
    }

    @Override
    public void dispose() {
        this.sb.dispose();
        if (music != null) {
            music.dispose();
    }

    
}
}