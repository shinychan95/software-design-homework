package edu.postech.csed332.homework6;

import javax.swing.*;
import java.awt.*;

public class GameUI {
    private static final int unitSize = 10;
    private static final int width = 45;
    private static final int height = 45;

    final JFrame top;

    public GameUI(Board board) {
        top = new JFrame("Even/Odd Sudoku");
        top.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        top.setLayout(new GridLayout(3, 3));

        Dimension dim = new Dimension(unitSize * width, unitSize * height);
        top.setMinimumSize(dim);
        top.setPreferredSize(dim);

        createCellUI(board);

        top.pack();
        top.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Board board = new Board(GameInstanceFactory.createGameInstance());
            new GameUI(board);
        });
    }

    /**
     * Create UI for cells
     * @param board
     */
    private void createCellUI(Board board) {
        CellUI[][] cells = new CellUI[9][9];
        JPanel[][] squares = new JPanel[3][3];

        //TODO: implement this. Create cells and squares, to add them to top, and to define layouts for them.

        // JFrame 및 JPanel에 대해 이해하고, 작동 방식과 요소 안에 추가하는 방식에 대해 알아야 한다.
        // 게임 판에 대한 정보는 Board에 저장되어 있다. (Cell 및 Group)
        // number와 evenflag. 숫자가 없을 경우 null로 저장되어 있다.
        // group 내부에 cell들이 들어가는데, 구조가 어떻게 되어야 할까.

        // 우리가 GameUI 상에서 Cell 값을 변경하는 액션은 CellUI가 감지하여 반응한다.
    }

}