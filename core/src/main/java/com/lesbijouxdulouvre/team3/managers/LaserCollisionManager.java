package com.lesbijouxdulouvre.team3.managers;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.lesbijouxdulouvre.team3.elements.Laser;
import com.lesbijouxdulouvre.team3.entities.Guardian;
import com.lesbijouxdulouvre.team3.entities.Voleur;

public class LaserCollisionManager {

    public static void handleGuardianVsLasers(Guardian guardian, Array<Laser> lasers, float delta) {
        Rectangle current = guardian.getBounds();
        Rectangle next = guardian.peekNextBounds(delta);
        Rectangle resolved = new Rectangle(current);
        boolean collided = false;

        Rectangle testX = new Rectangle(next.x, current.y, current.width, current.height);
        if (!collidesRect(testX, lasers)) resolved.x = testX.x;
        else collided = true;

        Rectangle testY = new Rectangle(resolved.x, next.y, current.width, current.height);
        if (!collidesRect(testY, lasers)) resolved.y = testY.y;
        else collided = true;

        guardian.applyResolvedPosition(resolved);

        if (collided) {
            guardian.reverseDirectionWithPause(0.5f);
            guardian.getBounds().x += guardian.getVelocity().x * delta;
            guardian.getBounds().y += guardian.getVelocity().y * delta;
            guardian.applyResolvedPosition(guardian.getBounds());
        }
    }

    public static boolean handleVoleurVsLasers(Voleur voleur, Array<Laser> lasers) {
        float cx = voleur.getX() + voleur.getWidth() / 2f;
        float cy = voleur.getY() + voleur.getHeight() / 2f;
        float rx = voleur.getWidth() * 0.28f;
        float ry = voleur.getHeight() * 0.38f;

        for (Laser l : lasers) {
            if (!l.isVisible()) continue;

            Rectangle hb = l.getHitbox();
            if (ellipseIntersectsRect(cx, cy, rx, ry, hb)) return true;
        }
        return false;
    }

    private static boolean collidesRect(Rectangle rect, Array<Laser> lasers) {
        for (Laser l : lasers) {
            if (!l.isVisible()) continue;
            if (rect.overlaps(l.getHitbox())) return true;
        }
        return false;
    }

    private static boolean ellipseIntersectsRect(float cx, float cy, float rx, float ry, Rectangle rect) {
        float closestX = Math.max(rect.x, Math.min(cx, rect.x + rect.width));
        float closestY = Math.max(rect.y, Math.min(cy, rect.y + rect.height));

        float dx = (closestX - cx) / rx;
        float dy = (closestY - cy) / ry;

        return dx * dx + dy * dy <= 1f;
    }
}