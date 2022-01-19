package helper;

import java.util.HashMap;
import java.util.Map;

public final class DataTravel {
    private Map<String, String> data = new HashMap<>();
    private final static DataTravel INSTANCE = new DataTravel();

    public static DataTravel getInstance() {
        return INSTANCE;
    }

    public void setData(Map<String, String> data) {
        this.data = data;
    }

    public Map<String, String> getData() {
        return data;
    }
}
