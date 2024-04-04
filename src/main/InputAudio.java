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
            // Get an AudioInputStream from the sound file
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);

            // Get a Clip to play the audio
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);

            // Start playing the audio
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
}
