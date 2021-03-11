package com.javarush.games.spaceinvaders;

import com.javarush.engine.cell.*;
import com.javarush.games.spaceinvaders.gameobjects.Bullet;
import com.javarush.games.spaceinvaders.gameobjects.EnemyFleet;
import com.javarush.games.spaceinvaders.gameobjects.PlayerShip;
import com.javarush.games.spaceinvaders.gameobjects.Star;

import java.util.ArrayList;
import java.util.List;

public class SpaceInvadersGame extends Game {

    public static final int WIDTH = 64;
    public static final int HEIGHT = 64;
    public static final int COMPLEXITY = 5;
    private List<Star> stars;
    private EnemyFleet enemyFleet;
    private List<Bullet> enemyBullets;
    private PlayerShip playerShip;
    private boolean isGameStopped;
    private int animationsCount;

    @Override
    public void initialize() {
        setScreenSize(WIDTH, HEIGHT);
        createGame();
    }

    private void createGame() {
        createStars();
        enemyFleet = new EnemyFleet();
        enemyBullets = new ArrayList<>();
        playerShip = new PlayerShip();
        isGameStopped = false;
        animationsCount = 0;
        setTurnTimer(40);
        drawScene();
    }

    private void drawScene() {
        drawField();
        enemyFleet.draw(this);
        playerShip.draw(this);
        for (Bullet bullet : enemyBullets) {
            bullet.draw(this);
        }
    }

    private void drawField() {
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                setCellValueEx(j, i, Color.BLACK, "");
            }
        }
        for (Star star : stars) {
            star.draw(this);
        }
    }

    @Override
    public void onTurn(int step) {
        moveSpaceObjects();
        check();
        Bullet bullet = enemyFleet.fire(this);
        if (bullet != null) {
            enemyBullets.add(bullet);
        }
        drawScene();
    }

    private void createStars() {
        stars = new ArrayList<>();
        stars.add(new Star(53,60));
        stars.add(new Star(10,3));
        stars.add(new Star(6,55));
        stars.add(new Star(40,10));
        stars.add(new Star(60,20));
        stars.add(new Star(31,41));
        stars.add(new Star(28,33));
        stars.add(new Star(22,7));
    }

    private void moveSpaceObjects() {
        enemyFleet.move();
        for (Bullet bullet : enemyBullets) {
            bullet.move();
        }
    }

    private void removeDeadBullets() {
        List<Bullet> copy = new ArrayList<>();
        copy.addAll(enemyBullets);
        for (Bullet bullet: copy) {
            if ((bullet.y >= HEIGHT - 1) || (!bullet.isAlive)) {
                enemyBullets.remove(bullet);
            }
        }
    }

    private void check() {
        playerShip.verifyHit(enemyBullets);
        removeDeadBullets();
        if (!playerShip.isAlive) stopGameWithDelay();
    }

    private void stopGame(boolean isWin) {
        isGameStopped = true;
        stopTurnTimer();
        if (isWin) {
            showMessageDialog(Color.PLUM, "Congratulations! You won",
                    Color.GREEN, 45);
        } else {
            showMessageDialog(Color.GRAY, "You lose!", Color.RED, 45);
        }
    }

    private void stopGameWithDelay() {
        animationsCount++;
        if (animationsCount >= 10) stopGame(playerShip.isAlive);
    }
}
