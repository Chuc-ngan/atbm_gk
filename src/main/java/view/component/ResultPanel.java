package view.component;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class ResultPanel extends JPanel {

    private JTextArea encryptResultTextArea;
    private JTextArea decryptResultTextArea;

    public ResultPanel() {
        // Sử dụng BorderLayout thay vì GridLayout
        setLayout(new BorderLayout(10, 10));
        setBorder(new TitledBorder("Result"));

        encryptResultTextArea = new JTextArea(5, 30);
        encryptResultTextArea.setLineWrap(true);
        encryptResultTextArea.setWrapStyleWord(true);
        encryptResultTextArea.setEditable(true);
        encryptResultTextArea.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true));

        decryptResultTextArea = new JTextArea(5, 30);
        decryptResultTextArea.setLineWrap(true);
        decryptResultTextArea.setWrapStyleWord(true);
        decryptResultTextArea.setEditable(false);
        decryptResultTextArea.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true));

        JPanel encryptResultPanel = createTextAreaPanel("Encryption", encryptResultTextArea);
        JPanel decryptResultPanel = createTextAreaPanel("Decryption", decryptResultTextArea);

        // Tạo một JSplitPane để chia phần trên và dưới
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, encryptResultPanel, decryptResultPanel);
        splitPane.setDividerLocation(0.5); // Chia đều giữa phần trên và dưới
        splitPane.setResizeWeight(0.5);    // Cân bằng giữa các panel khi thay đổi kích thước

        // Đặt JSplitPane vào vị trí CENTER của BorderLayout
        add(splitPane, BorderLayout.CENTER);
    }

    private JPanel createTextAreaPanel(String title, JTextArea textArea) {
        JPanel panel = new JPanel(new BorderLayout());
        TitledBorder border = new TitledBorder(title);
        border.setTitleJustification(TitledBorder.CENTER);
        panel.setBorder(border);
        panel.add(new JScrollPane(textArea), BorderLayout.CENTER);
        return panel;
    }

    public JTextArea getEncryptResultTextArea() {
        return encryptResultTextArea;
    }

    public JTextArea getDecryptResultTextArea() {
        return decryptResultTextArea;
    }
}
