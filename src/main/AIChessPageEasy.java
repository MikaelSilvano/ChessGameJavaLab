package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class AIChessPageEasy {
	private static JButton menuButton;
	private static JButton exitButton;
	private static InputAudio clickSound;

	public AIChessPageEasy() {
		clickSound = new InputAudio("src/res/ButtonClick.wav");

		JFrame frame = new JFrame("Chess");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setBackground(Color.black);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setResizable(false);

		AIBoard aiBoard = new AIBoard();
		ChessboardPanel chessboardPanel = new ChessboardPanel(aiBoard);

		JPanel buttonPanel = new JPanel();
		menuButton = new JButton("Menu");
		exitButton = new JButton("Exit");

		menuButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				clickSound.ButtonClickSound();
				frame.dispose();
				new HomePage();
			}
		});

		exitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				clickSound.ButtonClickSound();
				showExitConfirmationAI();
			}
		});

		buttonPanel.add(menuButton);
		buttonPanel.add(exitButton);

		frame.setLayout(new BorderLayout());

		frame.add(chessboardPanel, BorderLayout.WEST);

		frame.add(buttonPanel, BorderLayout.EAST);

		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

		System.out.println(aiBoard.possibleMoves());

		for (int i = 0; i < 8; i++)
			System.out.println(Arrays.toString(aiBoard.chessBoard[i]));

		frame.repaint();
	}

	private static void showExitConfirmationAI() {
		JFrame confirmFrame = new JFrame();
		int confirmed = JOptionPane.showConfirmDialog(confirmFrame, "Are you sure you want to exit the game?",
				"Confirmation", JOptionPane.YES_NO_OPTION);
		clickSound.ButtonClickSound();

		if (confirmed == JOptionPane.YES_OPTION) {
			System.exit(0);
		}
	}
}
