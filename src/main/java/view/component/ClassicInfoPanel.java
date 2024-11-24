package view.component;

import view.component.classical.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class ClassicInfoPanel extends JPanel {
    public HillCipherPanel hillCipherPanel;
    public SubstitutionCipherPanel substitutionCipherPanel;
    public AffineCipherPanel affineCipherPanel;
    public TranspositionCipherPanel transpositionCipherPanel;
    public VigenereCipherPanel vigenereCipherPanel;
    private JPanel selectionPanel;

    public ClassicInfoPanel() {
        setLayout(new BorderLayout(10, 10));
        setPreferredSize(new Dimension(400, 0));

        // Panel để chứa các lựa chọn (HillCipherPanel, v.v.)
        selectionPanel = new JPanel();
        selectionPanel.setLayout(new BoxLayout(selectionPanel, BoxLayout.Y_AXIS));

        add(selectionPanel, BorderLayout.CENTER);

        // Gọi hàm để cập nhật giao diện ban đầu
        updatePanelForAlgorithm("Hill");

    }

    public void updatePanelForAlgorithm(String selectedAlgorithm) {
        selectionPanel.removeAll();
        switch (selectedAlgorithm) {
            case "Hill":
                // Nếu thuật toán là Hill, tạo và thêm HillCipherPanel
                if (hillCipherPanel == null) {
                    hillCipherPanel = new HillCipherPanel();
                }
                hillCipherPanel.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),
                                "Enter Hill Cipher Key:", TitledBorder.LEFT, TitledBorder.TOP),
                        BorderFactory.createEmptyBorder(10, 10, 0, 10)));
                selectionPanel.add(hillCipherPanel); // Thêm HillCipherPanel vào selectionPanel
                break;
            case "Substitution":
                // Nếu thuật toán là Substitution, tạo và thêm SubstitutionCipherPanel
                substitutionCipherPanel = new SubstitutionCipherPanel();
                substitutionCipherPanel.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),
                                "Enter Substitution Cipher Key:", TitledBorder.LEFT, TitledBorder.TOP),
                        BorderFactory.createEmptyBorder(10, 10, 0, 10)));
                selectionPanel.add(substitutionCipherPanel);
                break;
            case "Vigenère":
                vigenereCipherPanel = new VigenereCipherPanel();
                vigenereCipherPanel.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),
                                "Enter Vigenère Cipher Key:", TitledBorder.LEFT, TitledBorder.TOP),
                        BorderFactory.createEmptyBorder(10, 10, 0, 10)));
                selectionPanel.add(vigenereCipherPanel);
                break;
            case "Affine":
                affineCipherPanel = new AffineCipherPanel();
                affineCipherPanel.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),
                                "Enter Affine Cipher Key:", TitledBorder.LEFT, TitledBorder.TOP),
                        BorderFactory.createEmptyBorder(10, 10, 0, 10)));
                selectionPanel.add(affineCipherPanel);
                break;
            case "Transposition":
                transpositionCipherPanel = new TranspositionCipherPanel();
                transpositionCipherPanel.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),
                                "Enter Transposition Cipher Key:", TitledBorder.LEFT, TitledBorder.TOP),
                        BorderFactory.createEmptyBorder(10, 10, 0, 10)));
                selectionPanel.add(transpositionCipherPanel);
                break;
            default:
                break;
        }

        // Cập nhật lại giao diện sau khi thêm các thành phần mới
        revalidate();
        repaint();
    }

}
