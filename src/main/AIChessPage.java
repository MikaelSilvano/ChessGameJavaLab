package main;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;
import javax.swing.*;

public class AIChessPage {
    private JFrame frame;
    static InputAudio clickSound;
    private AIBoard aiBoard;
    private JLabel[] timerLabels;
    private Timer[] timers;
    private int[] playerTimeInSeconds;
    private int currentPlayerIndex;
    private JLabel label;
    private JLabel turnLabel;
    ImageIcon menuButtonIcon;
    ImageIcon exitButtonIcon;

    public AIChessPage() {
        try {
            menuButtonIcon = new ImageIcon("src/res/menuButton.png");
            exitButtonIcon = new ImageIcon("src/res/exitButton.png");
        } catch (Exception e) {
            e.printStackTrace();
        }
        clickSound = new InputAudio("src/res/ButtonClick.wav");

        frame = new JFrame("Master Chess");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.setSize(new Dimension(1080, 720));
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        AIUserInterface ui = new AIUserInterface();
        aiBoard = new AIBoard();
        //ui.setPreferredSize(new Dimension(1920, 100));
        //aiBoard = new AIBoard(); //panggil board ai
        //JPanel chessBoardPanel = new JPanel(new BorderLayout());
        //chessBoardPanel.add(ui, BorderLayout.CENTER);
        //chessBoardPanel.setBackground(Color.BLACK);
        //frame.add(chessBoardPanel);
        frame.add(ui, BorderLayout.CENTER);
        ui.setOpaque(false);

        frame.getContentPane().setBackground(new Color(255, 134, 58));

        //button yang dikanan
        GridBagConstraints gbd = new GridBagConstraints();
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setOpaque(false);

        gbd.gridx = 0;
        gbd.gridy = 0;
        gbd.insets = new Insets(5, 5, 5, 5);

        JLabel menuButton = new JLabel(menuButtonIcon);
        menuButton.setPreferredSize(new Dimension(250, 100));
        JLabel exitButton = new JLabel(exitButtonIcon);
        exitButton.setPreferredSize(new Dimension(250, 100));

        buttonPanel.add(menuButton, gbd);
        gbd.gridy = 1;
        buttonPanel.add(exitButton, gbd);
        menuButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                clickSound.ButtonClickSound();
                showMenuConfirmationAI(frame);
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
                showExitConfirmationAI(frame);
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
        frame.add(buttonPanel, BorderLayout.EAST);
        frame.setVisible(true);
    }

    private void showMenuConfirmationAI(JFrame frame) {
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

    private void showExitConfirmationAI(JFrame frame) {
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
