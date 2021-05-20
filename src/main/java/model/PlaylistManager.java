package model;

import services.storage.Storage;
import services.storage.StorageModel;
import view.PopUp;

import java.awt.*;
import java.util.Map;
import java.util.Set;

public class PlaylistManager implements IPlaylistManager {
    public static final String PLAYLIST_TEMP_NAME = "playlist5236"; // todo: remove after file upload feature
    private final Storage storage;

    public PlaylistManager() {
        this.storage = new Storage();
    }

    @Override
    public boolean addSongToPlaylist(String playlistName, Song song, Component c) {
        Map<String, Playlist> storagePlaylists = this.storage.loadFromFile().getPlaylists();
        if (this.storage.loadFromFile().getPlaylists().containsKey(playlistName)) {
            Playlist playlist = storagePlaylists.get(playlistName);
            if (!playlist.getSongList().contains(song)) {
                playlist.addSong(song);

                storagePlaylists.put(playlistName, playlist);
                this.storage.writeToFile(new StorageModel(storagePlaylists));

                System.out.println(this);
                return true;
            }
            return false;
        }

        PopUp.showPopUp(c, "Such playlist doesnt exists");
        return false;
    }

    @Override
    public boolean isPlaylistExists(String playlistName) {
        Map<String, Playlist> storageData = this.storage.loadFromFile().getPlaylists();
        return storageData.containsKey(playlistName);
    }

    @Override
    public boolean addNewPlaylist(String playlistName) {

        // read from file
        Map<String, Playlist> storageData = this.storage.loadFromFile().getPlaylists();

        if (!storageData.containsKey(playlistName)) {
            storageData.put(playlistName, new Playlist(playlistName));

            // save to file
            this.storage.writeToFile(new StorageModel(storageData));
            return true;
        }

        return false;
    }

    @Override
    public Set<String> getAllPlaylistNames() {
        // read from file
        Map<String, Playlist> storageData = this.storage.loadFromFile().getPlaylists();
        return storageData.keySet();
    }

    @Override
    public Playlist getSongsByPlaylistName(String playlistName) throws NoSuchFieldException {
        // read from file
        Map<String, Playlist> storageData = this.storage.loadFromFile().getPlaylists();
        if (!storageData.containsKey(playlistName)) {
            throw new NoSuchFieldException("There is no playlist with such name");
        }

        return storageData.get(playlistName);
    }
}
