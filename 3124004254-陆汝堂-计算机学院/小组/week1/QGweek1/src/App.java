import Interface.MainInterface;
import JdbcUtils.Jdbc;

import java.sql.SQLException;

public class App {
    public static void main(String[] args) throws SQLException {
        new MainInterface();
        Jdbc.ShutDownPool();
    }
}
