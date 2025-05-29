package JavaFx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Hello extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        //标签label要选择javafx.scene.control包下的Label类，而不是java.awt包下的Label类
//        Label label = new Label("Hello, JavaFX!");
//        BorderPane pane=new BorderPane(label);
//        Scene scene = new Scene(pane, 300, 200);
//        stage.setTitle("Hello JavaFX");
//        stage.show();
        Button button = new Button("Click Me");
        BorderPane pane = new BorderPane(button);

        button.setOnAction(e->{
                getHostServices().showDocument("https://github.com/");
        });

        Scene scene = new Scene(pane, 300, 200);
        stage.setScene(scene);
        System.out.println("start()方法被调用");
        stage.setTitle("Hello JavaFX");
        stage.show();
    }

}
