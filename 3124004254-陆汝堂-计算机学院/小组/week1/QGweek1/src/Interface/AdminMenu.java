package Interface;

import JdbcUtils.Utils;

import java.sql.SQLException;
import java.util.Scanner;

public class AdminMenu {
    /*===== 管理员菜单 =====
1. 查询所有学生
2. 修改学生手机号
3. 查询所有课程
4. 修改课程学分
5. 查询某课程的学生名单
6. 查询某学生的选课情况
7. 退出
请选择操作（输入 1-7）：*/
    public AdminMenu() throws SQLException {
        Scanner sc = new Scanner(System.in);
        String choice;
        //管理员菜单
        while (true){
            System.out.println("===== 管理员菜单 ======");
            System.out.println("1. 查询所有学生");
            System.out.println("2. 修改学生手机号");
            System.out.println("3. 查询所有课程");
            System.out.println("4. 修改课程学分");
            System.out.println("5. 查询某课程的学生名单");
            System.out.println("6. 查询某学生的选课情况");
            System.out.println("7. 退出");
            System.out.println("请选择操作(输入 1-7):");
            //对输入的选项进行验证
            while (true) {


                choice = sc.nextLine();
                if (choice.length() == 1 && choice.charAt(0) >= '1' && choice.charAt(0) <= '7') {
                    break;
                } else {
                    System.out.println("输入不合法，请重新输入！");
                }
            }
            switch (choice) {
                case "1":
                    Utils.WatchAllStudent();
                    break;
                case "2":
                    int student_id = Utils.Choose_studentId();
                    Utils.Edit_Phone(student_id);
                    break;
                case "3":
                    Utils.WatchAllCourses();
                    break;
                case "4":
                    Utils.Edit_Credit();
                    break;
                case "5":
                    Utils.StudentOfCourse();
                    break;
                case "6":
                    Utils.Teacher_Watch_CoursesOfStudent();
                    break;
                case "7":
                    System.exit(0);
            }
        }
    }
}
