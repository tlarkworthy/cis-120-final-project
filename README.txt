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
                         are uncovered. I initially wanted to use a novel linked data structure to handle 
                         this, with GameSquare storing references to its 8 adjacent squares. However, I
                         decided against this after a discussion with TAs on Piazza in which they advised
                         me that this data structure was not actually necessary. Instead, I use a recursive
                         method to uncover tiles, since (under certain conditions) revealing one tile
                         can trigger those adjacent to it to be revealed, which can trigger the tiles
                         adjacent to those tiles to be revealed, and so on. A recursive method is the
                         only feasible way to implement this algorithm, as iteration would require many
                         passes and become unwieldy.

  2. I/O -- I/O is used to manage high scores. A user's score is their time to solve the board; a lower
            time is a better score. Scores.txt contains a list of the top 10 scores (sorted), along with
            the users who achieved them. The format of the user-score pairs is: [username]: [score]. When
            the high score window is opened, the text file is written to the screen. When the user wins a
            game, they have the chance to enter their username if they have achieved a new high score. 
            Their name and score are then written to the file in the correct position so as to maintain
            the order. This is handled by reading and parsing the lines of the file to correctly compare
            the user's score to the high scores and maintain the format of the file.

  3. 2D Array -- I use the 2D array to store the GameSquares that make up the Minesweeper board. This data
                 structure is ideal for the board as it allows for the indicies of the tiles in the 2D 
                 array to correspond to their positions on the board.

  4. JUnit Testing -- I test the methods that update the state of the GamePanel, including edge cases (such
                      as when the user tries to place a flag when the flag limit has been reached). I
                      also test helper methods that return meaningful state information, such as 
                      getAdjSqs(). Feedback on my proposal advised me to test the actual state of the game,
                      and that is what I aimed to do.


=========================
=: Your Implementation :=
=========================

- Provide an overview of each of the classes in your code, and what their
  function is in the overall game.
  
        Game -- Game is the class that contains the run() method to actually execute the program and
                display the JFrames. It also handles the timer that continuously displays the elapsed
                game time, the buttons for displaying instruction and high score windows (and the code for
                the windows themselves), as well as the I/O code for managing high scores. It handles the
                game's start and end states.
                
        GamePanel -- GamePanel stores a 2D array of GameSquares and manages the game board, translating
                     user input into meaningful changes in the game's state. Each time a change occurs, 
                     it also repaints itself.
        
        GameSquare -- GameSquare stores the state of an individual tile and provides methods for drawing
                      itself and accessing its stateful values.
        
        GamePanelTests -- This file includes JUnit tests of the methods in GamePanel that modify the
                           game's state.

- Were there any significant stumbling blocks while you were implementing your
  game (related to your design, or otherwise)?
  
        At first it was very difficult to get all the classes to communicate with each other properly. 
        The uncover method was challenging to implement because I had to ensure that I included the
        correct base cases. 


- Evaluate your design. Is there a good separation of functionality? How well is
  private state encapsulated? What would you refactor, if given the chance?

        The functionality is separated well. I don't think I would significantly refactor any part, 
        although there were some methods in GamePanel I made package-protected instead of private purely
        for the purposes of JUnit testing.

========================
=: External Resources :=
========================

- Cite any external resources (libraries, images, tutorials, etc.) that you may
  have used while implementing your game.
  
    I used StackOverflow for certain Swing elements (distinguishing left and right clicks, font size
    for JLabels), and I consulted the Javadocs for BufferedWriter and BufferedReader.


