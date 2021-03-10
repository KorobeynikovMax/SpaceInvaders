package com.javarush.games.spaceinvaders.gameobjects;

import com.javarush.games.spaceinvaders.ShapeMatrix;
import com.javarush.games.spaceinvaders.SpaceInvadersGame;

import java.util.List;

public class PlayerShip extends Ship {
    public PlayerShip() {
        super(SpaceInvadersGame.WIDTH / 2.0,
                SpaceInvadersGame.HEIGHT - ShapeMatrix.PLAYER.length - 1);
        setStaticView(ShapeMatrix.PLAYER);
    }

    public void verifyHit(List<Bullet> bullets) {
        if (bullets.size() > 0) {
            if (isAlive) {
                for (Bullet bullet : bullets) {
                    if (bullet.isAlive && this.isCollision(bullet)) {
                        this.kill();
                        bullet.kill();
                    }
                }
            }
        }
    }
}
