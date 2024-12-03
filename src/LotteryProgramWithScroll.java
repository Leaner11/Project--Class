import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

// 参与者类，用于存储每个参与者的姓名和编号
class Participant {
    private String name;
    private int number;

    // 构造函数，用于创建参与者对象时初始化姓名和编号
    public Participant(String name, int number) {
        this.name = name;
        this.number = number;
    }

    // 获取参与者姓名的方法
    public String getName() {
        return name;
    }

    // 获取参与者编号的方法
    public int getNumber() {
        return number;
    }
}

// 抽奖程序主类
public class LotteryProgramWithScroll {
    private List<Participant1> participants; // 存储所有参与者信息的列表
    private boolean[] isSelected; // 用于标记每个参与者是否已被选中的数组
    private int totalParticipants; // 参与者的总数
    private int numWinners; // 每次抽奖的中奖人数

    // 构造函数，初始化抽奖程序相关的成员变量
    public LotteryProgramWithScroll() {
        participants = new ArrayList<>();
        isSelected = null;
        totalParticipants = 0;
        numWinners = 0;
    }

    // 从文件导入参与者信息的方法
    public void importParticipantsFromFile(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;

            // 先清空参与者列表，以防重复导入时出现问题
            participants = new ArrayList<>();

            // 逐行读取文件内容
            while ((line = br.readLine())!= null) {
                String[] parts = line.split(",");

                // 确保每行数据格式正确，包含姓名和编号两部分
                if (parts.length == 2) {
                    String name = parts[0];
                    int number = Integer.parseInt(parts[1]);

                    // 创建参与者对象并添加到列表中
                    participants.add(new Participant1(name, number));
                }
            }

            // 更新参与者总数
            totalParticipants = participants.size();

            // 根据参与者总数创建标记数组，并初始化为全部未选中状态
            isSelected = new boolean[totalParticipants];
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("文件读取错误，请检查文件路径和格式：" + filePath);
        }
    }

    // 设置抽奖参数的方法，主要设置每次抽奖的中奖人数
    public void setLotterySettings(int numWinners) {
        // 中奖人数必须是正数，否则提示用户重新设置
        if (numWinners <= 0) {
            System.out.println("中奖人数必须是正数，请重新设置。");
            return;
        }

        // 中奖人数不能大于参与者总数，否则提示用户重新设置
        if (numWinners > totalParticipants) {
            System.out.println("中奖人数不能大于参与者总数，请重新设置。");
            return;
        }

        // 设置中奖人数
        this.numWinners = numWinners;
    }

    // 抽奖逻辑的方法，用于随机选择中奖者
    public List<Participant1> conductLottery() {
        List<Participant1> winners = new ArrayList<>();
        Random random = new Random();
        int count = 0;

        // 循环选择中奖者，直到达到设定的中奖人数
        while (count < numWinners) {
            int index = random.nextInt(totalParticipants);

            // 如果该参与者未被选中，则将其标记为已选中，并添加到中奖者列表中
            if (!isSelected[index]) {
                isSelected[index] = true;
                winners.add(participants.get(index));
                count++;
            }
        }

        return winners;
    }

    // 控制台抽奖效果展示的方法，包括倒计时和滚动显示等效果
    public static void consoleLotteryEffect(LotteryProgramWithScroll lottery) {
        Scanner scanner = new Scanner(System.in);

        // 提示用户抽奖即将开始，等待用户按回车键继续
        System.out.println("抽奖即将开始，请按回车键继续...");
        scanner.nextLine();

        // 创建一个线程用于实现抽奖倒计时效果
        Thread countdownThread = new Thread(() -> {
            for (int i = 10; i >= 0; i--) {
                System.out.println(i);

                try {
                    // 线程休眠1秒，实现每秒更新倒计时数字的效果
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        // 启动倒计时线程
        countdownThread.start();

        try {
            // 等待倒计时线程执行完毕
            countdownThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("抽奖滚动显示开始...");

        // 创建一个线程用于模拟抽奖滚动显示参与者信息的效果
        Thread scrollThread = new Thread(() -> {
            Random random = new Random();
            boolean interrupted = false;

            try {
                // 循环模拟滚动显示，直到线程被中断或满足其他退出条件
                while (!interrupted &&!Thread.currentThread().isInterrupted()) {
                    int index = random.nextInt(lottery.totalParticipants);
                    Participant1 p = lottery.participants.get(index);

                    // 打印参与者的姓名和编号，模拟滚动显示效果
                    System.out.println(p.getName() + " - " + p.getNumber());

                    try {
                        // 线程休眠200毫秒，控制滚动显示的速度
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        // 当线程在休眠期间被中断时，重新设置中断状态
                        Thread.currentThread().interrupt();
                        interrupted = true;
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (interrupted) {
                    System.out.println("抽奖滚动显示已停止。");
                }
            }
        });

        // 启动滚动显示线程
        scrollThread.start();

        System.out.println("按回车键结束抽奖滚动显示并确定中奖者...");
        try {
            // 等待用户按回车键，读取用户输入
            scanner.nextLine();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("控制台输入读取错误，请重新尝试。");
        }

        // 中断滚动显示线程，使其停止滚动显示
        scrollThread.interrupt();

        System.out.println("抽奖结束！");
    }

    public static void main(String[] args) {
        LotteryProgramWithScroll lottery = new LotteryProgramWithScroll();

        // 从指定文件导入参与者信息，假设文件名为participants.txt，可根据实际修改
        lottery.importParticipantsFromFile("participants.txt");

        // 设置中奖人数为3，可根据实际修改
        lottery.setLotterySettings(3);

        // 展示控制台抽奖效果
        consoleLotteryEffect(lottery);

        List<Participant1> winners = lottery.conductLottery();

        // 遍历中奖者列表，打印出中奖者的姓名和编号
        for (Participant1 winner : winners) {
            System.out.println("中奖者：" + winner.getName() + "，编号：" + winner.getNumber());
        }
    }
}