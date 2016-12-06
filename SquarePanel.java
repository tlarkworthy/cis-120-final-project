import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JPanel;

public class SquarePanel extends JPanel {
    private GameSquare gs;
    public final int WIDTH = 50;
    public final int HEIGHT = 50;
    private boolean mouseClick;
    
    public SquarePanel(GameSquare gs) {
        this.gs = gs;
        mouseClick = false;
        addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e) {
                mouseClick = true;
                repaint();
            }
        });
    }
    
    
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        gs.draw(g, WIDTH, HEIGHT);
        if(mouseClick) {
            g.setColor(Color.RED);
            g.fillRect(1, 1, WIDTH - 1, HEIGHT - 1);
        }
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
