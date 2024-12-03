import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class EnglishWordLearningProgram {

    // 存储不同主题下不同难度的单词
    private static Map<String, Map<String, List<String>>> wordBank = new HashMap<>();
    // 存储用户答题情况
    private static int totalQuestions = 0;
    private static int correctAnswers = 0;

    public static void main(String[] args) {
        initializeWordBank();

        Scanner scanner = new Scanner(System.in);

        System.out.println("欢迎使用英语单词学习程序！");
        System.out.println("请选择学习的主题：");
        for (String theme : wordBank.keySet()) {
            System.out.println(theme);
        }
        String selectedTheme = scanner.nextLine();

        System.out.println("请选择单词难度（简单、中等、困难）：");
        String selectedDifficulty = scanner.nextLine();

        List<String> wordsForLearning = wordBank.get(selectedTheme).get(selectedDifficulty);

        if (wordsForLearning == null) {
            System.out.println("所选主题和难度下没有可用的单词，请重新选择。");
            return;
        }

        learnWords(scanner, wordsForLearning);

        scanner.close();
    }

    private static void initializeWordBank() {
        // 示例：添加动物主题的单词
        Map<String, List<String>> animalWords = new HashMap<>();
        List<String> easyAnimalWords = new ArrayList<>();
        easyAnimalWords.add("cat");
        easyAnimalWords.add("dog");
        animalWords.put("简单", easyAnimalWords);

        List<String> mediumAnimalWords = new ArrayList<>();
        mediumAnimalWords.add("elephant");
        mediumAnimalWords.add("tiger");
        animalWords.put("中等", mediumAnimalWords);

        List<String> difficultAnimalWords = new ArrayList<>();
        difficultAnimalWords.add("hippopotamus");
        difficultAnimalWords.add("rhinoceros");
        animalWords.put("困难", difficultAnimalWords);

        wordBank.put("动物", animalWords);

        // 示例：添加食物主题的单词
        Map<String, List<String>> foodWords = new HashMap<>();
        List<String> easyFoodWords = new ArrayList<>();
        easyFoodWords.add("apple");
        easyFoodWords.add("banana");
        foodWords.put("简单", easyFoodWords);

        List<String> mediumFoodWords = new ArrayList<>();
        mediumFoodWords.add("pizza");
        mediumFoodWords.add("hamburger");
        foodWords.put("中等", mediumFoodWords);

        List<String> difficultFoodWords = new ArrayList<>();
        difficultFoodWords.add("spaghetti");
        difficultFoodWords.add("sushi");
        foodWords.put("困难", difficultFoodWords);

        wordBank.put("食物", foodWords);
    }

    private static void learnWords(Scanner scanner, List<String> words) {
        Random random = new Random();

        while (!words.isEmpty()) {
            int index = random.nextInt(words.size());
            String wordToLearn = words.get(index);
            words.remove(index);

            System.out.println("请写出中文意思为“" + getChineseMeaning(wordToLearn) + "”的英文单词：");
            String userAnswer = scanner.nextLine();

            if (userAnswer.equalsIgnoreCase(wordToLearn)) {
                System.out.println("回答正确！");
                correctAnswers++;
            } else {
                System.out.println("回答错误。如果你不会，可以选择求助（输入1），或者继续下一题（输入2）。");
                try {
                    int choice = scanner.nextInt();
                    if (choice == 1) {
                        scanner.nextLine(); // 清除输入缓冲区的无效内容

                        getHelp(scanner, wordToLearn);
                    }
                } catch (InputMismatchException e) {
                    System.out.println("无效输入，请输入数字1或2。");
                    scanner.nextLine(); // 清除输入缓冲区的无效内容
                }
            }

            totalQuestions++;
        }

        showSummary();
    }

    private static String getChineseMeaning(String word) {
        // 这里可以添加真实的中文释义查询逻辑，暂时简单返回示例释义
        if ("cat".equals(word)) {
            return "猫";
        } else if ("dog".equals(word)) {
            return "狗";
        } else if ("elephant".equals(word)) {
            return "大象";
        } else if ("tiger".equals(word)) {
            return "老虎";
        } else if ("hippopotamus".equals(word)) {
            return "河马";
        } else if ("rhinoceros".equals(word)) {
            return "犀牛";
        } else if ("apple".equals(word)) {
            return "苹果";
        } else if ("banana".equals(word)) {
            return "香蕉";
        } else if ("pizza".equals(word)) {
            return "披萨";
        } else if ("hamburger".equals(word)) {
            return "汉堡包";
        } else if ("spaghetti".equals(word)) {
            return "意大利面";
        } else if ("sushi".equals(word)) {
            return "寿司";
        }

        return "未知";
    }

    private static void getHelp(Scanner scanner, String word) {
        StringBuilder maskedWord = new StringBuilder();
        for (int i = 0; i < word.length(); i++) {
            maskedWord.append("*");
        }

        int attempts = 0;
        while (attempts < word.length()) {
            System.out.println("这个单词是：" + maskedWord.toString());
            System.out.println("请输入你认为可能在这个单词中的字母：");


            String letter = scanner.nextLine();

            if (word.contains(letter)) {
                for (int i = 0; i < word.length(); i++) {
                    if (word.charAt(i) == letter.charAt(0)) {
                        maskedWord.setCharAt(i, letter.charAt(0));
                    }
                }
                System.out.println("猜对了！现在这个单词是：" + maskedWord.toString());
            } else {
                System.out.println("很遗憾，这个字母不在单词中。");
            }

            attempts++;

            if (maskedWord.toString().equals(word)) {
                System.out.println("你已经猜出了这个单词，回答正确！");
                correctAnswers++;
                break;
            }
        }

        if (!maskedWord.toString().equals(word)) {
            System.out.println("很遗憾，你没有猜出这个单词。正确答案是：" + word);
        }
    }

    private static void showSummary() {
        System.out.println("学习总结：");
        System.out.println("总答题量：" + totalQuestions);
        System.out.println("答对数：" + correctAnswers);
        double accuracy = (double) correctAnswers / totalQuestions * 100;
        System.out.println("正确率：" + accuracy + "%" );
    }
}