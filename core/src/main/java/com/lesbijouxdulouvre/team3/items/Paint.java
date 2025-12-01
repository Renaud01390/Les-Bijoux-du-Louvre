package com.lesbijouxdulouvre.team3.items;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;

public class Paint extends Item {
    private Animation<TextureRegion> animation;
    private float stateTime;
    
    public Paint(float x, float y) {
        String[] paintTextures = {"item/tableau1.png", "item/tableau2.png", "item/tableau3.png"};
        int randomIndex = (int)(Math.random() * paintTextures.length);
        this.texture = new Texture(paintTextures[randomIndex]);
        this.texture = new Texture(paintTextures[randomIndex]);
        this.x = x;
        this.y = y;
        this.score = 100;
 
        TextureRegion[][] tmp = TextureRegion.split(texture, 25, 35);
        TextureRegion[] frames = new TextureRegion[16];
        int index = 0;
        for (int i = 0; i < tmp.length; i++) {
            for (int j = 0; j < tmp[i].length && index < 16; j++) {
                frames[index++] = tmp[i][j];
            }
        }
        animation = new Animation<TextureRegion>(0.1f, frames);
        animation.setPlayMode(Animation.PlayMode.LOOP);
        stateTime = 0f;
    }
    
    @Override
    public void render(SpriteBatch sb) {
        if (!collected) {
            stateTime += com.badlogic.gdx.Gdx.graphics.getDeltaTime();
            TextureRegion currentFrame = animation.getKeyFrame(stateTime);
            sb.draw(currentFrame, x, y);
        }
    }
}