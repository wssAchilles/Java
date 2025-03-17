package ex1_23030327;
import javax.swing.*;
//学生成绩分等级：A(>=90),B(>=80,<90),C(>=70,<80),D(>=60,<70),E(<60)编程输入（0~100）数分等级。
public class ex1_3 {
    public static void main(String[] args) {
        String input = JOptionPane.showInputDialog(null, "请输入学生成绩（0-100）：", "成绩输入", JOptionPane.QUESTION_MESSAGE);
        if (input == null || input.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "未输入成绩或取消输入", "错误", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            int score = Integer.parseInt(input);
            String grade;
            if (score >= 90 && score <= 100) {
                grade = "A";
            } else if (score >= 80) {
                grade = "B";
            } else if (score >= 70) {
                grade = "C";
            } else if (score >= 60) {
                grade = "D";
            } else if (score >= 0) {
                grade = "E";
            } else {
                JOptionPane.showMessageDialog(null, "成绩必须在 0-100 之间！", "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }
            JOptionPane.showMessageDialog(null, "成绩等级：" + grade, "结果", JOptionPane.INFORMATION_MESSAGE);

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "请输入有效的数字！", "错误", JOptionPane.ERROR_MESSAGE);
        }
    }
}
