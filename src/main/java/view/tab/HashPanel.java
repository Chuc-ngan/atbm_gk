package view.tab;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class HashPanel extends JPanel {

    public HashPanel() {
        setLayout(new GridBagLayout()); // Sử dụng GridBagLayout thay vì BorderLayout
        GridBagConstraints gbc = new GridBagConstraints();

        // Tạo hai panel cho settings và input
        JPanel settingsPanel = new JPanel();
        settingsPanel.setBorder(new TitledBorder("Settings"));
        settingsPanel.setLayout(new GridBagLayout()); // Sử dụng GridBagLayout cho settingsPanel

        // Tạo panel Input Encoding
        JPanel inputEncodingPanel = new JPanel();
        inputEncodingPanel.setLayout(new BorderLayout(5, 5));
        inputEncodingPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0), "Input Encoding"));

        // Tạo ComboBox cho Input Encoding với các lựa chọn Hex và Base64
        String[] encodingOptions = {"Hex", "Base64"};
        JComboBox<String> encodingComboBox = new JComboBox<>(encodingOptions);
        inputEncodingPanel.add(encodingComboBox, BorderLayout.CENTER); // Thêm ComboBox vào inputEncodingPanel

        // Thêm inputEncodingPanel vào settingsPanel
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;  // Cho phép phần tử mở rộng chiều ngang
        gbc.weightx = 1.0;  // Cung cấp không gian cho panel mở rộng
        settingsPanel.add(inputEncodingPanel, gbc);

        // Tạo panel Output Bits
        JPanel outputBitsPanel = new JPanel();
        outputBitsPanel.setLayout(new GridBagLayout()); // Sử dụng GridBagLayout cho Output Bits
        outputBitsPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0), "Output Bits"));

        // Tạo JSpinner cho Output Bits với giá trị mặc định là 256
        SpinnerNumberModel spinnerModel = new SpinnerNumberModel(256, 1, 1024, 1); // giá trị mặc định 256, tối thiểu 1, tối đa 1024
        JSpinner outputBitsSpinner = new JSpinner(spinnerModel);

        // Cài đặt kích thước chiều rộng cho JSpinner
        outputBitsSpinner.setPreferredSize(new Dimension(100, 40));  // Cài đặt kích thước chiều rộng cho JSpinner

        // Cập nhật GridBagConstraints cho JSpinner trong outputBitsPanel
        GridBagConstraints gbcOutputBits = new GridBagConstraints();
        gbcOutputBits.gridx = 0;
        gbcOutputBits.gridy = 0;
        gbcOutputBits.anchor = GridBagConstraints.WEST; // Căn trái giá trị JSpinner
        gbcOutputBits.fill = GridBagConstraints.HORIZONTAL;  // Cho phép mở rộng cả chiều ngang và chiều dọc
        gbcOutputBits.weightx = 1.0;  // Cung cấp không gian để mở rộng theo chiều ngang
        gbcOutputBits.weighty = 1.0;  // Cung cấp không gian để mở rộng theo chiều dọc
        outputBitsPanel.add(outputBitsSpinner, gbcOutputBits);

        // Thêm outputBitsPanel vào settingsPanel
        gbc.gridx = 0;
        gbc.gridy = 1; // Đặt vào hàng tiếp theo
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL; // Cho phép Output Bits panel mở rộng chiều ngang
        gbc.weightx = 1.0; // Cung cấp không gian cho panel mở rộng
        settingsPanel.add(outputBitsPanel, gbc);

        // Tạo panel Input
        JPanel inputPanel = new JPanel();
        inputPanel.setBorder(new TitledBorder("Input"));

        // Tạo JSplitPane chia tỷ lệ
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, settingsPanel, inputPanel);
        splitPane.setDividerLocation(0.35); // Chia tỷ lệ 35% cho settingsPanel
        splitPane.setResizeWeight(0.35); // Cân bằng khi thay đổi kích thước

        // Thêm splitPane vào HashPanel
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.BOTH; // Cân bằng với các kích thước
        add(splitPane, gbc);
    }
}
