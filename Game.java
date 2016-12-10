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
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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
        final JButton highScores = new JButton("High Scores");
        
        
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
        
        highScores.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                final JFrame scoreFrame = new JFrame("High Scores");
                //scoreFrame.setLayout(new FlowLayout());
                scoreFrame.setLocation(400, 400);
                scoreFrame.setSize(600, 500);
                List<String> scoreLines = new ArrayList<String>();
                try {
                    BufferedReader in = new BufferedReader(new FileReader(new File("scores.txt")));
                    String next = in.readLine();

                    Map<String, Integer> scores = new TreeMap<String, Integer>();
                    while(next != null) {
                        String name = parseName(next);
                        int s = parseScore(next);
                        scores.put(name, s);
                        next = in.readLine();
                    }
                    List<Integer> sscores = new ArrayList<Integer>(scores.values());
                    Collections.sort(sscores);
                    for (int i = 0; i < 10; i ++) {
                        for (String s : scores.keySet()) {
                            if (scores.get(s) == sscores.get(i)) {
                                scoreLines.add(s + ": " + sscores.get(i));
                            }
                        }
                    }
                    in.close();
                } catch (FileNotFoundException e1) {
                    System.exit(1);
                } catch (IOException e1) {
                    System.exit(1);
                }
                String label = "<html><p style=\"align:center\">";
                for (String s : scoreLines) {
                    label += s + "<br />";
                }
                label += "</p></html>";
                JLabel lose = new JLabel(label);
                lose.setFont(new Font(lose.getFont().getName(), lose.getFont().getStyle(), lose.getFont().getSize() * 2));
                scoreFrame.add(lose, SwingConstants.CENTER);
               
                //scoreFrame.add(new JLabel("i love abhi"));
                scoreFrame.pack();
                scoreFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                scoreFrame.setVisible(true);
            }
        });

        controlPanel.add(flagCount);
        controlPanel.add(instructions);
        controlPanel.add(highScores);
        controlPanel.add(time);
        
        frame.setResizable(false);
        gp = new GamePanel(flagCount, time);
        frame.add(gp, BorderLayout.CENTER);
        
        frame.add(controlPanel, BorderLayout.NORTH);
        
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        
        //win(0);
    }
    
    public static void gameOver() {
        gp.stopTimer();
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
    
    public static void win(int gameTime) {
        gp.stopTimer();
        JFrame endFrame = new JFrame("You Win!");
        endFrame.setLayout(new BorderLayout());
        endFrame.setLocation(500, 500);
        
        
        
        JLabel win = new JLabel("You Win! Time: " + gameTime, SwingConstants.CENTER);
        JTextField username = new JTextField("User", 1);
        JButton enterName = new JButton("Enter");
        enterName.setSize(50, 200);
        enterName.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                String name = username.getText();
                FileWriter out;
                try {
                    char[] chars = name.toCharArray();
                    for (int i = 0; i < chars.length; i++) {
                        if (chars[i] == 58) {
                            chars[i] = 0;
                        }
                    }
                    name = String.copyValueOf(chars);
                    out = new FileWriter(new File("scores.txt"), true);
                    out.append(name + ": " + gameTime + "\n");
                    out.flush();
                    out.close();
                } catch (IOException e1) {
                    System.out.println("error");
                    System.exit(0);
                }
                //System.out.println(name + " " + gameTime);
                System.exit(0);
            }
        });
        
        JPanel namePanel = new JPanel();
        namePanel.setPreferredSize(new Dimension(10, 25));
        namePanel.setLayout(new GridLayout(1, 3));
        namePanel.add(username);
        namePanel.add(enterName);
        
        win.setFont(new Font(win.getFont().getName(), win.getFont().getStyle(), win.getFont().getSize() * 3));
        endFrame.add(win, BorderLayout.NORTH);
        endFrame.add(namePanel, BorderLayout.SOUTH);
        endFrame.setSize(500, 200);
        endFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        endFrame.setVisible(true);
        frame.setEnabled(false);
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Game());
    }

}


