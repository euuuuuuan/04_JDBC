package com.exampleemp.common;

import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class JDBCSetUp {
    public static Connection getConnection() {
        Connection con = null;

        Properties empProp = new Properties();

        try {
            empProp.load(new FileReader("src/main/java/com/exampleemp/config/connection-info.properties"));

            System.out.println("empProp = " + empProp);

            String driver = empProp.getProperty("driver");
            String url = empProp.getProperty("url");
            String user = empProp.getProperty("user");
            String password = empProp.getProperty("password");

            Class.forName(driver);

            con = DriverManager.getConnection(url, empProp);

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return con;

    }
    public static void close(Connection con) {
        try {
            if (con != null && !con.isClosed()) {
                con.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void close(Statement stmt) {
        try {
            if (stmt != null && !stmt.isClosed()) {
                stmt.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void close(ResultSet rset) {
        try {
            if (rset != null && !rset.isClosed()) {
                rset.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
