package utils;

public class Alphabet {

    // Arrays for different character sets
    public static final String[] ENGLISH_CHAR_UPCASE = {
            "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M",
            "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"
    };

    public static final String[] ENGLISH_CHAR = {
            "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M",
            "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z",
            "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m",
            "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"
    };

    public static final String[] ENGLISH_CHAR_LOWER = {
            "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m",
            "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"
    };

    public static final String[] VIETNAMESE_CHAR_LOWER = {
            "a", "à", "ả", "ã", "á", "ạ", "â", "ầ", "ẩ", "ẫ", "ấ", "ậ", "ä", "b", "c", "d", "đ",
            "e", "è", "ẻ", "ẽ", "é", "ẹ", "ê", "ề", "ể", "ễ", "ế", "ệ", "f", "g", "h", "i", "ì",
            "ỉ", "ĩ", "í", "ị", "j", "k", "l", "m", "n", "o", "ò", "ỏ", "õ", "ó", "ọ", "ô", "ồ",
            "ổ", "ỗ", "ố", "ộ", "ơ", "ờ", "ở", "ỡ", "ớ", "ợ", "p", "q", "r", "s", "t", "u", "ù",
            "ủ", "ũ", "ú", "ụ", "ư", "ừ", "ử", "ữ", "ứ", "ự", "v", "w", "x", "y", "ỳ", "ỷ", "ỹ",
            "ý", "ỵ", "z"
    };

    public static final String[] NUMBER_CHAR = {
            "1", "2", "3", "4", "5", "6", "7", "8", "9", "0"
    };

    public static final String[] VIETNAMESE_CHAR_UPCASE = {
            "A", "À", "Á", "Ả", "Ã", "Ạ", "Ă", "Ằ", "Ắ", "Ẳ", "Ẵ", "Ặ", "Â", "Ầ", "Ấ", "Ẩ", "Ẫ",
            "Ậ", "B", "C", "D", "Đ", "E", "È", "É", "Ẻ", "Ẽ", "Ẹ", "Ê", "Ề", "Ế", "Ể", "Ễ", "Ệ",
            "G", "H", "I", "Ì", "Í", "Ỉ", "Ĩ", "Ị", "K", "L", "M", "N", "O", "Ò", "Ó", "Ỏ", "Õ",
            "Ọ", "Ô", "Ồ", "Ố", "Ổ", "Ỗ", "Ộ", "Ơ", "Ờ", "Ớ", "Ở", "Ỡ", "Ợ", "P", "Q", "R", "S",
            "T", "U", "Ù", "Ú", "Ủ", "Ũ", "Ụ", "Ư", "Ừ", "Ứ", "Ử", "Ữ", "Ự", "V", "X", "Y", "Ỳ",
            "Ý", "Ỷ", "Ỹ", "Ỵ"
    };

    // You can add methods to manipulate or retrieve these arrays if needed
    public static String[] getEnglishChars() {
        return ENGLISH_CHAR;
    }

    public static String[] getEnglishCharsCase() {
        return ENGLISH_CHAR_UPCASE;
    }

    public static String[] getEnglishCharsLower() {
        return ENGLISH_CHAR_LOWER;
    }

    public static String[] getVietnameseCharsCase() {
        return VIETNAMESE_CHAR_UPCASE;
    }

    public static String[] getVietnameseCharsLower() {
        return VIETNAMESE_CHAR_LOWER;
    }

    public static String[] getNumberChars() {
        return NUMBER_CHAR;
    }


}
