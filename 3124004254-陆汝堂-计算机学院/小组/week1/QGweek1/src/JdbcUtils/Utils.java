package JdbcUtils;

import CurdUtils.curd;
import Interface.StudentMenu;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;

import static CurdUtils.curd.QueryData;


public class Utils {
    private Utils() {
    }


    public static String Verify_Name_Password(String username, String password) throws SQLException {
        /*Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = Jdbc.getConnection();
            String sql = "select * from users ";
            ps = conn.prepareStatement(sql);

            rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("user_id");
                String RightName = rs.getString("username");
                String role = rs.getString("role");
                String RightPassword = rs.getString("password");
                if (username.equals(RightName) && password.equals(RightPassword)) {
                    return ("role=" + role.toString() + "&id=" + id);
                }
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            Jdbc.Release(conn, ps, rs);
        }*/


        String sql = "select user_id, role from users where username = ? and password = ?";
        List<Map> result = QueryData(sql, username, password);

        // 检查是否存在匹配的用户
        if (!result.isEmpty()) {
            Map<String, Object> row = result.get(0);
            return ("role=" + row.get("role") + "&id=" + row.get("user_id"));
        }
        return null;

    }

    public static boolean Check_Name(String name) throws SQLException {
        /*Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String regex = "[a-zA-Z][a-zA-Z0-9_\\-\\.]{3,19}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(name);

        if (!matcher.matches()) {
            return false;
        }

        try {
            conn = Jdbc.getConnection();
            String sql = "select username from users ";
            ps = conn.prepareStatement(sql);


            rs = ps.executeQuery();

            while (rs.next()) {
                String RightName = rs.getString("username");
                System.out.println(RightName);
                if (name.equals(RightName)) {
                    return false;
                }
            }
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            Jdbc.Release(conn, ps, rs);
        }*/


        //正则校验用户名格式
        String regex = "[a-zA-Z][a-zA-Z0-9_\\-\\.]{3,19}";
        if (!Pattern.matches(regex, name)) {
            return false;
        }


        String sql = "select username from users where username = ?";
        List<Map> result = curd.QueryData(sql, name);

        // 若空表示用户名可用
        return result.isEmpty();
    }


    //写入用户数据
    public static boolean Adduser(String username, String password, String role) throws SQLException {
        /*Connection conn = null;
        PreparedStatement ps = null;


        try {
            conn = Jdbc.getConnection();
            String sql = "insert into users (username,password,role) values(?,?,?)";
            ps = conn.prepareStatement(sql);


            ps.setString(1, username);
            ps.setString(2, password);
            if (role.equals("2")) {
                role = "admin";
            } else if (role.equals("1")) {
                role = "student";
            }
            ps.setString(3, role);


            int rs = ps.executeUpdate();


           if (role.equals("student")) {
            AddStudent();
        }

            if (rs > 0) {
                return true;
            }
            return false;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            Jdbc.Release(conn, ps, null);
        }*/


        // 1. 转换值（1变student，2变admin"）
        String result = null;

        switch (role) {
            case "1":
                result = "student";
                break;
            case "2":
                result = "admin";
                break;
        }
        ;

        // 2. 执行插入操作
        String sql = "insert into users (username, password, role) values (?, ?, ?)";
        int num = curd.UpdateData(sql, username, password, result);


        // 3. 再写入学生
        if (result.equals("student")) {
            AddStudent();
        }

        // 4. 判断是否插入成功
        return num > 0;
    }

