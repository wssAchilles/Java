package ex3_23030327;
//23030327许子祺
/**
 * 猜数游戏，随机产生一个整数：
 * a. 能够输入一个数判断其大小，若大于初始值提示"Too Large"屏幕背景为红色；
 *    若小于初始值提示"Too Small"背景为蓝色；当等于时显示"Right,Good!"。
 *    不等可反复重输入直到相等，相等时不可再输入。
 * b. 设置"重置"命令按钮。
 */
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Random;


public class ex3_4 extends Application {
    private int targetNumber; // 要猜的目标数字
    private TextField inputField; // 输入框
    private Label resultLabel; // 结果提示标签
    private Label rangeLabel; // 显示当前数字范围的标签
    private Scene scene; // 场景
    private boolean gameOver; // 游戏是否结束
    private int minValue = 1; // 当前范围的最小值
    private int maxValue = 100; // 当前范围的最大值

    @Override
    public void start(Stage primaryStage) {
        // 设置窗口标题
        primaryStage.setTitle("猜数字游戏");
        
        // 初始化界面组件
        Label instructionLabel = new Label("请猜一个1到100之间的数字：");
        inputField = new TextField();
        inputField.setPrefWidth(100);
        
        Button guessButton = new Button("猜测");
        Button resetButton = new Button("重置");
        
        resultLabel = new Label("");
        resultLabel.setMinHeight(30);
        resultLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        
        // 显示当前猜测范围的标签
        rangeLabel = new Label("当前范围: 1-100");
        rangeLabel.setStyle("-fx-font-style: italic;");
        
        // 创建水平布局放置输入框和猜测按钮
        HBox inputBox = new HBox(10);
        inputBox.setAlignment(Pos.CENTER);
        inputBox.getChildren().addAll(inputField, guessButton);
        
        // 创建垂直布局放置所有组件
        VBox root = new VBox(15);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(instructionLabel, rangeLabel, inputBox, resultLabel, resetButton);
        
        // 创建场景
        scene = new Scene(root, 400, 250);
        primaryStage.setScene(scene);
        
        // 初始化游戏
        initializeGame();
        
        // 设置猜测按钮点击事件
        guessButton.setOnAction(e -> checkGuess());
        
        // 设置输入框回车事件
        inputField.setOnAction(e -> checkGuess());
        
        // 设置重置按钮点击事件
        resetButton.setOnAction(e -> initializeGame());
        
        // 显示窗口
        primaryStage.show();
    }
    
    /**
     * 初始化游戏状态
     */
    private void initializeGame() {
        // 生成1-100之间的随机数
        Random random = new Random();
        targetNumber = random.nextInt(100) + 1;
        
        // 重置游戏状态和猜测范围
        minValue = 1;
        maxValue = 100;
        gameOver = false;
        inputField.setEditable(true);
        inputField.clear();
        resultLabel.setText("");
        rangeLabel.setText("当前范围: 1-100");
        scene.setFill(null); // 重置背景色
        scene.getRoot().setStyle("-fx-background-color: white;"); // 设置默认背景色
        
        // 将焦点设置到输入框
        inputField.requestFocus();
        
        System.out.println("游戏已重置，目标数字是: " + targetNumber); // 用于调试
    }
    
    /**
     * 检查用户输入的猜测值
     */
    private void checkGuess() {
        // 如果游戏已结束，不做处理
        if (gameOver) {
            return;
        }
        
        try {
            // 获取用户输入并转换为整数
            String input = inputField.getText().trim();
            int guessNumber = Integer.parseInt(input);
            
            // 比较猜测值与目标值
            if (guessNumber > targetNumber) {
                // 猜测值过大
                resultLabel.setText("Too Large");
                scene.getRoot().setStyle("-fx-background-color: #ff9999;"); // 红色背景
                
                // 更新猜测范围的最大值
                if (guessNumber < maxValue) {
                    maxValue = guessNumber - 1;
                    updateRangeLabel();
                }
                
                // 为"Too Large"添加跳动特效
                playBounceAnimation(resultLabel);
                
            } else if (guessNumber < targetNumber) {
                // 猜测值过小
                resultLabel.setText("Too Small");
                scene.getRoot().setStyle("-fx-background-color: #9999ff;"); // 蓝色背景
                
                // 更新猜测范围的最小值
                if (guessNumber > minValue) {
                    minValue = guessNumber + 1;
                    updateRangeLabel();
                }
                
                // 为"Too Small"添加跳动特效
                playBounceAnimation(resultLabel);
                
            } else {
                // 猜对了
                resultLabel.setText("Right, Good!");
                scene.getRoot().setStyle("-fx-background-color: #99ff99;"); // 绿色背景
                gameOver = true;
                inputField.setEditable(false); // 禁止再次输入
            }
            
        } catch (NumberFormatException e) {
            // 输入无效
            resultLabel.setText("请输入有效的数字！");
        }
    }
    
    /**
     * 更新显示当前猜测范围的标签
     */
    private void updateRangeLabel() {
        rangeLabel.setText(String.format("当前范围: %d-%d", minValue, maxValue));
    }
    
    /**
     * 为标签添加跳动特效
     */
    private void playBounceAnimation(Label label) {
        // 创建缩放动画
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(150), label);
        scaleTransition.setFromX(1.0);
        scaleTransition.setFromY(1.0);
        scaleTransition.setToX(1.3);
        scaleTransition.setToY(1.3);
        scaleTransition.setCycleCount(4);
        scaleTransition.setAutoReverse(true);
        
        // 创建上下移动动画
        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(100), label);
        translateTransition.setFromY(0);
        translateTransition.setToY(-10);
        translateTransition.setCycleCount(6);
        translateTransition.setAutoReverse(true);
        
        // 开始动画
        scaleTransition.play();
        translateTransition.play();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
