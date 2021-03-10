package com.javarush.games.spaceinvaders.gameobjects;

import com.javarush.engine.cell.Game;
import com.javarush.games.spaceinvaders.Direction;
import com.javarush.games.spaceinvaders.ShapeMatrix;
import com.javarush.games.spaceinvaders.SpaceInvadersGame;

import java.util.ArrayList;
import java.util.List;

public class EnemyFleet {

    private static final int ROWS_COUNT = 3;
    private static final int COLUMNS_COUNT = 10;
    private static final int STEP = ShapeMatrix.ENEMY.length + 1;
    private List<EnemyShip> ships;
    private Direction direction = Direction.RIGHT;

    private void createShips() {
        ships = new ArrayList<>();
        for (int x = 0; x < COLUMNS_COUNT; x++) {
            for (int y = 0; y < ROWS_COUNT; y++) {
                ships.add(new EnemyShip(x * STEP, y * STEP + 12));
            }
        }
    }

    public EnemyFleet() {
        createShips();
    }

    public void draw(Game game) {
        for (EnemyShip enemyShip : ships) {
            enemyShip.draw(game);
        }
    }

    private double getLeftBorder() {
        double result = ships.get(0).x;
        for (EnemyShip enemyShip : ships) {
            if (enemyShip.x < result) {
                result = enemyShip.x;
            }
        }
        return result;
    }

    private double getRightBorder() {
        double result = ships.get(0).x + ships.get(0).width;
        for (EnemyShip enemyShip : ships) {
            if (enemyShip.x + enemyShip.width > result) {
                result = enemyShip.x + enemyShip.width;
            }
        }
        return result;
    }

    private double getSpeed() {
        if (2.0 <= (3.0 / ships.size())) {
            return 2.0;
        } else return (3.0 / ships.size());
    }

    public void move() {
        if (ships.size() > 0) {
            boolean changed = false;
            if (Direction.LEFT.equals(direction) && (getLeftBorder() < 0)) {
                direction = Direction.RIGHT;
                changed = true;
            }
            if (Direction.RIGHT.equals(direction)
                    && (getRightBorder() > SpaceInvadersGame.WIDTH)) {
                direction = Direction.LEFT;
                changed = true;
            }
            if (changed) {
                for (EnemyShip enemyShip : ships) {
                    enemyShip.move(Direction.DOWN, getSpeed());
                }
            } else {
                for (EnemyShip enemyShip : ships) {
                    enemyShip.move(direction, getSpeed());
                }
            }
        }
    }

    public Bullet fire(Game game) {
        int enemyShipsCount = ships.size();
        if (enemyShipsCount > 0) {
            if (game.getRandomNumber(100 / SpaceInvadersGame.COMPLEXITY) > 0) {
                return null;
            } else {
                return ships.get(game.getRandomNumber(enemyShipsCount)).fire();
            }
        } else return null;
    }
}
