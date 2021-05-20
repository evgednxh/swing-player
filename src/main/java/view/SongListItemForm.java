package view;

import model.Song;

import javax.swing.*;
import java.awt.*;


public class SongListItemForm implements ListCellRenderer<Song> {
    private JPanel listItemPanel;
    private JLabel songNameLabel;
    private JLabel artistLabel;

    public SongListItemForm() {
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends Song> list, Song value, int index, boolean isSelected, boolean cellHasFocus) {
        this.songNameLabel.setText(value.getName());
        this.artistLabel.setText(value.getArtist());
        return listItemPanel;
    }
}
