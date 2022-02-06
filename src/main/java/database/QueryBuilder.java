package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class QueryBuilder {
    protected String tableFrom, tableName, orderBy = "";
    protected int limit;
    protected ArrayList<String> where = new ArrayList<>();
    protected ArrayList<String> select = new ArrayList<>();
    protected ArrayList<String> join = new ArrayList<>();
    protected ArrayList<String> groupBy = new ArrayList<>();

    public void select(String... selectStr){
        this.select.clear();
        if (selectStr.length == 0){
            this.select.add("*");
        } else {
            this.select.addAll(Arrays.asList(selectStr));
        }
    }

    public void table(String tableName){
        this.tableName = tableName;
        this.tableFrom = "FROM `" + tableName + "` ";
    }

    public void join(String join, Object... val){
        int endIndex;
        for (Object o : val) {
            String valStr = "";
            if ("java.lang.String".equals(o.getClass().getName())) {
                StringBuilder str = new StringBuilder();
                str.appendCodePoint(34);
                str.append(o);
                str.appendCodePoint(34);
                valStr += str.toString();
            } else {
                valStr += o;
            }

            endIndex = join.indexOf("?");
            if (endIndex != join.length()) {
                endIndex += 1;
            }
            String whSub = join.substring(0, endIndex);
            join = whSub.replace("?", valStr) + join.substring(endIndex);
        }
        this.join.add(join);
    }

    public void where(String wh, Object... val){
        int endIndex;
        for (Object o : val) {
            String valStr = "";
            if ("java.lang.String".equals(o.getClass().getName())) {
                StringBuilder str = new StringBuilder();
                str.appendCodePoint(34);
                str.append(o);
                str.appendCodePoint(34);
                valStr += str.toString();
            } else {
                valStr += o;
            }

            endIndex = wh.indexOf("?");
            if (endIndex != wh.length()) {
                endIndex += 1;
            }
            String whSub = wh.substring(0, endIndex);
            wh = whSub.replace("?", valStr) + wh.substring(endIndex);
        }

        this.where.add("("+ wh + ")");
    }

    public void groupBy(String group){
        this.groupBy.add(group);
    }

    public void limit(int limit){
        this.limit = limit;
    }

    public void order(String order){
        this.orderBy = order;
    }

    public String getQuerySelect(){
        String queryStr = "SELECT ";

        if (this.select.size() > 0){
            queryStr += String.join(", ", this.select) + " ";
        } else {
            queryStr += " * ";
        }

        queryStr += this.tableFrom;

        if (this.join.size() > 0){
            queryStr += String.join(" ", this.join);
        }

        if (this.where.size() > 0) {
            queryStr += " WHERE " + String.join(" AND ", this.where);
        }

        if (this.groupBy.size() > 0){
            queryStr += " GROUP BY " + String.join(", ", this.groupBy);
        }

        if (!this.orderBy.equals("")){
            queryStr += " ORDER BY "+this.orderBy;
        }

        if (this.limit > 0){
            queryStr += " LIMIT " + this.limit;
        }

        System.out.println("Executing SQL: "+queryStr);
        return queryStr;
    }

    public String getQueryUpdate(String fieldName, Object value){
        String queryStr = "UPDATE ";
        queryStr += this.tableName;
        queryStr += " SET " + fieldName + " = ";
        if ("java.lang.String".equals(value.getClass().getName())) {
            StringBuilder str = new StringBuilder();
            str.appendCodePoint(34);
            str.append(value);
            str.appendCodePoint(34);
            queryStr += str.toString();
        } else {
            queryStr += value;
        }

        if (this.where.size() > 0) {
            queryStr += " WHERE " + String.join(" AND ", this.where);
        }

        return queryStr;
    }

    public String getQueryUpdates(Map<String, Object> data){
        StringBuilder sql = new StringBuilder("UPDATE " + tableName + " SET ");

        AtomicInteger i = new AtomicInteger(1);
        data.forEach((key, val) -> {
            String valStr = "";
            if ("java.lang.String".equals(val.getClass().getName())) {
                StringBuilder str = new StringBuilder();
                str.appendCodePoint(34);
                str.append(val);
                str.appendCodePoint(34);
                valStr += str.toString();
            } else {
                valStr += val;
            }
            sql.append(key).append(" = ").append(valStr).append(i.get() != data.size() ? ", " : "");
            i.getAndIncrement();
        });

        if (this.where.size() > 0) {
            sql.append(" WHERE ").append(String.join(" AND ", this.where));
        }
        return sql.toString();
    }

    public String getQueryInsert(String tableName, Map<String, Object> data){
        ArrayList<String> keyList = new ArrayList<>();
        data.forEach((key, val) -> {
            keyList.add(key);
        });
        String colNameJoined = String.join(", ", keyList);
        StringBuilder sql = new StringBuilder("INSERT INTO " + tableName + "(" + colNameJoined + ") VALUES (");
        for (int i = 0; i < keyList.size(); i++) {
            String valStr = "";
            Object value = data.get(keyList.get(i));
            if ("java.lang.String".equals(value.getClass().getName())) {
                StringBuilder str = new StringBuilder();
                str.appendCodePoint(34);
                str.append(value);
                str.appendCodePoint(34);
                valStr += str.toString();
            } else {
                valStr += value;
            }

            sql.append(valStr).append(i+1 != keyList.size() ? ", " : "");
        }
        sql.append(");");
        return sql.toString();
    }

    public String geQuerytBulkInsert(String tableName, ArrayList<Map<String, Object>> data){
        ArrayList<String> keyList = new ArrayList<>();
        data.get(0).forEach((key, val) -> {
            keyList.add(key);
        });
        String colNameJoined = String.join(", ", keyList);
        StringBuilder sql = new StringBuilder("INSERT INTO " + tableName + "(" + colNameJoined + ") VALUES ");
        for (int i = 0; i < data.size(); i++) {
            sql.append("(");
            for (int j = 0; j < keyList.size(); j++) {
                String valStr = "";
                Object value = data.get(i).get(keyList.get(j));
                if ("java.lang.String".equals(value.getClass().getName())) {
                    StringBuilder str = new StringBuilder();
                    str.appendCodePoint(34);
                    str.append(value);
                    str.appendCodePoint(34);
                    valStr += str.toString();
                } else {
                    valStr += value;
                }

                sql.append(valStr).append(j+1 != keyList.size() ? ", " : "");
            }
            sql.append(i+1 != data.size() ? "), " : ")");
        }
        return sql.toString();
    }
}
