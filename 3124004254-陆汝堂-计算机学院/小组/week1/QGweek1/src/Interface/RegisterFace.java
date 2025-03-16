package Interface;

import JdbcUtils.Jdbc;
import JdbcUtils.Utils;

import java.sql.SQLException;
import java.util.Scanner;

public class RegisterFace {

    Scanner sc = new Scanner(System.in);
    String name = null;
    String password = null;
    String RePassword = null;
    String choice = null;
    public RegisterFace() throws SQLException {
        System.out.println("===== 用户注册 =====");
        while (true){
            System.out.println("请输入用户名：(4~20位，字母开头)");
            name = sc.nextLine();
            if (!Utils.Check_Name(name)){
                System.out.println("用户名不合法，请重新输入！");
                continue;
            }
            System.out.println("请输入密码：");
            password = sc.nextLine();
            System.out.println("请确认密码：");
            RePassword = sc.nextLine();

            if (!password.equals(RePassword)){
                System.out.println("两次密码不一样！");
                continue;
            }

            System.out.println("请选择角色（输入 1 代表学生，2 代表管理员）：");
            choice = sc.nextLine();
            if (choice.length()!=1||(choice.charAt(0)!='1'&&choice.charAt(0)!='2')){
                System.out.println("角色选择不合法，请重新选择！");
                continue;
            }

            break;
        }

        if (Utils.Adduser(name,password,choice)){
            System.out.println("注册成功！请返回主界面登录。");
        } else {
            System.out.println("注册失败！");
        }
        new LoginFace();
    }

}
