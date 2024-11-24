package view.component.classical;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HillCipherPanel extends JPanel {
    private JComboBox<String> matrixSizeComboBox;
    private JPanel matrixPanel;
    private JLabel errorLabel;
    public int[][] keyMatrix;
    String selectedSize;

    public HillCipherPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        String[] matrixSizes = {"2x2", "3x3", "4x4"};
        matrixSizeComboBox = new JComboBox<>(matrixSizes);

        matrixSizeComboBox.setPreferredSize(new Dimension(550, 80));
        matrixSizeComboBox.setMaximumSize(new Dimension(1000, 80));
        matrixSizeComboBox.setMinimumSize(new Dimension(300, 80));

        selectedSize = (String) matrixSizeComboBox.getSelectedItem();

        matrixSizeComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Lấy kích thước ma trận mới khi ComboBox thay đổi
                selectedSize = (String) matrixSizeComboBox.getSelectedItem();
                // Gọi phương thức để vẽ lại ma trận
                updateMatrix();
            }
        });

        add(matrixSizeComboBox);

        matrixPanel = new JPanel();
        matrixPanel.setPreferredSize(new Dimension(590, 150));
        add(matrixPanel);

        errorLabel = new JLabel(" ");
        errorLabel.setForeground(Color.RED);
        add(errorLabel);

        // Vẽ ma trận ban đầu khi khởi tạo
        updateMatrix();
    }

    // Phương thức để vẽ lại ma trận theo kích thước mới
    private void updateMatrix() {
        // Xóa các thành phần cũ trong matrixPanel trước khi vẽ lại
        matrixPanel.removeAll();

        // Lấy kích thước ma trận từ selectedSize
        int size = Integer.parseInt(selectedSize.split("x")[0]); // Ví dụ "2x2" -> 2
        keyMatrix = new int[size][size];

        // Tạo các ô nhập liệu cho ma trận
        matrixPanel.setLayout(new GridLayout(size, size));

        // Thêm các JTextField vào matrixPanel
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                JTextField textField = new JTextField(3); // Ô nhập liệu có kích thước nhỏ
                textField.setHorizontalAlignment(JTextField.CENTER);
                matrixPanel.add(textField);
            }
        }

        // Cập nhật lại giao diện sau khi thêm các thành phần mới
        matrixPanel.revalidate();
        matrixPanel.repaint();
    }

    public JComboBox<String> getMatrixSizeComboBox() {
        return matrixSizeComboBox;
    }

    public void updateKey(int[][] keyMatrix) {
        if (keyMatrix != null) {

            int size = keyMatrix.length;
            Component[] components = matrixPanel.getComponents();
            int index = 0;

            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    JTextField textField = (JTextField) components[index++];
                    textField.setText(String.valueOf(keyMatrix[i][j]));
                }
            }
        }
    }
}
