package JavaFx;

import javafx.application.Application;
import javafx.application.Platform; // 添加Platform的直接导入
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JokeGenerator extends Application {

    private TextArea jokeTextArea;
    private Button generateButton;
    private Label statusLabel;

    // API URL for jokes
    private static final String JOKE_API_URL = "https://official-joke-api.appspot.com/random_joke";

    @Override
    public void start(Stage primaryStage) {
        // Initialize components
        Text titleText = new Text("Random Joke Generator");
        titleText.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        jokeTextArea = new TextArea();
        jokeTextArea.setWrapText(true);
        jokeTextArea.setEditable(false);
        jokeTextArea.setPrefRowCount(10);
        jokeTextArea.setPrefColumnCount(50);
        jokeTextArea.setFont(Font.font("Arial", 14));

        generateButton = new Button("Generate New Joke");
        generateButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        generateButton.setPrefWidth(150);

        statusLabel = new Label("Click the button to get a joke!");
        statusLabel.setFont(Font.font("Arial", 12));

        // Set button action
        generateButton.setOnAction(e -> fetchJoke());

        // Layout components
        VBox topBox = new VBox(10, titleText);
        topBox.setAlignment(Pos.CENTER);
        topBox.setPadding(new Insets(15));

        VBox centerBox = new VBox(15, jokeTextArea);
        centerBox.setPadding(new Insets(0, 15, 0, 15));

        VBox bottomBox = new VBox(10, generateButton, statusLabel);
        bottomBox.setAlignment(Pos.CENTER);
        bottomBox.setPadding(new Insets(15));

        // Main layout
        BorderPane root = new BorderPane();
        root.setTop(topBox);
        root.setCenter(centerBox);
        root.setBottom(bottomBox);
        root.setStyle("-fx-background-color: #f0f0f0;");

        // Set scene and show stage
        Scene scene = new Scene(root, 500, 400);
        primaryStage.setTitle("Random Joke Generator");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Load initial joke
        fetchJoke();
    }

    private void fetchJoke() {
        generateButton.setDisable(true);
        statusLabel.setText("Fetching joke...");

        // Create and start a new thread for the API call
        Thread apiThread = new Thread(() -> {
            try {
                // Make API request
                URL url = new URL(JOKE_API_URL);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");

                // Check response code
                int responseCode = conn.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    // Read response
                    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    String inputLine;
                    StringBuilder response = new StringBuilder();

                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();

                    // Parse JSON and extract joke
                    JSONParser parser = new JSONParser();
                    JSONObject jokeObject = (JSONObject) parser.parse(response.toString());

                    String setup = (String) jokeObject.get("setup");
                    String punchline = (String) jokeObject.get("punchline");
                    final String jokeText = setup + "\n\n" + punchline;

                    // 修改这里: 使用导入的Platform类而不是完整路径
                    Platform.runLater(() -> {
                        jokeTextArea.setText(jokeText);
                        statusLabel.setText("Joke loaded successfully!");
                        generateButton.setDisable(false);
                    });

                } else {
                    throw new IOException("API request failed with response code: " + responseCode);
                }

            } catch (IOException | ParseException e) {
                e.printStackTrace();

                // 修改这里: 使用导入的Platform类而不是完整路径
                Platform.runLater(() -> {
                    jokeTextArea.setText("Failed to fetch joke. Please try again.");
                    statusLabel.setText("Error: " + e.getMessage());
                    generateButton.setDisable(false);

                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Failed to fetch joke");
                    alert.setContentText("Error: " + e.getMessage());
                    alert.showAndWait();
                });
            }
        });

        apiThread.setDaemon(true);
        apiThread.start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}