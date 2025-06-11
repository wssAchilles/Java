package database;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField; // 新增：用于输入用户名和密码
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Alert; // 新增：用于显示警告框

import java.sql.Connection;
import java.sql.PreparedStatement; // 新增：用于参数化查询（安全地写入数据库）
import java.sql.ResultSet;
import java.sql.Statement;

// (1) 连接数据库 和 (2) 设置button界面
public class 数据库复习 extends Application {

    // (1.1) 定义属性: labor (输入字段), text build (结果显示区域), btn (按钮)

    // text build: 用一个文本区域来显示结果，代替控制台输出
    private TextArea resultTextArea = new TextArea();

    // labor: 新增两个文本字段用于输入用户名和密码
    private TextField usernameField = new TextField();
    private TextField passwordField = new TextField();

    // btn: 新增保存、清空、读取按钮
    private Button saveButton = new Button("保存");
    private Button clearButton = new Button("清空");
    private Button readButton = new Button("读取");

    // 原有的增删改查按钮
    private Button insertButton = new Button("增 (Insert)");
    private Button deleteButton = new Button("删 (Delete)");
    private Button updateButton = new Button("改 (Update)");
    private Button selectButton = new Button("查 (Select)");

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("数据库操作界面");

        // 设置文本字段的提示文本
        usernameField.setPromptText("请输入用户名");
        passwordField.setPromptText("请输入密码");

        // 为所有按钮设置点击事件，直接调用下方对应的方法
        // 原有的增删改查事件
        insertButton.setOnAction(e -> Insert());
        deleteButton.setOnAction(e -> Delete());
        updateButton.setOnAction(e -> Update());
        selectButton.setOnAction(e -> Select());

        // (2) 实现四个事件: 保存、清空、读取 (和之前的增删改查)

        // 新增按钮的事件
        saveButton.setOnAction(e -> Save()); // 保存事件
        clearButton.setOnAction(e -> Clear()); // 清空事件
        readButton.setOnAction(e -> Read()); // 读取事件

        // 界面布局
        VBox vbox = new VBox(10); // 垂直布局，间距为10
        vbox.setPadding(new Insets(15, 15, 15, 15));
        vbox.getChildren().addAll(
                usernameField, // 用户名输入框
                passwordField, // 密码输入框
                saveButton,    // 保存按钮
                clearButton,   // 清空按钮
                readButton,    // 读取按钮
                insertButton,  // 增按钮
                deleteButton,  // 删按钮
                updateButton,  // 改按钮
                selectButton,  // 查按钮
                resultTextArea // 结果显示区域
        );

        Scene scene = new Scene(vbox, 400, 500); // 调整窗口大小以适应新增组件
        primaryStage.setScene(scene);
        primaryStage.show();

