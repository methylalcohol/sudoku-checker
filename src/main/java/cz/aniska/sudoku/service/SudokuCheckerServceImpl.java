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

    private final int BOARD_SIZE = 9;
    private final int SQUARE_SIDE_LEN = (int) Math.sqrt(BOARD_SIZE);
    /**
     *
     * @param board
     * @return
     */
    @Override
    public SudokuCheckerStatus checkSudokuBoard(final int[][] board) {
        boolean emptyBoxFoundFlag = false;

        for (int i = 0; i < BOARD_SIZE; i++) {
            //row is already known as it is first index of 2D array
            final int[] row = board[i];
            //column should be constructed by iteration through the rows
            final int[] column = new int[BOARD_SIZE];
            //square that will become an 1D array.
            final int[] square = new int[BOARD_SIZE];

            for (int rowIndex = 0; rowIndex < BOARD_SIZE; rowIndex ++) {
                //building column
                column[rowIndex] = board[rowIndex][i];
                //unbend the square, we re-map i-square to 1D array
                square[rowIndex] = board[(i / SQUARE_SIDE_LEN) * SQUARE_SIDE_LEN + rowIndex / SQUARE_SIDE_LEN]
                                        [i * SQUARE_SIDE_LEN % BOARD_SIZE + rowIndex % SQUARE_SIDE_LEN];
            }

            //validation results
            CheckNumbersResult checkRowResult = validateParticularRowColumnOrSquare(row);
            CheckNumbersResult checkColumnResult = validateParticularRowColumnOrSquare(column);
            CheckNumbersResult checkSquare = validateParticularRowColumnOrSquare(square);

            //check whether sudoku can be potentially finished
            emptyBoxFoundFlag = checkRowResult.isSomeBoxEmpty() |
                    checkColumnResult.isSomeBoxEmpty() | checkSquare.isSomeBoxEmpty();

            if (!checkRowResult.isNumbersAreValid() ||
                    !checkColumnResult.isNumbersAreValid() ||
                        !checkSquare.isNumbersAreValid()) {

                return SudokuCheckerStatus.NOT_VALID_MOVE;
            }
        }

        if (!emptyBoxFoundFlag) {
            return SudokuCheckerStatus.SUDOKU_FINISHED;
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
                continue;
            }

            //validate numbers in boxes
            if (number < 0 || number > BOARD_SIZE || !intSetChecker.add(number)) {
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
    private final class CheckNumbersResult {
        private final boolean  someBoxEmpty;
        private final boolean  numbersAreValid;

        public CheckNumbersResult(boolean someBoxEmpty, boolean numbersAreValid) {
            this.someBoxEmpty = someBoxEmpty;
            this.numbersAreValid = numbersAreValid;
        }

        public boolean isSomeBoxEmpty() {
            return this.someBoxEmpty;
        }

        public boolean isNumbersAreValid() {
            return this.numbersAreValid;
        }
    }

}
