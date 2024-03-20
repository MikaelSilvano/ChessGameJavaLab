import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

//set color sekitar jadi black, continue ke next frame, ganti judul class, ganti tombol play, connect sama board lama

public class ImageAdder extends JFrame {

    JFrame frame;
    JLabel displayField;
    ImageIcon image;

    public ImageAdder() {

        frame = new JFrame("Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Menggunakan ImageIcon untuk menampilkan gambar
        try {
            image = new ImageIcon(Objects.requireNonNull(getClass().getResource("Background_Menu.png")));
            displayField = new JLabel(image);
            frame.add(displayField, BorderLayout.CENTER);
        } catch (Exception e) {
            System.out.println("Image cannot be found!");
        }

        // Tombol "Play" menggunakan JButton
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
        buttonPanel.add(playButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.setSize(620, 620);
        frame.setVisible(true);

    }

    public static void main(String[] args) {
        // Membuat objek ImageAdder saat program dimulai
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ImageAdder();
            }
        });
    }
}
