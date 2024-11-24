package algorithms;

import javax.swing.*;
import java.io.*;
import java.util.Arrays;
import java.util.Random;

public class Vigenere {
    private String alphabet;
    private String[] key;
    private int keyLength;

    public Vigenere() {
    }

    public void genKey() {
        key = new String[alphabet.length()];
        Random random = new Random();
        for (int i = 0; i < alphabet.length(); i++) {
            key[i] = String.valueOf(alphabet.charAt(random.nextInt(alphabet.length()))); // Chọn ký tự ngẫu nhiên
        }
    }

    public void loadKey(File file) throws IOException {
        // Kiểm tra xem tệp có tồn tại và đúng định dạng không
        if (!file.exists() || !file.getName().toLowerCase().endsWith(".key")) {
            throw new IOException("Invalid key file. Please select a valid .key file.");
        }

        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(file))) {
            String[] loadedKey = (String[]) inputStream.readObject(); // Đọc khóa
            String loadedAlphabet = (String) inputStream.readObject(); // Đọc bảng chữ cái

            // Kiểm tra bảng chữ cái hiện tại với bảng chữ cái trong tệp
            if (!loadedAlphabet.equals(this.alphabet)) {
                // Thông báo lỗi cho người dùng
                JOptionPane.showMessageDialog(null,
                        "The alphabet in the file does not match the current one.\n" +
                                "File alphabet: \"" + loadedAlphabet + "\"\n" +
                                "Please set the alphabet to match the file and try again.",
                        "Alphabet Mismatch",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            key = loadedKey;
            JOptionPane.showMessageDialog(null, "Key loaded successfully from " + file.getAbsolutePath(), "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    public void saveKey(File file) throws IOException {
        // Kiểm tra nếu bảng chữ cái hoặc khóa chưa được khởi tạo
        if (this.alphabet == null || key == null) {
            throw new IOException("Alphabet or key is not initialized.");
        }

        // Kiểm tra nếu đường dẫn là file hay thư mục
        boolean isFile = file.getAbsolutePath().contains(".");
        String path = file.getAbsolutePath();

        if (!isFile) {
            if (!file.exists()) {
                file.mkdirs(); // Tạo thư mục nếu chưa tồn tại
            }
            path = path + File.separator + "vigenere.key";
        } else {
            File parent = file.getParentFile();
            if (!parent.exists()) {
                parent.mkdirs(); // Tạo thư mục cha nếu chưa tồn tại
            }
        }

        File destination = new File(path);

        // Kiểm tra nếu bảng chữ cái hoặc khóa chưa được khởi tạo
        if (this.alphabet == null ||  key == null ) {
            throw new IOException("Alphabet or key is not initialized.");
        }

        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(destination))) {
            // Ghi khóa vào file
            outputStream.writeObject(key); // Lưu mảng khóa
            outputStream.writeObject(alphabet);

            System.out.println("Key saved successfully to " + destination.getAbsolutePath());
        } catch (IOException e) {
            throw new IOException("Error while saving key. Please try again.", e);
        }
    }

    public String encrypt(String plainText){
        if(!validation()) throw new RuntimeException("Key is not valid");
        int[] key = transformKey();
        StringBuilder sb = new StringBuilder();
        int i = 0;
        while(sb.length() < plainText.length()){
            i = Math.min(plainText.length() - sb.length(), key.length);
            sb.append(encryptArrChar(plainText.substring(sb.length(), sb.length() + i), key));
        }
        return sb.toString();
    }

    private String encryptArrChar(String substring, int[] key) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < substring.length(); i++) {
            if(!alphabet.contains(String.valueOf(substring.charAt(i)).toUpperCase())){
                sb.append(substring.charAt(i));
            }
            else{
                sb.append(encryptChar(substring.charAt(i), key[i], Character.isUpperCase(substring.charAt(i))));
            }
        }
        return sb.toString();
    }

    private String encryptChar(char c, int keyShift, boolean upperCase) {
        int currentIndex = alphabet.indexOf(Character.toUpperCase(c)); // Lấy vị trí ký tự hiện tại
        int encryptedIndex = (currentIndex + keyShift) % alphabet.length(); // Tính vị trí mã hóa

        char encryptedChar = alphabet.charAt(encryptedIndex); // Lấy ký tự mã hóa
        return upperCase ? String.valueOf(encryptedChar) : String.valueOf(Character.toLowerCase(encryptedChar));
    }


    private int[] transformKey() {
        int[] result = new int[key.length];
        for (int i = 0; i < key.length; i++) {
            result[i] = alphabet.indexOf(key[i]);        }

        return result;
    }

    protected boolean validation() {
        if (key == null) return false;
        return true;
    }

    public String decrypt(String cipherText){
        if (!validation()) throw new RuntimeException("Key is not valid");
        int[] key = transformKey();
        StringBuilder sb = new StringBuilder();
        int i = 0;

        while(sb.length() < cipherText.length()){
            i = Math.min(cipherText.length() - sb.length(), key.length);
            sb.append(decryptCharArr(cipherText.substring(sb.length(), sb.length() + i), key));
        }
        return sb.toString();
    }

    private String decryptCharArr(String substring, int[] key) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < substring.length(); i++){
            if(!alphabet.contains(String.valueOf(substring.charAt(i)).toUpperCase())){
                sb.append(substring.charAt(i));
            }
            else{
                sb.append(decryptChar(substring.charAt(i), key[i], Character.isUpperCase(substring.charAt(i))));            }
        }
        return sb.toString();
    }

    private String decryptChar(char c, int keyShift, boolean upperCase) {
        int currentIndex = alphabet.indexOf(Character.toUpperCase(c));
        int decryptedIndex = (currentIndex - keyShift + alphabet.length()) % alphabet.length();

        char decryptedChar = alphabet.charAt(decryptedIndex);
        return upperCase ? String.valueOf(decryptedChar) : String.valueOf(Character.toLowerCase(decryptedChar));
    }

    public String[] getKey() {
        return key;
    }

    public void setKey(String[] key) {
        this.key = key;
    }

    public String getAlphabet() {
        return alphabet;
    }

    public void setAlphabet(String alphabet) {
        this.alphabet = alphabet;
    }

    public static void main(String[] args) {
        Vigenere vigenere = new Vigenere();
        vigenere.setAlphabet("ABCDEFGHIJKLMNOPQRSTUVWXYZ");

        vigenere.genKey();
        System.out.println(Arrays.toString(vigenere.key));

        String plainText = "Hello World!";
        String cipherText = vigenere.encrypt(plainText);
        System.out.println("Cipher Text: " + cipherText);

        String decryptedText = vigenere.decrypt(cipherText);
        System.out.println("Decrypted Text: " + decryptedText);
    }
}

