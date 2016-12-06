import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class GamePanel extends JPanel {
    private GameSquare[][] gameSquares;
    private SquarePanel[][] squarePanels;
    private final int INTERVAL = 35;
    private final int BOARD_WIDTH = 10;
    private final int BOARD_HEIGHT = 10;
    
    public GamePanel(JLabel flagCount, JLabel time) {
        setLayout(new GridLayout(BOARD_HEIGHT, BOARD_WIDTH, 0, 0));
        squarePanels = new SquarePanel[BOARD_WIDTH][BOARD_HEIGHT];
        gameSquares = new GameSquare[BOARD_WIDTH][BOARD_HEIGHT];
        for(int x = 0; x < squarePanels.length; x++) {
            for(int y = 0; y < squarePanels[0].length; y++) {
                gameSquares[x][y] = new GameSquare(false);
            }
        } 
        gameSquares[4][5].isBomb = true;
        for(int x = 0; x < squarePanels.length; x++) {
            for(int y = 0; y < squarePanels[0].length; y++) {
                GameSquare[] adjSqs = getAdjSqs(x,y);
                gameSquares[x][y].setSquares(adjSqs);
            }
        }

        for(int x = 0; x < squarePanels.length; x++) {
            for(int y = 0; y < squarePanels[0].length; y++) {
                squarePanels[x][y] = new SquarePanel(gameSquares[x][y]);
                add(squarePanels[x][y]);
            }
        }
        
        
        
        Timer timer = new Timer(INTERVAL, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                repaint();
            }
        });
        timer.start();
    }
    
    private GameSquare[] getAdjSqs(int x, int y) {
        GameSquare[] ret = new GameSquare[8];
        int ii = 0;
        for(int i = x - 1; i <= x + 1; i++) {
            for(int j = y - 1; j <= y + 1; j++) {
                if( i >= 0 && j >= 0 && i < BOARD_WIDTH && j < BOARD_HEIGHT && (i != x || j != y)) {
                    ret[ii] = gameSquares[i][j];
                    ii++;
                }
            }
        }
        return ret;
    }
    
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
//        int width = squarePanels[0][0].WIDTH;
//        int height = squarePanels[0][0].HEIGHT;
//        super.paintComponent(g);
//        for(int x = 0; x < BOARD_WIDTH; x++) {
//            for(int y = 0; y < BOARD_HEIGHT; y++) {
//                squarePanels[x][y].paintComponent(g);
//                g.translate(0, height);
//            }
//            g.translate(0, -BOARD_WIDTH * width);
//            g.translate(width, 0);
//        }
    }
    
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(BOARD_WIDTH * squarePanels[0][0].WIDTH, BOARD_HEIGHT * squarePanels[0][0].HEIGHT);
    }
}
