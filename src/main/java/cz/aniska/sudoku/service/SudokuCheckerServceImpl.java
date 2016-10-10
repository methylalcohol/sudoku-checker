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

        boolean someBoxEmptyEmptyFlag = false;

        for (int i = 0; i < 9; i++) {
            //row is already known as it is first index of 2D array
            final int[] row = board[i];
            //column should be constructed by iteration through the rows
            final int[] column = new int[9];
            //square that will become an 1D array.
            final int[] square = new int[9];

            for (int rowIndex = 0; rowIndex < 9; rowIndex ++) {
                //building column
                column[rowIndex] = board[rowIndex][i];
                //unbend the square, we re-map i-square to 1D array
                square[rowIndex] = board[(i / 3) * 3 + rowIndex / 3][i * 3 % 9 + rowIndex % 3];
            }

            //validation results
            CheckNumbersResult checkRowResult = validateParticularRowColumnOrSquare(row);
            CheckNumbersResult checkColumnResult = validateParticularRowColumnOrSquare(column);
            CheckNumbersResult checkSquare = validateParticularRowColumnOrSquare(square);

            //check whether sudoku can be potentially finished
            someBoxEmptyEmptyFlag = checkRowResult.isSomeBoxEmpty() |
                    checkColumnResult.isSomeBoxEmpty() | checkSquare.isSomeBoxEmpty();

            if (!checkRowResult.isNumbersAreValid() ||
                    !checkColumnResult.isNumbersAreValid() ||
                        !checkSquare.isNumbersAreValid()) {

                return SudokuCheckerStatus.NOT_VALID_MOVE;
            } else if (someBoxEmptyEmptyFlag) {
                return SudokuCheckerStatus.SUDOKU_FINISHED;
            }
        }

        return SudokuCheckerStatus.SUCCESS_MOVE;
    }

    /**
     *
     * @param numbers
     * @return
     */
    private CheckNumbersResult validateParticularRowColumnOrSquare(final int[] numbers) {
        //set does not allow duplicates, great technique for this case
        //as sudoku does not allow duplicate numbers in rows, columns or squares
        final Set<Integer> intSetChecker = new HashSet<>();

        boolean emptyBoxDetected = false;

        for (int number : numbers) {
            //empty boxes are valid (zero), so we check can jump to the next one
            if (number == 0) {
                emptyBoxDetected = true;
                break;
            }

            //validate numbers in boxes
            if (number <= 1 || number > 9 || !intSetChecker.add(number)) {
                return new CheckNumbersResult(emptyBoxDetected, false);
            }
        }

        return new CheckNumbersResult(emptyBoxDetected, true);
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

    /**
     * Board number validate class helper that indicates whether all numbers of row, column or square are valid.
     * Also it keeps information whether some number boxes were empty to help to indicate whether sudoku board
     * could potentially be successfully finished or not
     *
     * @author Aliaksandr Aniska
     */
    private class CheckNumbersResult {
        private boolean someBoxEmpty;
        private boolean numbersAreValid;

        public CheckNumbersResult(boolean someBoxEmpty, boolean numbersAreValid) {
            this.someBoxEmpty = someBoxEmpty;
            this.numbersAreValid = numbersAreValid;
        }

        public boolean isSomeBoxEmpty() {
            return someBoxEmpty;
        }

        public boolean isNumbersAreValid() {
            return numbersAreValid;
        }
    }

}
