package view.component;

import algorithms.Symmetric;

import javax.crypto.SecretKey;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.io.File;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

public class OptionPanel extends JPanel {
     private JComboBox<String> algorithmComboBox;
    private   JComboBox<String> modeComboBox;
     private JComboBox<String> paddingComboBox;

     Map<String, String[]> modeOptions;
    private final Map<String, String[]> paddingOptions;
    private KeyInfoPanel keyInfoPanel;

    // Các nút load, save và generate
    private JButton loadKeyButton;
    private JButton saveKeyButton;
    private JButton generateKeyButton;

    public Map<String, String[]> getModeOptions() {
        return modeOptions;
    }

    private Symmetric symmetric;
    private KeyAsymmetricPanel keyAsymmetricPanel;

    public OptionPanel(Map<String, String[]> modeOptions, Map<String, String[]> paddingOptions, KeyInfoPanel keyInfoPanel, Symmetric symmetric, KeyAsymmetricPanel keyAsymmetricPanel) {
        this.modeOptions = modeOptions;
//        System.out.println(modeOptions==null);
//        System.out.println("ngan: " +modeOptions.keySet());

        this.paddingOptions = paddingOptions;
        this.keyInfoPanel = keyInfoPanel;
        this.symmetric = symmetric;
        this.keyAsymmetricPanel = keyAsymmetricPanel;

        setLayout(new GridBagLayout());
        setBorder(new TitledBorder("Algorithm Options"));
        setPreferredSize(new Dimension(450, 0));
        GridBagConstraints gbc = new GridBagConstraints();

        // Giảm khoảng cách giữa các phần tử
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        // Khởi tạo các ComboBox
        algorithmComboBox = new JComboBox<>(modeOptions.keySet().toArray(new String[0])); // Lấy danh sách thuật toán
        algorithmComboBox.setSelectedItem("AES");
        modeComboBox = new JComboBox<>();
        paddingComboBox = new JComboBox<>();

        // Thêm các thành phần giao diện
        addTitledComboBox("Algorithm", algorithmComboBox, 0, gbc);
        addTitledComboBox("Mode", modeComboBox, 1, gbc);
        addTitledComboBox("Padding", paddingComboBox, 2, gbc);

        algorithmComboBox.addActionListener(e -> {
            String selectedAlgorithm = (String) algorithmComboBox.getSelectedItem();
            if (selectedAlgorithm != null) {
                keyInfoPanel.updateKeyAndIVOptions(selectedAlgorithm);
                keyAsymmetricPanel.updateKeyOptions(selectedAlgorithm);
            }
            if(keyInfoPanel != null){
                updateOptions();
            }
        });

        // Thêm các nút vào OptionPanel
        addButtons(gbc);
        generateKeyButton.addActionListener(e ->{
            String selectedAlgorithm = (String) algorithmComboBox.getSelectedItem();
            String selectedMode = (String) modeComboBox.getSelectedItem();
            String selectedPadding = (String) paddingComboBox.getSelectedItem();

            String ivSizeString = (String) keyInfoPanel.getIvSizeComboBox().getSelectedItem();
            String keySizeString = (String) keyInfoPanel.getKeySizeComboBox().getSelectedItem();

            int ivSize = Integer.parseInt(ivSizeString);  // Parse the String to int
            int keySize = Integer.parseInt(keySizeString);  // Parse the String to int
           
            // Sử dụng switch-case để xử lý theo thuật toán đã chọn
           if(selectedAlgorithm.equals("RSA")){
               JOptionPane.showMessageDialog(this, "RSA key generation not implemented yet.");
           }
           else{
               generateSymmetricKey(selectedMode, selectedPadding, ivSize, keySize, selectedAlgorithm);
           }
        });

        loadKeyButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File(".")); // Đặt thư mục mặc định là thư mục hiện tại

            fileChooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
                @Override
                public boolean accept(File file) {
                    return file.isDirectory() || file.getName().toLowerCase().endsWith(".key");
                }

