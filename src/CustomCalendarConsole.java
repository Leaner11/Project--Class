import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.Scanner;

/*
日历排序如何实现的，
 */

public class CustomCalendarConsole {

    // 显示指定年份和月份的月历
    public static void displayMonthCalendar(int year, int month) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month - 1, 1);

        System.out.println("\n" + getMonthName(month) + " " + year);
        System.out.println("Sun Mon Tue Wed Thu Fri Sat");
        // 获取月份的天数
        int daysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        // 获取月份的第一天是周几 周日为1 以此类推
        int firstDayOfMonth = cal.get(Calendar.DAY_OF_WEEK);

        int dayCounter = 1;
        for (int i = 1; i < firstDayOfMonth; i++) {
            System.out.print("    ");
        }

        for (int i = firstDayOfMonth; i <= 7; i++) {
            System.out.printf("%4d", dayCounter);
            dayCounter++;
        }

        System.out.println();

        while (dayCounter <= daysInMonth) {
            for (int i = 1; i <= 7 && dayCounter <= daysInMonth; i++) {
                System.out.printf("%4d", dayCounter);
                dayCounter++;
            }
            System.out.println();
        }
    }

    // 获取月份名称
    private static String getMonthName(int month) {
        String[] monthNames = {"January", "February", "March", "April", "May", "June", "July", "August", "September",
                "October", "November", "December"};
        return monthNames[month - 1];
    }

    // 添加日程到文件
    public static void addSchedule(int year, int month, int day, String schedule) {
        try {
            File file = new File(year + "_" + month + "_" + day + "_schedule.txt");
            if (!file.exists()) {
                file.createNewFile();
            }

            BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
            writer.write(schedule + "\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 显示指定月份所有日程信息
    public static void displayAllSchedules(int year, int month) {
        int daysInMonth = Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH);

        for (int day = 1; day <= daysInMonth; day++) {
            File file = new File(year + "_" + month + "_" + day + "_schedule.txt");
            if (file.exists()) {
                try {
                    Scanner scanner = new Scanner(file);
                    System.out.println("\nSchedule for " + year + "-" + month + "-" + day + ":");
                    while (scanner.hasNextLine()) {
                        System.out.println(scanner.nextLine());
                    }
                    scanner.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n1. Display current month calendar");
            System.out.println("2. Display specific month calendar");
            System.out.println("3. Add schedule");
            System.out.println("4. Display all schedules for a month");
            System.out.println("5. Quit");

            int choice = scanner.nextInt();

            if (choice == 1) {
                Calendar now = Calendar.getInstance();
                int year = now.get(Calendar.YEAR);
                int month = now.get(Calendar.MONTH) + 1;
                displayMonthCalendar(year, month);
            } else if (choice == 2) {
                System.out.println("Enter the year:");
                int year = scanner.nextInt();
                System.out.println("Enter the month:");
                int month = scanner.nextInt();
                displayMonthCalendar(year, month);
            } else if (choice == 3) {
                System.out.println("Enter the year:");
                int year = scanner.nextInt();
                System.out.println("Enter the month:");
                int month = scanner.nextInt();
                System.out.println("Enter the day:");
                int day = scanner.nextInt();
                System.out.println("Enter the schedule:");
                String schedule = scanner.next();
                addSchedule(year, month, day, schedule);
            } else if (choice == 4) {
                System.out.println("Enter the year:");
                int year = scanner.nextInt();
                System.out.println("Enter the month:");
                int month = scanner.nextInt();
                displayAllSchedules(year, month);
            } else if (choice == 5) {
                break;
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }

        scanner.close();
    }
}