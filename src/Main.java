import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class Main extends JFrame {
    JFrame frame = new JFrame("Master Chess");
    JLabel displayField;
    ImageIcon backgroundImage;
    ImageIcon displayOptions;
    ImageIcon displayDifficultyOptions;

    public Main() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(Color.BLACK);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setResizable(false);

        try {
            backgroundImage = new ImageIcon(Objects.requireNonNull(getClass().getResource("BackgroundMenu.png")));
            displayOptions = new ImageIcon(Objects.requireNonNull(getClass().getResource("singleplayer_Icon.png")));
            displayDifficultyOptions = new ImageIcon(Objects.requireNonNull(getClass().getResource("normal_Icon.png")));

            displayField = new JLabel(backgroundImage);
            frame.add(displayField, BorderLayout.CENTER);
        } catch (Exception e) {
            e.printStackTrace();
        }

        ImageIcon playButtonIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("PlayButton.png")));
        JButton playButton = new JButton(playButtonIcon);
        playButton.setSize(239, 70);
        playButton.setBorderPainted(false);
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                        if ("Nimbus".equals(info.getName())) {
                            UIManager.setLookAndFeel(info.getClassName());
                            break;
                        }
                    }
                } catch (Exception e1) {
                    // If Nimbus is not available, you can set the GUI to another look and feel.
                }
                displayOptions();
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(Color.BLACK);
        buttonPanel.add(playButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.setSize(1920, 1080);
        frame.setVisible(true);
    }

    private void displayOptions() {
        frame.getContentPane().removeAll();
        frame.repaint();

        JButton singlePlayerButton = new JButton("", displayOptions);
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

        JButton normalButton = new JButton("", displayDifficultyOptions);
        normalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame(true, "Normal");
            }
        });

        JButton hardButton = new JButton("Hard", displayDifficultyOptions);
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
        Board board = new Board();
        board.setDifficulty(difficulty);
        board.setSinglePlayer(isSinglePlayer);
        frame.dispose();
    }

    public static void main(String[] args) {
        new Main();
    }
}
