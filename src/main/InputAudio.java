package main;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class InputAudio {
    private File soundFile;

    public InputAudio(String s) {
        soundFile = new File("ButtonClick.wav");
    }

    public void play() {
        try {
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);

            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);

            clip.start();
            
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
}
