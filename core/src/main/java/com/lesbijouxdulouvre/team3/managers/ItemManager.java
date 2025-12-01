package com.lesbijouxdulouvre.team3.managers;

import java.util.ArrayList;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.lesbijouxdulouvre.team3.items.*;
import com.lesbijouxdulouvre.team3.entities.Voleur;

public class ItemManager {
    private ArrayList<Item> items;
    private int totalScore;
    private float collectionRadius;
    
    public ItemManager() {
        this.items = new ArrayList<Item>();
        this.totalScore = 0;
        this.collectionRadius = 30f;
    }
    public void addItem(Item item) {
        items.add(item);
    }

    public void update(Voleur voleur) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.Q)) {
            checkCollection(voleur);
        }
    }
    private void checkCollection(Voleur voleur) {
        for (Item item : items) {
            if (!item.isCollected()) {
                if (CollisionManager.isNearObject(voleur, item.getX(), item.getY(), collectionRadius)) {
                    item.collect();
                    totalScore += item.getScore();
                }
            }
        }
    }
    public void render(SpriteBatch sb) {
        for (Item item : items) {
            item.render(sb);
        }
    }
    public void dispose() {
        for (Item item : items) {
            item.dispose();
        }
        items.clear();
    }
    public int getTotalScore() {
        return totalScore;
    }
    public void resetScore() {
        totalScore = 0;
    }
    public boolean allItemsCollected() {
        for (Item item : items) {
            if (!item.isCollected()) {
                return false;
            }
        }
        return true;
    }
        public boolean hasCrownCollected() {
        for (Item item : items) {
            if (item instanceof Crown && item.isCollected()) {
                return true;
            }
        }
        return false;
    }
}