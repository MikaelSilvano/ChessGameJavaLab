package main;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class InputAudio {
    private File ButtonClick;
    private File PickPiece;
    private File PutPiece;
    private File EatPiece;

    public InputAudio(String s) {

        ButtonClick = new File("src/res/ButtonClick.wav");
        PickPiece = new File("src/res/PickUpPieces.wav");
        PutPiece = new File ("src/res/PutPieces.wav");
        EatPiece = new File("src/resEatPieces.wav");
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
}
