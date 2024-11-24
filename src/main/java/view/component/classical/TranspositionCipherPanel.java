package view.component.classical;

import javax.swing.*;
import java.awt.*;

public class TranspositionCipherPanel extends JPanel {
    private JTextField keyField;                   // Ô nhập khóa
    private JCheckBox keepCharsCheckBox;           // CheckBox giữ ký tự đặc biệt, khoảng trắng
    private JComboBox<String> modeComboBox;        // ComboBox chọn chế độ Encrypt/Decrypt
    private JCheckBox paddingCheckBox;             // CheckBox để chọn thêm padding

    public TranspositionCipherPanel() {
        // Set layout cho panel
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); // Thêm khoảng cách viền cho panel
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);// khoảng cách giữa các thành phần

        // Tạo các thành phần
        keyField = new JTextField(20);
        keepCharsCheckBox = new JCheckBox("Keep Spaces, Punctuation, and Other Characters");
        keepCharsCheckBox.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
        modeComboBox = new JComboBox<>(new String[]{"Write by rows, read by columns (by default)", "Write by columns, read by columns"});
        paddingCheckBox = new JCheckBox("Add Padding");
        paddingCheckBox.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));

        // Thêm các thành phần vào panel
        addLabeledComponent("Key:", keyField, gbc, 0);
        addComponent(keepCharsCheckBox, gbc, 1);
        addLabeledComponent("Mode:", modeComboBox, gbc, 2);
        addComponent(paddingCheckBox, gbc, 3);

        // Set border cho panel
        setBorder(BorderFactory.createTitledBorder("Transposition Cipher"));
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

    // Phương thức để thêm thành phần không có nhãn
    private void addComponent(JComponent component, GridBagConstraints gbc, int row) {
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 2; // Chiếm cả hai cột
        add(component, gbc);
    }

    public void updateKey(int key) {
        keyField.setText(String.valueOf(key));
    }
}
