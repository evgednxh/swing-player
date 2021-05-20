package view;

import javax.swing.*;
import java.awt.*;

public class PopUp {
    public static void showPopUp(Component c, String msg) {
        JPopupMenu popupMenu = new JPopupMenu();
        popupMenu.setSize(200, 100);
        popupMenu.setVisible(true);
        JOptionPane.showMessageDialog(c, msg);
    }
}
