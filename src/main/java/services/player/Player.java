package services.player;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackEvent;
import javazoom.jl.player.advanced.PlaybackListener;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Player implements IPlayer {
    private AdvancedPlayer player;
    private Thread playThread;

    @Override
    public void play(String filePath) throws IOException, JavaLayerException {
        File initialFile = new File(filePath);
        InputStream inputStream = new FileInputStream(initialFile);
//        URLConnection conn = new URL(filePath).openConnection();
//        InputStream inputStream = conn.getInputStream();

        AdvancedPlayer player = new AdvancedPlayer(inputStream);

        this.playThread = new Thread(() -> {
            try {
                player.play();
                player.setPlayBackListener(new PlaybackListener() {
                    @Override
                    public void playbackFinished(PlaybackEvent evt) {
                        player.stop();
                    }
                });
            } catch (Exception e) {
                System.out.println(e);
            }
        });
        this.playThread.start();
    }

    public void stopPlaying() {
        if (this.playThread != null)
            this.playThread.stop();

        if (this.player != null)
            this.player.stop();
    }
}
