import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ImageAdder extends JFrame {

    JFrame frame;
    JLabel displayField;
    ImageIcon image;

    public ImageAdder() {

        frame = new JFrame("Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Menggunakan ImageIcon untuk menampilkan gambar
        try {
            image = new ImageIcon(getClass().getResource("p1.PNG"));
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
