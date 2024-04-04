//ChessPage
package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChessPage {
    JFrame frame;
    private JButton menuButton;
    private JButton exitButton;
    InputAudio clickSound;

    public ChessPage() {
        frame = new JFrame();
        frame.getContentPane().setBackground(Color.black);
        frame.setLayout(new GridBagLayout());
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        clickSound = new InputAudio("ButtonClick.wav");

        Board3 board = new Board3();
        frame.add(board);
        frame.setVisible(true);

        //button yang dikanan
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setBackground(Color.BLACK);
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 5, 5, 5); //untuk merapihkan button yang disebelah kanan

        menuButton = new JButton("Menu");
        menuButton.setPreferredSize(new Dimension(200, 100));
        exitButton = new JButton("Exit");
        exitButton.setPreferredSize(new Dimension(200, 100));
        buttonPanel.add(menuButton, gbc);

        gbc.gridy = 1;
        buttonPanel.add(exitButton, gbc);

        menuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clickSound.ButtonClickSound();
                frame.dispose(); //menutup board
                new HomePage(); //back to menu
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clickSound.ButtonClickSound();
                System.exit(0); //exit button
            }
        });

        frame.add(buttonPanel, BorderLayout.EAST);
    }
}