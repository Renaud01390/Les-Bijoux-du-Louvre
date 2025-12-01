package com.lesbijouxdulouvre.team3.managers;

import com.badlogic.gdx.math.Rectangle;
import com.lesbijouxdulouvre.team3.entities.Voleur;
import com.lesbijouxdulouvre.team3.elements.*;
import java.util.ArrayList;

public class CollisionManager {
    
    public static void handleCollisions(Voleur voleur, Ground ground, ArrayList<Platform> platforms) {
        Rectangle voleurBounds = voleur.getBounds();
        boolean onGround = false;
        float voleurBottom = voleur.getY();
        float voleurTop = voleur.getY() + voleur.getHeight();
        float voleurCenterX = voleur.getX() + voleur.getWidth() / 2;
        if (ground != null) {
            Rectangle groundBounds = ground.getBounds();
            float groundTop = ground.getTopY();
            if (voleurBounds.overlaps(groundBounds)) {
                if (voleur.getVelocityY() <= 0 && voleurBottom >= groundTop - 5) {
                    voleur.setY(groundTop);
                    voleur.setVelocityY(0);
                    voleur.setCanJump(true);
                    onGround = true;
                }
            }
        }
        if (platforms != null) {
            for (Platform platform : platforms) {
                Rectangle platformBounds = platform.getBounds();
                float platformTop = platform.getTopY();
                float platformBottom = platform.getY();
                float platformLeft = platform.getX();
                float platformRight = platform.getX() + platform.getWidth();
                float collisionZoneBottom = platformBottom - platform.getCollisionBottomHeight();
                boolean isAbovePlatform = voleurCenterX >= platformLeft && voleurCenterX <= platformRight;
                if (voleurBounds.overlaps(platformBounds) && isAbovePlatform) {
                    if (voleur.getVelocityY() <= 0 && voleurBottom >= platformTop - 5) {
                        voleur.setY(platformTop);
                        voleur.setVelocityY(0);
                        voleur.setCanJump(true);
                        onGround = true;
                    }
                }
                if (voleur.getVelocityY() > 0 && 
                    voleurTop >= collisionZoneBottom && voleurTop <= platformBottom + 5 &&
                    voleurCenterX >= platformLeft && voleurCenterX <= platformRight) {
                    voleur.setY(collisionZoneBottom - voleur.getHeight());
                    voleur.setVelocityY(0);
                }
            }
        }
        if (!onGround) {
            voleur.setCanJump(false);
        }
    }
    public static boolean isNearObject(Voleur voleur, float objectX, float objectY, float distance) {
        return Math.abs(voleur.getX() - objectX) < distance &&
               Math.abs(voleur.getY() - objectY) < distance;
    }
public static boolean checkWindowCollision(Voleur voleur, Window window, ItemManager itemManager) {
    if (itemManager.hasCrownCollected()) {
        Rectangle voleurBounds = voleur.getBounds();
        Rectangle windowBounds = window.getBounds();
        float windowTop = window.getTopY();
        float voleurBottom = voleur.getY();
        
        if (voleurBounds.overlaps(windowBounds)) {
            if (voleur.getVelocityY() <= 0 && voleurBottom >= windowTop - 5) {
                return true;
            }
        }
    }
    return false;
}
}