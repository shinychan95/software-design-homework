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
        int x = this.getPosition().getX();
        int y = this.getPosition().getY();

        Position p = null;
        boolean[] canGo = {true, true, true, true}; // 앞, 위, 아래, 뒤

        if (x + 1 == gameboard.getWidth()) {
            if (y > gameboard.getHeight() / 2) {
                return this.getPosition().getRelativePosition(0, -1);
            } else {
                return this.getPosition().getRelativePosition(0, 1);
            }
        }

        if (gameboard.isGrondMob(x + 1, y)) canGo[0] = false;
        if (gameboard.isGrondMob(x - 1, y)) canGo[2] = false;
        if (gameboard.isGrondMob(x, y + 1)) canGo[1] = false;
        if (gameboard.isGrondMob(x, y - 1)) canGo[3] = false;

        if (gameboard.isGroundTower(x + 1, y)) canGo[0] = false;
        if (gameboard.isGroundTower(x - 1, y)) canGo[2] = false;
        if (gameboard.isGroundTower(x, y + 1)) canGo[1] = false;
        if (gameboard.isGroundTower(x, y - 1)) canGo[3] = false;

        if (gameboard.isAirTower(x + 1, y)) canGo[0] = false;
        if (gameboard.isAirTower(x - 1, y)) canGo[2] = false;
        if (gameboard.isAirTower(x, y + 1)) canGo[1] = false;
        if (gameboard.isAirTower(x, y - 1)) canGo[3] = false;

        if (gameboard.isGroundTower(x + 1, y + 1)) { canGo[0] = false; canGo[1] = false; }
        if (gameboard.isGroundTower(x + 1, y - 1)) { canGo[0] = false; canGo[2] = false; }
        if (gameboard.isGroundTower(x - 1, y + 1)) { canGo[1] = false; canGo[3] = false; }
        if (gameboard.isGroundTower(x - 1, y - 1)) { canGo[2] = false; canGo[3] = false; }

        if (gameboard.isGroundTower(x + 2, y)) { canGo[0] = false; }
        if (gameboard.isGroundTower(x - 2, y)) { canGo[3] = false; }
        if (gameboard.isGroundTower(x, y + 2)) { canGo[1] = false; }
        if (gameboard.isGroundTower(x, y - 2)) { canGo[2] = false; }

        if (!gameboard.isValidPosition(x + 1, y)) canGo[0] = false;
        if (!gameboard.isValidPosition(x, y + 1)) canGo[1] = false;
        if (!gameboard.isValidPosition(x, y - 1)) canGo[2] = false;
        if (!gameboard.isValidPosition(x - 1, y)) canGo[3] = false;

        if (gameboard.isGroundTower(x + 2, y + 1) && gameboard.isGroundTower(x + 2, y - 1)) {
            canGo[0] = false;
        } else if (gameboard.isGroundTower(x + 2, y + 1)) {
            canGo[1] = false;
        } else if (gameboard.isGroundTower(x + 2, y - 1)) {
            canGo[2] = false;
        }

        if (gameboard.isGroundTower(x + 2, y) && gameboard.isGroundTower(x + 2, y + 2)) { canGo[1] = false; }
        if (gameboard.isGroundTower(x + 2, y) && gameboard.isGroundTower(x + 2, y - 2)) { canGo[2] = false; }


        System.out.println("-canGo 상태-");
        for (boolean b : canGo) {
            System.out.println(b);
        }

        if (canGo[0]) { p = this.getPosition().getRelativePosition(1, 0); }
        else if (canGo[1]) { p = this.getPosition().getRelativePosition(0, 1); }
        else if (canGo[2]) { p = this.getPosition().getRelativePosition(0, -1); }
        else if (canGo[3]) { p = this.getPosition().getRelativePosition(-1, 0); }

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
