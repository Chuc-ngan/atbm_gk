package view.tab.resultView;

import view.component.DragHereIcon;
import view.tab.AsymmetricPanel;
import view.tab.ClassicPanel;
import view.tab.SymmetricPanel;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.io.File;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class FileEncryptionPanel extends JPanel implements IResultView {

    private JLabel inputFileLabel;
    protected JTextField inputFilePath;
    private JButton browseInputButton;

    private JLabel outputDirLabel;
    protected JTextField outputDirPath;
    private JButton browseOutputButton;


    public FileEncryptionPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 20));  // Adding padding around the panel

        // Initialize UI components
        inputFileLabel = new JLabel("Select file to encrypt:");
        inputFilePath = new JTextField(70);
        inputFilePath.setEditable(true);  // Read-only, user cannot type
        browseInputButton = new JButton("Browse");

        outputDirLabel = new JLabel("Select directory to save encrypted file:");
        outputDirPath = new JTextField(70);
        outputDirPath.setEditable(true);  // Read-only, user cannot type
        browseOutputButton = new JButton("Browse");

        // Create drag-and-drop area for file
        JPanel dragDropPanel = createDragDropPanel();

        // Configure "Browse" buttons to allow users to select input/output files
        browseInputButton.addActionListener(e -> browseFile(true));
        browseOutputButton.addActionListener(e -> browseFile(false));

        // Create a container panel that uses BoxLayout to make both panels fill equally
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.X_AXIS)); // Horizontal layout for input
        inputPanel.add(inputFileLabel);
        inputPanel.add(Box.createHorizontalStrut(110));  // Add space between label and text field
        inputPanel.add(inputFilePath);
        inputPanel.add(Box.createHorizontalStrut(20));  // Add space between text field and browse button
        inputPanel.add(browseInputButton);

        JPanel outputPanel = new JPanel();
        outputPanel.setLayout(new BoxLayout(outputPanel, BoxLayout.X_AXIS)); // Horizontal layout for output
        outputPanel.add(outputDirLabel);
        outputPanel.add(Box.createHorizontalStrut(20));  // Add space between label and text field
        outputPanel.add(outputDirPath);
        outputPanel.add(Box.createHorizontalStrut(20));  // Add space between text field and browse button
        outputPanel.add(browseOutputButton);

        // Create a container panel that uses GridLayout for equal distribution of space
        JPanel containerPanel = new JPanel();
        containerPanel.setLayout(new BoxLayout(containerPanel, BoxLayout.Y_AXIS));
        containerPanel.add(inputPanel);
        containerPanel.add(Box.createVerticalStrut(10)); // Add space between panels
        containerPanel.add(outputPanel);

        // Add components to the panel
        add(dragDropPanel); // Add drag-and-drop panel
        add(Box.createVerticalStrut(10));
        add(containerPanel); // Add the container with equal size panels
    }

    private JPanel createDragDropPanel() {
        JPanel dragDropPanel = new JPanel(new BorderLayout());

        JLabel dragLabel = new JLabel(new DragHereIcon());
        dragLabel.setText("Drag Stuff Here");
        dragLabel.setVerticalTextPosition(SwingConstants.BOTTOM);
        dragLabel.setHorizontalTextPosition(SwingConstants.CENTER);
        dragLabel.setForeground(Color.GRAY);
        dragLabel.setFont(new Font("Monospace", Font.PLAIN, 24));

        dragDropPanel.add(dragLabel, BorderLayout.CENTER);
        dragDropPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY, 2),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        // Handle drag-and-drop events
        dragLabel.setTransferHandler(new TransferHandler() {
            @Override
            public boolean canImport(TransferSupport support) {
                return support.isDataFlavorSupported(DataFlavor.javaFileListFlavor);
            }

            @Override
            public boolean importData(TransferSupport support) {
                try {
                    java.util.List<File> files = (java.util.List<File>) support.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
                    if (!files.isEmpty()) {
                        inputFilePath.setText(files.get(0).getAbsolutePath());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return true;
            }
        });

        return dragDropPanel;
    }

    private void browseFile(boolean isInput) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(isInput ? JFileChooser.FILES_ONLY : JFileChooser.DIRECTORIES_ONLY);
        int returnValue = fileChooser.showOpenDialog(this);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            if (isInput) {
                inputFilePath.setText(selectedFile.getAbsolutePath());
            } else {
                outputDirPath.setText(selectedFile.getAbsolutePath());
            }
        }


    }
    // Getter methods to access the file paths
    public String getInputFilePath() {
        return inputFilePath.getText().trim();
    }

    public String getOutputDirPath() {
        return outputDirPath.getText().trim();
    }

    @Override
    public void encrypt(JPanel aloPanel) {
        String inputFile = inputFilePath.getText();
        String outputFile = outputDirPath.getText();

        try {
        if (aloPanel instanceof SymmetricPanel){
            SymmetricPanel.encryptFile(inputFile, outputFile);
        }
        else if (aloPanel instanceof AsymmetricPanel)
            AsymmetricPanel.encryptFile(inputFile, outputFile);

        } catch (InvalidAlgorithmParameterException | IllegalBlockSizeException | NoSuchPaddingException |
                 BadPaddingException | NoSuchAlgorithmException | InvalidKeyException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void decrypt(JPanel aloPanel) {
        String inputFile = inputFilePath.getText();
        String outputFile = outputDirPath.getText();
//        if (aloPanel instanceof SymmetricPanel)
//            SymmetricPanel.decryptFile(inputFile, outputFile);
//        else if (aloPanel instanceof AsymmetricPanel)
//            AsymmetricPanel.decryptFile(inputFile, outputFile);
    }
}
