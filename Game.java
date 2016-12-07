import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class Game implements Runnable
{
    private static GamePanel gp;
    private static JFrame frame;
    public void run() {
        frame = new JFrame("Minesweeper");
        frame.setLocation(300, 300);
        
        final JLabel time = new JLabel("000");
        final JPanel controlPanel = new JPanel();
        final JLabel flagCount = new JLabel("000");
        time.setFont(new Font(time.getFont().getName(), time.getFont().getStyle(), time.getFont().getSize() * 2));
        flagCount.setFont(new Font(flagCount.getFont().getName(), flagCount.getFont().getStyle(), flagCount.getFont().getSize() * 2));
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

        controlPanel.add(flagCount);
        controlPanel.add(instructions);
        controlPanel.add(time);
        
        frame.setResizable(false);
        gp = new GamePanel(flagCount, time);
        frame.add(gp, BorderLayout.CENTER);
        
        frame.add(controlPanel, BorderLayout.NORTH);
        
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
    
    public static void gameOver() {
        gp.stopTimer();
        JFrame endFrame = new JFrame("You suck");
        endFrame.setLocation(500,500);
        JLabel lose = new JLabel("You lose you suck");
        lose.setFont(new Font(lose.getFont().getName(), lose.getFont().getStyle(), lose.getFont().getSize() * 4));
        endFrame.add(lose);
        endFrame.setSize(500, 500);
        endFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        endFrame.setVisible(true);
        frame.setEnabled(false);
    }
    
    public static void win(int gameTime) {
        gp.stopTimer();
        JFrame endFrame = new JFrame("You dont suck");
        endFrame.setLocation(500, 500);
        JLabel win = new JLabel("good job sharmouta: " + gameTime, SwingConstants.CENTER);
        win.setFont(new Font(win.getFont().getName(), win.getFont().getStyle(), win.getFont().getSize() * 4));
        endFrame.add(win);
        endFrame.setSize(700, 500);
        endFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        endFrame.setVisible(true);
        frame.setEnabled(false);
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Game());
    }

}


