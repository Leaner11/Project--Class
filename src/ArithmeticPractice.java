import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class ArithmeticPractice {

    private static final int MAX_NUMBER = 10; // 参与运算的最大数字
    private static ArrayList<UserResult> leaderboard = new ArrayList<>(); // 排行榜

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int correctCount = 0;
        int totalQuestions = 0;

        do {
            totalQuestions++;
            Question question = generateQuestion();
            System.out.println(question.getQuestionText());
            try {
                int userAnswer = scanner.nextInt();
                if (question.isAnswerCorrect(userAnswer)) {
                    correctCount++;
                    System.out.println("回答正确！很棒哦，继续加油呀~");
                } else {
                    System.out.println("回答错误啦，正确答案是：" + question.getCorrectAnswer());
                }
            } catch (InputMismatchException e) {
                System.out.println("输入不合法哦，请输入一个整数。");
                scanner.nextLine(); // 清除输入缓冲区的无效内容
                totalQuestions--; // 不计入此次无效答题
                continue;
            }

            System.out.println("是否继续下一题？(输入'1'继续，输入'0'退出)");
            int choice = scanner.nextInt();
            if (choice == 0) {
                break;
            }
        } while (true);

        double accuracy = (double) correctCount / totalQuestions * 100;
        System.out.println("本次练习共完成 " + totalQuestions + " 题，正确率为：" + accuracy + " %");

        if (accuracy >= 80) {
            System.out.println("太棒啦，你的表现非常出色哦！继续保持呀~");
        } else if (accuracy >= 60) {
            System.out.println("还不错哦，继续努力可以做得更好呢~");
        } else {
            System.out.println("没关系呀，多练习就会越来越棒的，加油哦~");
        }

        // 处理排行榜更新
        System.out.println("请输入你的用户名（用于排行榜记录）：");
        String username = scanner.next();
        updateLeaderboard(username, accuracy);

        // 输出排行榜
        System.out.println("排行榜：");
        for (int i = 0; i < Math.min(leaderboard.size(), 3); i++) {
            UserResult result = leaderboard.get(i);
            System.out.println((i + 1) + ". " + result.getUsername() + "：正确率 " + result.getAccuracy() + " %");
        }

        scanner.close();
    }

    private static Question generateQuestion() {
        Random random = new Random();
        int num1 = random.nextInt(MAX_NUMBER + 1);
        int num2 = random.nextInt(MAX_NUMBER + 1);
        int operation = random.nextInt(4); // 0: 加法, 1: 减法, 2: 乘法, 3: 除法

        int correctAnswer;
        String questionText;

        switch (operation) {
            case 0:
                correctAnswer = num1 + num2;
                questionText = num1 + " + " + num2 + " =?";
                break;
            case 1:
                correctAnswer = num1 - num2;
                questionText = num1 + " - " + num2 + " =?";
                break;
            case 2:
                correctAnswer = num1 * num2;
                questionText = num1 + " × " + num2 + " =?";
                break;
            default:
                // 确保除法有意义（除数不为0且能整除）
//                if (num2 == 0 ) {
//                    num2 = random.nextInt(1, MAX_NUMBER + 1);
//                }
                do {
                    num1 = random.nextInt(10) + 1;
                    num2 = random.nextInt(10) + 1;
                } while (num1 % num2!= 0);
                correctAnswer = num1 / num2;
                questionText = num1 + " ÷ " + num2 + " =?";
                break;
        }

        return new Question(questionText, correctAnswer);
    }

    private static void updateLeaderboard(String username, double accuracy) {
        UserResult newResult = new UserResult(username, accuracy);
        if (leaderboard.size() < 3) {
            leaderboard.add(newResult);
            leaderboard.sort((o1, o2) -> Double.compare(o2.getAccuracy(), o1.getAccuracy()));
        } else {
            if (accuracy > leaderboard.get(2).getAccuracy()) {
                leaderboard.remove(2);
                leaderboard.add(newResult);
                leaderboard.sort((o1, o2) -> Double.compare(o2.getAccuracy(), o1.getAccuracy()));
            }
        }
    }
}

class Question {
    private String questionText;
    private int correctAnswer;

    public Question(String questionText, int correctAnswer) {
        this.questionText = questionText;
        this.correctAnswer = correctAnswer;
    }

    public String getQuestionText() {
        return questionText;
    }

    public int getCorrectAnswer() {
        return correctAnswer;
    }

    public boolean isAnswerCorrect(int userAnswer) {
        return userAnswer == correctAnswer;
    }
}

class UserResult {
    private String username;
    private double accuracy;

    public UserResult(String username, double accuracy) {
        this.username = username;
        this.accuracy = accuracy;
    }

    public String getUsername() {
        return username;
    }

    public double getAccuracy() {
        return accuracy;
    }
}