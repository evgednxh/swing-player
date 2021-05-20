package services.storage;

import com.google.gson.Gson;
import model.Playlist;

import java.util.HashMap;
import java.util.Map;

public class StorageModel {
    private Map<String, Playlist> playlists;

    public StorageModel(Map<String, Playlist> playlists) {
        this.playlists = playlists;
    }

    public StorageModel() {
        this.playlists = new HashMap<>();
    }

    public Map<String, Playlist> getPlaylists() {
        if (this.playlists == null) {
            this.playlists = new HashMap<>();
        }
        return this.playlists;
    }

    public void setPlaylists(Map<String, Playlist> playlists) {
        this.playlists = playlists;
    }

    @Override
    public String toString() {
        String s = "{\"playlists\": ";
        s += new Gson().toJson(this.playlists);
        s += "}";
        return s;
    }
}
