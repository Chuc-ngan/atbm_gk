package view.component.classical;

import javax.swing.*;
import java.awt.*;

public class VigenereCipherPanel extends JPanel{
    private JTextField keyField;                   // Ô nhập key
    private JComboBox<String> keyModeCombo;        // ComboBox cho Key Mode
    private JComboBox<String> foreignCharsCombo;   // ComboBox cho Foreign Chars
    private JComboBox<String> caseStrategyCombo;   // ComboBox cho Case Strategy

    public VigenereCipherPanel() {
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 0, 10, 0);


        // Tạo các thành phần
        keyField = new JTextField(20);
        keyModeCombo = new JComboBox<>(new String[]{"Auto", "Manual"});
        foreignCharsCombo = new JComboBox<>(new String[]{"Include", "Ignore"});
        caseStrategyCombo = new JComboBox<>(new String[]{"Maintain case", "Ignore case", "Strict (A ≠ a)"});

        // Cấu hình và thêm các thành phần vào panel
        addLabeledComponent("Key:", keyField, gbc, 0);
        addLabeledComponent("Key Mode:", keyModeCombo, gbc, 1);
        addLabeledComponent("Foreign Chars:", foreignCharsCombo, gbc, 2);
        addLabeledComponent("Case Strategy:", caseStrategyCombo, gbc, 3);
    }

    // Phương thức để tạo nhãn và thành phần với tỷ lệ 20-80
    private void addLabeledComponent(String labelText, JComponent component, GridBagConstraints gbc, int row) {
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridy = row;

        // Tỷ lệ 20% cho label
        gbc.gridx = 0;
        gbc.weightx = 0.2;
        JLabel label = new JLabel(labelText);
        add(label, gbc);

        // Tỷ lệ 80% cho component
        gbc.gridx = 1;
        gbc.weightx = 0.8;
        add(component, gbc);
    }

    public void updateKey(String[] key) {
        if (key == null || key.length == 0) {
            keyField.setText(""); // Nếu key null hoặc rỗng, xóa nội dung hiện tại
            return;
        }

        // Chuyển mảng chuỗi thành một chuỗi duy nhất, ngăn cách bằng dấu phẩy
        String keyString = String.join(", ", key);

        // Hiển thị chuỗi đã nối vào ô nhập keyField
        keyField.setText(keyString);
    }

}
