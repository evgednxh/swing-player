package api;

import model.Song;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SefonMusicProvider implements ISongApiProvider {
    private final String url = "https://sefon.pro/news/";

    private final List<Song> songList;

    public SefonMusicProvider() {
        this.songList = new ArrayList<Song>();

        WebDriver browser;
        // todo: change path to webdriver for google + check google chrome version https://sites.google.com/a/chromium.org/chromedriver/downloads https://www.selenium.dev/documentation/en/webdriver/driver_requirements/
        System.setProperty("webdriver.chrome.driver", "");
        browser = new ChromeDriver();
        browser.get(url);

        try {
            Document doc = Jsoup.parse(browser.getPageSource());
            Elements rawSongList = doc.getElementsByClass("mp3");

            for (Element rawElement : rawSongList) {
                Elements urlWrapper = rawElement.getElementsByClass("btns");
                if (urlWrapper.size() > 0) {

                    // search for url
                    Song song = new Song();

                    Elements urlCnt = urlWrapper.get(0).getElementsByTag("span");
                    if (urlCnt.size() > 0) {
                        String downloadUrl = urlCnt.get(0).attr("data-url");
                        System.out.println(downloadUrl);
                        song.setUrl(downloadUrl);
                    }

                    Elements rawArtistName = rawElement.getElementsByClass("artist_name");
                    if (rawArtistName.size() > 0) {
                        String songName = rawArtistName.get(0).text();
                        song.setArtist(songName);
//                        System.out.println(songName);
                    }

                    Elements rawSongName = rawElement.getElementsByClass("song_name");
                    if (rawSongName.size() > 0) {
                        String songArtist = rawSongName.get(0).text();
                        song.setName(songArtist);
//                        System.out.println(songArtist);
                    }

                    this.songList.add(song);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<Song> getPopular() {
        return this.songList;
    }

    @Override
    public void download() {

    }
}
