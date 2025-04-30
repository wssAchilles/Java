package JavaFx;
//--module-path E:\Javafx\javafx-sdk-24.0.1\lib --add-modules javafx.controls,javafx.fxml
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.*;

public class database extends Application {
    // MySQL database connection details
    private static final String URL = "jdbc:mysql://localhost:3306/research";
    private static final String USER = "root";
    private static final String PASSWORD = "758205Blns";

    private TableView<ObservableList<String>> tableView;
    private ObservableList<ObservableList<String>> data;

    private TextField idField;
    private TextField nameField;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // Initialize UI components
        tableView = new TableView<>();
        data = FXCollections.observableArrayList();

        idField = new TextField();
        idField.setPromptText("ID");
        nameField = new TextField();
        nameField.setPromptText("Name");

        Button addButton = new Button("Add");
        Button updateButton = new Button("Update");
        Button deleteButton = new Button("Delete");
        Button refreshButton = new Button("Refresh");

        addButton.setOnAction(e -> insertData());
        updateButton.setOnAction(e -> updateData());
        deleteButton.setOnAction(e -> deleteData());
        refreshButton.setOnAction(e -> loadDataFromDatabase("courses"));

        HBox inputBox = new HBox(10, idField, nameField, addButton, updateButton, deleteButton, refreshButton);
        VBox vbox = new VBox(10, tableView, inputBox);

        Scene scene = new Scene(vbox, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Research Database CRUD App");
        primaryStage.show();

        // Load initial data
        loadDataFromDatabase("courses");
    }

    // Method to load data from a specific table in the database
    private void loadDataFromDatabase(String tableName) {
        data.clear();
        tableView.getColumns().clear();

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM " + tableName)) {

            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            for (int i = 1; i <= columnCount; i++) {
                final int columnIndex = i - 1;
                TableColumn<ObservableList<String>, String> column = new TableColumn<>(metaData.getColumnName(i));
                column.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(columnIndex)));
                tableView.getColumns().add(column);
            }

            while (resultSet.next()) {
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = 1; i <= columnCount; i++) {
                    row.add(resultSet.getString(i));
                }
                data.add(row);
            }

            tableView.setItems(data);

        } catch (SQLException e) {
            showError("Error loading data: " + e.getMessage());
        }
    }

    // Method to insert data into the table
    private void insertData() {
        String name = nameField.getText();
        if (name.isEmpty()) {
            showError("Name cannot be empty.");
            return;
        }

        String query = "INSERT INTO courses (name) VALUES (?)";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, name);
            preparedStatement.executeUpdate();
            loadDataFromDatabase("courses");

        } catch (SQLException e) {
            showError("Error inserting data: " + e.getMessage());
        }
    }

    // Method to update data in the table
    private void updateData() {
        String id = idField.getText();
        String name = nameField.getText();

        if (id.isEmpty() || name.isEmpty()) {
            showError("Both ID and Name are required for update.");
            return;
        }

        String query = "UPDATE courses SET name = ? WHERE id = ?";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, Integer.parseInt(id));
            preparedStatement.executeUpdate();
            loadDataFromDatabase("courses");

        } catch (SQLException e) {
            showError("Error updating data: " + e.getMessage());
        }
    }

    // Method to delete data from the table
    private void deleteData() {
        String id = idField.getText();

        if (id.isEmpty()) {
            showError("ID is required for deletion.");
            return;
        }

        String query = "DELETE FROM courses WHERE id = ?";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, Integer.parseInt(id));
            preparedStatement.executeUpdate();
            loadDataFromDatabase("courses");

        } catch (SQLException e) {
            showError("Error deleting data: " + e.getMessage());
        }
    }

    // Method to show error messages
    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(message);
        alert.showAndWait();
    }
}