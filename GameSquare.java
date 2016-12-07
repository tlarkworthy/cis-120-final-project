import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Arrays;

public class GameSquare {
    private GameSquare[] squares;
    public boolean isBomb;
    private boolean flagged;
    private boolean covered;
    public boolean gameOver;
    public boolean lastBomb;
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
    
    public void toggleFlag() {
        flagged = !flagged;
    }
    
    public int uncover() {
        int i = 0;
        if (isBomb) {
            gameOver = true;
            return 0;
        }
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
    
    public void draw(Graphics g, int w, int h) {
        g.setColor(Color.DARK_GRAY);
        g.drawRect(0, 0, w, h);
        if (lastBomb) {
            g.setColor(Color.RED);
            g.fillRect(1, 1, w - 1, h - 1);
        }
        
        if(flagged) {
            g.setColor(Color.BLUE);
            g.fillRect(w/4, h/4, w/2, h/2);
        }
        if(isBomb && gameOver) {
            g.setColor(Color.BLACK);
            g.fillOval(w/4, h/4, w / 2, h / 2);
        }
        if(bombs != 0 && !covered) {
            g.setColor(Color.BLACK);
            //g.drawOval(3 * w / 8, 3*h/8, w/4, w/4);
            g.setFont(new Font(g.getFont().getFontName(), g.getFont().getStyle(), g.getFont().getSize() + 15));
            g.drawString(Integer.toString(bombs), w/ 3, 3 * h / 4);
        }
    }
}
