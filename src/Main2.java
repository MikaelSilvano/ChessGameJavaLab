import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class Main2 extends JFrame {
    JFrame frame = new JFrame("Master Chess");
    JLabel displayField;
    ImageIcon backgroundImage;
    ImageIcon displayOptions;
    ImageIcon displayDifficultyOptions;

    public Main2() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(Color.BLACK);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setResizable(false);

        try {
            backgroundImage = new ImageIcon(Objects.requireNonNull(getClass().getResource("BackgroundMenu.png")));
            displayOptions = new ImageIcon(Objects.requireNonNull(getClass().getResource("singleplayer_icon.png")));
            displayDifficultyOptions = new ImageIcon(Objects.requireNonNull(getClass().getResource("normal_icon.png")));

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
                new Board();
                frame.dispose();
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(Color.BLACK);
        buttonPanel.add(playButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.setSize(1920, 1080);
        frame.setVisible(true);
    }
    public static void main(String[] args) {
        new Main2();
    }
}
