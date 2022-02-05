package helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

public class Helper {
    public String generateIdUser() {
        Date date = new Date();
        int floor = randomInt(1000, 9999);
        return "USR" + date.getTime() + floor;
    }

    public String mapToJson(Map<String, Object> map) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(map);
    }

    public Map<String, Object> jsonStringToMap(String json) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, Map.class);
    }

    public int randomInt(int min, int max) {
        return (int) Math.floor(Math.random() * (max - min + 1) + min);
    }

    public String getCapcha(String old) {
        System.out.println("old: " + old);
        String[] hewan = {"gajah", "kelinci", "komodo", "panda"};
        String hewanName = "";
        if (old != null && !old.equals("")) {
            hewanName = hewan[randomInt(0, 3)];
            while (hewanName.equals(old)) {
                hewanName = hewan[randomInt(0, 3)];
            }
        } else {
            hewanName = hewan[randomInt(0, 3)];
        }
        return hewanName;
    }

    public String formatDateTimeFull(Timestamp t){
        Locale locale = new Locale("id", "ID");
        SimpleDateFormat simpleDateFormat =new SimpleDateFormat("dd MMMM yyyy HH:mm:ss", locale );
        return  simpleDateFormat.format(t);
    }
}
