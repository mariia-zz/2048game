Popular game 2048.
Its goal is to obtain tiles with a face value of 2048.
The game is implemented using the MVC pattern.
Main class - serves as the entry point to the application.

The field consists of 16 tiles (Tile class), each tile has a certain weight.

In the Model class:
- created a private constant FIELD_WIDTH = 4, which determines the width of the playing field;
- private two-dimensional array gameTiles consisting of objects of class Tile;
- the rollback method sets the current game state equal to the last one in the stack (previousStates and previousScores), makes it possible to rewind one move back.
- the randomMove method in the Model class makes a random move.
- the autoMove method, implements a smart move. The logic of this move was chosen by comparing all possible moves from the current state of the game.

At the beginning of the game, 2 tiles appear on the field, it can be 2 or 4 (probabilities are 0.9 and 0.1, respectively).

The methods compressTiles (Tile [] tiles) and mergeTiles (Tile [] tiles) have been created, which implement compression of tiles and merge of tiles of the same denomination. These methods are implemented to move to the left. If necessary, add one tile using the left method.

Other types of movements (right, down, up) are performed by turning the field 90 degrees plus movement to the left.

The Controller class inherits from the KeyAdapter class.

The View class inherits from the JPanel class, overrides the paint method and displays the current state of the model received through the controller.
The resetGame method in the Controller class should return the game to its initial state.