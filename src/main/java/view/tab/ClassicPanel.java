package view.tab;

import algorithms.*;
import view.component.ClassicInfoPanel;
import view.component.ClassicalOptionsPanel;

import javax.swing.*;
import java.awt.*;

public class ClassicPanel extends JPanel {

    private static ClassicalOptionsPanel classicalOptionsPanel;
    private ClassicInfoPanel cipherInfoPanel;

    private static Hill hill;
    private static Affine affine;
    private static Transposition transposition;
    private static Substitution substitution;
    private static Vigenere vigenere;

    public ClassicPanel(){
        setLayout(new BorderLayout(10, 10));

        hill = new Hill();
        affine = new Affine();
        transposition = new Transposition();
        substitution = new Substitution();
        vigenere = new Vigenere();
        cipherInfoPanel = new ClassicInfoPanel();
        classicalOptionsPanel = new ClassicalOptionsPanel(cipherInfoPanel, hill, affine, substitution, transposition, vigenere);
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, classicalOptionsPanel, cipherInfoPanel);
        splitPane.setContinuousLayout(true); // Hiển thị liên tục khi thay đổi kích thước
        splitPane.setDividerLocation(0.5); // Vị trí phân chia ban đầu
        splitPane.setResizeWeight(0.5); // Cân bằng chiều rộng cho cả hai bên

        // Thêm splitPane vào KeyOptionsPanel
        add(splitPane, BorderLayout.CENTER);


    }

    public static String encrypt(String inputText) {
        String algorithm = (String) classicalOptionsPanel.algorithmComboBox.getSelectedItem();
        System.out.println(algorithm);
        switch (algorithm) {
            case "Hill":
                inputText = hill.encryptText(inputText);
                break;
            case "Vigenère":
                inputText = vigenere.encrypt(inputText);
                break;
            case "Affine":
                inputText = affine.encrypt(inputText);
                break;
            case "Transposition":
                inputText = transposition.encrypt(inputText);
                break;
            case "Substitution":
                inputText = substitution.encrypt(inputText);
                break;
            default:
                break;
        }


    return inputText;
    }

    public static String decrypt(String cipherText) {

        String algorithm = (String) classicalOptionsPanel.algorithmComboBox.getSelectedItem();
        System.out.println(algorithm);
        switch (algorithm) {
            case "Hill":
                cipherText = hill.decryptText(cipherText);
                break;
            case "Vigenère":
                cipherText = vigenere.decrypt(cipherText);
                break;
            case "Affine":
                cipherText = affine.decrypt(cipherText);
                break;
            case "Transposition":
                cipherText = transposition.decrypt(cipherText);
                break;
            case "Substitution":
                cipherText = substitution.decrypt(cipherText);
                break;
            default:
                break;
        }
        return cipherText;
    }

}
