package edu.postech.csed332.homework6;

import edu.postech.csed332.homework6.events.SetNumberEvent;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * An even-odd Sudoku board
 */
public class
Board {
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

        // 그룹을 초기화. 비어있는 상태.
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
                // Cell 생성자가 불리면서 possibility가 초기화
                cells[i][j] = new Cell(game.isEven(i, j) ? Cell.Type.EVEN : Cell.Type.ODD);

                // 그룹에 셀을 추가
                rowGroup[i].addCell(cells[i][j]);
                colGroup[j].addCell(cells[i][j]);
                squareGroup[i / 3][j / 3].addCell(cells[i][j]);

                // 셀에 그룹을 추가
                cells[i][j].addGroup(rowGroup[i]);
                cells[i][j].addGroup(colGroup[j]);
                cells[i][j].addGroup(squareGroup[i / 3][j / 3]);

            }
        }

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                // 이미 숫자가 들어있는 셀
                // -> SetNumberEvent를 날림
                // -> group안 cell들의 possibility에 반영 가능
                if (game.getNumbers(i, j).isPresent()) {
                    int presetNum = game.getNumbers(i, j).get();
//                    System.out.println("Board.java presetNum : " + presetNum);
                    cells[i][j].setNumber(presetNum);
                }
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
