package Interface;

import JdbcUtils.Jdbc;
import JdbcUtils.Utils;

import java.sql.SQLException;
import java.util.Scanner;

public class LoginFace {
    Scanner sc = new Scanner(System.in);
    String username = null;
    String password = null;
    String role = null;
    String information = null;
    int user_id ;
    public LoginFace() throws SQLException {
        System.out.println("===== 用户登录 =====");
        while (true) {
            System.out.println("请输入用户名：");
            username = sc.nextLine();
            System.out.println("请输入密码：");
            password = sc.nextLine();

            information = Utils.Verify_Name_Password(username, password);
            if (information != null) {
                break;
            }

            System.out.println("用户名或密码错误！");
        }
        role = information.split("&")[0].split("=")[1];
        user_id = Integer.parseInt(information.split("&")[1].split("=")[1]);

        System.out.println("登录成功！你的角色是："+ role);


        if (role.equals("admin")) {
            new AdminMenu();
        }
        else if (role.equals("student")){
            int id = Utils.userId_To_studentId(user_id);
            new StudentMenu(id);
        }
    }
}
