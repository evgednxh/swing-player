package model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Playlist {

    private UUID uuid;
    private String name;
    private List<Song> songList;

    public Playlist(String name, List<Song> songList) {
        this.uuid = UUID.randomUUID();
        this.name = name;
        this.songList = songList;
    }

    public Playlist(String name) {
        this.name = name;
        this.uuid = UUID.randomUUID();
        this.songList = new ArrayList<>();
    }

    public void addSong(Song song) {
        this.songList.add(song);
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Song> getSongList() {
        return songList;
    }

    public void setSongList(List<Song> songList) {
        this.songList = songList;
    }

    @Override
    public String toString() {
        return "{" +
                " \"uuid\":" + "\"" + uuid + "\"" +
                ", \"name\":" + "\"" + name + "\"" +
                ", \"songList\": " + songList + "" +
                '}';
    }
}
