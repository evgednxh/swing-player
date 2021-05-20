package services.downloader;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

public class Downloader implements IDownloader {
    private static final String DIR_NAME = ".spotify_player";

    private String path;

    public Downloader() {
        this.path = System.getProperty("user.home") + "/" + DIR_NAME + "/";
    }

    @Override
    public String download(String rawUrl, String songName) throws IOException {
        songName += ".mp3";
        this.path += songName;

        File newFile = new File(this.path);
        File dir = new File(newFile.getParent());
        if (!dir.exists()) {
            dir.mkdirs();
        }

        URLConnection conn = new URL(rawUrl).openConnection();
        InputStream inputStream = conn.getInputStream();

        OutputStream outputStream = new FileOutputStream(this.path);

        byte[] buffer = new byte[4096];
        int length;
        while ((length = inputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, length);
        }

        outputStream.close();
        System.out.println("file is donwloaded");
        return this.path;
    }
}
