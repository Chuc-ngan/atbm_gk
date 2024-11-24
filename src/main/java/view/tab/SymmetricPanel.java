package view.tab;

import algorithms.Symmetric;
import view.component.KeyInfoPanel;
import view.component.OptionPanel;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class SymmetricPanel extends JPanel {
    private static OptionPanel optionPanel;
    private KeyInfoPanel keyInfoPanel;
    private static Symmetric symmetric;

    public SymmetricPanel() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(400, 0));

        // Tạo các tùy chọn cho các thuật toán
        Map<String, String[]> modeOptions = createModeOptions();
        Map<String, String[]> paddingOptions = createPaddingOptions();
        Map<String, String[]> keySizeOptions = createKeySizeOptions();
        Map<String, String[]> ivSizeOptions = createIVSizeOptions();

        symmetric = new Symmetric();
        keyInfoPanel = new KeyInfoPanel(keySizeOptions, ivSizeOptions);
        optionPanel = new OptionPanel(modeOptions, paddingOptions, keyInfoPanel, symmetric, null);
        keyInfoPanel.updateKeyAndIVOptions("AES");

        // Tạo JSplitPane để chia hai panel
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, optionPanel, keyInfoPanel);
        splitPane.setContinuousLayout(true);
        splitPane.setDividerLocation(0.5);
        splitPane.setResizeWeight(0.5);

        // Thêm splitPane vào panel chính
        add(splitPane, BorderLayout.CENTER);

        optionPanel.getModeComboBox().addActionListener(e -> {
            updatePaddingOptions(optionPanel, paddingOptions);
            optionPanel.updateMode();
        });

    }



    private Map<String, String[]> createModeOptions() {
        Map<String, String[]> modeOptions = new HashMap<>();
        modeOptions.put("AES", new String[]{"None", "ECB", "CBC", "CFB", "CFB8", "CFB16", "CFB48", "CFB64", "CFB128", "OFB", "OFB8", "OFB16", "OFB48", "OFB64", "OFB128",  "CTS", "CTR"});
        modeOptions.put("DES", new String[]{"None", "ECB", "CBC"});
        return modeOptions;
    }

    private Map<String, String[]> createPaddingOptions() {
        Map<String, String[]> paddingOptions = new HashMap<>();
        paddingOptions.put("AES", new String[]{"NoPadding"});
        paddingOptions.put("DES", new String[]{"NoPadding", "PKCS5Padding"});
        return paddingOptions;
    }

    private Map<String, String[]> createKeySizeOptions() {
        Map<String, String[]> keySizsOptions = new HashMap<>();
        keySizsOptions.put("AES", new String[]{"128", "192", "256"});
        keySizsOptions.put("DES", new String[]{"56"});
        return keySizsOptions;
    }

    private Map<String, String[]> createIVSizeOptions() {
        Map<String, String[]> ivSizeOptions = new HashMap<>();
        ivSizeOptions.put("AES", new String[]{"0"});
        ivSizeOptions.put("DES", new String[]{"0"});
        return ivSizeOptions;
    }

    private void updatePaddingOptions(OptionPanel optionPanel, Map<String, String[]> paddingOptions) {
        String selectedMode = (String) optionPanel.getModeComboBox().getSelectedItem();
        String selectedAlgorithm = (String) optionPanel.getAlgorithmComboBox().getSelectedItem();

        if ("None".equals(selectedMode)) {
            updateForNoPadding(selectedAlgorithm, paddingOptions);
        } else {
            updateForWithPadding(selectedAlgorithm, selectedMode, paddingOptions);
        }

        // Cập nhật lại paddingOptions trong OptionPanel
        optionPanel.updatePaddingOptions(paddingOptions);
    }

    private void updateForNoPadding(String selectedAlgorithm, Map<String, String[]> paddingOptions) {
        paddingOptions.put(selectedAlgorithm, new String[]{"NoPadding"});
        // Cập nhật ivSize về 0
        optionPanel.updateIVSizeOptions(new String[]{"0"});
    }

    private void updateForWithPadding(String selectedAlgorithm, String selectedMode, Map<String, String[]> paddingOptions) {
        if ("AES".equals(selectedAlgorithm)) {
            if ("CTR".equals(selectedMode) || "CTS".equals(selectedMode)) {
                paddingOptions.put("AES", new String[]{"NoPadding"});
            } else {
                paddingOptions.put("AES", new String[]{"NoPadding", "PKCS5Padding", "ISO10126Padding"});
            }
        } else if ("DES".equals(selectedAlgorithm)) {
            paddingOptions.put("DES", new String[]{"NoPadding", "PKCS5Padding"});
        }
        // Cập nhật ivSize cho các chế độ khác
        updateIVSizeForMode(selectedAlgorithm, selectedMode);
    }

    private void updateIVSizeForMode(String selectedAlgorithm, String selectedMode) {
        if ("AES".equals(selectedAlgorithm)) {
            if("ECB".equals(selectedMode)) {
                optionPanel.updateIVSizeOptions(new String[]{"0"});
            }
            else {
                optionPanel.updateIVSizeOptions(new String[]{"128"});
            }
        } else if ("DES".equals(selectedAlgorithm)) {
            if ("CBC".equals(selectedMode)) {
                optionPanel.updateIVSizeOptions(new String[]{"64"});
            }
        }
    }
    public static String encrypt(String inputText) throws InvalidAlgorithmParameterException, IllegalBlockSizeException, NoSuchPaddingException, BadPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        return symmetric.encrypt(inputText);
    }

    public static String decrypt(String inputText) throws InvalidAlgorithmParameterException, IllegalBlockSizeException, NoSuchPaddingException, BadPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        return symmetric.decrypt(inputText);
    }

    public static void encryptFile(String inputFile, String outputFile) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, IOException, BadPaddingException, InvalidKeyException {
        symmetric.encryptFile(inputFile, outputFile);
    }

    public static void decryptFile(String inputFile, String outputFile) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, IOException, BadPaddingException, InvalidKeyException {
        symmetric.decryptFile(inputFile, outputFile);
    }
}
