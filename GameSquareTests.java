import org.junit.Test;
import org.junit.Before;

import static org.junit.Assert.*;

import javax.swing.JLabel;

import org.junit.After;



public class GameSquareTests {
    GamePanel gp;
    
    @Before public void setUp() {
        gp = new GamePanel(new JLabel(), new JLabel(), 8, 8, 10);
    }
    
    @Test public void noBombsBeforeSetup() {
        int uncovered = gp.uncover(6, 4);
        assertEquals("uncovered", uncovered, 64);
        //assertTrue("Won", gp.checkWin());
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
        GamePanel smallGp = new GamePanel(new JLabel(), new JLabel(), 4, 4, 1);
        smallGp.setBomb(0, 0);
        GameSquare[][] gs = smallGp.getCopyOfBoard();
        
        assertEquals("number of adjacent bombs is 1 for (0,1)", gs[0][1].getBombs(), 1);
    }
    
    
}
