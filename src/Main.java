import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

//set color sekitar jadi black, continue ke next frame, ganti judul class, ganti tombol play, connect sama board lama

public class Main extends JFrame {

    public Main() {
        JFrame frame = new JFrame("Master Chess");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(Color.BLACK);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setResizable(false); // Disable resizing

        // Menggunakan ImageIcon untuk menampilkan gambar
        try {
            ImageIcon image = new ImageIcon(Objects.requireNonNull(getClass().getResource("BackgroundMenu.png")));
            JLabel displayField = new JLabel(image);
            frame.add(displayField, BorderLayout.CENTER);
        } catch (Exception e) {
            System.out.println("Image cannot be found!");
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
                new Board();
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

    public static void main(String[] args) {
        new Main();
    }
}
