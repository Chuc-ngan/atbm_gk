package algorithms;

import javax.swing.*;
import java.io.*;
import java.util.Random;

public class Affine {
    private int a; // Khóa 'a'
    private int b; // Khóa 'b'
    private String alphabet;

    public Affine(int a, int b, String alphabet) {
        this.a = a;
        this.b = b;
        this.alphabet = alphabet;
    }

    public Affine() {
    }

    public void genKey() {
        this.a = genA();
        this.b = new Random().nextInt(this.alphabet.length());
    }

    public void loadKey(File selectedFile) throws IOException, ClassNotFoundException {
        // Kiểm tra xem file có tồn tại và đúng định dạng không
        if (!selectedFile.exists() || !selectedFile.getName().toLowerCase().endsWith(".key")) {
            throw new IOException("Invalid key file. Please select a valid .key file.");
        }

        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(selectedFile))) {
            // Đọc giá trị từ tệp
            int loadedA = (int) inputStream.readObject();           // Khóa 'a'
            int loadedB = (int) inputStream.readObject();           // Khóa 'b'
            String loadedAlphabet = (String) inputStream.readObject(); // Bảng chữ cái

            // So sánh alphabet hiện tại với alphabet trong tệp
            if (!loadedAlphabet.equals(this.alphabet)) {
                // Thông báo lỗi cho người dùng
                JOptionPane.showMessageDialog(null,
                        "The alphabet in the file does not match the current one.\n" +
                                "File alphabet: \"" + loadedAlphabet + "\"\n" +
                                "Please set the alphabet to match the file and try again.",
                        "Alphabet Mismatch",
                        JOptionPane.ERROR_MESSAGE);
                return; // Ngừng xử lý nếu không khớp
            }

            // Nếu alphabet khớp, gán giá trị khóa
            this.a = loadedA;
            this.b = loadedB;

            // Thông báo thành công
            JOptionPane.showMessageDialog(null,
                    "Key loaded successfully from " + selectedFile.getAbsolutePath(),
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);

        } catch (IOException | ClassNotFoundException e) {
            throw new IOException("Error while loading key. Please try again.", e);
        }
    }



    public void saveKey(File selectedFile) throws IOException {
        // Kiểm tra nếu là file hay thư mục
        boolean isFile = selectedFile.getAbsolutePath().contains(".");
        String path = selectedFile.getAbsolutePath();

        if (isFile) {
            File parent = selectedFile.getParentFile();
            path = parent.getAbsolutePath();
            if (!parent.exists()) {
                parent.mkdirs(); // Tạo thư mục cha nếu chưa tồn tại
            }
        } else {
            if (!selectedFile.exists()) {
                selectedFile.mkdirs(); // Tạo thư mục nếu là đường dẫn thư mục
            }
        }

        // Kiểm tra nếu khóa chưa được tạo
        if (this.a == 0 || this.b == 0 || this.alphabet == null) {
            throw new IOException("Key is not fully initialized. Please generate a key before saving.");
        }

        // Nếu là thư mục, thêm tên file mặc định
        if (selectedFile.isDirectory()) {
            selectedFile = new File(selectedFile, "affine.key");
        }

        if (!selectedFile.getName().toLowerCase().endsWith(".key")) {
            throw new IOException("File name must end with '.key'.");
        }
        try {
            // Tạo file đích
            File destination = new File(path + File.separator + "affine.key");
            if (!destination.exists()) {
                destination.createNewFile(); // Tạo file nếu chưa tồn tại
            }

            // Ghi khóa vào file bằng ObjectOutputStream
            try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(destination))) {
                outputStream.writeObject(this.a);       // Ghi khóa 'a'
                outputStream.writeObject(this.b);       // Ghi khóa 'b'
                outputStream.writeObject(this.alphabet); // Ghi bảng chữ cái
            }

            JOptionPane.showMessageDialog(null, "Key saved successfully to " + destination.getAbsolutePath(), "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            throw new IOException("Error while saving key. Please try again.");
        }
    }


    private int genA() {
        int size = this.alphabet.length();
        int a = new Random().nextInt(size);
        while (gcd(a, size) != 1) {
            a = new Random().nextInt(size);
        }
        return a;
    }

    public String encrypt(String plaintext) {
        try {
            if (gcd(a, alphabet.length()) != 1) {
                throw new IllegalArgumentException("'a' must be coprime with the size of the alphabet.");
            }

            StringBuilder ciphertext = new StringBuilder();

            // Encrypt each character
            for (char c : plaintext.toCharArray()) {
                if (Character.isLetter(c)) {
                    int x = alphabet.indexOf(Character.toUpperCase(c));
                    int y = (a * x + b) % alphabet.length();
                    char encryptedChar = alphabet.charAt(y);
                    ciphertext.append(encryptedChar);
                } else {
                    ciphertext.append(c); // Non-alphabetic characters remain unchanged
                }
            }

            return ciphertext.toString();
        } catch (StringIndexOutOfBoundsException e) {
            JOptionPane.showMessageDialog(null, "Error: An index out of bounds error occurred during encryption!", "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    public String decrypt(String ciphertext) {
        try {
            if (gcd(a, alphabet.length()) != 1) {
                throw new IllegalArgumentException("'a' must be coprime with the size of the alphabet.");
            }

            // Find the modular inverse of 'a'
            int aInverse = modInverse(a, alphabet.length());

            StringBuilder plaintext = new StringBuilder();

            // Decrypt each character
            for (char c : ciphertext.toCharArray()) {
                if (Character.isLetter(c)) {
                    int y = alphabet.indexOf(Character.toUpperCase(c)); // Get the index of the character in the alphabet
                    int x = (aInverse * (y - b + alphabet.length())) % alphabet.length(); // Apply the Affine cipher decryption formula
                    char decryptedChar = alphabet.charAt(x); // Get the decrypted character
                    plaintext.append(decryptedChar);
                } else {
                    plaintext.append(c); // Non-alphabetic characters remain unchanged
                }
            }

            return plaintext.toString();
        } catch (StringIndexOutOfBoundsException e) {
            JOptionPane.showMessageDialog(null, "Error: An index out of bounds error occurred during decryption!", "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    // Tính ước chung lớn nhất (GCD)
    private int gcd(int a, int b) {
        if (b == 0) return a;
        return gcd(b, a % b);
    }

    // Tìm nghịch đảo của a mod alphabetSize
    private int modInverse(int a, int m) {
        for (int x = 1; x < m; x++) {
            if ((a * x) % m == 1) {
                return x;
            }
        }
        return -1;  // Nếu không có nghịch đảo
    }

    public int getA() {
        return a;
    }

    public String getAlphabet() {
        return alphabet;
    }

    public int getB() {
        return b;
    }

    public void setA(int a) {
        this.a = a;
    }

    public void setAlphabet(String alphabet) {
        this.alphabet = alphabet;
    }

    public void setB(int b) {
        this.b = b;
    }



}