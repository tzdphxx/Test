package CurdUtils;

import JdbcUtils.Jdbc;

import java.sql.*;
import java.util.*;

public class curd {


    public static List<Map> QueryData (String sql , Object...objs) throws SQLException {
        List<Object> objectList = Arrays.asList(objs);
        List<Map> result = new ArrayList<>();

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = Jdbc.getConnection();
            ps = conn.prepareStatement(sql);

            for (int i = 0; i < (sql+" ").split("\\?").length-1; i++) {
                ps.setObject(i+1, objectList.get(i));
            }

            rs = ps.executeQuery();

            ResultSetMetaData metaData = rs.getMetaData();

            while (rs.next()) {
                Map map = new HashMap();
                for (int i = 1; i <= metaData.getColumnCount(); i++) {
                    map.put(metaData.getColumnName(i), rs.getObject(i));
                }
                result.add(map);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            Jdbc.Release(conn, ps, rs);
        }
        return result;
    }

    public static int UpdateData (String sql , Object...objs) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        conn = Jdbc.getConnection();
        ps = conn.prepareStatement(sql);

        for (int i = 0; i < (sql+" ").split("\\?").length-1; i++) {
            ps.setObject(i+1, objs[i]);
        }
        int count = ps.executeUpdate();


        Jdbc.Release(conn, ps, rs);

        return count;
    }
}
