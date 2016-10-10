package cz.aniska.sudoku.service;

import cz.aniska.sudoku.model.SudokuCheckerStatus;

/**
 * @author Aliaksandr Aniska
 */
public interface SudokuCheckerService {

    public void validateBoardDimensions(final int[][] board);

    public SudokuCheckerStatus checkSudokuBoard(int[][] board);
}
