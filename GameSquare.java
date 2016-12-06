import java.awt.Color;
import java.awt.Graphics;
import java.util.Arrays;

public class GameSquare {
    private GameSquare[] squares;
    public boolean isBomb;
    private boolean flagged;
    private boolean covered;
    private int bombs;
    
    public GameSquare(boolean isBomb) {
        this.isBomb = isBomb;
        flagged = false;
        covered = true;
        bombs = 0;
    }
    
    
    public boolean isBomb() {
        return isBomb;
    }
    
    public boolean covered() {
        return covered;
    }
    
    public boolean flagged() {
        return flagged;
    }
    
    public void setSquares(GameSquare[] adjSqs) {
        squares = Arrays.copyOf(adjSqs, 8);
        for(GameSquare gs : squares) {
            if(gs != null && gs.isBomb()) {
                bombs++;
            }
        }
    }
    
    public void flag() {
        flagged = true;
    }
    
    public void uncover() {
        if (isBomb) {
            Game.gameOver();
            return;
        }
        covered = false;
        if (bombs == 0) {
            for(GameSquare gs : squares) {
                if (gs != null && gs.covered()) {
                    gs.uncover();
                }
            }
        }
    }
    
    public void draw(Graphics g, int w, int h) {
        g.setColor(Color.DARK_GRAY);
        g.drawRect(0, 0, w, h);
        if(isBomb) {
            g.setColor(Color.BLACK);
            g.fillOval(w/4, h/4, w / 2, h / 2);
        }
        if(flagged) {
            g.setColor(Color.RED);
            g.fillRect(w/4, h/4, w/2, h/2);
        }
        if(bombs != 0 && !covered) {
            g.setColor(Color.BLACK);
            g.drawOval(3 * w / 8, 3*h/8, w/4, w/4);
        }
    }
}
