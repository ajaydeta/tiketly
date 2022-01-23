package database;

import java.sql.*;
import java.sql.Driver;
import java.util.ArrayList;

public class Database {
    private String table;

    public Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/tiketly","root","");
    }


//    public void getColumn() throws SQLException, ClassNotFoundException {
//        Connection db =  getConnection();
////        SELECT COLUMN_NAME
////        FROM INFORMATION_SCHEMA.COLUMNS
////        WHERE TABLE_SCHEMA = n'tiketly'
////        AND TABLE_NAME = N'user'
//        PreparedStatement stmt = db.prepareStatement(
//                "SELECT COLUMN_NAME FROM " +
//                "INFORMATION_SCHEMA.COLUMNS " +
//                "WHERE TABLE_SCHEMA = ? " +
//                "AND TABLE_NAME = ? ");
//        stmt.setString(1, "tiketly");
//        stmt.setString(2, "user");
//        ResultSet rs = stmt.executeQuery();
//
//        ArrayList<String> columnName = new ArrayList<String>();
////        String[] columnName = new String[rs.getRow()];
//        while (rs.next()){
//            columnName.add(rs.getString(1));
//        }
//        System.out.println(columnName);
//    }

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


}