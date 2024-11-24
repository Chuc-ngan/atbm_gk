package view.tab;


import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class TabPanel extends JTabbedPane {
    private SymmetricPanel symmetricPanel;
    private AsymmetricPanel asymmetricPanel;

    private FixedBottomPanel fixedBottomPanel;


    public TabPanel(FixedBottomPanel fixedBottomPanel) {
        setPreferredSize(new Dimension(1200, 340));
        this.fixedBottomPanel = fixedBottomPanel;

        symmetricPanel = new SymmetricPanel();
        asymmetricPanel = new AsymmetricPanel();

        addTab("Symmetric", symmetricPanel);
        addTab("Classical Symmetric", new ClassicPanel());
        addTab("Asymmetric", asymmetricPanel);
        addTab("Hash", new HashPanel());
        addTab("Digital Signature", new DigitalSignaturePanel());
        addTab("Instructions For Use", new InstructionsForUse());
        addTab("Creator Information", new CreatorInformation());

        fixedBottomPanel.updateForSelectedTab((JPanel) getSelectedComponent());
        // Lắng nghe sự thay đổi tab
        addChangeListener(e -> updateFixedBottomPanel());
    }

    private void updateFixedBottomPanel() {
        // Lấy chỉ số của tab hiện tại
        int selectedIndex = getSelectedIndex();

        // Nếu tab nằm trong 4 tab cuối, ẩn fixedBottomPanel
        if (selectedIndex >= 3) {
            fixedBottomPanel.setVisible(false);
        } else {
            fixedBottomPanel.setVisible(true);
            // Cập nhật nội dung FixedBottomPanel cho tab được chọn
            fixedBottomPanel.updateForSelectedTab((JPanel) getSelectedComponent());
        }
    }

}
