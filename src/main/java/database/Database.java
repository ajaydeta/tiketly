package database;

import java.sql.*;
import java.util.*;

public class Database {
    private String table;
    private ArrayList<String> where = new ArrayList<>();
    private ArrayList<String> select = new ArrayList<>();


    public Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/tiketly",
                "root",
                ""
        );
    }

    public ArrayList<String> getColumn() throws SQLException, ClassNotFoundException {
        Connection db =  getConnection();
        PreparedStatement stmt = db.prepareStatement(
                "SELECT COLUMN_NAME FROM " +
                "INFORMATION_SCHEMA.COLUMNS " +
                "WHERE TABLE_SCHEMA = ? " +
                "AND TABLE_NAME = ? ");
        stmt.setString(1, "tiketly");
        stmt.setString(2, "user");
        System.out.println(stmt);
        ResultSet rs = stmt.executeQuery();
        ArrayList<String> columnName = new ArrayList<>();
        while (rs.next()){
            columnName.add(rs.getString(1));
        }
        return columnName;
    }

    public PreparedStatement getInstert(String tableName, String[] colName) throws SQLException, ClassNotFoundException {
        Connection db = getConnection();
        String colNameJoined = String.join(", ", colName);
        StringBuilder sql = new StringBuilder("INSERT INTO " + tableName + "(" + colNameJoined + ") VALUES (");
        for (int i = 0; i < colName.length; i++) {
            if (colName.length - 1 == i) {
                sql.append("?");
            } else {
                sql.append("?, ");
            }
        }
        sql.append(");");
        return db.prepareStatement(sql.toString());
    }

    public void select(String... selectStr){
        if (selectStr.length == 0){
            this.select.add("*");
        } else {
            this.select.addAll(Arrays.asList(selectStr));
        }
    }

    public void table(String tableName){
        this.table = "FROM `" + tableName + "` ";
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

    public String getQueryString(){
        String queryStr = "SELECT " + String.join(", ", this.select) + " " + this.table;
        if (this.where.toArray().length > 0) {
            queryStr += "WHERE " + String.join(" AND ", this.where);
        }
        return queryStr;
    }

    public ResultSet getResultSet() throws SQLException, ClassNotFoundException {
        Connection db = getConnection();
        return db.prepareStatement(getQueryString()).executeQuery();
    }

    public Map<String, String> getMapResult() throws SQLException, ClassNotFoundException {
        ResultSet rs = getResultSet();
        ArrayList<String> columnName = new ArrayList<>();
        if (!Objects.equals(this.select.get(0), "*")){
            for (int i = 0; i < this.select.toArray().length; i++) {
                columnName.add(this.select.get(i));
            }
        } else {
            columnName = getColumn();
        }

        Map<String, String> elements = new HashMap<>();
       System.out.println("column name: " + columnName);
       while (rs.next()){
           System.out.println(rs);
           for (int i = 0; i < columnName.toArray().length; i++) {
            elements.put(columnName.get(i), rs.getString(columnName.get(i)));
           }
        }

       return elements;
    }

}