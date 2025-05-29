package JavaFx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class javafx extends Application {
    @Override
    public void start(Stage primaryStage) {
        Button btn = new Button("Say 'Hello JavaFX'");
        btn.setOnAction(e-> System.out.println("Hello, JavaFX!"));

        BorderPane pane = new BorderPane(btn);

        Scene scene = new Scene(pane, 300, 200);

        primaryStage.setTitle("JavaFX Test");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}