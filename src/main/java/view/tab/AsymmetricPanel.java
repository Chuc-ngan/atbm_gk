package view.tab;

import view.component.KeyAsymmetricPanel;
import view.component.OptionPanel;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class AsymmetricPanel extends JPanel {
    private OptionPanel optionPanel;
    private KeyAsymmetricPanel keyAsymmetricPanel;

    public AsymmetricPanel() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(400, 0));

        // Cấu hình dữ liệu cho các thuật toán BẤT đối xứng
        Map<String, String[]> modeOptions = createModeOptions();
        Map<String, String[]> keySizeOptions = createKeySizeOptions();
        Map<String, String[]> paddingOptions = createPaddingOptions();
        keyAsymmetricPanel = new KeyAsymmetricPanel(keySizeOptions);
        keyAsymmetricPanel.updateKeyOptions("RSA");
        optionPanel = new OptionPanel(modeOptions, paddingOptions, null, null, keyAsymmetricPanel);

        // Tạo JSplitPane để chia giao diện thành hai phần
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, optionPanel, keyAsymmetricPanel);
        splitPane.setContinuousLayout(true); // Hiển thị liên tục khi kéo
        splitPane.setDividerLocation(0.5);  // Vị trí phân chia ban đầu
        splitPane.setResizeWeight(0.5);     // Tỉ lệ kích thước mỗi bên

        // Thêm SplitPane vào panel chính
        add(splitPane, BorderLayout.CENTER);

        // Cập nhật tùy chọn padding khi thay đổi mode
        optionPanel.getModeComboBox().addActionListener(e -> updatePaddingOptions());
    }

    private Map<String, String[]> createModeOptions() {
        Map<String, String[]> modeOptions = new HashMap<>();
        modeOptions.put("RSA", new String[]{"ECB"});
        return modeOptions;
    }

    private Map<String, String[]> createPaddingOptions() {
        Map<String, String[]> paddingOptions = new HashMap<>();
        paddingOptions.put("RSA", new String[]{"NoPadding", "PKCS1Padding", "OAEPPadding", "OAEPWithMD5AndMGF1Padding"});
        return paddingOptions;
    }

    private Map<String, String[]> createKeySizeOptions() {
        Map<String, String[]> keySizeOptions = new HashMap<>();
        keySizeOptions.put("RSA", new String[]{"512", "1024", "2048", "4096"});
        return keySizeOptions;
    }

    private void updatePaddingOptions() {
        String selectedMode = (String) optionPanel.getModeComboBox().getSelectedItem();

        if ("ECB".equals(selectedMode)) {
            optionPanel.updatePaddingOptions(createPaddingOptions());
        }
    }

    public static String encrypt(String inputText) {
        // Triển khai logic mã hóa RSA
        return inputText; // Thay thế bằng kết quả mã hóa
    }

    public static String decrypt(String inputText) {
        // Triển khai logic giải mã RSA
        return inputText; // Thay thế bằng kết quả giải mã
    }

    public static void encryptFile(String inputFile, String outputFile) {
        // Triển khai logic mã hóa file
    }

    public static void decryptFile(String inputFile, String outputFile) {
        // Triển khai logic giải mã file
    }
}
