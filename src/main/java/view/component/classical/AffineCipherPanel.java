package view.component.classical;

import javax.swing.*;
import java.awt.*;

public class AffineCipherPanel extends JPanel {
    private JTextField keyAField; // Khóa 'a'
    private JTextField keyBField; // Khóa 'b'
    private JComboBox<String> caseStrategyCombo; // ComboBox cho Case Strategy
    private JComboBox<String> foreignCharsCombo; // ComboBox cho Foreign Chars

    public AffineCipherPanel(){
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20)); // Thêm khoảng cách viền cho panel
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 0, 10, 0); // khoảng cách giữa các thành phần

        // Tạo các thành phần
        keyAField = new JTextField(20); // Ô nhập cho khóa 'a'
        keyBField = new JTextField(20); // Ô nhập cho khóa 'b'
        caseStrategyCombo = new JComboBox<>(new String[]{"Ignore Case", "Uppercase", "Lowercase"});  // Lựa chọn chế độ chữ hoa/chữ thường
        foreignCharsCombo = new JComboBox<>(new String[]{"Maintain case", "Ignore case", "Strict (A ≠ a)"});  // Lựa chọn cho các ký tự đặc biệt

        // Cấu hình và thêm các thành phần vào panel
        addLabeledComponent("Enter 'a' (1-25, must have inverse mod 26):", keyAField, gbc, 0);
        addLabeledComponent("Enter 'b' (0-25):", keyBField, gbc, 1);
        addLabeledComponent("Case Strategy:", caseStrategyCombo, gbc, 2);
        addLabeledComponent("Foreign Chars:", foreignCharsCombo, gbc, 3);
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

    public void updateKey(int a, int b) {
        keyAField.setText(String.valueOf(a));  // Gán giá trị khóa 'a' vào ô nhập keyAField
        keyBField.setText(String.valueOf(b));
    }
}
