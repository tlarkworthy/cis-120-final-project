import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class GamePanel extends JPanel {
    private GameSquare[][] gameSquares;
    private SquarePanel[][] squarePanels;
    private final int INTERVAL = 100;
    private final int BOARD_WIDTH = 8;
    private final int BOARD_HEIGHT = 8;
    private final int NUM_BOMBS = 10;
    private static int sec;
    private final int SAFE_SQUARES = BOARD_WIDTH * BOARD_HEIGHT - NUM_BOMBS;
    private int uncoveredCount;
    private int flagsLeft;
    private final Timer timer;
    private boolean firstClick;
    private long startTime;
    
    public GamePanel(JLabel flagCount, JLabel time) {
        firstClick = true;
        flagsLeft = NUM_BOMBS;
        uncoveredCount = 0;
        setLayout(new GridLayout(BOARD_HEIGHT, BOARD_WIDTH, 0, 0));
        squarePanels = new SquarePanel[BOARD_WIDTH][BOARD_HEIGHT];
        gameSquares = new GameSquare[BOARD_WIDTH][BOARD_HEIGHT];
        for(int x = 0; x < squarePanels.length; x++) {
            for(int y = 0; y < squarePanels[0].length; y++) {
                gameSquares[x][y] = new GameSquare(false);
            }
        } 
        
        DecimalFormat timeFormat = new DecimalFormat("000");
        timer = new Timer(INTERVAL, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                repaint();
                long timeNow = System.currentTimeMillis();
                sec = (int) (timeNow - startTime) / 1000;
                time.setText(timeFormat.format(sec));
                flagCount.setText(timeFormat.format(flagsLeft));
            }
        });
        
        for(int y = 0; y < BOARD_HEIGHT; y++) {
                for(int x = 0; x < BOARD_WIDTH; x++) {
                    GameSquare gs = gameSquares[x][y];
                    final int i = x;                
                    final int j = y;
                    squarePanels[x][y] = new SquarePanel(gameSquares[x][y]);
                    squarePanels[x][y].addMouseListener(new MouseAdapter(){
                    public void mouseClicked(MouseEvent e) {
                      if(SwingUtilities.isLeftMouseButton(e)){
                            if (firstClick) {
                                setup(i, j);
                                firstClick = false;
                                mouseClicked(e);
                                startTime = System.currentTimeMillis();
                                timer.start();
                                return;
                            }
                            if (!gs.flagged() && gs.isBomb()) {
                                gs.setLastBomb(true);
                                revealAllBombs();
                                Game.gameOver();
                            }
                            if(gs.covered() && !gs.flagged()) {
                                uncoveredCount += gs.uncover();
                                checkWin();
                            }
                        } else if(SwingUtilities.isRightMouseButton(e)) {
                            if (gs.covered() && gs.flagged()) {
                                flagsLeft++;
                                gs.toggleFlag();
                            } else if (gs.covered() && !gs.flagged() && flagsLeft > 0) {
                                flagsLeft--;
                                gs.toggleFlag();
                            }
                        }
                        repaint();
                    }
                });
                add(squarePanels[x][y], SwingConstants.CENTER);
            }
        }
    }
    
    private void setup(int u, int v) {
        Set<GameSquare> safe = new HashSet<GameSquare>();
        for (GameSquare gs : getAdjSqs(u, v)) {
            safe.add(gs);
        }
        Random rand = new Random();
        for (int i = 0; i < NUM_BOMBS; i++) {
            int x = 0;
            int y = 0;
            do {
                x = rand.nextInt(BOARD_WIDTH);
                y = rand.nextInt(BOARD_HEIGHT);
            } while (gameSquares[x][y].isBomb() || (x == u && y == v) || safe.contains(gameSquares[x][y]));
            gameSquares[x][y].setBomb(true);
        }
        
        for(int x = 0; x < squarePanels.length; x++) {
            for(int y = 0; y < squarePanels[0].length; y++) {
                GameSquare[] adjSqs = getAdjSqs(x,y);
                gameSquares[x][y].setSquares(adjSqs);
            }
        }

    }
    
    private void checkWin() {
        if (uncoveredCount == SAFE_SQUARES) {
            Game.win(sec);
        }
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
    
    private void revealAllBombs() {
        for (GameSquare[] g : gameSquares) {
            for (GameSquare gs : g){
                if (gs.isBomb()) {
                    gs.setGameOver(true);
                }
            }
        }
    }
    public void stopTimer() {
        timer.stop();
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
    
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(BOARD_WIDTH * squarePanels[0][0].getWidth(), BOARD_HEIGHT * squarePanels[0][0].getHeight());
    }
}

