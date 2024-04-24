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

        setTaskbarIcon();
        clickSound = new InputAudio("ButtonClick.wav");

        frame.setVisible(true);

    }

    private void displayOptions() {

        JPanel backgroundPanel = new JPanel() {
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
        frame.repaint();


        JLabel multiplayerOfflineButton = new JLabel(multiPlayerIcon);
        multiplayerOfflineButton.setBounds (900, 730, 1080, 500);
        clickSound = new InputAudio("ButtonClick.wav");

        multiplayerOfflineButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                clickSound.ButtonClickSound();
                new ChessPage();
                frame.dispose();
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

        JLabel singlePlayerButton = new JLabel(singlePlayerIcon);
        singlePlayerButton.setBounds (910, 730, 200, 75);
        clickSound = new InputAudio("ButtonClick.wav");

        singlePlayerButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                clickSound.ButtonClickSound();
                new AIChessPage();
                frame.dispose();
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


        frame.add(multiplayerOfflineButton);
        frame.add(singlePlayerButton);

        frame.revalidate();
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