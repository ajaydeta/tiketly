package util;

import java.util.HashMap;
import java.util.Map;

public final class DataTravel {
    private Map<String, Object> data = new HashMap<>();
    private final static DataTravel INSTANCE = new DataTravel();

    public static DataTravel getInstance() {
        return INSTANCE;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public void addData(String key, Object value) {
        this.data.put(key, value);
    }

    public void deleteData(String key) {
        this.data.remove(key);
    }

    public Object getData(String key) {
        return this.data.get(key);
    }

    public Map<String, Object> getData() {
        return data;
    }

    public boolean contains(String key) {
        return this.data.containsKey(key);
    }

    public void clear() {
        this.data.clear();
    }
}
