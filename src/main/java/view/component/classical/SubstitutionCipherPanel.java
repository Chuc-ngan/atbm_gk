package view.component.classical;

import javax.swing.*;
import java.awt.*;
import java.util.Map;
import javax.swing.border.TitledBorder;

public class SubstitutionCipherPanel extends JPanel {
    private JTextField ciphertextAlphabetField;
    private JComboBox<String> caseStrategyCombo;
    private JComboBox<String> foreignCharsCombo;

    public SubstitutionCipherPanel() {
        // Set layout cho panel
        setLayout(new GridBagLayout());
        // Set khoảng cách viền cho panel
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 0, 10, 0); // khoảng cách giữa các thành phần

        // Tạo các thành phần
        ciphertextAlphabetField = new JTextField(20);  // Ô nhập cho bảng chữ cái mã hóa
        caseStrategyCombo = new JComboBox<>(new String[]{"Ignore Case", "Uppercase", "Lowercase"});  // Lựa chọn chế độ chữ hoa/chữ thường
        foreignCharsCombo = new JComboBox<>(new String[]{"Strict (A ≠ a)", "Maintain case", "Ignore case"});  // Lựa chọn cho các ký tự đặc biệt

        // Cấu hình và thêm các thành phần vào panel với border title
        addLabeledComponent("Ciphertext Alphabet:", ciphertextAlphabetField, gbc, 0);
        addLabeledComponent("Case Strategy:", caseStrategyCombo, gbc, 1);
        addLabeledComponent("Foreign Chars:", foreignCharsCombo, gbc, 2);
    }

    // Phương thức để tạo nhãn và thành phần với border title
    private void addLabeledComponent(String labelText, JComponent component, GridBagConstraints gbc, int row) {
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridy = row;

        // Tạo JPanel cho mỗi thành phần với TitledBorder
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0), labelText));

        // Thêm component vào panel
        panel.add(component, BorderLayout.CENTER);

        // Thêm panel vào GridBagLayout
        gbc.gridx = 0;
        gbc.weightx = 1.0;
        add(panel, gbc);
    }

    public void updateKey(Map<String, String> key) {
        if (key == null || key.isEmpty()) return;

        // Duyệt qua Map và chuyển các entry thành chuỗi "key -> value"
        StringBuilder keyStringBuilder = new StringBuilder();
        for (Map.Entry<String, String> entry : key.entrySet()) {
            keyStringBuilder.append(entry.getValue()).append(", ");
        }

        // Loại bỏ dấu phẩy cuối cùng
        if (keyStringBuilder.length() > 0) {
            keyStringBuilder.setLength(keyStringBuilder.length() - 2);
        }

        // Hiển thị chuỗi vào trường Ciphertext Alphabet
        ciphertextAlphabetField.setText(keyStringBuilder.toString());
    }


}
