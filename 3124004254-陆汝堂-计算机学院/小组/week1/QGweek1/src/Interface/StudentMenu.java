package Interface;

import JdbcUtils.Utils;

import java.sql.SQLException;
import java.util.Scanner;

public class StudentMenu {
    public StudentMenu(int student_id) throws SQLException {
        Scanner sc = new Scanner(System.in);
        String choice;
    //学生菜单
        while (true){
            System.out.println("===== 学生菜单 =====");
            System.out.println("1. 查看可选课程");
            System.out.println("2. 选择课程");
            System.out.println("3. 退选课程");
            System.out.println("4. 查看已选课程");
            System.out.println("5. 修改手机号");
            System.out.println("6. 退出");
            System.out.println("请选择操作（输入 1-6）：");
            //对输入的选项进行验证
            while (true) {
                choice = sc.nextLine();
                if (choice.length() == 1 && choice.charAt(0) >= '1' && choice.charAt(0) <= '6') {
                    break;
                } else {
                    System.out.println("输入不合法，请重新输入！");
                }
            }
            switch (choice) {
                case "1":
                    Utils.WatchAllCourses();
                    break;
                case "2":
                    Utils.ChooseCourse(student_id);
                    break;
                case "3":
                    Utils.withdrawCourse(student_id);
                    break;
                case "4":
                    Utils.WatchChooseCourse(student_id);
                    break;
                case "5":
                    Utils.Edit_Phone(student_id);
                    break;
                case "6":
                    System.exit(0);
            }
        }
    }
}
