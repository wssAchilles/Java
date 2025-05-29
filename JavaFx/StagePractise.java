package JavaFx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class StagePractise extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Button button0=new Button("窗口0");
        Button button1=new Button("窗口1");
        button0.setLayoutX(200);
        button0.setLayoutY(200);
        button1.setLayoutX(200);
        button1.setLayoutY(200);

        button0.setOnAction(e->{
            Stage stage0=new Stage();
            stage0.setHeight(200);
            stage0.setWidth(200);
            stage0.initModality(Modality.APPLICATION_MODAL);
            stage0.getIcons().add(new Image("JavaFx/icon.png"));
            stage0.show();
        });

        button1.setOnAction(e->{
            Stage stage1=new Stage();
            stage1.setHeight(200);
            stage1.setWidth(200);
            stage1.initModality(Modality.APPLICATION_MODAL);
            stage1.getIcons().add(new Image("JavaFx/icon.png"));
            stage1.initStyle(StageStyle.UNIFIED);
            stage1.show();
        });


        AnchorPane pane = new AnchorPane();
        pane.getChildren().addAll(button0, button1);
        Scene scene = new Scene(pane, 300, 300);
        primaryStage.setScene(scene);

        primaryStage.setTitle("Hello JavaFX");
        //primaryStage.setResizable(false);

        //primaryStage.initStyle(StageStyle.UNIFIED);
        primaryStage.show();

    }
}
