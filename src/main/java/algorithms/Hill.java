package algorithms;

import javax.swing.*;
import java.io.*;
import java.util.Random;

public class Hill {
    private static int[][] keyMatrix;
    private static String alphabet;
    public static String selectedSize;

    public Hill() {
    }

    public void genKey() {
        int size = Integer.parseInt(selectedSize.substring(0, 1));
        keyMatrix = new int[size][size];
        Random random = new Random();
        int attempts = 0;

        do {
            // Tạo ma trận khóa với các giá trị ngẫu nhiên
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    keyMatrix[i][j] = random.nextInt(alphabet.length());
                }
            }
            attempts++;
            if (attempts > 1000) {
                throw new IllegalStateException("Không thể tạo ma trận khóa khả nghịch.");
            }
        } while (!isInverseMatrix(keyMatrix, alphabet.length()));
    }

    public void loadKey(File selectedFile) throws IOException, ClassNotFoundException {
        if (!selectedFile.exists() || !selectedFile.getName().toLowerCase().endsWith(".key")) {
            throw new IOException("Invalid key file. Please select a valid .key file.");
        }

        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(selectedFile))) {
            // Đọc kích thước ma trận từ file
            int loadedSize = inputStream.readInt();
            // Đọc ma trận khóa từ file
            int[][] loadedKeyMatrix = (int[][]) inputStream.readObject();
            // Đọc bảng chữ cái từ file
            String loadedAlphabet = (String) inputStream.readObject();

            // Kiểm tra bảng chữ cái hiện tại
            if (alphabet != null && !alphabet.equals(loadedAlphabet)) {
                JOptionPane.showMessageDialog(null,
                        "Alphabet in the file does not match the current alphabet.\nFile alphabet: " + loadedAlphabet,
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                throw new IOException("Alphabet mismatch. Please reset the current alphabet to match the file.");
            }

            // Kiểm tra kích thước ma trận hiện tại
            if (keyMatrix != null && keyMatrix.length != loadedSize) {
                JOptionPane.showMessageDialog(null,
                        "Matrix size in the file does not match the current key size.\nFile matrix size: " + loadedSize + "x" + loadedSize,
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                throw new IOException("Matrix size mismatch. Please reset the key size to match the file.");
            }

            // Gán dữ liệu mới khi mọi thứ hợp lệ
            keyMatrix = loadedKeyMatrix;
            alphabet = loadedAlphabet;

            JOptionPane.showMessageDialog(null, "Key loaded successfully from " + selectedFile.getAbsolutePath(), "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException | ClassNotFoundException e) {
            throw new IOException("Error while loading key. Please try again.", e);
        }
    }


    public void saveKey(File selectedFile) throws IOException {
        boolean isFile = selectedFile.getAbsolutePath().contains(".");
        String path = selectedFile.getAbsolutePath();

        if (isFile) {
            File parent = selectedFile.getParentFile();
            path = parent.getAbsolutePath();
            if (!parent.exists()) {
                parent.mkdirs(); // Tạo thư mục cha nếu chưa tồn tại
            }
        } else {
            if (!selectedFile.exists()) {
                selectedFile.mkdirs(); // Tạo thư mục nếu là đường dẫn thư mục
            }
        }

        // Kiểm tra nếu ma trận khóa chưa được tạo
        if (keyMatrix == null || alphabet == null) {
            throw new IOException("Key matrix or alphabet is not initialized.");
        }

        try {
            File destination = new File(path + File.separator + "hill.key");
            if (!destination.exists()) {
                destination.createNewFile(); // Tạo file nếu chưa tồn tại
            }

            // Ghi dữ liệu vào file
            try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(destination))) {
                outputStream.writeInt(keyMatrix.length); // Ghi kích thước ma trận
                outputStream.writeObject(keyMatrix);     // Ghi ma trận khóa
                outputStream.writeObject(alphabet);      // Ghi bảng chữ cái
            }

            JOptionPane.showMessageDialog(null, "Key saved successfully to " + destination.getAbsolutePath(), "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            throw new IOException("Error while saving key. Please try again.", e);
        }
    }


    public static boolean isInverseMatrix(int[][] matrix, int modulus) {
        int determinant = (int) calculateDeterminant(matrix, modulus);
        return gcd(determinant, modulus) == 1; // Kiểm tra nếu gcd == 1
    }

    private static int gcd(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return Math.abs(a);
    }

    private static double calculateDeterminant(int[][] matrix, int modulus) {
        int n = matrix.length;
        if (n == 1) {
            return matrix[0][0] % modulus;
        } else if (n == 2) {
            return ((matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0]) % modulus + modulus) % modulus;
        }

        double determinant = 0;
        for (int i = 0; i < n; i++) {
            double minorDeterminant = calculateDeterminant(getMinor(matrix, 0, i), modulus);
            double cofactor = Math.pow(-1, i) * matrix[0][i] * minorDeterminant;
            determinant = (determinant + cofactor) % modulus;
        }

        // Ensure determinant is non-negative
        determinant = (determinant + modulus) % modulus;
        return determinant;
    }

    private static int[][] getMinor(int[][] matrix, int row, int col) {
        int n = matrix.length;
        int[][] minor = new int[n - 1][n - 1];
        for (int i = 0, minorRow = 0; i < n; i++) {
            if (i == row) continue;
            for (int j = 0, minorCol = 0; j < n; j++) {
                if (j == col) continue;
                minor[minorRow][minorCol++] = matrix[i][j];
            }
            minorRow++;
        }
        return minor;
    }

    // Encrypt the whole text
    public String encryptText(String plaintext) {
        StringBuilder stringBuilder = new StringBuilder();

        // Tính số ký tự cần thêm vào để đảm bảo chiều dài của chuỗi chia hết cho kích thước key
        int padding = 0;
        for (int i = 0; i < plaintext.length(); i++) {
            if (alphabet.contains(String.valueOf(plaintext.charAt(i)).toUpperCase())) {
                padding++;
            }
        }
        // Số ký tự đệm cần thêm
        padding = (keyMatrix.length - padding % keyMatrix.length) % keyMatrix.length;

        int i = 0;
        String[] splits = plaintext.split("");
        StringBuilder encrypted = new StringBuilder();

        for(String split : splits) {
            if(alphabet.contains(split.toUpperCase())) i++;
            encrypted.append(split);
            if(i == keyMatrix.length){
                stringBuilder.append(encryptArr(encrypted.toString(), keyMatrix));
                i = 0;
                encrypted.setLength(0);
            }

        }
        if (i > 0) {
            stringBuilder.append(encryptArr(encrypted.toString(), keyMatrix));
            // Thêm ký tự đệm
            for (int j = 0; j < padding; j++) {
                stringBuilder.append("=");

            }
        }

        return stringBuilder.toString();
    }

    private char[] encryptArr(String plaintext, int[][] keyMatrix) {
        StringBuilder sb = new StringBuilder();
        StringBuilder temp =  new StringBuilder();
        for(String s : plaintext.split("")){
            if(alphabet.contains(s.toUpperCase())){
                temp.append(s);
            }
        }

        if(temp.length() % keyMatrix.length !=0){
            int n = keyMatrix.length - temp.length() % keyMatrix.length;
            for (int j = 0; j < n; j++) {
                temp.append('\0');
            }
        }
        int j = 0;
        while (sb.length() < temp.length()) {
            j = Math.min(temp.length() - sb.length(), keyMatrix.length);
            String cut = temp.substring(sb.length(), sb.length() + j);
            int[] inputArr = transformInput(cut, keyMatrix.length);
            int[] multiplyMatrix =  multiplyVectorWithMatrix(inputArr, keyMatrix);
            int[] result =  new int[multiplyMatrix.length];
            for (int i = 0; i < result.length; i++) {
                result[i] = (multiplyMatrix[i] % alphabet.length() + alphabet.length()) % alphabet.length();
            }
            String s = reverseTransformInput(result, multiplyMatrix.length);
            for (int i = 0; i < plaintext.length(); i++) {
                if (!alphabet.contains(String.valueOf(plaintext.charAt(i)).toUpperCase())) {
                    sb.append(plaintext.charAt(i));
                } else {
                    sb.append(s.charAt(0));
                    s = s.substring(1);
                }
            }
            if (!s.isEmpty()) {
                sb.append(s);
            }
        }
        return sb.toString().toCharArray();
    }

    private int[] transformInput(String input, int length) {
        int[] result = new int[length];
        for (int i = 0; i < input.length(); i++) {
            result[i] = alphabet.indexOf(String.valueOf(input.charAt(i)).toUpperCase());
        }
        return result;
    }

    public static int[] multiplyVectorWithMatrix(int[] a, int[][] b) {
        int[] result = new int[a.length];
        int[][] transpose = transposeMatrix(b);
        for (int i = 0; i < a.length; i++) {
            result[i] = multiplyMatrix(a, transpose[i]);
        }
        return result;
    }

    private static int multiplyMatrix(int[] a, int[] b) {
        int result = 0;
        for (int i = 0; i < a.length; i++) {
            result += a[i] * b[i];
        }
        return result;
    }

    public static int[][] transposeMatrix(int[][] matrix) {
        int n = matrix.length;
        int[][] transpose = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                transpose[i][j] = matrix[j][i];
            }
        }
        return transpose;
    }

    private String reverseTransformInput(int[] input, int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(alphabet.charAt(input[i]));
        }
        return sb.toString();
    }


    // Decrypt the whole text
    public  String decryptText(String ciphertext) {
        int inputLength = ciphertext.length();
        int padding = 0;
        for (int i = ciphertext.length() - 1; i > ciphertext.length() - 4; i--) {
            if (ciphertext.charAt(i) == "=".charAt(0)) {
                padding++;
            } else {
                break;
            }
        }
        if (padding > 0) {
            ciphertext = ciphertext.substring(0, ciphertext.length() - padding);
        }

        StringBuilder sb = new StringBuilder();
        int i = 0;
        String[] lines = ciphertext.split("");
        StringBuilder decrypted = new StringBuilder();
        for (String line : lines) {
            if(alphabet.contains(line.toUpperCase())) i++;
            decrypted.append(line);
            if( i == keyMatrix.length){
                sb.append(decryptArr(decrypted.toString(), keyMatrix));
                i = 0;
                decrypted = new StringBuilder();
            }
        }

        if(i> 0){
            sb.append(decryptArr(decrypted.toString(),  keyMatrix));
        }

        return sb.substring(0, inputLength - padding - padding);
    }

    private String decryptArr(String cipherText, int[][] keyMatrix) {
        int[][] inverse = calculateInverseMatrix(keyMatrix, alphabet.length());
        StringBuilder sb = new StringBuilder();
        StringBuilder temp = new StringBuilder();

        for(String s : cipherText.split("")) {
            if(alphabet.contains(s.toUpperCase())){
                temp.append(s);
            }
        }
        int j = 0;
        while(sb.length() < temp.length()) {
            j = Math.min(temp.length() - sb.length(), keyMatrix.length);
            String cut = temp.substring(sb.length(), sb.length() + j);
            int[] inputArr = transformInput(cut, keyMatrix.length);
            int[] multiplyMatrix =  multiplyVectorWithMatrix(inputArr, inverse);
            int[] result =  new int[multiplyMatrix.length];
            for (int i = 0; i < cut.length(); i++) {
                result[i] = (multiplyMatrix[i] % alphabet.length() + alphabet.length()) % alphabet.length();
            }
            String s = reverseTransformInput(result, cut.length());

            for (int i = 0; i < cipherText.length(); i++) {
                if (!alphabet.contains(String.valueOf(cipherText.charAt(i)).toUpperCase())) {
                    sb.append(cipherText.charAt(i));
                } else {
                    sb.append(s.charAt(0));
                    s = s.substring(1);
                }
            }
        }
        return sb.toString();
    }

    public static int[][] calculateInverseMatrix(int[][] matrix, int mod) {
        int n = matrix.length;
        int[][] inverse = new int[n][n];
        double determinant = calculateDeterminant(matrix, mod);
        if (determinant == 0) {
            return null; // Ma trận không khả nghịch
        }
        double invDeterminant = findModularInverse((int) determinant, mod);
        int[][] adjoint = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                adjoint[i][j] = (int) (Math.pow(-1, i + j) * calculateDeterminant(getMinor(matrix, i, j), mod));
            }
        }

        int[][] adjointTranspose = transposeMatrix(adjoint);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                inverse[i][j] = (int) ((invDeterminant * adjointTranspose[i][j]) % mod + mod) % mod;
            }
        }
        return inverse;
    }

    public static int findModularInverse(int a, int m) {
        a = a % m;
        for (int x = 1; x < m; x++) {
            if ((a * x) % m == 1)
                return x;
        }
        return 1; // Không tìm thấy nghịch đảo
    }


    public int[][] getKeyMatrix() {
        return keyMatrix;
    }

    public void setKeyMatrix(int[][] keyMatrix) {
        this.keyMatrix = keyMatrix;
    }

    public String getSelectedSize() {
        return selectedSize;
    }

    public void setSelectedSize(String selectedSize) {
        this.selectedSize = selectedSize;
    }

    public String getAlphabet() {
        return alphabet;
    }

    public void setAlphabet(String alphabet) {
        this.alphabet = alphabet;
    }

    public static void main(String[] args) {
        Hill hillCipher = new Hill();

        // Cấu hình bảng chữ cái và kích thước
        hillCipher.setAlphabet("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
        hillCipher.setSelectedSize("3x3"); // Ma trận 3x3

        // Tạo khóa
        hillCipher.genKey();

        // Hiển thị ma trận khóa
        System.out.println("Ma trận khóa:");
        int[][] keyMatrix = hillCipher.getKeyMatrix();
        for (int[] row : keyMatrix) {
            for (int value : row) {
                System.out.print(value + " ");
            }
            System.out.println();
        }

        // Văn bản cần mã hóa
        String plaintext = "HELLO WORLD";
        System.out.println("Văn bản gốc: " + plaintext);

        // Mã hóa
        String encryptedText = hillCipher.encryptText(plaintext);
        System.out.println("Văn bản mã hóa: " + encryptedText);

        // Giải mã
        String decryptedText = hillCipher.decryptText(encryptedText);
        System.out.println("Văn bản giải mã: " + decryptedText);
    }


}
