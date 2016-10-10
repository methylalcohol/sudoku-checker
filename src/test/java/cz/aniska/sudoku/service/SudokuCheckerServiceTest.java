package cz.aniska.sudoku.service;

import cz.aniska.sudoku.exception.SudokuCheckerException;
import cz.aniska.sudoku.model.SudokuCheckerStatus;
import cz.aniska.sudoku.service.SudokuCheckerService;
import cz.aniska.sudoku.service.SudokuCheckerServiceImpl;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @author Aliaksandr Aniska
 */
public class SudokuCheckerServiceTest {

    private SudokuCheckerServiceImpl service;

    @Before
    public void setUp() {
        service = new SudokuCheckerServiceImpl();
    }

    @Test
    public void testSuccessfulMove() {
        SudokuCheckerStatus sudokuCheckerStatus = service.checkSudokuBoard(SUCCESSFUL_MOVE);
        assertEquals(SudokuCheckerStatus.SUCCESS_MOVE, sudokuCheckerStatus);
    }

    @Test
    public void testInvalidMove() {
        SudokuCheckerStatus sudokuCheckerStatus = service.checkSudokuBoard(INVALID_MOVE);
        assertEquals(SudokuCheckerStatus.NOT_VALID_MOVE, sudokuCheckerStatus);
    }

    @Test
    public void testFinishedSudoku() {
        SudokuCheckerStatus sudokuCheckerStatus = service.checkSudokuBoard(FINISHED_SUDOKU);
        assertEquals(SudokuCheckerStatus.SUDOKU_FINISHED, sudokuCheckerStatus);
    }

    @Test
    public void testValidateNumbersMethod() {
        SudokuCheckerServiceImpl.CheckNumbersResult checkResult =
                service.validateParticularRowColumnOrSquare(new int[]{1, 3, 4, 5, 6, 7, 8, 9});
        assertTrue(checkResult.isNumbersAreValid());
        assertFalse(checkResult.isSomeBoxEmpty());

        checkResult = service.validateParticularRowColumnOrSquare(new int[]{1, 0, 4, 5, 6, 7, 8, 9});
        assertTrue(checkResult.isNumbersAreValid());
        assertTrue(checkResult.isSomeBoxEmpty());

        checkResult = service.validateParticularRowColumnOrSquare(new int[]{1, 2, 4, 4, 6, 7, 8, 9});
        assertFalse(checkResult.isNumbersAreValid());
        assertFalse(checkResult.isSomeBoxEmpty());

        checkResult = service.validateParticularRowColumnOrSquare(new int[]{1, 0, 4, 4, 6, 7, 8, 9});
        assertFalse(checkResult.isNumbersAreValid());
        assertTrue(checkResult.isSomeBoxEmpty());
    }

    @Test(expected = SudokuCheckerException.class)
    public void testInvalidDimensionsOfBoard() {
        service.validateBoardDimensions(new int [][] { { 1, 3, 4, 5}});
    }
    private final int[][] SUCCESSFUL_MOVE = {
            {8, 2, 7, 1, 5, 4, 3, 9, 6},
            {9, 6, 5, 3, 2, 0, 1, 4, 8},
            {3, 4, 1, 6, 8, 9, 7, 5, 2},
            {5, 9, 3, 4, 6, 8, 2, 7, 1},
            {4, 7, 2, 5, 1, 3, 6, 8, 9},
            {6, 1, 8, 9, 7, 2, 4, 3, 5},
            {7, 8, 6, 2, 3, 5, 9, 1,4},
            {1, 5, 4, 7, 9, 6, 8, 2, 3},
            {2, 3, 9, 8, 4, 1, 5, 6, 7}};

    private final int[][] FINISHED_SUDOKU = {
            {8, 2, 7, 1, 5, 4, 3, 9, 6},
            {9, 6, 5, 3, 2, 7, 1, 4, 8},
            {3, 4, 1, 6, 8, 9, 7, 5, 2},
            {5, 9, 3, 4, 6, 8, 2, 7, 1},
            {4, 7, 2, 5, 1, 3, 6, 8, 9},
            {6, 1, 8, 9, 7, 2, 4, 3, 5},
            {7, 8, 6, 2, 3, 5, 9, 1,4},
            {1, 5, 4, 7, 9, 6, 8, 2, 3},
            {2, 3, 9, 8, 4, 1, 5, 6, 7}};

    private final int[][] INVALID_MOVE = {
            {8, 2, 7, 1, 5, 4, 3, 9, 6},
            {9, 6, 5, 0, 2, 7, 1, 4, 8},
            {3, 4, 1, 6, 8, 9, 7, 5, 2},
            {5, 9, 3, 0, 6, 8, 2, 7, 1},
            {4, 7, 2, 5, 1, 3, 6, 8, 9},
            {6, 1, 8, 0, 7, 2, 4, 3, 5},
            {7, 8, 6, 2, 2, 5, 9, 1,4},
            {1, 5, 4, 7, 9, 6, 8, 2, 3},
            {2, 3, 9, 8, 4, 1, 5, 6, 7}};
}
