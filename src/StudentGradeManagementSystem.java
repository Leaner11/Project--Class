import java.util.Scanner;

public class StudentGradeManagementSystem {

    // 假设存储学生成绩信息的二维数组，格式为：[学号][课程成绩]
    // 这里简单示例两门课程：数学和英语
    private static double[][] studentGrades = new double[100][2];
    // 模拟用户登录信息，实际应用中可从数据库等获取
    private static final String TEACHER_USERNAME = "teacher";
    private static final String TEACHER_PASSWORD = "password";
    private static final String STUDENT_USERNAME_PREFIX = "student";
    private static final String STUDENT_PASSWORD_PREFIX = "password";

    // 用于记录当前是否登录的状态，初始化为false表示未登录
    private static boolean loggedIn = false;
    // 用于记录当前登录的用户类型，初始化为空字符串
    private static String userType = "";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String name = "";
        while (!loggedIn) {
            System.out.println("欢迎使用学生成绩管理系统，请先登录：");
            System.out.print("请输入用户名：");
            String username = scanner.next();
            System.out.print("请输入密码：");
            String password = scanner.next();

            // 验证教师用户登录信息
            if (username.equals(TEACHER_USERNAME) && password.equals(TEACHER_PASSWORD)) {
                loggedIn = true;
                userType = "teacher";
            } else if (username.startsWith(STUDENT_USERNAME_PREFIX) && password.startsWith(STUDENT_PASSWORD_PREFIX)) {
                loggedIn = true;
                userType = "student";
                name = username;
            } else {
                System.out.println("用户名或密码错误，请重新输入。");
            }
        }

        if ("student".equals(userType)) {
            studentMenu(scanner, name);
        } else if ("teacher".equals(userType)) {
            teacherMenu(scanner);
        }

        scanner.close();
    }

    /**
     * 学生菜单，提供学生查询自己成绩的功能选项
     *
     * @param scanner 用于获取用户输入的Scanner对象
     * @param username 学生用户名，用于获取学号
     */
    private static void studentMenu(Scanner scanner, String username) {
        int studentId = Integer.parseInt(username.substring(STUDENT_USERNAME_PREFIX.length()));

        System.out.println("学生成绩查询菜单：");
        System.out.println("1. 查询我的成绩");
        System.out.println("2. 退出");
        System.out.print("请输入选项：");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                queryStudentGrades(studentId);
                break;
            case 2:
                // 学生选择退出后，设置登录状态为未登录，然后重新进入登录循环
                loggedIn = false;
                userType = "";
                System.out.println("已退出学生成绩查询系统，返回登录界面。");
                break;
            default:
                System.out.println("无效的选项，请重新选择。");
        }

        if (loggedIn) {
            studentMenu(scanner, username);
        } else {
            // 重新进入登录循环
            main(null);
        }
    }

    /**
     * 根据学号查询学生的数学和英语成绩并打印出来
     *
     * @param studentId 要查询成绩的学生学号
     */
    private static void queryStudentGrades(int studentId) {
        System.out.println("学号为 " + studentId + " 的学生成绩如下：");
        System.out.println("数学成绩：" + studentGrades[studentId][0]);
        System.out.println("英语成绩：" + studentGrades[studentId][1]);
    }

    /**
     * 教师菜单，提供教师录入、修改、删除学生成绩的功能选项
     *
     * @param scanner 用于获取用户输入的Scanner对象
     */
    private static void teacherMenu(Scanner scanner) {
        System.out.println("教师成绩管理菜单：");
        System.out.println("1. 录入学生成绩");
        System.out.println("2. 修改学生成绩");
        System.out.println("3. 删除学生成绩");
        System.out.println("4. 退出");
        System.out.print("请输入选项：");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                enterStudentGrade(scanner);
                break;
            case 2:
                modifyStudentGrade(scanner);
                break;
            case 3:
                deleteStudentGrade(scanner);
                break;
            case 4:
                // 老师选择退出后，设置登录状态为未登录，然后重新进入登录循环
                loggedIn = false;
                userType = "";
                System.out.println("已退出教师成绩管理系统，返回登录界面。");
                break;
            default:
                System.out.println("无效的选项，请重新选择。");
        }

        if (loggedIn) {
            teacherMenu(scanner);
        } else {
            // 重新进入登录循环
            main(null);
        }
    }

    /**
     * 教师录入学生某门课程成绩的方法
     *
     * @param scanner 用于获取用户输入的Scanner对象
     */
    private static void enterStudentGrade(Scanner scanner) {
        System.out.print("请输入学生学号：");
        int studentId = scanner.nextInt();
        System.out.print("请输入要录入成绩的课程（0 - 数学，1 - 英语）：");
        int courseIndex = scanner.nextInt();
        System.out.print("请输入成绩：");
        double grade = scanner.nextDouble();

        studentGrades[studentId][courseIndex] = grade;

        System.out.println("成绩录入成功。");
    }

    /**
     * 教师修改学生某门课程成绩的方法
     *
     * @param scanner 用于获取用户输入的Scanner对象
     */
    private static void modifyStudentGrade(Scanner scanner) {
        System.out.print("请输入学生学号：");
        int studentId = scanner.nextInt();
        System.out.print("请输入要修改成绩的课程（0 - 数学，1 - 英语）：");
        int courseIndex = scanner.nextInt();
        System.out.print("请输入新的成绩：");
        double newGrade = scanner.nextDouble();

        studentGrades[studentId][courseIndex] = newGrade;

        System.out.println("成绩修改成功。");
    }

    /**
     * 教师删除学生某门课程成绩的方法
     *
     * @param scanner 用于获取用户输入的Scanner对象
     */
    private static void deleteStudentGrade(Scanner scanner) {
        System.out.print("请输入学生学号：");
        int studentId = scanner.nextInt();
        System.out.print("请输入要删除成绩的课程（0 - 数学，1 - 英语）：");
        int courseIndex = scanner.nextInt();

        studentGrades[studentId][courseIndex] = 0;

        System.out.println("成绩删除成功。");
    }
}