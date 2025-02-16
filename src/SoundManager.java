package src;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class SoundManager {

    public static void playSound(String soundFile) {
        try {
            URL soundURL = SoundManager.class.getResource("/sfx/" + soundFile); // ✅ Works for JAR files too
            if (soundURL == null) {
                System.err.println("Sound file not found: " + soundFile);
                return;
            }

            AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundURL);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
}
