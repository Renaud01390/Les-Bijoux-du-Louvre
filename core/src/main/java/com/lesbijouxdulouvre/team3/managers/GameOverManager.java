package com.lesbijouxdulouvre.team3.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.lesbijouxdulouvre.team3.entities.Guardian;
import com.lesbijouxdulouvre.team3.entities.Voleur;
import com.lesbijouxdulouvre.team3.utils.GameTimer;

public class GameOverManager {
    private static final float VOLEUR_RX = 0.20f;
    private static final float VOLEUR_RY = 0.28f;
    private static final float GUARDIAN_R = 0.28f;
    private static final float GUARDIAN_Y = 0.38f;
    private static final float CONE_SHRINK = 0.50f;
    private boolean lost = false;
    private int totalScore = 0;
    private GameTimer timer;
    private BitmapFont timerFont;
    private float gameOverTimer = 0f;
    private static final float DELAY = 3f;
    private BitmapFont lostFont;
    private Sound alarmSound;
    private OrthographicCamera hudCam;
    private ShapeRenderer debugRenderer;
    private boolean showDebug = false;
    private boolean debugCone = true;
    private boolean debugGuardian = true;
    private boolean debugVoleur = true;
    private float viewportWidth;
    private float viewportHeight;
    
    public GameOverManager(float viewportWidth, float viewportHeight, float timerDuration) {
        this.viewportWidth = viewportWidth;
        this.viewportHeight = viewportHeight;
        this.timer = new GameTimer(timerDuration);
        this.timerFont = new BitmapFont();
        timerFont.getData().setScale(1.5f);
        timerFont.setColor(Color.WHITE);
        this.lostFont = new BitmapFont();
        lostFont.getData().setScale(3f);
        lostFont.setColor(Color.RED);
        this.alarmSound = Gdx.audio.newSound(Gdx.files.internal("sound/Alarm.mp3"));
        this.hudCam = new OrthographicCamera();
        hudCam.setToOrtho(false, viewportWidth, viewportHeight);
        this.debugRenderer = new ShapeRenderer();
    }
    
    public void stopAlarm() {
        alarmSound.stop();
    }

    public void update(float dt) {
        if (lost) {
            gameOverTimer += dt; 
            return;
        }
        timer.update(dt);
        if (timer.isCritical() && !timer.isAlarmTriggered()) {
            timerFont.setColor(Color.RED);
            alarmSound.play(0.6f);
            timer.triggerAlarm();
        }
        if (timer.isFinished()) {
            lost = true;
            return;
        }
    }
    
    private void checkCollisions(Voleur voleur, Guardian guardian) {
        float vx = voleur.getX() + voleur.getWidth() / 2f;
        float vy = voleur.getY() + voleur.getHeight() / 2f;
        float vrx = voleur.getWidth() * VOLEUR_RX;
        float vry = voleur.getHeight() * VOLEUR_RY;
        float gx = guardian.getBounds().x + guardian.getBounds().width / 2f;
        float gy = guardian.getBounds().y + guardian.getBounds().height / 2f;
        float grx = 20 * GUARDIAN_R;
        float gry = 19 * GUARDIAN_Y;
        if (ellipseIntersectsEllipse(vx, vy, vrx, vry, gx, gy, grx, gry)) {
            lost = true;
            return;
        }
        if (checkConeCollision(vx, vy, vrx, vry, guardian)) {
            lost = true;
        }
    }
    
    private boolean checkConeCollision(float vx, float vy, float vrx, float vry, Guardian guardian) {
        float[] c = guardian.getVisionCone().getTriangleVertices();
        float oX = c[0];
        float oY = c[1];
        float x1 = oX + (c[2] - oX) * CONE_SHRINK;
        float y1 = oY + (c[3] - oY) * CONE_SHRINK;
        float x2 = oX + (c[4] - oX) * CONE_SHRINK;
        float y2 = oY + (c[5] - oY) * CONE_SHRINK;

        Polygon conePoly = new Polygon(new float[]{
                oX, oY, x1, y1, x2, y2
        });

        Polygon vole = ellipseToPolygon(vx, vy, vrx, vry);

        return Intersector.overlapConvexPolygons(conePoly, vole);
    }
    
    public void renderHUD(SpriteBatch sb) {
        sb.setProjectionMatrix(hudCam.combined);
        sb.begin();
        timerFont.draw(sb, "Time : " + timer.getFormattedTime(),
                20, viewportHeight - 20);
        timerFont.draw(sb, "Score : " + totalScore,
                20, viewportHeight - 60);
        if (lost) {
            lostFont.draw(sb, "PERDU !",
                    viewportWidth / 2f - 80, viewportHeight / 2f);
        }
        sb.end();
    }
    
