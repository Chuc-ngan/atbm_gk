package view.component;

import view.tab.resultView.IResultView;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class ActionPanel extends JPanel {
    private IResultView view;
    private JPanel aloPanel;
    private JButton encryptButton;
    private JButton decryptButton;

    public ActionPanel() {
        setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        initializeButtons();
        addButtonsToPanel();
    }

    private void initializeButtons() {
        encryptButton = createButton("Encrypt", new Color(0, 128, 0));
        decryptButton = createButton("Decrypt", new Color(128, 0, 0));

        encryptButton.addActionListener(e -> {
            System.out.println(aloPanel.getClass().getSimpleName());
            view.encrypt(aloPanel);
        });
        decryptButton.addActionListener(e -> view.decrypt(aloPanel));
    }

    private JButton createButton(String text, Color backgroundColor) {
        JButton button = new JButton(text);
        button.setBackground(backgroundColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        return button;
    }

    private void addButtonsToPanel() {
        add(encryptButton);
        add(decryptButton);
    }

    private void handleAction() {
//        try {
//            action.execute();
//        } catch (InvalidAlgorithmParameterException | NoSuchPaddingException |
//                 IllegalBlockSizeException | NoSuchAlgorithmException |
//                 IOException | BadPaddingException | InvalidKeyException ex) {
//            throw new RuntimeException("Error during encryption/decryption", ex);
//        }
    }

    public void resetView(IResultView view, JPanel panel) {
        this.view = view;
        this.aloPanel = panel;
//        System.out.println(view instanceof FileEncryptionPanel ? "123" : "321");
    }

//    @FunctionalInterface
//    private interface Actionable {
//        void execute() throws InvalidAlgorithmParameterException, NoSuchPaddingException,
//                IllegalBlockSizeException, NoSuchAlgorithmException, IOException,
//                BadPaddingException, InvalidKeyException;
//    }
}
