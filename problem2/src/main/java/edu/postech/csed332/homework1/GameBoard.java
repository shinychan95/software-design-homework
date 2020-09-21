package edu.postech.csed332.homework1;

import java.util.*;

/**
 * A game board contains a set of units and a goal position. A game consists
 * of a number of rounds. For each round, all units perform their actions:
 * a monster can escape or move, and a tower can attack nearby monsters.
 * The following invariant conditions must be maintained throught the game:
 * <p>
 * (a) The position of each unit is inside the boundaries.
 * (b) Different ground units cannot be on the same tile.
 * (c) Different air units cannot be on the same tile.
 * <p>
 * TODO: implements all the unimplemented methods (marked with TODO)
 * NOTE: do not modify the signature of public methods (which will be
 * used for GUI and grading). But you can feel free to add new fields
 * or new private methods if needed.
 */
public class GameBoard {
    private final Position goal;
    private final int width, height;
    // TODO: add more fields to implement this class
    private Map<Position, Map<Boolean, Unit>> board;
    private Map<Unit, Position> units;
    private Position[][] pMatrix;
    private Set<Monster> mobs;
    private Set<Tower> towers;
    private int numMobs;
    private int numTowers;
    private int numMobsKilled;
    private int numMobsEscaped;

    /**
     * Creates a game board with a given width and height. The goal position
     * is set to the middle of the end column.
     *
     * @param width  of this board
     * @param height of this board
     */
    public GameBoard(int width, int height) {
        this.width = width;
        this.height = height;
        goal = new Position(width - 1, height / 2);
        // TODO: add more lines if needed.
        board = new HashMap<>();
        units = new HashMap<>();
        pMatrix = new Position[width][height];
        mobs = new HashSet<>();
        towers = new HashSet<>();
        numMobs = 0;
        numTowers = 0;
        numMobsKilled = 0;
        numMobsEscaped = 0;
    }

    /**
     * Places a unit at a given position into this board.
     *
     * @param obj a unit to be placed
     * @param p   the position of obj
     * @throws IllegalArgumentException if p is outside the bounds of the board.
     */
    public void placeUnit(Unit obj, Position p) {
        // TODO: implement this
        int x = p.getX();
        int y = p.getY();
        if (x >= width || y >= height || x < 0 || y < 0) {
            throw new IllegalArgumentException();
        }

        if (pMatrix[x][y] == null) {
            board.put(p, new HashMap<Boolean, Unit>() {{ put(obj.isGround(), obj); }});
            pMatrix[x][y] = p;
            setUnit(obj, p);
        } else if (!(board.get(pMatrix[x][y]).containsKey(obj.isGround()))) {
            board.get(pMatrix[x][y]).put(obj.isGround(), obj);
            setUnit(obj, pMatrix[x][y]);
        } else {
            throw new IllegalArgumentException();
        }

        System.out.println("유닛을 위치시켰습니다.");
        System.out.println("x: " + x + ", y: " + y);
        System.out.println("unit: " + obj.getClass().toString());
    }

    private void setUnit(Unit obj, Position p) {
        if (obj instanceof Monster) {
            units.put(obj, p);
            mobs.add((Monster) obj);
            numMobs++;
        } else {
            units.put(obj, p);
            towers.add((Tower) obj);
            numTowers++;
        }
    }

