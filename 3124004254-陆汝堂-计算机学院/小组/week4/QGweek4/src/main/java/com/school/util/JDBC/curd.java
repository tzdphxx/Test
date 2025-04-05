package com.school.util.JDBC;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class curd {


    public static <T> List<T> Query(Class<T> type, String sql, Object... objs) throws SQLException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchFieldException {
        List<T> result = new ArrayList<>();

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {

            conn = Jdbc.getConnection();
            ps = conn.prepareStatement(sql);

            for (int i = 0; i < (sql + " ").split("\\?").length - 1; i++) {
                ps.setObject(i + 1, objs[i]);
            }

            rs = ps.executeQuery();
            ResultSetMetaData metaData = rs.getMetaData();

            while (rs.next()) {
                T instance = type.getDeclaredConstructor().newInstance();

                for (int i = 1; i <= metaData.getColumnCount(); i++) {
                    String columnName = metaData.getColumnName(i);
                    Object value = rs.getObject(i);

                    String fieldName = toCamlCase(columnName);
                    Field field;
                    field = type.getDeclaredField(fieldName);

                    if (field != null) {
                        field.setAccessible(true);
                        field.set(instance, value);
                    }
                }
                result.add(instance);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (SecurityException e) {
            throw new RuntimeException(e);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } finally {
            Jdbc.Release(conn, ps, rs);
        }
        return result;
    }
    public static <T> List<T> Query(Class<T> type, String sql) throws SQLException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchFieldException {
        List<T> result = new ArrayList<>();

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {

            conn = Jdbc.getConnection();
            ps = conn.prepareStatement(sql);

            rs = ps.executeQuery();
            ResultSetMetaData metaData = rs.getMetaData();

            while (rs.next()) {
                T instance = type.getDeclaredConstructor().newInstance();

                for (int i = 1; i <= metaData.getColumnCount(); i++) {
                    String columnName = metaData.getColumnName(i);
                    Object value = rs.getObject(i);

                    String fieldName = toCamlCase(columnName);
                    Field field;
                    field = type.getDeclaredField(fieldName);

                    if (field != null) {
                        field.setAccessible(true);
                        field.set(instance, value);
                    }
                }
                result.add(instance);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (SecurityException e) {
            throw new RuntimeException(e);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } finally {
            Jdbc.Release(conn, ps, rs);
        }
        return result;
    }

    private static String toCamlCase(String columnName) {
        if (columnName == null || columnName.isEmpty()) {
            return columnName;
        }
        String[] parts = columnName.toLowerCase().split("_");
        StringBuilder sb = new StringBuilder(parts[0]);
        for (int i = 1; i < parts.length; i++) {
            if (parts[i].length() > 0) {
                sb.append(parts[i].substring(0, 1).toUpperCase())
                        .append(parts[i].substring(1));
            }
        }
        return sb.toString();
    }


    public static int UpdateData(String sql, Object... objs) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        conn = Jdbc.getConnection();
        ps = conn.prepareStatement(sql);

        for (int i = 0; i < (sql + " ").split("\\?").length - 1; i++) {
            ps.setObject(i + 1, objs[i]);
        }
        int count = ps.executeUpdate();


        Jdbc.Release(conn, ps, rs);

        return count;
    }
    public static int UpdateData(String sql) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        conn = Jdbc.getConnection();
        ps = conn.prepareStatement(sql);

        int count = ps.executeUpdate();


        Jdbc.Release(conn, ps, rs);

        return count;
    }
}
