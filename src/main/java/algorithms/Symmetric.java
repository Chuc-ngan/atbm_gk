package algorithms;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;

public class Symmetric {
    private String algorthm;
    protected int keySize;
    protected int ivSize;
    protected String mode;
    protected String padding;
    protected SecretKey key;
    protected IvParameterSpec iv;

    public Symmetric() {
    }

    public SecretKey genKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGen = KeyGenerator.getInstance(algorthm);
        keyGen.init(keySize);
        key = keyGen.generateKey();
        if (ivSize > 0) {
            generateIV();
        }
        return key;
    }

    public IvParameterSpec generateIV() {
        if (ivSize <= 0) {
            throw new IllegalArgumentException("IV size must be greater than zero to generate an IV.");
        }
        byte[] ivBytes = new byte[ivSize/8];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(ivBytes);
        iv = new IvParameterSpec(ivBytes);

        return iv;
    }


    public void saveKey(File selectedFile) {


    }

    public void loadKey(File selectedFile) {

    }


    // Encrypt method
    public String encrypt(String plainText) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException {
        Cipher cipher;

        // Nếu chế độ là "None", không dùng IV và padding
        if ("None".equals(mode)) {
            cipher = Cipher.getInstance(algorthm);  // Chỉ cần key, không có IV hoặc padding
            cipher.init(Cipher.ENCRYPT_MODE, this.key);
        } else {
            cipher = Cipher.getInstance(algorthm + "/" + mode + "/" + padding);  // Với các chế độ khác, sử dụng IV và padding
            if (iv != null) {
                cipher.init(Cipher.ENCRYPT_MODE, this.key, iv);  // Sử dụng IV nếu có
            } else {
                cipher.init(Cipher.ENCRYPT_MODE, this.key);  // Không có IV cho các chế độ như ECB
            }
        }

        byte[] data = plainText.getBytes(StandardCharsets.UTF_8);
        byte[] encryptedData = cipher.doFinal(data);  // Encrypt the data

        return Base64.getEncoder().encodeToString(encryptedData); // Return Base64 encoded string
    }

    public void encryptFile(String src, String des) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IOException, IllegalBlockSizeException, BadPaddingException, IOException, InvalidAlgorithmParameterException {
        Cipher cipher;
        if ("None".equals(mode)) {
            cipher = Cipher.getInstance(algorthm);  // Chỉ cần key, không có IV hoặc padding
            cipher.init(Cipher.ENCRYPT_MODE, this.key);
        } else {
            cipher = Cipher.getInstance(algorthm + "/" + mode + "/" + padding);  // Với các chế độ khác, sử dụng IV và padding
            if (iv != null) {
                cipher.init(Cipher.ENCRYPT_MODE, this.key, iv);  // Sử dụng IV nếu có
            } else {
                cipher.init(Cipher.ENCRYPT_MODE, this.key);  // Không có IV cho các chế độ như ECB
            }
        }
        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(src));
             BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(des));
             CipherInputStream in = new CipherInputStream(bis, cipher)) {
            int i;
            byte[] buf = new byte[1024];
            while ((i = in.read(buf)) != -1) {
                bos.write(buf, 0, i);
            }
            bos.flush();
        }
    }

    // Decrypt method
    public String decrypt(String cipherText) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException {
        Cipher cipher;

        // Nếu chế độ là "None", không dùng IV và padding
        if ("None".equals(mode)) {
            cipher = Cipher.getInstance(algorthm);  // Chỉ cần key, không có IV hoặc padding
            cipher.init(Cipher.DECRYPT_MODE, this.key);
        } else {
            cipher = Cipher.getInstance(algorthm + "/" + mode + "/" + padding);  // Với các chế độ khác, sử dụng IV và padding
            if (iv != null) {
                cipher.init(Cipher.DECRYPT_MODE, this.key, iv);  // Sử dụng IV nếu có
            } else {
                cipher.init(Cipher.DECRYPT_MODE, this.key);  // Không có IV cho các chế độ như ECB
            }
        }

        byte[] decodedData = Base64.getDecoder().decode(cipherText); // Decode Base64 encoded string
        byte[] decryptedData = cipher.doFinal(decodedData); // Decrypt the data

        return new String(decryptedData, StandardCharsets.UTF_8); // Return the decrypted string
    }

    public void decryptFile(String src, String des) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IOException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        Cipher cipher;

        // Nếu chế độ là "None", không dùng IV và padding
        if ("None".equals(mode)) {
            cipher = Cipher.getInstance(algorthm);  // Chỉ cần key, không có IV hoặc padding
            cipher.init(Cipher.DECRYPT_MODE, this.key);
        } else {
            cipher = Cipher.getInstance(algorthm + "/" + mode + "/" + padding);  // Với các chế độ khác, sử dụng IV và padding
            if (iv != null) {
                cipher.init(Cipher.DECRYPT_MODE, this.key, iv);  // Sử dụng IV nếu có
            } else {
                cipher.init(Cipher.DECRYPT_MODE, this.key);  // Không có IV cho các chế độ như ECB
            }
        }

        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(src));
             BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(des));
             CipherInputStream in = new CipherInputStream(bis, cipher)) {
            int i;
            byte[] buf = new byte[1024];
            while ((i = in.read(buf)) != -1) {
                bos.write(buf, 0, i);
            }
            bos.flush();
        }
    }

    public int getKeySize() {
        return keySize;
    }

    public void setKeySize(int keySize) {
        this.keySize = keySize;
    }

    public IvParameterSpec getIv() {
        return iv;
    }

    public void setIv(IvParameterSpec iv) {
        this.iv = iv;
    }

    public String getPadding() {
        return padding;
    }

    public void setPadding(String padding) {
        this.padding = padding;
    }

    public int getIvSize() {
        return ivSize;
    }

    public void setIvSize(int ivSize) {
        this.ivSize = ivSize;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public SecretKey getKey() {
        return key;
    }

    public void setKey(SecretKey key) {
        this.key = key;
    }

    public String getAlgorthm() {
        return algorthm;
    }

    public void setAlgorthm(String algorthm) {
        this.algorthm = algorthm;
    }
    public static void main(String[] args) {
        try {
            // Example usage
            Symmetric symmetric = new Symmetric();
            symmetric.algorthm = "AES";   // Example: AES encryption
            symmetric.keySize = 128;       // Key size in bits
            symmetric.ivSize = 128;         // IV size (16 bytes for AES CBC mode)
            symmetric.mode = "CBC";        // CBC mode
            symmetric.padding = "PKCS5Padding";  // Padding scheme

            // Generate key and IV
            symmetric.genKey();

            // Encrypt data
//            String plainText = "Hello, world!";
//            String encrypted = symmetric.encrypt(plainText);
//            System.out.println("Encrypted Text: " + encrypted);
//
//            // Decrypt data
//            String decrypted = symmetric.decrypt(encrypted);
//            System.out.println("Decrypted Text: " + decrypted);

            symmetric.encryptFile("D:\\Downloads\\Thuchanh.txt", "D:\\Downloads\\en.txt");
            symmetric.decryptFile("D:\\Downloads\\en.txt", "D:\\Downloads\\de.txt");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}