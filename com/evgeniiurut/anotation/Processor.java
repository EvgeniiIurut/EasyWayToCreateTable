package com.evgeniiurut.anotation;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class Processor {
    private static Connection connection;
    private static Statement statement;


    public static void main(String[] args) {
        try {
            connect();
            buildTable(Dog.class);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            disconnect();
        }
    }

    public static void connect() throws SQLException {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:C:/Users/EvgeniiIurut/Desktop/MyBase.db");
            statement = connection.createStatement();

        } catch (ClassNotFoundException | SQLException e) {
            throw new SQLException("Unable to connect");
        }
    }

    public static void buildTable(Class cl) throws SQLException {
        if (!cl.isAnnotationPresent(Table.class)) {
            throw new RuntimeException("@Table missed");
        }
        Map<Class, String> map = new HashMap<>();
        map.put(int.class, "INTEGER");
        map.put(String.class, "TEXT");

        //CREATE TABLE dogs (id INTEGER, name TEXT, age INTEGER);
        StringBuilder stringBuilder = new StringBuilder("CREATE TABLE ");
        //"CREATE TABLE "
        stringBuilder.append(((Table) cl.getAnnotation(Table.class)).title());
        stringBuilder.append(" (");
        //CREATE TABLE dogs
        Field[] fields = cl.getDeclaredFields();
        for (Field o : fields) {
            if (o.isAnnotationPresent(Column.class)) {
                stringBuilder.append((o.getName()))
                        .append(" ")
                        .append(map.get(o.getType()))
                        .append(", ");
            }
        }
        //CREATE TABLE dogs (id INTEGER, name TEXT, age INTEGER,
        stringBuilder.setLength(stringBuilder.length() - 2);
        stringBuilder.append(");");
        statement.executeUpdate(stringBuilder.toString());

    }


    public static void disconnect() {
        try {
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