    /**
     * Clears this game board. That is, all units are removed, and all numbers
     * for game statistics are reset to 0.
     */
    public void clear() {
        // TODO: implement this
        board.clear();
        units.clear();
        mobs.clear();
        towers.clear();
        numMobs = 0;
        numTowers = 0;
        numMobsKilled = 0;
        numMobsEscaped = 0;
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                pMatrix[x][y] = null;
            }
        }
    }

    /**
     * Returns the set of units at a given position in the board. Note that
     * multiple units can exist at the same position (e.g., ground and air)
     *
     * @param p a position
     * @return the set of units at position p
     */
    public Set<Unit> getUnitsAt(Position p) {
        // TODO: implement this
        int x = p.getX();
        int y = p.getY();

        if (x >= width || y >= height || x < 0 || y < 0) throw new IllegalArgumentException();

        if (pMatrix[x][y] == null) {
            return null;
        }

        Set<Unit> unitsAt = new HashSet<>();
        for (Unit unit : board.get(pMatrix[x][y]).values()) {
            unitsAt.add(unit);
        }
        return unitsAt;
    }

    /**
     * Returns the set of all monsters in this board.
     *
     * @return the set of all monsters
     */
    public Set<Monster> getMonsters() {
        // TODO: implement this
        return mobs;
    }

    /**
     * Returns the set of all towers in this board.
     *
     * @return the set of all towers
     */
    public Set<Tower> getTowers() {
        // TODO: implement this
        return towers;
    }

    /**
     * Returns the position of a given unit
     *
     * @param obj a unit
     * @return the position of obj
     */
    public Position getPosition(Unit obj) {
        // TODO: implement this
        return units.get(obj);
    }


    /**
     * Proceeds one round of a game. The behavior of this method is as follows:
     * (1) Every monster at the goal position escapes from the game.
     * (2) Each tower attacks nearby remaining monsters (using the attack method).
     * (3) All remaining monsters (neither escaped nor attacked) moves (using the goal method).
     */
    public void step() {
        // TODO: implement this
        // (1)
        int x = goal.getX();
        int y = goal.getY();
        if (pMatrix[x][y] != null) {
            for (Unit unit : board.get(pMatrix[x][y]).values()){
                System.out.println("Unit 탈출: " + unit.getClass().toString());
                System.out.println("위치: " + unit.getPosition().toString());
                removeUnit(unit);
                numMobsEscaped++;
            }
        }
        // (2)
        for (Tower t : towers) {
            for (Unit unit : t.attack() ) {
                removeUnit(unit);
                numMobsKilled++;
            }
        }

        // (3)
        Map<Position, Monster> moveMobs = new HashMap<>();
        for (Monster m : mobs) {
            System.out.println("*************** MOVE ***************");
            System.out.println(m.getClass().toString());
            System.out.println("현재 위치: " + m.getPosition().toString());
            Position p = m.move();
            System.out.println("이동 대기: " + p.toString());
            if (canPlacePosition(p, m.isGround())) {
                System.out.println("이동 **가능**");
                moveMobs.put(p, m);
            }
        }
        for (Position p : moveMobs.keySet()) {
            removeUnit(moveMobs.get(p));
            placeUnit(moveMobs.get(p), p);
        }
    }

    private void removeUnit(Unit unit) {
        board.get(unit.getPosition()).remove(unit.isGround());
        if (board.get(unit.getPosition()).isEmpty()) {
            board.remove(unit.getPosition());
            pMatrix[unit.getPosition().getX()][unit.getPosition().getY()] = null;
        }
        units.remove(unit);
        mobs.remove(unit);
        numMobs--;
    }

    /**
     * Checks whether the following invariants hold in the current game board:
     * (a) All units are in the boundaries.
     * (b) Different ground units cannot be on the same tile.
     * (c) Different air units cannot be on the same tile.
     *
     * @return true if there is no problem. false otherwise.
     */
    public boolean isValid() {
        // TODO: implement this
        // (a)
        for (Position p : units.values()) if (!isValidPosition(p)) return false;

        // (b) && (c)
        int[][] groundCount = new int[width][height];
        int[][] airCount = new int[width][height];

        for (Unit u : units.keySet()) {
            int x = u.getPosition().getX();
            int y = u.getPosition().getY();
            if (u.isGround()) {
                if (groundCount[x][y] == 0) groundCount[x][y]++;
                else return false;
            } else {
                if (airCount[x][y] == 0) airCount[x][y]++;
                else return false;
            }
        }
        return true;
    }

    public boolean isValidPosition(Position p) {
        if (p == null) return false;

        int x = p.getX();
        int y = p.getY();

        if (x >= width || y >= height || x < 0 || y < 0) return false;

        return true;
    }

    public boolean isValidPosition(int x, int y) {
        if (x >= width || y >= height || x < 0 || y < 0) return false;

        return true;
    }

    public boolean canPlacePosition(Position p, Boolean isGround) {
        try {
            if (getUnitsAt(p) != null){
                for (Unit unit : getUnitsAt(p)) {
                    if (isGround == unit.isGround()) return false;
                }
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public boolean isGroundTower(int x, int y){
        if (!isValidPosition(x, y)) return false;

        if (pMatrix[x][y] != null)
            if (board.get(pMatrix[x][y]).containsKey(true))
                if (board.get(pMatrix[x][y]).get(true) instanceof Tower)
                    return true;

        return false;
    }

    public boolean isAirTower(int x, int y){
        if (!isValidPosition(x, y)) return false;

        if (pMatrix[x][y] != null)
            if (board.get(pMatrix[x][y]).containsKey(false))
                if (board.get(pMatrix[x][y]).get(false) instanceof Tower)
                    return true;

        return false;
    }

    public boolean isAirMob(int x, int y){
        if (!isValidPosition(x, y)) return false;

        if (pMatrix[x][y] != null)
            if (board.get(pMatrix[x][y]).containsKey(false))
                if (board.get(pMatrix[x][y]).get(false) instanceof Monster)
                    return true;

        return false;
    }

    public boolean isGrondMob(int x, int y){
        if (!isValidPosition(x, y)) return false;

        if (pMatrix[x][y] != null)
            if (board.get(pMatrix[x][y]).containsKey(true))
                if (board.get(pMatrix[x][y]).get(true) instanceof Monster)
                    return true;

        return false;
    }



    /**
     * Returns the number of the monsters in this board.
     *
     * @return the number of the monsters
     */
    public int getNumMobs() {
        // TODO: implement this
        return numMobs;
    }

    /**
     * Returns the number of the towers in this board.
     *
     * @return the number of the towers
     */
    public int getNumTowers() {
        // TODO: implement this
        return numTowers;
    }

    /**
     * Returns the number of the monsters removed so far in this game.
     * This number will be reset to 0 by the clear method.
     *
     * @return the number of the monsters removed
     */
    public int getNumMobsKilled() {
        // TODO: implement this
        return numMobsKilled;
    }

    /**
     * Returns the number of the monsters escaped so far in this game
     * This number will be reset to 0 by the clear method.
     *
     * @return the number of the monsters escaped
     */
    public int getNumMobsEscaped() {
        // TODO: implement this
        return numMobsEscaped;
    }

    /**
     * Returns the width of this board.
     *
     * @return width
     */
    public int getWidth() {
        return width;
    }

    /**
     * Returns the height of this board.
     *
     * @return height
     */
    public int getHeight() {
        return height;
    }

    /**
     * Returns the goal position where the monster can escape.
     *
     * @return the goal position of this game
     */
    public Position getGoalPosition() {
        return goal;
    }
}
