package ex3_23030327;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ex3_3 extends Application {
    
    // 电梯状态
    private enum Direction {
        UP, DOWN, IDLE
    }
    
    // 乘客类
    private static class Person {
        private final int id;
        private final int sourceFloor;
        private final int targetFloor;
        
        public Person(int id, int sourceFloor, int targetFloor) {
            this.id = id;
            this.sourceFloor = sourceFloor;
            this.targetFloor = targetFloor;
        }
        
        @Override
        public String toString() {
            return "乘客" + id + " (从" + sourceFloor + "楼到" + targetFloor + "楼)";
        }
    }
    
    // 电梯类
    private static class Elevator {
        private int currentFloor;
        private Direction direction;
        private Person passenger;
        
        public Elevator() {
            this.currentFloor = 1;
            this.direction = Direction.IDLE;
            this.passenger = null;
        }
        
        public boolean isEmpty() {
            return passenger == null;
        }
        
        public int getCurrentFloor() {
            return currentFloor;
        }
        
        public void setCurrentFloor(int floor) {
            this.currentFloor = floor;
        }
        
        public void move() {
            if (direction == Direction.UP) {
                currentFloor++;
            } else if (direction == Direction.DOWN) {
                currentFloor--;
            }
        }
        
        public Direction getDirection() {
            return direction;
        }
        
        public void setDirection(Direction direction) {
            this.direction = direction;
        }
        
        public Person getPassenger() {
            return passenger;
        }
        
        public void loadPassenger(Person person) {
            this.passenger = person;
            if (person.targetFloor > currentFloor) {
                direction = Direction.UP;
            } else if (person.targetFloor < currentFloor) {
                direction = Direction.DOWN;
            }
        }
        
        public Person unloadPassenger() {
            Person p = passenger;
            passenger = null;
            direction = Direction.IDLE;
            return p;
        }
    }
    
    // 界面组件
    private ListView<String> logListView;
    private ObservableList<String> logItems;
    private ProgressBar floorIndicator;
    private Label elevatorStatusLabel;
    private Label floor1QueueLabel;
    private Label floor2QueueLabel;
    
    // 电梯和队列
    private final Elevator elevator = new Elevator();
    private final Queue<Person> floor1Queue = new LinkedList<>();
    private final Queue<Person> floor2Queue = new LinkedList<>();
    private int personIdCounter = 1;
    
    // 定时任务
    private ScheduledExecutorService executorService;
    
    @Override
    public void start(Stage primaryStage) {
        // 创建界面
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10));
        
        // 创建电梯状态区域
        VBox elevatorBox = new VBox(10);
        elevatorBox.setAlignment(Pos.CENTER);
        elevatorBox.setPadding(new Insets(10));
        
        elevatorStatusLabel = new Label("电梯状态: 空闲 (1楼)");
        floorIndicator = new ProgressBar(0);
        floorIndicator.setPrefWidth(200);
        
        floor1QueueLabel = new Label("1楼等待: 0人");
        floor2QueueLabel = new Label("2楼等待: 0人");
        
        Button addFloor1Button = new Button("1楼添加乘客");
        addFloor1Button.setOnAction(e -> addPerson(1));
        
        Button addFloor2Button = new Button("2楼添加乘客");
        addFloor2Button.setOnAction(e -> addPerson(2));
        
        Button startButton = new Button("开始模拟");
        startButton.setOnAction(e -> {
            startButton.setDisable(true);
            startSimulation();
        });
        
        elevatorBox.getChildren().addAll(
            elevatorStatusLabel, 
            floorIndicator, 
            floor1QueueLabel, 
            floor2QueueLabel,
            new HBox(10, addFloor1Button, addFloor2Button),
            startButton
        );
        
        // 创建日志区域
        VBox logBox = new VBox(10);
        logBox.setPadding(new Insets(10));
        
        Label logLabel = new Label("运行日志:");
        logItems = FXCollections.observableArrayList();
        logListView = new ListView<>(logItems);
        logListView.setPrefHeight(300);
        
        logBox.getChildren().addAll(logLabel, logListView);
        
        // 布局
        root.setTop(elevatorBox);
        root.setCenter(logBox);
        
        Scene scene = new Scene(root, 500, 600);
        primaryStage.setTitle("电梯模拟系统");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        primaryStage.setOnCloseRequest(e -> {
            if (executorService != null) {
                executorService.shutdown();
            }
        });
    }
    
    private void addPerson(int sourceFloor) {
        int targetFloor = (sourceFloor == 1) ? 2 : 1; // 简化为只有两层楼
        Person person = new Person(personIdCounter++, sourceFloor, targetFloor);
        
        if (sourceFloor == 1) {
            floor1Queue.add(person);
            updateFloor1QueueLabel();
        } else {
            floor2Queue.add(person);
            updateFloor2QueueLabel();
        }
        
        logMessage("添加" + person);
    }
    
    private void startSimulation() {
        executorService = Executors.newScheduledThreadPool(1);
        executorService.scheduleAtFixedRate(this::update, 0, 2, TimeUnit.SECONDS);
        
        // 随机添加乘客
        executorService.scheduleAtFixedRate(() -> {
            Platform.runLater(() -> {
                if (new Random().nextDouble() < 0.3) { // 30%概率添加乘客
                    addPerson(new Random().nextInt(2) + 1);
                }
            });
        }, 5, 5, TimeUnit.SECONDS);
    }
    
    private void update() {
        Platform.runLater(() -> {
            // 如果电梯是空的, 检查是否有人在等待
            if (elevator.isEmpty()) {
                Person nextPerson = null;
                
                // 如果电梯在1楼而且有人在等待
                if (elevator.getCurrentFloor() == 1 && !floor1Queue.isEmpty()) {
                    nextPerson = floor1Queue.poll();
                    updateFloor1QueueLabel();
                }
                // 如果电梯在2楼而且有人在等待
                else if (elevator.getCurrentFloor() == 2 && !floor2Queue.isEmpty()) {
                    nextPerson = floor2Queue.poll();
                    updateFloor2QueueLabel();
                }
                // 如果没有人在当前楼层等待，但另一楼层有人，则移动电梯
                else if (elevator.getCurrentFloor() == 1 && !floor2Queue.isEmpty()) {
                    elevator.setDirection(Direction.UP);
                    logMessage("电梯上移至2楼");
                    elevator.move();
                    updateElevatorStatus();
                    return;
                } else if (elevator.getCurrentFloor() == 2 && !floor1Queue.isEmpty()) {
                    elevator.setDirection(Direction.DOWN);
                    logMessage("电梯下移至1楼");
                    elevator.move();
                    updateElevatorStatus();
                    return;
                }
                
                // 如果有人等待电梯，让他们上电梯
                if (nextPerson != null) {
                    elevator.loadPassenger(nextPerson);
                    logMessage(nextPerson + " 进入电梯");
                    updateElevatorStatus();
                }
            } 
            // 如果电梯里有人
            else {
                Person passenger = elevator.getPassenger();
                
                // 如果电梯到达乘客的目的地，乘客下电梯
                if (elevator.getCurrentFloor() == passenger.targetFloor) {
                    Person departingPassenger = elevator.unloadPassenger();
                    logMessage(departingPassenger + " 到达目的地并离开电梯");
                    updateElevatorStatus();
                }
                // 如果电梯还没到达乘客的目的地，移动电梯
                else {
                    if (passenger.targetFloor > elevator.getCurrentFloor()) {
                        elevator.setDirection(Direction.UP);
                        logMessage("电梯上移至" + (elevator.getCurrentFloor() + 1) + "楼");
                    } else {
                        elevator.setDirection(Direction.DOWN);
                        logMessage("电梯下移至" + (elevator.getCurrentFloor() - 1) + "楼");
                    }
                    
                    elevator.move();
                    updateElevatorStatus();
                }
            }
        });
    }
    
    private void updateElevatorStatus() {
        String status;
        if (elevator.isEmpty()) {
            status = "空闲";
        } else {
            status = "载客: " + elevator.getPassenger();
        }
        
        elevatorStatusLabel.setText("电梯状态: " + status + " (" + elevator.getCurrentFloor() + "楼)");
        
        // 更新楼层指示器
        double progress = (elevator.getCurrentFloor() - 1) / 1.0; // 1楼为0，2楼为1
        floorIndicator.setProgress(progress);
    }
    
    private void updateFloor1QueueLabel() {
        floor1QueueLabel.setText("1楼等待: " + floor1Queue.size() + "人");
    }
    
    private void updateFloor2QueueLabel() {
        floor2QueueLabel.setText("2楼等待: " + floor2Queue.size() + "人");
    }
    
    private void logMessage(String message) {
        logItems.add(0, message);
        if (logItems.size() > 100) {
            logItems.remove(100, logItems.size());
        }
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}