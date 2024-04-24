//ChessPage
package main;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;

public class ChessPage {
    private CheckScanner checkScanner;
    private JFrame frame;
    private JPanel  displayPanel;
    private JLabel turnLabel;
    private JLabel[] timerLabels;
    private static JLabel checkStatusLabel;
    private JLabel checkmateStatusLabel;
    private JPanel backgroundPanel;
    private Timer[] timers;
    private int[] playerTimeInSeconds;
    private int currentPlayerIndex;
    private Board board;
    InputAudio clickSound;
    InputAudio winChessSound;
    ImageIcon menuButtonIcon;
    ImageIcon exitButtonIcon;

    public ChessPage() {
        try {
            menuButtonIcon = new ImageIcon("src/res/menuButton.png");
            exitButtonIcon = new ImageIcon("src/res/exitButton.png");
        } catch (Exception e) {
            e.printStackTrace();
        }
        clickSound = new InputAudio("src/res/ButtonClick.wav");
        winChessSound = new InputAudio("src/res/WinChess.wav");

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
                ImageIcon backgroundImage = new ImageIcon("src/res/BackgroundMultiplayerOffline.png");
                ImageIcon pumpkinImage = new ImageIcon("src/res/pump.png");
                Image image = backgroundImage.getImage();
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
        };
        backgroundPanel.setLayout(new GridBagLayout());
        frame.setContentPane(backgroundPanel);


        GridBagConstraints gbcFrame = new GridBagConstraints();
        gbcFrame.anchor = GridBagConstraints.WEST;
        gbcFrame.insets = new Insets(10, 10, 10, 10);
        gbcFrame.gridx = 0;
        gbcFrame.gridy = 0;

        //this.board = new Board(this);
        this.checkScanner = new CheckScanner(board);
        //frame.add(board, gbcFrame);
        frame.setVisible(true);

        timerLabels = new JLabel[2];
        timerLabels[0] = new JLabel("Player 1 Time: 10:00");
        timerLabels[1] = new JLabel("Player 2 Time: 10:00");
        timerLabels[0].setFont(new Font("Monospaced", Font.BOLD, 16));
        timerLabels[1].setFont(new Font("Monospaced", Font.BOLD, 16));
        timerLabels[0].setForeground(Color.WHITE);
        timerLabels[1].setForeground(Color.WHITE);

        gbcFrame.gridx = 1;
        gbcFrame.gridy = 0;
        gbcFrame.anchor = GridBagConstraints.NORTH;
        frame.add(timerLabels[1], gbcFrame);
        gbcFrame.gridy = 1;
        frame.add(timerLabels[0], gbcFrame);

        turnLabel = new JLabel("Player 1 Turn"); //untuk turn label
        turnLabel.setFont(new Font("Monospaced", Font.BOLD, 20));
        turnLabel.setForeground(Color.WHITE);

        gbcFrame.gridx = 1;
        gbcFrame.gridy = 0;
        gbcFrame.anchor = GridBagConstraints.CENTER;
        frame.add(turnLabel, gbcFrame);

        checkStatusLabel = new JLabel("TEST");
        checkStatusLabel.setFont(new Font("Monospaced", Font.BOLD, 20));
        checkStatusLabel.setForeground(Color.BLUE);

        gbcFrame.gridx = 3;
        gbcFrame.gridy = 0;
        frame.add(checkStatusLabel, gbcFrame);

        checkmateStatusLabel = new JLabel("TEST");
        checkmateStatusLabel.setFont(new Font("Monospaced", Font.BOLD, 20));
        checkmateStatusLabel.setForeground(Color.RED);

