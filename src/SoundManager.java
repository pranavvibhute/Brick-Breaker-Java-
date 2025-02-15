package src;
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class SoundManager {
    public static void playSound(String soundFile) {
        try {
            File file = new File("sfx/" + soundFile);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
}
