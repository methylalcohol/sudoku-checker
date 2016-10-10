package cz.aniska.sudoku.service;

import cz.aniska.sudoku.exception.SudokuCheckerException;
import cz.aniska.sudoku.model.SudokuCheckerStatus;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * Service responsible to validate sudoku board and also to check whether current move
 * is successful. It also recognizes whether sudoku board is finished or not.
 *
 * @author Aliaksandr Aniska
 */
@Component
public class SudokuCheckerServceImpl implements SudokuCheckerService {

    /**
     *
     * @param board
     * @return
     */
    @Override
    public SudokuCheckerStatus checkSudokuBoard(final int[][] board) {
        //we do 9 iterations as we have 9 rows, 9 columns and 9 squares
        for (int i = 0; i < 9; i++) {
            //row is already known as it is first index of 2D array
            int[] row = board[i];
            //column should be constructed by iteration through the rows
            int[] column = new int[9];
            //square that becomes an 1D array.
            int[] square = new int[9];

            for (int rowIndex = 0; rowIndex < 9; rowIndex ++) {
                //building column
                column[rowIndex] = board[rowIndex][i];
                //unbend the square, we map i-square to 1D array
                square[rowIndex] = board[(i / 3) * 3 + rowIndex / 3][i * 3 % 9 + rowIndex % 3];
            }

            if (!validateParticularRowColumnOrSquare(row) ||
                    !validateParticularRowColumnOrSquare(column) ||
                        !validateParticularRowColumnOrSquare(square)) {
                return SudokuCheckerStatus.NOT_VALID_MOVE;
            }
        }

        return SudokuCheckerStatus.SUCCESS_MOVE;
    }

    /**
     *
     * @param numbers
     * @return
     */
    private boolean validateParticularRowColumnOrSquare(int[] numbers) {
        final Set<Integer> intSet = new HashSet<>();

        for (int number : numbers) {

            //empty boxes are valid, so we check can jump to the next one
            if (number == 0) { break; }

            //validate numbers in boxes
            if (number <= 0 || number > 9 || !intSet.add(number)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Method validates dimensions of sudoku board. In case of invalid
     * board dimensions SudokuCheckerException is thrown
     *
     * @param board for validation
     * @throws cz.aniska.sudoku.exception.SudokuCheckerException
     */
    @Override
    public void validateBoardDimensions(final int[][] board) {
        //check number of rows
        if (board == null || board.length != 9) {
            throw new SudokuCheckerException("Sudoku board dimensions are invalid!");
        }

        //check length of rows
        for (int [] row : board) {
            if (row == null || row.length != 9) {
                throw new SudokuCheckerException("Sudoku board dimensions are invalid!");
            }
        }
    }

}
