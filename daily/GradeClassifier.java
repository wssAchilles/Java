package daily;

public class GradeClassifier {
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

