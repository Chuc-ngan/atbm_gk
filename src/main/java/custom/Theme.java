package custom;

import com.formdev.flatlaf.FlatIntelliJLaf;
import javax.swing.*;
import java.awt.*;

public class Theme {

    public void setup() {
        // Cài đặt giao diện FlatLaf với IntelliJ theme
        FlatIntelliJLaf.setup();

        // Cài đặt màu sắc và các thuộc tính cho UI
        UIManager.put("Button.arc", 15); // Tăng độ cong của nút
        UIManager.put("Button.margin", new Insets(12, 24, 12, 24)); // Tăng khoảng cách giữa văn bản và cạnh của nút

        UIManager.put("Component.arc", 15); // Tăng độ cong cho tất cả các component
        UIManager.put("ProgressBar.arc", 15); // Tăng độ cong của thanh tiến trình

        UIManager.put("TextComponent.arc", 15); // Tăng độ cong cho các ô nhập liệu
        UIManager.put("TextField.margin", new Insets(10, 10, 10, 10)); // Tăng khoảng cách giữa văn bản và viền ô nhập liệu
        UIManager.put("ComboBox.padding", new Insets(10, 10, 10, 10)); // Tăng padding cho ComboBox

        UIManager.put("TabbedPane.selectedBackground", new Color(255, 255, 255)); // Màu nền của Tab khi được chọn

        // Màu sắc của Button, Label, Panel và các thành phần khác
        UIManager.put("Button.background", new Color(191, 150, 201)); // Màu nền nút, sử dụng màu xanh đậm
        UIManager.put("Button.foreground", Color.WHITE); // Màu chữ trắng trên nút
        UIManager.put("Panel.background", new Color(245, 245, 245)); // Màu nền panel sáng, dễ nhìn
        UIManager.put("Label.foreground", new Color(51, 51, 51)); // Màu chữ label tối, dễ đọc
        UIManager.put("Button.font", new Font("Arial", Font.BOLD, 14)); // Font cho button
        UIManager.put("Label.font", new Font("Arial", Font.PLAIN, 12)); // Font cho label
        UIManager.put("TextField.font", new Font("Arial", Font.PLAIN, 12)); // Font cho text field

        // Cài đặt màu cho các thanh cuộn (scrollbar)
        UIManager.put("ScrollBar.thumb", new Color(180, 180, 180)); // Màu của thanh cuộn
        UIManager.put("ScrollBar.track", new Color(230, 230, 230)); // Màu nền của thanh cuộn
        UIManager.put("ScrollBar.width", 12); // Độ rộng thanh cuộn

        // Màu nền khi button được chọn
        UIManager.put("Button.select", new Color(100, 149, 237)); // Màu khi button được chọn
        UIManager.put("ComboBox.selectionBackground", new Color(173, 216, 230)); // Màu nền của combo box khi chọn
        UIManager.put("List.selectionBackground", new Color(135, 206, 250)); // Màu nền của list khi chọn

        // Các hiệu ứng hover và focus
        UIManager.put("Button.hoverBackground", new Color(70, 130, 180)); // Màu nền của button khi hover
        UIManager.put("TextField.focusBackground", new Color(255, 255, 255)); // Màu nền của text field khi focus
        UIManager.put("ComboBox.selectionBorder", new Color(70, 130, 180)); // Viền của ComboBox khi focus

        // Tạo hiệu ứng shadow cho button
        UIManager.put("Button.shadow", new Color(150, 150, 150)); // Bóng đổ cho button

        // Cài đặt theme tối (Dark Mode) nếu cần
        // FlatDarkLaf.setup(); // Bạn có thể kích hoạt theme tối bằng cách gọi dòng này nếu muốn
    }
}
