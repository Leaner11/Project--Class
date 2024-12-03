import java.util.HashMap;
import java.util.Scanner;

public class GarbageClassificationProgram {

    // 用于存储垃圾名称及其对应的垃圾分类信息的哈希表
    private static HashMap<String, String> garbageClassificationMap = new HashMap<>();

    public static void main(String[] args) {
        // 初始化垃圾名称和分类的对应关系
        initializeClassificationMap();

        Scanner scanner = new Scanner(System.in);

        // 主循环，用于持续提供菜单选项给用户
        while (true) {
            System.out.println("请选择功能：");
            System.out.println("1. 查询");
            System.out.println("2. 测试");
            System.out.println("3. 退出");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    queryGarbageClassification(scanner);
                    break;
                case 2:
                    conductTest(scanner);
                    break;
                case 3:
                    System.out.println("感谢使用，再见！");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("无效的选择，请重新输入。");
            }
        }
    }

    // 初始化垃圾名称和分类的对应关系的方法
    private static void initializeClassificationMap() {
        // 示例数据，可根据实际情况补充更多
        garbageClassificationMap.put("苹果核", "厨余垃圾");
        garbageClassificationMap.put("塑料瓶", "可回收垃圾");
        garbageClassificationMap.put("废旧电池", "有害垃圾");
        garbageClassificationMap.put("用过的纸巾", "其他垃圾");
    }

    // 查询垃圾分类情况的方法
    private static void queryGarbageClassification(Scanner scanner) {
        System.out.println("请输入垃圾名称：");
        String garbageName = scanner.next();

        if (garbageClassificationMap.containsKey(garbageName)) {
            System.out.println(garbageName + "属于：" + garbageClassificationMap.get(garbageName));
        } else {
            System.out.println("未找到该垃圾的分类信息，请确认输入是否正确。");
        }
    }

    // 进行垃圾分类测试的方法
    private static void conductTest(Scanner scanner) {
        int totalQuestions = garbageClassificationMap.size();
        int correctAnswers = 0;

        for (String garbageName : garbageClassificationMap.keySet()) {
            System.out.println("请判断 " + garbageName + " 属于哪种垃圾：");
            String userAnswer = scanner.next();

            if (userAnswer.equals(garbageClassificationMap.get(garbageName))) {
                correctAnswers++;
                System.out.println("回答正确！");
            } else {
                System.out.println("回答错误，正确答案是：" + garbageClassificationMap.get(garbageName));
            }
        }

        double accuracy = (double) correctAnswers / totalQuestions * 100;
        System.out.println("本次测试结束，您的正确率为：" + accuracy + "%");
    }
}