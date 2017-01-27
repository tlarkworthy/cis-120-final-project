import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.*;


public class Game implements Runnable
{
    private static final int BOARD_WIDTH = 8;
    private static final int BOARD_HEIGHT = 8;
    private static final int NUM_BOMBS = 10;
    private static final int INTERVAL = 100;
    private static long startTime;
    private static Timer timer;
    private static int sec;
    private static GamePanel gp;
    private static JFrame frame;
    private static JLabel flagCount;
    public void run() {
        frame = new JFrame("Minesweeper");
        frame.setLocation(300, 300);
        final JLabel time = new JLabel("000");
        final JPanel controlPanel = new JPanel();
        flagCount = new JLabel("000");
        time.setFont(new Font(time.getFont().getName(), time.getFont().getStyle(), time.getFont().getSize() * 2));
        flagCount.setFont(new Font(flagCount.getFont().getName(), flagCount.getFont().getStyle(), flagCount.getFont().getSize() * 2));
        final JButton instructions = new JButton("Instructions");
        final JButton highScores = new JButton("High Scores");
        
        
        instructions.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                final JFrame instrucFrame = new JFrame("Instructions");
                instrucFrame.setLocation(400, 400);
                instrucFrame.setSize(750, 300);
                instrucFrame.setLayout(new GridLayout(15, 1));
                instrucFrame.add(new JLabel("Instructions:\n", SwingConstants.CENTER));
                instrucFrame.add(new JLabel("Minesweeper is a game in which the goal is to uncover squares and avoid mines.", 
                        SwingConstants.CENTER));
                instrucFrame.add(new JLabel("If you hit a mine, you lose!", SwingConstants.CENTER));
                instrucFrame.add(new JLabel("Left-click on a square to uncover it.", SwingConstants.CENTER));
                instrucFrame.add(new JLabel("If you reveal a tile without a mine under it, it will display the number of"
                        + " adjacent mines.", SwingConstants.CENTER));
                instrucFrame.add(new JLabel("Right-click on a square to flag it as a mine.\n Right-click again to remove "
                        + "the flag.", SwingConstants.CENTER));
                instrucFrame.add(new JLabel("If you manage to uncover all non-mine tiles, you win!", SwingConstants.CENTER));
                instrucFrame.add(new JLabel("The upper right counter displays the elapsed time.", SwingConstants.CENTER));
                instrucFrame.add(new JLabel("If you solve the board "
                        + "with a low enough time, you can make the top 10 high score list!", SwingConstants.CENTER));
                instrucFrame.add(new JLabel("Good luck!", SwingConstants.CENTER));
                instrucFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                instrucFrame.setVisible(true);
            }
        });
        
        highScores.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                final JFrame scoreFrame = new JFrame("High Scores");
                scoreFrame.setLayout(new GridLayout(11, 1));
                scoreFrame.setLocation(400, 400);
                scoreFrame.setSize(600, 500);
                JLabel title = new JLabel("High Scores");
                title.setFont(title.getFont().deriveFont((float) 25));
                scoreFrame.add(title);
                try {
                    BufferedReader in = new BufferedReader(new FileReader(new File("scores.txt")));
                    for (int i = 0; i < 10; i++) {
                        JLabel l = new JLabel((i + 1) + ".  " + in.readLine(), SwingConstants.LEFT);
                        l.setFont(l.getFont().deriveFont((float) 20));
                        scoreFrame.add(l);
                    }
                   
                    in.close();
                } catch (FileNotFoundException e1) {
                    System.exit(1);
                } catch (IOException e1) {
                    System.exit(1);
                } catch (NullPointerException e1) {
                    System.out.print("scores.txt format corrupted!");
                    System.exit(1);
                }
                
                scoreFrame.pack();
                scoreFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                scoreFrame.setVisible(true);
            }
        });
        
        DecimalFormat timeFormat = new DecimalFormat("000");
        flagCount.setText(timeFormat.format(0));
        timer = new Timer(INTERVAL, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                long timeNow = System.currentTimeMillis();
                sec = (int) (timeNow - startTime) / 1000;
                time.setText(timeFormat.format(sec));
            }
        });
        
        controlPanel.add(flagCount);
        controlPanel.add(instructions);
        controlPanel.add(highScores);
        controlPanel.add(time);
        
        frame.setResizable(false);
        gp = new GamePanel(BOARD_WIDTH, BOARD_HEIGHT, NUM_BOMBS);
        frame.add(gp, BorderLayout.CENTER);
        
        frame.add(controlPanel, BorderLayout.NORTH);
        
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        
    }
    
    public static void gameOver() {
        timer.stop();
        JFrame endFrame = new JFrame("Game Over");
        endFrame.setLocation(500,500);
        JLabel lose = new JLabel("<html><p style=\"text-align: center\">You hit a mine! Game over!</p></html>");
        lose.setFont(new Font(lose.getFont().getName(), lose.getFont().getStyle(), lose.getFont().getSize() * 4));
        endFrame.add(lose);
        endFrame.setSize(500, 500);
        endFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        endFrame.setVisible(true);
        frame.setEnabled(false);
    }
    
    public static void updateFlagCount(int flags) {
        if (flagCount == null) {
            return;
        }
        DecimalFormat flagFormat = new DecimalFormat("000");
        flagCount.setText(flagFormat.format(flags));
    }
    
    private static String parseName(String input) throws IOException {
        StringReader in = new StringReader(input);
        int c = in.read();
        String out = "";
        while (c != -1 && c != 58) {
            out = out + (char) c;
            c = in.read();
        }
        return out;
    }
    
    private static int parseScore(String input) throws IOException {
        StringReader in = new StringReader(input);
        int c = in.read();
        String out = "";
        while (c != 58) {
            c = in.read();
        }
        c = in.read();
        while (c != 10 && c != -1) {
            if (Character.isDigit(c)) {
                out = out + (char) c;
            }
            c = in.read();
        }
        return Integer.parseInt(out);
    }
    
    public static void start() {
        timer.start();
        startTime = System.currentTimeMillis();
    }
    
    public static void win() {
        timer.stop();
        JFrame endFrame = new JFrame("You Win!");
        endFrame.setLayout(new BorderLayout());
        endFrame.setLocation(500, 500);
        
        List<String> scoreLines = new ArrayList<String>();
        try {
            BufferedReader in = new BufferedReader(new FileReader(new File("scores.txt")));

            ArrayList<Integer> scores = new ArrayList<Integer>(11);
            ArrayList<String> users = new ArrayList<String>(11);
            for (int i = 0; i < 10; i++){            
                String next = in.readLine();
                String n = parseName(next);
                int s = parseScore(next);
                scores.add(i, s);
                users.add(i, n);
            }
            if (sec <= scores.get(9)) {
                JLabel newHighScore = new JLabel("New High Score!", SwingConstants.CENTER);
                newHighScore.setFont(new Font(newHighScore.getFont().getFontName(), newHighScore.getFont().getStyle(), 
                        newHighScore.getFont().getSize() * 2));
                JTextField username = new JTextField("User", 1);
                JButton enterName = new JButton("Enter");
                JPanel namePanel = new JPanel();
                namePanel.setPreferredSize(new Dimension(10, 25));
                namePanel.setLayout(new GridLayout(1, 3));
                namePanel.add(username);
                namePanel.add(enterName);
                enterName.setSize(50, 200);
                endFrame.add(newHighScore, BorderLayout.CENTER);
                endFrame.add(namePanel, BorderLayout.SOUTH);
                enterName.addActionListener(new ActionListener(){
                    public void actionPerformed(ActionEvent e) {
                        String name = username.getText();
                        FileWriter out;
                        try {
                            //sanitize the name input
                            char[] chars = name.toCharArray();
                            for (int i = 0; i < chars.length; i++) {
                                if (chars[i] == 58) {
                                    chars[i] = 0;
                                }
                            }
                            name = String.copyValueOf(chars);
                            out = new FileWriter(new File("scores.txt"));
                            boolean written = false;
                            for (int i = 0; i < 10; i++) {
                                if (sec <= scores.get(i) && !written) {
                                    scores.add(i, sec);
                                    users.add(i, name);
                                    written = true;
                                }
                                out.write(users.get(i) + ": " + scores.get(i) + "\n");
                            }

                            out.flush();
                            out.close();
                        } catch (IOException e1) {
                            System.out.println("error");
                            System.exit(0);
                        }
                        System.exit(0);
                    }
                });
            }
                
            in.close();
        } catch (FileNotFoundException e1) {
            System.out.println("No scores.txt file found!");
            System.exit(1);
        } catch (IOException e1) {
            System.out.println("Input error");
            System.exit(1);
        }
        
        JLabel win = new JLabel("You Win! Time: " + sec, SwingConstants.CENTER);
        
        
        win.setFont(new Font(win.getFont().getName(), win.getFont().getStyle(), win.getFont().getSize() * 3));
        endFrame.add(win, BorderLayout.NORTH);
        endFrame.setSize(500, 200);
        endFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        endFrame.setVisible(true);
        frame.setEnabled(false);
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Game());
    }

}


