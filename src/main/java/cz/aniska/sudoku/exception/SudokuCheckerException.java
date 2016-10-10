package cz.aniska.sudoku.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception indicates that sudoku board is corrupted.
 * Most relevant case is invalid dimensions of the board.
 *
 * @author Aliaksandr Aniska
 */
@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class SudokuCheckerException extends RuntimeException {

    public SudokuCheckerException(String message) {
        super(message);
    }
}
