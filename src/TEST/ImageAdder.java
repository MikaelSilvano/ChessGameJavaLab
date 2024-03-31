import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class ImageAdder extends JFrame {

    JFrame frame;
    JLabel displayField;
    ImageIcon backgroundImage;
    ImageIcon singleplayerIcon;
    ImageIcon multiplayerIcon;
    ImageIcon normalIcon;
    ImageIcon HardIcon;

    public ImageAdder() {
        frame = new JFrame("Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        try {
            backgroundImage = new ImageIcon(Objects.requireNonNull(getClass().getResource("MenuBackground.png")));
            singleplayerIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("Singleplayer_Icon.png")));
            multiplayerIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("Multiplayer_Icon.png")));
            normalIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("Normal_Icon.png")));
            HardIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("Hard_Icon.png")));

            displayField = new JLabel(backgroundImage);
            frame.add(displayField, BorderLayout.CENTER);
        } catch (Exception e) {
            e.printStackTrace();
        }

        JButton playButton = new JButton("PLAY");
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
                    e1.printStackTrace();
                }
                displayOptions();
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(playButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.setSize(650, 650);
        frame.setVisible(true);
    }

    private void displayOptions() {
        frame.getContentPane().removeAll();
        frame.repaint();

        JButton singlePlayerButton = new JButton("",singleplayerIcon);
        singlePlayerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayDifficultyOptions(true);
            }
        });

        JButton multiplayerButton = new JButton("", multiplayerIcon);
        multiplayerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame(false, "");
            }
        });

        JPanel optionsPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        optionsPanel.add(singlePlayerButton);
        optionsPanel.add(multiplayerButton);
        frame.add(optionsPanel, BorderLayout.CENTER);

        frame.revalidate();
    }

    private void displayDifficultyOptions(boolean isSinglePlayer) {
        frame.getContentPane().removeAll();
        frame.repaint();

        JButton normalButton = new JButton("", normalIcon );
        frame.setVisible(false);
        normalButton.setSize(100, 100);
        normalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame(true, "Normal");
            }
        });

        JButton hardButton = new JButton("", HardIcon );
        frame.setVisible(false);
        hardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame(true, "Hard");
            }
        });

        JPanel difficultyPanel = new JPanel(new GridLayout(2, 2, 10, 10));
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
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ImageAdder();
            }
        });
    }
}