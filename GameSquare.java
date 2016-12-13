import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Arrays;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class GameSquare extends JPanel {
    private GameSquare[] squares;
    private boolean isBomb;
    private boolean flagged;
    private boolean covered;
    private boolean gameOver;
    private boolean lastBomb;
    private final int WIDTH = 50;
    private final int HEIGHT = 50;
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
    
    public void setBombs(int b) {
        bombs = b;
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
    
    public void reveal() {
        covered = false;
    }
    
    @Override
    public void paintComponent(Graphics g) {
        if (covered) {
            g.setColor(Color.GRAY);
            g.fillRect(1, 1, WIDTH - 1, HEIGHT - 1);
        }
        g.setColor(Color.DARK_GRAY);
        g.drawRect(0, 0, WIDTH, HEIGHT);
        if (lastBomb) {
            g.setColor(Color.RED);
            g.fillRect(1, 1, WIDTH - 1, HEIGHT - 1);
        }
        
        if (flagged) {
            g.setColor(Color.BLUE);
            g.fillRect(WIDTH / 4, HEIGHT / 4, WIDTH / 2, HEIGHT / 2);
        }
        if (isBomb && gameOver) {
            g.setColor(Color.BLACK);
            g.fillOval(WIDTH / 4, HEIGHT / 4, WIDTH / 2, HEIGHT / 2);
        }
        if (bombs != 0 && !covered) {
            g.setColor(Color.BLACK);
            g.setFont(new Font(g.getFont().getFontName(), g.getFont().getStyle(), g.getFont().getSize() + 15));
            g.drawString(Integer.toString(bombs), WIDTH / 3, 3 * HEIGHT / 4);
        }
    }
    
    @Override
    public int getWidth() {
        return WIDTH;
    }
    
    @Override
    public int getHeight() {
        return HEIGHT;
    }
    
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(WIDTH, HEIGHT);
    }
    
}
