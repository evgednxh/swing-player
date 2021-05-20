package view;

import javazoom.jl.decoder.JavaLayerException;
import model.IPlaylistManager;
import model.Song;
import services.downloader.Downloader;
import services.downloader.IDownloader;
import services.player.IPlayer;
import services.player.Player;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.Set;

public class SongDialog extends JDialog {
    private JPanel contentPane;
    private JButton playBtn;
    private JButton buttonCancel;
    private JLabel songName;
    private JLabel songArtist;
    private JComboBox<String> playlistCombobox;

    private final IPlaylistManager playlistManager;
    private final IPlayer audioPlayer;
    private final IDownloader downloader;
    private final Song song;


    public SongDialog(Song song, IPlaylistManager playlistManager) {
        this.playlistManager = playlistManager;
        this.audioPlayer = new Player();
        this.downloader = new Downloader();
        this.song = song;

        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(playBtn);

        this.populatePlaylistCombobox();

        this.songName.setText(song.getName());
        this.songArtist.setText(song.getArtist());

        playBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        playlistCombobox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onCombobox(e);
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
                dispose();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
                dispose();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        try {
            String path = this.downloader.download(this.song.getUrl(), song.getName());
            audioPlayer.play(path);
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException | JavaLayerException e) {
            PopUp.showPopUp(contentPane, e.getMessage());
        }

        // add your code here
    }

    private void onCancel() {
        System.out.println("cancel");
        audioPlayer.stopPlaying();
        // add your code here if necessary
    }

    private void onCombobox(ActionEvent e) {
        String playlistName = (String) this.playlistCombobox.getSelectedItem();
        if (playlistManager.isPlaylistExists(playlistName)) {
            playlistManager.addSongToPlaylist(playlistName, song, contentPane);
        }
        // add your code here if necessary
    }

    private void populatePlaylistCombobox() {
        Set<String> playlistNamesSet = this.playlistManager.getAllPlaylistNames();
        for (String name : playlistNamesSet) {
            this.playlistCombobox.addItem(name);
        }
    }

}
