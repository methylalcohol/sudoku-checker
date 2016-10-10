package cz.aniska.sudoku.service;

import cz.aniska.sudoku.model.SudokuCheckerStatus;

/**
 * @author Aliaksandr Aniska
 */
public interface SudokuCheckerService {

    /**
     *
     * @param board
     */
    public void validateBoardDimensions(final int[][] board);

    /**
     *
     * @param board
     * @return
     */
    public SudokuCheckerStatus checkSudokuBoard(int[][] board);
}
