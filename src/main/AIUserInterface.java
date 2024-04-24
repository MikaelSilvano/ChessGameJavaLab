package main;

import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import javax.swing.*;

import static main.AIBoard.*;

public class AIUserInterface extends JPanel implements MouseListener, MouseMotionListener{
	InputAudio promotionSound;
	InputAudio eatSound;
	InputAudio putSound;
	InputAudio pickSound;
	InputAudio clickSound;
	InputAudio winChessSound;
	private Timer[] timers;
	private int[] playerTimeInSeconds;
	private int currentPlayerIndex;
	private JLabel[] timerLabels;
	private static String userPossibleMoves;
	public AIUserInterface() {
		this.setPreferredSize(new Dimension(500, 500));
		promotionSound = new InputAudio("src/res/PawnPromotion.wav");
		eatSound = new InputAudio("src/res/EatPieces.wav");
		pickSound = new InputAudio("src/res/PickUpPieces.wav");
		putSound = new InputAudio("src/res/PutPieces.wav");
		timerLabels = new JLabel[1];
		timerLabels[0] = new JLabel("Your Time: 10:00");
		timerLabels[0].setFont(new Font("Monospaced", Font.BOLD, 16));
		timerLabels[0].setForeground(Color.BLACK);
		this.add(timerLabels[0]);
		timers = new Timer[2];
		playerTimeInSeconds = new int[2];
		playerTimeInSeconds[0] = 600;

		for (int i = 0; i < 2; i++) {
			final int playerIndex = i;
			timers[i] = new Timer(1000, new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					playerTimeInSeconds[playerIndex]--;
					updateTimerLabel(playerIndex);

					if (playerTimeInSeconds[playerIndex] <= 0) {
						timers[playerIndex].stop();
						computerWins();
					}
				}
			});
			timers[i].setInitialDelay(0);
		}
		startCurrentPlayerTimer();
		clickSound = new InputAudio("src/res/ButtonClick.wav");
		winChessSound = new InputAudio("src/res/WinChess.wav");
	}
	public void computerWins() {
		JDialog dialog = new JDialog(JOptionPane.getRootFrame(), "Computer Wins", true);
		dialog.getContentPane().setBackground(Color.WHITE);
		dialog.setLayout(new BorderLayout());
		dialog.setResizable(false);
		winChessSound.WinChessSound();

		ImageIcon icon = createImageIcon("/res/pump.png");

		JLabel messageLabel = new JLabel("<html>You ran out of time!<br/>Computer wins!</html>", icon, JLabel.CENTER);
		messageLabel.setFont(new Font("Monospaced", Font.PLAIN, 20));
		messageLabel.setForeground(new Color(255, 134, 58));
		messageLabel.setHorizontalAlignment(JLabel.CENTER);
		messageLabel.setVerticalTextPosition(JLabel.BOTTOM);
		messageLabel.setHorizontalTextPosition(JLabel.CENTER);

		dialog.add(messageLabel, BorderLayout.CENTER);

		JButton retryButton = new JButton("Retry");
		retryButton.setForeground(new Color(57, 47, 79));
		retryButton.addActionListener(e -> {
			clickSound.ButtonClickSound();
			dialog.dispose();
			JOptionPane.getRootFrame().dispose();
			new HomePage();
		});
		retryButton.setPreferredSize(new Dimension(100, 40));
		retryButton.setFont(new Font("Monospaced", Font.PLAIN, 16));

		JButton exitButton = new JButton("Exit");
		exitButton.setForeground(new Color(57, 47, 79));
		exitButton.addActionListener(e -> {
			clickSound.ButtonClickSound();
			dialog.dispose();
			JOptionPane.getRootFrame().dispose();
			System.exit(0);
		});
		exitButton.setPreferredSize(new Dimension(100, 40));
		exitButton.setFont(new Font("Monospaced", Font.PLAIN, 16));


		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		buttonPanel.setBackground(Color.WHITE);
		buttonPanel.add(retryButton);
		buttonPanel.add(exitButton);

		dialog.add(buttonPanel, BorderLayout.SOUTH);

		dialog.setSize(500, 250);
		dialog.setLocationRelativeTo(JOptionPane.getRootFrame());

		dialog.setVisible(true);
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

	static int oldMouseX,oldMouseY,newMouseX, newMouseY;
	static int squareSize=90;
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		for(int row = 0; row < 8; row++) {
			for(int col = 0; col < 8; col++) {
				if((row + col) % 2 == 0) { // Check if it's a white square
					g.setColor(new Color(210, 81, 66)); // Set color for white square
				} else {
					g.setColor(new Color(57, 47, 79)); // Set color for black square
				}
				g.fillRect(col * squareSize, row * squareSize, squareSize, squareSize); // Draw the square
			}
		}
		Image chessPiecesImage;
		chessPiecesImage=new ImageIcon("src/res/PiecesImages2.png").getImage();
		int x,y,x1=-1,y1=-1;


      /*  switch (main.AlphaBetaChess.chessBoard[oldMouseX][oldMouseY]) {
   	 	case "P": x1=5; y1=0;
        	 break;
        case "p": x1=5; y1=1;
            break;
        case "R": x1=2; y1=0;
            break;
        case "r": x1=2; y1=1;
            break;
        case "K": x1=4; y1=0;
            break;
        case "k": x1=4; y1=1;
            break;
        case "B": x1=3; y1=0;
            break;
        case "b": x1=3; y1=1;
            break;
        case "Q": x1=1; y1=0;
            break;
        case "q": x1=1; y1=1;
            break;
        case "A": x1=0; y1=0;
            break;
        case "a": x1=0; y1=1;
            break;
   	}
        g.drawImage(chessPiecesImage, (newMouseX-15), (newMouseY-15), (newMouseX+64), (newMouseY+64), x1*64, y1*64, (x1+1)*64, (y1+1)*64, this);*/
		for(int i=0;i<64;i++){
			x=-1;
			y=-1;
			switch (chessBoard[i/8][i%8]) {
				case "P": x=5; y=0;
					break;
				case "p": x=5; y=1;
					break;
				case "R": x=2; y=0;
					break;
				case "r": x=2; y=1;
					break;
				case "K": x=4; y=0;
					break;
				case "k": x=4; y=1;
					break;
				case "B": x=3; y=0;
					break;
				case "b": x=3; y=1;
					break;
				case "Q": x=1; y=0;
					break;
				case "q": x=1; y=1;
					break;
				case "A": x=0; y=0;
					break;
				case "a": x=0; y=1;
					break;
			}
			if(x!=-1 && y!=-1)
				g.drawImage(chessPiecesImage, (i%8)*squareSize, (i/8)*squareSize, (i%8+1)*squareSize, (i/8+1)*squareSize, x*64, y*64, (x+1)*64, (y+1)*64, this);
		}
		/*
		if (userPossibleMoves != null) {
			for (int i = 0; i < userPossibleMoves.length(); i += 5) {
				int startX = Character.getNumericValue(userPossibleMoves.charAt(i + 1)) * squareSize;
				int startY = Character.getNumericValue(userPossibleMoves.charAt(i)) * squareSize;
				g.setColor(Color.GREEN);
				g.fillRect(startX, startY, squareSize, squareSize);
			}
		}
		 */
	}
	private void startCurrentPlayerTimer() {
		timers[currentPlayerIndex].start();
	}

	private void stopCurrentPlayerTimer() {
		timers[currentPlayerIndex].stop();
	}

	private void updateTimerLabel(int playerIndex) {
		int minutes = playerTimeInSeconds[playerIndex] / 60;
		int seconds = playerTimeInSeconds[playerIndex] % 60;
		timerLabels[playerIndex].setText(String.format("Player %d Time: %02d:%02d", playerIndex + 1, minutes, seconds));
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if(e.getX()<8*squareSize && e.getY()<8*squareSize){//if mouse is dragged inside the chess board
			newMouseX=e.getX();
			newMouseY=e.getY();
			repaint();
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getX()<8*squareSize && e.getY()<8*squareSize){//if mouse is pressed inside the chess board
			oldMouseX=e.getX()/squareSize;
			oldMouseY=e.getY()/squareSize;
			//pickSound.PickPieceSound();
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(e.getX()<8*squareSize && e.getY()<8*squareSize){//if mouse is released inside the chess board
			newMouseX=e.getX()/squareSize;
			newMouseY=e.getY()/squareSize;
			String move;
			if(e.getButton()==MouseEvent.BUTTON1){
				if(newMouseY==0 && oldMouseY==1 && "P".equals(chessBoard[oldMouseY][oldMouseX])){
					//if pawn promotion
					move=""+oldMouseX+newMouseX+ chessBoard[newMouseY][newMouseX]+"QP";
				}
				else{	//if a regular move
					move=""+oldMouseY+oldMouseX+newMouseY+newMouseX+ chessBoard[newMouseY][newMouseX];
				}
				userPossibleMoves= possibleMoves();
				if(userPossibleMoves.replaceAll(move, "").length()<userPossibleMoves.length()){
					makeMove(move);
					flipBoard();
					makeMove(alphaBeta(globalDepth, Integer.MAX_VALUE, Integer.MIN_VALUE, "", 0));
					flipBoard();
					repaint();
					putSound.PutPieceSound();
				}
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}
}