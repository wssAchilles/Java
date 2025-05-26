package JavaFx;

import javafx.application.Application;
import javafx.stage.Stage;

public class Hello extends Application {

    public static void main(String[] args) {

        launch(args);
    }
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("JavaFX");
        primaryStage.setWidth(500);
        primaryStage.setHeight(500);

        primaryStage.show();


    }
}
