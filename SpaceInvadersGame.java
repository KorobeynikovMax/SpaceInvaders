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
    private static final int PLAYER_BULLETS_MAX = 1;
    private List<Star> stars;
    private EnemyFleet enemyFleet;
    private List<Bullet> enemyBullets;
    private PlayerShip playerShip;
    private boolean isGameStopped;
    private int animationsCount;
    private List<Bullet> playerBullets;
    private int score;

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
        playerBullets = new ArrayList<>();
        score = 0;
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
        for (Bullet playerBullet : playerBullets) {
            playerBullet.draw(this);
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
        setScore(score);
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
        for (Bullet playerBullet : playerBullets) {
            playerBullet.move();
        }
        playerShip.move();
    }

    @Override
    public void onKeyPress(Key key) {
        if (Key.SPACE.equals(key)) {
            if (isGameStopped)
            {
                createGame();
            } else {
                Bullet bullet = playerShip.fire();
                if (bullet != null && playerBullets.size() < PLAYER_BULLETS_MAX) {
                    playerBullets.add(bullet);
                }
            }
        } else if (Key.LEFT.equals(key)) {
            playerShip.setDirection(Direction.LEFT);
        } else if (Key.RIGHT.equals(key)) {
            playerShip.setDirection(Direction.RIGHT);
        }
    }

    @Override
    public void onKeyReleased(Key key) {
        if (Key.LEFT.equals(key) && playerShip.getDirection().equals(Direction.LEFT) ||
                Key.RIGHT.equals(key) && playerShip.getDirection().equals(Direction.RIGHT)) {
            playerShip.setDirection(Direction.UP);
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
        List<Bullet> copy2 = new ArrayList<>();
        copy2.addAll(playerBullets);
        for (Bullet pbullet: copy2) {
            if ((pbullet.y + pbullet.height < 0) || (!pbullet.isAlive)) {
                playerBullets.remove(pbullet);
            }
        }
    }

    private void check() {
        playerShip.verifyHit(enemyBullets);
        score += enemyFleet.verifyHit(playerBullets);
        enemyFleet.deleteHiddenShips();
        removeDeadBullets();
        if (!playerShip.isAlive) stopGameWithDelay();
        if (enemyFleet.getBottomBorder() >= playerShip.y) {
            playerShip.kill();
        }
        if (enemyFleet.getShipsCount() == 0) {
            playerShip.win();
            stopGameWithDelay();
        }
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

    @Override
    public void setCellValueEx(int x, int y, Color cellColor, String value) {
        if (x >= 0 && x < WIDTH && y >= 0 && y < HEIGHT) {
            super.setCellValueEx(x, y, cellColor, value);
        }
    }
}
