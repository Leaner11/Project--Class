import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class BankSystem {

    // 用户信息文件路径
    private static final String USER_FILE = "bank.txt";
    // 交易记录文件路径
    private static final String TRANSACTION_FILE = "transactions.txt";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("欢迎使用银行系统，请选择用户类型：");
        System.out.println("1. 银行员工");
        System.out.println("2. 普通用户");
        System.out.print("请输入选项：");
        int userTypeChoice = scanner.nextInt();

        if (userTypeChoice == 1) {
            employeeLogin(scanner);
        } else if (userTypeChoice == 2) {
            userLogin(scanner);
        } else {
            System.out.println("无效的选择，请重新运行程序。");
        }

        scanner.close();
    }

    private static void employeeLogin(Scanner scanner) {
        System.out.print("请输入员工用户名：");
        String username = scanner.next();
        System.out.print("请输入员工密码：");
        String password = scanner.next();

        // 这里可添加真实的员工验证逻辑，暂时简单模拟
        if ("111".equals(username) && "111".equals(password)) {
            employeeMenu(scanner);
        } else {
            System.out.println("员工用户名或密码错误。");
        }
    }

    private static void employeeMenu(Scanner scanner) {
        System.out.println("银行员工菜单：");
        System.out.println("1. 创建新用户");
        System.out.println("2. 为普通用户存钱");
        System.out.println("3. 为普通用户取钱");
        System.out.println("4. 退出");
        System.out.print("请输入选项：");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                createNewUser();
                break;
            case 2:
                depositForUser(scanner);
                break;
            case 3:
                withdrawForUser(scanner);
                break;
            case 4:
                System.out.println("已退出员工菜单。");
                return;
            default:
                System.out.println("无效的选项，请重新选择。");
        }

        employeeMenu(scanner);
    }

    private static void createNewUser() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("请输入新用户的用户名：");
        String newUsername = scanner.next();
        System.out.print("请输入新用户的密码：");
        String newPassword = scanner.next();

        try (FileWriter writer = new FileWriter(USER_FILE, true)) {
            writer.write(newUsername + "," + newPassword + ",0\n");
            System.out.println("新用户创建成功，初始余额为 0。");
        } catch (IOException e) {
            System.out.println("创建新用户时发生错误：" + e.getMessage());
        }

        scanner.close();
    }

    private static void depositForUser(Scanner scanner) {
        System.out.print("请输入要存钱的普通用户的用户名：");
        String username = scanner.next();

        double amount = getAmount(scanner, "请输入存款金额：");

        try {
            File file = new File(USER_FILE);
            Scanner fileScanner = new Scanner(file);
            StringBuilder newContent = new StringBuilder();
            boolean userFound = false;
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] userData = line.split(",");
                if (userData[0].equals(username)) {
                    double newBalance = Double.parseDouble(userData[2]) + amount;
                    userData[2] = String.valueOf(newBalance);
                    userFound = true;
                }
                newContent.append(String.join(",", userData)).append("\n");
            }
            fileScanner.close();

            if (userFound) {
                // 更新用户文件
                FileWriter writer = new FileWriter(USER_FILE);
                writer.write(newContent.toString());
                writer.close();

                // 记录交易
                recordTransaction(username, "deposit", amount);

                System.out.println("存款成功，用户 " + username + " 的新余额为 " + newContent);
            } else {
                System.out.println("未找到该用户。");
            }
        } catch (IOException e) {
            System.out.println("存款操作发生错误：" + e.getMessage());
        }
    }

    private static void withdrawForUser(Scanner scanner) {
        System.out.print("请输入要取钱的普通用户的用户名：");
        String username = scanner.next();

        double amount = getAmount(scanner, "请输入取款金额：");

        try {
            File file = new File(USER_FILE);
            Scanner fileScanner = new Scanner(file);
            StringBuilder newContent = new StringBuilder();
            boolean userFound = false;
            double currentBalance = 0;
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] userData = line.split(",");
                if (userData[0].equals(username)) {
                    currentBalance = Double.parseDouble(userData[2]);
                    if (currentBalance >= amount) {
                        double newBalance = currentBalance - amount;
                        userData[2] = String.valueOf(newBalance);
                        userFound = true;
                    } else {
                        System.out.println("余额不足。");
                        return;
                    }
                }
                newContent.append(String.join(",", userData)).append("\n");
            }
            fileScanner.close();

            if (userFound) {
                // 更新用户文件
                FileWriter writer = new FileWriter(USER_FILE);
                writer.write(newContent.toString());
                writer.close();

                // 记录交易
                recordTransaction(username, "withdrawal", amount);

                System.out.println("取款成功，用户 " + username + " 的新余额为 " + currentBalance);
            } else {
                System.out.println("未找到该用户。");
            }
        } catch (IOException e) {
            System.out.println("取款操作发生错误：" + e.getMessage());
        }
    }

    private static double getAmount(Scanner scanner, String prompt) {
        System.out.print(prompt);
        return scanner.nextDouble();
    }

    private static void recordTransaction(String username, String type, double amount) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = sdf.format(new Date());

        try (FileWriter writer = new FileWriter(TRANSACTION_FILE, true)) {
            writer.write(username + "," + type + "," + amount + "," + date + "\n");
        } catch (IOException e) {
            System.out.println("记录交易时发生错误：" + e.getMessage());
        }
    }

    private static void userLogin(Scanner scanner) {
        System.out.print("请输入用户名：");
        String username = scanner.next();
        System.out.print("请输入密码：");
        String password = scanner.next();

        try {
            File file = new File(USER_FILE);
            Scanner fileScanner = new Scanner(file);
            boolean userFound = false;
            double balance = 0;
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] userData = line.split(",");
                if (userData[0].equals(username) && userData[1].equals(password)) {
                    userFound = true;
                    balance = Double.parseDouble(userData[2]);
                    break;
                }
            }
            fileScanner.close();

            if (userFound) {
                userMenu(scanner, username, balance);
            } else {
                System.out.println("用户名或密码错误。");
            }
        } catch (IOException e) {
            System.out.println("登录时发生错误：" + e.getMessage());
        }
    }

    private static void userMenu(Scanner scanner, String username, double balance) {
        System.out.println("普通用户菜单：");
        System.out.println("1. 查询余额");
        System.out.println("2. 存钱");
        System.out.println("3. 取钱");
        System.out.println("4. 退出");
        System.out.print("请输入选项：");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                System.out.println("您的余额为：" + balance);
                break;
            case 2:
                double depositAmount = getAmount(scanner, "请输入存款金额：");
                balance += depositAmount;
                updateUserBalance(username, balance);
                recordTransaction(username, "deposit", depositAmount);
                System.out.println("存款成功，您的新余额为：" + balance);
                break;
            case 3:
                double withdrawalAmount = getAmount(scanner, "请输入取款金额：");
                if (balance >= withdrawalAmount) {
                    balance -= withdrawalAmount;
                    updateUserBalance(username, balance);
                    recordTransaction(username, "withdrawal", withdrawalAmount);
                    System.out.println("取款成功，您的新余额为：" + balance);
                } else {
                    System.out.println("余额不足。");
                }
                break;
            case 4:
                System.out.println("已退出普通用户菜单。");
                return;
            default:
                System.out.println("无效的选项，请重新选择。");
        }

        userMenu(scanner, username, balance);
    }

    private static void updateUserBalance(String username, double newBalance) {
        try {
            File file = new File(USER_FILE);
            Scanner fileScanner = new Scanner(file);
            StringBuilder newContent = new StringBuilder();
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] userData = line.split(",");
                if (userData[0].equals(username)) {
                    userData[2] = String.valueOf(newBalance);
                }
                newContent.append(String.join(",", userData)).append("\n");
            }
            fileScanner.close();

            FileWriter writer = new FileWriter(USER_FILE);
            writer.write(newContent.toString());
            writer.close();
        } catch (IOException e) {
            System.out.println("更新用户余额时发生错误：" + e.getMessage());
        }
    }
}