    public void renderDebug(OrthographicCamera cam, Voleur voleur, Guardian guardian) {
        if (!showDebug) return;
        if (debugCone) renderDebugCone(cam, guardian);
        if (debugGuardian) renderDebugGuardian(cam, guardian);
        if (debugVoleur) renderDebugVoleur(cam, voleur);
    }
    
    private void renderDebugCone(OrthographicCamera cam, Guardian guardian) {
        float[] c = guardian.getVisionCone().getTriangleVertices();
        float oX = c[0];
        float oY = c[1];
        float x1 = oX + (c[2] - oX) * CONE_SHRINK;
        float y1 = oY + (c[3] - oY) * CONE_SHRINK;
        float x2 = oX + (c[4] - oX) * CONE_SHRINK;
        float y2 = oY + (c[5] - oY) * CONE_SHRINK;

        debugRenderer.setProjectionMatrix(cam.combined);
        debugRenderer.begin(ShapeRenderer.ShapeType.Line);
        debugRenderer.setColor(Color.RED);
        debugRenderer.triangle(oX, oY, x1, y1, x2, y2);
        debugRenderer.end();
    }

    private void renderDebugGuardian(OrthographicCamera cam, Guardian guardian) {
        float gx = guardian.getBounds().x + guardian.getBounds().width / 2f;
        float gy = guardian.getBounds().y + guardian.getBounds().height / 2f;
        float grx = 20 * GUARDIAN_R;
        float gry = 19 * GUARDIAN_Y;

        debugRenderer.setProjectionMatrix(cam.combined);
        debugRenderer.begin(ShapeRenderer.ShapeType.Line);
        debugRenderer.setColor(Color.RED);
        for (int i = 0; i < 360; i += 10) {
            float a1 = (float) Math.toRadians(i);
            float a2 = (float) Math.toRadians(i + 10);
            float x1 = gx + (float) Math.cos(a1) * grx;
            float y1 = gy + (float) Math.sin(a1) * gry;
            float x2 = gx + (float) Math.cos(a2) * grx;
            float y2 = gy + (float) Math.sin(a2) * gry;
            debugRenderer.line(x1, y1, x2, y2);
        }
        debugRenderer.end();
    }

    private void renderDebugVoleur(OrthographicCamera cam, Voleur voleur) {
        float vx = voleur.getX() + voleur.getWidth() / 2f;
        float vy = voleur.getY() + voleur.getHeight() / 2f;
        float vrx = voleur.getWidth() * VOLEUR_RX;
        float vry = voleur.getHeight() * VOLEUR_RY;
        debugRenderer.setProjectionMatrix(cam.combined);
        debugRenderer.begin(ShapeRenderer.ShapeType.Line);
        debugRenderer.setColor(Color.CYAN);

        for (int i = 0; i < 360; i += 10) {
            float a1 = (float) Math.toRadians(i);
            float a2 = (float) Math.toRadians(i + 10);
            float x1 = vx + (float) Math.cos(a1) * vrx;
            float y1 = vy + (float) Math.sin(a1) * vry;
            float x2 = vx + (float) Math.cos(a2) * vrx;
            float y2 = vy + (float) Math.sin(a2) * vry;
            debugRenderer.line(x1, y1, x2, y2);
        }

        debugRenderer.end();
    }

    private static Polygon ellipseToPolygon(float cx, float cy, float rx, float ry) {
        float[] verts = new float[20];
        for (int i = 0; i < 10; i++) {
            float a = (float)(i * Math.PI * 2f / 10f);
            verts[i * 2] = cx + (float)Math.cos(a) * rx;
            verts[i * 2 + 1] = cy + (float)Math.sin(a) * ry;
        }
        return new Polygon(verts);
    }

    private static boolean ellipseIntersectsEllipse(float x1, float y1, float rx1, float ry1,
                                             float x2, float y2, float rx2, float ry2) {
        float dx = x2 - x1;
        float dy = y2 - y1;
        float norm = (dx * dx) / ((rx1 + rx2) * (rx1 + rx2)) +
                     (dy * dy) / ((ry1 + ry2) * (ry1 + ry2));
        return norm < 1;
    }

    public boolean ReturnToMenu() {
            return lost && gameOverTimer >= DELAY;
    }
    
    public boolean isLost() {
        return lost;
    }
    public void setLost(boolean lost) {
        this.lost = lost;
    }
    public void setScore(int score) {
        this.totalScore = score;
    }
    public void toggleDebug() {
        showDebug = !showDebug;
    }
    public void dispose() {
        alarmSound.dispose();
        debugRenderer.dispose();
        timerFont.dispose();
        lostFont.dispose();
    }
    public void update(Voleur voleur, Guardian guardian) {
        if (lost) return;
        checkCollisions(voleur, guardian);
    }
}