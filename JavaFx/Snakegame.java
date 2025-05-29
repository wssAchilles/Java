package JavaFx;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Snakegame extends Application {

    // 游戏区域的宽度和高度
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    // 游戏单元格的大小
    private static final int TILE_SIZE = 20;

    // 游戏速度（每毫秒） - 初始值，将根据难度调整
    private long gameSpeedNs = 150_000_000; // 150毫秒

    private GraphicsContext gc; // 用于绘制的图形上下文
    private List<Point> snake; // 蛇的身体节段
    private Point food; // 普通食物的位置
    private Point bonusFood; // 奖励食物的位置
    private long bonusFoodSpawnTime; // 奖励食物生成时间
    private static final long BONUS_FOOD_LIFETIME_NS = 5_000_000_000L; // 奖励食物存在时间（5秒）
    private static final int BONUS_FOOD_SCORE = 50; // 奖励食物得分
    private static final int REGULAR_FOOD_SCORE = 10; // 普通食物得分

    private Direction currentDirection; // 蛇当前移动的方向
    private boolean gameOver; // 游戏是否结束
    private int score; // 游戏得分
    private Label scoreLabel; // 用于显示分数的Label
    private Label messageLabel; // 用于显示游戏消息的Label (暂停/游戏结束)

    private AnimationTimer gameTimer; // 游戏主循环计时器

    private Stage primaryStage; // 主舞台
    private StackPane rootLayout; // 根布局，用于切换不同的游戏视图（菜单、游戏、设置等）

    private GameState currentGameState; // 当前游戏状态
    private List<Integer> highScores; // 高分列表

    // 定义游戏状态枚举
    private enum GameState {
        MENU, PLAYING, PAUSED, GAME_OVER, SETTINGS, HIGH_SCORES
    }

    // 定义方向枚举
    private enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    // 定义点类来表示蛇的节段和食物的位置
    private static class Point {
        int x, y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Point point = (Point) obj;
            return x == point.x && y == point.y;
        }

        @Override
        public int hashCode() {
            return java.util.Objects.hash(x, y);
        }
    }

    @Override
    public void start(Stage stage) {
        this.primaryStage = stage;
        rootLayout = new StackPane(); // 使用StackPane层叠不同的视图
        Scene scene = new Scene(rootLayout, WIDTH, HEIGHT);

        // 设置键盘事件监听，用于处理游戏中的控制和暂停
        scene.setOnKeyPressed(e -> {
            if (currentGameState == GameState.PLAYING) {
                // 游戏进行中，处理方向键
                if (e.getCode() == KeyCode.UP && currentDirection != Direction.DOWN) {
                    currentDirection = Direction.UP;
                } else if (e.getCode() == KeyCode.DOWN && currentDirection != Direction.UP) {
                    currentDirection = Direction.DOWN;
                } else if (e.getCode() == KeyCode.LEFT && currentDirection != Direction.RIGHT) {
                    currentDirection = Direction.LEFT;
                } else if (e.getCode() == KeyCode.RIGHT && currentDirection != Direction.LEFT) {
                    currentDirection = Direction.RIGHT;
                } else if (e.getCode() == KeyCode.P) { // 按P键暂停
                    setGameState(GameState.PAUSED);
                }
            } else if (currentGameState == GameState.PAUSED) {
                if (e.getCode() == KeyCode.P) { // 在暂停状态下按P键恢复
                    setGameState(GameState.PLAYING);
                }
            }
        });

        highScores = new ArrayList<>(); // 初始化高分列表

        primaryStage.setTitle("JavaFX 贪吃蛇");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false); // 禁止窗口大小调整
        primaryStage.show();

        // 初始显示主菜单
        setGameState(GameState.MENU);
    }

    // 根据游戏状态切换显示内容
    private void setGameState(GameState newState) {
        currentGameState = newState;
        rootLayout.getChildren().clear(); // 清除当前显示的所有节点

        switch (currentGameState) {
            case MENU:
                showMainMenu();
                break;
            case PLAYING:
                startGame();
                break;
            case PAUSED:
                showPausedScreen();
                break;
            case GAME_OVER:
                showGameOverScreen();
                break;
            case SETTINGS:
                showSettingsMenu();
                break;
            case HIGH_SCORES:
                showHighScoresScreen();
                break;
        }
    }

    // 显示主菜单
    private void showMainMenu() {
        VBox menuBox = new VBox(20); // 按钮之间间距20
        menuBox.setAlignment(Pos.CENTER);
        menuBox.setStyle("-fx-background-color: #333;");

        Label titleLabel = new Label("JavaFX 贪吃蛇");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 50));
        titleLabel.setTextFill(Color.LIMEGREEN);

        Button playButton = createMenuButton("开始游戏");
        playButton.setOnAction(e -> setGameState(GameState.PLAYING));

        Button settingsButton = createMenuButton("设置");
        settingsButton.setOnAction(e -> setGameState(GameState.SETTINGS));

        Button highScoresButton = createMenuButton("高分榜");
        highScoresButton.setOnAction(e -> setGameState(GameState.HIGH_SCORES));

        Button exitButton = createMenuButton("退出");
        exitButton.setOnAction(e -> primaryStage.close());

        menuBox.getChildren().addAll(titleLabel, playButton, settingsButton, highScoresButton, exitButton);
        rootLayout.getChildren().add(menuBox);
    }

    // 创建标准菜单按钮
    private Button createMenuButton(String text) {
        Button button = new Button(text);
        button.setPrefWidth(200);
        button.setPrefHeight(50);
        button.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        button.setTextFill(Color.WHITE);
        button.setStyle("-fx-background-color: #555; -fx-background-radius: 10; -fx-border-color: #888; -fx-border-width: 2; -fx-border-radius: 8;");
        // 鼠标悬停效果
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: #777; -fx-background-radius: 10; -fx-border-color: #EEE; -fx-border-width: 2; -fx-border-radius: 8;"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: #555; -fx-background-radius: 10; -fx-border-color: #888; -fx-border-width: 2; -fx-border-radius: 8;"));
        return button;
    }

    // 显示设置菜单
    private void showSettingsMenu() {
        VBox settingsBox = new VBox(20);
        settingsBox.setAlignment(Pos.CENTER);
        settingsBox.setStyle("-fx-background-color: #333;");

        Label titleLabel = new Label("游戏设置");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 40));
        titleLabel.setTextFill(Color.LIMEGREEN);

        Label speedLabel = new Label("游戏速度:");
        speedLabel.setFont(Font.font("Arial", 20));
        speedLabel.setTextFill(Color.WHITE);

        Slider speedSlider = new Slider(50, 250, 150); // min, max, default
        speedSlider.setBlockIncrement(10);
        speedSlider.setShowTickLabels(true);
        speedSlider.setShowTickMarks(true);
        speedSlider.setMajorTickUnit(50);
        speedSlider.setMinorTickCount(4);
        speedSlider.setSnapToTicks(true);
        speedSlider.setPrefWidth(300);

        // 绑定滑块值到游戏速度（反向）
        speedSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            // 将滑块值转换为纳秒，值越大速度越慢
            gameSpeedNs = (long) (newVal.doubleValue() * 1_000_000);
        });
        speedSlider.setValue(gameSpeedNs / 1_000_000); // 初始化滑块位置

        HBox speedControl = new HBox(10, speedLabel, speedSlider);
        speedControl.setAlignment(Pos.CENTER);

        Button backButton = createMenuButton("返回主菜单");
        backButton.setOnAction(e -> setGameState(GameState.MENU));

        settingsBox.getChildren().addAll(titleLabel, speedControl, backButton);
        rootLayout.getChildren().add(settingsBox);
    }

    // 显示高分榜
    private void showHighScoresScreen() {
        VBox scoresBox = new VBox(15);
        scoresBox.setAlignment(Pos.CENTER);
        scoresBox.setStyle("-fx-background-color: #333;");

        Label titleLabel = new Label("高分榜");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 40));
        titleLabel.setTextFill(Color.LIMEGREEN);

        // 确保高分列表排序
        List<Integer> sortedScores = highScores.stream()
                .sorted(Comparator.reverseOrder())
                .limit(10) // 只显示前10名
                .collect(Collectors.toList());

        if (sortedScores.isEmpty()) {
            Label noScoresLabel = new Label("还没有高分记录，快去玩吧！");
            noScoresLabel.setFont(Font.font("Arial", 20));
            noScoresLabel.setTextFill(Color.WHITE);
            scoresBox.getChildren().add(noScoresLabel);
        } else {
            for (int i = 0; i < sortedScores.size(); i++) {
                Label scoreEntry = new Label(String.format("%d. %d", i + 1, sortedScores.get(i)));
                scoreEntry.setFont(Font.font("Arial", 22));
                scoreEntry.setTextFill(Color.WHITE);
                scoresBox.getChildren().add(scoreEntry);
            }
        }

        Button backButton = createMenuButton("返回主菜单");
        backButton.setOnAction(e -> setGameState(GameState.MENU));

        scoresBox.getChildren().addAll(titleLabel, backButton);
        rootLayout.getChildren().add(scoresBox);
    }


    // 启动游戏
    private void startGame() {
        BorderPane gamePane = new BorderPane();
        gamePane.setStyle("-fx-background-color: #222;"); // 整个游戏区域的背景

        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        gc = canvas.getGraphicsContext2D();

        // 顶部HBox用于显示分数和消息
        HBox topBar = new HBox(10);
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setPrefHeight(50);
        topBar.setStyle("-fx-background-color: #111; -fx-padding: 10;");

        scoreLabel = new Label("得分: 0");
        scoreLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        scoreLabel.setTextFill(Color.WHITE);
        scoreLabel.setPrefWidth(WIDTH / 2); // 占据左半部分

        messageLabel = new Label("");
        messageLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        messageLabel.setTextFill(Color.ORANGE);
        messageLabel.setPrefWidth(WIDTH / 2); // 占据右半部分
        messageLabel.setAlignment(Pos.CENTER_RIGHT); // 靠右对齐

        topBar.getChildren().addAll(scoreLabel, messageLabel);

        gamePane.setTop(topBar);
        gamePane.setCenter(canvas); // 游戏画布在中间

        rootLayout.getChildren().add(gamePane); // 将游戏界面添加到根布局

        initGame(); // 初始化游戏状态

        // 确保计时器是新的并启动
        if (gameTimer != null) {
            gameTimer.stop();
        }
        gameTimer = new AnimationTimer() {
            long lastTick = 0; // 上次更新的时间戳

            @Override
            public void handle(long now) {
                if (currentGameState == GameState.PLAYING) {
                    if (now - lastTick > gameSpeedNs) {
                        lastTick = now;
                        tick(); // 每一帧更新游戏状态
                        draw(); // 每一帧重新绘制
                    }
                }
            }
        };
        gameTimer.start();
    }


    // 初始化游戏状态
    private void initGame() {
        snake = new ArrayList<>();
        // 初始蛇的长度为3，位于中间偏上位置
        snake.add(new Point(WIDTH / (2 * TILE_SIZE), HEIGHT / (2 * TILE_SIZE) - 2));
        snake.add(new Point(WIDTH / (2 * TILE_SIZE), HEIGHT / (2 * TILE_SIZE) - 1));
        snake.add(new Point(WIDTH / (2 * TILE_SIZE), HEIGHT / (2 * TILE_SIZE)));
        currentDirection = Direction.UP; // 初始向上移动
        score = 0;
        gameOver = false;
        bonusFood = null; // 初始化奖励食物为空
        generateFood(); // 生成第一个普通食物
        setScoreLabel(); // 更新分数显示
        messageLabel.setText(""); // 清空消息
    }

    // 显示暂停屏幕
    private void showPausedScreen() {
        // 停止游戏计时器
        if (gameTimer != null) {
            gameTimer.stop();
        }

        // 显示暂停信息
        messageLabel.setText("游戏暂停 (按 P 键恢复)");
        messageLabel.setTextFill(Color.YELLOW);
    }

    // 随机生成普通食物的位置
    private void generateFood() {
        Random rand = new Random();
        int foodX, foodY;
        do {
            foodX = rand.nextInt(WIDTH / TILE_SIZE);
            foodY = rand.nextInt((HEIGHT - 50) / TILE_SIZE); // 避开顶部状态栏
            food = new Point(foodX, foodY);
        } while (snake.contains(food) || (bonusFood != null && bonusFood.equals(food))); // 确保食物不生成在蛇或奖励食物上
    }

    // 随机生成奖励食物的位置
    private void generateBonusFood(long now) {
        Random rand = new Random();
        int bonusFoodX, bonusFoodY;
        do {
            bonusFoodX = rand.nextInt(WIDTH / TILE_SIZE);
            bonusFoodY = rand.nextInt((HEIGHT - 50) / TILE_SIZE); // 避开顶部状态栏
            bonusFood = new Point(bonusFoodX, bonusFoodY);
        } while (snake.contains(bonusFood) || food.equals(bonusFood)); // 确保奖励食物不生成在蛇或普通食物上

        bonusFoodSpawnTime = now; // 记录生成时间
    }

    // 游戏逻辑更新，每一帧调用一次
    private void tick() {
        // 获取蛇头当前位置
        Point head = snake.get(0);
        Point newHead = null;

        // 根据当前方向计算新蛇头的位置
        switch (currentDirection) {
            case UP:
                newHead = new Point(head.x, head.y - 1);
                break;
            case DOWN:
                newHead = new Point(head.x, head.y + 1);
                break;
            case LEFT:
                newHead = new Point(head.x - 1, head.y);
                break;
            case RIGHT:
                newHead = new Point(head.x + 1, head.y);
                break;
        }

        // 碰撞检测
        // 1. 撞墙
        if (newHead.x < 0 || newHead.x >= WIDTH / TILE_SIZE ||
                newHead.y < 0 || newHead.y >= (HEIGHT - 50) / TILE_SIZE) { // 考虑顶部状态栏
            gameOver = true;
            return;
        }

        // 2. 撞到自己
        if (snake.contains(newHead)) {
            gameOver = true;
            return;
        }

        // 3. 吃到食物
        if (newHead.equals(food)) {
            score += REGULAR_FOOD_SCORE; // 增加普通食物分数
            snake.add(0, newHead); // 蛇变长，新头直接加入
            generateFood(); // 重新生成普通食物
            setScoreLabel(); // 更新分数显示
        } else if (bonusFood != null && newHead.equals(bonusFood)) {
            // 吃到奖励食物
            score += BONUS_FOOD_SCORE; // 增加奖励食物分数
            snake.add(0, newHead); // 蛇变长
            bonusFood = null; // 奖励食物消失
            setScoreLabel(); // 更新分数显示
        } else {
            // 没吃到食物，蛇头前进，蛇尾移除，保持长度不变
            snake.add(0, newHead); // 在头部添加新节段
            snake.remove(snake.size() - 1); // 移除尾部节段
        }

        // 检查奖励食物是否过期或是否生成新的奖励食物
        if (bonusFood == null && new Random().nextInt(100) < 5) { // 5%几率生成奖励食物
            generateBonusFood(System.nanoTime());
        } else if (bonusFood != null && System.nanoTime() - bonusFoodSpawnTime > BONUS_FOOD_LIFETIME_NS) {
            bonusFood = null; // 奖励食物过期
        }

        // 如果游戏结束，则更新状态
        if (gameOver) {
            if (gameTimer != null) {
                gameTimer.stop(); // 停止游戏循环
            }
            highScores.add(score); // 将得分添加到高分列表
            setGameState(GameState.GAME_OVER);
        }
    }

    // 更新分数显示的Label
    private void setScoreLabel() {
        scoreLabel.setText("得分: " + score);
    }

    // 绘制游戏界面
    private void draw() {
        // 清空画布，绘制背景 (避开顶部的状态栏)
        gc.setFill(Color.web("#222")); // 使用深灰色作为背景
        gc.fillRect(0, 0, WIDTH, HEIGHT);

        // 绘制网格线
        gc.setStroke(Color.web("#444")); // 网格线颜色
        gc.setLineWidth(0.5);
        for (int x = 0; x < WIDTH / TILE_SIZE; x++) {
            gc.strokeLine(x * TILE_SIZE, 0, x * TILE_SIZE, HEIGHT);
        }
        for (int y = 0; y < HEIGHT / TILE_SIZE; y++) {
            gc.strokeLine(0, y * TILE_SIZE, WIDTH, y * TILE_SIZE);
        }

        // 绘制普通食物
        gc.setFill(Color.RED);
        // 使用圆角矩形代替圆形，使食物看起来更“方块”
        gc.fillRoundRect(food.x * TILE_SIZE, food.y * TILE_SIZE, TILE_SIZE, TILE_SIZE, TILE_SIZE / 4, TILE_SIZE / 4);

        // 绘制奖励食物
        if (bonusFood != null) {
            gc.setFill(Color.GOLD); // 奖励食物使用金色
            // 绘制一个带有小星星的奖励食物
            double centerX = bonusFood.x * TILE_SIZE + TILE_SIZE / 2;
            double centerY = bonusFood.y * TILE_SIZE + TILE_SIZE / 2;
            drawStar(gc, centerX, centerY, TILE_SIZE * 0.4, 5, 0.5); // 绘制一个五角星
        }

        // 绘制蛇
        for (int i = 0; i < snake.size(); i++) {
            Point p = snake.get(i);
            if (i == 0) {
                gc.setFill(Color.LIMEGREEN); // 蛇头
            } else if (i == snake.size() - 1) {
                gc.setFill(Color.DARKGREEN); // 蛇尾稍暗
            } else {
                gc.setFill(Color.GREEN); // 蛇身
            }
            // 绘制圆角矩形蛇身节段
            gc.fillRoundRect(p.x * TILE_SIZE, p.y * TILE_SIZE, TILE_SIZE, TILE_SIZE, TILE_SIZE / 4, TILE_SIZE / 4);
            gc.setStroke(Color.DARKOLIVEGREEN); // 蛇身边框
            gc.setLineWidth(1);
            gc.strokeRoundRect(p.x * TILE_SIZE, p.y * TILE_SIZE, TILE_SIZE, TILE_SIZE, TILE_SIZE / 4, TILE_SIZE / 4);
        }
    }

    // 绘制星星的辅助方法
    private void drawStar(GraphicsContext gc, double centerX, double centerY, double outerRadius, int numPoints, double innerRadiusRatio) {
        double innerRadius = outerRadius * innerRadiusRatio;
        double angleStep = Math.PI / numPoints;

        double[] xPoints = new double[numPoints * 2];
        double[] yPoints = new double[numPoints * 2];

        for (int i = 0; i < numPoints * 2; i++) {
            double currentRadius = (i % 2 == 0) ? outerRadius : innerRadius;
            double angle = i * angleStep - Math.PI / 2; // -PI/2 使星星尖角向上

            xPoints[i] = centerX + currentRadius * Math.cos(angle);
            yPoints[i] = centerY + currentRadius * Math.sin(angle);
        }
        gc.fillPolygon(xPoints, yPoints, numPoints * 2);
    }

    // 显示游戏结束屏幕
    private void showGameOverScreen() {
        VBox gameOverBox = new VBox(20);
        gameOverBox.setAlignment(Pos.CENTER);
        gameOverBox.setStyle("-fx-background-color: rgba(0, 0, 0, 0.8);"); // 半透明黑色背景

        Label gameOverTitle = new Label("游戏结束!");
        gameOverTitle.setFont(Font.font("Arial", FontWeight.BOLD, 60));
        gameOverTitle.setTextFill(Color.RED);

        Label finalScoreLabel = new Label("你的得分: " + score);
        finalScoreLabel.setFont(Font.font("Arial", FontWeight.BOLD, 40));
        finalScoreLabel.setTextFill(Color.WHITE);

        Button playAgainButton = createMenuButton("再玩一次");
        playAgainButton.setOnAction(e -> setGameState(GameState.PLAYING));

        Button backToMenuButton = createMenuButton("返回主菜单");
        backToMenuButton.setOnAction(e -> setGameState(GameState.MENU));

        gameOverBox.getChildren().addAll(gameOverTitle, finalScoreLabel, playAgainButton, backToMenuButton);
        rootLayout.getChildren().add(gameOverBox);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
