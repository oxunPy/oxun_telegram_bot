package com.example.rest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class TestConnection {

    public static void main(String[] args) {

        System.out.println(connection1("prodaja-desktop", "myadmin", "myadmin"));
        System.out.println(connection2("prodaja-desktop", "myadmin", "myadmin"));
        System.out.println(connection3("prodaja-desktop", "myadmin", "myadmin"));
    }

    public static boolean connection1(String dbName, String user, String password) {
        Connection connection = null;

        try {
            String dbUrl = String.format("jdbc:postgresql:%s?user=%s&password=%s", dbName, user, password);
            connection = DriverManager.getConnection(dbUrl);
        } catch (SQLException e) {
//            e.printStackTrace();
            return false;
        } finally {
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException ex) {
//                ex.printStackTrace();
            }
        }
        return connection != null;
    }

    public static boolean connection2(String dbName, String user, String password) {
        Connection connection = null;

        try {
            String dbUrl = "jdbc:postgresql://localhost/" + dbName;
            connection = DriverManager.getConnection(dbUrl, user, password);
        } catch (SQLException e) {
//            e.printStackTrace();
            return false;
        } finally {
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException ex) {
//                ex.printStackTrace();
                return false;
            }
        }
        return connection != null;
    }

    public static boolean connection3(String dbName, String user, String password) {
        Connection connection = null;

        try {
            String dbUrl = "jdbc:postgresql://localhost:5432/" + dbName;
            Properties parameters = new Properties();
            parameters.put("user", user);
            parameters.put("password", password);
            connection = DriverManager.getConnection(dbUrl, parameters);
        } catch (SQLException e) {
//            e.printStackTrace();
            return false;
        } finally {
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException ex) {
//                ex.printStackTrace();
                return false;
            }
        }
        return connection != null;
    }
}