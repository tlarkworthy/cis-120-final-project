import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class Game implements Runnable
{
    public void run() {
        final JFrame frame = new JFrame("Minesweeper");
        frame.setLocation(300, 300);
        
       
        
        final JPanel controlPanel = new JPanel();
        final JLabel flagCount = new JLabel("000");
        final JButton instructions = new JButton("Instructions");
        instructions.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                final JFrame instrucFrame = new JFrame("Instructions");
                instrucFrame.setLocation(400, 400);
                instrucFrame.add(new JLabel("i love abhi"));
                instrucFrame.pack();
                instrucFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                instrucFrame.setVisible(true);
            }
        });
        final JLabel time = new JLabel("000");
        controlPanel.add(flagCount);
        controlPanel.add(instructions);
        controlPanel.add(time);
        
        frame.setResizable(false);
        final GamePanel gp = new GamePanel(flagCount, time);
        frame.add(gp, BorderLayout.CENTER);
        
        frame.add(controlPanel, BorderLayout.NORTH);
        
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Game());
    }

}


