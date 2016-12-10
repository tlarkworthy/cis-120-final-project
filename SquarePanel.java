import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class SquarePanel extends JPanel {
    private GameSquare gs;
    private final int WIDTH = 50;
    private final int HEIGHT = 50;
    
    public SquarePanel(GameSquare gameSq) {
        gs = gameSq;
    }
    
    
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(gs.covered()) {
            g.setColor(Color.GRAY);
            g.fillRect(1, 1, WIDTH - 1, HEIGHT - 1);
        }
        g.setColor(Color.DARK_GRAY);
        g.drawRect(0, 0, WIDTH, HEIGHT);
        if (gs.isLastBomb()) {
            g.setColor(Color.RED);
            g.fillRect(1, 1, WIDTH - 1, HEIGHT - 1);
        }
        
        if(gs.flagged()) {
            g.setColor(Color.BLUE);
            g.fillRect(WIDTH / 4, HEIGHT / 4, WIDTH / 2, HEIGHT / 2);
        }
        if(gs.isBomb() && gs.gameOver()) {
            g.setColor(Color.BLACK);
            g.fillOval(WIDTH / 4, HEIGHT / 4, WIDTH / 2, HEIGHT / 2);
        }
        if(gs.getBombs() != 0 && !gs.covered()) {
            g.setColor(Color.BLACK);
            g.setFont(new Font(g.getFont().getFontName(), g.getFont().getStyle(), g.getFont().getSize() + 15));
            g.drawString(Integer.toString(gs.getBombs()), WIDTH / 3, 3 * HEIGHT / 4);
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
