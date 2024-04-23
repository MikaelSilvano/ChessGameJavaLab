package main;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import javax.swing.*;

public class AIChessPage {
	static InputAudio clickSound;
	private AIBoard aiBoard;

	public AIChessPage() {
		clickSound = new InputAudio("src/res/ButtonClick.wav");

		JFrame frame = new JFrame("Master Chess");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);

		AIUserInterface ui = new AIUserInterface();
		ui.setPreferredSize(new Dimension(1920, 100));
		aiBoard = new AIBoard(); //panggil board ai
		JPanel chessBoardPanel = new JPanel(new BorderLayout());
		chessBoardPanel.add(ui, BorderLayout.CENTER);
		chessBoardPanel.setBackground(Color.BLACK);
		frame.add(chessBoardPanel);
		frame.getContentPane().setBackground(Color.black);

		//button yang dikanan
		GridBagConstraints gbd = new GridBagConstraints();
		JPanel buttonPanel = new JPanel(new GridBagLayout());
		buttonPanel.setOpaque(false);

		gbd.gridx = 2;
		gbd.gridy = 0;
		gbd.insets = new Insets(5, 5, 5, 5); //untuk merapihkan button yang disebelah kanan

		JButton menuButton = new JButton("Menu");
		menuButton.setPreferredSize(new Dimension(200, 100));
		JButton exitButton = new JButton("Exit");
		exitButton.setPreferredSize(new Dimension(200, 100));

		buttonPanel.add(menuButton, gbd);
		gbd.gridy = 1;
		buttonPanel.add(exitButton, gbd);


		menuButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				clickSound.ButtonClickSound();
				showMenuConfirmationAI(frame);
			}
		});

		exitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				clickSound.ButtonClickSound();
				showExitConfirmationAI(frame);
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
