package services.downloader;

import java.io.IOException;

public interface IDownloader {
    String download(String rawUrl, String songName) throws IOException;
}
