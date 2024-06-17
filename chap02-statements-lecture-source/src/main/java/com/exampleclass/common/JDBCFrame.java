package com.exampleclass.common;

import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class JDBCFrame {

    public static Connection getConnection() {
        Connection con = null;

        Properties classProp = new Properties();

        try {
            classProp.load(new FileReader("src/main/java/com/exampleclass/config/connection-info.properties"));

            System.out.println("classProp = " + classProp);

            String driver = classProp.getProperty("driver");
            String url = classProp.getProperty("url");
            String user = classProp.getProperty("user");
            String password = classProp.getProperty("password");

            Class.forName(driver);

            con = DriverManager.getConnection(url, classProp);

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
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
