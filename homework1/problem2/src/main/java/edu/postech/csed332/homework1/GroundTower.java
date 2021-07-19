package edu.postech.csed332.homework1;

import java.util.HashSet;
import java.util.Set;

/**
 * A ground tower that can attack nearby ground monsters within 1 tile of distance.
 * For example, a ground tower at (x,y) can attack any ground monsters at (x-1, y),
 * (x+1, y), (x, y-1), and (x, y+1). The class GroundTower implements the interface
 * Tower. TODO: implement all unimplemented methods.
 * Feel free to modify this file, e.g., adding new fields or methods. If needed,
 * you can define a new (abstract) super class that this class inherits.
 */
public class GroundTower implements Tower {

    GameBoard gameboard;

    public GroundTower(GameBoard gameboard) {
        // TODO: implement this
        this.gameboard = gameboard;
    }

    @Override
    public Set<Monster> attack() {
        // TODO: implement this
        Set<Monster> mobs = new HashSet<>();
        for (Monster m : gameboard.getMonsters()) {
            if (m.isGround() && this.getPosition().getDistance(m.getPosition()) == 1) mobs.add(m);
        }
        return mobs;
    }

    @Override
    public GameBoard getBoard() {
        // TODO: implement this
        return gameboard;
    }
}
