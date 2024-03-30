package main;

import main.ChessPage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomePage extends JFrame {
    JFrame frame;
    ImageIcon backgroundImage;
    ImageIcon playButtonIcon;
    ImageIcon singlePlayerIcon;
    ImageIcon normalDifficultyIcon;
    ImageIcon hardDifficultyIcon;

    public HomePage() {
        frame = new JFrame("Master Chess");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(Color.BLACK);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setResizable(false);

        try {
            backgroundImage = new ImageIcon(HomePage.class.getResource("/BackgroundMenu.png"));
            playButtonIcon = new ImageIcon(HomePage.class.getResource("/PlayButton.png"));
            singlePlayerIcon = new ImageIcon(HomePage.class.getResource("/Singleplayer_Icon.png"));
            normalDifficultyIcon = new ImageIcon(HomePage.class.getResource("/Normal_Icon.png"));
            hardDifficultyIcon = new ImageIcon(HomePage.class.getResource("/Hard_Icon.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        JButton playButton = new JButton(playButtonIcon);
        playButton.setBorderPainted(false);
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayOptions();
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(Color.BLACK);
        buttonPanel.add(playButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    private void displayOptions() {
        frame.getContentPane().removeAll();
        frame.repaint();

        JButton singlePlayerButton = new JButton(singlePlayerIcon);
        singlePlayerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayDifficultyOptions(true);
            }
        });

        JPanel optionsPanel = new JPanel(new GridLayout(1, 1));
        optionsPanel.add(singlePlayerButton);
        frame.add(optionsPanel, BorderLayout.CENTER);

        frame.revalidate();
    }

    private void displayDifficultyOptions(boolean isSinglePlayer) {
        frame.getContentPane().removeAll();
        frame.repaint();

        JButton normalButton = new JButton(normalDifficultyIcon);
        normalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame(true, "Normal");
            }
        });

        JButton hardButton = new JButton(hardDifficultyIcon);
        hardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame(true, "Hard");
            }
        });

        JPanel difficultyPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        difficultyPanel.add(normalButton);
        difficultyPanel.add(hardButton);
        frame.add(difficultyPanel, BorderLayout.CENTER);

        frame.revalidate();
    }

    private void startGame(boolean isSinglePlayer, String difficulty) {
        new ChessPage();
        frame.dispose();
    }

    public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
    }
}
