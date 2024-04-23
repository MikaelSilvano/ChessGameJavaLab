package main;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
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
        JButton button = new JButton();
        button.setBounds(945, 730, 200, 75);

        frame = new JFrame("Catur Mas Putra");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(Color.BLACK);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setResizable(false);
        frame.add(button);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clickSound.ButtonClickSound();
                displayOptions();
            }
        });

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

        setTaskbarIcon();

        frame.add(new JLabel(new ImageIcon(String.valueOf(backgroundImage))));
        clickSound = new InputAudio("ButtonClick.wav");


        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(Color.BLACK);

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
                startGameSingleplayerEasy(); // Memulai permainan single player dengan AIChessPageEasy
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

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(Color.BLACK);
        buttonPanel.add(singlePlayerButton);
        buttonPanel.add(multiplayerOfflineButton);

        frame.add(buttonPanel, BorderLayout.CENTER);
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

    public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
    }


    private void setTaskbarIcon() {
        // Load icon image
        ImageIcon icon = new ImageIcon("src/res/pumpkin.png"); // Adjust path as needed

        // Set icon for JFrame
        setIconImage(icon.getImage());

        // Set icon for top-level window (taskbar icon)
        Frame[] frames = Frame.getFrames();
        for (Frame frame : frames) {
            frame.setIconImage(icon.getImage());
        }
    }
}