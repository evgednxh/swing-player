package services.player;

import javazoom.jl.decoder.JavaLayerException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public interface IPlayer {
    void play(String filePath) throws UnsupportedAudioFileException, IOException, LineUnavailableException, JavaLayerException;

    void stopPlaying();
}
