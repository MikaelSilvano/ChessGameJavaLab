package main;

import javax.swing.*;
import java.awt.*;

public class Main3 {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.getContentPane().setBackground(Color.black);
        frame.setLayout(new GridBagLayout());
        frame.setMinimumSize(new Dimension(1000, 1000));
        frame.setLocationRelativeTo(null);

        Board3 board = new Board3();
        frame.add(board);
        frame.setVisible(true);
    }
}
