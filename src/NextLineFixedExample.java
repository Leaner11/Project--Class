import java.util.Scanner;
public class NextLineFixedExample {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("请输入一个整数: ");
        int num = scanner.nextInt();
        scanner.nextLine();//消耗掉整数后的换行符
        System.out.print("请输入一行字符串: ");
        String line = scanner.nextLine();
        System.out.println("你输入的整数是: " + num);
        System.out.println("你输入的字符串是: " + line);
        scanner.close();
    }
}