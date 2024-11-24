package view.tab.resultView;

import view.component.InputPanel;
import view.component.ResultPanel;
import view.tab.AsymmetricPanel;
import view.tab.ClassicPanel;
import view.tab.SymmetricPanel;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.swing.*;
import java.awt.*;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class TextEncryptionPanel extends JPanel implements IResultView {
    InputPanel inputPanel;
    ResultPanel resultPanel;

    public TextEncryptionPanel() {

        // Tạo InputPanel, ResultPanel và ActionPanel
        inputPanel = new InputPanel();
        resultPanel = new ResultPanel();
        setLayout(new BorderLayout(10, 10));

        // JSplitPane cho InputPanel và ResultPanel
        JSplitPane inputResultSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, inputPanel, resultPanel);
        inputResultSplitPane.setDividerLocation(0.5);
        inputResultSplitPane.setResizeWeight(0.5);

        add(inputResultSplitPane, BorderLayout.CENTER);
    }

    @Override
    public void encrypt(JPanel aloPanel) {
        String inputText = inputPanel.textArea.getText();
        String encryptedText = "";
        try {
            if (aloPanel instanceof SymmetricPanel)
                encryptedText = SymmetricPanel.encrypt(inputText);
            else if (aloPanel instanceof ClassicPanel)
                encryptedText = ClassicPanel.encrypt(inputText);
            else if (aloPanel instanceof AsymmetricPanel)
                encryptedText = AsymmetricPanel.encrypt(inputText);
            resultPanel.getEncryptResultTextArea().setText(encryptedText);
        } catch (InvalidAlgorithmParameterException | IllegalBlockSizeException | NoSuchPaddingException |
                 BadPaddingException | NoSuchAlgorithmException | InvalidKeyException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void decrypt(JPanel aloPanel) {
        String inputText = resultPanel.getEncryptResultTextArea().getText();
        String decryptedText = "";
        try {
            if (aloPanel instanceof SymmetricPanel)
                decryptedText = SymmetricPanel.decrypt(inputText);
            else if (aloPanel instanceof ClassicPanel)

                decryptedText = ClassicPanel.decrypt(inputText);
            else if (aloPanel instanceof AsymmetricPanel)

                decryptedText = AsymmetricPanel.decrypt(inputText);

            resultPanel.getDecryptResultTextArea().setText(decryptedText);

        } catch (InvalidAlgorithmParameterException | IllegalBlockSizeException | NoSuchPaddingException |
                 BadPaddingException | NoSuchAlgorithmException | InvalidKeyException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
