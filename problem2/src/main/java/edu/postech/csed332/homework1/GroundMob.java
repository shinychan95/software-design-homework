package edu.postech.csed332.homework1;

/**
 * A ground monster that moves towards to the goal position of the board, while
 * satisfying the game board invariants. The class GroundMob implements the interface
 * Monster. TODO: implement all unimplemented methods.
 * Feel free to modify this file, e.g., adding new fields or methods. If needed,
 * you can define a new (abstract) super class that this class inherits.
 *
 * @see GameBoard#isValid
 */
public class GroundMob implements Monster {

    GameBoard gameboard;

    public GroundMob(GameBoard gameboard) {
        // TODO: implement this
        this.gameboard = gameboard;
    }

    @Override
    public Position move() {
        // TODO implement this
        Position p = this.getPosition().getRelativePosition(1, 0);

        return (gameboard.isValidPosition(p) ? p : this.getPosition());
    }

    @Override
    public boolean isGround() {
        // TODO implement this
        return true;
    }

    @Override
    public GameBoard getBoard() {
        // TODO implement this
        return gameboard;
    }
}
