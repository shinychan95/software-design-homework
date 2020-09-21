package edu.postech.csed332.homework1;

/**
 * An air monster that moves towards to the goal position of the board, while
 * satisfying the game board invariants. The class AirMob implements the interface
 * Monster. TODO: implement all unimplemented methods.
 * Feel free to modify this file, e.g., adding new fields or methods. If needed,
 * you can define a new (abstract) super class that this class inherits.
 *
 * @see GameBoard#isValid
 */
public class AirMob implements Monster {

    GameBoard gameboard;

    public AirMob(GameBoard gameboard) {
        // TODO: implement this
        this.gameboard = gameboard;
    }

    @Override
    public Position move() {
        // TODO: implement this
        Position p = this.getPosition().getRelativePosition(1, 0);

        return (gameboard.isValidPosition(p) ? p : this.getPosition());
    }

    @Override
    public boolean isGround() {
        // TODO: implement this
        return false;
    }

    @Override
    public GameBoard getBoard() {
        // TODO: implement this
        return gameboard;
    }
}
