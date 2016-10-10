package cz.aniska.sudoku.model;

/**
 * Response wraps sudoku checker status due to proper json format on the client side.
 *
 * @author Aliaksandr Aniska
 */
public class SudokuCheckerStatusResponse {

    private final SudokuCheckerStatus status;

    public SudokuCheckerStatusResponse(SudokuCheckerStatus status) {
        this.status = status;
    }

    public SudokuCheckerStatus getStatus() {
        return status;
    }
}
