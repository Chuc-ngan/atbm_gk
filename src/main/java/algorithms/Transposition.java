package algorithms;

import javax.swing.*;
import java.io.*;
import java.util.Random;

public class Transposition {
    private static int key;
    private String alphabet;


    public void genKey(){
        this.key = new Random().nextInt(alphabet.length());
    }

    public void saveKey(File file) throws IOException {
        // Kiểm tra nếu đường dẫn là file hay thư mục
        boolean isFile = file.getAbsolutePath().contains(".");
        String path = file.getAbsolutePath();

        if (!isFile) {
            if (!file.exists()) {
                file.mkdirs(); // Tạo thư mục nếu chưa tồn tại
            }
            path = path + File.separator + "transposition.key";
        } else {
            File parent = file.getParentFile();
            if (!parent.exists()) {
                parent.mkdirs(); // Tạo thư mục cha nếu chưa tồn tại
            }
        }

        File destination = new File(path);

        // Kiểm tra nếu bảng chữ cái hoặc khóa chưa được khởi tạo
        if (this.alphabet == null ||  key == 0 ) {
            throw new IOException("Alphabet or key is not initialized.");
        }

        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(destination))) {
            // Ghi bảng chữ cái
            outputStream.writeObject(this.alphabet);

            // Ghi khóa (Map)
            outputStream.writeObject(key);

            JOptionPane.showMessageDialog(null, "Key saved successfully to " + destination.getAbsolutePath(), "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            throw new IOException("Error while saving key. Please try again.", e);
        }

    }

    public void loadKey(File file) throws IOException {
        // Kiểm tra xem tệp có tồn tại và đúng định dạng không
        if (!file.exists() || !file.getName().toLowerCase().endsWith(".key")) {
            throw new IOException("Invalid key file. Please select a valid .key file.");
        }

        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(file))) {
            int loadedKey = (int) inputStream.readObject();            // Đọc khóa
            String loadedAlphabet = (String) inputStream.readObject(); // Đọc bảng chữ cái

            // Kiểm tra bảng chữ cái
            if (!loadedAlphabet.equals(this.alphabet)) {
                JOptionPane.showMessageDialog(null,
                        "The alphabet in the file does not match the current one.\n" +
                                "File alphabet: \"" + loadedAlphabet + "\"\n" +
                                "Please set the alphabet to match the file and try again.",
                        "Alphabet Mismatch",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            this.key = loadedKey;
            JOptionPane.showMessageDialog(null, "Key loaded successfully from " + file.getAbsolutePath(), "Success", JOptionPane.INFORMATION_MESSAGE);

        } catch (IOException | ClassNotFoundException e) {
            throw new IOException("Error while loading key. Please try again.", e);
        }


    }

    public String encrypt(String plainText){
        StringBuilder stringBuilder = new StringBuilder();

        for(char c : plainText.toCharArray()){
            if(!alphabet.contains(String.valueOf(c).toUpperCase())){
                stringBuilder.append(c);
            }
            else{
                stringBuilder.append(encryptChar(String.valueOf(c), Character.isUpperCase(c)));
            }
        }
        return stringBuilder.toString();
    }

    private String encryptChar(String s, boolean upperCase) {
        int move = key > alphabet.length() ? key % alphabet.length() : key;
        int current = alphabet.indexOf(s);
        
        if(!upperCase){
            current = alphabet.indexOf(s.toUpperCase());
            return String.valueOf(alphabet.charAt((current + move) % alphabet.length())).toLowerCase();
        }
        return String.valueOf(alphabet.charAt((current + move) % alphabet.length()));
    }

    public String decrypt(String cipherText){
        StringBuilder sb = new StringBuilder();
        for(char c : cipherText.toCharArray()) {
            if(!alphabet.contains(String.valueOf(c).toUpperCase())) {
                sb.append(c);
            }
            else{
                sb.append(decryptChar(String.valueOf(c), Character.isUpperCase(c)));
            }
        }
        return sb.toString();
    }

    private String decryptChar(String s, boolean upperCase) {
        int move = key > alphabet.length() ? key % alphabet.length() : key;
        int current = alphabet.indexOf(s);

        if(!upperCase){
            current = alphabet.indexOf(s.toUpperCase());
            return String.valueOf(alphabet.charAt((current - move - alphabet.length()) % alphabet.length())).toLowerCase();
        }
        return String.valueOf(alphabet.charAt((current - move - alphabet.length()) % alphabet.length() + alphabet.length()));
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public String getAlphabet() {
        return alphabet;
    }

    public void setAlphabet(String alphabet) {
        this.alphabet = alphabet;
    }

    public static void main(String[] args) {
        // Tạo đối tượng Transposition
        Transposition transposition = new Transposition();

        // Thiết lập bảng chữ cái
        transposition.setAlphabet("ABCDEFGHIJKLMNOPQRSTUVWXYZ");

        // Sinh khóa ngẫu nhiên
        transposition.genKey();
        System.out.println("Generated Key: " + transposition.getKey());

        // Chuỗi cần mã hóa
        String plainText = "HELLO WORLD";
        System.out.println("Plain Text: " + plainText);

        // Mã hóa chuỗi
        String cipherText = transposition.encrypt(plainText);
        System.out.println("Cipher Text: " + cipherText);

        // Giải mã chuỗi
        String decryptedText = transposition.decrypt(cipherText);
        System.out.println("Decrypted Text: " + decryptedText);

        // Kiểm tra kết quả
        if (plainText.equals(decryptedText)) {
            System.out.println("Encryption and Decryption are successful!");
        } else {
            System.out.println("Something went wrong in the process.");
        }
    }
}
