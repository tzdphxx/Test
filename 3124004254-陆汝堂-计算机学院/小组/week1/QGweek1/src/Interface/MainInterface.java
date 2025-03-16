package Interface;

import java.sql.SQLException;
import java.util.Scanner;

public class MainInterface {

    public MainInterface() throws SQLException {
        Scanner sc = new Scanner(System.in);
        String choice;
    //页面菜单
        System.out.println("===================");
        System.out.println("  学生选课管理系统");
        System.out.println("===================");
        System.out.println("1. 登录");
        System.out.println("2. 注册");
        System.out.println("3. 退出");
        System.out.println("请选择操作（输入 1-3）：");
    //对输入的选项进行验证
        while (true){

            choice = sc.nextLine();
            if (choice.length() == 1 && choice.charAt(0) >= '1' && choice.charAt(0) <= '3') {
                break;
            }
            else {
                System.out.println("输入不合法，请重新输入！");
            }
        }
        switch (choice) {
            case "1":
                new LoginFace();
                break;
            case "2":
                new RegisterFace();
                break;
            case "3":
                System.exit(0);
                break;
        }
    }
}
