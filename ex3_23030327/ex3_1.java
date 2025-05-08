package ex3_23030327;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.sql.*;
import java.time.LocalDate;
import java.util.Optional;

public class ex3_1 extends Application {
    // 数据库连接信息
    private static final String URL = "jdbc:mysql://localhost:3306/student";
    private static final String USER = "root";
    private static final String PASSWORD = "758205Blns";
    
    // 界面组件
    private TextField idField;
    private TextField nameField;
    private TextField birthYearField;
    private TextField birthMonthField;
    private TextField birthDayField;
    private ComboBox<String> genderCombo;
    private TextField collegeField;
    private TextField majorField;
    private TextField classField;
    private TextField addressField;
    private TextField homePhoneField;
    private TextField mobilePhoneField;
    private TableView<Student> studentTable;
    private ObservableList<Student> studentData;
    
    // 数据库连接
    private Connection connection;
    // 主窗口
    private Stage mainStage;
    
    @Override
    public void start(Stage stage) {
        try {
            // 连接数据库
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            
            // 保存主窗口引用
            this.mainStage = stage;
            
            // 首先显示登录界面
            showLoginScreen(stage);
            
        } catch (SQLException e) {
            showAlert("错误", "数据库连接失败: " + e.getMessage());
        }
    }
    
    // 显示登录界面
    private void showLoginScreen(Stage stage) {
        // 创建登录页面组件
        VBox loginRoot = new VBox(10);
        loginRoot.setAlignment(Pos.CENTER);
        loginRoot.setPadding(new Insets(20));
        
        Label titleLabel = new Label("学生信息管理系统 - 登录");
        titleLabel.setStyle("-fx-font-size: 18; -fx-font-weight: bold;");
        
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));
        
        Label userLabel = new Label("用户名:");
        TextField usernameField = new TextField();
        usernameField.setPromptText("请输入用户名");
        
        Label passLabel = new Label("密码:");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("请输入密码");
        
        Button loginButton = new Button("登录");
        loginButton.setPrefWidth(100);
        
        // 添加登录事件
        loginButton.setOnAction(e -> {
            String username = usernameField.getText().trim();
            String password = passwordField.getText().trim();
            
            if (username.isEmpty() || password.isEmpty()) {
                showAlert("警告", "请输入用户名和密码");
                return;
            }
            
            // 验证登录信息
            if (validateLogin(username, password)) {
                // 登录成功，显示主界面
                showMainScreen(stage);
            } else {
                showAlert("错误", "用户名或密码错误！");
            }
        });
        
        // 布局组件
        grid.add(userLabel, 0, 0);
        grid.add(usernameField, 1, 0);
        grid.add(passLabel, 0, 1);
        grid.add(passwordField, 1, 1);
        
        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().add(loginButton);
        
        loginRoot.getChildren().addAll(titleLabel, grid, buttonBox);
        
        // 设置场景和舞台
        Scene scene = new Scene(loginRoot, 350, 250);
        stage.setTitle("登录");
        stage.setScene(scene);
        stage.show();
    }
    
    // 使用PreparedStatement验证登录信息，防止SQL注入
    private boolean validateLogin(String username, String password) {
        try {
            String query = "SELECT COUNT(*) FROM administer WHERE username = ? AND password = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, username);
            stmt.setString(2, password);
            
            ResultSet rs = stmt.executeQuery();
            rs.next();
            return rs.getInt(1) > 0;
            
        } catch (SQLException e) {
            showAlert("错误", "登录验证失败: " + e.getMessage());
            return false;
        }
    }
    
    // 显示主界面
    private void showMainScreen(Stage stage) {
        try {
            // 创建布局
            BorderPane root = new BorderPane();
            
            // 创建表单区域
            GridPane formPane = createFormPane();
            root.setTop(formPane);
            
            // 创建按钮区域
            HBox buttonBox = createButtonBox();
            root.setCenter(buttonBox);
            
            // 创建表格区域
            createTableView();
            root.setBottom(studentTable);
            
            // 设置场景和舞台
            Scene scene = new Scene(root, 900, 700);
            stage.setTitle("学生信息管理系统");
            stage.setScene(scene);
            stage.show();
            
            // 加载初始数据
            loadStudentData();
            
        } catch (Exception e) {
            showAlert("错误", "显示主界面失败: " + e.getMessage());
        }
    }
    
    // 创建表单区域
    private GridPane createFormPane() {
        GridPane formPane = new GridPane();
        formPane.setPadding(new Insets(10));
        formPane.setHgap(10);
        formPane.setVgap(10);
        
        // 添加表单字段
        int row = 0;
        
        formPane.add(new Label("学号:"), 0, row);
        idField = new TextField();
        formPane.add(idField, 1, row);
        
        formPane.add(new Label("姓名:"), 2, row);
        nameField = new TextField();
        formPane.add(nameField, 3, row);
        row++;
        
        formPane.add(new Label("出生年:"), 0, row);
        birthYearField = new TextField();
        formPane.add(birthYearField, 1, row);
        
        formPane.add(new Label("月:"), 2, row);
        birthMonthField = new TextField();
        formPane.add(birthMonthField, 3, row);
        
        formPane.add(new Label("日:"), 4, row);
        birthDayField = new TextField();
        formPane.add(birthDayField, 5, row);
        row++;
        
        formPane.add(new Label("性别:"), 0, row);
        genderCombo = new ComboBox<>(FXCollections.observableArrayList("男", "女"));
        genderCombo.setValue("男");
        formPane.add(genderCombo, 1, row);
        row++;
        
        formPane.add(new Label("所在学院:"), 0, row);
        collegeField = new TextField();
        formPane.add(collegeField, 1, row);
        
        formPane.add(new Label("专业:"), 2, row);
        majorField = new TextField();
        formPane.add(majorField, 3, row);
        
        formPane.add(new Label("班级:"), 4, row);
        classField = new TextField();
        formPane.add(classField, 5, row);
        row++;
        
        formPane.add(new Label("家庭住址:"), 0, row);
        addressField = new TextField();
        addressField.setPrefWidth(200);
        formPane.add(addressField, 1, row, 3, 1);
        row++;
        
        formPane.add(new Label("家庭电话:"), 0, row);
        homePhoneField = new TextField();
        formPane.add(homePhoneField, 1, row);
        
        formPane.add(new Label("本人电话:"), 2, row);
        mobilePhoneField = new TextField();
        formPane.add(mobilePhoneField, 3, row);
        
        return formPane;
    }
    
    // 创建按钮区域
    private HBox createButtonBox() {
        HBox buttonBox = new HBox(10);
        buttonBox.setPadding(new Insets(10));
        
        Button addButton = new Button("添加学生");
        addButton.setOnAction(e -> addStudent());
        
        Button queryButton = new Button("查询学生");
        queryButton.setOnAction(e -> queryStudent());
        
        Button updateButton = new Button("修改学生");
        updateButton.setOnAction(e -> editStudent());
        
        Button deleteButton = new Button("删除学生");
        deleteButton.setOnAction(e -> deleteStudent());
        
        Button clearButton = new Button("清空表单");
        clearButton.setOnAction(e -> clearFields());
        
        buttonBox.getChildren().addAll(addButton, queryButton, updateButton, deleteButton, clearButton);
        return buttonBox;
    }
    
    // 创建表格视图
    private void createTableView() {
        studentTable = new TableView<>();
        studentData = FXCollections.observableArrayList();
        studentTable.setItems(studentData);
        
        // 设置列
        createTableColumns();
        
        // 设置表格行选择事件
        studentTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                fillFormFromStudent(newSelection);
            }
        });
        
        // 设置表格大小
        studentTable.setPrefHeight(300);
    }
    
    // 创建表格列
    private void createTableColumns() {
        TableColumn<Student, String> idCol = new TableColumn<>("学号");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        
        TableColumn<Student, String> nameCol = new TableColumn<>("姓名");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        
        TableColumn<Student, String> birthdayCol = new TableColumn<>("出生日期");
        birthdayCol.setCellValueFactory(new PropertyValueFactory<>("birthday"));
        
        TableColumn<Student, String> genderCol = new TableColumn<>("性别");
        genderCol.setCellValueFactory(new PropertyValueFactory<>("gender"));
        
        TableColumn<Student, String> collegeCol = new TableColumn<>("学院");
        collegeCol.setCellValueFactory(new PropertyValueFactory<>("college"));
        
        TableColumn<Student, String> majorCol = new TableColumn<>("专业");
        majorCol.setCellValueFactory(new PropertyValueFactory<>("major"));
        
        TableColumn<Student, String> classCol = new TableColumn<>("班级");
        classCol.setCellValueFactory(new PropertyValueFactory<>("classname"));
        
        TableColumn<Student, String> addressCol = new TableColumn<>("地址");
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        
        TableColumn<Student, String> homePhoneCol = new TableColumn<>("家庭电话");
        homePhoneCol.setCellValueFactory(new PropertyValueFactory<>("homePhone"));
        
        TableColumn<Student, String> mobilePhoneCol = new TableColumn<>("手机");
        mobilePhoneCol.setCellValueFactory(new PropertyValueFactory<>("mobilePhone"));
        
        studentTable.getColumns().addAll(idCol, nameCol, birthdayCol, genderCol, 
                collegeCol, majorCol, classCol, addressCol, homePhoneCol, mobilePhoneCol);
    }
    
    // 从数据库加载学生数据
    private void loadStudentData() {
        try {
            studentData.clear();
            // 修改查询，按学号排序
            String query = "SELECT * FROM inform ORDER BY student_id ASC";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            
            while (rs.next()) {
                // 解析出生日期
                Date birthDate = rs.getDate("birth_date");
                java.util.Calendar cal = java.util.Calendar.getInstance();
                cal.setTime(birthDate);
                int birthYear = cal.get(java.util.Calendar.YEAR);
                int birthMonth = cal.get(java.util.Calendar.MONTH) + 1; // 月份从0开始
                int birthDay = cal.get(java.util.Calendar.DAY_OF_MONTH);
                
                Student student = new Student(
                    rs.getString("student_id"),
                    rs.getString("name"),
                    birthYear,
                    birthMonth,
                    birthDay,
                    rs.getString("gender"),
                    rs.getString("college"),
                    rs.getString("major"),
                    rs.getString("class"),
                    rs.getString("home_address"),
                    rs.getString("home_phone"),
                    rs.getString("personal_phone")
                );
                studentData.add(student);
            }
        } catch (SQLException e) {
            showAlert("错误", "加载学生数据失败: " + e.getMessage());
        }
    }
    
    // 添加学生
    private void addStudent() {
        if (!validateInput()) return;
        
        try {
            Student student = createStudentFromInput();
            
            // 检查学生ID是否已存在
            String checkQuery = "SELECT COUNT(*) FROM inform WHERE student_id = ?";
            PreparedStatement checkStmt = connection.prepareStatement(checkQuery);
            checkStmt.setString(1, student.getId());
            ResultSet rs = checkStmt.executeQuery();
            rs.next();
            if (rs.getInt(1) > 0) {
                showAlert("错误", "学号 " + student.getId() + " 已存在！");
                return;
            }
            
            // 创建日期对象
            java.sql.Date birthDate = java.sql.Date.valueOf(
                student.getBirthYear() + "-" + 
                String.format("%02d", student.getBirthMonth()) + "-" + 
                String.format("%02d", student.getBirthDay())
            );
            
            // 插入新学生记录 - 使用正确的表名和字段名
            String insertQuery = "INSERT INTO inform (student_id, name, birth_date, gender, " +
                              "college, major, class, home_address, home_phone, personal_phone) " +
                              "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            
            PreparedStatement insertStmt = connection.prepareStatement(insertQuery);
            insertStmt.setString(1, student.getId());
            insertStmt.setString(2, student.getName());
            insertStmt.setDate(3, birthDate);
            insertStmt.setString(4, student.getGender());
            insertStmt.setString(5, student.getCollege());
            insertStmt.setString(6, student.getMajor());
            insertStmt.setString(7, student.getClassname());
            insertStmt.setString(8, student.getAddress());
            insertStmt.setString(9, student.getHomePhone());
            insertStmt.setString(10, student.getMobilePhone());
            
            int result = insertStmt.executeUpdate();
            if (result > 0) {
                studentData.add(student);
                showAlert("成功", "学生添加成功！");
                clearFields();
            }
        } catch (SQLException e) {
            showAlert("错误", "添加学生失败: " + e.getMessage());
        }
    }
    
    // 查询学生
    private void queryStudent() {
        String id = idField.getText().trim();
        String name = nameField.getText().trim();
        
        if (id.isEmpty() && name.isEmpty()) {
            showAlert("提示", "请输入学号或姓名进行查询！");
            return;
        }
        
        try {
            StringBuilder queryBuilder = new StringBuilder("SELECT * FROM inform WHERE 1=1");
            if (!id.isEmpty()) queryBuilder.append(" AND student_id = '").append(id).append("'");
            if (!name.isEmpty()) queryBuilder.append(" AND name = '").append(name).append("'");
            
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(queryBuilder.toString());
            
            studentData.clear();
            boolean found = false;
            
            while (rs.next()) {
                found = true;
                
                // 解析出生日期
                Date birthDate = rs.getDate("birth_date");
                java.util.Calendar cal = java.util.Calendar.getInstance();
                cal.setTime(birthDate);
                int birthYear = cal.get(java.util.Calendar.YEAR);
                int birthMonth = cal.get(java.util.Calendar.MONTH) + 1; // 月份从0开始
                int birthDay = cal.get(java.util.Calendar.DAY_OF_MONTH);
                
                Student student = new Student(
                    rs.getString("student_id"),
                    rs.getString("name"),
                    birthYear,
                    birthMonth,
                    birthDay,
                    rs.getString("gender"),
                    rs.getString("college"),
                    rs.getString("major"),
                    rs.getString("class"),
                    rs.getString("home_address"),
                    rs.getString("home_phone"),
                    rs.getString("personal_phone")
                );
                studentData.add(student);
            }
            
            if (!found) {
                showAlert("提示", "未找到匹配的学生！");
                loadStudentData(); // 恢复所有学生数据
            } else if (studentData.size() == 1) {
                // 如果只找到一个学生，自动填充表单
                fillFormFromStudent(studentData.get(0));
            }
        } catch (SQLException e) {
            showAlert("错误", "查询学生失败: " + e.getMessage());
        }
    }
    
    // 修改学生信息
    private void editStudent() {
        String id = idField.getText().trim();
        if (id.isEmpty()) {
            showAlert("提示", "请输入要修改的学生学号！");
            return;
        }
        
        if (!validateInput()) return;
        
        try {
            // 检查学生是否存在
            String checkQuery = "SELECT COUNT(*) FROM inform WHERE student_id = ?";
            PreparedStatement checkStmt = connection.prepareStatement(checkQuery);
            checkStmt.setString(1, id);
            ResultSet rs = checkStmt.executeQuery();
            rs.next();
            if (rs.getInt(1) == 0) {
                showAlert("错误", "学号 " + id + " 不存在！");
                return;
            }
            
            // 获取表单中的学生信息
            Student student = createStudentFromInput();
            
            // 创建日期对象
            java.sql.Date birthDate = java.sql.Date.valueOf(
                student.getBirthYear() + "-" + 
                String.format("%02d", student.getBirthMonth()) + "-" + 
                String.format("%02d", student.getBirthDay())
            );
            
            // 更新学生记录 - 使用正确的表名和字段名
            String updateQuery = "UPDATE inform SET name = ?, birth_date = ?, gender = ?, " +
                              "college = ?, major = ?, class = ?, home_address = ?, " +
                              "home_phone = ?, personal_phone = ? WHERE student_id = ?";
            
            PreparedStatement updateStmt = connection.prepareStatement(updateQuery);
            updateStmt.setString(1, student.getName());
            updateStmt.setDate(2, birthDate);
            updateStmt.setString(3, student.getGender());
            updateStmt.setString(4, student.getCollege());
            updateStmt.setString(5, student.getMajor());
            updateStmt.setString(6, student.getClassname());
            updateStmt.setString(7, student.getAddress());
            updateStmt.setString(8, student.getHomePhone());
            updateStmt.setString(9, student.getMobilePhone());
            updateStmt.setString(10, id);
            
            int result = updateStmt.executeUpdate();
            if (result > 0) {
                showAlert("成功", "学生信息更新成功！");
                loadStudentData(); // 重新加载数据
                clearFields();
            }
        } catch (SQLException e) {
            showAlert("错误", "更新学生信息失败: " + e.getMessage());
        }
    }
    
    // 删除学生
    private void deleteStudent() {
        String id = idField.getText().trim();
        if (id.isEmpty()) {
            showAlert("提示", "请输入要删除的学生学号！");
            return;
        }
        
        try {
            // 确认删除
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("确认删除");
            alert.setHeaderText(null);
            alert.setContentText("确定要删除学号为 " + id + " 的学生吗？");
            Optional<ButtonType> result = alert.showAndWait();
            
            if (result.isPresent() && result.get() == ButtonType.OK) {
                // 执行删除
                String deleteQuery = "DELETE FROM inform WHERE student_id = ?";
                PreparedStatement deleteStmt = connection.prepareStatement(deleteQuery);
                deleteStmt.setString(1, id);
                
                int rowsAffected = deleteStmt.executeUpdate();
                if (rowsAffected > 0) {
                    showAlert("成功", "学生删除成功！");
                    loadStudentData(); // 重新加载数据
                    clearFields();
                } else {
                    showAlert("提示", "未找到学号为 " + id + " 的学生！");
                }
            }
        } catch (SQLException e) {
            showAlert("错误", "删除学生失败: " + e.getMessage());
        }
    }
    
    // 从表单创建学生对象
    private Student createStudentFromInput() {
        String studentId = idField.getText().trim();
        String name = nameField.getText().trim();
        int birthYear = Integer.parseInt(birthYearField.getText().trim());
        int birthMonth = Integer.parseInt(birthMonthField.getText().trim());
        int birthDay = Integer.parseInt(birthDayField.getText().trim());
        String gender = genderCombo.getValue();
        String college = collegeField.getText().trim();
        String major = majorField.getText().trim();
        String classname = classField.getText().trim();
        String homeAddress = addressField.getText().trim();
        String homePhone = homePhoneField.getText().trim();
        String personalPhone = mobilePhoneField.getText().trim();
        
        return new Student(studentId, name, birthYear, birthMonth, birthDay, gender, college, 
                          major, classname, homeAddress, homePhone, personalPhone);
    }
    
    // 使用学生对象填充表单
    private void fillFormFromStudent(Student student) {
        idField.setText(student.getId());
        nameField.setText(student.getName());
        birthYearField.setText(String.valueOf(student.getBirthYear()));
        birthMonthField.setText(String.valueOf(student.getBirthMonth()));
        birthDayField.setText(String.valueOf(student.getBirthDay()));
        genderCombo.setValue(student.getGender());
        collegeField.setText(student.getCollege());
        majorField.setText(student.getMajor());
        classField.setText(student.getClassname());
        addressField.setText(student.getAddress());
        homePhoneField.setText(student.getHomePhone());
        mobilePhoneField.setText(student.getMobilePhone());
    }
    
    // 验证输入
    private boolean validateInput() {
        if (idField.getText().trim().isEmpty() ||
            nameField.getText().trim().isEmpty() ||
            birthYearField.getText().trim().isEmpty() ||
            birthMonthField.getText().trim().isEmpty() ||
            birthDayField.getText().trim().isEmpty() ||
            collegeField.getText().trim().isEmpty() ||
            majorField.getText().trim().isEmpty() ||
            classField.getText().trim().isEmpty()) {
            showAlert("错误", "请填写所有必填字段！");
            return false;
        }
        
        try {
            int year = Integer.parseInt(birthYearField.getText().trim());
            int month = Integer.parseInt(birthMonthField.getText().trim());
            int day = Integer.parseInt(birthDayField.getText().trim());
            
            if (year < 1900 || year > 2023 || month < 1 || month > 12 || day < 1 || day > 31) {
                showAlert("错误", "日期格式不正确！");
                return false;
            }
            
            // 简单验证日期是否合法
            if (!validateAge(year, month, day)) {
                showAlert("错误", "日期不合法或超出范围！");
                return false;
            }
        } catch (NumberFormatException e) {
            showAlert("错误", "日期必须为数字！");
            return false;
        }
        
        return true;
    }
    
    // 验证年龄是否合法
    private boolean validateAge(int year, int month, int day) {
        try {
            // 检查日期是否有效
            LocalDate date = LocalDate.of(year, month, day);
            LocalDate now = LocalDate.now();
            
            // 检查是否未来日期
            if (date.isAfter(now)) {
                return false;
            }
            
            // 检查年龄是否在合理范围内（例如 10-100 岁）
            int age = now.getYear() - date.getYear();
            if (now.getMonthValue() < date.getMonthValue() ||
                (now.getMonthValue() == date.getMonthValue() && now.getDayOfMonth() < date.getDayOfMonth())) {
                age--;
            }
            
            return age >= 10 && age <= 100;
        } catch (Exception e) {
            return false;
        }
    }
    
    // 清空表单字段
    private void clearFields() {
        idField.clear();
        nameField.clear();
        birthYearField.clear();
        birthMonthField.clear();
        birthDayField.clear();
        genderCombo.setValue("男");
        collegeField.clear();
        majorField.clear();
        classField.clear();
        addressField.clear();
        homePhoneField.clear();
        mobilePhoneField.clear();
        
        // 清空表单后重新加载所有学生数据
        loadStudentData();
    }
    
    // 显示弹窗
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
    
    // 在关闭程序时释放数据库连接
    @Override
    public void stop() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // 学生类
    public static class Student {
        private String id; // 对应数据库中的student_id
        private String name;
        private int birthYear;
        private int birthMonth;
        private int birthDay;
        private String gender;
        private String college;
        private String major;
        private String classname;
        private String address; // 对应数据库中的home_address
        private String homePhone;
        private String mobilePhone; // 对应数据库中的personal_phone
        
        public Student(String id, String name, int birthYear, int birthMonth, int birthDay, 
                      String gender, String college, String major, String classname, 
                      String address, String homePhone, String mobilePhone) {
            this.id = id;
            this.name = name;
            this.birthYear = birthYear;
            this.birthMonth = birthMonth;
            this.birthDay = birthDay;
            this.gender = gender;
            this.college = college;
            this.major = major;
            this.classname = classname;
            this.address = address;
            this.homePhone = homePhone;
            this.mobilePhone = mobilePhone;
        }
        
        public String getId() { return id; }
        public String getName() { return name; }
        public int getBirthYear() { return birthYear; }
        public int getBirthMonth() { return birthMonth; }
        public int getBirthDay() { return birthDay; }
        public String getGender() { return gender; }
        public String getCollege() { return college; }
        public String getMajor() { return major; }
        public String getClassname() { return classname; }
        public String getAddress() { return address; }
        public String getHomePhone() { return homePhone; }
        public String getMobilePhone() { return mobilePhone; }
        
        // 获取格式化的生日字符串
        public String getBirthday() {
            return birthYear + "-" + birthMonth + "-" + birthDay;
        }
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
