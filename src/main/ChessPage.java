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
    ImageIcon menuButtonIcon;
    ImageIcon exitButtonIcon;

    public ChessPage() {
        try {
            menuButtonIcon = new ImageIcon("src/res/menuButton.png");
            exitButtonIcon = new ImageIcon("src/res/exitButton.png");
        } catch (Exception e) {
            e.printStackTrace();
        }

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

        this.board = new Board(this);
        this.checkScanner = new CheckScanner(board);
        frame.add(board);
        frame.setVisible(true);

        timerLabels = new JLabel[2];
        timerLabels[0] = new JLabel("Player 1 Time: 10:00");
        timerLabels[1] = new JLabel("Player 2 Time: 10:00");
        timerLabels[0].setFont(new Font("Monospaced", Font.BOLD, 16));
        timerLabels[1].setFont(new Font("Monospaced", Font.BOLD, 16));
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
        turnLabel = new JLabel("Player 1 Turn");
        turnLabel.setFont(new Font("Monospaced", Font.BOLD, 20));
        turnLabel.setForeground(Color.WHITE);
        gbd.insets = new Insets(10, 10, 10, 10);
        gbd.gridx = 1;
        gbd.gridy = 0;
        frame.add(turnLabel, gbd);

        checkStatusLabel = new JLabel("TEST");
        checkStatusLabel.setFont(new Font("Monospaced", Font.BOLD, 20));
        checkStatusLabel.setForeground(Color.RED);

        GridBagConstraints gbcCheckStatusLabel = new GridBagConstraints();
        gbcCheckStatusLabel.anchor = GridBagConstraints.CENTER;
        gbcCheckStatusLabel.insets = new Insets(10, 10, 10, 10);
        gbcCheckStatusLabel.gridx = 4;
        gbcCheckStatusLabel.gridy = 1;


        frame.add(checkStatusLabel, gbcCheckStatusLabel);

        checkmateStatusLabel = new JLabel("TEST");
        checkmateStatusLabel.setFont(new Font("Monospaced", Font.BOLD, 20));
        checkmateStatusLabel.setForeground(Color.RED);
        GridBagConstraints gbcCheckmateStatusLabel = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
        gbcCheckmateStatusLabel.insets = new Insets(10, 10, 10, 10);
        gbcCheckmateStatusLabel.gridx = 4;
        gbcCheckmateStatusLabel.gridy = 0;
        frame.add(checkmateStatusLabel, gbcCheckmateStatusLabel);

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

            gbd.gridx = 1;
            gbd.gridy = 0;
            gbd.insets = new Insets(5, 5, 5, 5); //untuk merapihkan button yang disebelah kanan

            JLabel menuButton = new JLabel(menuButtonIcon);
            //menuButton.setPreferredSize(new Dimension(200, 100));
            clickSound = new InputAudio("ButtonClick.wav");

            JLabel exitButton = new JLabel(exitButtonIcon);
            //exitButton.setPreferredSize(new Dimension(200, 100));
            buttonPanel.add(menuButton, gbd);

            gbd.gridx = 1;
            gbd.gridy = 1;
            buttonPanel.add(exitButton, gbd);
            menuButton.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    clickSound.ButtonClickSound();
                    frame.dispose();
                    new HomePage();
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
                    //System.exit(0); //exit button
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
    public void updateCheckStatusLabel(int playerNumber) {
        if (playerNumber == 1) {
            checkStatusLabel.setText("Player 1 is in check!");
        } else if (playerNumber == 2) {
            checkStatusLabel.setText("Player 2 is in check!");
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