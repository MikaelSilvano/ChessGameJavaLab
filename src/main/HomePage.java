package main;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomePage extends JFrame {
    JFrame frame;
    ImageIcon backgroundImage;
    ImageIcon playButtonIcon;
    ImageIcon singlePlayerIcon;
    ImageIcon multiPlayerIcon;
    ImageIcon easyDifficultyIcon;
    ImageIcon hardDifficultyIcon;
    InputAudio clickSound;

    public HomePage() {
        frame = new JFrame("Master Chess");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(Color.BLACK);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setResizable(false);

        try {
            backgroundImage = new ImageIcon("src/res/Background.png");
            playButtonIcon = new ImageIcon("src/res/PlayButton.png");
            singlePlayerIcon = new ImageIcon("src/res/SinglePlayer.png");
            multiPlayerIcon = new ImageIcon("src/res/MultiplayerOffline.png");
            easyDifficultyIcon = new ImageIcon("src/res/ComputerEasy.png");
            hardDifficultyIcon = new ImageIcon("src/res/ComputerHard.png");
        } catch (Exception e) {
            e.printStackTrace();
        }

        clickSound = new InputAudio("ButtonClick.wav");

        JButton playButton = new JButton(playButtonIcon);
        playButton.setBorderPainted(false);
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clickSound.ButtonClickSound();
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
                clickSound.ButtonClickSound();
                displayDifficultyOptions();
            }
        });

        JButton multiplayerOfflineButton = new JButton(multiPlayerIcon);
        multiplayerOfflineButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clickSound.ButtonClickSound();
                startGameMultiplayerOffline();
            }
        });

        JPanel difficultyPanel = new JPanel(new GridLayout(1, 1));
        difficultyPanel.add(singlePlayerButton);
        difficultyPanel.add(multiplayerOfflineButton);
        frame.add(difficultyPanel, BorderLayout.CENTER);
        frame.revalidate();
    }

    private void displayDifficultyOptions() {
        frame.getContentPane().removeAll();
        frame.repaint();
        JButton easyButton = new JButton(easyDifficultyIcon);
        easyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clickSound.ButtonClickSound();
                startGameSingleplayerEasy();
            }
        });

        JButton hardButton = new JButton(hardDifficultyIcon);
        hardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clickSound.ButtonClickSound();
                startGameSingleplayerHard();
            }
        });

        JPanel difficultyPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        difficultyPanel.add(easyButton);
        difficultyPanel.add(hardButton);
        frame.add(difficultyPanel, BorderLayout.CENTER);
        frame.revalidate();
    }

    private void startGameMultiplayerOffline() {
        new ChessPage();
        frame.dispose();
    }
    private void startGameSingleplayerEasy() {
        new AIChessPageEasy();
        frame.dispose();
    }
    private void startGameSingleplayerHard() {
        new AIChessPageHard();
        frame.dispose();
    }

    public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
    }
}
