package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class AIUserInterface extends JPanel implements MouseListener, MouseMotionListener{
	InputAudio promotionSound;
	InputAudio eatSound;
	InputAudio putSound;
	InputAudio pickSound;
	private String userPossibleMoves;
	public AIUserInterface() {
		promotionSound = new InputAudio("src/res/PawnPromotion.wav");
		eatSound = new InputAudio("src/res/EatPieces.wav");
		pickSound = new InputAudio("src/res/PickUpPieces.wav");
		putSound = new InputAudio("src/res/PutPieces.wav");
	}

	static int oldMouseX,oldMouseY,newMouseX, newMouseY;
	static int squareSize=105;
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		this.addMouseListener(this);
        this.addMouseMotionListener(this);
		for(int i=0;i<64;i+=2){
			g.setColor(new Color(255,200,100));
			g.fillRect((i%8+(i/8)%2)*squareSize, (i/8)*squareSize, squareSize, squareSize);
			g.setColor(new Color(150,50,30));
			g.fillRect(((i+1)%8-((i+1)/8)%2)*squareSize, ((i+1)/8)*squareSize, squareSize, squareSize);
		}
		Image chessPiecesImage;
        chessPiecesImage=new ImageIcon("src/res/ChessPieces.png").getImage();
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
	    	 switch (AIChessPageEasy.chessBoard[i/8][i%8]) {
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
				if(newMouseY==0 && oldMouseY==1 && "P".equals(AIChessPageEasy.chessBoard[oldMouseY][oldMouseX])){
					//if pawn promotion
					move=""+oldMouseX+newMouseX+ AIChessPageEasy.chessBoard[newMouseY][newMouseX]+"QP";
				}	
				else{	//if a regular move
					move=""+oldMouseY+oldMouseX+newMouseY+newMouseX+ AIChessPageEasy.chessBoard[newMouseY][newMouseX];
				}
				userPossibleMoves= AIChessPageEasy.possibleMoves();
				if(userPossibleMoves.replaceAll(move, "").length()<userPossibleMoves.length()){
					AIChessPageEasy.makeMove(move);
					AIChessPageEasy.flipBoard();
					AIChessPageEasy.makeMove(AIChessPageEasy.alphaBeta(AIChessPageEasy.globalDepth, Integer.MAX_VALUE, Integer.MIN_VALUE, "", 0));
					AIChessPageEasy.flipBoard();
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