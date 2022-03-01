package helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

public class Helper {
    public String generateIdUser() {
        Date date = new Date();
        int floor = randomInt(1000, 9999);
        return "USR" + date.getTime() + floor;
    }

    public String generateIdTransaksi(){
        Date date = new Date();
        int floor = randomInt(1000, 9999);
        return "TRX" + date.getTime() + floor;
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

    public String formatDateTimeFull(LocalDateTime t){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return  t.format(formatter);
    }
    public String formatDateTimeFullNoSpace(LocalDateTime t){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyy-HHmmss");
        return  t.format(formatter);
    }

    public String formatDateSlash(Timestamp t){
        Locale locale = new Locale("id", "ID");
        SimpleDateFormat simpleDateFormat =new SimpleDateFormat("M/d/yyyy", locale );
        return  simpleDateFormat.format(t);
    }

    public String getNamaKursiTeater(int baris, int kolom){
        StringBuilder nama = new StringBuilder();
        nama.appendCodePoint(baris + 64);
        nama.append(kolom);
        return nama.toString();
    }

    public int getStatusKursiInt(String status){
        switch (status){
            case "Tersedia": return 1;
            case "Dipesan": return 2;
            case "Rusak": return 3;
            default: return 0;
        }
    }

    public String getStatusKursiString(int status){
        switch (status){
            case 1: return "Tersedia";
            case 2: return "Dipesan";
            case 3: return "Rusak";
            default: return "";
        }
    }

    public String getIdDalamKurung(String rawVal){
        if (rawVal != null){
            return rawVal.substring(rawVal.indexOf("(") + 1, rawVal.indexOf(")"));
        }
        return "";
    }

    public String setIdDalamKurung(Object val, Object id){
        return val.toString() + " ("+id+")";
    }
}
