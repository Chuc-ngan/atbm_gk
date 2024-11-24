package view.component;

import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.Base64;
import java.util.Map;

public class KeyInfoPanel extends JPanel {

    private JTextField keyField;
    private JTextField ivField;
    private JComboBox<String> ivSizeComboBox;
    public JComboBox<String> keySizeComboBox;  // ComboBox cho key size

    private final Map<String, String[]> keySizeOptions;
    private final Map<String, String[]> ivSizeOptions;

    public KeyInfoPanel(Map<String, String[]> keySizeOptions, Map<String, String[]> ivSizeOptions) {
        this.keySizeOptions = keySizeOptions;
        this.ivSizeOptions = ivSizeOptions;

        setLayout(new GridBagLayout());
        setBorder(new TitledBorder("Key Info"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        // Tạo các thành phần giao diện
        keyField = new JTextField(40);
        ivField = new JTextField(40);
        keySizeComboBox = new JComboBox<>();
        ivSizeComboBox = new JComboBox<>();

        // Thêm các trường với tiêu đề
        addTitledComponent("Key Size", keySizeComboBox, 0, gbc);
        addTitledComponent("Key", keyField, 1, gbc);
        addTitledComponent("IV Size", ivSizeComboBox, 2, gbc);
        addTitledComponent("IV", ivField, 3, gbc);
    }

    private void addTitledComponent(String title, JComponent component, int row, GridBagConstraints gbc) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0), title));
        panel.add(component, BorderLayout.CENTER);

        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 3; // Chiếm toàn bộ hàng
        gbc.weightx = 1.0;
        add(panel, gbc);
    }

    public void updateKeyAndIVOptions(String algorithm) {
        if (keySizeOptions.containsKey(algorithm)) {
            keySizeComboBox.setModel(new DefaultComboBoxModel<>(keySizeOptions.get(algorithm)));
        }
        if (ivSizeOptions.containsKey(algorithm)) {
            ivSizeComboBox.setModel(new DefaultComboBoxModel<>(ivSizeOptions.get(algorithm)));
        }
    }

    public JComboBox<String> getIvSizeComboBox() {
        return ivSizeComboBox;
    }

    public JComboBox<String> getKeySizeComboBox() {
        return keySizeComboBox;
    }

    // Cập nhật giá trị cho IV và Key (nếu có)
    public void updateIvAndKey(IvParameterSpec iv,SecretKey secretKey) {
        if (iv == null) {
            ivField.setText("");  // Đặt lại IV về rỗng trên giao diện
        } else {
            ivField.setText(bytesToBase64(iv.getIV()));  // Hiển thị IV nếu có
        }

        if (secretKey == null) {
            keyField.setText("");  // Đặt lại Key về rỗng trên giao diện
        } else {
            keyField.setText(bytesToBase64(secretKey.getEncoded()));  // Hiển thị Key nếu có
        }
    }

    private String bytesToBase64(byte[] bytes) {
        return Base64.getEncoder().encodeToString(bytes);
    }

}
