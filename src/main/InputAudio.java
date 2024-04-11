package main;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class InputAudio {
    private final File ButtonClick;
    private final File PickPiece;
    private final File PutPiece;
    private final File EatPiece;
    private final File PawnPromotion;
    private final File WinChess;


    public InputAudio(String s) {

        ButtonClick = new File("src/res/ButtonClick.wav");
        PickPiece = new File("src/res/PickUpPieces.wav");
        PutPiece = new File ("src/res/PutPieces.wav");
        EatPiece = new File("src/res/EatPieces.wav");
        PawnPromotion = new File("src/res/PawnPromotion.wav");
        WinChess = new File("src/res/WinChess.wav");

    }

    public void ButtonClickSound() {
        try {
            AudioInputStream buttonClickAudio = AudioSystem.getAudioInputStream(ButtonClick);

            Clip buttonClickClip = AudioSystem.getClip();
            buttonClickClip.open(buttonClickAudio);

            buttonClickClip.start();

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void PickPieceSound() {
        try {
            AudioInputStream buttonClickAudio = AudioSystem.getAudioInputStream(PickPiece);

            Clip buttonClickClip = AudioSystem.getClip();
            buttonClickClip.open(buttonClickAudio);

            buttonClickClip.start();

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
    public void PutPieceSound() {
        try {
            AudioInputStream putPieceAudio = AudioSystem.getAudioInputStream(PutPiece);

            Clip putPieceClip = AudioSystem.getClip();
            putPieceClip.open(putPieceAudio);

            putPieceClip.start();

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
    public void EatPieceSound() {
        try {
            AudioInputStream putPieceAudio = AudioSystem.getAudioInputStream(EatPiece);

            Clip putPieceClip = AudioSystem.getClip();
            putPieceClip.open(putPieceAudio);

            putPieceClip.start();

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
    public void PawnPromotionSound() {
        try {
            AudioInputStream putPieceAudio = AudioSystem.getAudioInputStream(PawnPromotion);

            Clip putPieceClip = AudioSystem.getClip();
            putPieceClip.open(putPieceAudio);

            putPieceClip.start();

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
    public void WinChessSound() {
        try {
            AudioInputStream putPieceAudio = AudioSystem.getAudioInputStream(WinChess);

            Clip putPieceClip = AudioSystem.getClip();
            putPieceClip.open(putPieceAudio);

            putPieceClip.start();

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
}
