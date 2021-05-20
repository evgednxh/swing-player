package model;

// todo: add genre sorting
public class Song {
    private String name;
    private String artist;
    private String url;

    public Song() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        String s = "{";

        s += " \"name\":" + "\"" + name + "\",";
        s += " \"artist\":" + "\"" + artist + "\",";
        s += " \"url\":" + "\"" + url + "\"";

        s += "}";
        return s;
    }
}
