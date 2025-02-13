package org.example.bookmanagement;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ManagementSystem extends Application {
    private double x = 0;
    private double y = 0;
    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml.fxml"));

        Parent root = fxmlLoader.load();

        Scene scene = new Scene(root);

        scene.setFill(javafx.scene.paint.Color.TRANSPARENT);

        root.setOnMousePressed((MouseEvent event) ->
        {
            x = event.getSceneX();
            y = event.getSceneY();
        });


        root.setOnMouseDragged((MouseEvent event) ->
        {
            stage.setX(event.getScreenX() - x);
            stage.setY(event.getScreenY() - y);
            stage.setOpacity(.8);
        });

        root.setOnMouseReleased((MouseEvent event) ->
        {
            stage.setOpacity(1);
        });


        stage.initStyle(StageStyle.TRANSPARENT);


        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
