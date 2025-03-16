package ex1_23030327;
import java.util.Scanner;
//学生成绩分等级：A(>=90),B(>=80,<90),C(>=70,<80),D(>=60,<70),E(<60)编程输入（0~100）数分等级。
public class ex1_3 {
    public static void main(String[] args) {
        java.io.Console console = System.console();
        System.out.print("请输入学生成绩（0-100）：");
        String input = console.readLine();
        int score = Integer.parseInt(input);
        if (score >= 90 && score <= 100) {
            System.out.println("成绩等级：A");
        } else if (score >= 80) {
            System.out.println("成绩等级：B");
        } else if (score >= 70) {
            System.out.println("成绩等级：C");
        } else if (score >= 60) {
            System.out.println("成绩等级：D");
        } else if (score >= 0) {
            System.out.println("成绩等级：E");
        }
    }
}
