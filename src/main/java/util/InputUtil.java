package util;

import java.util.Arrays;

public class InputUtil {

    private String regexEmoji(String val) {
        return val.replaceAll("[^!@#$%^&*(),.?\\t\\n\\\":{}|<>_=\\/;\\'`~+-\\[\\]\\\\a-zA-Z0-9 ]", "");
    }

    public String inputNama(String newValue) {
        return newValue.replaceAll("[^A-Za-z\\s]", "");
    }

    public String inputNamaBioskop(String newValue) {
        return newValue.replaceAll("[^A-Za-z0-9\\s]", "");
    }

    public String inputPhone(String oldValue, String newValue) {
        String newVal = newValue.replaceAll("[^\\d]", "");
        if (!newVal.matches("^\\d{0,14}$")) {
            newVal = oldValue;
        }
        return newVal;
    }

    public String inputSearch(String newValue) {
        return newValue.replaceAll("[^\\d\\w\\s]", "");
    }

    public String inputJam(String oldValue, String newValue) {
        String newVal = newValue.replaceAll("[^0-9\\:]", "");
        if (!newVal.matches("^[0-9\\:]{0,5}$")) {
            newVal = oldValue;
        }
        return newVal;
    }

    public String inputHarga(String newValue){
        return newValue.replaceAll("[^0-9\\.]", "");
    }
}
