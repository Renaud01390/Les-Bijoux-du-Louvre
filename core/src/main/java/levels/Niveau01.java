package levels;

import java.util.ArrayList;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.lesbijouxdulouvre.team3.managers.*;
import com.lesbijouxdulouvre.team3.states.GameState;
import com.lesbijouxdulouvre.team3.states.MainMenu;
import com.lesbijouxdulouvre.team3.states.PauseState;
import com.lesbijouxdulouvre.team3.states.TransitionState;
import com.lesbijouxdulouvre.team3.entities.*;
import com.lesbijouxdulouvre.team3.items.*;
import com.lesbijouxdulouvre.team3.elements.*;
import com.badlogic.gdx.utils.Array;

public class Niveau01 extends GameState {
    private Voleur voleur;
    private ItemManager itemManager;
    private GameOverManager gameOverManager;
    private Ground ground;
    private Pillar pillar;
    private ArrayList<Guardian> guardians;
    private ArrayList<Platform> platforms;
    private Window exitWindow;
    private Array<Laser> lasers;
    private boolean hasCrown = false; 
    
    public Niveau01(GameStateManager gsm) {
        super(gsm);
        this.guardians = new ArrayList<Guardian>();
        guardians.add(new Guardian(410, 13).stat(20, 0, 200, 475, cam.combined));
        guardians.add(new Guardian(420, 13).stat(70, 0, 200, 450, cam.combined));
        guardians.add(new Guardian(400, 13).stat(50, 0, 200, 450, cam.combined));
        this.voleur = new Voleur(50, 14);
        this.ground = new Ground(0, 0);
        this.pillar = new Pillar(500, 7);
        this.lasers = new Array<>();
        lasers.add(new Laser(200, 0, 200, 400, 0f));
        lasers.add(new Laser(150, 0, 150, 400, 0.7f));
        for (Laser l : lasers) {
            l.setProjectionMatrix(cam.combined);
        }
        this.platforms = new ArrayList<Platform>();
        this.platforms.add(new Platform(200, 50));
        this.platforms.add(new Platform(250, 100));
        this.itemManager = new ItemManager();
        itemManager.addItem(new Crown(502, 20));
        itemManager.addItem(new Diamond(257, 120));
        itemManager.addItem(new Paint(400, 20));
        itemManager.addItem(new Paint(205, 80));
        itemManager.addItem(new Paint(100, 20));
        itemManager.addItem(new Paint(250, 20));
        this.exitWindow = new Window(20, 20);
        this.gameOverManager = new GameOverManager(VIEWPORT_WIDTH, VIEWPORT_HEIGHT, 30f);
    }

    @Override
    public void handleInput() {
    if (Gdx.input.isKeyJustPressed(Input.Keys.F3)) {
            gameOverManager.toggleDebug();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            gsm.push(new PauseState(gsm));
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
        gameOverManager.update(dt);
        if (gameOverManager.isLost()) {
            if (gameOverManager.ReturnToMenu()) {  
                gameOverManager.stopAlarm();
                gsm.setGameState(new MainMenu(gsm));
            }
        }
        for (Guardian guardian : guardians) {
            gameOverManager.update(voleur, guardian);
        if (gameOverManager.isLost()) return;
        }
        voleur.update(dt);
        for (Guardian guardian : guardians) {
            guardian.update(dt);
        }
        for (Laser l : lasers) {
            l.update(dt);
        }
        for (Guardian guardian : guardians) {
            LaserCollisionManager.handleGuardianVsLasers(guardian, lasers, dt);
        }
        if (LaserCollisionManager.handleVoleurVsLasers(voleur, lasers)) {
            gameOverManager.setLost(true);
        }
        CollisionManager.handleCollisions(voleur, ground, platforms);
        centerOnTarget(voleur.getX() + voleur.getWidth() / 2, voleur.getY() + voleur.getHeight() / 2);
        itemManager.update(voleur);
        if (itemManager.hasCrownCollected()) {
            hasCrown = true;
        }
        if (CollisionManager.checkWindowCollision(voleur, exitWindow, itemManager)) {
            gameOverManager.stopAlarm();
            gsm.setGameState(LevelManager.RandomLevel(gsm));
        }

    }   

@Override
public void render(SpriteBatch sb) {
    Gdx.gl.glClearColor(0.45f, 0.30f, 0.20f, 1f);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    sb.setProjectionMatrix(cam.combined);
    sb.begin();
    ground.render(sb);
    pillar.render(sb);
    itemManager.render(sb);
    gameOverManager.setScore(itemManager.getTotalScore());
    for (Platform platform : platforms) {
        platform.render(sb);
    }
    exitWindow.render(sb);
    voleur.render(sb);
    for (Guardian guardian : guardians) {
            guardian.render(sb);
        }
    sb.end();
    for (Laser l : lasers) {
            l.setProjectionMatrix(cam.combined);
            l.render();
    }
    Gdx.gl.glEnable(GL20.GL_BLEND);
    Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
    for (Guardian guardian : guardians) {
            guardian.getVisionCone().setProjectionMatrix(cam.combined);
            guardian.renderCone();
        }
    gameOverManager.renderHUD(sb);
    for (Guardian guardian : guardians) {
            gameOverManager.renderDebug(cam, voleur, guardian);
    }
}

    @Override
    public void dispose() {
        voleur.dispose();
        ground.dispose();
        for (Guardian guardian : guardians) {
            guardian.dispose();
        }
        itemManager.dispose();
        for (Laser l : lasers) {
            l.dispose();
        }
        if (pillar != null) {
            pillar.dispose();
        }
        for (Platform platform : platforms) {
            platform.dispose();
        }
    }
}