package algorithms;

import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.io.*;
import java.security.*;

public class Asymmetric {
    private String algorthm;
    protected int keySize;
    protected int ivSize;
    protected String mode;
    protected String padding;
    KeyPair keyPair;
    PrivateKey privateKey;
    PublicKey publicKey;
    IvParameterSpec iv;
    SecretKey secretKey;

    public Asymmetric() {
    }

    public void genKey() throws NoSuchAlgorithmException, IOException {
        KeyPairGenerator generator = KeyPairGenerator.getInstance(algorthm);
        generator.initialize(keySize);
        keyPair = generator.generateKeyPair();
        publicKey = keyPair.getPublic();
        privateKey = keyPair.getPrivate();

        try (FileOutputStream fos = new FileOutputStream("key-pri.dat");
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(privateKey);
        }

        try (FileOutputStream fos = new FileOutputStream("key-pub.dat");
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(privateKey);
        }
    }

    public void loadKey(){
        try(FileInputStream fis = new FileInputStream("key-pri.dat");
            ObjectInputStream ois = new ObjectInputStream(fis)){
            privateKey = (PrivateKey) ois.readObject();

        } catch (Exception e) {
            e.printStackTrace();
        }

        try(FileInputStream fis = new FileInputStream("key-pub.dat");
            ObjectInputStream ois = new ObjectInputStream(fis)){
            privateKey = (PrivateKey) ois.readObject();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
