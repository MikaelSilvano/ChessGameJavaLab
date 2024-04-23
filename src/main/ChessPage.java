//ChessPage
package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

public class ChessPage {
    private CheckScanner checkScanner;
    private JFrame frame;
    private JLabel turnLabel;
    private JLabel[] timerLabels;
    private JLabel checkStatusLabel;
    private JPanel backgroundPanel;
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
        backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Draw the background image
                ImageIcon backgroundImage = new ImageIcon("src/res/Background.png");
                ImageIcon pumpkinImage = new ImageIcon("src/res/pump.png");
                Image image = backgroundImage.getImage();
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
        };

        backgroundPanel.setLayout(new GridBagLayout());
        frame.setContentPane(backgroundPanel);

        this.board = new Board3(this);
        this.checkScanner = new CheckScanner(board);
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
        frame.add(timerLabels[1], gbc);
        gbc.gridy = 1;
        frame.add(timerLabels[0], gbc);

        GridBagConstraints gbd = new GridBagConstraints();
        turnLabel = new JLabel("White's Turn");
        turnLabel.setFont(new Font("Arial", Font.BOLD, 20));
        turnLabel.setForeground(Color.WHITE);
        gbd.insets = new Insets(10, 10, 10, 10);
        gbd.gridx = 1;
        gbd.gridy = 0;
        frame.add(turnLabel, gbd);

        checkStatusLabel = new JLabel("");
        checkStatusLabel.setFont(new Font("Arial", Font.BOLD, 20));
        checkStatusLabel.setForeground(Color.RED);
        GridBagConstraints gbcCheckStatusLabel = new GridBagConstraints();
        gbcCheckStatusLabel.insets = new Insets(10, 10, 10, 10);
        gbcCheckStatusLabel.gridx = 1;
        gbcCheckStatusLabel.gridy = 1;
        frame.add(checkStatusLabel, gbcCheckStatusLabel);

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
            buttonPanel.setOpaque(false);

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
                    showMenuConfirmation();
                }
            });

            exitButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    clickSound.ButtonClickSound();
                    showExitConfirmation();
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
        } else {
            turnLabel.setText("Black's Turn");
        }
    }
    public void updateStatusLabel(boolean isWhiteChecked, boolean isCheckmate) {
        if (isCheckmate) {
            checkStatusLabel.setText(isWhiteChecked ? "White is in Checkmate!" : "Black is in Checkmate!");
        } else if (isWhiteChecked) {
            checkStatusLabel.setText("White is in Check!");
        } else {
            checkStatusLabel.setText("Black is in Check!");
        }
    }

    public void onPlayerMove() {
        // Called when a player makes a move on the board
        switchTurn(); // Switch turn after a valid move
    }

    private void showMenuConfirmation() {
        int confirmed = JOptionPane.showConfirmDialog(frame,
                "Are you sure you want to go back to menu?",
                "Menu Confirmation",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                createImageIcon("/res/pump.png"));
        clickSound.ButtonClickSound();

        if (confirmed == JOptionPane.YES_OPTION) {
            clickSound.ButtonClickSound();
            frame.dispose(); // Close the main frame
            new HomePage();
        }
    }

    private void showExitConfirmation() {
        int confirmed = JOptionPane.showConfirmDialog(frame,
                "Are you sure you want to exit the game?",
                "Exit Confirmation",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                createImageIcon("/res/pump.png"));
        clickSound.ButtonClickSound();

        if (confirmed == JOptionPane.YES_OPTION) {
            clickSound.ButtonClickSound();
            frame.dispose(); // Close the main frame
            System.exit(0);
        }
    }

    public ImageIcon createImageIcon(String s) {
        URL imgUrl = getClass().getResource("/res/pump.png");
        if (imgUrl != null) {
            return new ImageIcon(imgUrl);
        } else {
            System.err.println("Couldn't find file: " + "/res/pump.png");
            return null;
        }
    }
}