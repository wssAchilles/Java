package homework;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class FaceChanger extends Application {

    private boolean isHappy = true; // 默认显示笑脸
    private Canvas canvas;

    public void start(Stage primaryStage) {
        // 创建画布用于绘制表情
        canvas = new Canvas(300, 300);

        // 创建切换按钮
        Button changeButton = new Button("切换表情");
        changeButton.setPrefWidth(120);
        changeButton.setOnAction(e -> {
            isHappy = !isHappy;
            drawFace();
        });

        // 创建垂直布局容器
        VBox root = new VBox(20);
        root.setPadding(new Insets(15));
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(canvas, changeButton);

        // 创建场景
        Scene scene = new Scene(root, 320, 380);

        // 设置舞台(窗口)属性
        primaryStage.setTitle("JavaFX表情切换器");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);

        // 初始绘制表情
        drawFace();

        // 显示舞台
        primaryStage.show();
    }

    // 绘制表情方法
    private void drawFace() {
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // 清除画布
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        // 绘制脸部轮廓(黄色圆形)
        gc.setFill(Color.YELLOW);
        gc.fillOval(50, 30, 200, 200);

        // 绘制黑色边框
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2);
        gc.strokeOval(50, 30, 200, 200);

        // 绘制眼睛
        gc.setFill(Color.BLACK);
        gc.fillOval(100, 80, 25, 25);  // 左眼
        gc.fillOval(175, 80, 25, 25);  // 右眼

        // 根据状态绘制嘴巴
        gc.setLineWidth(3);
        if (isHappy) {
            // 笑脸 - 绘制弧形向上的嘴巴
            gc.strokeArc(75, 80, 150, 100, 0, -180, javafx.scene.shape.ArcType.OPEN);
        } else {
            // 哭脸 - 绘制弧形向下的嘴巴
            gc.strokeArc(75, 120, 150, 100, 0, 180, javafx.scene.shape.ArcType.OPEN);

            // 添加眼泪
            gc.setFill(Color.BLUE);
            gc.fillOval(105, 105, 8, 20);  // 左眼泪
            gc.fillOval(185, 105, 8, 20);  // 右眼泪
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}