package ConnectionPool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.util.LinkedList;


public class MyConnectionPool {
    //队列
    private final LinkedList<Connection> pool = new LinkedList<>();
    //数据库 的配置
    String url = null;
    String username = null;
    String password = null;
    String driver = null;
    //当前数量
    private  int currentCount = 0;
    //最大数量
    private final int maxCount;
    //等待的数量
    private int waitCount = 0;


    //构造方法
    public MyConnectionPool (String driver, String url, String username, String password, int maxCount) throws ClassNotFoundException {
        Class.forName(driver);
        this.url = url;
        this.username = username;
        this.driver = driver;
        this.password = password;
        this.maxCount = maxCount;
    }

    //获取连接
    public synchronized Connection getConnection() throws SQLException {


        //要是既没有空的，也超过最大数不能创建新的，就等待
        while (pool.isEmpty() && currentCount >= maxCount) {
            try {
                //等待数+1
                waitCount++;
                //等待最大时间
                wait(5000);
                //超了就不等了
                waitCount--;

                if (pool.isEmpty() && currentCount >= maxCount) {
                    throw new SQLException("等待超时！");
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new SQLException("线程中断！");
            }
        }


        //优先使用空闲的队列
        if (pool.size()>0) {
            return pool.removeFirst();
        }

        //创建新的连接
        if (currentCount < maxCount) {
            currentCount++;
            return DriverManager.getConnection(url,username,password);
        }

        //满了
        throw new SQLException("线程池满了！");
    }

    //处理用完的连接
    public synchronized void releaseConnection(Connection conn) throws SQLException {
        if (currentCount <= maxCount) {
            //还没满就放回
            pool.addLast(conn);
        } else if (waitCount > 0) {
            //有等待唤醒
            notifyAll();
        } else {
            //满了就释放
            try {
                conn.close();
                currentCount--;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

    }
    //关闭所有 的连接

    public synchronized void shutdown() throws SQLException {
        while (pool.size() >0) {
            try {
                pool.removeFirst().close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        pool.clear();
    }

}
