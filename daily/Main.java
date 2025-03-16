package daily;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        // 创建一个线程来显示全屏弹窗，这样可以不阻塞主线程
        SwingUtilities.invokeLater(() -> {
            // 创建一个全屏 JFrame
            JFrame fullScreenFrame = new JFrame("大屏后台窗口");

            // 设置全屏
            fullScreenFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            fullScreenFrame.setUndecorated(true); // 去掉窗口的装饰（边框、标题栏等）
            fullScreenFrame.setBackground(Color.BLACK); // 设置背景色为黑色

            // 可以添加一些提示信息
            JLabel label = new JLabel("后台程序正在运行...", JLabel.CENTER);
            label.setFont(new Font("Arial", Font.PLAIN, 40));
            label.setForeground(Color.WHITE); // 设置文字颜色为白色
            fullScreenFrame.add(label, BorderLayout.CENTER);

            // 显示全屏窗口
            fullScreenFrame.setVisible(true);

            // 模拟其他程序后台运行
            try {
                Thread.sleep(3000);  // 这里可以模拟后台任务，显示3秒钟
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // 隐藏窗口（可以选择在后台继续运行）
            fullScreenFrame.setVisible(false);
        });

        // 主程序逻辑
        System.out.println("程序开始执行...");

        // 模拟主程序运行
        try {
            Thread.sleep(5000);  // 模拟其他任务
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("主程序运行完毕...");
    }
}
