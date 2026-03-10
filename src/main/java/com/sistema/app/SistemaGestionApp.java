package com.sistema.app;
import javax.swing.SwingUtilities;
import com.sistema.ui.MainFrame;


public class SistemaGestionApp {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }
}
