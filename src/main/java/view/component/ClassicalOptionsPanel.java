package view.component;

import javax.swing.*;
import java.awt.*;
import java.io.File;

import algorithms.*;
import utils.Alphabet;

public class ClassicalOptionsPanel extends JPanel {
    public JComboBox<String> algorithmComboBox;
    public JComboBox<String> languageComboBox;
    public JComboBox<String> alphabetComboBox;

    public JButton loadKeyButton;
    public JButton saveKeyButton;
    public JButton generateKeyButton;

    private ClassicInfoPanel cipherInfoPanel;
    private Hill hill;
    private Affine affine;
    private Transposition transposition;
    private Substitution substitution;
    private Vigenere vigenere;

    public ClassicalOptionsPanel(ClassicInfoPanel cipherInfoPanel, Hill hill, Affine affine,Substitution substitution, Transposition transposition, Vigenere vigenere) {
        this.hill = hill;
        this.affine = affine;
        this.substitution = substitution;
        this.transposition = transposition;
        this.vigenere = vigenere;
        this.cipherInfoPanel = cipherInfoPanel;
        setLayout(new GridBagLayout());
        // Thêm border cho panel chính
        setBorder(BorderFactory.createTitledBorder("Algorithm Options"));
        setPreferredSize(new Dimension(390, 0)); // Độ rộng của bảng chọn
        GridBagConstraints gbc = new GridBagConstraints();

        // Giảm khoảng cách giữa các phần tử
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;  // Chiếm toàn bộ chiều rộng

        // Tạo các thành phần giao diện
        algorithmComboBox = new JComboBox<>(new String[]{"Hill", "Substitution", "Vigenère", "Affine", "Transposition"});
        languageComboBox = new JComboBox<>(new String[]{"English", "Vietnamese"});
        alphabetComboBox = new JComboBox<>(new String[]{String.join("", Alphabet.getEnglishCharsCase())}); // Mặc định hiển thị tiếng Anh

        // Thuật toán
        JPanel algorithmPanel = new JPanel(new BorderLayout()); // Dùng BorderLayout để comboBox chiếm toàn bộ chiều ngang
        algorithmPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0), "Algorithm"));; // Thêm border cho panel
        algorithmPanel.add(algorithmComboBox, BorderLayout.CENTER); // Thêm JComboBox vào panel

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3; // Chiếm toàn bộ chiều rộng
        gbc.weightx = 1.0; // Chiếm toàn bộ chiều rộng
        add(algorithmPanel, gbc); // Thêm panel vào layout chính

        // Độ dài khóa (Language)
        JPanel languagePanel = new JPanel(new BorderLayout()); // Dùng BorderLayout để comboBox chiếm toàn bộ chiều ngang
        languagePanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0), "Language")); // Thêm border cho panel
        languagePanel.add(languageComboBox, BorderLayout.CENTER); // Thêm JComboBox vào panel

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 3; // Chiếm toàn bộ chiều rộng
        gbc.weightx = 1.0; // Chiếm toàn bộ chiều rộng
        add(languagePanel, gbc); // Thêm panel vào layout chính

        // Alphabet
        JPanel alphabetPanel = new JPanel(new BorderLayout()); // Dùng BorderLayout để comboBox chiếm toàn bộ chiều ngang
        alphabetPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0), "Alphabet")); // Thêm border cho panel
        alphabetPanel.add(alphabetComboBox, BorderLayout.CENTER); // Thêm JComboBox vào panel

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 3; // Chiếm toàn bộ chiều rộng
        gbc.weightx = 1.0; // Chiếm toàn bộ chiều rộng
        add(alphabetPanel, gbc); // Thêm panel vào layout chính

        // Tạo các nút và thêm vào panel
        loadKeyButton = new JButton("Load Key");
        saveKeyButton = new JButton("Save Key");
        generateKeyButton = new JButton("Generate");

        // Thêm các nút vào panel dưới cùng theo hàng ngang và căn phải
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT)); // Căn phải các nút
        buttonPanel.add(loadKeyButton);
        buttonPanel.add(saveKeyButton);
        buttonPanel.add(generateKeyButton);

        // Đặt vị trí các nút dưới các ComboBox
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 3; // Chiếm toàn bộ chiều rộng
        gbc.weightx = 1.0; // Chiếm toàn bộ chiều rộng
        add(buttonPanel, gbc);

        // Lắng nghe sự kiện thay đổi lựa chọn ngôn ngữ
        languageComboBox.addActionListener(e -> updateAlphabetComboBox());
        generateKeyButton.addActionListener(e -> generateKey());
        algorithmComboBox.addActionListener(e -> {
            String selectedAlgorithm = (String) algorithmComboBox.getSelectedItem();
            cipherInfoPanel.updatePanelForAlgorithm(selectedAlgorithm);

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

    }

    private void saveKey(File selectedFile) {
        try {
            String selectedAlgorithm = (String) algorithmComboBox.getSelectedItem();
            switch (selectedAlgorithm) {
                case "Hill":
                    hill.saveKey(selectedFile);
                    break;
                case "Affine":
                    affine.saveKey(selectedFile);
                    break;
                case "Vigenère":
                    vigenere.saveKey(selectedFile);
                    break;
                case "Transposition":
                    transposition.saveKey(selectedFile);
                    break;
                case "Substitution":
                    substitution.saveKey(selectedFile);
                    break;
                default:
                    JOptionPane.showMessageDialog(this, "Key saving for " + selectedAlgorithm + " is not supported.");
                    break;
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error saving key: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadKey(File selectedFile) {
        try {
            String selectedAlgorithm = (String) algorithmComboBox.getSelectedItem();
            String alphabet = (String) alphabetComboBox.getSelectedItem();  // Lấy bảng chữ cái đã chọn
            // Thực hiện logic tải khóa
            switch (selectedAlgorithm) {
                case "Hill":
                    // Thực hiện tạo khóa cho thuật toán Hill
                    String matrixSize = String.valueOf(cipherInfoPanel.hillCipherPanel.getMatrixSizeComboBox().getSelectedItem());
                    hill.setAlphabet(alphabet);
                    hill.setSelectedSize(matrixSize);
                    hill.loadKey(selectedFile);

                    // Cập nhật key trên HillCipherPanel
                    if (cipherInfoPanel.hillCipherPanel != null) {
                        cipherInfoPanel.hillCipherPanel.updateKey(hill.getKeyMatrix());
                    }
                    break;
                case "Affine":
                    affine.setAlphabet(alphabet);
                    affine.loadKey(selectedFile);
                    if (cipherInfoPanel.affineCipherPanel != null) {
                        cipherInfoPanel.affineCipherPanel.updateKey(affine.getA(), affine.getB());
                    }
                    break;
                case "Vigenère":
                    vigenere.setAlphabet(alphabet);
                    vigenere.loadKey(selectedFile);

                    if(cipherInfoPanel.vigenereCipherPanel != null){
                        cipherInfoPanel.vigenereCipherPanel.updateKey(vigenere.getKey());
                    }
                    break;
                case "Transposition":
                    transposition.setAlphabet(alphabet);
                    transposition.loadKey(selectedFile);

                    if(cipherInfoPanel.transpositionCipherPanel != null){
                        cipherInfoPanel.transpositionCipherPanel.updateKey(transposition.getKey());
                    }
                    break;
                case "Substitution":
                    substitution.setAlphabet(alphabet);
                    substitution.loadKey(selectedFile);

                    if(cipherInfoPanel.substitutionCipherPanel != null){
                        cipherInfoPanel.substitutionCipherPanel.updateKey(substitution.getKey());
                    }
                    break;
                default:
                    JOptionPane.showMessageDialog(this, "Key loading for " + selectedAlgorithm + " is not supported.");
                    break;
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error loading key: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void generateKey() {
        String selectedAlgorithm = (String) algorithmComboBox.getSelectedItem();  // Lấy thuật toán đã chọn
        String alphabet = (String) alphabetComboBox.getSelectedItem();  // Lấy bảng chữ cái đã chọn

        // Tạo khóa cho từng thuật toán
        switch (selectedAlgorithm) {
            case "Hill":
                // Thực hiện tạo khóa cho thuật toán Hill
                String matrixSize = String.valueOf(cipherInfoPanel.hillCipherPanel.getMatrixSizeComboBox().getSelectedItem());
                hill.setSelectedSize(matrixSize);
                hill.setAlphabet(alphabet);
                hill.genKey();

                // Cập nhật key trên HillCipherPanel
                if (cipherInfoPanel.hillCipherPanel != null) {
                    cipherInfoPanel.hillCipherPanel.updateKey(hill.getKeyMatrix());
                }
                break;

            case "Substitution":
                substitution.setAlphabet(alphabet);
                substitution.genKey();

                // Cập nhật key trên HillCipherPanel
                if (cipherInfoPanel.substitutionCipherPanel != null) {
                    cipherInfoPanel.substitutionCipherPanel.updateKey(substitution.getKey());
                }
                break;

            case "Vigenère":
                vigenere.setAlphabet(alphabet);
                vigenere.genKey();

                if(cipherInfoPanel.vigenereCipherPanel != null){
                    cipherInfoPanel.vigenereCipherPanel.updateKey(vigenere.getKey());
                }
                break;

            case "Affine":
                affine.setAlphabet(alphabet);
                affine.genKey();
                // Cập nhật key trên HillCipherPanel
                if (cipherInfoPanel.affineCipherPanel != null) {
                    cipherInfoPanel.affineCipherPanel.updateKey(affine.getA(), affine.getB());
                }
                break;

            case "Transposition":
                transposition.setAlphabet(alphabet);
                transposition.genKey();

                if(cipherInfoPanel.transpositionCipherPanel != null){
                    cipherInfoPanel.transpositionCipherPanel.updateKey(transposition.getKey());
                }
                break;

            default:
                JOptionPane.showMessageDialog(this, "Please select a valid algorithm.");
                break;
        }
    }


    // Phương thức cập nhật alphabetComboBox khi lựa chọn ngôn ngữ thay đổi
    private void updateAlphabetComboBox() {
        // Kiểm tra lựa chọn ngôn ngữ
        String selectedLanguage = (String) languageComboBox.getSelectedItem();
        String selectedAlgorithm = (String) algorithmComboBox.getSelectedItem(); // Get the selected algorithm

        // Kiểm tra nếu là thuật toán Affine
        if ("Affine".equals(selectedAlgorithm) || "Hill".equals(selectedAlgorithm)) {
            // Nếu là Affine, chỉ hiển thị alphabet tiếng Việt chữ hoa
            if ("Vietnamese".equals(selectedLanguage)) {
                alphabetComboBox.setModel(new DefaultComboBoxModel<>(new String[]{String.join("", Alphabet.getVietnameseCharsCase())}));
            } else {
                // Nếu ngôn ngữ là tiếng Anh, vẫn hiển thị bảng chữ cái tiếng Anh
                alphabetComboBox.setModel(new DefaultComboBoxModel<>(new String[]{String.join("", Alphabet.getEnglishCharsCase()), String.join("", Alphabet.getEnglishChars())}));
            }
        } else {
            // Nếu không phải Affine, vẫn hiển thị bảng chữ cái như bình thường
            if ("English".equals(selectedLanguage)) {
                alphabetComboBox.setModel(new DefaultComboBoxModel<>(new String[]{String.join("", Alphabet.getEnglishCharsCase())}));
            } else if ("Vietnamese".equals(selectedLanguage)) {
                alphabetComboBox.setModel(new DefaultComboBoxModel<>(new String[]{String.join("", Alphabet.getVietnameseCharsCase()), String.join("", Alphabet.getVietnameseCharsLower())}));
            }
        }
    }
}
