package JdbcUtils;

import ConnectionPool.MyConnectionPool;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class Jdbc {

    private static MyConnectionPool pool;

    private static String driver = null;
    private static String url = null;
    private static String username = null ;
    private static String password = null ;

    //连接池 的配置（最大数量）
    private static int maxCount =0;


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
            driver = prop.getProperty("driver");
            url = prop.getProperty("url");
            username = prop.getProperty("username");
            password = prop.getProperty("password");

            maxCount = Integer.parseInt(prop.getProperty("maxCount"));

            pool = new MyConnectionPool(driver, url, username, password, maxCount);


            /*//加载驱动
            Class.forName(driver);*/

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
       /* return DriverManager.getConnection(url,username,password);*/
        return pool.getConnection();
    }


    public static void Release (Connection conn, Statement st , ResultSet rs) throws SQLException {
        if (conn != null) {
            /*conn.close();*/
            pool.releaseConnection(conn);
        }
        if (st != null) {
            st.close();
        }
        if (rs != null) {
            rs.close();
        }
    }
    public static void ShutDownPool () throws SQLException {
        pool.shutdown();
    }
}
