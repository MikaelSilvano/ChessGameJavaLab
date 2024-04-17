//ChessPage
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
        frame.getContentPane().setBackground(Color.black);
        frame.setLayout(new GridBagLayout());
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        clickSound = new InputAudio("src/res/ButtonClick.wav");
        playerTimeInSeconds = new int[]{600, 600};
        currentPlayerIndex = 0;

        Board3 board = new Board3(this);
        //board.setSize(new Dimension(500, 500));
        frame.add(board);
        frame.setVisible(true);

        timerLabels = new JLabel[2];
        timerLabels[0] = new JLabel("Player 1 Time: 10:00");
        timerLabels[1] = new JLabel("Player 2 Time: 10:00");
        timerLabels[0].setFont(new Font("Arial", Font.PLAIN, 16));
        timerLabels[1].setFont(new Font("Arial", Font.PLAIN, 16));
        timerLabels[0].setForeground(Color.WHITE);
        timerLabels[1].setForeground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 1;
        gbc.gridy = 0;
        frame.add(timerLabels[0], gbc);
        gbc.gridy = 1;
        frame.add(timerLabels[1], gbc);

        GridBagConstraints gbd = new GridBagConstraints();
        turnLabel = new JLabel("White's Turn");
        turnLabel.setFont(new Font("Arial", Font.BOLD, 20));
        turnLabel.setForeground(Color.WHITE);
        gbd.insets = new Insets(10, 10, 10, 10);
        gbd.gridx = 1;
        gbd.gridy = 0;
        frame.add(turnLabel, gbd);

        timers = new Timer[2];
        {
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
                        }
                    }
                });
                timers[i].setInitialDelay(0);
            }
            startCurrentPlayerTimer();
            frame.setVisible(true);

            //button yang dikanan
            JPanel buttonPanel = new JPanel(new GridBagLayout());
            buttonPanel.setBackground(Color.BLACK);

            gbd.gridx = 2;
            gbd.gridy = 0;
            gbd.insets = new Insets(5, 5, 5, 5); //untuk merapihkan button yang disebelah kanan

            JButton menuButton = new JButton("Menu");
            menuButton.setPreferredSize(new Dimension(200, 100));
            JButton exitButton = new JButton("Exit");
            exitButton.setPreferredSize(new Dimension(200, 100));
            buttonPanel.add(menuButton, gbd);

            gbd.gridy = 1;
            buttonPanel.add(exitButton, gbd);

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

            frame.add(buttonPanel);
        }
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
        // Called when a player makes a move on the board
        switchTurn(); // Switch turn after a valid move
    }

    public void pauseTimer() {
            stopCurrentPlayerTimer();
    }

    public void resumeTimer() {
            startCurrentPlayerTimer();
    }
}