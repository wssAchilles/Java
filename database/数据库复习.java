package database;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

// (1) 连接数据库 和 (2) 设置button界面
public class 数据库复习 extends Application {

    // 用一个文本区域来显示结果，代替控制台输出
    private TextArea resultTextArea = new TextArea();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("数据库操作界面");

        // 设置按钮
        Button insertButton = new Button("增 (Insert)");
        Button deleteButton = new Button("删 (Delete)");
        Button updateButton = new Button("改 (Update)");
        Button selectButton = new Button("查 (Select)");

        // 为每个按钮设置点击事件，直接调用下方和原文件几乎一样的方法
        insertButton.setOnAction(e -> Insert());
        deleteButton.setOnAction(e -> Delete());
        updateButton.setOnAction(e -> Update());
        selectButton.setOnAction(e -> Select());

        // 界面布局
        VBox vbox = new VBox(10); // 垂直布局，间距为10
        vbox.setPadding(new Insets(15, 15, 15, 15));
        vbox.getChildren().addAll(insertButton, deleteButton, updateButton, selectButton, resultTextArea);

        Scene scene = new Scene(vbox, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();

        // 启动时检查数据库是否为空
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


    // (3) 设置insert插入语句插入数据
    // 这个方法和你的 Insert() 方法逻辑完全一样
    public void Insert() {
        Connection conn = null;
        Statement stat = null;
        ResultSet rs = null; // 你的原始代码这里是null，保持一致
        try {
            // 1. 注册数据库驱动 // 2. 获取数据库连接
            conn = Config.getConn();

            // 3. 获取传输器
            stat = conn.createStatement();

            // 4. 执行sql语句
            String sql = "insert into administer(username,password) values('admin','88888888')";
            int count = stat.executeUpdate(sql);

            // 将结果显示在文本区域
            resultTextArea.setText("插入了" + count + "条数据");

        } catch (Exception e) {
            e.printStackTrace();
            resultTextArea.setText("插入失败！\n" + e.getMessage()); // 显示错误信息
        } finally {
            Config.close(conn, stat, rs);
        }
    }

    // (4) 删除表格语句
    // 这个方法和你的 Delete() 方法逻辑完全一样
    public void Delete() {
        Connection conn = null;
        Statement stat = null;
        // ResultSet rs = null; // 你的原始代码这里是null，保持一致
        try {
            // 1. 注册数据库驱动 // 2. 获取数据库连接
            conn = Config.getConn();

            // 3. 获取传输器
            stat = conn.createStatement();

            // 4. 执行sql语句
            String sql = "delete from administer where username='admin'";
            int count = stat.executeUpdate(sql);

            // 将结果显示在文本区域
            resultTextArea.setText("删除了" + count + "条数据");

        } catch (Exception e) {
            e.printStackTrace();
            resultTextArea.setText("删除失败！\n" + e.getMessage());
        } finally {
            Config.close(conn, stat, null);
        }
    }

    // 这个方法和你的 Update() 方法逻辑完全一样
    public void Update() {
        Connection conn = null;
        Statement stat = null;
        // ResultSet rs = null; // 你的原始代码这里是null，保持一致
        try {
            // 1. 注册数据库驱动 // 2. 获取数据库连接
            conn = Config.getConn();

            // 3. 获取传输器
            stat = conn.createStatement();

            // 4. 执行sql语句
            String sql = "update administer set password='5201314' where username='admin'";
            int count = stat.executeUpdate(sql);

            // 将结果显示在文本区域
            resultTextArea.setText("修改了" + count + "条数据");
        } catch (Exception e) {
            e.printStackTrace();
            resultTextArea.setText("修改失败！\n" + e.getMessage());
        } finally {
            Config.close(conn, stat, null);
        }
    }

    // 这个方法和你的 Select() 方法逻辑完全一样
    public void Select() {
        Connection conn = null;
        Statement stat = null;
        ResultSet rs = null;
        // 使用 StringBuilder 来拼接查询结果字符串
        StringBuilder results = new StringBuilder();
        try {
            // 1. 注册数据库驱动 // 2. 获取数据库连接
            conn = Config.getConn();

            // 3. 获取传输器
            stat = conn.createStatement();

            // 4. 执行sql语句
            String sql = "select * from administer";
            rs = stat.executeQuery(sql);

            // 5. 处理结果集
            while (rs.next()) {
                // 获取数据
                String username = rs.getString("username");
                String password = rs.getString("password");
                // 将每一行结果拼接到 StringBuilder
                results.append("用户名：").append(username).append("\t密码：").append(password).append("\n");
            }

            // 将拼接好的所有结果一次性设置到文本区域
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

    public static void main(String[] args) {
        launch(args);
    }
}