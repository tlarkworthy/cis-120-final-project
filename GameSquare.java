import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Arrays;

public class GameSquare {
    private GameSquare[] squares;
    private boolean isBomb;
    private boolean flagged;
    private boolean covered;
    private boolean gameOver;
    private boolean lastBomb;
    private int bombs;
    
    public GameSquare(boolean isBomb) {
        this.isBomb = isBomb;
        lastBomb = false;
        gameOver = false;
        flagged = false;
        covered = true;
        bombs = 0;
    }
    
    
    public boolean isBomb() {
        return isBomb;
    }
    
    public boolean isLastBomb() {
        return lastBomb;
    }
    
    public boolean gameOver() {
        return gameOver;
    }
    
    public boolean covered() {
        return covered;
    }
    
    public boolean flagged() {
        return flagged;
    }
    
    public int getBombs() {
        return bombs;
    }
    
    public void setBomb(boolean b) {
        isBomb = b;
    }
    
    public void setLastBomb (boolean b) {
        lastBomb = true;
    }
    
    public void setGameOver (boolean b) {
        gameOver = b;
    }
    public void setSquares(GameSquare[] adjSqs) {
        squares = Arrays.copyOf(adjSqs, 8);
        for(GameSquare gs : squares) {
            if(gs != null && gs.isBomb()) {
                bombs++;
            }
        }
    }
    
    
    public void toggleFlag() {
        flagged = !flagged;
    }
    
    public int uncover() {
        int i = 0;
        covered = false;
        i++;
        if (bombs == 0) {
            for(GameSquare gs : squares) {
                if (gs != null && gs.covered() && !gs.flagged()) {
                    i += gs.uncover();
                }
            }
        }
        return i;
    }
    
}
