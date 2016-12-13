import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;



public class GamePanelTests {
    GamePanel gp;
    
    @Before public void setUp() {
        gp = new GamePanel(8, 8, 10);
    }
    
    @Test public void noBombsBeforeSetup() {
        gp.uncover(6, 4);
        int uncovered = gp.getUncoveredCount();
        assertEquals("uncovered", uncovered, 64);
        assertTrue("Won", gp.checkWin());
    }
    
    @Test public void bombsPlaced() {
        gp.placeBombs(5, 7);
        GameSquare[][] gs = gp.getCopyOfBoard();
        int i = 0;
        for (GameSquare[] g : gs) {
            for (GameSquare s : g) {
                if (s.isBomb()) {
                    i++;
                }
            }
        }
        assertEquals("number of placed bombs", i, 10);
    }
    
    @Test public void testSetUp() {
        gp.placeBombs(5, 6);
        GameSquare[][] gs = gp.getCopyOfBoard();
        assertFalse("first square clicked not bomb", gs[5][7].isBomb());
        boolean adjacent = gs[4][5].isBomb() || gs[4][6].isBomb() || gs[4][7].isBomb() || gs[5][6].isBomb()
                           || gs[5][7].isBomb() || gs[6][5].isBomb() || gs[6][6].isBomb() || gs[6][7].isBomb();
        assertFalse("no square adjacent to first square is bomb", adjacent);
        
    }
    
    @Test public void testNumBombs() {
        GamePanel smallGp = new GamePanel(4, 4, 1);
        smallGp.setBomb(0, 0);
        GameSquare[][] gs = smallGp.getCopyOfBoard();
        
        assertEquals("number of adjacent bombs is 1 for (0,1)", gs[0][1].getBombs(), 1);
    }
    
    @Test public void cantExceedFlagLimit() {
        for (int i = 0; i < 10; i++) {
            if (i % 2 == 0) {
                gp.rightClick(i / 2, 0);
            } else {
                gp.rightClick(0, (i / 2) + 1);
            }
        }
            gp.rightClick(6, 7);
            GameSquare[][] gs = gp.getCopyOfBoard();
            assertFalse("can't place more than the flag limit", gs[6][7].flagged());

    }
    
    @Test public void adjacentSquaresTest() {
        GameSquare[][] gs = gp.getCopyOfBoard();
        GameSquare[] squares = gp.getAdjSqs(0, 0);
        assertEquals("length is still 8", squares.length, 8);
        boolean nulls = squares[0] != null &&
                        squares[1] != null &&
                        squares[2] != null &&
                        squares[3] == null &&
                        squares[4] == null &&
                        squares[5] == null &&
                        squares[6] == null &&
                        squares[7] == null;
        assertTrue("only 3 squares adjacent", nulls);
        ArrayList<GameSquare> list = new ArrayList<GameSquare>(3);
        list.add(squares[0]);
        list.add(squares[1]);
        list.add(squares[2]);
        assertTrue("correct adjacent squares", list.contains(gs[1][0]));
    }
    
    @Test public void uncoverCornerTest() {
        gp.setBomb(6, 6);
        gp.uncover(7, 7);
        GameSquare[][] gs = gp.getCopyOfBoard();
        assertFalse("only one square uncovered", gs[7][7].covered());
        assertTrue("only one square uncovered", gs[7][6].covered());
        assertTrue("only one square uncovered", gs[6][7].covered());
    }
    
    
    
    
    
}
