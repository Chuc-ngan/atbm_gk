package view.frame;

import custom.Theme;
import javax.swing.*;

public class GUI extends JFrame{

    private MainPanel mainPanel;

    public GUI() {
        Theme theme = new Theme();
        theme.setup();
        setTitle("Tool mã hóa");
        init();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void init() {
        mainPanel =  new MainPanel();
        add(mainPanel);
    }

    public static void main(String[] args) {
        Theme theme = new Theme();
        theme.setup();
        SwingUtilities.invokeLater(() -> new GUI());
    }

}
