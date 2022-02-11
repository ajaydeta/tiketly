package database;

import com.fasterxml.jackson.databind.ser.std.MapSerializer;

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

    public boolean ping() throws SQLException, ClassNotFoundException {
        try {
            getConnection().prepareStatement("SELECT 1 FROM `user`").executeQuery();
            return true;
        } catch (SQLException | ClassNotFoundException e){
            return false;
        }
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
        String sql = getQueryInsert(tableName, data);
        System.out.println("Executing SQL: "+sql);
        PreparedStatement preparedStatement = db.prepareStatement(sql);
        return preparedStatement.executeUpdate();

    }

    public ResultSet getResultSet(boolean isOne) throws SQLException, ClassNotFoundException {
        Connection db = getConnection();
        if (isOne){
            limit(1);
        }
        return db.prepareStatement(getQuerySelect()).executeQuery();
    }

    private ArrayList<Map<String, Object>> setArrayMapResult(ResultSet rs) throws SQLException {
        ArrayList<Map<String, Object>> result = new ArrayList<>();
        ArrayList<String> columnName = new ArrayList<>();
        ResultSetMetaData rsmd = rs.getMetaData();
        int columnCount = rsmd.getColumnCount();

        for (int i = 1; i <= columnCount; i++ ) {
            String name = rsmd.getColumnName(i);
            columnName.add(name);
        }

        while (rs.next()){
            Map<String, Object> elements = new HashMap<>();
            for (String s : columnName) {
                elements.put(s, rs.getObject(s));
            }
            result.add(elements);
        }

        return result;
    }

    private Map<String, Object> setOneMapResult(ResultSet rs) throws SQLException {
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

    public Map<String, Object> getOneMapResult() throws SQLException, ClassNotFoundException {
        ResultSet rs = getResultSet(true);
       return setOneMapResult(rs);
    }

    public ArrayList<Map<String, Object>> getArrayMapResult() throws SQLException, ClassNotFoundException {
        ResultSet rs = getResultSet(false);
        return setArrayMapResult(rs);
    }

    public int update(String fieldName, Object value) throws SQLException, ClassNotFoundException {
        Connection db = getConnection();
        String queryStr = getQueryUpdate(fieldName, value);
        System.out.println("Executing SQL: "+queryStr);
        PreparedStatement preparedStatement = db.prepareStatement(queryStr);
        return preparedStatement.executeUpdate();
    }

    public int updates(Map<String, Object> data) throws SQLException, ClassNotFoundException {
        Connection db = getConnection();
        String sql = getQueryUpdates(data);
        PreparedStatement preparedStatement = db.prepareStatement(sql.toString());
        System.out.println("Executing SQL: "+sql);
        return preparedStatement.executeUpdate();
    }

    public Object execute(Connection conn, String sql) throws SQLException {
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        System.out.println("Executing SQL: "+sql);
        if (sql.startsWith("SELECT")){
            if (sql.contains("LIMIT 1")){
                return setOneMapResult(preparedStatement.executeQuery());
            } else {
                return setArrayMapResult(preparedStatement.executeQuery());
            }
        } else {
            return preparedStatement.executeUpdate();
        }
    }

}

