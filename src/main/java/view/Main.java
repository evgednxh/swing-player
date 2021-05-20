package view;

import api.ISongApiProvider;
import api.SefonMusicProvider;
import model.IPlaylistManager;
import model.Playlist;
import model.PlaylistManager;
import model.Song;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Set;


public class Main extends JFrame {
    public static final int TABLE_DIMENSION = 80;
    public static final int WIDTH = 1000;
    public static final int HEIGHT = 600;

    private final IPlaylistManager playlistManager;
    private final ISongApiProvider songApiProvider;
    private String currentPlaylistName;
    // home tab elements
    private JList<Song> homeSongList;
    private JPanel panel1;
    private JTabbedPane tabbedPane;
    // playlist tab elements
    private JButton createPlaylistBtn;
    private JTextField playlistNameTextField;
    private JComboBox<String> playlistNameComboBox;
    private JList<Song> playlistSongList;

    public Main() {
        this.songApiProvider = new SefonMusicProvider();
        this.playlistManager = new PlaylistManager();

        // ui
        this.setSize(WIDTH, HEIGHT);
        this.setTitle("Spotify player");
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        this.add(tabbedPane);

        this.homeSongList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                int index = homeSongList.locationToIndex(e.getPoint());
                Song song = homeSongList.getModel().getElementAt(index);
                System.out.println(song.getName());

                SongDialog dialog = new SongDialog(song, playlistManager);
                dialog.pack();
                dialog.setVisible(true);
            }
        });

        DefaultListModel<Song> defaultListModel = this.createDefaultListModel();
        this.homeSongList.setModel(defaultListModel);
        this.homeSongList.setCellRenderer(new SongListItemForm());

        // playlist tab
        this.initPlaylistTab();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Main();
            }
        });
    }

    private DefaultListModel<Song> createDefaultListModel() {
        List<Song> songList = this.songApiProvider.getPopular();
        DefaultListModel<Song> songDefaultListModel = new DefaultListModel<>();

        for (Song song : songList) {
            songDefaultListModel.addElement(song);
        }

        return songDefaultListModel;
    }

    private DefaultListModel<Song> createDefaultListModel(Playlist playlist) {
        List<Song> songList = playlist.getSongList();
        DefaultListModel<Song> songDefaultListModel = new DefaultListModel<>();

        for (Song song : songList) {
            songDefaultListModel.addElement(song);
        }

        return songDefaultListModel;
    }

    private void initPlaylistTab() {
        this.populatePlaylistCombobox();

        this.initPlaylistSongList();

        createPlaylistBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String newPlaylistName = playlistNameTextField.getText();
                boolean ok = playlistManager.addNewPlaylist(newPlaylistName);

                if (ok) {
                    playlistNameTextField.setText("");
                    PopUp.showPopUp(panel1, newPlaylistName + " was successfully created!");

                    playlistNameComboBox.removeAllItems();
                    populatePlaylistCombobox();
                } else {
                    PopUp.showPopUp(panel1, "Such playlist is already existed");
                }
            }
        });

        playlistNameComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentPlaylistName = (String) playlistNameComboBox.getSelectedItem();
                initPlaylistSongList();
            }
        });
    }

    private void initPlaylistSongList() {
        try {
            Playlist playlist = this.playlistManager.getSongsByPlaylistName(this.currentPlaylistName);
            DefaultListModel<Song> defaultListModel = this.createDefaultListModel(playlist);

            this.playlistSongList.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);

                    int index = playlistSongList.locationToIndex(e.getPoint());
                    Song song = playlistSongList.getModel().getElementAt(index);
                    System.out.println(song.getName());

                    SongDialog dialog = new SongDialog(song, playlistManager);
                    dialog.pack();
                    dialog.setVisible(true);
                }
            });

            this.playlistSongList.setModel(defaultListModel);
            this.playlistSongList.setCellRenderer(new SongListItemForm());

        } catch (NoSuchFieldException e) {
            PopUp.showPopUp(panel1, e.getMessage());
        }
    }

    private void populatePlaylistCombobox() {
        Set<String> playlistNamesSet = this.playlistManager.getAllPlaylistNames();
        for (String name : playlistNamesSet) {
            this.playlistNameComboBox.addItem(name);
        }
        currentPlaylistName = (String) playlistNameComboBox.getSelectedItem();
    }
}