    public static ArrayList<String> WatchAllCourses() throws SQLException {
        /*Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        ArrayList<String> CourseList = new ArrayList<>();

        try {
            conn = Jdbc.getConnection();
            String sql = "select * from courses ";
            ps = conn.prepareStatement(sql);


            rs = ps.executeQuery();


            while (rs.next()) {
                int id = rs.getInt("course_id");
                String courseCode = rs.getString("course_code");
                String courseName = rs.getString("course_name");
                String credit = rs.getString("credit");
                String teacher_id = rs.getString("teacher_id");
                String start = rs.getDate("start_date").toLocalDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

                String courseInformation = "课程id：" + id + " ，课程编号：" + courseCode + "， 课程名称：" + courseName + " ，学分：" + credit + " ，授课教师id：" + teacher_id + " ，开课时间：" + start;
                CourseList.add(courseInformation);
                System.out.println(courseInformation);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            Jdbc.Release(conn, ps, rs);
        }
        return CourseList;*/

        ArrayList<String> courseList = new ArrayList<>();


        String sql = "select * from courses";
        List<Map> result = curd.QueryData(sql);

        // 遍历结果集（保持原有格式化逻辑）
        for (Map<String, Object> row : result) {
            int id = (Integer) row.get("course_id");
            String code = (String) row.get("course_code");
            String name = (String) row.get("course_name");
            String credit = row.get("credit").toString();
            String teacherId = row.get("teacher_id").toString();


            Date startDate = (Date) row.get("start_date");
            String start = startDate.toLocalDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));


