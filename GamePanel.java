import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

@SuppressWarnings("serial")
public class GamePanel extends JPanel {
    private GameSquare[][] gameSquares;
    private int width;
    private int height;
    private int numBombs;
    private int safeSquares;
    private int uncoveredCount;
    private int flagsLeft;
    private boolean firstClick;
    
    public GamePanel(int w, int h, int nb) {
        width = w;
        height = h;
        numBombs = nb;
        firstClick = true;
        flagsLeft = nb;
        uncoveredCount = 0;
        safeSquares = width * height;
        gameSquares = new GameSquare[width][height];
        
        Game.updateFlagCount(flagsLeft);
        
        setLayout(new GridLayout(height, width, 0, 0));
        
        for(int y = 0; y < height; y++) {
                for(int x = 0; x < width; x++) {
                    gameSquares[x][y] = new GameSquare(false);
                    add(gameSquares[x][y], SwingConstants.CENTER);
                    final int i = x;                
                    final int j = y;
                    gameSquares[x][y].addMouseListener(new MouseAdapter(){
                        public void mouseClicked(MouseEvent e) {
                          if(SwingUtilities.isLeftMouseButton(e)) {
                                if (firstClick) {
                                    placeBombs(i, j);
                                    Game.start();
                                    firstClick = false;
                                    mouseClicked(e);
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
        safeSquares -= numBombs;
    }
    
    
    boolean checkWin() {
        return uncoveredCount == safeSquares;
    }
    
   void leftClick(int x, int y) {
        GameSquare gs = gameSquares[x][y];
        if (!gs.flagged() && gs.isBomb()) {
            gs.setLastBomb(true);
            revealAllBombs();
            Game.gameOver();
        }
        if(gs.covered() && !gs.flagged()) {
            uncover(x, y);
            if (checkWin()) {
                Game.win();
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
        Game.updateFlagCount(flagsLeft);
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
    
    void uncover(int x, int y) {
        GameSquare g = gameSquares[x][y];
        if (g.isBomb() || g.flagged()) {
            return;
        }
        if (gameSquares[x][y].getBombs() != 0) {
            gameSquares[x][y].reveal();
            uncoveredCount++;
            return;
        } else {
            gameSquares[x][y].reveal();
            uncoveredCount++;
        }
        for(int i = x - 1; i <= x + 1; i++) {
            for(int j = y - 1; j <= y + 1; j++) {
                if( i >= 0 && j >= 0 && i < width && j < height && (i != x || j != y)) {
                    GameSquare gs = gameSquares[i][j];
                    if (gs != null && gs.covered() && !gs.flagged()) {
                        gs.reveal();
                        uncover(i, j);
                    }
                }
            }
        }
    }
    
    int getUncoveredCount() {
        return uncoveredCount;
    }
    
    GameSquare[] getAdjSqs(int x, int y) {
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
    
    void revealAllBombs() {
        for (GameSquare[] g : gameSquares) {
            for (GameSquare gs : g){
                if (gs.isBomb()) {
                    gs.setGameOver(true);
                }
            }
        }
    }
    
    
    GameSquare[][] getCopyOfBoard() {
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

