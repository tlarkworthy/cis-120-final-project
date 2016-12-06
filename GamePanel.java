import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class GamePanel extends JPanel {
    private GameSquare[][] gameSquares;
    private SquarePanel[][] squarePanels;
    private final int BOARD_WIDTH = 10;
    private final int BOARD_HEIGHT = 10;
    
    public GamePanel(JLabel flagCount, JLabel time) {
        setLayout(new GridLayout(BOARD_HEIGHT, BOARD_WIDTH, 0, 0));
        squarePanels = new SquarePanel[BOARD_WIDTH][BOARD_HEIGHT];
        for(int x = 0; x < squarePanels.length; x++) {
            for(int y = 0; y < squarePanels[0].length; y++) {
                squarePanels[x][y] = new SquarePanel(new GameSquare(null, false));
                add(squarePanels[x][y]);
            }
        }
    }
    
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
//        int width = squarePanels[0][0].WIDTH;
//        int height = squarePanels[0][0].HEIGHT;
//        super.paintComponent(g);
//        for(int x = 0; x < BOARD_WIDTH; x++) {
//            for(int y = 0; y < BOARD_HEIGHT; y++) {
//                squarePanels[x][y].paintComponent(g);
//                g.translate(0, height);
//            }
//            g.translate(0, -BOARD_WIDTH * width);
//            g.translate(width, 0);
//        }
    }
    
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(BOARD_WIDTH * squarePanels[0][0].WIDTH, BOARD_HEIGHT * squarePanels[0][0].HEIGHT);
    }
}
