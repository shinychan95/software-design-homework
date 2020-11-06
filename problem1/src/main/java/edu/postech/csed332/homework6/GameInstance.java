package edu.postech.csed332.homework6;

import java.util.Optional;

/**
 * An even/odd Sudoku puzzle
 */
interface GameInstance {
    /**
     * Initial numbers (null if a cell is empty)
     *
     * @param i row index
     * @param j column index
     * @return the number in the (i+1) row of (j+1) column
     * 수도쿠 판에서 숫자 받아오는 메소드. 반환값이 null일 수 있으니 Optional 데이터타입 사용
     */
    Optional<Integer> getNumbers(int i, int j);

    /**
     * Flags for even or odd numbers
     *
     * @param i row index
     * @param j column index
     * @return return true if the (i+1) row of (j+1) column is even
     */
    boolean isEven(int i, int j);
}