        gbcFrame.gridx = 4;
        gbcFrame.gridy = 0;
        frame.add(checkmateStatusLabel, gbcFrame);

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
                            if(playerIndex == 0) {
                                player2Wins();
                            } else if(playerIndex == 1) {
                                player1Wins();
                            }

                        }
                    }
                });
                timers[i].setInitialDelay(0);
            }
            startCurrentPlayerTimer();
            frame.setVisible(true);

            //button yang dikanan
            JPanel buttonPanel = new JPanel(new BorderLayout());
            buttonPanel.setPreferredSize(new Dimension(250,  200));
            buttonPanel.setOpaque(false);

            JLabel menuButton = new JLabel(menuButtonIcon);
            buttonPanel.add(menuButton, BorderLayout.NORTH);

            JLabel exitButton = new JLabel(exitButtonIcon);
            buttonPanel.add(exitButton, BorderLayout.SOUTH);
            menuButton.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    clickSound.ButtonClickSound();
                    showMenuConfirmation();
                }

                @Override
                public void mousePressed(MouseEvent e) {

                }

                @Override
                public void mouseReleased(MouseEvent e) {

                }

                @Override
                public void mouseEntered(MouseEvent e) {

                }

                @Override
                public void mouseExited(MouseEvent e) {

                }
            });

            exitButton.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    clickSound.ButtonClickSound();
                    showExitConfirmation();
                }

                @Override
                public void mousePressed(MouseEvent e) {

                }

                @Override
                public void mouseReleased(MouseEvent e) {

                }

                @Override
                public void mouseEntered(MouseEvent e) {

                }

                @Override
                public void mouseExited(MouseEvent e) {

                }
            });
            gbcFrame.gridx = 2;
            gbcFrame.gridy = 0;
            frame.add(buttonPanel, gbcFrame);
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
            turnLabel.setText("Player 1 Turn");
        } else {
            turnLabel.setText("Player 2 Turn");
        }
    }
    public void clearCheckStatusLabel(int playerNumber) {
        if (playerNumber == 1) {
            checkStatusLabel.setText("TEST");
        } else if (playerNumber == 2) {
            checkStatusLabel.setText("TEST");
        }
    }
    public void clearCheckmateStatusLabel(int playerNumber) {
        if (playerNumber == 1) {
            checkmateStatusLabel.setText("TEST");
        } else if (playerNumber == 2) {
            checkmateStatusLabel.setText("TEST");
        }
    }
    public void updateCheckmateStatusLabel(int playerNumber) {
        if (playerNumber == 1) {
            checkmateStatusLabel.setText("Player 1 is in checkmate!");
        } else if (playerNumber == 2) {
            checkmateStatusLabel.setText("Player 2 is in checkmate!");
        }
    }
    /////////
    public void clearCheckStatus() {
        checkStatusLabel.setText("TEST");
    }
    public void updateCheckStatus() {
        checkStatusLabel.setText("Check!");
    }
    public void clearCheckmateStatus() {
        checkStatusLabel.setText("TEST");
    }
    public void updateCheckmateStatus() {
        checkStatusLabel.setText("Check!");
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
            frame.dispose();
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
            frame.dispose();
            System.exit(0);
        }
    }
    public void player1Wins() {
        JDialog dialog = new JDialog(frame, "Player 1 Wins", true);
        dialog.getContentPane().setBackground(Color.WHITE);
        dialog.setLayout(new BorderLayout());
        dialog.setResizable(false);
        winChessSound.WinChessSound();

        ImageIcon icon = createImageIcon("/res/pump.png");

        JLabel messageLabel = new JLabel("<html>Player 2 ran out of time!<br/>Player 1 wins!</html>", icon, JLabel.CENTER);
        messageLabel.setFont(new Font("Monospaced", Font.BOLD, 20));
        messageLabel.setBackground(new Color(0,0,0));
        messageLabel.setForeground(new Color(255, 134, 58));
        messageLabel.setHorizontalAlignment(JLabel.CENTER);
        messageLabel.setVerticalTextPosition(JLabel.BOTTOM);
        messageLabel.setHorizontalTextPosition(JLabel.CENTER);

        dialog.add(messageLabel, BorderLayout.CENTER);

        JButton retryButton = new JButton("Retry");
        retryButton.setForeground(new Color(57, 47, 79));
        retryButton.addActionListener(e -> {
            clickSound.ButtonClickSound();
            dialog.dispose();
            frame.dispose();
            new HomePage();
        });
        retryButton.setPreferredSize(new Dimension(100, 40));
        retryButton.setFont(new Font("Monospaced", Font.PLAIN, 16));

        JButton exitButton = new JButton("Exit");
        exitButton.setForeground(new Color(57, 47, 79));
        exitButton.addActionListener(e -> {
            clickSound.ButtonClickSound();
            dialog.dispose();
            frame.dispose();
            System.exit(0);
        });
        exitButton.setPreferredSize(new Dimension(100, 40));
        exitButton.setFont(new Font("Monospaced", Font.PLAIN, 16));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(retryButton);
        buttonPanel.add(exitButton);

        dialog.add(buttonPanel, BorderLayout.SOUTH);

        dialog.setSize(500, 250);
        dialog.setLocationRelativeTo(frame);

        dialog.setVisible(true);
    }

    public void player2Wins() {
        JDialog dialog = new JDialog(frame, "Player 2 Wins", true);
        dialog.getContentPane().setBackground(Color.WHITE);
        dialog.setLayout(new BorderLayout());
        dialog.setResizable(false);
        winChessSound.WinChessSound();

        ImageIcon icon = createImageIcon("/res/pump.png");

        JLabel messageLabel = new JLabel("<html>Player 1 ran out of time!<br/>Player 2 wins!</html>", icon, JLabel.CENTER);
        messageLabel.setFont(new Font("Monospaced", Font.PLAIN, 20));
        messageLabel.setForeground(new Color(255, 134, 58));
        messageLabel.setHorizontalAlignment(JLabel.CENTER);
        messageLabel.setVerticalTextPosition(JLabel.BOTTOM);
        messageLabel.setHorizontalTextPosition(JLabel.CENTER);

        dialog.add(messageLabel, BorderLayout.CENTER);

        JButton retryButton = new JButton("Retry");
        retryButton.setForeground(new Color(57, 47, 79));
        retryButton.addActionListener(e -> {
            clickSound.ButtonClickSound();
            dialog.dispose();
            frame.dispose();
            new HomePage();
        });
        retryButton.setPreferredSize(new Dimension(100, 40));
        retryButton.setFont(new Font("Monospaced", Font.PLAIN, 16));

        JButton exitButton = new JButton("Exit");
        exitButton.setForeground(new Color(57, 47, 79));
        exitButton.addActionListener(e -> {
            clickSound.ButtonClickSound();
            dialog.dispose();
            frame.dispose();
            System.exit(0);
        });
        exitButton.setPreferredSize(new Dimension(100, 40));
        exitButton.setFont(new Font("Monospaced", Font.PLAIN, 16));


        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(retryButton);
        buttonPanel.add(exitButton);

        dialog.add(buttonPanel, BorderLayout.SOUTH);

        dialog.setSize(500, 250);
        dialog.setLocationRelativeTo(frame);

        dialog.setVisible(true);
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