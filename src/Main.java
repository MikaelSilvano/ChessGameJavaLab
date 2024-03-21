import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

//set color sekitar jadi black, continue ke next frame, ganti judul class, ganti tombol play, connect sama board lama

public class Main extends JFrame {
    JFrame frame = new JFrame("Master Chess");
    JLabel displayField;
    ImageIcon backgroundImage;
    ImageIcon displayOptions;
    ImageIcon displayDifficultyOptions;

    public Main() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(Color.BLACK);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); //agar tidak bablas sizenya
        //frame.setUndecorated(true); //agar tidak ada windows di atas
        frame.setResizable(false);

        // Menggunakan ImageIcon untuk menampilkan gambar
        try {
            backgroundImage = new ImageIcon(Objects.requireNonNull(getClass().getResource("BackgroundMenu.png")));
            displayOptions = new ImageIcon(Objects.requireNonNull(getClass().getResource("singleplayer_icon.png")));
            displayDifficultyOptions = new ImageIcon(Objects.requireNonNull(getClass().getResource("normal_icon.png")));

            displayField = new JLabel(backgroundImage);
            frame.add(displayField, BorderLayout.CENTER);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Tombol "Play" menggunakan JButton
        ImageIcon playButtonIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("PlayButton.png")));
        JButton playButton = new JButton(playButtonIcon);
        playButton.setSize(239, 70);
        playButton.setBorderPainted(false); //agar tidak ada border image
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
                // Membuat objek Board saat tombol "Play" ditekan
                displayOptions();
                // Menghilangkan frame ImageAdder setelah tombol "Play" ditekan
                frame.dispose();
            }
        });

        // Menambahkan tombol "Play" di bagian tengah frame menggunakan FlowLayout
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

        JButton singlePlayerButton = new JButton("Single Player", displayOptions);
        singlePlayerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayDifficultyOptions(true);
            }
        }
    }

        private void displayDifficultyOptions ( boolean isSinglePlayer){
            frame.getContentPane().removeAll();
            frame.repaint();

            JButton normalButton = new JButton("Normal", displayDifficultyOptions);
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

        private void startGame ( boolean isSinglePlayer, String difficulty){
            Board board = new Board();
            board.setDifficulty(difficulty);
            board.setSinglePlayer(isSinglePlayer);
            frame.dispose();
        }
    }
    }
        public static void main(String[] args) {
            new Main();
        }
    }

        /*
    import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

    public class ImageAdder extends JFrame {

        JFrame frame;
        JLabel displayField;
        ImageIcon backgroundImage;
        ImageIcon  displayOptions;
        ImageIcon displayDifficultyOptions;

        public ImageAdder() {
            frame = new JFrame("Game");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            try {
                backgroundImage = new ImageIcon(Objects.requireNonNull(getClass().getResource("Background_Menu.png")));
                displayOptions = new ImageIcon(Objects.requireNonNull(getClass().getResource("singleplayer_icon.png")));
                displayDifficultyOptions = new ImageIcon(Objects.requireNonNull(getClass().getResource("normal_icon.png")));

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
            frame.setSize(620, 620);
            frame.setVisible(true);
        }

        private void displayOptions() {
            frame.getContentPane().removeAll();
            frame.repaint();

            JButton singlePlayerButton = new JButton("Single Player", displayOptions);
            singlePlayerButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    displayDifficultyOptions(true);
                }
            });

            JButton multiplayerButton = new JButton("Multiplayer", displayOptions);
            multiplayerButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    startGame(false, "Normal");
                }
            });

            JPanel optionsPanel = new JPanel(new GridLayout(2, 1, 10, 10));
            optionsPanel.add(singlePlayerButton);
            optionsPanel.add(multiplayerButton);
            frame.add(optionsPanel, BorderLayout.CENTER);

            frame.revalidate();
        }

        private void displayDifficultyOptions(boolean isSinglePlayer) {
            frame.getContentPane().removeAll();
            frame.repaint();

            JButton normalButton = new JButton("Normal", displayDifficultyOptions);
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
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    new ImageAdder();
                }
            });
        }
    }

