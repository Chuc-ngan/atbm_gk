package view.component;

import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.Base64;
import java.util.Map;

public class KeyAsymmetricPanel extends JPanel {

    private JTextField keyField;
    public JComboBox<String> keySizeComboBox;  // ComboBox cho key size

    private final Map<String, String[]> keySizeOptions;

    public KeyAsymmetricPanel(Map<String, String[]> keySizeOptions) {
        this.keySizeOptions = keySizeOptions;

        setLayout(new GridBagLayout());
        setBorder(new TitledBorder("Key Info"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        // Tạo các thành phần giao diện
        keyField = new JTextField(40);
        keySizeComboBox = new JComboBox<>();

        // Thêm các trường với tiêu đề
        addTitledComponent("Key Size", keySizeComboBox, 0, gbc);
        addTitledComponent("Key", keyField, 1, gbc);
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

    public void updateKeyOptions(String algorithm) {
        if (keySizeOptions.containsKey(algorithm)) {
            keySizeComboBox.setModel(new DefaultComboBoxModel<>(keySizeOptions.get(algorithm)));
        }
    }

    public JComboBox<String> getKeySizeComboBox() {
        return keySizeComboBox;
    }
}

