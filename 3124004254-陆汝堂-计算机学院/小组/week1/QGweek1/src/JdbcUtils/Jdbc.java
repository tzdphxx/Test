package JdbcUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class Jdbc {
    private static String url = null;
    private static String username = null ;
    private static String password = null ;
    static {

        try {
            //调用工具文件
            InputStream in = Jdbc.class.getClassLoader().getResourceAsStream("dp.properties");
            if (in == null) {
                throw new FileNotFoundException("未找到配置文件！");
            }
            Properties prop = new Properties();
            prop.load(in);

            //给相应的量赋值
            String driver = prop.getProperty("driver");
            url = prop.getProperty("url");
            username = prop.getProperty("username");
            password = prop.getProperty("password");

            //加载驱动
            Class.forName(driver);

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url,username,password);
    }


    public static void Release (Connection conn, Statement st , ResultSet rs) throws SQLException {
        if (conn != null) {
            conn.close();
        }
        if (st != null) {
            st.close();
        }
        if (rs != null) {
            rs.close();
        }
    }
}
