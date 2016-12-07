import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class SquarePanel extends JPanel {
    private GameSquare gs;
    public final int WIDTH = 50;
    public final int HEIGHT = 50;
    private boolean mouseClick;
    
    public SquarePanel(GameSquare gameSq) {
        gs = gameSq;
        mouseClick = false;
        
    }
    
    
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(gs.covered()) {
            g.setColor(Color.GRAY);
            g.fillRect(1, 1, WIDTH - 1, HEIGHT - 1);
        }
        gs.draw(g, WIDTH, HEIGHT);
    }
    
    @Override
    public int getWidth() {
        return WIDTH + 1;
    }
    
    @Override
    public int getHeight() {
        return HEIGHT + 1;
    }
    
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(WIDTH, HEIGHT);
    }
    

}
