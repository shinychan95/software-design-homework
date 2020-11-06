package edu.postech.csed332.homework6;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * An even-odd Sudoku board
 */
public class Board {
    //TODO: add private member variables for Board
    private Cell[][] cells;
    private Group[] rowGroup;
    private Group[] colGroup;
    private Group[][] squareGroup;

    /**
     * Creates an even-odd Sudoku board
     *
     * @param game an initial configuration
     */
    Board(@NotNull GameInstance game) {
        //TODO: implement this
        cells = new Cell[9][9];
        rowGroup = new Group[9];
        colGroup = new Group[9];
        squareGroup = new Group[3][3];

        for (int i = 0; i < 9; i++) {
            rowGroup[i] = new Group();
            colGroup[i] = new Group();
        }

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                squareGroup[i][j] = new Group();
            }
        }

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                cells[i][j] = new Cell(game.isEven(i, j) ? Cell.Type.EVEN : Cell.Type.ODD);
                rowGroup[i].addCell(cells[i][j]);
                colGroup[j].addCell(cells[i][j]);
                squareGroup[i / 3][j / 3].addCell(cells[i][j]);
            }
        }
    }

    /**
     * Returns a cell in the (i+1)-th row of (j+1)-th column, where 0 <= i, j < 9.
     *
     * @param i a row index
     * @param j a column index
     * @return a cell in the (i+1)-th row of (j+1)-th column
     */
    @NotNull
    Cell getCell(int i, int j) {
        //TODO: implement this
        return cells[i][j];
    }

    /**
     * Returns a group for the (i+1)-th row, where 0 <= i < 9.
     *
     * @param i a row index
     * @return a group for the (i+1)-th row
     */
    @NotNull
    Group getRowGroup(int i) {
        //TODO: implement this
        return rowGroup[i];
    }

    /**
     * Returns a group for the (j+1)-th column, where 0 <= j < 9.
     *
     * @param j a column index
     * @return a group for the (j+1)-th column
     */
    @NotNull
    Group getColGroup(int j) {
        //TODO: implement this
        return colGroup[j];
    }

    /**
     * Returns a square group for the (n+1)-th row of (m+1)-th column, where 1 <= n, m <= 3
     *
     * @param n a square row index
     * @param m a square column index
     * @return a square group for the (n+1)-th row of (m+1)-th column
     */
    @NotNull
    Group getSquareGroup(int n, int m) {
        //TODO: implement this
        return squareGroup[n][m];
    }
}
