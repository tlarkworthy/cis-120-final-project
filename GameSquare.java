import java.awt.Color;
import java.awt.Graphics;
import java.util.Arrays;

public class GameSquare {
    private GameSquare[] squares;
    private final boolean isBomb;
    private boolean flagged;
    private boolean covered;
    
    public GameSquare(GameSquare[] adjSqs, boolean isBomb) {
        //squares = Arrays.copyOf(adjSqs, 8);
        this.isBomb = isBomb;
        flagged = false;
        covered = true;
    }
    
    
    public boolean isBomb() {
        return isBomb;
    }
    
    public boolean covered() {
        return covered;
    }
    
    public void draw(Graphics g, int w, int h) {
        g.setColor(Color.DARK_GRAY);
        g.drawRect(0, 0, w, h);
    }
}
