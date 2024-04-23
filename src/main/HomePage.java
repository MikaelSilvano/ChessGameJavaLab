package main;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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

        JLabel playButton = new JLabel(playButtonIcon);
        playButton.setBounds (945, 730, 200, 75);
        clickSound = new InputAudio("ButtonClick.wav");

        frame = new JFrame("Catur Mas Putra");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(Color.BLACK);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setResizable(false);
        frame.add(playButton);

        frame.add(new JLabel(new ImageIcon(String.valueOf(backgroundImage))));

        playButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                clickSound.ButtonClickSound();
                displayOptions();
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
        frame.setVisible(true);

        setTaskbarIcon();
        clickSound = new InputAudio("ButtonClick.wav");

        frame.setVisible(true);
    }

    private void displayOptions() {
        frame.getContentPane().removeAll();
        frame.repaint();

        JButton multiplayerOfflineButton = new JButton(multiPlayerIcon);
        multiplayerOfflineButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clickSound.ButtonClickSound();
                startGameMultiplayerOffline();
            }
        });

        JButton singlePlayerButton = new JButton(singlePlayerIcon);
        singlePlayerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clickSound.ButtonClickSound();
                startGameSingleplayerEasy(); // Memulai permainan single player dengan AIChessPageEasy
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(Color.BLACK);
        buttonPanel.add(multiplayerOfflineButton);
        buttonPanel.add(singlePlayerButton);

        frame.add(buttonPanel, BorderLayout.CENTER);
        frame.revalidate();
    }

    private void startGameMultiplayerOffline() {
        new ChessPage();
        frame.dispose();
    }

    private void startGameSingleplayerEasy() {
        new AIChessPage();
        frame.dispose();
    }

    public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
    }


    private void setTaskbarIcon() {
        ImageIcon icon = new ImageIcon("src/res/pumpkin.png");

        setIconImage(icon.getImage());

        Frame[] frames = Frame.getFrames();
        for (Frame frame : frames) {
            frame.setIconImage(icon.getImage());
        }
    }
}