        // 启动时检查数据库是否为空，并给出警示
        checkIfEmptyOnStart();
    }

    // 启动时检查数据库是否为空，如果为空，则返回警告
    private void checkIfEmptyOnStart() {
        Connection conn = null;
        Statement stat = null;
        ResultSet rs = null;
        try {
            conn = Config.getConn();
            stat = conn.createStatement();
            String sql = "select * from administer";
            rs = stat.executeQuery(sql);

            // rs.isBeforeFirst() 检查是否有任何行，如果没有，则结果集为空
            if (!rs.isBeforeFirst()) {
                resultTextArea.setText("警告：数据库 'administer' 表为空！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            resultTextArea.setText("错误：连接数据库或查询时发生错误。\n" + e.getMessage());
        } finally {
            Config.close(conn, stat, rs);
        }
    }

    // (2.1) 保存事件：先检查是否为空，如果为空则跳出警示；然后保存到数据库
    public void Save() {
        String username = usernameField.getText().trim(); // 获取用户名输入
        String password = passwordField.getText().trim(); // 获取密码输入

        // (2.1.1) 检查是否为空 if语句
        if (username.isEmpty() || password.isEmpty()) {
            // (2.1.1.1) 如果等于空，则跳出警示
            showAlert("警告", "用户名和密码不能为空！");
            return; // 终止方法执行，不继续保存
        }

        // (2.1.2) 保存到数据库
        Connection conn = null;
        PreparedStatement pstmt = null; // 使用 PreparedStatement 防止 SQL 注入，更安全
        try {
            // (2.1.2.1) 数据库链接
            conn = Config.getConn();
            // (2.1.2.2) 把值取出 (已在上面通过 getText() 取出)
            // 通过映射语句写入数据库：使用参数化查询
            String sql = "insert into administer(username,password) values(?,?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username); // 映射第一个问号为用户名
            pstmt.setString(2, password); // 映射第二个问号为密码

            int count = pstmt.executeUpdate(); // 执行插入操作
            resultTextArea.setText("成功保存了" + count + "条数据到数据库。");

        } catch (Exception e) {
            e.printStackTrace();
            resultTextArea.setText("保存失败！\n" + e.getMessage());
        } finally {
            Config.close(conn, pstmt, null); // 关闭资源
        }
    }

    // (2.2) 清空事件：点击清空按钮，将text view清空
    public void Clear() {
        usernameField.clear(); // 清空用户名输入框
        passwordField.clear(); // 清空密码输入框
        resultTextArea.clear(); // 清空结果显示区域
        resultTextArea.setText("所有输入框和结果区域已清空。"); // 提示用户
    }

    // (2.3) 读取事件：select语句，再分别放入
    public void Read() {
        // 直接调用 Select 方法来读取数据并显示在 resultTextArea 中
        Select();
    }

    // 以下是原有的增删改查方法，保持不变（仅添加了注释）

    // (3) 设置insert插入语句插入数据
    public void Insert() {
        Connection conn = null;
        Statement stat = null;
        ResultSet rs = null;
        try {
            conn = Config.getConn();
            stat = conn.createStatement();
            String sql = "insert into administer(username,password) values('admin','88888888')";
            int count = stat.executeUpdate(sql);
            resultTextArea.setText("插入了" + count + "条数据");
        } catch (Exception e) {
            e.printStackTrace();
            resultTextArea.setText("插入失败！\n" + e.getMessage());
        } finally {
            Config.close(conn, stat, rs);
        }
    }

    // (4) 删除表格语句
    public void Delete() {
        Connection conn = null;
        Statement stat = null;
        try {
            conn = Config.getConn();
            stat = conn.createStatement();
            String sql = "delete from administer where username='admin'";
            int count = stat.executeUpdate(sql);
            resultTextArea.setText("删除了" + count + "条数据");
        } catch (Exception e) {
            e.printStackTrace();
            resultTextArea.setText("删除失败！\n" + e.getMessage());
        } finally {
            Config.close(conn, stat, null);
        }
    }

    // (5) 更新表格语句
    public void Update() {
        Connection conn = null;
        Statement stat = null;
        try {
            conn = Config.getConn();
            stat = conn.createStatement();
            String sql = "update administer set password='5201314' where username='admin'";
            int count = stat.executeUpdate(sql);
            resultTextArea.setText("修改了" + count + "条数据");
        } catch (Exception e) {
            e.printStackTrace();
            resultTextArea.setText("修改失败！\n" + e.getMessage());
        } finally {
            Config.close(conn, stat, null);
        }
    }

    // (6) 查询表格语句 (即读取功能)
    public void Select() {
        Connection conn = null;
        Statement stat = null;
        ResultSet rs = null;
        StringBuilder results = new StringBuilder();
        try {
            conn = Config.getConn();
            stat = conn.createStatement();
            String sql = "select * from administer";
            rs = stat.executeQuery(sql);
            while (rs.next()) {
                String username = rs.getString("username");
                String password = rs.getString("password");
                results.append("用户名：").append(username).append("\t密码：").append(password).append("\n");
            }

            if (results.length() == 0) {
                resultTextArea.setText("查询完成，表中没有数据。");
            } else {
                resultTextArea.setText(results.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
            resultTextArea.setText("查询失败！\n" + e.getMessage());
        } finally {
            Config.close(conn, stat, rs);
        }
    }

    // 辅助方法：显示警告框 (替代 alert())
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
