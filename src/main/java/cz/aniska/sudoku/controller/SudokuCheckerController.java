package cz.aniska.sudoku.controller;

import cz.aniska.sudoku.model.SudokuCheckerStatus;
import cz.aniska.sudoku.service.SudokuCheckerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Aliaksandr Aniska
 */
@RestController
public class SudokuCheckerController {

    @Autowired
    private SudokuCheckerService sudokuCheckerService;

    @RequestMapping(value="/checksudoku", method= RequestMethod.POST)
    public ResponseEntity<SudokuCheckerStatus> checkBoard(@RequestBody final int[][] sudokuBoard) {
        sudokuCheckerService.validateBoardDimensions(sudokuBoard);

        //todo check moves

        return new ResponseEntity<>(SudokuCheckerStatus.SUCCESS_MOVE, HttpStatus.OK);
    }

}