            String info = "课程id：" + id + " ，课程编号：" + code + "，课程名称：" + name + " ，学分：" + credit + " ，授课教师id：" + teacherId + " ，开课时间：" + start;
            courseList.add(info);
            System.out.println(info);
        }

        return courseList;

    }


    public static void ChooseCourse(int student_id) throws SQLException {

        Scanner sc = new Scanner(System.in);
        String choose = null;
        int choice;
        WatchAllCourses();
        pool:
        while (true) {
            System.out.println("请输入你的选择");
            choose = sc.nextLine();
            for (int i = 0; i < choose.length(); i++) {
                if (choose.charAt(i) < '0' && choose.charAt(i) > '9') {
                    System.out.println("输入不合法！，请重新输入！");
                    continue pool;
                }
            }
            choice = Integer.parseInt(choose);
            if (choice < 1 || choice > 30) {
                System.out.println("没有这节课！请重新选择！");
                continue;
            }
            if (ChooseAble(student_id, choice)) {
                break;
            }
        }
        AddCourse(student_id, choice);

    }

    //判断能不能选课
    private static boolean ChooseAble(int StudentID, int courseID) throws SQLException {
        /*Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;


        try {
            conn = Jdbc.getConnection();
            String sql1 = "select * from student_courses where student_id = ? ";
            ps = conn.prepareStatement(sql1);

            ps.setString(1, String.valueOf(StudentID));

            rs = ps.executeQuery();
            int count = 0;
            while (rs.next()) {
                int CourseID = rs.getInt("course_id");
                if (CourseID == courseID) {
                    System.out.println("相同的课不能重复选择！");
                    return false;
                }
                count++;
            }
            if (count >= 5) {
                System.out.println("选课已满，无法再选！");
                new StudentMenu(StudentID);
            }
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            Jdbc.Release(conn, ps, rs);
        }*/

        // 1. 检查是否重复选课
        String checkDupSql = "select course_id from student_courses where student_id = ? and course_id = ?";
        List<Map> Result = curd.QueryData(checkDupSql, StudentID, courseID);
        if (!Result.isEmpty()) {
            System.out.println("相同的课不能重复选择！");
            return false;
        }

        // 2. 检查选课数量是否超过5门
        String countSql = "select course_id from student_courses where student_id = ?";
        List<Map> allCourses = curd.QueryData(countSql, StudentID);

        if (allCourses.size() >= 5) {
            System.out.println("选课已满，无法再选！");
            new StudentMenu(StudentID); // 保持原界面跳转逻辑
            return false;
        }

        return true;

    }

    //加入选课结果
    private static void AddCourse(int student_id, int Course_id) throws SQLException {
        /*Connection conn = null;
        PreparedStatement ps = null;


        try {
            conn = Jdbc.getConnection();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String timestamp = LocalDateTime.now().format(formatter);

            String sql = "insert into student_courses (student_id,course_id,select_at) values(?,?,?)";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, student_id);
            ps.setInt(2, Course_id);
            ps.setTimestamp(3, Timestamp.valueOf(timestamp));

            int rs = ps.executeUpdate();
            if (rs > 0) {
                System.out.println("选课成功！");
            } else {
                System.out.println("选课失败！");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            Jdbc.Release(conn, ps, null);
        }*/


        LocalDateTime now = LocalDateTime.now();
        Timestamp timestamp = Timestamp.valueOf(now);


        String sql = "insert into student_courses (student_id, course_id, select_at) values (?, ?, ?)";
        int num = curd.UpdateData(sql, student_id, Course_id, timestamp);

        if (num > 0) {
            System.out.println("选课成功！");
        } else {
            System.out.println("选课失败！");
        }


    }

    public static int userId_To_studentId(int userId) throws SQLException {
        /*Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;


        try {
            conn = Jdbc.getConnection();
            String sql = "select * from student where user_id = ? ";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            rs = ps.executeQuery();
            rs.next();
            int student_id = rs.getInt("student_id");

            return student_id;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            Jdbc.Release(conn, ps, rs);
        }*/


        String sql = "select student_id from student where user_id = ?";
        List<Map> result = curd.QueryData(sql, userId);

        // 处理查询结果
        if (result.isEmpty()) {
            System.out.println("用户ID[" + userId + "]无对应学生记录");
        }

        return (Integer) result.get(0).get("student_id");
    }

    //判断课程是否已经开课
    private static boolean is_course_start(int course_id) throws SQLException {
        /*Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;


        try {
            conn = Jdbc.getConnection();
            String sql = "select * from courses where course_id = ? ";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, course_id);
            rs = ps.executeQuery();
            rs.next();
            long startTime = rs.getDate("start_date").getTime();
            long nowTime = System.currentTimeMillis();
            if (nowTime - startTime >= 0) {
                System.out.println("已经开课，不能退选！");
                return false;
            }
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            Jdbc.Release(conn, ps, rs);
        }*/


        String sql = "select start_date from courses where course_id = ?";
        List<Map> result = curd.QueryData(sql, course_id);

        // 处理空结果
        if (result.isEmpty()) {
            System.out.println("课程ID[" + course_id + "]不存在");
        }

        // 时间比较
        Date startDate = (Date) result.get(0).get("start_date");
        long startTime = startDate.getTime();
        long nowTime = System.currentTimeMillis();


        if (nowTime >= startTime) {
            System.out.println("已经开课，不能退选！");
            return false;
        }
        return true;

    }


    //删除选课数据
    public static void withdrawCourse(int student_id) throws SQLException {
        /*Connection conn = null;
        PreparedStatement ps = null;


        try {
            int course_id = Choose_Withdraw(student_id);

            conn = Jdbc.getConnection();
            String sql = "delete from student_courses where student_id = ? and course_id = ? ";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, student_id);
            ps.setInt(2, course_id);

            if (is_course_start(course_id)) {
                int rs = ps.executeUpdate();
                if (rs > 0) {
                    System.out.println("退课成功！");
                } else {
                    System.out.println("退课失败！");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            Jdbc.Release(conn, ps, null);
        }*/


        int course_id = Choose_Withdraw(student_id);

        // 当课程未开始时允许退课
        if (!is_course_start(course_id)) {
            //执行删除
            String sql = "delete from student_courses where student_id = ? and course_id = ?";
            int result = curd.UpdateData(sql, student_id, course_id);
            if (result > 0) {
                System.out.println("退课成功！");
            } else {
                System.out.println("退课失败");
            }

        }

    }

    //查看已选课程
    public static ArrayList<String> WatchChooseCourse(int student_id) throws SQLException {
        /*Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        ArrayList<String> CourseList = new ArrayList<>();

        try {
            conn = Jdbc.getConnection();
            String sql = "select * from student_courses where student_id = ? ";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, student_id);
            rs = ps.executeQuery();

            while (rs.next()) {
                int course_id = rs.getInt("course_id");
                String sql2 = "select * from courses where course_id = ? ";
                ps = conn.prepareStatement(sql2);
                ps.setInt(1, course_id);
                ResultSet innerRs = ps.executeQuery();  // 使用一个新的变量存储内层查询的结果
                while (innerRs.next()) {  // 添加循环处理内层查询的所有结果
                    String courseName = innerRs.getString("course_name");
                    CourseList.add(course_id + ":" + courseName);
                    System.out.println(course_id + ":" + courseName);
                }
                innerRs.close();  // 关闭内层查询的结果集
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Jdbc.Release(conn, ps, rs);
        }
        return CourseList;*/


        ArrayList<String> courseList = new ArrayList<>();

        // 获取学生选课记录
        String outerSql = "select course_id from student_courses where student_id = ?";
        List<Map> outerResult = curd.QueryData(outerSql, student_id);

        //逐条查询课程
        for (Map<String, Object> row : outerResult) {
            int courseId = (Integer) row.get("course_id");

            // 获取课程名称
            String innerSql = "select course_name from courses where course_id = ?";
            List<Map> innerResult = curd.QueryData(innerSql, courseId);


            if (!innerResult.isEmpty()) {
                String courseName = (String) innerResult.get(0).get("course_name");
                String info = courseId + ":" + courseName;
                courseList.add(info);
                System.out.println(info);
            }
        }

        return courseList;


    }

    //选择要删除的课
    private static int Choose_Withdraw(int student_id) throws SQLException {
        Scanner sc = new Scanner(System.in);

        ArrayList<String> CourseList = WatchChooseCourse(student_id);
        int[] id_arr = new int[CourseList.size()];

        for (int i = 0; i < CourseList.size(); i++) {
            id_arr[i] = Integer.parseInt(CourseList.get(i).split(":")[0]);
        }
        pool2:
        while (true) {
            System.out.println("选择要删除的课：");
            String choice = sc.nextLine();
            for (int i = 0; i < choice.length(); i++) {
                if (choice.charAt(i) < '0' && choice.charAt(i) > '9') {
                    System.out.println("输入不合法！请重新输入。");
                    continue pool2;
                }
            }

            for (int i = 0; i < id_arr.length; i++) {
                if (id_arr[i] == Integer.parseInt(choice)) {
                    return id_arr[i];
                }
            }

        }
    }

    //修改手机号
    public static void Edit_Phone(int student_id) throws SQLException {
        /*Connection conn = null;
        PreparedStatement ps = null;


        String phone = null;

        while (true) {
            Scanner sc = new Scanner(System.in);
            System.out.println("请输入新的手机号：");
            phone = sc.nextLine();
            if (phone.length() == 11 && phone.charAt(0) == '1') {
                break;
            }
            System.out.println("手机号格式不对！");
        }

        try {
            conn = Jdbc.getConnection();
            String sql = "update student set phone = ? where student_id =?";

            ps = conn.prepareStatement(sql);


            ps.setString(1, phone);
            ps.setInt(2, student_id);

            int rs = ps.executeUpdate();

            if (rs > 0) {
                System.out.println("修改成功！");
            } else {
                System.out.println("修改失败！");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            Jdbc.Release(conn, ps, null);
        }*/


        Scanner sc = new Scanner(System.in);
        String phone;

        // 输入校验循环
        while (true) {
            System.out.print("请输入新的手机号：");
            phone = sc.nextLine().trim();
            if (phone.matches("1\\d{10}")) {  // 正则验证 1开头 + 11位数字
                break;
            }
            System.out.println("手机号格式不对！");
        }

        // 执行更新
        String sql = "update student set phone = ? where student_id = ?";
        int num = curd.UpdateData(sql, phone, student_id);

        if (num > 0) {
            System.out.println("修改成功！");
        } else {
            System.out.println("修改失败！");
        }

    }


    public static ArrayList<String> WatchAllStudent() throws SQLException {


        /*ArrayList<String> StudentList = new ArrayList<>();

        Connection  conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = Jdbc.getConnection();
            String sql = "select * from student ";
            ps = conn.prepareStatement(sql);


            rs = ps.executeQuery();


            while (rs.next()) {
                int student_id = rs.getInt("student_id");
                int user_id = rs.getInt("user_id");
                String student_number = rs.getString("student_number");
                String name = rs.getString("name");
                String gender = rs.getString("gender");
                String phone = rs.getString("phone");
                int enrollment = rs.getInt("enrollment_year");
                String str = "学生id："+student_id+", 账号id："+user_id+" ,学号："+student_number+", 名字："+name+", 性别："+gender+", 手机："+phone+", 入学年份："+enrollment;
                System.out.println(str);
                StudentList.add(str);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            Jdbc.Release(conn, ps, rs);
        }



        return StudentList;*/

        ArrayList<String> studentList = new ArrayList<>();


        String sql = "select * from student";
        List<Map> result = curd.QueryData(sql);


        for (Map<String, Object> row : result) {
            int studentId = (Integer) row.get("student_id");
            int userId = (Integer) row.get("user_id");
            String number = (String) row.get("student_number");
            String name = (String) row.get("name");
            String gender = (String) row.get("gender");
            String phone = (String) row.get("phone");
            int enrollment = (Integer) row.get("enrollment_year");


            String info = String.format("学生id：%d, 账号id：%d, 学号：%s, 名字：%s, 性别：%s, 手机：%s, 入学年份：%d",
                    studentId, userId, number, name, gender, phone, enrollment);
            studentList.add(info);
            System.out.println(info);
        }

        return studentList;

    }

    //找到学生id
    public static int Choose_studentId() throws SQLException {
        Scanner sc = new Scanner(System.in);
        ArrayList<String> studentList = WatchAllStudent();
        String id = null;
        pool3:
        while (true) {
            System.out.println("请输入对应的学生的id");
            id = sc.nextLine();
            for (int i = 0; i < id.length(); i++) {
                if (id.charAt(i) < '0' && id.charAt(i) > '9') {
                    System.out.println("输入不合法！");
                    continue pool3;
                }
            }
            for (String string : studentList) {
                String studentID = string.split(",")[0].split("：")[1];
                if (studentID.equals(id)) {
                    return Integer.parseInt(id);
                }
            }
            System.out.println("找不到输入的id");
        }

    }

    //找到要修改学分的课程的ID
    private static int Choose_CourseId() throws SQLException {

        Scanner sc = new Scanner(System.in);

        ArrayList<String> CourseList = WatchAllCourses();

        pool4:
        while (true) {
            System.out.println("输入你对应的课程id");
            String id = sc.nextLine();
            for (int i = 0; i < id.length(); i++) {
                if (id.charAt(i) < '0' && id.charAt(i) > '9') {
                    System.out.println("输入不合法！");
                    continue pool4;
                }
            }
            for (String CourseInformation : CourseList) {
                String courseID = CourseInformation.split(" ，")[0].split("：")[1];
                if (courseID.equals(id)) {
                    return Integer.parseInt(id);
                }
            }
            System.out.println("找不到要改的课程！");
        }
    }

    //修改学分
    public static void Edit_Credit() throws SQLException {
        /*Scanner sc = new Scanner(System.in);

        Connection conn = null;
        PreparedStatement ps = null;

        int Course_id = Choose_CourseId();
        String credit = null;

        while (true) {
            System.out.println("请输入新的学分：(1~5的整数)");
            credit = sc.nextLine();

            if (credit.length() == 1 && (credit.equals("1") || credit.equals("2") || credit.equals("3") || credit.equals("4") || credit.equals("5"))) {
                break;
            }
            System.out.println("学分输入错误！");
        }
        try {
            conn = Jdbc.getConnection();
            String sql = "update courses set credit = ? where course_id = ?";
            ps = conn.prepareStatement(sql);

            ps.setInt(1, Integer.parseInt(credit));
            ps.setInt(2, Course_id);

            int rs = ps.executeUpdate();

            if (rs > 0) {
                System.out.println("修改成功！");
            } else {
                System.out.println("修改失败！");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (NumberFormatException e) {
            throw new RuntimeException(e);
        } finally {
            Jdbc.Release(conn, ps, null);
        }*/

        Scanner sc = new Scanner(System.in);

        //  选择课程
        int courseId = Choose_CourseId();

        // 输入验证
        String credit;
        while (true) {
            System.out.print("请输入新的学分（1~5的整数）:");
            credit = sc.nextLine().trim();
            if (credit.matches("[1-5]")) {  // 正则验证1-5单个数字
                break;
            }
            System.out.println("输入错误！请输入1~5的整数");
        }


        String sql = "update courses set credit = ? where course_id = ?";
        int num = curd.UpdateData(sql, Integer.parseInt(credit), courseId);


        if (num > 0) {
            System.out.println("修改成功！");
        } else {
            System.out.println("修改失败！");
        }


    }

    //查看选择某课程的学生
    public static void StudentOfCourse() throws SQLException {

        /*Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            int id = Choose_CourseId();

            conn = Jdbc.getConnection();
            String sql = "select * from student_courses where course_id = ?";
            ps = conn.prepareStatement(sql);

            ps.setInt(1, id);

            rs = ps.executeQuery();

            while (rs.next()) {
                int student_id = rs.getInt("student_id");
                String sql2 = "select * from student where student_id = ? ";
                ps = conn.prepareStatement(sql2);
                ps.setInt(1, student_id);
                ResultSet innerRs = ps.executeQuery();  // 使用一个新的变量存储内层查询的结果
                while (innerRs.next()) {  // 添加循环处理内层查询的所有结果
                    String Name = innerRs.getString("name");
                    System.out.println(Name);
                }
                innerRs.close();  // 关闭内层查询的结果集
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            Jdbc.Release(conn, ps, rs);
        }*/


        // 选择课程
        int courseId = Choose_CourseId();

        //获取课程的所有学生ID
        String outerSql = "select student_id from student_courses where course_id = ?";
        List<Map> outerResult = curd.QueryData(outerSql, courseId);

        // 查询学生姓名
        for (Map<String, Object> row : outerResult) {
            int studentId = (Integer) row.get("student_id");

            // 获取学生姓名
            String innerSql = "select name from student where student_id = ?";
            List<Map> innerResult = curd.QueryData(innerSql, studentId);


            if (!innerResult.isEmpty()) {
                String name = (String) innerResult.get(0).get("name");
                System.out.println(name);
            }
        }
    }

    //查看某课程的学生的名单
    public static void Teacher_Watch_CoursesOfStudent() throws SQLException {
        int student_id = Choose_studentId();
        WatchChooseCourse(student_id);
    }

    //加入新的学生
    public static void AddStudent() throws SQLException {

        Scanner sc = new Scanner(System.in);

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String gender = null;
        String phone = null;
        String name = null;

        while (true) {
            System.out.println("请输入用户名：");
            name = sc.nextLine();

            if (name.length() > 2) {
                break;
            }
            System.out.println("用户名不合法，请重新输入！");
        }


        // 输入校验循环
        while (true) {
            System.out.print("请输入新的手机号：");
            phone = sc.nextLine().trim();
            if (phone.matches("1\\d{10}")) {  // 正则验证 1开头 + 11位数字
                break;
            }
            System.out.println("手机号格式不对！");
        }

        while (true) {
            System.out.println("输入你的性别");
            gender = sc.nextLine();
            if (gender.equals("男") || gender.equals("女")) {
                break;
            }
            System.out.println("性别输入不对！");
        }

        int year = 2025;
        try {



            conn = Jdbc.getConnection();
            String maxIdSql = "select MAX(user_id) as max_id from users";
            PreparedStatement Maxps = conn.prepareStatement(maxIdSql);
            rs= Maxps.executeQuery();


            rs.next();


            int user_id = rs.getInt("max_id");




            String sql2 = "insert into student (user_id, student_number, name, gender,phone, enrollment_year) values(?, ?, ?, ?, ?, ?)";
            ps = conn.prepareStatement(sql2);

            ps.setInt(1, user_id);
            ps.setInt(2, user_id + 10086);
            ps.setString(3, name);
            ps.setString(4, gender);
            ps.setString(5, phone);
            ps.setInt(6, year);

            int rows = ps.executeUpdate();
            if (rows>0){
                System.out.println("插入成功");
            }else {
                System.out.println("插入失败");
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (NumberFormatException e) {
            throw new RuntimeException(e);
        } finally {
            Jdbc.Release(conn, ps, rs);
        }


    }
}