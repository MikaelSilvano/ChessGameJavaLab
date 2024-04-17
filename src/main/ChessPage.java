package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChessPage {
    private JFrame frame;
    private JLabel turnLabel;
    private JLabel[] timerLabels;
    private Timer[] timers;
    private int[] playerTimeInSeconds;
    private int currentPlayerIndex;
    private Board3 board;

    InputAudio clickSound;

    public ChessPage() {
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(Color.BLACK);
        frame.setLayout(new GridBagLayout());
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setResizable(false);

        clickSound = new InputAudio("src/res/ButtonClick.wav");

        playerTimeInSeconds = new int[]{10, 10};
        currentPlayerIndex = 0;

        board = new Board3(this);
        frame.add(board);
        frame.setVisible(true);

        turnLabel = new JLabel("White's Turn");
        turnLabel.setFont(new Font("Arial", Font.BOLD, 20));
        turnLabel.setForeground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 1;
        gbc.gridy = 0;
        frame.add(turnLabel, gbc);

        timerLabels = new JLabel[2];
        timerLabels[0] = new JLabel("Player 1 Time: 10:00");
        timerLabels[1] = new JLabel("Player 2 Time: 10:00");
        timerLabels[0].setFont(new Font("Arial", Font.PLAIN, 16));
        timerLabels[1].setFont(new Font("Arial", Font.PLAIN, 16));
        timerLabels[0].setForeground(Color.WHITE);
        timerLabels[1].setForeground(Color.WHITE);
        gbc.gridy = 1;
        frame.add(timerLabels[0], gbc);
        gbc.gridy = 2;
        frame.add(timerLabels[1], gbc);

        timers = new Timer[2];
        for (int i = 0; i < 2; i++) {
            final int playerIndex = i;
            timers[i] = new Timer(1000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    playerTimeInSeconds[playerIndex]--;
                    updateTimerLabel(playerIndex);

                    if (playerTimeInSeconds[playerIndex] <= 0) {
                        timers[playerIndex].stop();
                        JOptionPane.showMessageDialog(frame, "Player " + (playerIndex + 1) + " ran out of time!");
                        frame.dispose();
                        new HomePage();
                    }
                }
            });
            timers[i].setInitialDelay(0);
        }

        startCurrentPlayerTimer();

        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setBackground(Color.BLACK);
        gbc.gridy = 0;

        JButton menuButton = new JButton("Menu");
        menuButton.setPreferredSize(new Dimension(200, 50));
        buttonPanel.add(menuButton, gbc);

        JButton exitButton = new JButton("Exit");
        exitButton.setPreferredSize(new Dimension(200, 50));
        gbc.gridy = 1;
        buttonPanel.add(exitButton, gbc);

        menuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clickSound.ButtonClickSound();
                frame.dispose();
                new HomePage();
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clickSound.ButtonClickSound();
                System.exit(0);
            }
        });

        frame.add(buttonPanel, gbc);
        frame.pack();
    }

    private void startCurrentPlayerTimer() {
        timers[currentPlayerIndex].start();
    }

    private void stopCurrentPlayerTimer() {
        timers[currentPlayerIndex].stop();
    }

    private void updateTimerLabel(int playerIndex) {
        int minutes = playerTimeInSeconds[playerIndex] / 60;
        int seconds = playerTimeInSeconds[playerIndex] % 60;
        timerLabels[playerIndex].setText(String.format("Player %d Time: %02d:%02d", playerIndex + 1, minutes, seconds));
    }

    public void switchTurn() {
        stopCurrentPlayerTimer();
        currentPlayerIndex = (currentPlayerIndex + 1) % 2;
        startCurrentPlayerTimer();
        updateTurnLabel(currentPlayerIndex == 0);
    }

    public void updateTurnLabel(boolean isWhiteTurn) {
        if (isWhiteTurn) {
            turnLabel.setText("White's Turn");
            turnLabel.setForeground(Color.WHITE);
        } else {
            turnLabel.setText("Black's Turn");
            turnLabel.setForeground(Color.WHITE);
        }
    }

    public void onPlayerMove() {
        switchTurn();
    }

    public void pauseTimer() {
        stopCurrentPlayerTimer();
    }

    public void resumeTimer() {
        startCurrentPlayerTimer();
    }
}