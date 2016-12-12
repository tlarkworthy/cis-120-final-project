import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
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
    private final int INTERVAL = 100;
    private int width;
    private int height;
    private int numBombs;
    private int sec;
    private final int SAFE_SQUARES;
    int uncoveredCount;
    private int flagsLeft;
    private final Timer timer;
    private boolean firstClick;
    private long startTime;
    
    public GamePanel(JLabel flagCount, JLabel time, int w, int h, int nb) {
        width = w;
        height = h;
        numBombs = nb;
        firstClick = true;
        flagsLeft = nb;
        uncoveredCount = 0;
        SAFE_SQUARES = width * height - numBombs;
        
        setLayout(new GridLayout(height, width, 0, 0));
        gameSquares = new GameSquare[width][height];
        
        for(int y = 0; y < gameSquares.length; y++) {
            for(int x = 0; x < gameSquares[0].length; x++) {
                gameSquares[x][y] = new GameSquare(false);
                add(gameSquares[x][y], SwingConstants.CENTER);
            }
        } 
        
        DecimalFormat timeFormat = new DecimalFormat("000");
        flagCount.setText(timeFormat.format(flagsLeft));
        timer = new Timer(INTERVAL, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                long timeNow = System.currentTimeMillis();
                sec = (int) (timeNow - startTime) / 1000;
                time.setText(timeFormat.format(sec));
                flagCount.setText(timeFormat.format(flagsLeft));
            }
        });
        
        for(int y = 0; y < height; y++) {
                for(int x = 0; x < width; x++) {
                    GameSquare gs = gameSquares[x][y];
                    final int i = x;                
                    final int j = y;
                    gameSquares[x][y].addMouseListener(new MouseAdapter(){
                    public void mouseClicked(MouseEvent e) {
                      if(SwingUtilities.isLeftMouseButton(e)) {
                            if (firstClick) {
                                placeBombs(i, j);
                                timer.start();
                                mouseClicked(e);
                                startTime = System.currentTimeMillis();
                                return;
                            }
                            leftClick(i, j);
                        } else if(SwingUtilities.isRightMouseButton(e)) {
                            rightClick(i, j);
                        }
                        repaint();
                    }
                });
            }
        }

    }
    
    void placeBombs(int u, int v) {
        Set<GameSquare> safe = new HashSet<GameSquare>();
        for (GameSquare gs : getAdjSqs(u, v)) {
            safe.add(gs);
        }
        System.out.println(safe.size());
        Random rand = new Random();
        for (int i = 0; i < numBombs; i++) {
            int x = 0;
            int y = 0;
            do {
                x = rand.nextInt(width);
                y = rand.nextInt(height);
            } while (gameSquares[x][y].isBomb() || (x == u && y == v) || safe.contains(gameSquares[x][y]));
            gameSquares[x][y].setBomb(true);
        }
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int i = 0;
                for (GameSquare gs : getAdjSqs(x, y)) {
                    if (gs != null && gs.isBomb()) {
                        i++;
                    }
                }
                gameSquares[x][y].setBombs(i);
            }
        }
        firstClick = false;
    }
    
    
    boolean checkWin() {
        return uncoveredCount == SAFE_SQUARES;
    }
    
   void leftClick(int x, int y) {
        GameSquare gs = gameSquares[x][y];
        if (!gs.flagged() && gs.isBomb()) {
            gs.setLastBomb(true);
            revealAllBombs();
            Game.gameOver();
        }
        if(gs.covered() && !gs.flagged()) {
            uncoveredCount += uncover(x, y);
            if (checkWin()) {
                Game.win(sec);
            }
        }
    }
    
    void rightClick(int x, int y) {
        GameSquare gs = gameSquares[x][y];
        if(!gs.covered()) {
            return;
        }
        if (gs.flagged()) {
            flagsLeft++;
            gs.toggleFlag();
        } else if (flagsLeft > 0) {
            flagsLeft--;
            gs.toggleFlag();
        }
    }
    
    void setBomb(int i, int j) {
        gameSquares[i][j].setBomb(true);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int ii = 0;
                for (GameSquare gs : getAdjSqs(x, y)) {
                    if (gs != null && gs.isBomb()) {
                        ii++;
                    }
                }
                gameSquares[x][y].setBombs(ii);
            }
        }
    }
    
    int uncover(int x, int y) {
        int ii = 0;
        GameSquare g = gameSquares[x][y];
        if (g.isBomb() || g.flagged()) {
            return 0;
        }
        if (gameSquares[x][y].getBombs() != 0) {
            gameSquares[x][y].reveal();
            return 1;
        } else {
            gameSquares[x][y].reveal();
            ii++;
        }
        for(int i = x - 1; i <= x + 1; i++) {
            for(int j = y - 1; j <= y + 1; j++) {
                if( i >= 0 && j >= 0 && i < width && j < height && (i != x || j != y)) {
                    GameSquare gs = gameSquares[i][j];
                    if (gs != null && gs.covered() && !gs.flagged()) {
                        gs.reveal();
                        ii += uncover(i, j);
                    }
                }
            }
        }
        return ii;
    }
    
    private GameSquare[] getAdjSqs(int x, int y) {
        GameSquare[] ret = new GameSquare[8];
        int ii = 0;
        for(int i = x - 1; i <= x + 1; i++) {
            for(int j = y - 1; j <= y + 1; j++) {
                if( i >= 0 && j >= 0 && i < width && j < height && (i != x || j != y)) {
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
    
    public GameSquare[][] getCopyOfBoard() {
        GameSquare[][] ret = new GameSquare[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y ++) {
                ret[x][y] = gameSquares[x][y];
            }
        }
        return ret;
    }
    
    
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(width * gameSquares[0][0].getWidth(), height * gameSquares[0][0].getHeight());
    }
}

