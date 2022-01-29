package helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Date;
import java.util.Map;

public class Helper {
    public String generateIdUser(){
        Date date = new Date();
        int floor = (int) Math.floor(Math.random() * (9999 - 1000 + 1) + 1000);
        return "USR"+ date.getTime() + floor;
    }

    public String mapToJson(Map<String, String> map) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(map);
    }

    public Map<String, String> jsonStringToMap(String json) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, Map.class);
    }
}
