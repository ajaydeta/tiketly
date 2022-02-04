package database;

import java.sql.*;
import java.util.*;

public class Database extends QueryBuilder {
    public Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/tiketly?parseTime=True&loc=Local&characterEncoding=utf8",
                "root",
                ""
        );
//        return DriverManager.getConnection("jdbc:mysql://localhost:3306/tiketly", )
    }

    public ArrayList<String> getColumn() throws SQLException, ClassNotFoundException {
        Connection db =  getConnection();
        PreparedStatement stmt = db.prepareStatement(
                "SELECT COLUMN_NAME FROM " +
                "INFORMATION_SCHEMA.COLUMNS " +
                "WHERE TABLE_SCHEMA = ? " +
                "AND TABLE_NAME = ? ");
        stmt.setString(1, "tiketly");
        stmt.setString(2, this.tableName);
//        System.out.println(stmt);
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

    public int insert(String tableName, Map<String, Object> data) throws SQLException, ClassNotFoundException {
        Connection db = getConnection();
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
        System.out.println("Executing SQL: "+sql);
        PreparedStatement preparedStatement = db.prepareStatement(sql.toString());
        return preparedStatement.executeUpdate();

    }

    public ResultSet getResultSet(boolean isOne) throws SQLException, ClassNotFoundException {
        Connection db = getConnection();
        if (isOne){
            limit(1);
        }
        return db.prepareStatement(getQueryString()).executeQuery();
    }

    public Map<String, Object> getOneMapResult() throws SQLException, ClassNotFoundException {
        ResultSet rs = getResultSet(true);
        ArrayList<String> columnName = new ArrayList<>();

        ResultSetMetaData rsmd = rs.getMetaData();
        int columnCount = rsmd.getColumnCount();

        for (int i = 1; i <= columnCount; i++ ) {
            String name = rsmd.getColumnName(i);
            columnName.add(name);
        }

        Map<String, Object> elements = new HashMap<>();
       while (rs.next()){
           for (String s : columnName) {
               elements.put(s, rs.getObject(s));
           }
        }

       return elements;
    }

    public ArrayList<Map<String, Object>> getArrayMapResult() throws SQLException, ClassNotFoundException {
        ResultSet rs = getResultSet(false);
        ArrayList<String> columnName = new ArrayList<>();
        ResultSetMetaData rsmd = rs.getMetaData();
        int columnCount = rsmd.getColumnCount();

        for (int i = 1; i <= columnCount; i++ ) {
            String name = rsmd.getColumnName(i);
            columnName.add(name);
        }

        ArrayList<Map<String, Object>> result = new ArrayList<>();
        while (rs.next()){
            Map<String, Object> elements = new HashMap<>();
            for (String s : columnName) {
                elements.put(s, rs.getObject(s));
            }
            result.add(elements);
        }

        return result;
    }

}