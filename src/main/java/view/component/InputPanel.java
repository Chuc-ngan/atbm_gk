package view.component;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class InputPanel extends JPanel {

    public JTextArea textArea;

    public InputPanel() {
        setLayout(new BorderLayout(10, 10));
        // Text input panel
        JPanel textInputPanel = new JPanel(new BorderLayout());
        textInputPanel.setBorder(new TitledBorder("Enter Text"));
        textArea = new JTextArea(4, 30);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true));
        textInputPanel.add(new JScrollPane(textArea), BorderLayout.CENTER);
        add(textInputPanel, BorderLayout.CENTER);
    }
}
