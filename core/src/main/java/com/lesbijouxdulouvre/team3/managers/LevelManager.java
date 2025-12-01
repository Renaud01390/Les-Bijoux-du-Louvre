package com.lesbijouxdulouvre.team3.managers;

import com.lesbijouxdulouvre.team3.states.GameState;
import com.lesbijouxdulouvre.team3.states.TransitionState;

import levels.*;

public class LevelManager {
    
    public static GameState RandomLevel(GameStateManager gsm) {
        GameState[] levels = {
            new Niveau01(gsm),
            new Niveau02(gsm),
            new Niveau03(gsm)
        };
        
        int index = (int) (Math.random() * levels.length);
        return new TransitionState(gsm, levels[index], 1f);
    }
}