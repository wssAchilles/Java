package daily;

import javax.swing.*;

public class GradeClassifier {
    public static void main(String[] args) {
        String input = JOptionPane.showInputDialog(null, "请输入内容:", "输入框", JOptionPane.PLAIN_MESSAGE);

        // 如果用户点击“取消”或关闭窗口，input 为 null
        if (input != null) {
            JOptionPane.showMessageDialog(null, "你输入的是: " + input);
        } else {
            JOptionPane.showMessageDialog(null, "输入取消");
        }
    }
}