                @Override
                public String getDescription() {
                    return "Key Files (*.key)";
                }
            });

            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                loadKey(selectedFile); // Gọi phương thức loadKey
            }
        });

        saveKeyButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File(".")); // Đặt thư mục mặc định là thư mục hiện tại

            int result = fileChooser.showSaveDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                // Kiểm tra và thêm phần mở rộng `.key` nếu chưa có
                if (!selectedFile.getName().toLowerCase().endsWith(".key")) {
                    selectedFile = new File(selectedFile.getAbsolutePath() + ".key");
                }
                saveKey(selectedFile); // Gọi phương thức saveKey
            }
        });
            updateOptions();
    }

    private void saveKey(File selectedFile) {
        symmetric.saveKey(selectedFile);
    }

    private void loadKey(File selectedFile) {
        String selectedAlgorithm = (String) algorithmComboBox.getSelectedItem();
        String selectedMode = (String) modeComboBox.getSelectedItem();
        String selectedPadding = (String) paddingComboBox.getSelectedItem();

        String ivSizeString = (String) keyInfoPanel.getIvSizeComboBox().getSelectedItem();
        String keySizeString = (String) keyInfoPanel.getKeySizeComboBox().getSelectedItem();

        int ivSize = Integer.parseInt(ivSizeString);  // Parse the String to int
        int keySize = Integer.parseInt(keySizeString);  // Parse the String to int

        symmetric.loadKey(selectedFile);
    }

    private void generateSymmetricKey(String selectedMode, String selectedPadding, int ivSize, int keySize, String selectedAlgorithm) {
        symmetric.setMode(selectedMode);
        symmetric.setIvSize(ivSize);
        symmetric.setPadding(selectedPadding);
        symmetric.setKeySize(keySize);
        symmetric.setAlgorthm(selectedAlgorithm);
        try {
            SecretKey secretKey = symmetric.genKey();
            keyInfoPanel.updateIvAndKey(symmetric.getIv(), secretKey);
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }


    }

    public void updateOptions() {
        String selectedAlgorithm = (String) algorithmComboBox.getSelectedItem();
        if (selectedAlgorithm == null) return;
        if(keyInfoPanel != null) keyInfoPanel.updateIvAndKey(null, null);
        modeComboBox.setModel(new DefaultComboBoxModel<>(modeOptions.get(selectedAlgorithm)));
        paddingComboBox.setModel(new DefaultComboBoxModel<>(paddingOptions.get(selectedAlgorithm)));
    }

    private void addTitledComboBox(String title, JComboBox<String> comboBox, int row, GridBagConstraints gbc) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0), title));
        panel.add(comboBox, BorderLayout.CENTER);

        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 3;
        gbc.weightx = 1.0;
        add(panel, gbc);
    }

    // Thêm các nút Load Key, Save Key và Generate vào OptionPanel
    private void addButtons(GridBagConstraints gbc) {
        loadKeyButton = new JButton("Load Key");
        saveKeyButton = new JButton("Save Key");
        generateKeyButton = new JButton("Generate");

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(loadKeyButton);
        buttonPanel.add(saveKeyButton);
        buttonPanel.add(generateKeyButton);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(buttonPanel, gbc);
    }

    public JComboBox<String> getAlgorithmComboBox() {
        return algorithmComboBox;
    }

    public JComboBox<String> getModeComboBox() {
        return modeComboBox;
    }

    public JComboBox<String> getPaddingComboBox() {
        return paddingComboBox;
    }

    public void updatePaddingOptions(Map<String, String[]> paddingOptions) {
        String selectedAlgorithm = (String) algorithmComboBox.getSelectedItem();
        String[] availablePadding = paddingOptions.get(selectedAlgorithm);

        // Update the padding combo box with the new options
        paddingComboBox.removeAllItems();
        for (String padding : availablePadding) {
            paddingComboBox.addItem(padding);
        }

        // Optionally select the first item
        if (availablePadding.length > 0) {
            paddingComboBox.setSelectedIndex(0);
        }
    }

    public void updateIVSizeOptions(String[] ivSizes) {
        JComboBox<String> ivSizeComboBox = keyInfoPanel.getIvSizeComboBox();

        // Make sure ivSizeComboBox is not null before proceeding
        if (ivSizeComboBox != null) {
            ivSizeComboBox.removeAllItems();
            for (String ivSize : ivSizes) {
                ivSizeComboBox.addItem(ivSize);
            }
            if (ivSizes.length > 0) {
                ivSizeComboBox.setSelectedIndex(0);
            }

            // Ensure the UI is updated
            ivSizeComboBox.revalidate();
            ivSizeComboBox.repaint();
        } else {
            System.err.println("Error: ivSizeComboBox is null.");
        }
    }

    public void updateMode() {
        keyInfoPanel.updateIvAndKey(null, null);
    }
}
