package com.javarush.games.spaceinvaders.gameobjects;

import com.javarush.games.spaceinvaders.Direction;
import com.javarush.games.spaceinvaders.ShapeMatrix;

public class EnemyShip extends Ship {

    public EnemyShip(double x, double y) {
        super(x, y);
        setStaticView(ShapeMatrix.ENEMY);
    }

    public void move(Direction direction, double speed) {
        if (Direction.RIGHT.equals(direction)) {
            x += speed;
        } else if (Direction.LEFT.equals(direction)) {
            x -= speed;
        } else if (Direction.DOWN.equals(direction)) {
            y += 2;
        }
    }
}
