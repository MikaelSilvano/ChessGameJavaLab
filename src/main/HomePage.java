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
            singlePlayerIcon = new ImageIcon("src/res/singleplayer-Recovered.png");
            multiPlayerIcon = new ImageIcon("src/res/multiplayerplayer-Recovered.png");
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

        frame.getContentPane().removeAll();
        frame.repaint();
        frame.add(new JLabel(new ImageIcon(String.valueOf(backgroundImage))));


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
       /* multiplayerOfflineButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clickSound.ButtonClickSound();
                startGameMultiplayerOffline();
            }
        });*/

        /*JButton singlePlayerButton = new JButton(singlePlayerIcon);
        singlePlayerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clickSound.ButtonClickSound();
                startGameSingleplayerEasy(); // Memulai permainan single player dengan AIChessPageEasy
            }
        }); */
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

        //JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
       /* buttonPanel.setBackground(Color.BLACK);
        buttonPanel.add(multiplayerOfflineButton);
        buttonPanel.add(singlePlayerButton);*/

        frame.add(multiplayerOfflineButton, BorderLayout.CENTER);
        frame.add(singlePlayerButton, BorderLayout.CENTER);

        frame.revalidate();
    }

   /* private void startGameMultiplayerOffline() {
        new ChessPage();
        frame.dispose();
    } */

    /*private void startGameSingleplayerEasy() {
        new AIChessPage();
        frame.dispose();
    } */

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