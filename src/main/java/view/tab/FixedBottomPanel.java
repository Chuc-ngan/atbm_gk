package view.tab;

import view.component.*;
import view.tab.resultView.FileEncryptionPanel;
import view.tab.resultView.IResultView;
import view.tab.resultView.TextEncryptionPanel;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.swing.*;
import java.awt.*;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class FixedBottomPanel extends JPanel {
    private JTabbedPane tabbedPane;
    JPanel currentPanel;
    TextEncryptionPanel textEncryptionPanel;
    FileEncryptionPanel fileEncryptionPanel;
    ActionPanel actionPanel;

    public FixedBottomPanel() {
        setPreferredSize(new Dimension(1200, 400)); // Chiếm 50% chiều cao của frame
        setLayout(new BorderLayout());

        actionPanel = new ActionPanel();

        // Tạo TextEncryptionPanel để chứa InputPanel và ResultPanel
        TextEncryptionPanel textEncryptionPanel = new TextEncryptionPanel();

        // Tạo FileEncryptionPanel cho mã hóa tệp
        fileEncryptionPanel = new FileEncryptionPanel();
        // Tạo tabbedPane và thêm các panel vào tab
        tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Text Encryption", textEncryptionPanel);
        tabbedPane.addTab("File Encryption", fileEncryptionPanel);
        tabbedPane.addChangeListener(e -> {
            actionPanel.resetView((IResultView) tabbedPane.getSelectedComponent(), currentPanel);
        });
        // Thêm tabbedPane vào BorderLayout.CENTER và actionPanel vào BorderLayout.SOUTH
        add(tabbedPane, BorderLayout.CENTER);
        add(actionPanel, BorderLayout.SOUTH);
    }

    public void updateForSelectedTab(JPanel selectedTab) {
        if (selectedTab != currentPanel) {
            tabbedPane.setSelectedIndex(0);
            currentPanel = selectedTab;
            actionPanel.resetView((IResultView) tabbedPane.getSelectedComponent(), selectedTab);
        }
    }
}
