package com.javarush.games.spaceinvaders;

import com.javarush.engine.cell.*;
import com.javarush.games.spaceinvaders.gameobjects.EnemyFleet;
import com.javarush.games.spaceinvaders.gameobjects.Star;

import java.util.ArrayList;
import java.util.List;

public class SpaceInvadersGame extends Game {

    public static final int WIDTH = 64;
    public static final int HEIGHT = 64;
    private List<Star> stars;
    private EnemyFleet enemyFleet;

    @Override
    public void initialize() {
        setScreenSize(WIDTH, HEIGHT);
        createGame();
    }

    private void createGame() {
        createStars();
        enemyFleet = new EnemyFleet();
        setTurnTimer(40);
        drawScene();
    }

    private void drawScene() {
        drawField();
        enemyFleet.draw(this);
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
}
