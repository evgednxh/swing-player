package model;


import java.awt.*;
import java.util.Set;

public interface IPlaylistManager {
    boolean addNewPlaylist(String playlistName);

    Set<String> getAllPlaylistNames();

    Playlist getSongsByPlaylistName(String playlistName) throws NoSuchFieldException;

    boolean addSongToPlaylist(String playlistName, Song song, Component c);

    boolean isPlaylistExists(String playlistName);
}
