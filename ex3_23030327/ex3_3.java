package ex3_23030327;
//23030327许子祺
/**
 * 编写一个最简单的 2 层楼的电梯管理模拟实验，假定电梯容纳 1 人，先来先上。
 */
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Reflection;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.paint.CycleMethod;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class ex3_3 extends Application {

    private Rectangle elevator;
    private Rectangle elevatorDoor;
    private Text floorDisplay;
    private Button upButton;
    private Button downButton;
    private int currentFloor = 1; // 开始在一楼

    // 楼层高度常量
    private static final double FLOOR_HEIGHT = 150;
    private static final double FLOOR1_Y = 350;
    private static final double FLOOR2_Y = 200;
    
    // 颜色常量
    private static final Color BUILDING_COLOR = Color.rgb(220, 220, 220);
    private static final Color ELEVATOR_COLOR = Color.rgb(100, 140, 230);
    private static final Color DOOR_COLOR = Color.rgb(180, 180, 180);
    private static final Color FLOOR_COLOR = Color.rgb(80, 80, 80);

    @Override
    public void start(Stage primaryStage) {
        // 创建模拟区域
        Pane simulationPane = new Pane();
        simulationPane.setPrefSize(500, 500);
        simulationPane.setStyle("-fx-background-color: linear-gradient(to bottom, #87CEFA, #E0F7FA);");
        
        // 创建建筑物外墙
        Rectangle building = new Rectangle(300, 400);
        building.setFill(BUILDING_COLOR);
        building.setStroke(Color.rgb(100, 100, 100));
        building.setStrokeWidth(2);
        building.setX(100);
        building.setY(50);
        
        // 添加阴影效果
        DropShadow buildingShadow = new DropShadow();
        buildingShadow.setColor(Color.rgb(50, 50, 50, 0.5));
        buildingShadow.setRadius(15);
        buildingShadow.setOffsetX(5);
        buildingShadow.setOffsetY(5);
        building.setEffect(buildingShadow);
        
        // 创建楼层
        Rectangle floor1 = new Rectangle(300, 5);
        floor1.setFill(FLOOR_COLOR);
        floor1.setX(100);
        floor1.setY(FLOOR1_Y);
        
        Rectangle floor2 = new Rectangle(300, 5);
        floor2.setFill(FLOOR_COLOR);
        floor2.setX(100);
        floor2.setY(FLOOR2_Y);
        
        // 创建电梯井道
        Rectangle shaft = new Rectangle(100, 350);
        shaft.setFill(Color.rgb(230, 230, 230));
        shaft.setStroke(Color.rgb(200, 200, 200));
        shaft.setX(200);
        shaft.setY(100);
        
        // 楼层标签
        Text floor1Text = new Text("1 楼");
        floor1Text.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        floor1Text.setFill(Color.rgb(50, 50, 50));
        floor1Text.setX(120);
        floor1Text.setY(FLOOR1_Y + 20);
        
        Text floor2Text = new Text("2 楼");
        floor2Text.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        floor2Text.setFill(Color.rgb(50, 50, 50));
        floor2Text.setX(120);
        floor2Text.setY(FLOOR2_Y + 20);
        
        // 创建电梯 - 高度小于楼层间距
        double elevatorHeight = FLOOR_HEIGHT - 20;
        double elevatorWidth = 80;
        elevator = new Rectangle(elevatorWidth, elevatorHeight);
        
        // 使用渐变色填充电梯
        LinearGradient elevatorGradient = new LinearGradient(
            0, 0, 1, 1, true, CycleMethod.NO_CYCLE,
            new Stop[]{new Stop(0, ELEVATOR_COLOR), new Stop(1, ELEVATOR_COLOR.darker())}
        );
        elevator.setFill(elevatorGradient);
        elevator.setStroke(Color.rgb(70, 70, 70));
        elevator.setStrokeWidth(2);
        elevator.setArcWidth(10);
        elevator.setArcHeight(10);
        elevator.setX(210); // 居中显示在电梯井道中
        
        // 添加电梯门
        elevatorDoor = new Rectangle(elevatorWidth - 10, elevatorHeight - 10);
        elevatorDoor.setFill(DOOR_COLOR);
        elevatorDoor.setStroke(Color.rgb(150, 150, 150));
        elevatorDoor.setStrokeWidth(1);
        elevatorDoor.setArcWidth(5);
        elevatorDoor.setArcHeight(5);
        elevatorDoor.setX(elevator.getX() + 5);
        
        // 添加电梯楼层显示器
        Rectangle displayPanel = new Rectangle(40, 30);
        displayPanel.setFill(Color.rgb(40, 40, 40));
        displayPanel.setArcWidth(5);
        displayPanel.setArcHeight(5);
        displayPanel.setX(elevator.getX() + (elevatorWidth - 40) / 2);
        
        floorDisplay = new Text("1");
        floorDisplay.setFont(Font.font("Verdana", FontWeight.BOLD, 18));
        floorDisplay.setFill(Color.rgb(255, 140, 0));
        floorDisplay.setX(elevator.getX() + elevatorWidth / 2 - 5);
        
        // 将所有元素添加到模拟区域
        simulationPane.getChildren().addAll(
            building, shaft, floor1, floor2, 
            floor1Text, floor2Text, elevator, elevatorDoor,
            displayPanel, floorDisplay
        );

        // 创建按钮
        upButton = new Button("↑ 上升");
        downButton = new Button("↓ 下降");
        
        String buttonStyle = "-fx-background-color: #2196F3; -fx-text-fill: white; " +
                          "-fx-font-size: 14px; -fx-font-weight: bold; " +
                          "-fx-padding: 10 20; -fx-background-radius: 5; " +
                          "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 5, 0, 0, 2);";
        String disabledStyle = "-fx-background-color: #9E9E9E; -fx-text-fill: #E0E0E0; " +
                              "-fx-font-size: 14px; -fx-font-weight: bold; " +
                              "-fx-padding: 10 20; -fx-background-radius: 5;";
        
        upButton.setStyle(buttonStyle);
        downButton.setStyle(disabledStyle); // 初始时下降按钮禁用
        
        upButton.setOnAction(e -> moveElevator(2));
        downButton.setOnAction(e -> moveElevator(1));
        
        // 创建电梯控制面板
        VBox controlPanel = new VBox(15);
        controlPanel.setAlignment(Pos.CENTER);
        controlPanel.setPadding(new Insets(15));
        controlPanel.setStyle("-fx-background-color: #EEEEEE; -fx-background-radius: 10; " +
                            "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 8, 0, 0, 3);");
        
        Text panelTitle = new Text("电梯控制");
        panelTitle.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        panelTitle.setFill(Color.rgb(70, 70, 70));
        
        controlPanel.getChildren().addAll(panelTitle, upButton, downButton);
        
        HBox buttonBox = new HBox(20, controlPanel);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(20));

        // 主布局
        BorderPane root = new BorderPane();
        root.setCenter(simulationPane);
        root.setBottom(buttonBox);
        root.setStyle("-fx-background-color: #F5F5F5;");
        
        // 添加标题
        Text title = new Text("电梯模拟系统");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        title.setFill(Color.rgb(50, 50, 120));
        
        HBox titleBox = new HBox(title);
        titleBox.setAlignment(Pos.CENTER);
        titleBox.setPadding(new Insets(15));
        titleBox.setStyle("-fx-background-color: #E3F2FD;");
        
        root.setTop(titleBox);

        Scene scene = new Scene(root, 500, 650);
        primaryStage.setTitle("电梯模拟 - 23030327许子祺");
        primaryStage.setScene(scene);
        primaryStage.show();

        // 初始化电梯在一楼
        moveElevatorToFloor(1);
        updateButtonStates();
    }

    private void moveElevator(int targetFloor) {
        if (targetFloor == currentFloor) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("电梯状态提醒");
            if (targetFloor == 1) {
                alert.setHeaderText("电梯无法下降");
                alert.setContentText("电梯已经在一楼了！");
            } else {
                alert.setHeaderText("电梯无法上升");
                alert.setContentText("电梯已经在二楼了！");
            }
            // 设置警告框样式
            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.setStyle("-fx-background-color: #F8F8F8; -fx-border-color: #E0E0E0;");
            alert.showAndWait();
            return;
        }

        // 在移动期间禁用按钮
        upButton.setDisable(true);
        downButton.setDisable(true);
        
        // 电梯门关闭动画
        ScaleTransition doorClose = new ScaleTransition(Duration.millis(500), elevatorDoor);
        doorClose.setToX(0.1);
        doorClose.setFromX(1.0);
        
        // 更新楼层显示
        floorDisplay.setText(String.valueOf(targetFloor));
        
        // 电梯移动动画
        TranslateTransition elevatorTransition = new TranslateTransition(Duration.seconds(1.5), elevator);
        double targetY = targetFloor == 1 ? FLOOR1_Y - elevator.getHeight() : FLOOR2_Y - elevator.getHeight();
        elevatorTransition.setToY(targetY - elevator.getY());
        
        // 电梯门也跟随移动
        TranslateTransition doorTransition = new TranslateTransition(Duration.seconds(1.5), elevatorDoor);
        double doorTargetY = targetY - elevatorDoor.getY() + 5; // 门的位置需要偏移一点
        doorTransition.setToY(doorTargetY);
        
        // 显示屏也跟随移动
        TranslateTransition displayTransition = new TranslateTransition(Duration.seconds(2), floorDisplay);
        displayTransition.setToY(targetY - floorDisplay.getY() + 20); // 调整显示位置
        
        // 电梯门打开动画
        ScaleTransition doorOpen = new ScaleTransition(Duration.millis(500), elevatorDoor);
        doorOpen.setToX(1.0);
        doorOpen.setFromX(0.1);
        
        // 顺序执行动画
        doorClose.setOnFinished(e -> {
            ParallelTransition parallelTransition = new ParallelTransition(elevatorTransition, doorTransition, displayTransition);
            parallelTransition.setOnFinished(event -> {
                // 更新电梯位置并重置动画
                elevator.setY(targetY);
                elevator.setTranslateY(0);
                elevatorDoor.setY(targetY + 5);
                elevatorDoor.setTranslateY(0);
                floorDisplay.setY(targetY - 25); // 更新显示器位置
                floorDisplay.setTranslateY(0);
                
                // 播放门打开动画
                doorOpen.play();
                
                // 更新当前楼层
                currentFloor = targetFloor;
                
                // 更新按钮状态
                updateButtonStates();
            });
            parallelTransition.play();
        });
        
        doorClose.play();
    }

    private void moveElevatorToFloor(int floor) {
        // 直接设置电梯位置，无动画
        double targetY = floor == 1 ? FLOOR1_Y - elevator.getHeight() : FLOOR2_Y - elevator.getHeight();
        elevator.setY(targetY);
        elevatorDoor.setY(targetY + 5); // 设置门的位置
        floorDisplay.setY(targetY - 25); // 设置显示器位置
        floorDisplay.setText(String.valueOf(floor)); // 更新显示数字
        currentFloor = floor;
    }

    private void updateButtonStates() {
        upButton.setDisable(currentFloor == 2);
        downButton.setDisable(currentFloor == 1);

        // 按钮样式常量
        String activeStyle = "-fx-background-color: #2196F3; -fx-text-fill: white; " +
                           "-fx-font-size: 14px; -fx-font-weight: bold; " +
                           "-fx-padding: 10 20; -fx-background-radius: 5; " +
                           "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 5, 0, 0, 2);";
        
        String disabledStyle = "-fx-background-color: #9E9E9E; -fx-text-fill: #E0E0E0; " +
                              "-fx-font-size: 14px; -fx-font-weight: bold; " +
                              "-fx-padding: 10 20; -fx-background-radius: 5;";

        // 更改按钮样式以反映状态
        if (currentFloor == 1) {
            upButton.setStyle(activeStyle);
            downButton.setStyle(disabledStyle);
        } else {
            upButton.setStyle(disabledStyle);
            downButton.setStyle(activeStyle);
        }
    }

    public static void main(String[] args) {

        launch(args);
    }
}