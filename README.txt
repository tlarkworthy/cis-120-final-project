=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
CIS 120 Game Project README
PennKey: tlarkwor
=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=

===================
=: Core Concepts :=
===================

- List the four core concepts, the features they implement, and why each feature
  is an appropriate use of the concept. Incorporate the feedback you got after
  submitting your proposal.

  1. Recursive method -- The uncover() method in GamePanel recursively calls itself until all valid squares
    are uncovered.

  2. I/O -- I/O is used to manage high scores.

  3. 2D Array -- I use the 2D array to store the GameSquares that make up the Minesweeper board.

  4. JUnit Testing -- I test the methods that update the state of the GamePanel.


=========================
=: Your Implementation :=
=========================

- Provide an overview of each of the classes in your code, and what their
  function is in the overall game.
  
        Game -- Game is the class that contains the run() method to actually exectue the program and
                display the JFrames.
                
        GamePanel -- GamePanel stores a 2D array of GameSquares and manages the game board.
        
        GameSquare -- GameSquare stores the state of an individual tile
        
        GameSquareTests -- the testing file.

- Were there any significant stumbling blocks while you were implementing your
  game (related to your design, or otherwise)?
  
        It was difficult to get all the parts communicating with each other


- Evaluate your design. Is there a good separation of functionality? How well is
  private state encapsulated? What would you refactor, if given the chance?

        The functionality is separated well. I don't think I would refactor any further.

========================
=: External Resources :=
========================

- Cite any external resources (libraries, images, tutorials, etc.) that you may
  have used while implementing your game.
  
    I used StackOverflow for certain Swing elements (distinguishing left and right clicks).


