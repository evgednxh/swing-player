package api;

import model.Song;

import java.util.List;

public interface ISongApiProvider {
    List<Song> getPopular();

    void download();
}

