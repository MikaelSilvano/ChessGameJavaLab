package main;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class InputAudio {
    private File ButtonClick;
    private File PutPiece;

    public InputAudio(String s) {

        ButtonClick = new File("ButtonClick.wav");
        PutPiece = new File ("PutPiece.wav");
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
