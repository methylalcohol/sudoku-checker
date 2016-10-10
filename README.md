##Sudoku checker##

The Designed POST RESTful service is available on URL /checksudoku.
To post your sudoku please follow this simple json format (empty box is an empty string):
```json
[	["8", "2", "7", "1", "5", "4", "3", "9", "6"],
	["9", "6", "5", "3", "2", "7", "1", "4", "8"],
	["3", "4", "1", "6", "8", "9", "7", "5", "2"],
	["5", "9", "3", "", "6", "8", "2", "7", "1"],
	["4", "7", "2", "5", "1", "3", "6", "8", "9"],
	["6", "1", "8", "9", "", "2", "4", "3", "5"],
	["7", "8", "6", "2", "3", "5", "9", "1", "4"],
	["1", "5", "4", "7", "9", "6", "8", "2", "3"],
	["2", "3", "9", "8", "4", "1", "5", "6", "7"]
]
```

The service can response with 3 different statuses:

1. **NOT_VALID_MOVE** - Indicates that your sudoku board has invalid move, so try to fix it

2. **SUCCESS_MOVE** - Indicated that current move is on the good way to success

3. **SUDOKU_FINISHED** - Indicated that sudoku board is solved. Congrats!







