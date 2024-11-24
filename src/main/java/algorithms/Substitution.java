package algorithms;

import javax.swing.*;
import java.io.*;
import java.util.*;

public class Substitution {
    private static Map<String, String> key;
    private String alphabet;

    public Substitution() {
    }

    public void genKey(){
        // Chuyển đổi chuỗi alphabet thành danh sách ký tự
        List<Character> charList = new ArrayList<>();
        for (char c : alphabet.toCharArray()) {
            charList.add(c);
        }

        // Xáo trộn danh sách ký tự
        Collections.shuffle(charList);
        System.out.println(charList);
        Map<String, String> keys = new HashMap<>();
        for (int i = 0; i < charList.size(); i++) {
            keys.put(String.valueOf(alphabet.charAt(i)), String.valueOf(charList.get(i)));
        }
        key = keys;
    }

    public void loadKey(File file) throws IOException {
        // Kiểm tra xem tệp có tồn tại và đúng định dạng không
        if (!file.exists() || !file.getName().toLowerCase().endsWith(".key")) {
            throw new IOException("Invalid key file. Please select a valid .key file.");
        }

        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(file))) {
            // Đọc bảng chữ cái
            String loadedAlphabet = (String) inputStream.readObject();

            // Đọc khóa (Map)
            Map<String, String> loadedKey = (Map<String, String>) inputStream.readObject();

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

            // Gán giá trị cho key nếu hợp lệ
            Substitution.key = loadedKey;
            JOptionPane.showMessageDialog(null, "Key loaded successfully from " + file.getAbsolutePath(), "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (ClassNotFoundException | IOException e) {
            throw new IOException("Error while loading key. Please ensure the file is valid.", e);
        }
    }


    public void saveKey(File file) throws IOException {
        // Kiểm tra nếu đường dẫn là file hay thư mục
        boolean isFile = file.getAbsolutePath().contains(".");
        String path = file.getAbsolutePath();

        if (!isFile) {
            if (!file.exists()) {
                file.mkdirs(); // Tạo thư mục nếu chưa tồn tại
            }
            path = path + File.separator + "substitution.key";
        } else {
            File parent = file.getParentFile();
            if (!parent.exists()) {
                parent.mkdirs(); // Tạo thư mục cha nếu chưa tồn tại
            }
        }

        File destination = new File(path);

        // Kiểm tra nếu bảng chữ cái hoặc khóa chưa được khởi tạo
        if (this.alphabet == null || Substitution.key == null) {
            throw new IOException("Alphabet or key is not initialized.");
        }

        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(destination))) {
            // Ghi bảng chữ cái
            outputStream.writeObject(this.alphabet);

            // Ghi khóa (Map)
            outputStream.writeObject(Substitution.key);

            JOptionPane.showMessageDialog(null, "Key saved successfully to " + destination.getAbsolutePath(), "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            throw new IOException("Error while saving key. Please try again.", e);
        }
    }


    public String encrypt(String plainText){
        char[] chars = plainText.toCharArray();
        StringBuilder sb = new StringBuilder();
        for(char c : chars){
            if(!alphabet.contains(String.valueOf(c).toUpperCase())){
                sb.append(c);
            }
            else{
                sb.append(encryptToken(c, Character.isUpperCase(c)));
            }
        }
        return sb.toString();
    }

    private String encryptToken(char c, boolean upperCase) {
        if(upperCase){
            return key.get(String.valueOf(c)).toUpperCase();
        }
        return key.get(String.valueOf(c).toUpperCase()).toLowerCase();
    }

    public String decrypt(String cipherText){
        char[] chars = cipherText.toCharArray();
        StringBuilder sb = new StringBuilder();
        for(char c : chars){
            if(!alphabet.contains(String.valueOf(c).toUpperCase())){
                sb.append(c);
            }
            else{
                sb.append(decryptToken(c, Character.isUpperCase(c)));
            }
        }
        return sb.toString();
    }

    private String decryptToken(char c, boolean upperCase) {
        for(Map.Entry<String, String> entry : key.entrySet()){
            if (upperCase)
                if (entry.getValue().equals(String.valueOf(c)))
                    return entry.getKey();
            if (entry.getValue().equals(String.valueOf(c).toUpperCase()))
                return entry.getKey().toLowerCase();
        }
        return String.valueOf(c);
    }

    public static Map<String, String> getKey() {
        return key;
    }

    public static void setKey(Map<String, String> key) {
        Substitution.key = key;
    }

    public String getAlphabet() {
        return alphabet;
    }

    public void setAlphabet(String alphabet) {
        this.alphabet = alphabet;
    }

    public static void main(String[] args) {
        // Tạo đối tượng Substitution
        Substitution substitution = new Substitution();

        // Thiết lập bảng chữ cái
        substitution.alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

        // Sinh khóa ngẫu nhiên
        substitution.genKey();
        System.out.println("Generated Key: " + Substitution.key);

        // Chuỗi cần mã hóa
        String plainText = "AES is based on a design principle known as a substitution–permutation network, and is efficient in both software and hardware.";
        System.out.println("Plain Text: " + plainText);

        // Mã hóa chuỗi
        String cipherText = substitution.encrypt(plainText);
        System.out.println("Cipher Text: " + cipherText);

        // Giải mã chuỗi
        String decryptedText = substitution.decrypt(cipherText);
        System.out.println("Decrypted Text: " + decryptedText);

        // Kiểm tra kết quả
        if (plainText.equalsIgnoreCase(decryptedText)) {
            System.out.println("Encryption and Decryption are successful!");
        } else {
            System.out.println("Something went wrong in the process.");
        }
    }
}
